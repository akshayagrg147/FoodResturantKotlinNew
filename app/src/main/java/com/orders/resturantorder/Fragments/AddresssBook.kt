package com.orders.resturantorder.Fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.LinearLayoutManager
import com.meetSuccess.Database.AddressItems
import com.meetSuccess.Database.ProductDatabase
import com.orders.resturantorder.Activity.AddNewAddressActivity
import com.orders.resturantorder.R
import com.orders.resturantorder.adapter.AllAddressEditAdapter
import com.orders.resturantorder.adapter.CartItemssAdapter
import com.orders.resturantorder.databinding.FragmentAddresssBookBinding

import com.orders.resturantorder.viewmodel.AddressViewModal
import com.orders.resturantorder.viewmodel.BaseFragment
import com.orders.resturantorder.viewmodel.CartFragmentViewModal
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_proceed_to_address2.*

@AndroidEntryPoint
class AddresssBook  : BaseFragment<FragmentAddresssBookBinding, AddressViewModal>()  {
    private val mHomeViewModel: AddressViewModal by viewModels()


    override fun getBindingVariable(): Int = 2
    override fun getLayoutId(): Int = R.layout.fragment_addresss_book
    override fun getViewModel(): AddressViewModal = mHomeViewModel
    override fun getLifeCycleOwner(): LifecycleOwner = this
    lateinit var database: ProductDatabase
    lateinit var ListAddress: List<AddressItems>
    lateinit var addnewaddress: TextView
    lateinit var confirmorder: TextView

    private lateinit var categorySelectAdapter: AllAddressEditAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        database= ProductDatabase.getInstance(requireActivity())

        addnewaddress=view.findViewById(R.id.addnewaddress)
        confirmorder=view.findViewById(R.id.confirmorder)




        addnewaddress.setOnClickListener {
            val intent = Intent(context, AddNewAddressActivity::class.java);
            this.startActivity(intent);
        }
        confirmorder.setOnClickListener{
//            val intent = Intent(this,OrderPlaced::class.java);
//            this.startActivity(intent);
//            finish()

        }
        // initRecyclerview()


        database.contactDao().getAllAddress().observe(requireActivity()) {
            ListAddress = it
            categorySelectAdapter = AllAddressEditAdapter(it, database, requireContext())

            recyclerCategory.isVisible = true
            //  binding.shimmerCategoryListItems.shimmerCategory.isVisible = false

            recyclerCategory.apply {
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(requireActivity())
                adapter = categorySelectAdapter
            }
        }
    }

}