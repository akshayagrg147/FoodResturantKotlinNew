package com.orders.resturantorder.Fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.coroutineScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.meetSuccess.Database.CartItems
import com.meetSuccess.Database.ProductDatabase
import com.orders.resturantorder.Activity.AddNewAddressActivity
import com.orders.resturantorder.Activity.ProceedToAddress
import com.orders.resturantorder.R
import com.orders.resturantorder.adapter.CartItemssAdapter

import kotlinx.android.synthetic.main.activity_cart_itemss.*
import kotlinx.android.synthetic.main.fragment_cart.emptyLayout
import kotlinx.android.synthetic.main.fragment_cart.linearLayoutButton
import kotlinx.android.synthetic.main.fragment_cart.linearlayout
import kotlinx.android.synthetic.main.fragment_cart.priceAmount
import kotlinx.android.synthetic.main.fragment_cart.recyclerCategory
import kotlinx.android.synthetic.main.fragment_cart.totalquantity
import kotlinx.android.synthetic.main.fragment_cart.*
import kotlinx.coroutines.launch

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CartFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CartFragment : Fragment(), CartItemssAdapter.cartItemClickListner {

    lateinit var database: ProductDatabase
    private lateinit var categorySelectAdapter: CartItemssAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        database = ProductDatabase.getInstance(requireContext())
        view.findViewById<TextView>(R.id.reply_textview).setOnClickListener {

            database.contactDao().getAllAddress().observe(
                requireActivity()
            ) {
                if ((it != null) && (it.size > 0)) {
                    val intent = Intent(requireContext(), ProceedToAddress::class.java);
                    this.startActivity(intent);

                } else {
                    val intent = Intent(requireContext(), AddNewAddressActivity::class.java);
                    this.startActivity(intent);
                }

            }
        }
        // initRecyclerview()
        database.contactDao().getTotalProductItems().observe(viewLifecycleOwner) {
            if (it != null) {
                totalquantity.setText(it.toString())
                if (it <= 0) {

                    recyclerCategory.visibility = View.GONE
                    emptyLayout.visibility = View.VISIBLE
                    linearlayout.visibility = View.GONE
                    table_invoice1.visibility = View.GONE
                    linearLayoutButton.visibility = View.GONE


                } else {
                    callingIfItemThere();
                }
            } else {
                recyclerCategory.visibility = View.GONE
                emptyLayout.visibility = View.VISIBLE
                linearlayout.visibility = View.GONE
                table_invoice1.visibility = View.GONE
                linearLayoutButton.visibility = View.GONE
            }


        }

    }

    private fun callingIfItemThere() {
        database.contactDao().getTotalPrice().observe(viewLifecycleOwner) {
            if (it != null) {
                val totaltaxvalue = it - 10;
                priceAmount.text = "₹$it"
                item_total_price1.text = "₹$it"
                item_total_price1.text = "₹$totaltaxvalue"
                toPay_amount1.text = (it + totaltaxvalue + 12).toString()
            }

        }


        database.contactDao().getContact().observe(viewLifecycleOwner) {


            //  binding.recyclerCategory.isVisible = true
            //  binding.shimmerCategoryListItems.shimmerCategory.isVisible = false

            recyclerCategory.apply {
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(requireContext())
                categorySelectAdapter = CartItemssAdapter(it, this@CartFragment, database)
                adapter = categorySelectAdapter
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cart, container, false)
    }

    override fun ClickedPlusButton(cartitems: CartItems) {
        lifecycle.coroutineScope.launch {
            // database.contactDao().getProductBasedId(1212).observe(this@AfterCategorySelectionActivity,{})

            val intger: Int =
                database.contactDao().getProductBasedIdCount(cartitems.ProductIdNumber)
            database.contactDao()
                .updateCartItem(intger + 1, cartitems.ProductIdNumber)

            Log.d("coundddtis",
                cartitems.ProductIdNumber + "--" + cartitems.strCategoryThumb + "--" + "--" + intger + "--" + database.contactDao()
                    .getProductBasedIdCount(cartitems.ProductIdNumber).toString()
            )


        }

    }

    override fun ClickedMinusButton(cartitems: CartItems) {
        lifecycle.coroutineScope.launch {
            // database.contactDao().getProductBasedId(1212).observe(this@AfterCategorySelectionActivity,{})

            var intger: Int =
                database.contactDao().getProductBasedIdCount(cartitems.ProductIdNumber)

            intger = intger - 1
            Log.d(
                "coundddtis",
                cartitems.ProductIdNumber + "--" + cartitems.strCategoryThumb + "--" + "--" + intger + "--" + database.contactDao()
                    .getProductBasedIdCount(cartitems.ProductIdNumber).toString()
            )
            if (intger >= 1) {
                database.contactDao()
                    .updateCartItem(intger, cartitems.ProductIdNumber)


            } else if (intger == 0) {
                database.contactDao()
                    .deleteCartItem(cartitems.ProductIdNumber)
//                linearlayout.visibility = View.GONE
//                table_invoice1.visibility=View.GONE
//                linearLayoutButton.visibility = View.GONE
//                emptyLayout.visibility = View.VISIBLE

                //  binding.recyclerCategory.visibility = View.GONE
                // categorySelectAdapter.notifyDataSetChanged()
            }
        }
    }

}