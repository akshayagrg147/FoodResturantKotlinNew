package com.orders.resturantorder.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.meetSuccess.Database.AddressItems

import com.orders.resturantorder.R
import kotlinx.android.synthetic.main.saved_address.view.*


class AddressItemssAdapter(
    private var categories1: List<AddressItems>, val itemChossen: AddressItemssAdapter.AddressChosen
) : RecyclerView.Adapter<AddressItemssAdapter.PostViewHolder>() {
    var selectedPosition: Int = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.saved_address, parent, false)
        return PostViewHolder(view)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.onbind(categories1[position])
    }

    inner class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun onbind(addressItems: AddressItems) {
            itemView.nameSection.text =
                addressItems.customer_name.toString() + "\n" + addressItems.Address1.toString() + "," + addressItems.Address2.toString() + "," + addressItems.PinCode.toString() + "," + addressItems.customer_PhoneNumber.toString()
            itemView.radioButton1.tag = position;
            itemView.radioButton1.setOnClickListener(View.OnClickListener { v ->
                itemCheckChanged(v)
            })

        }
    }

    override fun getItemCount(): Int {
        return categories1.size

    }

    private fun itemCheckChanged(v: View) {
        selectedPosition = v.getTag() as Int
        itemChossen.itemChossen(selectedPosition)
        //notifyDataSetChanged()
    }

    interface AddressChosen {
        fun itemChossen(int: Int)
    }

}

