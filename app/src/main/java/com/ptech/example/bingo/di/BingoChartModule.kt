package com.ptech.example.bingo.di

import com.ptech.example.bingo.util.BingoEventManager
import dagger.Module
import dagger.Provides

@Module
class BingoChartModule {

    @BingoScope
    @Provides
    fun provideBingoEventManager() : BingoEventManager = BingoEventManager()
}