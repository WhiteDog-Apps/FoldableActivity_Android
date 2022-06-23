package com.whitedog.foldableactivity.ui.motion_layout_guides

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import com.whitedog.foldable_activity.enums.FoldPosture
import com.whitedog.foldable_activity.ui.FoldableActivity
import com.whitedog.foldableactivity.R
import com.whitedog.foldableactivity.databinding.ActivityMotionLayoutGuidesBinding

class MotionLayoutGuidesActivity : FoldableActivity(), MotionLayoutGuidesContract.View {

    private lateinit var binding: ActivityMotionLayoutGuidesBinding
    private lateinit var controller: MotionLayoutGuidesContract.Controller

    private val viewModel: MotionLayoutGuidesViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        controller = MotionLayoutGuidesController(applicationContext, this, viewModel)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_motion_layout_guides)
        binding.controller = controller
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        controller.onCreate()
    }

    //-----------------------------------------------------------------------------------

    override fun getActivity(): AppCompatActivity {
        return this
    }

    //-----------------------------------------------------------------------------------

    override fun getRootView(): View {
        return binding.clMotionLayoutGuidesRoot
    }

    //-----------------------------------------------------------------------------------

    override fun updateUI(foldPosture: FoldPosture, position: Int) {
        when(foldPosture) {
            FoldPosture.TABLE_TOP -> {
                ConstraintLayout.getSharedValues().fireNewValue(R.id.rg_motion_layout_guides_tabletop, position)
                ConstraintLayout.getSharedValues().fireNewValue(R.id.rg_motion_layout_guides_book, 0)
            }
            FoldPosture.BOOK      -> {
                ConstraintLayout.getSharedValues().fireNewValue(R.id.rg_motion_layout_guides_tabletop, 0)
                ConstraintLayout.getSharedValues().fireNewValue(R.id.rg_motion_layout_guides_book, position)
            }
            else                  -> {
                ConstraintLayout.getSharedValues().fireNewValue(R.id.rg_motion_layout_guides_tabletop, 0)
                ConstraintLayout.getSharedValues().fireNewValue(R.id.rg_motion_layout_guides_book, 0)
            }
        }
    }

    //-----------------------------------------------------------------------------------

    override fun onFoldablePostureChanged(foldPosture: FoldPosture, foldPositionFromEnd: Int) {
        controller.onFoldablePostureChanged(foldPosture, foldPositionFromEnd)
    }

    //-----------------------------------------------------------------------------------

    override fun finishActivity() {
        finish()
    }

    //-----------------------------------------------------------------------------------

    override fun onDestroy() {
        controller.onDestroy()

        super.onDestroy()
    }

}