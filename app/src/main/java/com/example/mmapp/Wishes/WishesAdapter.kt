package com.example.mmapp.Wishes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mmapp.R

class WishesAdapter(private val onItemClicked: (WishesEntity) -> Unit,private val onItemClicked1: (WishesEntity) -> Unit): ListAdapter<WishesEntity, WishesAdapter.WishesViewHolder>(DiffCallback()) {
    class DiffCallback: DiffUtil.ItemCallback<WishesEntity>()
    {
        override fun areItemsTheSame(oldItem: WishesEntity, newItem: WishesEntity): Boolean {
            return oldItem.id==newItem.id
        }

        override fun areContentsTheSame(oldItem: WishesEntity, newItem: WishesEntity): Boolean {
            return oldItem==newItem

        }
    }

    inner class WishesViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val wishesItemTv1=view.findViewById<TextView>(R.id.wishesItemTv1)
        val wishesItemTv2=view.findViewById<TextView>(R.id.wishesItemTv2)
        val checkBox=view.findViewById<CheckBox>(R.id.checkBox)
        val plusButton=view.findViewById<TextView>(R.id.plusButton)

        fun bind(item: WishesEntity)
        {
            wishesItemTv1.text=item.wish
            wishesItemTv2.text="+"+item.amount.toString()
            checkBox.isChecked=item.checked

            plusButton.setOnClickListener {
                onItemClicked1(item)
            }

            checkBox.setOnClickListener {
                onItemClicked(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WishesViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.layout_itemwishes,parent,false)
        return WishesViewHolder(view)
    }

    override fun onBindViewHolder(holder: WishesViewHolder, position: Int) {
       val item=getItem(position)
        holder.bind(item)
    }

}