package com.meetSuccess.FoodResturant.Adapter

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.orders.resturantorder.R

import com.meetSuccess.FoodResturant.Model.CategoriesHeader
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_recycler_category.view.*


class CategoryHeaderAdapter(private var categories1: List<CategoriesHeader.Category>, val context: Context)
    : RecyclerView.Adapter<CategoryHeaderAdapter.PostViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_recycler_category, parent, false)
        return PostViewHolder(view)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.OnBind(categories1[position])
    }

//    override fun getItemCount(): Int = categories1.size

    class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        fun OnBind(categories1: CategoriesHeader.Category) {
            itemView.categoryName.text=categories1.getname()
            Picasso.get().load(categories1.getStrimage()).placeholder(R.drawable.clock_my_time_in_button)
                .into(itemView.categoryThumb)
            itemView.setOnClickListener{
                val bundle = Bundle()
                bundle.putString("categoryId", categories1.getIdCategory())
                Navigation.findNavController(it).navigate(R.id.action_dashBoardCategories_to_afterSelectionCategoryFragment,bundle);

//            val intent = Intent(context, AfterCategorySelectionActivity::class.java);
//            intent.putExtra("categoryId",categories1.get(position).getIdCategory())
//
//            context.startActivity(intent);

            }



        }


    }



    override fun getItemCount(): Int {
        return categories1.size

    }

}