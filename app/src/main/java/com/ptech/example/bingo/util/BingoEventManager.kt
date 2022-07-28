package com.ptech.example.bingo.util

import com.ptech.example.bingo.di.BingoScope
import com.ptech.example.bingo.util.Config.Companion.BINGO_HIGHEST_LIMIT
import com.ptech.example.bingo.util.Config.Companion.BINGO_SET
import com.ptech.example.bingo.util.Config.Companion.BINGO_TIMER
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.ObservableOnSubscribe
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import kotlin.random.Random

@BingoScope
class BingoEventManager @Inject constructor() {

    //TODO change the observable creation
    private val publishSubject = PublishSubject.create<Long>()
    fun getEventPublisher(): Observable<Long> = publishSubject.hide()

    private val bingoCompleteEvent = PublishSubject.create<Boolean>()
    fun getBingoCompleteEvent (): Observable<Boolean> = bingoCompleteEvent.hide()


    fun startBingoEvents(): Disposable? {
        return Observable
            .intervalRange(0L, BINGO_SET, 0, BINGO_TIMER, TimeUnit.SECONDS)
            .doOnComplete{ bingoCompleteEvent.onNext(true) }
            .doOnError { bingoCompleteEvent.onNext(false) }
            .subscribe {
                publishSubject.onNext(Random.nextLong(BINGO_HIGHEST_LIMIT))
            }
    }

    fun getResetEvent() =  Observable.create(emit)

    fun resetBingoChart(){
        emit.onNext(true)
    }

    object emit : ObservableOnSubscribe<Boolean> {
        private var _emitter :  ObservableEmitter<Boolean>? = null
        override fun subscribe(emitter: ObservableEmitter<Boolean>) {
            _emitter = emitter
        }

        fun onNext(value : Boolean){
            _emitter?.onNext(value)
        }
    }


}

