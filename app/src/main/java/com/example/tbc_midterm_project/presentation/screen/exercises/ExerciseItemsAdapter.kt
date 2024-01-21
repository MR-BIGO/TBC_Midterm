package com.example.tbc_midterm_project.presentation.screen.exercises

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tbc_midterm_project.databinding.ExerciseItemBinding

import com.example.tbc_midterm_project.presentation.model.exercise_items.ExercisePres

class ExerciseItemsAdapter : RecyclerView.Adapter<ExerciseItemsAdapter.ExerciseItemsViewHolder>() {

    private var items: List<ExercisePres> = listOf()

    inner class ExerciseItemsViewHolder(private val binding: ExerciseItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(position: Int) {
            val item = items[position]
            with(binding) {
                tvTitle.text = item.name
                tvSets.text = "Number of sets: ".plus(item.sets)
                tvReps.text = "Number of repetitions: ".plus(item.reps)
                tvRest.text = "Rest in between sets: ".plus(item.rest).plus(" minutes")
                tvDesc.text = item.desc
                Glide.with(itemView.context).load(item.image).into(ivImage)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseItemsViewHolder {
        return ExerciseItemsViewHolder(
            ExerciseItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    fun setData(items: List<ExercisePres>){
        this.items = items
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ExerciseItemsViewHolder, position: Int) {
        holder.bind(position)
    }
}