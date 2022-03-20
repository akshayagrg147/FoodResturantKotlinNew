package com.orders.resturantorder.Fragments


import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.meetSuccess.Database.CartItems
import com.meetSuccess.Database.ProductDatabase
import com.meetSuccess.FoodResturant.Adapter.CategoryAdapter
import com.meetSuccess.FoodResturant.Adapter.CategoryHeaderAdapter
import com.meetSuccess.FoodResturant.Adapter.SearchAdapter
import com.meetSuccess.FoodResturant.Model.*
import com.meetSuccess.FoodResturant.Util.ApiState
import com.orders.resturantorder.MainActivity
import com.orders.resturantorder.R
import com.orders.resturantorder.databinding.FragmentBlankBinding
import com.orders.resturantorder.model.BR
import com.orders.resturantorder.viewmodel.BaseFragment
import com.orders.resturantorder.viewmodel.DashBoardCategoriesViewModal
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_blank.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


@AndroidEntryPoint
class DashBoardCategories : BaseFragment<FragmentBlankBinding, DashBoardCategoriesViewModal>() ,MainActivity.passingInterface {
    private var passingclicwk: passingclick? = null
    private lateinit var categoryAdapter: CategoryAdapter
    private lateinit var headerpart: CategoryHeaderAdapter
    private val mHomeViewModel: DashBoardCategoriesViewModal by viewModels()
    private var fragmentHomeViewBinding: FragmentBlankBinding? = null
    lateinit var database: ProductDatabase
    override fun getBindingVariable(): Int = BR.viewModel
    override fun getLayoutId(): Int = R.layout.fragment_blank
    override fun getViewModel(): DashBoardCategoriesViewModal = mHomeViewModel
    override fun getLifeCycleOwner(): LifecycleOwner = this
    lateinit var list: ArrayList<String>
    lateinit var searchAdapter: SearchAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        database = ProductDatabase.getInstance(requireContext())
        onBackPressed()

    }

    public fun SendSearchResponse(param: DashBoardCategories.passingclick) {
        passingclicwk=param

    }

    private fun onBackPressed() {
        val callback: OnBackPressedCallback = object : OnBackPressedCallback(true /* enabled by default */) {
            override fun handleOnBackPressed() {
                // Handle the back button event
                NavHostFragment.findNavController(this@DashBoardCategories).navigateUp();
                return
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    companion object {
        lateinit var appContext: Context
        fun newInstance(): DashBoardCategories {
            return DashBoardCategories()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        appContext = context

//        if (context is passingclick) {
//            passingclicwk = context as passingclick
//        } else {
////            throw RuntimeException(
////                context.toString()
////                        + " must implement OnFragmentInteractionListener"
////            )
//        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentHomeViewBinding = getViewDataBinding()
        (requireActivity() as MainActivity?)?.setOnPassingListner(this)
        if (arguments != null) {
            val str: String = requireArguments().getString("typeDataSend").toString()
            Toast.makeText(context, str, Toast.LENGTH_SHORT).show()
        }
        list = ArrayList()

        initRecyclerviewMeals()
        initRecyclerview()
        initSearchRecyclerView()
        mHomeViewModel.viewModelScope.launch(Dispatchers.Main){


            mHomeViewModel.getCloseButtonStataus().observe(requireActivity(), Observer {
                fragmentHomeViewBinding!!.scrollview.visibility = View.VISIBLE
          toolbar_home.visibility=View.VISIBLE
                fragmentHomeViewBinding!!.searchlistView.visibility = View.GONE

            })


        }

            mHomeViewModel.getSelectedItem().observe(requireActivity(), Observer {
                val modalclass = SearchingPassingData(it)
                mHomeViewModel.viewModelScope.launch(Dispatchers.Main) {
                    mHomeViewModel.getCallingSearchApi(modalclass)
                }

            })
        mHomeViewModel.getItemClicked().observe(requireActivity(), Observer {
            if (it) {
                scrollview.visibility = View.VISIBLE
            toolbar_home.visibility=View.VISIBLE
                fragmentHomeViewBinding!!.searchlistView.visibility = View.GONE
            }

        })



        fragmentHomeViewBinding!!.cardSearch.setOnClickListener {
            mHomeViewModel.viewModelScope.launch(Dispatchers.Main) {
                passingclicwk?.passingvalue(true)
                scrollview.visibility = View.GONE
          toolbar_home.visibility=View.VISIBLE
                fragmentHomeViewBinding!!.searchlistView.visibility = View.VISIBLE
            } }

//   CoroutineScope(Dispatchers.IO).launch
        val parentjob =  mHomeViewModel.viewModelScope.launch(Dispatchers.Main) {
            val headercategory = async {

                mHomeViewModel._postStateFlowHeader.collect { it ->
                    when (it) {
                        is ApiState.Loading -> {

                            // framelayout.setVisibility(View.VISIBLE)

                        }
                        is ApiState.Failure -> {
                            //framelayout.setVisibility(View.VISIBLE)

                        }
                        is ApiState.SuccessCategories <*>-> {
                            val response =
                                (it.data as CategoriesHeader)

                            //framelayout.setVisibility(View.GONE)


                            (appContext as MainActivity).runOnUiThread {
                                headerpart.setData(response.categories)
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
                mHomeViewModel._postStateFlowLower.collect { it ->
                    when (it) {
                        is ApiState.Loading -> {

                        }
                        is ApiState.Failure -> {

                        }
                        is ApiState.SuccessCategories<*> -> {
                            val response =
                                (it.data as Categories)
                          shimmerCategory.isVisible=false

                            (appContext as MainActivity).runOnUiThread {
                                categoryAdapter.setData(response.categories)
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
                mHomeViewModel._searchStateFlow.collect { it ->
                    when (it) {
                        is ApiState.Loading -> {

                        }
                        is ApiState.Failure -> {

                        }
                        is ApiState.SuccessCategories<*> -> {
                            val response =
                                (it.data as SerchingResponse)
//                        shimmerCategory.isVisible=false
                            ContextCompat.getMainExecutor(appContext).execute {

                                val mainViewModel: DashBoardCategoriesViewModal =
                                    ViewModelProvider((appContext as MainActivity)).get(
                                        DashBoardCategoriesViewModal::class.java
                                    )
                                searchAdapter.setData(response, mainViewModel)


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

            //animation
            val list = listOf(
                R.anim.layout_animation_fall_down,
                R.anim.layout_animation_from_bottom,
                R.anim.layout_animation_from_left,
                R.anim.layout_animation_from_right
            )
            val animation = AnimationUtils.loadLayoutAnimation(context, list.random())
            layoutAnimation = animation
            scheduleLayoutAnimation()

            layoutManager = StaggeredGridLayoutManager(1, LinearLayoutManager.HORIZONTAL)
            adapter = headerpart

        }
    }

    private fun initRecyclerview() {
        categoryAdapter =
            CategoryAdapter(ArrayList(), requireContext())
        fragmentHomeViewBinding!!.recyclerCategory.apply {
            setHasFixedSize(true)
            //animation
            val list = listOf(
                R.anim.layout_animation_fall_down,
                R.anim.layout_animation_from_bottom,
                R.anim.layout_animation_from_left,
                R.anim.layout_animation_from_right
            )
            val animation = AnimationUtils.loadLayoutAnimation(context, list.random())
            layoutAnimation = animation
            scheduleLayoutAnimation()

            layoutManager = GridLayoutManager(requireContext(), 3)
            adapter = categoryAdapter
        }
    }


    private fun initSearchRecyclerView() {
        searchAdapter =
            SearchAdapter(ArrayList(), requireContext(), object : SearchAdapter.onclick {
                override fun itemclicked(
                    value: Boolean,
                    item: SerchingResponse.SearchResponseModal
                ) {
                    if (value) {
                        val intger: Int = database.contactDao()
                            .getProductBasedIdCount(item.getidMeal().toString())
                        if (intger == 0) {
                            database.contactDao()
                                .insertCartItem(
                                    CartItems(
                                        item.getidMeal().toString(),
                                        item.getstrimage(),
                                        intger + 1,
                                        Integer.parseInt(item.getsale_price()),
                                        item.getstrname()
                                    )
                                )

                        } else if (intger >= 1) {

                            database.contactDao()
                                .updateCartItem(intger + 1, item.getidMeal().toString())
                        }
                        mHomeViewModel.itemclicked(true)
                    }

                }

            })
        fragmentHomeViewBinding!!.searchlistView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext())
            adapter = searchAdapter
        }
    }


    interface passingclick {
        public fun passingvalue(boolean: Boolean)

    }



    override fun passing(item: String) {
      Log.d("passing",item)
    }

}