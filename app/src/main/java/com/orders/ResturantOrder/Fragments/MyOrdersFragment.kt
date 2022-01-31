package com.orders.ResturantOrder.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.orders.ResturantOrder.R
import kotlinx.android.synthetic.main.fragment_my_orders.*


class MyOrdersFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_orders, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        change_dining_layout.setOnClickListener(View.OnClickListener {
            val bundle = Bundle()
            bundle.putString("typeDataSend", "Dining")
            Navigation.findNavController(view).navigate(R.id.action_MyOrder_to_Dashboard,bundle);


        })
        change_deliverylayout.setOnClickListener(View.OnClickListener {
            val bundle = Bundle()
            bundle.putString("typeDataSend", "Delivery")
            Navigation.findNavController(view).navigate(R.id.action_MyOrder_to_Dashboard,bundle);


        })
        change_deliverylayout.setOnClickListener(View.OnClickListener {
            val bundle = Bundle()
            bundle.putString("typeDataSend", "Takeaway")
            Navigation.findNavController(view).navigate(R.id.action_MyOrder_to_Dashboard,bundle);


        })


    }
}