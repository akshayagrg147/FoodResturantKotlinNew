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
import kotlinx.android.synthetic.main.itemproduct_category.view.*


class ListItemsAfterCategorySelectionAdapter(
    private var cntx: Context,
    private var db: ProductDatabase?,
    private var categories1: List<cateogryAfterSelectionModal.cateogryAfterSelectionModal1>,
    onitemClicked1: onclick
) : RecyclerView.Adapter<ListItemsAfterCategorySelectionAdapter.PostViewHolder>() {

    private var database: ProductDatabase? = db
    private var onitemClicked: onclick = onitemClicked1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.itemproduct_category, parent, false)
        return PostViewHolder(view)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.onBind(categories1[position])


    }

//    override fun getItemCount(): Int = categories1.size

    inner class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun onBind(category: cateogryAfterSelectionModal.cateogryAfterSelectionModal1) {
            itemView.address.text = category.getstrname()
            itemView.priceCategory.text = "₹ ${category.getsale_price()}"
            itemView.priceMrp.text = "₹" + category.getstrprice()
            itemView.description.text = category.getdescription()
            val intger: Int? =
                database?.contactDao()?.getProductBasedIdCount(category.getidMeal().toString())
            if (intger != null) {
                if (intger > 0) {
                    itemView.AddButton.visibility = View.GONE
                    itemView.ItemAdded.visibility = View.VISIBLE
                    val intger: Int? = database?.contactDao()
                        ?.getProductBasedIdCount(category.getidMeal().toString())
                    itemView.totalquantity.text = intger.toString()
                }
            }

            itemView.AddButton.setOnClickListener {
                onitemClicked.itemclicked(category)
                itemView.AddButton.visibility = View.GONE
                itemView.ItemAdded.visibility = View.VISIBLE
                val intger: Int? = database?.contactDao()
                    ?.getProductBasedIdCount(category.getidMeal().toString())

                itemView.totalquantity.text = intger.toString()


            };
            itemView.minusButton.setOnClickListener {
                onitemClicked.ClickedMinusButton(category)

                val intger: Int? =
                    database?.contactDao()
                        ?.getProductBasedIdCount(category.getidMeal().toString())

                if (intger == 0) {
                    itemView.AddButton.visibility = View.VISIBLE
                    itemView.ItemAdded.visibility = View.GONE
                    return@setOnClickListener
                }
                itemView.AddButton.visibility = View.GONE
                itemView.ItemAdded.visibility = View.VISIBLE
                itemView.totalquantity.text = intger.toString()

            };
            itemView.plusButton.setOnClickListener {
                onitemClicked.ClickedPlusButton(category)
                val intger: Int? = database?.contactDao()
                    ?.getProductBasedIdCount(category.getidMeal().toString())

                if (intger != null) {
                    itemView.totalquantity.text = (intger).toString()
                }


            };


//
            Picasso.get().load(category.getstrimage())
                .placeholder(R.drawable.ic_circle)
                .into(itemView.image)

        }


    }

    fun setData(categoriesList: List<cateogryAfterSelectionModal.cateogryAfterSelectionModal1>) {
        categories1 = categoriesList
        notifyDataSetChanged()
    }

    fun getData(): List<cateogryAfterSelectionModal.cateogryAfterSelectionModal1> {
        return categories1
    }


    override fun getItemCount(): Int {

        return categories1.size

    }

    interface onclick {
        public fun itemclicked(item: cateogryAfterSelectionModal.cateogryAfterSelectionModal1)
        fun ClickedPlusButton(cartitems: cateogryAfterSelectionModal.cateogryAfterSelectionModal1)
        fun ClickedMinusButton(cartitems: cateogryAfterSelectionModal.cateogryAfterSelectionModal1)
    }


}