package com.whitedog.foldableactivity

import android.os.Bundle
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.window.layout.FoldingFeature
import com.whitedog.foldable_activity.enums.FoldPosture
import com.whitedog.foldable_activity.ui.FoldableActivity
import com.whitedog.foldable_activity.utils.FlexModeHelper
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : FoldableActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    //-----------------------------------------------------------------------------------

    override fun onFoldableFlat(foldFeature: FoldingFeature) {
        ConstraintLayout.getSharedValues().fireNewValue(R.id.tabletop_fold_guide, 0)
        ConstraintLayout.getSharedValues().fireNewValue(R.id.book_fold_guide, 0)
    }

    override fun onFoldableHalfOpen(foldFeature: FoldingFeature, foldPosture: FoldPosture) {
        val foldPosition: Int = FlexModeHelper.getFoldPosition(root, foldFeature)

        val posture: FoldPosture = FlexModeHelper.getPosture(foldFeature)

        if(posture == FoldPosture.TABLE_TOP) {
            ConstraintLayout.getSharedValues().fireNewValue(R.id.tabletop_fold_guide, foldPosition)
        }
        else if(posture == FoldPosture.BOOK) {
            ConstraintLayout.getSharedValues().fireNewValue(R.id.book_fold_guide, foldPosition)
        }
    }

}