package com.whitedog.foldable_activity.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.window.layout.DisplayFeature
import androidx.window.layout.FoldingFeature
import androidx.window.layout.WindowInfoRepository
import androidx.window.layout.WindowInfoRepository.Companion.windowInfoRepository
import com.whitedog.foldable_activity.enums.FoldPosture
import com.whitedog.foldable_activity.utils.FlexModeHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

abstract class FoldableActivity : AppCompatActivity() {

    private lateinit var windowInfoRepo: WindowInfoRepository

    //-----------------------------------------------------------------------------------

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        windowInfoRepo = windowInfoRepository()

        lifecycleScope.launch(Dispatchers.Main) {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                windowInfoRepo.windowLayoutInfo.collect { newLayoutInfo ->
                    for (displayFeature : DisplayFeature in newLayoutInfo.displayFeatures) {
                        val foldFeature: FoldingFeature? = displayFeature as? FoldingFeature

                        if(foldFeature != null) {
                            if(FlexModeHelper.isHalfOpen(foldFeature)) {
                                onFoldableHalfOpen(foldFeature, FlexModeHelper.getPosture(foldFeature))
                            }
                            else if(FlexModeHelper.isFlat(foldFeature)){
                                onFoldableFlat(foldFeature)
                            }
                        }
                    }
                }
            }
        }
    }

    //-----------------------------------------------------------------------------------

    abstract fun onFoldableFlat(foldFeature: FoldingFeature)
    abstract fun onFoldableHalfOpen(foldFeature: FoldingFeature, foldPosture: FoldPosture)

}