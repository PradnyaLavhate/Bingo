package com.ptech.example.bingo.header

import android.content.DialogInterface
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ViewModel
import com.ptech.example.bingo.provider.AlertDialogProvider
import com.ptech.example.bingo.util.BingoEventManager
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class HeaderViewModel @Inject constructor(
    private val bingoEventManager: BingoEventManager,
    private val alertDialogProvider: AlertDialogProvider
): ViewModel(),LifecycleObserver {

    var currentBingoNumber = ObservableField<String>("0")
    var isStartButtonEnabled = ObservableBoolean(true)


    private val disposable  = CompositeDisposable()

    init {
        subscribeToShowBingoHeaderEvents()
    }

    private fun subscribeToShowBingoHeaderEvents() {
        bingoEventManager.getEventPublisher()
            .subscribeOn(Schedulers.io())
            .subscribe {
                currentBingoNumber.set(it?.toString())
            }.let(disposable::add)
    }

    fun startBingoEvents() {
        isStartButtonEnabled.set(false)
        with(bingoEventManager) {
            startBingoEvents()?.let(disposable::add)
            listenForBingoCompletion().let(disposable::add)
        }
    }

    private fun BingoEventManager.listenForBingoCompletion() =
        getBingoCompleteEvent().subscribe {
            with(alertDialogProvider) {
                getSorryDialog()
                    .apply { primaryBtnListener = getSorryDialogListener() }
                    .let(this::showAlertDialog)
            }
        }

    private fun getSorryDialogListener() =
        DialogInterface.OnClickListener { p0, p1 -> onClickOfSorryDialog().also { p0.dismiss() } }

    private fun onClickOfSorryDialog() {
        isStartButtonEnabled.set(true)
        bingoEventManager.resetBingoChart()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy (){
        disposable.dispose()
    }
}