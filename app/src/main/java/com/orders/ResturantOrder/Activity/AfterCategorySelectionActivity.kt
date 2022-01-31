package com.orders.ResturantOrder.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import androidx.lifecycle.coroutineScope
import android.view.MenuItem
import kotlinx.coroutines.flow.collect
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.meetSuccess.Database.CartItems
import com.meetSuccess.Database.ProductDatabase

import com.meetSuccess.FoodResturant.Model.Categories
import com.meetSuccess.FoodResturant.Util.ApiState
import com.orders.ResturantOrder.R
import com.orders.ResturantOrder.adapter.ListItemsAfterCategorySelectionAdapter
import com.orders.ResturantOrder.viewmodel.AfterCategorySelectionViewModel
import com.rowland.cartcounter.view.CartCounterActionView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_after_category_selection.*
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AfterCategorySelectionActivity : AppCompatActivity() {
    lateinit var database: ProductDatabase
    private lateinit var categorySelectAdapter: ListItemsAfterCategorySelectionAdapter
    private  lateinit var actionView:CartCounterActionView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_after_category_selection)

        val afterCategorySelectionViewModel  = ViewModelProvider(this).get(AfterCategorySelectionViewModel::class.java)


        val categories:ArrayList<String> =ArrayList<String>()
        categories.add("hii")
        val dataAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, categories)
//        categoryAdapter =
//            CustomYearSpinnerAdaptor(
//                this@AfterCategorySelectionActivity,
//                android.R.layout.simple_spinner_item,
//                categories
//            )
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
       spinnerStatus.setAdapter(dataAdapter)

       database= ProductDatabase.getInstance(this@AfterCategorySelectionActivity)

        val actionBar: ActionBar? = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.setHomeButtonEnabled(true)
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)


        initRecyclerview()
        //  initRecyclerviewMeals()


        afterCategorySelectionViewModel.getPost()
        // mainViewModel.getLatestMeals()
        val parentjob= lifecycleScope.launchWhenStarted {

            val catergories=async { afterCategorySelectionViewModel._postStateFlow.collect { it->
                when(it){
                    is ApiState.Loading -> {
                     //recyclerCategory.isVisible = false
                     //  shimmerCategoryListItems.shimmerCategory.isVisible = true
                    }
                    is ApiState.Failure -> {
                       // binding.recyclerCategory.isVisible = false
                       // binding.shimmerCategoryListItems.shimmerCategory.isVisible = true
                        Log.d("main", "onCreate: ${it.msg}")
                    }
                    is ApiState.SuccessCategories -> {
                        Log.d("dsfddd", "dsfsd");
                        //  binding.shimmerCategory.shimmerCategory .isVisible= true


                       // binding.recyclerCategory.isVisible = true
                       // binding.shimmerCategoryListItems.shimmerCategory.isVisible = false
                        //  it.data.categories.filter{it.getIdCategory().contentEquals("")}
                        categorySelectAdapter.setData(it.data.categories)
                        // categorySelectAdapter.notifyDataSetChanged()
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

        //return super.onOptionsItemSelected(item)
        return when(item.itemId){
            R.id.action_addcart -> {
                val intent = Intent(this, CartItemss::class.java);
                this.startActivity(intent);



                true

            }
            else -> super.onOptionsItemSelected(item)
        }
    }
    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        val itemData = menu?.findItem(R.id.action_addcart)
        actionView = itemData?.actionView as CartCounterActionView
        actionView.setItemData(menu, itemData)
        database.contactDao().getTotalProductItems().observe(this@AfterCategorySelectionActivity, {
            if (it != null)
                actionView.count = it.toString().toIntOrNull()!!
            else
                actionView.count = 0
        })
        actionView.setCount(0)
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.cartmenu, menu)
        return super.onCreateOptionsMenu(menu)
    }




    private  fun initRecyclerview() {
        categorySelectAdapter= ListItemsAfterCategorySelectionAdapter(baseContext, ArrayList(),
            object : ListItemsAfterCategorySelectionAdapter.onclick {
                override fun itemclicked(item: Categories.Category) {


                    lifecycle.coroutineScope.launch {
                        // database.contactDao().getProductBasedId(1212).observe(this@AfterCategorySelectionActivity,{})
                        val intger: Int = database.contactDao().getProductBasedIdCount("121212")
                        if (intger == 0) {
                            database.contactDao()
                                .insertCartItem(
                                    CartItems(
                                        "121212",
                                        item.getStrCategoryThumb(),
                                        intger + 1,
                                        12,
                                        "dddd"
                                    )
                                )

                        } else if (intger >= 1) {

                            database.contactDao()
                                .updateCartItem(intger + 1, "121212")
                        }

                        Log.d("countis",database.contactDao().getProductBasedIdCount("121212").toString())


                    }
                    database.contactDao().getTotalProductItems()
                        .observe(this@AfterCategorySelectionActivity, {
                            if (it != null)
                                actionView.count = it.toString().toIntOrNull()!!
                        })
                    val dialog = BottomSheetDialog(this@AfterCategorySelectionActivity)

                    val inflater = LayoutInflater.from(this@AfterCategorySelectionActivity)
                    val view = inflater.inflate(R.layout.bottom_sheet_dialog, null)
                    view.findViewById<TextView>(R.id.idTVCourseName).setText(item.getStrCategory())
                    view.findViewById<Button>(R.id.idBtnProceed).setOnClickListener {
                        database.contactDao().getAllAddress()
                            .observe(this@AfterCategorySelectionActivity, {
                                if ((it != null) && (it.size > 0)) {
//                                    val intent = Intent(
//                                        this@AfterCategorySelectionActivity,
//                                        ProceedToAddress::class.java
//                                    );
//                                    this@AfterCategorySelectionActivity.startActivity(intent);

                                } else {
//                                    val intent = Intent(
//                                        this@AfterCategorySelectionActivity,
//                                        AddNewAddressActivity::class.java
//                                    );
//                                    this@AfterCategorySelectionActivity.startActivity(intent);
                                }
                            })
                    }
                    //  dialog.setCancelable(false)
                    dialog.setContentView(view)
                    dialog.show()


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