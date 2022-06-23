package com.whitedog.foldableactivity.ui.motion_layout_guides

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.whitedog.foldable_activity.enums.FoldPosture

class MotionLayoutGuidesViewModel: ViewModel() {

    var foldPosture: MutableLiveData<FoldPosture> = MutableLiveData(FoldPosture.UNKNOWN)

    var foldPosition: Int = 0

    var updateUI: Boolean = false

}