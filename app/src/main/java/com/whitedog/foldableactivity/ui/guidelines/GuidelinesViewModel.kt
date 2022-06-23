package com.whitedog.foldableactivity.ui.guidelines

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.whitedog.foldable_activity.enums.FoldPosture

class GuidelinesViewModel: ViewModel() {

    var foldPosture: MutableLiveData<FoldPosture> = MutableLiveData(FoldPosture.UNKNOWN)

    var foldPosition: Int = 0

}