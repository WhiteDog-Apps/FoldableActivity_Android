package com.whitedog.foldable_activity.utils

import android.graphics.Rect
import android.view.View
import androidx.window.layout.DisplayFeature
import androidx.window.layout.FoldingFeature
import com.whitedog.foldable_activity.enums.FoldPosture

object FlexModeHelper {

    fun getPosture(foldFeature: FoldingFeature): FoldPosture {
        return when {
            isFlat(foldFeature)            -> FoldPosture.FLAT
            isTableTopPosture(foldFeature) -> FoldPosture.TABLE_TOP
            isBookPosture(foldFeature)     -> FoldPosture.BOOK
            else                           -> FoldPosture.FLAT
        }
    }

    //-----------------------------------------------------------------------------------

    private fun isFlat(foldFeature: FoldingFeature): Boolean {
        return foldFeature.state == FoldingFeature.State.FLAT
    }

    private fun isTableTopPosture(foldFeature: FoldingFeature): Boolean {
        return isHalfOpen(foldFeature) && foldFeature.orientation == FoldingFeature.Orientation.HORIZONTAL
    }

    private fun isBookPosture(foldFeature: FoldingFeature): Boolean {
        return isHalfOpen(foldFeature) && foldFeature.orientation == FoldingFeature.Orientation.VERTICAL
    }

    //-----------------------------------------------------------------------------------

    private fun isHalfOpen(foldFeature: FoldingFeature): Boolean {
        return foldFeature.state == FoldingFeature.State.HALF_OPENED
    }

    //-----------------------------------------------------------------------------------

    fun getFoldPosition(view: View, foldFeature: FoldingFeature): Int {
        var position: Int = 0

        getFeatureBoundsInWindow(foldFeature, view)?.let { featureBounds ->
            val posture: FoldPosture = getPosture(foldFeature)
            val viewRect: Rect = getViewRectOnScreen(view)

            if(posture == FoldPosture.TABLE_TOP) {
                position = viewRect.height().minus(featureBounds.top)
            }
            else if(posture == FoldPosture.BOOK) {
                position = viewRect.width().minus(featureBounds.left)
            }
        }

        return position
    }

    private fun getFeatureBoundsInWindow(displayFeature: DisplayFeature, view: View, includePadding: Boolean = true): Rect? {
        val viewRect: Rect = getViewRectOnScreen(view)

        if(includePadding) {
            viewRect.left   += view.paddingLeft
            viewRect.top    += view.paddingTop
            viewRect.right  -= view.paddingRight
            viewRect.bottom -= view.paddingBottom
        }

        val featureRectInView: Rect = Rect(displayFeature.bounds)
        val intersects: Boolean = featureRectInView.intersect(viewRect)

        return if((featureRectInView.width() == 0 && featureRectInView.height() == 0) || !intersects) {
            null
        }
        else {
            featureRectInView.offset(-viewRect.left, -viewRect.top)
            featureRectInView
        }
    }

    private fun getViewRectOnScreen(view: View): Rect {
        val viewLocationOnScreen: IntArray = IntArray(2)
        view.getLocationOnScreen(viewLocationOnScreen)

        return Rect(viewLocationOnScreen[0], viewLocationOnScreen[1], viewLocationOnScreen[0] + view.width, viewLocationOnScreen[1] + view.height)
    }
}