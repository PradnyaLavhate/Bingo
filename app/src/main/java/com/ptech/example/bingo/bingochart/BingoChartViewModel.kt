package com.ptech.example.bingo.bingochart

import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ViewModel
import com.ptech.example.bingo.util.BingoEventManager
import com.ptech.example.bingo.adapter.BingoChartAdapter
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class BingoChartViewModel @Inject constructor(
    private val bingoChartFactory : BingoChartData.BingoChartFactory,
    private val bingoEventManager: BingoEventManager,
    val bingoChartAdapter : BingoChartAdapter) : ViewModel(), LifecycleObserver {


    private val TAG: String = "BingoChart"
    var bingoChart: BingoChartData = bingoChartFactory.newInstance()

    private val compositeDisposable = CompositeDisposable()


    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun loadChartData(){
        bingoChartAdapter.numberList = bingoChart.bingoMatrix
            .map { BingoChartItemVM().apply { element.set(it)  } }
                as MutableList<BingoChartItemVM>

        bingoChartAdapter.notifyDataSetChanged()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun listenToBingoFlow(){
        Log.d(TAG,"BingoChartViewModel  bingoEventManager $bingoEventManager")
        bingoEventManager
            .getEventPublisher()
            .subscribeOn(Schedulers.io())
            .subscribe {
                Log.d(TAG,"listenToBingoFlow::$it")
                updateBingoChartAdapter(it)
            }.let(compositeDisposable::add)
    }

    private fun updateBingoChartAdapter(currentBingo : Long){
       bingoChartAdapter.numberList.find {
           it.element.get().toLong() == currentBingo
       }?.let {
         it.isElementCanceled.set(true)
       }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy(){
        compositeDisposable.dispose()
    }
}

