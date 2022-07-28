package com.ptech.example.bingo.provider

import com.ptech.example.bingo.di.BingoScope
import com.ptech.example.bingo.modals.AlertDialogModal
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

@BingoScope
class AlertDialogProvider @Inject constructor() {

    private val alertDialogPublishSubject = PublishSubject.create<AlertDialogModal>()

    fun alertDialogListener(): Observable<AlertDialogModal> = alertDialogPublishSubject.hide()

    fun showAlertDialog(dialog : AlertDialogModal) {
        alertDialogPublishSubject.onNext(dialog)
    }


    fun getCongratulationDialog(): AlertDialogModal {
        return AlertDialogModal().apply {
            title = "Congratulation !!"
            message = "You got bingo."
            primaryButton = "Okay"
        }
    }

    fun getSorryDialog(): AlertDialogModal {
        return AlertDialogModal().apply {
            title = "Sorry !!"
            message = "You didn't get bingo."
            primaryButton = "Okay"
        }
    }
}