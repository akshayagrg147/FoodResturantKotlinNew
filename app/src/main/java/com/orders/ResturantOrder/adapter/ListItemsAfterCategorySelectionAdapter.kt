package com.orders.ResturantOrder.adapter


import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

import com.google.android.material.bottomsheet.BottomSheetDialog
import com.meetSuccess.FoodResturant.Adapter.CategoryAdapter
import com.meetSuccess.FoodResturant.Model.Categories
import com.orders.ResturantOrder.R


class ListItemsAfterCategorySelectionAdapter(
    private var cntx: Context, private var categories1: List<Categories.Category>,
    onitemClicked1: ListItemsAfterCategorySelectionAdapter.onclick
)
    : RecyclerView.Adapter<ListItemsAfterCategorySelectionAdapter.PostViewHolder>() {


    private  var onitemClicked:ListItemsAfterCategorySelectionAdapter.onclick=onitemClicked1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.itemproduct_category, parent, false)
        return PostViewHolder(view)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.itemView.findViewById<TextView>(R.id.address).setText(categories1.get(position).getStrCategory())


        holder.itemView.findViewById<Button>(R.id.AddButton).setOnClickListener{

            holder.itemView.findViewById<Button>(R.id.AddButton).visibility=View.GONE
            holder.itemView.findViewById<Button>(R.id.ItemAdded).visibility=View.VISIBLE
            Log.d("callingTest", "ddddddd--button" + position)
            onitemClicked.itemclicked(categories1.get(position))

   };


//        binding.categoryName.text=categories1.get(position).getStrCategory()
//
//        Picasso.get().load(categories1.get(position).getStrCategoryThumb()).placeholder(R.drawable.ic_circle)
//            .into(binding.categoryThumb)

    }

//    override fun getItemCount(): Int = categories1.size

    class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){


    }

    fun setData(categoriesList: List<Categories.Category>)
    {
        categories1=categoriesList
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {

        return categories1.size

    }
    interface onclick{
        public fun itemclicked(item: Categories.Category)
    }

}