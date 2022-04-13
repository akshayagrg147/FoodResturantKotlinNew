package com.orders.resturantorder.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.meetSuccess.Database.CartItems
import com.meetSuccess.Database.ProductDatabase
import com.orders.resturantorder.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.cartitems_product.view.*


class CartItemssAdapter(
    private var categories1: List<CartItems>,
    cartItemClickListnerr: cartItemClickListner,
    database1: ProductDatabase

) : RecyclerView.Adapter<CartItemssAdapter.PostViewHolder>() {
    private val database: ProductDatabase = database1
    private val itemclickListner: cartItemClickListner = cartItemClickListnerr
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.cartitems_product, parent, false)
        return PostViewHolder(view)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.onbind(categories1[position])
    }

    inner class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun onbind(cartItems: CartItems) {
            var cartItems: CartItems =
                database.contactDao().getProductBasedId(cartItems.ProductIdNumber)
            var totalNumber: Int =
                database.contactDao().getProductBasedIdCount(cartItems.ProductIdNumber)
            itemView.totalquantity.text = totalNumber.toString()
            itemView.priceCategory.text =
                (cartItems.let { it.strProductPrice?.times(totalNumber) }).toString()
            itemView.cart_product_name.text = cartItems.let { it.strProductName }
            itemView.plusButton.setOnClickListener {

                itemclickListner.ClickedPlusButton(cartItems)
            }
            itemView.minusButton.setOnClickListener {

                itemclickListner.ClickedMinusButton(cartItems)

            }
            Picasso.get().load(cartItems.strCategoryThumb)
                .placeholder(R.drawable.clock_my_time_in_button)
                .into(itemView.image)

        }

    }

    override fun getItemCount(): Int {
        return categories1.size

    }

    public interface cartItemClickListner {
        fun ClickedPlusButton(cartitems: CartItems)
        fun ClickedMinusButton(cartitems: CartItems)

    }


}
