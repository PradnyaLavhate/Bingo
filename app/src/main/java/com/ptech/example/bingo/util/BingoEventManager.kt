package com.ptech.example.bingo.util

import com.ptech.example.bingo.di.BingoScope
import com.ptech.example.bingo.util.Config.Companion.BINGO_SET
import com.ptech.example.bingo.util.Config.Companion.BINGO_TIMER
import io.reactivex.Observable
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import kotlin.random.Random

@BingoScope
class BingoEventManager @Inject constructor(){

    private val eventPublisher: Observable<Long> = Observable
        .intervalRange(0L, BINGO_SET, 0, BINGO_TIMER, TimeUnit.SECONDS)
        .map { Random.nextLong(100) }

    fun getEventPublisher() = eventPublisher
}

