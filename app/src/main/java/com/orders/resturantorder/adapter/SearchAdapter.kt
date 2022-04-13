package com.meetSuccess.FoodResturant.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.orders.resturantorder.R

import com.meetSuccess.FoodResturant.Model.SerchingResponse
import com.orders.resturantorder.Activity.AfterCategorySelectionActivity
import com.orders.resturantorder.viewmodel.DashBoardCategoriesViewModal
import kotlinx.android.synthetic.main.item_search_category.view.*


class SearchAdapter(
    private var categories1: List<SerchingResponse.SearchResponseModal>,
    var context: Context,
    onitemClicked1: SearchAdapter.onclick
) : RecyclerView.Adapter<SearchAdapter.PostViewHolder>() {
    private var onitemClicked: SearchAdapter.onclick = onitemClicked1
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_search_category, parent, false)
        return PostViewHolder(view)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.OnBind(categories1.get(position))
    }

    inner class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun OnBind(item: SerchingResponse.SearchResponseModal) {

            itemView.categoryName.text = item.name
            if (!item.is_category.equals("No")) {
                itemView.cartImage.visibility = View.GONE
                itemView.rightarrow.visibility = View.VISIBLE
            } else {
                itemView.cartImage.visibility = View.VISIBLE
                itemView.rightarrow.visibility = View.GONE

            }
            itemView.setOnClickListener {
                if (!item.is_category.equals("No")) {

                    val intent = Intent(context, AfterCategorySelectionActivity::class.java);
                    intent.putExtra("categoryId", item.idMeal.toString())
                    context.startActivity(intent);
                } else {
                    onitemClicked.itemclicked(true, item)

                }
            }
        }
    }

    fun setData(
        categoriesList: SerchingResponse
    ) {
        categories1 = categoriesList.searchResponse

        //  notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return categories1.size

    }

    interface onclick {
        public fun itemclicked(value: Boolean, get: SerchingResponse.SearchResponseModal)
    }

}