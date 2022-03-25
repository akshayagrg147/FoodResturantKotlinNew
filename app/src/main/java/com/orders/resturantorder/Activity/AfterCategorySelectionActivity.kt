package com.orders.resturantorder.Activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.test.core.app.ActivityScenario.launch
import com.meetSuccess.Database.ProductDatabase
import com.meetSuccess.FoodResturant.Util.ApiState
import com.orders.resturantorder.Base.BaseActivity
import com.orders.resturantorder.Fragments.DashBoardCategories
import com.orders.resturantorder.R
import com.orders.resturantorder.adapter.ListItemsAfterCategorySelectionAdapter
import com.orders.resturantorder.databinding.ActivityAfterCategorySelectionBinding
import com.orders.resturantorder.databinding.FragmentAfterSelectionCategoryBinding
import com.orders.resturantorder.viewmodel.AfterCategorySelectionViewModel
import com.orders.resturantorder.viewmodel.AfterSelectionFragmentViewModal
import com.rowland.cartcounter.view.CartCounterActionView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch


@AndroidEntryPoint
class AfterCategorySelectionActivity :  BaseActivity<ActivityAfterCategorySelectionBinding, AfterSelectionFragmentViewModal>(){
    //,DashBoardCategories.passingclick
    private  lateinit var actionView:CartCounterActionView

    private val mainViewModel: AfterSelectionFragmentViewModal by viewModels()

    override fun getBindingVariable(): Int = 2

    override fun getLayoutId(): Int = R.layout.activity_after_category_selection

    override fun getViewModel(): AfterSelectionFragmentViewModal = mainViewModel
    lateinit var database: ProductDatabase
    private lateinit var navController: NavController


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        database= ProductDatabase.getInstance(this@AfterCategorySelectionActivity)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.my_nav) as NavHostFragment
        navController = navHostFragment.navController
        setupActionBarWithNavController(navController)

      //  supportActionBar!!.hide();

        val stringId: String? = intent.getStringExtra("categoryId")
        if(stringId!=null)
            mainViewModel.viewModelScope.launch(Dispatchers.Main){
               mainViewModel.sendData(stringId)
            }




    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp()||super.onSupportNavigateUp()
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


}