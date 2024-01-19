package com.example.tbc_midterm_project.presentation.screen.calculator

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tbc_midterm_project.databinding.AnswerItemBinding
import com.example.tbc_midterm_project.presentation.model.calculator.AnswerItem

class AnswerItemsRecyclerAdapter :
    RecyclerView.Adapter<AnswerItemsRecyclerAdapter.AnswerItemsViewHolder>() {

    private var _results = listOf<AnswerItem>()

    inner class AnswerItemsViewHolder(private val binding: AnswerItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(position: Int) {
            val item = _results[position]
            with(binding) {
                tvSection.text = item.section
                tvResult.text = item.result.toString()
                ivIcon.setBackgroundResource(item.icon)
                tvBmiResult.text = item.bmiResult
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnswerItemsViewHolder {
        return AnswerItemsViewHolder(
            AnswerItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    fun setItems(items: List<AnswerItem>) {
        _results = items
    }

    override fun getItemCount() = _results.size

    override fun onBindViewHolder(holder: AnswerItemsViewHolder, position: Int) {
        holder.bind(position)
    }
}