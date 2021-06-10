package com.whitedog.foldable_activity.utils

import android.graphics.Rect
import android.view.View
import androidx.window.DisplayFeature
import androidx.window.FoldingFeature
import com.whitedog.foldable_activity.enums.FoldPosture

object FlexModeHelper {

    fun getFoldPosition(view: View, foldingFeature: FoldingFeature): Int {
        var position: Int = 0

        val splitRect: Rect? = getFeatureBoundsInWindow(foldingFeature, view)
        splitRect?.let {
            val posture: FoldPosture = getPosture(foldingFeature)

            if(posture == FoldPosture.TABLE_TOP) {
                position = view.height.minus(splitRect.top)
            }
            else if(posture == FoldPosture.BOOK) {
                position = view.width.minus(splitRect.left)
            }
        }

        return position
    }

    fun getPosture(foldFeature: FoldingFeature): FoldPosture {
        return when {
            isTableTopPosture(foldFeature) -> FoldPosture.TABLE_TOP
            isBookPosture(foldFeature)     -> FoldPosture.BOOK
            else                           -> FoldPosture.UNKNOWN
        }
    }

    //-----------------------------------------------------------------------------------

    fun isFlat(foldFeature: FoldingFeature): Boolean {
        return foldFeature.state == FoldingFeature.STATE_FLAT
    }

    fun isHalfOpen(foldFeature: FoldingFeature): Boolean {
        return foldFeature.state == FoldingFeature.STATE_HALF_OPENED
    }

    //-----------------------------------------------------------------------------------

    private fun isTableTopPosture(foldFeature: FoldingFeature): Boolean {
        return foldFeature.isSeparating && foldFeature.orientation == FoldingFeature.ORIENTATION_HORIZONTAL
    }

    private fun isBookPosture(foldFeature: FoldingFeature): Boolean {
        return foldFeature.isSeparating && foldFeature.orientation == FoldingFeature.ORIENTATION_VERTICAL
    }

    //-----------------------------------------------------------------------------------

    private fun getFeatureBoundsInWindow(displayFeature: DisplayFeature, view: View, includePadding: Boolean = true): Rect? {
        val viewLocationInWindow: IntArray = IntArray(2)
        view.getLocationInWindow(viewLocationInWindow)

        val viewRect: Rect = Rect(viewLocationInWindow[0], viewLocationInWindow[1], viewLocationInWindow[0] + view.width, viewLocationInWindow[1] + view.height)

        if(includePadding) {
            viewRect.left += view.paddingLeft
            viewRect.top += view.paddingTop
            viewRect.right -= view.paddingRight
            viewRect.bottom -= view.paddingBottom
        }

        val featureRectInView: Rect = Rect(displayFeature.bounds)
        val intersects: Boolean = featureRectInView.intersect(viewRect)

        return if((featureRectInView.width() == 0 && featureRectInView.height() == 0) || !intersects) {
            null
        }
        else {
            featureRectInView.offset(-viewLocationInWindow[0], -viewLocationInWindow[1])
            featureRectInView
        }
    }
}