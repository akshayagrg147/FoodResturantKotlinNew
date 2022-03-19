package com.orders.resturantorder.adapter




import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.recyclerview.widget.RecyclerView
import com.meetSuccess.Database.CartItems
import com.meetSuccess.Database.ProductDatabase

import com.orders.resturantorder.R
import com.squareup.picasso.Picasso


class CartItemssAdapter(private var categories1: List<CartItems>, cartItemClickListnerr: cartItemClickListner, database1: ProductDatabase

)
    : RecyclerView.Adapter<CartItemssAdapter.PostViewHolder>() {


    private  val database: ProductDatabase=database1

    private  val itemclickListner:cartItemClickListner=cartItemClickListnerr


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.cartitems_product, parent, false)
        return PostViewHolder(view)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        var cartItems:CartItems= database.contactDao().getProductBasedId(categories1.get(position).ProductIdNumber)
        var totalNumber:Int= database.contactDao().getProductBasedIdCount(categories1.get(position).ProductIdNumber)
       holder.totalquantity.text=totalNumber.toString()
        holder.priceCategory.text=(cartItems?.let { it.strProductPrice?.times(totalNumber) }).toString()
        holder.cart_product_name.text=cartItems.let { it.strProductName }
     holder.plusButton.setOnClickListener{
//            totalNumber=totalNumber+1
//            binding.totalquantity.text=totalNumber.toString()
            itemclickListner.ClickedPlusButton(categories1.get(position))
        }
        holder.minusButton.setOnClickListener{
//            totalNumber=totalNumber-1
//            binding.totalquantity.text=totalNumber.toString()
            itemclickListner.ClickedMinusButton(categories1.get(position))

        }
        Picasso.get().load(cartItems.strCategoryThumb).placeholder(R.drawable.clock_my_time_in_button)
                .into(holder.image)

    }


    class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val minusButton: AppCompatButton = itemView.findViewById(R.id.minusButton)
        val plusButton: AppCompatButton = itemView.findViewById(R.id.plusButton)
        val totalquantity: TextView = itemView.findViewById(R.id.totalquantity)
        val cart_product_name: TextView = itemView.findViewById(R.id.cart_product_name)
        val priceCategory: TextView = itemView.findViewById(R.id.priceCategory)
        val image:ImageView=itemView.findViewById(R.id.image)
    }

  override fun getItemCount(): Int {
        Log.d("calllllllll",categories1.size.toString());
        return categories1.size

    }
    public interface cartItemClickListner{
        fun  ClickedPlusButton(cartitems: CartItems)
        fun  ClickedMinusButton(cartitems: CartItems)

    }


}
