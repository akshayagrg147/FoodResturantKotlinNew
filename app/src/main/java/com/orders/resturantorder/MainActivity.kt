package com.orders.resturantorder


import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import android.widget.PopupMenu
import android.widget.SearchView
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.meetSuccess.Database.ProductDatabase
import com.orders.resturantorder.Base.BaseActivity
import com.orders.resturantorder.Fragments.DashBoardCategories
import com.orders.resturantorder.databinding.ActivityMainBinding

import com.orders.resturantorder.viewmodel.Mainactivityviewmodel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding,Mainactivityviewmodel>(){
    //,DashBoardCategories.passingclick


    private lateinit var mActivityMainBinding: ActivityMainBinding

    private val mainViewModel: Mainactivityviewmodel by viewModels()

    override fun getBindingVariable(): Int = 2

    override fun getLayoutId(): Int = R.layout.activity_main

    override fun getViewModel(): Mainactivityviewmodel = mainViewModel


   private lateinit var navController: NavController
   private lateinit var  view:SearchView
   lateinit var database: ProductDatabase
//    lateinit  var passingSearchObject: passingSearchText
//    private var mainViewModel: MainActivityViewModel? = null
//    private lateinit var   bottomview: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mActivityMainBinding = getViewDataBinding()


        database= ProductDatabase.getInstance(this@MainActivity)


        navController = findNavController(R.id.my_nav_host_fragment)
       setupActionBarWithNavController(navController)
        supportActionBar!!.hide();
        setupSmoothBottomMenu()

      //  mainViewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)

    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.menu, menu)
        val searchItem = menu.findItem(R.id.appSearchBar)
        if (searchItem != null) {
             view = searchItem.actionView as SearchView
            view.maxWidth = Integer.MAX_VALUE;
            view.setOnCloseListener(object:SearchView.OnCloseListener{
                override fun onClose(): Boolean {
                    mainViewModel?.passingSearchClose(true)
                    supportActionBar!!.hide();
                    mActivityMainBinding.bottombar.visibility=View.VISIBLE
                    return true

                }

            })
            database.contactDao().getTotalProductItems().observe(this@MainActivity
            ) {
                if ((it != null) && (it > 0)) {
                    mActivityMainBinding.bottombar.getOrCreateBadge(R.id.CartFragment).number = it


                } else
                    mActivityMainBinding.bottombar.getOrCreateBadge(R.id.CartFragment).number = 0


            }
            mainViewModel?.getItemClicked()?.observe(this, Observer {
                if(it) {
                    supportActionBar!!.hide();
                    mActivityMainBinding.bottombar.visibility=View.VISIBLE
                }

            })


            view.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String): Boolean {

//                if (list.contains(query)) {
////                    searchAdapter.filter.filter(query)
////                    listView.visibility=View.VISIBLE
//                } else {
//                    Toast.makeText(this@MainActivity, "No Match found", Toast.LENGTH_LONG).show()
//                }
                    return false
                }
                override fun onQueryTextChange(newText: String): Boolean {


                    Log.d("calling","callingdone")
                    mainViewModel?.passingValue(newText)


                   // passingSearchObject.sendingText(newText)

                    return false
                }
            })
        }
        return true
    }

    override fun onBackPressed() {
       // super.onBackPressed()
        mainViewModel?.passingSearchClose(true)
        supportActionBar!!.hide();
        mActivityMainBinding.bottombar.visibility=View.VISIBLE
    }


    private fun setupSmoothBottomMenu() {
        val popupMenu = PopupMenu(this, null)
        popupMenu.inflate(R.menu.bottom_nav_menu)
        mActivityMainBinding.bottombar.setupWithNavController( navController)





    }


    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
    interface passingSearchText{
        public fun sendingText(item: String)
    }

//    override fun passingvalue(boolean: Boolean) {
//     if(boolean)
//     {
//         supportActionBar!!.show();
//         supportActionBar!!.setDisplayShowTitleEnabled(false);
//         view.isIconifiedByDefault = true;
//         view.isFocusable = true;
//         view.isIconified = false;
//         mActivityMainBinding.bottombar.visibility=View.GONE
//         view.requestFocusFromTouch();
//
//     }
//    }
}