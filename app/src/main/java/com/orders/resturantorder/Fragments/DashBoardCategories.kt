package com.orders.resturantorder.Fragments


import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
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
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.meetSuccess.Database.CartItems
import com.meetSuccess.Database.ProductDatabase
import com.meetSuccess.FoodResturant.Adapter.CategoryAdapter
import com.meetSuccess.FoodResturant.Adapter.CategoryHeaderAdapter
import com.meetSuccess.FoodResturant.Adapter.SearchAdapter
import com.meetSuccess.FoodResturant.Model.*
import com.meetSuccess.FoodResturant.Util.ApiState
import com.orders.resturantorder.Base.AppUtils
import com.orders.resturantorder.Base.MyDividerItemDecoration
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
import kotlinx.coroutines.withContext


@AndroidEntryPoint
class DashBoardCategories : BaseFragment<FragmentBlankBinding, DashBoardCategoriesViewModal>(),
    MainActivity.passingInterface {

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

    var con: Context?=null
    override fun onAttach(context: Context) {
        super.onAttach(context)
        con=context
    }

    override fun onResume() {
        super.onResume()

    }

    override fun onDetach() {
        super.onDetach()
        con=null
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        database = ProductDatabase.getInstance(con!!)

        onBackPressed()


    }



    private fun onBackPressed() {
        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true /* enabled by default */) {
                override fun handleOnBackPressed() {
                    // Handle the back button event
                    NavHostFragment.findNavController(this@DashBoardCategories).navigateUp();
                    return
                }
            }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    companion object {

        fun newInstance(): DashBoardCategories {
            return DashBoardCategories()
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
        fragmentHomeViewBinding = getViewDataBinding()
        (con!! as MainActivity?)?.setOnPassingListner(this)
        if (arguments != null) {
            val str: String = requireArguments().getString("typeDataSend").toString()
            Toast.makeText(context, str, Toast.LENGTH_SHORT).show()
        }
        list = ArrayList()



        initSearchRecyclerView()
        mHomeViewModel.viewModelScope.launch(Dispatchers.Main) {
            mHomeViewModel.getCloseButtonStataus().observe(requireActivity(), Observer {
                fragmentHomeViewBinding!!.scrollview.visibility = View.VISIBLE
                toolbar_home.visibility = View.VISIBLE
                fragmentHomeViewBinding!!.searchlistView.visibility = View.GONE

            })


        }

        mHomeViewModel.getSelectedItem().observe(requireActivity(), Observer {
            Log.d("calling","callingdone   "+it)

            val modalclass = SearchingPassingData(it)
//            mHomeViewModel.viewModelScope.launch(Dispatchers.Main) {
//                mHomeViewModel.getCallingSearchApi(modalclass)
//            }
//
        }
      )
        mHomeViewModel.getItemClicked().observe(requireActivity(), Observer {
            if (it) {
                scrollview.visibility = View.VISIBLE
                toolbar_home.visibility = View.VISIBLE
                fragmentHomeViewBinding!!.searchlistView.visibility = View.GONE
            }

        })



        fragmentHomeViewBinding!!.cardSearch.setOnClickListener {
            mHomeViewModel.viewModelScope.launch(Dispatchers.Main) {

                (requireActivity() as MainActivity).passingvalue(true)
                scrollview.visibility = View.GONE
                toolbar_home.visibility = View.VISIBLE
                fragmentHomeViewBinding!!.searchlistView.visibility = View.VISIBLE
            }
        }


        val parentjob = mHomeViewModel.viewModelScope.launch(Dispatchers.IO) {
            val headercategoryR: Pair<*, Any>?

            val lowercategoryR: Pair<*, Any>?
            val searchingR: Pair<*, Any>?
            val headercategory = async {
              gettingResponseForheader()
            }
            val lowercategory = async {
                gettingResponseForLower()
            }
            val searching = async {
                searchingResponse()
            }
            headercategoryR = try {
                headercategory.await()
            } catch (ex: Exception) {
                null
            }


            lowercategoryR = try {
                lowercategory.await()
            } catch (ex: Exception) {
                null
            }
            searchingR = try {
                searching.await()
            } catch (ex: Exception) {
                null
            }


            if(headercategoryR!!.first==1)
            {
                headerpart(headercategoryR.second as List<CategoriesHeader.Category>)
                shimmer_view_containerheader.isVisible = false

//                recyclerView.post(Runnable { // Call smooth scroll
//                    recyclerView.smoothScrollToPosition((headercategoryR.second as List<CategoriesHeader.Category>).size - 1);
//                })


            }
//            if(lowercategoryR!!.first==1)
//            {
//                lowerpart(headercategoryR.second as List<Categories.Category>)
//                shimmer_view_containerheader.isVisible = false
//
////                recyclerView.post(Runnable { // Call smooth scroll
////                    recyclerView.smoothScrollToPosition((headercategoryR.second as List<CategoriesHeader.Category>).size - 1);
////                })
//
//
//            }
            if(searchingR!!.first==1)
            {
                ContextCompat.getMainExecutor(getBaseActivity()).execute {


                    searchAdapter.setData(searchingR.second as SerchingResponse, mHomeViewModel)


                    searchAdapter.notifyDataSetChanged()
                    // do something
                }


            }
        }

        parentjob.invokeOnCompletion { print("api call completion") }
    }

    suspend fun searchingResponse(): Pair<*, Any> {
        return withContext(Dispatchers.IO) {
            var msg: Pair<Int, Any>
            msg = Pair(0, "")
            mHomeViewModel._searchStateFlow.collect { it ->
             msg=   when (it) {
                    is ApiState.Loading -> {
                        Pair(0, "")
                    }
                    is ApiState.Failure -> {
                        Log.d("passing", "fail")
                        Pair(0, "")
                    }
                    is ApiState.SuccessCategories<*>-> {
                        Log.d("passing", "pass")

                        val response =
                            (it.data as SerchingResponse)
                        searchpart(response.searchResponse as List<SerchingResponse.SearchResponseModal>)
                        Pair(1, response)
//                        shimmerCategory.isVisible=false


                    }
                    is ApiState.Empty -> {
                        Pair(0, "")

                    }
                 else -> { Pair(0, "")}
             }
            }
            return@withContext msg
        }
    }

    suspend fun gettingResponseForLower(): Pair<*, Any> {
        return withContext(Dispatchers.Main) {
            val msg: Pair<Int, Any>
            msg = Pair(0, "")
            mHomeViewModel._postStateFlowLower.collect { it ->
                when (it) {
                    is ApiState.Loading -> {

                    }
                    is ApiState.Failure -> {
                        //showMessageOKCancel("Apistate faliure", { dialog, _ -> dialog.dismiss() }, false)
                    }
                    is ApiState.SuccessCategories<*> -> {
                        val response =
                            (it.data as Categories)





                            lowerpart(response.categories as List<Categories.Category>)
                            shimmerCategory.isVisible = false



                    }
                    is ApiState.Empty -> {

                    }
                }
            }
            msg
        }
    }

    suspend fun gettingResponseForheader(): Pair<*, Any> {

        return withContext(Dispatchers.Main) {
            var msg: Pair<Int, Any>
            msg = Pair(0, "")
            mHomeViewModel._postStateFlowHeader.collect { it ->
                msg= when (it) {
                    is ApiState.Loading -> {
                        Pair(0, "")
                    }
                    is ApiState.Failure -> {
                        Pair(0, "")

                    }
                    is ApiState.SuccessCategories<*> -> {
                        val response = (it.data as CategoriesHeader)

                            headerpart(response.categories as List<CategoriesHeader.Category>)
                            shimmer_view_containerheader.isVisible = false



                        Pair(0, "")

                    }
                    is ApiState.Empty -> {
                        Pair(0, "")

                    }
                    else ->  Pair(0, "")
                }


            }
            msg

        }
    }
    private fun headerpart(listAccountData: List<CategoriesHeader.Category>) {
        AppUtils.setUpRecyclerItemLayout(con!!, fragmentHomeViewBinding!!.recyclerView)
        headerpart = CategoryHeaderAdapter(listAccountData,con!!)
       // StaggeredGridLayoutManager(1, LinearLayoutManager.HORIZONTAL)
        fragmentHomeViewBinding!!.recyclerView.adapter = headerpart
    }
    private fun lowerpart(listAccountData: List<Categories.Category>) {
        Log.d("sizeofcategory",listAccountData.size.toString())

        AppUtils.setUpRecyclerItemLayoutStaggered(con!!, fragmentHomeViewBinding!!.recyclerCategory)
        categoryAdapter = CategoryAdapter(listAccountData,con!!)
      //  fragmentHomeViewBinding!!.recyclerCategory.addItemDecoration(SpaceItemDecorator())

        fragmentHomeViewBinding!!.recyclerCategory.adapter = categoryAdapter
    }
    private fun searchpart(listAccountData: List<SerchingResponse.SearchResponseModal>) {
        AppUtils.setUpRecyclerItemLayout(con!!, fragmentHomeViewBinding!!.searchlistView)

        searchAdapter =
            SearchAdapter(listAccountData, con!!, object : SearchAdapter.onclick {
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
        // StaggeredGridLayoutManager(1, LinearLayoutManager.HORIZONTAL)
        fragmentHomeViewBinding!!.recyclerView.adapter = searchAdapter


    }

//    private fun initRecyclerviewMeals() {
//        headerpart = CategoryHeaderAdapter(ArrayList(), requireContext())
//        fragmentHomeViewBinding?.recyclerView.apply {
//
//           this?. setHasFixedSize(true)
//
//            //animation
//            val list = listOf(
//                R.anim.layout_animation_fall_down,
//                R.anim.layout_animation_from_bottom,
//                R.anim.layout_animation_from_left,
//                R.anim.layout_animation_from_right
//            )
//            val animation = AnimationUtils.loadLayoutAnimation(context, list.random())
//            layoutAnimation = animation
//            scheduleLayoutAnimation()
//
//            layoutManager = StaggeredGridLayoutManager(1, LinearLayoutManager.HORIZONTAL)
//            adapter = headerpart
//
//        }
//    }

//    private fun initRecyclerview() {
//        categoryAdapter =
//            CategoryAdapter(ArrayList(), requireContext())
//        fragmentHomeViewBinding!!.recyclerCategory.apply {
//            setHasFixedSize(true)
//            //animation
//            val list = listOf(
//                R.anim.layout_animation_fall_down,
//                R.anim.layout_animation_from_bottom,
//                R.anim.layout_animation_from_left,
//                R.anim.layout_animation_from_right
//            )
//            val animation = AnimationUtils.loadLayoutAnimation(context, list.random())
//            layoutAnimation = animation
//            scheduleLayoutAnimation()
//
//            layoutManager = GridLayoutManager(requireContext(), 3)
//            adapter = categoryAdapter
//        }
 //   }


    private fun initSearchRecyclerView() {
        searchAdapter =
            SearchAdapter(ArrayList(), con!!, object : SearchAdapter.onclick {
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
            layoutManager = LinearLayoutManager(con!!)
            adapter = searchAdapter
        }
    }





    override fun passing(item: String) {
        mHomeViewModel.viewModelScope.launch(Dispatchers.IO){
            mHomeViewModel.getCallingSearchApi(item)

        }


    }

}