package com.orders.resturantorder.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.Navigation
import com.orders.resturantorder.R
import com.orders.resturantorder.databinding.FragmentBlankBinding
import com.orders.resturantorder.databinding.FragmentMyOrdersBinding
import com.orders.resturantorder.viewmodel.BaseFragment
import com.orders.resturantorder.viewmodel.DashBoardCategoriesViewModal
import com.orders.resturantorder.viewmodel.FragmentMyOrderViewModal
import kotlinx.android.synthetic.main.fragment_my_orders.*


class MyOrdersFragment : BaseFragment<FragmentMyOrdersBinding, FragmentMyOrderViewModal>(){
    private val mHomeViewModel: FragmentMyOrderViewModal by activityViewModels()
    override fun getBindingVariable(): Int = 2
    override fun getLayoutId(): Int = R.layout.fragment_blank
    override fun getViewModel(): FragmentMyOrderViewModal = mHomeViewModel
    override fun getLifeCycleOwner(): LifecycleOwner = this
    private var fragmentmyorderbinding: FragmentMyOrdersBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentmyorderbinding = getViewDataBinding()
        fragmentmyorderbinding!!. changeDiningLayout.setOnClickListener(View.OnClickListener {
            val bundle = Bundle()
            bundle.putString("typeDataSend", "Dining")
            Navigation.findNavController(view).navigate(R.id.action_MyOrder_to_Dashboard,bundle);


        })
        fragmentmyorderbinding!!.changeDeliverylayout.setOnClickListener(View.OnClickListener {
            val bundle = Bundle()
            bundle.putString("typeDataSend", "Delivery")
            Navigation.findNavController(view).navigate(R.id.action_MyOrder_to_Dashboard,bundle);


        })
        fragmentmyorderbinding!!.changeDeliverylayout.setOnClickListener(View.OnClickListener {
            val bundle = Bundle()
            bundle.putString("typeDataSend", "Takeaway")
            Navigation.findNavController(view).navigate(R.id.action_MyOrder_to_Dashboard,bundle);


        })


    }
}