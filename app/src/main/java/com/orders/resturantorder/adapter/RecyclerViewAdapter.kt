package com.orders.resturantorder.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.orders.resturantorder.R
import com.orders.resturantorder.model.RepositoryData
import kotlinx.android.synthetic.main.reporitory_list_row.view.*

class RecyclerViewAdapter() : RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>() {

    private var listData: List<RepositoryData>? = null
    fun setListData(listData: List<RepositoryData>?) {
        this.listData = listData
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerViewAdapter.MyViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.reporitory_list_row, parent, false)

        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerViewAdapter.MyViewHolder, position: Int) {
        holder.bind(listData?.get(position)!!)
    }

    override fun getItemCount(): Int {
        if (listData == null) return 0
        return listData?.size!!
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(data: RepositoryData) {
            itemView.tvName.text = data.name
            itemView.tvDesc.text = data.description

            Glide.with(itemView.image_avatar_url.image_avatar_url)
                .load(data.owner?.avatar_url)
                .into(itemView.image_avatar_url.image_avatar_url)
        }
    }
}