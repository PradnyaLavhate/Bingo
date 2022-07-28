package com.ptech.example.bingo.bingochart


import android.content.DialogInterface
import android.util.Log
import androidx.annotation.VisibleForTesting
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ViewModel
import com.ptech.example.bingo.util.BingoEventManager
import com.ptech.example.bingo.adapter.BingoChartAdapter
import com.ptech.example.bingo.provider.AlertDialogProvider
import com.ptech.example.bingo.util.Config.Companion.BINGO_CHART_SIZE
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class BingoChartViewModel @Inject constructor(
    private val bingoChartFactory: BingoChartData.BingoChartFactory,
    private val bingoEventManager: BingoEventManager,
    val bingoChartAdapter: BingoChartAdapter,
    private val alertDialogProvider: AlertDialogProvider
) : ViewModel(), LifecycleObserver {


    private val TAG: String = "BingoChart"
    var bingoChart: BingoChartData = bingoChartFactory.newInstance()

    private val compositeDisposable = CompositeDisposable()


    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun loadChartData() {
        bingoChartAdapter.bingoList = bingoChart.bingoMatrix
            .map { BingoChartItemVM().apply { element.set(it) } }
                as MutableList<BingoChartItemVM>

        bingoChartAdapter.notifyDataSetChanged()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun listenToBingoFlow() {
        Log.d(TAG, "BingoChartViewModel  bingoEventManager $bingoEventManager")
        bingoEventManager
            .getEventPublisher()
            .subscribeOn(Schedulers.io())
            .subscribe {
                Log.d(TAG, "listenToBingoFlow::$it")
                updateBingoChartAdapter(it)
            }.let(compositeDisposable::add)
    }

    private fun updateBingoChartAdapter(currentBingo: Long) {
        bingoChartAdapter.bingoList.find {
            it.element.get().toLong() == currentBingo
        }?.let {
            it.isElementCanceled.set(true)
        }
        checkForBingo()
    }

    private fun checkForBingo() {
        bingoChartAdapter.bingoList
            .map {
                it.isElementCanceled.get()
            }.let(::checkForBingoOnCheckedElements)
            .takeIf { it }
            ?.let { showBingoAlert() }
    }

    private fun showBingoAlert() {
        alertDialogProvider.run {
            val dialog = getCongratulationDialog().apply {
                primaryBtnListener = object : DialogInterface.OnClickListener {
                    override fun onClick(p0: DialogInterface?, p1: Int) {

                    }
                }
            }
            showAlertDialog(dialog)
        }
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun checkForBingoOnCheckedElements(elementsChecked: List<Boolean>): Boolean {
        var rows: MutableList<List<Boolean>> = mutableListOf()
        for (index in 0 until BINGO_CHART_SIZE) {
            elementsChecked
                .subList(index * BINGO_CHART_SIZE, (index * BINGO_CHART_SIZE) + BINGO_CHART_SIZE)
                .let { rows.add(it) }
        }
        return ifRowBingo(rows)
            .takeIf { !it }
            ?.let { ifColumnBingo(rows) }
            ?.takeIf { !it }
            ?.let {  ifDiagonalBingo(rows) }
            ?: true
    }

    private fun ifDiagonalBingo(rows: MutableList<List<Boolean>>): Boolean {
        var result = false

        //left to right diagonal

        rows.filterIndexed { index, list -> list[index] }
            .takeIf { it.size == BINGO_CHART_SIZE }
            ?.let { result = true }
            ?: run {
                //right to left
                rows.filterIndexed { index, list -> list[BINGO_CHART_SIZE - index - 1]  }
                    .takeIf { it.size == BINGO_CHART_SIZE }
                    ?.let {  result = true }
        }
        return result
    }

    private fun ifRowBingo(rows: MutableList<List<Boolean>>): Boolean {
        var result = false

        run breakCondition@{
            rows.forEach { list ->
                list.filter { isElementCanceled -> !isElementCanceled }
                    .takeIf { it.isEmpty() }
                    ?.let {
                        result = true
                        return@breakCondition
                    }
            }
        }
        return result
    }

    private fun ifColumnBingo(rows: MutableList<List<Boolean>>): Boolean {
        var result = false

        kotlin.run breakCondition@{
            for (index in 0 until BINGO_CHART_SIZE) {
                rows.filter { it[index] }
                    .takeIf { resultList -> resultList.size == 5 }
                    ?.let {
                        result = true
                        return@breakCondition
                    }
            }
        }

        return result
    }


    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() {
        compositeDisposable.dispose()
    }

}
