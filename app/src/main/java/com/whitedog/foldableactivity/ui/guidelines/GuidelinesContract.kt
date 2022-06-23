package com.whitedog.foldableactivity.ui.guidelines

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.whitedog.foldable_activity.enums.FoldPosture

interface GuidelinesContract {

    interface View {
        fun onCreate(savedInstanceState: Bundle?)

        fun getActivity(): AppCompatActivity

        fun updateUI(foldPosture: FoldPosture, position: Int)

        fun onDestroy()
    }

    interface Controller {
        fun onCreate()

        fun onFoldablePostureChanged(foldPosture: FoldPosture, foldPosition: Int)

        fun onDestroy()
    }

}