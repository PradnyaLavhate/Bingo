package com.ptech.example.bingo.bingochart

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableInt
import androidx.lifecycle.ViewModel
import javax.inject.Inject

class BingoChartItemVM @Inject constructor(): ViewModel() {
    var element = ObservableInt(0)
    val isElementCanceled = ObservableBoolean(false)
}