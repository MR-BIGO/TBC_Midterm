package com.example.tbc_midterm_project.presentation.screen.offers

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tbc_midterm_project.databinding.PackageItemBinding
import com.example.tbc_midterm_project.presentation.model.exercise_items.OfferPres

class OfferItemRecyclerAdapter :
    RecyclerView.Adapter<OfferItemRecyclerAdapter.OfferItemViewHolder>() {

    private var items: List<OfferPres> = listOf()
    var itemOnClick: ((Int) -> Unit)? = null

    inner class OfferItemViewHolder(private val binding: PackageItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

            fun bind(position: Int){
                with(binding){
                    val item = items[position]
                    tvTitle.text = item.name
                    tvNumberExercises.text = (item.nExercises.toString()).plus(" exercises")
                    Glide.with(itemView.context).load(item.image).into(ivImage)
                }
            }

        fun listeners(position: Int){
            binding.root.setOnClickListener {
                itemOnClick!!(items[position].id!!)
            }
        }
    }

    fun setData(items: List<OfferPres>){
        this.items = items
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OfferItemViewHolder {
        return OfferItemViewHolder(
            PackageItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: OfferItemViewHolder, position: Int) {
        holder.bind(position)
        holder.listeners(position)
    }
}