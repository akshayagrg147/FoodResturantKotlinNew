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
import com.meetSuccess.FoodResturant.Model.CategoriesHeader
import com.meetSuccess.FoodResturant.Model.SerchingResponse
import com.orders.ResturantOrder.Activity.AfterCategorySelectionActivity
import com.squareup.picasso.Picasso


class SearchAdapter(private var categories1: List<SerchingResponse.SearchResponseModal>, val context: Context)
    : RecyclerView.Adapter<SearchAdapter.PostViewHolder>() {

  //  private lateinit var binding:ItemRecyclerCategoryBinding


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_search_category, parent, false)
        return PostViewHolder(view)


    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.categoryName.text=categories1.get(position).getname()



//categories1.get(position).getStrimage()
//        Picasso.get().load(categories1.get(position).getImageUrl()).placeholder(R.drawable.clock_my_time_in_button)
//            .into(holder.imageView)
//        holder.itemView.setOnClickListener{
//Toast.makeText(context,categories1.get(position).getIdCategory().toString(),Toast.LENGTH_SHORT).show()
//
//
//            val intent = Intent(context, AfterCategorySelectionActivity::class.java);
//            intent.putExtra("categoryId",categories1.get(position).getIdCategory().toString())
//
//
//            context.startActivity(intent);
//
//        }

    }

//    override fun getItemCount(): Int = categories1.size

    class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
      //  val imageView: ImageView = itemView.findViewById(R.id.categoryThumb)
        val categoryName: TextView = itemView.findViewById(R.id.categoryName)

    }

    fun setData(categoriesList: SerchingResponse)
    {
        categories1=categoriesList.searchResponse
      //  notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return categories1.size

    }

}