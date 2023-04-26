package com.example.gym_system.interfaces

import android.content.Context
import android.graphics.Color
import android.os.Handler
import android.os.Looper

import cn.pedant.SweetAlert.SweetAlertDialog

class SweetAlert {
    companion object {
        fun showProgressDialog(context: Context) {
            val pDialog = SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE)
            pDialog.progressHelper.barColor = Color.parseColor("#A5DC86")
            pDialog.titleText = "Cargando..."
            pDialog.setCancelable(false)
            pDialog.progressHelper
            pDialog.show()
            Handler(Looper.getMainLooper()).postDelayed({ pDialog.dismiss() }, 2500)
        }

        fun showErrorDialog(context: Context, message: String) {
            SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE)
                .setTitleText("Oops...")
                .setContentText(message)
                .show()
        }

        fun showSuccessDialog(context: Context, message: String) {
            SweetAlertDialog(context, SweetAlertDialog.SUCCESS_TYPE)
                .setTitleText("Genial")
                .setContentText(message)
                .show()
        }


    }

}