package com.orders.ResturantOrder.Fragments


import android.content.Context
import android.os.Bundle
import android.text.Layout
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.meetSuccess.FoodResturant.Adapter.CategoryAdapter
import com.meetSuccess.FoodResturant.Adapter.CategoryHeaderAdapter
import com.meetSuccess.FoodResturant.Adapter.SearchAdapter
import com.meetSuccess.FoodResturant.Model.SearchingPassingData
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
    private var passingclicwk: passingclick? = null
    private lateinit var categoryAdapter: CategoryAdapter
    private lateinit var headerpart: CategoryHeaderAdapter
    private lateinit var shimmer_view_containerheader: LinearLayout
    private lateinit var shimmerCategory: LinearLayout
    private lateinit var scrollview:ScrollView
    private lateinit var recyclerView:RecyclerView
    private lateinit var searchlistView:RecyclerView


    private lateinit var mainViewModel: MainActivityViewModel
    lateinit var list: ArrayList<String>


    lateinit var searchAdapter: SearchAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    companion object {

        lateinit  var appContext: Context
        fun newInstance(): DashBoardCategories {
            return DashBoardCategories()
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_blank, container, false)
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
appContext=context
        if (context is passingclick) {
            passingclicwk = context as passingclick
        } else {
            throw RuntimeException(
                context.toString()
                        + " must implement OnFragmentInteractionListener"
            )
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        scrollview=view.findViewById(R.id.scrollview)
        recyclerView=view.findViewById(R.id.recyclerView)
        searchlistView=view.findViewById(R.id.searchlistView)
        if (arguments != null) {
            val str: String = requireArguments().getString("typeDataSend").toString()

            Toast.makeText(context, str, Toast.LENGTH_SHORT).show()
        }
        list = ArrayList()
        shimmer_view_containerheader=view.findViewById(R.id.shimmer_view_containerheader)
        shimmerCategory=view.findViewById(R.id.shimmerCategory)
        mainViewModel =
            ViewModelProvider((requireActivity() as MainActivity)).get(MainActivityViewModel::class.java)

        initRecyclerviewMeals()
        initRecyclerview()
        initSearchRecyclerView()

        mainViewModel.getproductOffersHeader()
        mainViewModel.getLowestCategory()
        mainViewModel.getSelectedItem().observe(requireActivity(), Observer {
            val modalclass: SearchingPassingData = SearchingPassingData(it)
            mainViewModel.getCallingSearchApi(modalclass)
        })
        mainViewModel.getCloseButtonStataus().observe(requireActivity(), Observer {
            scrollview.visibility = View.VISIBLE
//            toolbar_home.visibility=View.VISIBLE
            searchlistView.visibility = View.GONE

        })
        cardSearch.setOnClickListener {
            Log.d("callingjrrrr", passingclicwk.toString())
            passingclicwk?.passingvalue(true)
            scrollview.visibility = View.GONE
//            toolbar_home.visibility=View.VISIBLE
            searchlistView.visibility = View.VISIBLE
        }


        val parentjob = CoroutineScope(Dispatchers.IO).launch {
            val headercategory = async {
                mainViewModel._postStateFlowHeader.collect { it ->
                    when (it) {
                        is ApiState.Loading -> {

                        // framelayout.setVisibility(View.VISIBLE)

                        }
                        is ApiState.Failure -> {
                        //framelayout.setVisibility(View.VISIBLE)

                        }
                        is ApiState.SuccessCategoriesHeader -> {

                            //framelayout.setVisibility(View.GONE)



                            (appContext as MainActivity).runOnUiThread {
                                headerpart.setData(it.data.categories)
                                shimmer_view_containerheader.isVisible = false
                                headerpart.notifyDataSetChanged()
                            }
//                            ContextCompat.getMainExecutor(appContext).execute {
//
//                                // do something
//                            }

                            recyclerView.post(Runnable { // Call smooth scroll
                                recyclerView.smoothScrollToPosition(it.data.categories.size - 1);
                            })


                        }
                        is ApiState.Empty -> {

                        }
                    }
                }
            }


            val lowercategory = async {
                mainViewModel._postStateFlowLower.collect { it ->
                    when (it) {
                        is ApiState.Loading -> {

                        }
                        is ApiState.Failure -> {

                        }
                        is ApiState.SuccessCategories -> {
//                        shimmerCategory.isVisible=false


                            (appContext as MainActivity).runOnUiThread {
                                categoryAdapter.setData(it.data.categories)
                                shimmerCategory.isVisible = false

                                categoryAdapter.notifyDataSetChanged()
                            }



                        }
                        is ApiState.Empty -> {

                        }
                    }
                }
            }
            val searching = async {
                mainViewModel._searchStateFlow.collect { it ->
                    when (it) {
                        is ApiState.Loading -> {

                        }
                        is ApiState.Failure -> {

                        }
                        is ApiState.GetResultBasedOnKeywords -> {
//                        shimmerCategory.isVisible=false
                            ContextCompat.getMainExecutor(appContext).execute {
                                searchAdapter.setData(it.data)

                                searchAdapter.notifyDataSetChanged()
                                // do something
                            }
//                            appContext .runOnUiThread {
//
//                            }


                        }
                        is ApiState.Empty -> {

                        }
                    }
                }
            }

            ////  headercategory.await()
            searching.await()
            print("both api get called")


        }
        parentjob.invokeOnCompletion { print("api call completion") }
    }

    private fun initRecyclerviewMeals() {
        headerpart = CategoryHeaderAdapter(ArrayList(), requireContext())
        recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = StaggeredGridLayoutManager(1, LinearLayoutManager.HORIZONTAL)
            adapter = headerpart

        }
    }

    private fun initRecyclerview() {
        categoryAdapter =
            CategoryAdapter(ArrayList(), requireContext())
        recyclerCategory.apply {
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(requireContext(), 3)
            adapter = categoryAdapter
        }
    }


    private fun initSearchRecyclerView() {
        searchAdapter = SearchAdapter(ArrayList(), requireContext())
        searchlistView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext())
            adapter = searchAdapter
        }
    }


    interface passingclick {
        public fun passingvalue(boolean: Boolean)

    }

}