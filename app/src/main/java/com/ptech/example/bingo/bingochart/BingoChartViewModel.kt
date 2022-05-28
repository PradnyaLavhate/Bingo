package com.ptech.example.bingo.bingochart

import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ViewModel
import com.ptech.example.bingo.adapter.BingoChartAdapter
import javax.inject.Inject

class BingoChartViewModel @Inject constructor(
    private val bingoChartFactory : BingoChartData.BingoChartFactory,
    val bingoChartAdapter : BingoChartAdapter) : ViewModel(), LifecycleObserver {


    var bingoChart: BingoChartData = bingoChartFactory.newInstance()


    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun loadChartData(){
        bingoChartAdapter.numberList = bingoChart.bingoMatrix
            .map { BingoChartItemVM().apply { element.set(it)  } }
                as MutableList<BingoChartItemVM>

        bingoChartAdapter.notifyDataSetChanged()
    }

}

