package com.example.mmapp.Savings

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mmapp.R

class SavingsAdapter(private val onScroll: (SavingsEntity)-> Unit):ListAdapter<SavingsEntity, SavingsAdapter.SavingsViewHolder>(DiffCallback2())
{
    class DiffCallback2:DiffUtil.ItemCallback<SavingsEntity>()
    {
        override fun areItemsTheSame(oldItem: SavingsEntity, newItem: SavingsEntity): Boolean {
            return oldItem.id==newItem.id
        }

        override fun areContentsTheSame(oldItem: SavingsEntity, newItem: SavingsEntity): Boolean {
            return oldItem==newItem
        }
    }

    inner class SavingsViewHolder(view: View): RecyclerView.ViewHolder(view)
    {
        val savingsItemTv1=view.findViewById<TextView>(R.id.savingsItemTv1)
        val savingsItemTv2=view.findViewById<TextView>(R.id.savingsItemTv2)

        fun bind(item: SavingsEntity)
        {

            savingsItemTv1.text=item.deed
            savingsItemTv2.text="+"+item.amount.toString()
            onScroll(item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SavingsViewHolder
    {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.layout_itemsavings,parent,false)
        return SavingsViewHolder(view)
    }

    override fun onBindViewHolder(holder: SavingsViewHolder, position: Int)
    {
        val item=getItem(position)
        holder.bind(item)
    }
}