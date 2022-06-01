package com.ptech.example.bingo.header

import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ViewModel
import com.ptech.example.bingo.util.BingoEventManager
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class HeaderViewModel @Inject constructor(private val bingoEventManager: BingoEventManager): ViewModel(),LifecycleObserver {

    private val TAG = "HeaderViewModel"
    var currentBingoNumber = ObservableField<String>("0")


    private val disposable  = CompositeDisposable()

    init {
        //generate the number after every 1 min and set it to observable
        //currentBingoNumber
        bingoEventManager.getEventPublisher()
            .subscribeOn(Schedulers.io())
            .subscribe {
                Log.d(TAG, "after 5 second $it")
                currentBingoNumber.set(it?.toString())//0 -99
            }.let(disposable::add)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResume(){
        Log.d(TAG,"HeaderViewModel bingoEventManager $bingoEventManager")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy (){
        disposable.dispose()
    }
}