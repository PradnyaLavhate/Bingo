package com.ptech.example.bingo.bingochart

import android.util.Log
import androidx.databinding.ObservableField
import java.util.*
import javax.inject.Inject
import kotlin.random.Random

class BingoChartData private constructor(){

    val bingoSize = 5

    val bingoMatrix =  IntArray(bingoSize*bingoSize){ Random.nextInt(100) }

    class BingoChartFactory @Inject constructor(){
        fun newInstance() = BingoChartData()
    }
}