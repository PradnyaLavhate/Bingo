package com.ptech.example.bingo.bingochart


import com.ptech.example.bingo.util.Config.Companion.BINGO_CHART_SIZE
import com.ptech.example.bingo.util.Config.Companion.BINGO_HIGHEST_LIMIT
import javax.inject.Inject
import kotlin.random.Random

class BingoChartData private constructor(){


    ///TODO
    val bingoMatrix =
        IntArray(BINGO_CHART_SIZE * BINGO_CHART_SIZE) { Random.nextInt(BINGO_HIGHEST_LIMIT.toInt()) }

    class BingoChartFactory @Inject constructor() {
        fun newInstance() = BingoChartData()
    }
}