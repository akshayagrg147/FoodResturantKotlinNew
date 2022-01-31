package com.orders.ResturantOrder.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.lifecycle.coroutineScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.meetSuccess.Database.CartItems
import com.meetSuccess.Database.ProductDatabase
import com.orders.ResturantOrder.R
import com.orders.ResturantOrder.adapter.CartItemssAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_cart_itemss.*
import kotlinx.coroutines.launch
@AndroidEntryPoint
class CartItemss : AppCompatActivity(), CartItemssAdapter.cartItemClickListner {
    lateinit var database: ProductDatabase
    private lateinit var categorySelectAdapter: CartItemssAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart_itemss)
        database= ProductDatabase.getInstance(this@CartItemss)
        val actionBar: ActionBar? = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
       findViewById<TextView>(R.id.reply_textview).setOnClickListener {

            database.contactDao().getAllAddress().observe(this@CartItemss,{
                if((it!=null)&&(it.size>0))
                {
                    val intent = Intent(this,ProceedToAddress::class.java);
                    this.startActivity(intent);

                }
                else
                {
                    val intent = Intent(this,AddNewAddressActivity::class.java);
                    this.startActivity(intent);
                }

            }
            )
        }
        // initRecyclerview()
        database.contactDao().getTotalProductItems().observe(this@CartItemss,{
            if(it!=null) {
              totalquantity.setText(it.toString())
                if (it <= 0) {
                   recyclerCategory.visibility = View.GONE
                  emptyLayout.visibility = View.VISIBLE
                  linearlayout.visibility = View.GONE
                   linearLayoutButton.visibility = View.GONE


                }
            }
        })
        database.contactDao().getTotalPrice().observe(this@CartItemss,{
            if(it!=null)
              priceAmount.setText("₹"+it.toString())

        })


        database.contactDao().getContact().observe(this@CartItemss,{
            categorySelectAdapter= CartItemssAdapter(it,this,database)

            //  binding.recyclerCategory.isVisible = true
            //  binding.shimmerCategoryListItems.shimmerCategory.isVisible = false

            recyclerCategory.apply {
                setHasFixedSize(true)
                layoutManager= LinearLayoutManager(this@CartItemss)
                adapter=categorySelectAdapter
            }
        })


    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun ClickedPlusButton(cartitems: CartItems) {
        lifecycle.coroutineScope.launch {
            // database.contactDao().getProductBasedId(1212).observe(this@AfterCategorySelectionActivity,{})

            val intger: Int = database.contactDao().getProductBasedIdCount(cartitems.ProductIdNumber)
            database.contactDao()
                .updateCartItem(intger+1,cartitems.ProductIdNumber)

            Log.d("coundddtis",cartitems.ProductIdNumber+"--"+cartitems.strCategoryThumb+"--"+"--"+intger+"--"+database.contactDao().getProductBasedIdCount(cartitems.ProductIdNumber).toString())


        }

    }

    override fun ClickedMinusButton(cartitems: CartItems) {
        lifecycle.coroutineScope.launch {
            // database.contactDao().getProductBasedId(1212).observe(this@AfterCategorySelectionActivity,{})

            var intger: Int = database.contactDao().getProductBasedIdCount(cartitems.ProductIdNumber)

            intger=intger - 1
            Log.d("coundddtis",
                cartitems.ProductIdNumber + "--" + cartitems.strCategoryThumb + "--" + "--" + intger + "--" + database.contactDao()
                    .getProductBasedIdCount(cartitems.ProductIdNumber).toString()
            )
            if(intger>=1) {
                database.contactDao()
                    .updateCartItem(intger, cartitems.ProductIdNumber)




            }
            else if(intger==0)
            {
                database.contactDao()
                    .deleteCartItem(cartitems.ProductIdNumber)
               linearlayout.visibility = View.GONE
             linearLayoutButton.visibility = View.GONE
              emptyLayout.visibility = View.VISIBLE

                //  binding.recyclerCategory.visibility = View.GONE
                categorySelectAdapter.notifyDataSetChanged()
            }
        }
    }
}