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


class AddressItemssAdapter(
    private var categories1: List<AddressItems>,itemChossen1:AddressItemssAdapter.AddressChosen


)
    : RecyclerView.Adapter<AddressItemssAdapter.PostViewHolder>() {

    var selectedPosition:Int  = -1
     val itemChossen:AddressItemssAdapter.AddressChosen=itemChossen1


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.saved_address, parent, false)
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

        holder.radioButton.setTag(position);
        holder.radioButton.setOnClickListener(View.OnClickListener { v ->
            itemCheckChanged(v)
        })

    }


    class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        val radioButton: RadioButton = itemView.findViewById(R.id.radioButton1)
        val nameSection: TextView = itemView.findViewById(R.id.nameSection)

    }

    override fun getItemCount(): Int {
        Log.d("calllllllll", categories1.size.toString());
        return categories1.size

    }
    private fun itemCheckChanged(v: View) {
        selectedPosition = v.getTag() as Int
        itemChossen.itemChossen(selectedPosition)
        //notifyDataSetChanged()
    }
interface AddressChosen{
    fun itemChossen( int: Int)
}

}

