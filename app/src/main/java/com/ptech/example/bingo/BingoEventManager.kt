package com.ptech.example.bingo

import io.reactivex.Observable
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton
@Singleton
class BingoEventManager @Inject constructor(){
    private val bingoTimer: Long = 5

     val eventPublisher: Observable<Long> = Observable
            .intervalRange(0L, 100L, 0, bingoTimer, TimeUnit.SECONDS)
}

