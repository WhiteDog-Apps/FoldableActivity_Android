package com.whitedog.foldableactivity.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.whitedog.foldableactivity.R
import com.whitedog.foldableactivity.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), MainContract.View {

    private lateinit var binding: ActivityMainBinding
    private lateinit var controller: MainContract.Controller

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        controller = MainController(applicationContext, this, viewModel)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.controller = controller
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        controller.onCreate()
    }

    //-----------------------------------------------------------------------------------

    override fun showIntent(intent: Intent) {
        startActivity(intent)
    }

    //-----------------------------------------------------------------------------------

    override fun onDestroy() {
        controller.onDestroy()

        super.onDestroy()
    }

}