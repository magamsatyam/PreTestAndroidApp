package com.satya.pretest.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView

import com.satya.pretest.model.Result
import com.satya.pretest.R
import com.satya.pretest.databinding.ListItemAnimeBinding

class AnimAdapter(private val data: ArrayList<Result>) : RecyclerView.Adapter<AnimViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimViewHolder {
        val listItemBinding: ListItemAnimeBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context),
                R.layout.list_item_anime,
                parent,
                false)
        return AnimViewHolder(listItemBinding)
    }



    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: AnimViewHolder, position: Int) {
        holder.listItemBinding.result = data[position]
    }

    fun setUpData(result:List<Result>){
        data.clear()
        data.addAll(result)
        notifyDataSetChanged()
    }

}

class AnimViewHolder(
        val listItemBinding: ListItemAnimeBinding
) : RecyclerView.ViewHolder(listItemBinding.root)