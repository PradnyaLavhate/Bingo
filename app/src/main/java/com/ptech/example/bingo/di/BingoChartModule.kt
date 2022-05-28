package com.ptech.example.bingo.di

import com.ptech.example.bingo.BingoEventManager
import com.ptech.example.bingo.bingochart.BingoChartData
import dagger.Module
import dagger.Provides

@Module
class BingoChartModule {

    @Provides
    fun provideBingoChartData() : BingoChartData = BingoChartData.BingoChartFactory().newInstance()

    @Provides
    fun provideBingoEventManager() : BingoEventManager = BingoEventManager()
}