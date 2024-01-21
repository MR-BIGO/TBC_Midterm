package com.example.tbc_midterm_project.presentation.screen.offers

import androidx.fragment.app.viewModels
import com.example.tbc_midterm_project.databinding.FragmentOffersBinding
import com.example.tbc_midterm_project.presentation.screen.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OffersFragment : BaseFragment<FragmentOffersBinding>(FragmentOffersBinding::inflate) {

    private val viewModel: OffersFragmentViewModel by viewModels()
    override fun setUp() {
    }

}