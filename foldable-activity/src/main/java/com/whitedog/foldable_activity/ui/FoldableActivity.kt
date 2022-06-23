package com.whitedog.foldable_activity.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.window.layout.DisplayFeature
import androidx.window.layout.FoldingFeature
import androidx.window.layout.WindowInfoTracker
import com.whitedog.foldable_activity.enums.FoldPosture
import com.whitedog.foldable_activity.utils.FlexModeHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

abstract class FoldableActivity : AppCompatActivity() {

    private lateinit var windowInfoRepo: WindowInfoTracker

    //-----------------------------------------------------------------------------------

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        windowInfoRepo = WindowInfoTracker.getOrCreate(this)

        lifecycleScope.launch(Dispatchers.Main) {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                windowInfoRepo.windowLayoutInfo(this@FoldableActivity).collect { newLayoutInfo ->
                    for(displayFeature: DisplayFeature in newLayoutInfo.displayFeatures) {
                        val foldFeature: FoldingFeature? = displayFeature as? FoldingFeature

                        if(foldFeature == null) {
                            onFoldablePostureChanged(FoldPosture.UNKNOWN, 0)
                        }
                        else {
                            val foldPosture: FoldPosture = FlexModeHelper.getPosture(foldFeature)
                            val foldPosition: Int = FlexModeHelper.getFoldPosition(getRootView(), foldFeature)

                            onFoldablePostureChanged(foldPosture, foldPosition)
                        }
                    }
                }
            }
        }
    }

    //-----------------------------------------------------------------------------------

    abstract fun getRootView(): View

    abstract fun onFoldablePostureChanged(foldPosture: FoldPosture, foldPositionFromEnd: Int)

}