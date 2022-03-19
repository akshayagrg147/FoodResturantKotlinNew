package com.orders.resturantorder.adapter


import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.widget.AppCompatButton
import androidx.recyclerview.widget.RecyclerView

import com.meetSuccess.Database.ProductDatabase
import com.meetSuccess.FoodResturant.Model.cateogryAfterSelectionModal
import com.orders.resturantorder.R
import com.squareup.picasso.Picasso


class ListItemsAfterCategorySelectionAdapter(
    private var cntx: Context,private var db: ProductDatabase?, private var categories1: List<cateogryAfterSelectionModal.cateogryAfterSelectionModal1>,
    onitemClicked1: ListItemsAfterCategorySelectionAdapter.onclick
)
    : RecyclerView.Adapter<ListItemsAfterCategorySelectionAdapter.PostViewHolder>() {

private var database:ProductDatabase?=db
    private  var onitemClicked:ListItemsAfterCategorySelectionAdapter.onclick=onitemClicked1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.itemproduct_category, parent, false)
        return PostViewHolder(view)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.itemView.findViewById<TextView>(R.id.address).setText(categories1.get(position).getstrname())
        holder.priceCategory.text="₹"+categories1.get(position).getsale_price()
        holder.priceMrp.text="₹"+categories1.get(position).getstrprice()
        holder.description.text=categories1.get(position).getdescription()
        Log.d("callingTest", "ddddddd--button" + categories1.get(position).getstrname()+"---"+categories1.get(position).getidMeal())
        val intger: Int? = database?.contactDao()?.getProductBasedIdCount(categories1.get(position).getidMeal().toString())
        if (intger != null) {
            if(intger>0){
                holder.itemView.findViewById<Button>(R.id.AddButton).visibility=View.GONE
                holder.itemView.findViewById<LinearLayout>(R.id.ItemAdded).visibility=View.VISIBLE
                val intger: Int? = database?.contactDao()?.getProductBasedIdCount(categories1.get(position).getidMeal().toString())
                holder.itemView.findViewById<TextView>(R.id.totalquantity).text= intger.toString()
            }
        }

        holder.itemView.findViewById<Button>(R.id.AddButton).setOnClickListener{
            onitemClicked.itemclicked(categories1.get(position))
            holder.itemView.findViewById<Button>(R.id.AddButton).visibility=View.GONE
            holder.itemView.findViewById<LinearLayout>(R.id.ItemAdded).visibility=View.VISIBLE
            val intger: Int? = database?.contactDao()?.getProductBasedIdCount(categories1.get(position).getidMeal().toString())

            holder.itemView.findViewById<TextView>(R.id.totalquantity).text= intger.toString()


   };
        holder.itemView.findViewById<AppCompatButton>(R.id.minusButton).setOnClickListener{
            onitemClicked.ClickedMinusButton(categories1.get(position))

             val intger: Int? =
                 database?.contactDao()?.getProductBasedIdCount(categories1.get(position).getidMeal().toString())

            if(intger==0)
            {
                holder.itemView.findViewById<Button>(R.id.AddButton).visibility=View.VISIBLE
                holder.itemView.findViewById<LinearLayout>(R.id.ItemAdded).visibility=View.GONE
                return@setOnClickListener
            }
            holder.itemView.findViewById<Button>(R.id.AddButton).visibility=View.GONE
            holder.itemView.findViewById<LinearLayout>(R.id.ItemAdded).visibility=View.VISIBLE
            holder.itemView.findViewById<TextView>(R.id.totalquantity).text= intger.toString()

        };
        holder.itemView.findViewById<AppCompatButton>(R.id.plusButton).setOnClickListener{
            onitemClicked.ClickedPlusButton(categories1.get(position))
            val intger: Int? =database?.contactDao()?.getProductBasedIdCount(categories1.get(position).getidMeal().toString())

            if (intger != null) {
                holder.itemView.findViewById<TextView>(R.id.totalquantity).text= (intger).toString()
            }


           // itemclickListner.ClickedPlusButton(categories1.get(position))

        };


//        binding.categoryName.text=categories1.get(position).getStrCategory()
//
        Picasso.get().load(categories1.get(position).getstrimage()).placeholder(R.drawable.ic_circle)
            .into(holder.image)

    }

//    override fun getItemCount(): Int = categories1.size

    class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        val image: ImageView = itemView.findViewById(R.id.image)
        val description: TextView = itemView.findViewById(R.id.description)
        val priceCategory:TextView=itemView.findViewById(R.id.priceCategory)
        val priceMrp:TextView=itemView.findViewById(R.id.priceMrp)
    }

    fun setData(categoriesList: List<cateogryAfterSelectionModal.cateogryAfterSelectionModal1>)
    {
        categories1=categoriesList
        notifyDataSetChanged()
    }
    fun getData():List<cateogryAfterSelectionModal.cateogryAfterSelectionModal1>
    {
      return categories1
    }


    override fun getItemCount(): Int {

        return categories1.size

    }
    interface onclick{
        public fun itemclicked(item: cateogryAfterSelectionModal.cateogryAfterSelectionModal1)
        fun  ClickedPlusButton(cartitems: cateogryAfterSelectionModal.cateogryAfterSelectionModal1)
        fun  ClickedMinusButton(cartitems: cateogryAfterSelectionModal.cateogryAfterSelectionModal1)
    }


}