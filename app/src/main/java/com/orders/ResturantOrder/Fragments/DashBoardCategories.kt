package com.orders.ResturantOrder.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible


import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.meetSuccess.FoodResturant.Adapter.CategoryAdapter

import com.meetSuccess.FoodResturant.Adapter.CategoryHeaderAdapter
import com.meetSuccess.FoodResturant.Model.Categories
import com.meetSuccess.FoodResturant.Util.ApiState
import com.orders.ResturantOrder.MainActivity
import com.orders.ResturantOrder.R
import com.orders.ResturantOrder.adapter.RecyclerViewAdapter
import com.orders.ResturantOrder.viewmodel.MainActivityViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_blank.*

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch



@AndroidEntryPoint
class DashBoardCategories : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var categoryAdapter: CategoryAdapter

    private lateinit var headerpart: CategoryHeaderAdapter

    private val linearLayoutManager:LinearLayoutManager?=null
    private lateinit var recyclerViewAdapter: RecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        (requireActivity() as MainActivity).supportActionBar!!.hide()
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_blank, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if(arguments!=null)
        {val str:String=requireArguments().getString("typeDataSend").toString()

            Toast.makeText(context,str,Toast.LENGTH_SHORT).show()
        }

        val mainViewModel  = ViewModelProvider((requireActivity() as MainActivity)).get(MainActivityViewModel::class.java)

        initRecyclerviewMeals()
        initRecyclerview()

        mainViewModel.getproductOffersHeader()
         mainViewModel.getLowestCategory()
        val parentjob= CoroutineScope(Dispatchers.IO).launch {
            val headercategory= async { mainViewModel._postStateFlowHeader.collect { it->
                when(it){
                    is ApiState.Loading -> {

                       // framelayout.setVisibility(View.VISIBLE)

                    }
                    is ApiState.Failure -> {


                        //framelayout.setVisibility(View.VISIBLE)

                    }
                    is ApiState.SuccessCategoriesHeader -> {

                       //framelayout.setVisibility(View.GONE)

                        headerpart.setData(it.data.categories)

                        ( requireActivity() as MainActivity).runOnUiThread {
                            shimmer_view_containerheader.isVisible=false

                            headerpart.notifyDataSetChanged() }

                        recyclerView.post(Runnable { // Call smooth scroll
                            recyclerView.smoothScrollToPosition(it.data.categories.size - 1);
                     })


                    }
                    is ApiState.Empty -> {

                    }
                }
            } }



            val lowercategory=async { mainViewModel._postStateFlowLower.collect { it->
                when(it){
                    is ApiState.Loading -> {

                    }
                    is ApiState.Failure -> {

                    }
                    is ApiState.SuccessCategories -> {
                      //  shimmerCategory.isVisible=false
                       categoryAdapter.setData(it.data.categories)
                        ( requireActivity() as MainActivity).runOnUiThread {
                            shimmerCategory.isVisible=false

                            categoryAdapter.notifyDataSetChanged() }


                    }
                    is ApiState.Empty -> {

                    }
                }
            } }

          ////  headercategory.await()
            lowercategory.await()
            print("both api get called")


        }
        parentjob.invokeOnCompletion { print("api call completion") }
    }
    private fun initRecyclerviewMeals() {
        headerpart= CategoryHeaderAdapter(ArrayList(), requireContext())
        recyclerView.apply {
            setHasFixedSize(true)
            layoutManager= StaggeredGridLayoutManager(1, LinearLayoutManager.HORIZONTAL)
            adapter=headerpart

        }
    }

    private fun initRecyclerview() {
        categoryAdapter=
            CategoryAdapter(ArrayList(), requireContext())
        recyclerCategory.apply {
            setHasFixedSize(true)
            layoutManager= GridLayoutManager(requireContext(), 3)
            adapter=categoryAdapter
        }
    }


}