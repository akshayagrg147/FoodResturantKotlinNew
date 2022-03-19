package com.orders.resturantorder.Activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.coroutineScope
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.meetSuccess.Database.CartItems
import com.meetSuccess.Database.ProductDatabase
import com.meetSuccess.FoodResturant.Model.Meals
import com.meetSuccess.FoodResturant.Model.MobileNumberExistCheck
import com.meetSuccess.FoodResturant.Model.cateogryAfterSelectionModal
import com.meetSuccess.FoodResturant.Util.ApiState
import com.orders.resturantorder.Base.BaseActivity
import com.orders.resturantorder.R
import com.orders.resturantorder.adapter.ListItemsAfterCategorySelectionAdapter
import com.orders.resturantorder.databinding.ActivityAfterCategorySelectionBinding
import com.orders.resturantorder.databinding.ActivityMainBinding
import com.orders.resturantorder.viewmodel.AfterCategorySelectionViewModel
import com.orders.resturantorder.viewmodel.Mainactivityviewmodel
import com.rowland.cartcounter.view.CartCounterActionView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_after_category_selection.*
import kotlinx.android.synthetic.main.activity_after_category_selection.recyclerCategory
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlin.collections.ArrayList

@AndroidEntryPoint
class AfterCategorySelectionActivity :  BaseActivity<ActivityAfterCategorySelectionBinding, AfterCategorySelectionViewModel>(){
    //,DashBoardCategories.passingclick


    private lateinit var mActivityMainBinding: ActivityAfterCategorySelectionBinding

    private val mainViewModel: AfterCategorySelectionViewModel by viewModels()

    override fun getBindingVariable(): Int = 2

    override fun getLayoutId(): Int = R.layout.activity_after_category_selection

    override fun getViewModel(): AfterCategorySelectionViewModel = mainViewModel
    lateinit var database: ProductDatabase
    private lateinit var categorySelectAdapter: ListItemsAfterCategorySelectionAdapter
    private  lateinit var actionView:CartCounterActionView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



//        val categories:ArrayList<String> =ArrayList<String>()
//        categories.add("hiijj")
//        val dataAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, categories)
//        categoryAdapter =
//            CustomYearSpinnerAdaptor(
//                this@AfterCategorySelectionActivity,
//                android.R.layout.simple_spinner_item,
//                categories
//            )
//        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
//       spinnerStatus.setAdapter(dataAdapter)

       database= ProductDatabase.getInstance(this@AfterCategorySelectionActivity)

        val actionBar: ActionBar? = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.setHomeButtonEnabled(true)
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)


        initRecyclerview()
        //  initRecyclerviewMeals()

        var stringId: String? = intent.getStringExtra("categoryId")
        if(stringId!=null)
            mainViewModel.getProductsAfterSelection(Integer.parseInt(stringId))
        // mainViewModel.getLatestMeals()
        val parentjob= lifecycleScope.launchWhenStarted {

            val catergories=async { mainViewModel._postStateflowAfterSelection.collect { it->
                when(it){
                    is ApiState.Loading -> {
                        shimmerCategoryListItems.isVisible=true

                    }
                    is ApiState.Failure -> {
                        Log.d("dsfddd", "dsfsd1");
                        shimmerCategoryListItems.isVisible=true
                       // binding.shimmerCategoryListItems.shimmerCategory.isVisible = true
                        Log.d("main", "onCreate: ${it.msg}")
                    }
                    is ApiState.SuccessCategories<*> -> {
                        val response =
                            (it.data as cateogryAfterSelectionModal)

                        shimmerCategoryListItems.isVisible=false
                       categorySelectAdapter.setData(it.data.meals)
//                        categorySelectAdapter.notifyDataSetChanged()
                    }
                    is ApiState.Empty -> {

                    }
                }
            } }
            catergories.await()

            print("one api get called")

        }
        parentjob.invokeOnCompletion { print("api call completion") }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {


        return when(item.itemId){
            R.id.action_addcart -> {
                val intent = Intent(this, CartItemss::class.java);
                this.startActivity(intent);
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
    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        val itemData = menu?.findItem(R.id.action_addcart)
        actionView = itemData?.actionView as CartCounterActionView
        actionView.setItemData(menu, itemData)
        database.contactDao().getTotalProductItems().observe(this@AfterCategorySelectionActivity) {
            if (it != null)
                actionView.count = it.toString().toIntOrNull()!!
            else
                actionView.count = 0
        }
        actionView.setCount(0)
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.cartmenu, menu)
        return super.onCreateOptionsMenu(menu)
    }




    private  fun initRecyclerview() {
        categorySelectAdapter= ListItemsAfterCategorySelectionAdapter(baseContext, database, ArrayList(),
            object : ListItemsAfterCategorySelectionAdapter.onclick {
                override fun itemclicked(item: cateogryAfterSelectionModal.cateogryAfterSelectionModal1) {


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
                        .observe(this@AfterCategorySelectionActivity, {
                            if (it != null)
                                actionView.count = it.toString().toIntOrNull()!!
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
//
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
                        .observe(this@AfterCategorySelectionActivity, {
                            if (it != null)
                                actionView.count = it.toString().toIntOrNull()!!
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

        recyclerCategory.apply {
            setHasFixedSize(true)
            layoutManager= LinearLayoutManager(this@AfterCategorySelectionActivity)
            adapter=categorySelectAdapter
        }
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}