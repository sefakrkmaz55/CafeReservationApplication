package com.bitirme.rezervasyonuygulamasi.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bitirme.rezervasyonuygulamasi.R
import com.bitirme.rezervasyonuygulamasi.databinding.ItemLayoutBinding
import com.bitirme.rezervasyonuygulamasi.model.Item


class MyAdapter(
    private var itemList: List<Item>,
    private var itemClickListener: ItemClickListener
) :
    RecyclerView.Adapter<MyAdapter.ViewHolder>() {

    inner class ViewHolder(val view: ItemLayoutBinding) : RecyclerView.ViewHolder(view.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_layout,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = itemList[position]
        holder.view.cafe = item
        holder.view.root.setOnClickListener {
            itemClickListener.onItemClick()
        }
    }

    override fun getItemCount() = itemList.size

    interface ItemClickListener {
        fun onItemClick()
    }
}