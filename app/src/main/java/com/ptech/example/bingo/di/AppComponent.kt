package com.ptech.example.bingo.di

import android.app.Activity
import android.app.Application
import android.content.Context

class AppComponent :Application() {

    val component: BingoChartComponent by lazy {
        DaggerBingoChartComponent
            .builder()
            .build()
    }

    companion object{
        fun getApplication(context :Context) = context.applicationContext as AppComponent
    }
}