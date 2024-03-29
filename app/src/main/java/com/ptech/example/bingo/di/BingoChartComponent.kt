package com.ptech.example.bingo.di

import com.ptech.example.bingo.bingochart.BingoChartFragment
import com.ptech.example.bingo.header.HeaderFragment
import dagger.Component


@BingoScope
@Component(modules = [BingoChartModule::class])
interface BingoChartComponent {

    fun injectFor(fragment : BingoChartFragment)

    fun injectDataFor(fragment : HeaderFragment)
}