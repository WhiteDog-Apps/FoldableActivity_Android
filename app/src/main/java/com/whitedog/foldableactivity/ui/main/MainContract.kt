package com.whitedog.foldableactivity.ui.main

import android.content.Intent
import android.os.Bundle

interface MainContract {

    interface View {
        fun onCreate(savedInstanceState: Bundle?)

        fun showIntent(intent: Intent)

        fun onDestroy()
    }

    interface Controller {
        fun onCreate()

        fun onGuideLinesClick()
        fun onMotionLayoutGuidesClick()

        fun onDestroy()
    }

}