package com.orders.ResturantOrder.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.meetSuccess.Database.AddressItems
import com.meetSuccess.Database.ProductDatabase

import com.orders.ResturantOrder.R
import kotlinx.android.synthetic.main.activity_proceed_to_address2.*


class AllAddressEditAdapter(
    private var categories1: List<AddressItems>,
    var database: ProductDatabase,
    requireContext: Context


)
    : RecyclerView.Adapter<AllAddressEditAdapter.PostViewHolder>() {




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.saved_editaddress, parent, false)
        return PostViewHolder(view)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {

        holder.nameSection.setText(
            categories1.get(position).customer_name.toString() + "\n" + categories1.get(
                position
            ).Address1.toString() + "," + categories1.get(position).Address2.toString() + "," + categories1.get(
                position
            ).PinCode.toString() + "," + categories1.get(position).customer_PhoneNumber.toString()
        )

        holder.deleteAddress.setOnClickListener(View.OnClickListener {

            database.contactDao()
                .deleteAddressItems( categories1.get(position).customer_name,categories1.get(
                    position
                ).Address1,categories1.get(position).Address2)

                   notifyDataSetChanged()

        })



    }


    class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){


        val nameSection: TextView = itemView.findViewById(R.id.nameSection)
        val deleteAddress:TextView=itemView.findViewById(R.id.deleteAddress)

    }

    override fun getItemCount(): Int {
        Log.d("calllllllll", categories1.size.toString());
        return categories1.size

    }



}

