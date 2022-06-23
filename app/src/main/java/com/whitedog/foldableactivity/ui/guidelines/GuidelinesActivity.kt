package com.whitedog.foldableactivity.ui.guidelines

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintSet
import androidx.databinding.DataBindingUtil
import androidx.transition.TransitionManager
import com.whitedog.foldable_activity.enums.FoldPosture
import com.whitedog.foldable_activity.ui.FoldableActivity
import com.whitedog.foldableactivity.R
import com.whitedog.foldableactivity.databinding.ActivityGuidelinesBinding

class GuidelinesActivity : FoldableActivity(), GuidelinesContract.View {

    private lateinit var binding: ActivityGuidelinesBinding
    private lateinit var controller: GuidelinesContract.Controller

    private val viewModel: GuidelinesViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        controller = GuidelinesController(applicationContext, this, viewModel)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_guidelines)
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
        return binding.clGuidelinesRoot
    }

    //-----------------------------------------------------------------------------------

    override fun updateUI(foldPosture: FoldPosture, position: Int) {
        val constraintSet: ConstraintSet = ConstraintSet()
        constraintSet.clone(binding.clGuidelinesContent)

        when(foldPosture) {
            FoldPosture.TABLE_TOP -> {
                constraintSet.setGuidelineEnd(R.id.gl_guidelines_tabletop, position)
                constraintSet.setGuidelineEnd(R.id.gl_guidelines_book, 0)
            }
            FoldPosture.BOOK      -> {
                constraintSet.setGuidelineEnd(R.id.gl_guidelines_tabletop, 0)
                constraintSet.setGuidelineEnd(R.id.gl_guidelines_book, position)
            }
            else                  -> {
                constraintSet.setGuidelineEnd(R.id.gl_guidelines_tabletop, 0)
                constraintSet.setGuidelineEnd(R.id.gl_guidelines_book, 0)
            }
        }

        TransitionManager.beginDelayedTransition(binding.clGuidelinesContent)
        constraintSet.applyTo(binding.clGuidelinesContent)
    }

    //-----------------------------------------------------------------------------------

    override fun onFoldablePostureChanged(foldPosture: FoldPosture, foldPositionFromEnd: Int) {
        controller.onFoldablePostureChanged(foldPosture, foldPositionFromEnd)
    }

    //-----------------------------------------------------------------------------------

    override fun onDestroy() {
        controller.onDestroy()

        super.onDestroy()
    }

}