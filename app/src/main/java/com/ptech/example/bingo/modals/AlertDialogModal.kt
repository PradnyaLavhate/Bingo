package com.ptech.example.bingo.modals

import android.content.DialogInterface


class AlertDialogModal() {

    var title = "Bingo"
    var message = "Please Add Description"
    private var hasPrimaryButton = false
        get() = field
        private set(value) {
            field = value
        }

    var primaryButton :String = "Okay"
        set(value) {
            field = value
            hasPrimaryButton = true
        }

    var primaryBtnListener : DialogInterface.OnClickListener ? = null

}
