package com.whitedog.foldableactivity.ui.guidelines

import android.content.Context
import com.whitedog.foldable_activity.enums.FoldPosture

class GuidelinesController(private val context: Context, private val view: GuidelinesContract.View, private val viewModel: GuidelinesViewModel): GuidelinesContract.Controller {

    override fun onCreate() {
        viewModel.foldPosture.observe(view.getActivity()) { posture ->
            view.updateUI(posture, viewModel.foldPosition)
        }
    }

    //-----------------------------------------------------------------------------------

    override fun onFoldablePostureChanged(foldPosture: FoldPosture, foldPosition: Int) {
        viewModel.foldPosition = foldPosition
        viewModel.foldPosture.postValue(foldPosture)
    }

    //-----------------------------------------------------------------------------------

    override fun onDestroy() {

    }

}