package com.ptech.example.bingo.util

import com.ptech.example.bingo.di.BingoScope
import com.ptech.example.bingo.util.Config.Companion.BINGO_SET
import com.ptech.example.bingo.util.Config.Companion.BINGO_TIMER
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import kotlin.random.Random

@BingoScope
class BingoEventManager @Inject constructor() {

    fun startEvents(): Disposable? {
        return Observable
            .intervalRange(0L, BINGO_SET, 0, BINGO_TIMER, TimeUnit.SECONDS)
            .doOnComplete{ }
            .subscribe {
                publishSubject.onNext(Random.nextLong(100))
            }
    }

    private val publishSubject = PublishSubject.create<Long>()

    fun getEventPublisher(): Observable<Long> = publishSubject.hide()



}

