package com.orders.resturantorder.Fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.coroutineScope
import androidx.lifecycle.viewModelScope
import androidx.navigation.Navigation
import com.meetSuccess.Database.CartItems
import com.meetSuccess.Database.ProductDatabase
import com.meetSuccess.FoodResturant.Model.cateogryAfterSelectionModal
import com.meetSuccess.FoodResturant.Util.ApiState
import com.orders.resturantorder.Util.AppUtils
import com.orders.resturantorder.R
import com.orders.resturantorder.adapter.ListItemsAfterCategorySelectionAdapter
import com.orders.resturantorder.databinding.FragmentAfterSelectionCategoryBinding
import com.orders.resturantorder.model.BR
import com.orders.resturantorder.viewmodel.AfterSelectionFragmentViewModal
import com.orders.resturantorder.viewmodel.BaseFragment
import com.rowland.cartcounter.view.CartCounterActionView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


@AndroidEntryPoint
class AfterSelectionCategoryFragment : BaseFragment<FragmentAfterSelectionCategoryBinding, AfterSelectionFragmentViewModal>(){
    private val mHomeViewModel: AfterSelectionFragmentViewModal by activityViewModels()

    private  lateinit var actionView:CartCounterActionView
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
        setHasOptionsMenu(true)
        fragmentHomeViewBinding = getViewDataBinding()
        database= ProductDatabase.getInstance(con!!)
        (activity as AppCompatActivity?)!!.supportActionBar!!.show()
        if (arguments != null) {
            val str: String = requireArguments().getString("categoryId").toString()
            mHomeViewModel.viewModelScope.launch(Dispatchers.Main){
                mHomeViewModel.sendData(str)
            }
            Toast.makeText(context, str, Toast.LENGTH_SHORT).show()
        }
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
                        val intger: Int = database.contactDao().getProductBasedIdCount(item.idMeal.toString())
                        if (intger == 0) {
                            database.contactDao()
                                .insertCartItem(
                                    CartItems(
                                        item.idMeal.toString(),
                                        item.image,
                                        intger + 1,
                                        Integer.parseInt(item.sale_price),
                                        item.name
                                    )
                                )

                        } else if (intger >= 1) {

                            database.contactDao()
                                .updateCartItem(intger + 1,  item.idMeal.toString())
                        }

                        Log.d("countis",database.contactDao().getProductBasedIdCount( item.idMeal.toString()).toString())


                    }
                    database.contactDao().getTotalProductItems()
                        .observe(requireActivity()) {
                            //    if (it != null)
                            //   actionView.count = it.toString().toIntOrNull()!!
                        }
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
                        val intger: Int = database.contactDao().getProductBasedIdCount(cartitems.idMeal.toString())
                        Log.d("jdjdjd",intger.toString());
                        if (intger == 0) {
                            database.contactDao()
                                .insertCartItem(
                                    CartItems(
                                        cartitems.idMeal.toString(),
                                        cartitems.image,
                                        intger + 1,
                                        Integer.parseInt(cartitems.sale_price),
                                        cartitems.name
                                    )
                                )

                        } else if (intger >= 1) {

                            database.contactDao()
                                .updateCartItem(intger + 1,  cartitems.idMeal.toString())
                        }

                        Log.d("countis",database.contactDao().getProductBasedIdCount( cartitems.idMeal.toString()).toString())


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

                        var intger: Int = database.contactDao().getProductBasedIdCount(cartitems.idMeal.toString())

                        intger=intger - 1

                        if(intger>=1) {
                            database.contactDao()
                                .updateCartItem(intger, cartitems.idMeal.toString())




                        }
                        else if(intger==0)
                        {
                            database.contactDao()
                                .deleteCartItem(cartitems.idMeal.toString())
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

    override fun onPrepareOptionsMenu(menu: Menu) {
        val itemData = menu?.findItem(R.id.action_addcart)
        actionView = itemData?.actionView as CartCounterActionView
        actionView.setItemData(menu, itemData)
        database.contactDao().getTotalProductItems().observe(requireActivity()) {
            if (it != null)
                actionView.count = it.toString().toIntOrNull()!!
            else
                actionView.count = 0
        }
        actionView.setCount(0)
        super.onPrepareOptionsMenu(menu)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.cartmenu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {


        return when(item.itemId){
            R.id.action_addcart -> {
                Navigation.findNavController(view!!).navigate(R.id.action_afterSelectionCategoryFragment_to_cartFragment);

//                val intent = Intent(this, CartItemss::class.java);
//                this.startActivity(intent);
                true

            }
            R.id.byPrice-> {

//                Collections.sort(categorySelectAdapter.getData(), object : Comparator() {
//                    fun compare(o1: Any, o2: Any): Int {
//                        val p1: cateogryAfterSelectionModal.cateogryAfterSelectionModal1 = o1 as cateogryAfterSelectionModal.cateogryAfterSelectionModal1
//                        val p2: cateogryAfterSelectionModal.cateogryAfterSelectionModal1 = o2 as cateogryAfterSelectionModal.cateogryAfterSelectionModal1
//                        return p1.getdprice()!!.compareTo(p2.getdprice()?)
//                    }
//                })


                true

            }
            else -> super.onOptionsItemSelected(item)
        }
    }





}