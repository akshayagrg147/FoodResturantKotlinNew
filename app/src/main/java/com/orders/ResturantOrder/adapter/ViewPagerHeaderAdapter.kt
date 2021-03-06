package com.meetSuccess.FoodResturant.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.orders.ResturantOrder.R

import com.meetSuccess.FoodResturant.Model.Categories
import com.orders.ResturantOrder.Activity.AfterCategorySelectionActivity
import com.squareup.picasso.Picasso


class ViewPagerHeaderAdapter(private var categories1: List<Categories.Category>, val context: Context)
    : RecyclerView.Adapter<ViewPagerHeaderAdapter.PostViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_recycler_category, parent, false)
        return PostViewHolder(view)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
       // binding.mealName.text=categories1.get(position).getStrCategory()

        holder.categoryName.text=categories1.get(position).getStrCategory()

        Picasso.get().load(categories1.get(position).getStrCategoryThumb()).placeholder(R.drawable.clock_my_time_in_button)
            .into(holder.imageView)
        holder.itemView.setOnClickListener{



            val intent = Intent(context, AfterCategorySelectionActivity::class.java);

            context.startActivity(intent);

        }

    }

//    override fun getItemCount(): Int = categories1.size

    class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val imageView: ImageView = itemView.findViewById(R.id.categoryThumb)
        val categoryName: TextView = itemView.findViewById(R.id.categoryName)
    }

    fun setData(categoriesList: List<Categories.Category>)
    {
        categories1=categoriesList
      //  notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return categories1.size

    }

}