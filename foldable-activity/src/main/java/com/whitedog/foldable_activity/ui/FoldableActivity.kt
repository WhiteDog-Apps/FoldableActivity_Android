package com.whitedog.foldable_activity.ui

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.core.util.Consumer
import androidx.window.FoldingFeature
import androidx.window.WindowLayoutInfo
import androidx.window.WindowManager
import com.whitedog.foldable_activity.enums.FoldPosture
import com.whitedog.foldable_activity.utils.FlexModeHelper
import java.util.concurrent.Executor

abstract class FoldableActivity : AppCompatActivity() {

    private lateinit var windowManager: WindowManager

    private val handler = Handler(Looper.getMainLooper())
    private val mainThreadExecutor = Executor {r: Runnable -> handler.post(r)}
    private val stateContainer = StateContainer()

    //-----------------------------------------------------------------------------------

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        windowManager = WindowManager(this)
    }

    //-----------------------------------------------------------------------------------

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()

        windowManager.registerLayoutChangeCallback(mainThreadExecutor, stateContainer)
    }

    override fun onDetachedFromWindow() {
        windowManager.unregisterLayoutChangeCallback(stateContainer)

        super.onDetachedFromWindow()
    }

    //-----------------------------------------------------------------------------------

    abstract fun onFoldableFlat(foldFeature: FoldingFeature)
    abstract fun onFoldableHalfOpen(foldFeature: FoldingFeature, foldPosture: FoldPosture)

    //-----------------------------------------------------------------------------------

    inner class StateContainer : Consumer<WindowLayoutInfo> {
        override fun accept(newLayoutInfo: WindowLayoutInfo) {
            for(displayFeature in newLayoutInfo.displayFeatures) {
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