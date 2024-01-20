package com.example.tbc_midterm_project.presentation.screen.calculator

import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
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
    private lateinit var answersRecyclerAdapter: AnswerItemsRecyclerAdapter
    override fun setUp() {
        listeners()
        bindObservers()
        setUpBtnAnim()
        setUpAnswersRecycler()
    }

    private fun listeners() {
        with(binding) {
            if (checkFields()) {
                btnCalculate.setOnClickListener {
                    viewModel.onEvent(
                        CalculatorEvents.CalculatePressed(
                            etSex.text.toString(),
                            etAge.text.toString().toInt(),
                            etHeight.text.toString().toDouble(),
                            etWeight.text.toString().toDouble()
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

    private fun setUpAnswersRecycler(){
        answersRecyclerAdapter = AnswerItemsRecyclerAdapter()

        binding.rvAnswerItem.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = answersRecyclerAdapter
        }
    }

    private fun checkFields(): Boolean {
        return true
    }

    private fun handleState(state: CalculatorState) {
        binding.progressBar.visibility = if (state.isLoading) View.VISIBLE else View.GONE

        state.data?.let {
            answersRecyclerAdapter.setItems(it)
            answersRecyclerAdapter.notifyItemRangeChanged(0, 4)
        }
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