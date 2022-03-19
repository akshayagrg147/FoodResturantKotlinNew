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


class SearchAdapter(private var categories1: List<SerchingResponse.SearchResponseModal>, val context: Context,onitemClicked1: SearchAdapter.onclick)
    : RecyclerView.Adapter<SearchAdapter.PostViewHolder>() {

    private  var onitemClicked: SearchAdapter.onclick=onitemClicked1

  //  private lateinit var binding:ItemRecyclerCategoryBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {

        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_search_category, parent, false)
        return PostViewHolder(view)


    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {

        holder.categoryName.text=categories1.get(position).getname()
        if(!categories1.get(position).getis_category().equals("No")) {
            holder.cartImage.visibility = View.GONE
            holder.arrowimage.visibility = View.VISIBLE
        }
        else{
            holder.cartImage.visibility=View.VISIBLE
            holder.arrowimage.visibility=View.GONE

        }
        holder.itemView.setOnClickListener{
            if(!categories1.get(position).getis_category().equals("No"))
            {

            val intent = Intent(context, AfterCategorySelectionActivity::class.java);
            intent.putExtra("categoryId",categories1.get(position).getIdCategory().toString())
                context.startActivity(intent);
            }
            else
            {

                onitemClicked.itemclicked(true,categories1.get(position))

            }


        }

    }

//    override fun getItemCount(): Int = categories1.size

    class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
       val cartImage: ImageView = itemView.findViewById(R.id.cartImage)
        val arrowimage: ImageView = itemView.findViewById(R.id.rightarrow)
        val categoryName: TextView = itemView.findViewById(R.id.categoryName)

    }

    fun setData(categoriesList: SerchingResponse,mainActivityViewModel: DashBoardCategoriesViewModal)
    {
        categories1=categoriesList.searchResponse

      //  notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return categories1.size

    }
    interface onclick{
        public fun itemclicked(value: Boolean, get: SerchingResponse.SearchResponseModal)
    }

}