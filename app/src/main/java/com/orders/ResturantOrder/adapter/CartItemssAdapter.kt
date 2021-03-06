package com.orders.ResturantOrder.adapter




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
import com.orders.ResturantOrder.Fragments.CartFragment

import com.orders.ResturantOrder.R


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
        var totalNumber:Int= database.contactDao().getProductBasedIdCount(categories1.get(position).ProductIdNumber)
        holder.totalquantity.text=totalNumber.toString()
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

    }


    class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val minusButton: AppCompatButton = itemView.findViewById(R.id.minusButton)
        val plusButton: AppCompatButton = itemView.findViewById(R.id.plusButton)
        val totalquantity: TextView = itemView.findViewById(R.id.totalquantity)
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
