package com.example.tbc_midterm_project.presentation.screen.calculator

import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.tbc_midterm_project.databinding.FragmentCalculatorBinding
import com.example.tbc_midterm_project.presentation.event.calculator.CalculatorEvents
import com.example.tbc_midterm_project.presentation.screen.base.BaseFragment
import com.example.tbc_midterm_project.presentation.state.calculator.CalculatorState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CalculatorFragment :
    BaseFragment<FragmentCalculatorBinding>(FragmentCalculatorBinding::inflate) {

    private val viewModel: CalculatorFragmentViewModel by viewModels()
    override fun setUp() {
        listeners()
        bindObservers()
        setUpBtnAnim()
    }

    private fun listeners() {
        with(binding) {
            if (checkFields()) {
                btnCalculate.setOnClickListener {
                    viewModel.onEvent(
                        CalculatorEvents.CalculatePressed(
                            etSex.text.toString(),
                            etAge.text.toString().toInt(),
                            etHeight.text.toString().toInt(),
                            etWeight.text.toString().toInt()
                        )
                    )
                }
            }
            btnBack.setOnClickListener {
                findNavController().navigateUp()
            }
        }

    }

    private fun setUpBtnAnim() {
        val anim = AlphaAnimation(0.8f, 1.0f)
        anim.apply {
            duration = 1200
            repeatMode = Animation.REVERSE
            repeatCount = Animation.INFINITE
        }
        binding.btnCalculate.startAnimation(anim)
    }

    private fun checkFields(): Boolean {
        return true
    }

    private fun handleState(state: CalculatorState) {

    }

    private fun bindObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.calculatorState.collect {
                    handleState(it)
                }
            }
        }
    }
}