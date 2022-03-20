package com.orders.resturantorder.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.Navigation
import com.orders.resturantorder.R
import com.orders.resturantorder.databinding.FragmentBlankBinding
import com.orders.resturantorder.databinding.FragmentMyOrdersBinding
import com.orders.resturantorder.databinding.FragmentProfileBinding
import com.orders.resturantorder.model.BR
import com.orders.resturantorder.viewmodel.BaseFragment
import com.orders.resturantorder.viewmodel.DashBoardCategoriesViewModal
import com.orders.resturantorder.viewmodel.FragmentMyOrderViewModal
import com.orders.resturantorder.viewmodel.FragmentProfileViewModal
import kotlinx.android.synthetic.main.fragment_profile.*

class ProfileFragment : BaseFragment<FragmentProfileBinding, FragmentProfileViewModal>(){

    private val mHomeViewModel: FragmentProfileViewModal by activityViewModels()
    override fun getBindingVariable(): Int = BR.viewModel
    override fun getLayoutId(): Int = R.layout.fragment_profile
    override fun getViewModel(): FragmentProfileViewModal = mHomeViewModel
    override fun getLifeCycleOwner(): LifecycleOwner = this
    private var fragmentmyorderbinding: FragmentMyOrdersBinding? = null




    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // AddressBook_layout=view.findViewById(R.id.AddressBook_layout)
        AddressBook_layout?.let {
            it.setOnClickListener(View.OnClickListener {
                Navigation.findNavController(view).navigate(R.id.addressBookFragment);
                //   Navigation.findNavController(view).navigate(R.id.settingsFragment, bundle, AppUtils.getNavOptions())

            })


        }
    }


}