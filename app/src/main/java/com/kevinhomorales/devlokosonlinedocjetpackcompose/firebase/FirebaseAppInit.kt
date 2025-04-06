package com.kevinhomorales.devlokosonlinedocjetpackcompose.firebase

import android.app.Application
import com.google.firebase.FirebaseApp

class FirebaseAppInit : Application() {
    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
    }
}