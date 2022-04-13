package com.orders.resturantorder.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.meetSuccess.Database.AddressItems
import com.meetSuccess.Database.ProductDatabase

import com.orders.resturantorder.R
import kotlinx.android.synthetic.main.saved_editaddress.view.*


class AllAddressEditAdapter(
    private var categories1: List<AddressItems>,
    var database: ProductDatabase,
    requireContext: Context
) : RecyclerView.Adapter<AllAddressEditAdapter.PostViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.saved_editaddress, parent, false)
        return PostViewHolder(view)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.onbind(categories1[position])

    }

    inner class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun onbind(addressItems: AddressItems) {
            itemView.nameSection.text =
                addressItems.customer_name.toString() + "\n" + addressItems.Address1.toString() + "," + addressItems.Address2.toString() + "," + addressItems.PinCode.toString() + "," + addressItems.customer_PhoneNumber.toString()
            itemView.deleteAddress.setOnClickListener(View.OnClickListener {
                database.contactDao()
                    .deleteAddressItems(
                        addressItems.customer_name, addressItems.Address1, addressItems.Address2)
                notifyDataSetChanged()

            })
        }
    }

    override fun getItemCount(): Int {
        return categories1.size

    }


}

