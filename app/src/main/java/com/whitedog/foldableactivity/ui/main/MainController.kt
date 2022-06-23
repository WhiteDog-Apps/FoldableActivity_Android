package com.whitedog.foldableactivity.ui.main

import android.content.Context
import android.content.Intent
import com.whitedog.foldableactivity.ui.guidelines.GuidelinesActivity
import com.whitedog.foldableactivity.ui.motion_layout_guides.MotionLayoutGuidesActivity

class MainController(private val context: Context, private val view: MainContract.View, private val viewModel: MainViewModel): MainContract.Controller {

    override fun onCreate() {
    }

    //-----------------------------------------------------------------------------------

    override fun onGuideLinesClick() {
        val intent: Intent = Intent(context, GuidelinesActivity::class.java)

        view.showIntent(intent)
    }

    override fun onMotionLayoutGuidesClick() {
        val intent: Intent = Intent(context, MotionLayoutGuidesActivity::class.java)

        view.showIntent(intent)
    }

    //-----------------------------------------------------------------------------------

    override fun onDestroy() {
    }

}