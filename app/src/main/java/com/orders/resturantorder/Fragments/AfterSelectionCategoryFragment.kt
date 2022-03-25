package com.orders.resturantorder.Fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.coroutineScope
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.navigation.Navigation
import com.meetSuccess.Database.CartItems
import com.meetSuccess.Database.ProductDatabase
import com.meetSuccess.FoodResturant.Adapter.CategoryAdapter
import com.meetSuccess.FoodResturant.Model.Categories
import com.meetSuccess.FoodResturant.Model.cateogryAfterSelectionModal
import com.meetSuccess.FoodResturant.Util.ApiState
import com.orders.resturantorder.Base.AppUtils
import com.orders.resturantorder.R
import com.orders.resturantorder.adapter.ListItemsAfterCategorySelectionAdapter
import com.orders.resturantorder.databinding.FragmentAfterSelectionCategoryBinding

import com.orders.resturantorder.model.BR
import com.orders.resturantorder.viewmodel.AfterSelectionFragmentViewModal
import com.orders.resturantorder.viewmodel.BaseFragment
import com.orders.resturantorder.viewmodel.DashBoardCategoriesViewModal
import com.rowland.cartcounter.view.CartCounterActionView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AfterSelectionCategoryFragment : BaseFragment<FragmentAfterSelectionCategoryBinding, AfterSelectionFragmentViewModal>(){
    private val mHomeViewModel: AfterSelectionFragmentViewModal by activityViewModels()


    lateinit var database: ProductDatabase
    override fun getBindingVariable(): Int = BR.viewModel
    override fun getLayoutId(): Int = R.layout.fragment_after_selection_category
    override fun getViewModel(): AfterSelectionFragmentViewModal = mHomeViewModel
    private lateinit var categorySelectAdapter: ListItemsAfterCategorySelectionAdapter
    override fun getLifeCycleOwner(): LifecycleOwner = this
    private var fragmentHomeViewBinding: FragmentAfterSelectionCategoryBinding? = null

    var con: Context?=null
    override fun onAttach(context: Context) {
        super.onAttach(context)
        con=context
    }

    override fun onDetach() {
        super.onDetach()
       // con=null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentHomeViewBinding = getViewDataBinding()
        database= ProductDatabase.getInstance(con!!)
        //mHomeViewModel.getProductsAfterSelection(Integer.parseInt(mHomeViewModel.stringvalue.value))
        val parentjob= mHomeViewModel.viewModelScope.launch(Dispatchers.Main){

            val catergories=async { mHomeViewModel._postStateflowAfterSelection.collect{
                when(it){
                    is ApiState.Loading -> {
                    //    fragmentHomeViewBinding!!. shimmerCategoryListItems.isVisible=true

                    }
                    is ApiState.Failure -> {
                        fragmentHomeViewBinding!!.shimmerCategoryListItems.isVisible=true
                        // binding.shimmerCategoryListItems.shimmerCategory.isVisible = true
                        Log.d("main", "onCreate: ${it.msg}")
                    }
                    is ApiState.SuccessCategories<*> -> {
                        val response =
                            (it.data as cateogryAfterSelectionModal)
                        AfterCategoryList(view,response.meals)

                        fragmentHomeViewBinding!!. shimmerCategoryListItems.isVisible=false
                       // categorySelectAdapter.setData(it.data.meals)
//                        categorySelectAdapter.notifyDataSetChanged()
                    }
                    is ApiState.Empty -> {

                    }
                }

                }

            }
            catergories.await()

            }


            print("one api get called")
        parentjob.invokeOnCompletion { print("api call completion") }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        }

    private fun AfterCategoryList(view:View,listAccountData: List<cateogryAfterSelectionModal.cateogryAfterSelectionModal1>) {
        Log.d("sizeofcategory",listAccountData.size.toString())

        AppUtils.setUpRecyclerItemLayout(con!!, fragmentHomeViewBinding!!.recyclerCategory)
        categorySelectAdapter = ListItemsAfterCategorySelectionAdapter(con!!,database,listAccountData,
            object : ListItemsAfterCategorySelectionAdapter.onclick {
                override fun itemclicked(item: cateogryAfterSelectionModal.cateogryAfterSelectionModal1) {
                 //   Navigation.findNavController(view).navigate(R.id.action_afterselectionfragment_to_cartfragment);

                    lifecycle.coroutineScope.launch {
                        // database.contactDao().getProductBasedId(1212).observe(this@AfterCategorySelectionActivity,{})
                        val intger: Int = database.contactDao().getProductBasedIdCount(item.getidMeal().toString())
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
                                .updateCartItem(intger + 1,  item.getidMeal().toString())
                        }

                        Log.d("countis",database.contactDao().getProductBasedIdCount( item.getidMeal().toString()).toString())


                    }
                    database.contactDao().getTotalProductItems()
                        .observe(requireActivity(), {
                        //    if (it != null)
                             //   actionView.count = it.toString().toIntOrNull()!!
                        })
//                    val dialog = BottomSheetDialog(this@AfterCategorySelectionActivity)
//
//                    val inflater = LayoutInflater.from(this@AfterCategorySelectionActivity)
//                    val view = inflater.inflate(R.layout.bottom_sheet_dialog, null)
//
//                    view.findViewById<TextView>(R.id.idTVCourseName).setText(item.getstrname())
//
//                    Picasso.get().load(item.getstrimage()).placeholder(R.drawable.clock_my_time_in_button)
//                            .into(view.findViewById<ImageView>(R.id.idImageView))

//                    view.findViewById<Button>(R.id.idBtnProceed).setOnClickListener {
//                        database.contactDao().getAllAddress()
//                            .observe(this@AfterCategorySelectionActivity, {
//                                if ((it != null) && (it.size > 0)) {
//                                    val intent = Intent(
//                                        this@AfterCategorySelectionActivity,
//                                        ProceedToAddress::class.java
//                                    );
//                                    this@AfterCategorySelectionActivity.startActivity(intent);
//
//                                } else {
//                                    val intent = Intent(
//                                        this@AfterCategorySelectionActivity,
//                                        AddNewAddressActivity::class.java
//                                    );
//                                    this@AfterCategorySelectionActivity.startActivity(intent);
//                                }
//                            })
//                    }
//                    //  dialog.setCancelable(false)
//                    dialog.setContentView(view)
//                    dialog.show()


                }

                override fun ClickedPlusButton(cartitems: cateogryAfterSelectionModal.cateogryAfterSelectionModal1) {
                    lifecycle.coroutineScope.launch {
                        // database.contactDao().getProductBasedId(1212).observe(this@AfterCategorySelectionActivity,{})
                        val intger: Int = database.contactDao().getProductBasedIdCount(cartitems.getidMeal().toString())
                        Log.d("jdjdjd",intger.toString());
                        if (intger == 0) {
                            database.contactDao()
                                .insertCartItem(
                                    CartItems(
                                        cartitems.getidMeal().toString(),
                                        cartitems.getstrimage(),
                                        intger + 1,
                                        Integer.parseInt(cartitems.getsale_price()),
                                        cartitems.getstrname()
                                    )
                                )

                        } else if (intger >= 1) {

                            database.contactDao()
                                .updateCartItem(intger + 1,  cartitems.getidMeal().toString())
                        }

                        Log.d("countis",database.contactDao().getProductBasedIdCount( cartitems.getidMeal().toString()).toString())


                    }
                    database.contactDao().getTotalProductItems()
                        .observe(requireActivity(), {
                            //if (it != null)
                             //   actionView.count = it.toString().toIntOrNull()!!
                        })
                }

                override fun ClickedMinusButton(cartitems: cateogryAfterSelectionModal.cateogryAfterSelectionModal1) {
                    lifecycle.coroutineScope.launch {
                        // database.contactDao().getProductBasedId(1212).observe(this@AfterCategorySelectionActivity,{})

                        var intger: Int = database.contactDao().getProductBasedIdCount(cartitems.getidMeal().toString())

                        intger=intger - 1

                        if(intger>=1) {
                            database.contactDao()
                                .updateCartItem(intger, cartitems.getidMeal().toString())




                        }
                        else if(intger==0)
                        {
                            database.contactDao()
                                .deleteCartItem(cartitems.getidMeal().toString())
//                            linearlayout.visibility = View.GONE
//                            table_invoice.visibility= View.GONE
//                            linearLayoutButton.visibility = View.GONE
//                            emptyLayout.visibility = View.VISIBLE


                        }
                    }

                }


            })
        //  fragmentHomeViewBinding!!.recyclerCategory.addItemDecoration(SpaceItemDecorator())

        fragmentHomeViewBinding!!.recyclerCategory.adapter = categorySelectAdapter
    }





}