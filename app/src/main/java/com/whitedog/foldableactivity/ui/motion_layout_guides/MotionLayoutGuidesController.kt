package com.whitedog.foldableactivity.ui.motion_layout_guides

import android.content.Context
import com.whitedog.foldable_activity.enums.FoldPosture

class MotionLayoutGuidesController(private val context: Context, private val view: MotionLayoutGuidesContract.View, private val viewModel: MotionLayoutGuidesViewModel): MotionLayoutGuidesContract.Controller {

    override fun onCreate() {
        viewModel.foldPosture.observe(view.getActivity()) { posture ->
            if(viewModel.updateUI) {
                viewModel.updateUI = false
                view.updateUI(posture, viewModel.foldPosition)
            }
        }
    }

    //-----------------------------------------------------------------------------------

    override fun onBackClick() {
        view.finishActivity()
    }

    //-----------------------------------------------------------------------------------

    override fun onFoldablePostureChanged(foldPosture: FoldPosture, foldPosition: Int) {
        viewModel.updateUI = true
        viewModel.foldPosition = foldPosition
        viewModel.foldPosture.postValue(foldPosture)
    }

    //-----------------------------------------------------------------------------------

    override fun onDestroy() {

    }

}