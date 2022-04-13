package com.meetSuccess.FoodResturant.Adapter

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.orders.resturantorder.R

import com.meetSuccess.FoodResturant.Model.Categories
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_recycler_category.view.*


class CategoryAdapter(private var categories1: List<Categories.Category>, val context: Context) :
    RecyclerView.Adapter<CategoryAdapter.PostViewHolder>() {
    var tempPosition = 0


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_recycler_category, parent, false)
        return PostViewHolder(view)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.onBind(categories1.get(position), position)
        holder.onItemClick(position)


    }


    inner class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun onBind(subcategory: Categories.Category, position: Int) {
            if (tempPosition == position) {
                itemView.llParent.setBackgroundResource(R.drawable.rounded_corner_save_button)
            } else {
                itemView.llParent.setBackgroundResource(R.drawable.rounded_corner_save_buttonwhite)
            }

            itemView.categoryName.text = subcategory.name

            Picasso.get().load(subcategory.image)
                .placeholder(R.drawable.clock_my_time_in_button)
                .into(itemView.categoryThumb)

            itemView.setOnClickListener {

                val bundle = Bundle()
                bundle.putString("categoryId", subcategory.idCategory.toString())
                Navigation.findNavController(it).navigate(
                    R.id.action_dashBoardCategories_to_afterSelectionCategoryFragment,
                    bundle
                );

//            val intent = Intent(context, AfterCategorySelectionActivity::class.java);
//            intent.putExtra("categoryId",categories1.get(position).getIdCategory().toString())
//
//
//            context.startActivity(intent);

            }
        }

        fun onItemClick(position: Int) {
            itemView.setOnClickListener {
                tempPosition = position
                notifyDataSetChanged()

            }

        }

    }

    override fun getItemCount(): Int {
        return categories1.size

    }


}