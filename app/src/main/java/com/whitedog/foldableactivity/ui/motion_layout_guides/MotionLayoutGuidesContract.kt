package com.whitedog.foldableactivity.ui.motion_layout_guides

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.whitedog.foldable_activity.enums.FoldPosture

interface MotionLayoutGuidesContract {

    interface View {
        fun onCreate(savedInstanceState: Bundle?)

        fun getActivity(): AppCompatActivity

        fun updateUI(foldPosture: FoldPosture, position: Int)

        fun finishActivity()
        fun onDestroy()
    }

    interface Controller {
        fun onCreate()

        fun onBackClick()

        fun onFoldablePostureChanged(foldPosture: FoldPosture, foldPosition: Int)

        fun onDestroy()
    }
}