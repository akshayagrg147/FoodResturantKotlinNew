package com.orders.ResturantOrder


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import android.widget.PopupMenu
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.badge.BadgeDrawable
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.meetSuccess.Database.ProductDatabase
import com.orders.ResturantOrder.Activity.AddNewAddressActivity
import com.orders.ResturantOrder.Activity.ProceedToAddress
import com.orders.ResturantOrder.Fragments.DashBoardCategories
import com.orders.ResturantOrder.viewmodel.MainActivityViewModel
import com.orders.ResturantOrder.viewmodel.MobileNumberIntegrationViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import me.ibrahimsn.lib.SmoothBottomBar


@AndroidEntryPoint
class MainActivity : AppCompatActivity(),DashBoardCategories.passingclick{

    private lateinit var navController: NavController
    private lateinit var  view:SearchView
    lateinit var database: ProductDatabase
    lateinit  var passingSearchObject: passingSearchText
    private var mainViewModel: MainActivityViewModel? = null
    private lateinit var   bottomview: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        database= ProductDatabase.getInstance(this@MainActivity)


        navController = findNavController(R.id.my_nav_host_fragment)
       setupActionBarWithNavController(navController)
        supportActionBar!!.hide();
        setupSmoothBottomMenu()

        mainViewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)

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
                    bottomview.visibility=View.VISIBLE
                    return true

                }

            })
            database.contactDao().getTotalProductItems().observe(this@MainActivity
            ) {
                if ((it != null) && (it > 0)) {
                    bottomview.getOrCreateBadge(R.id.CartFragment).number = it


                } else
                    bottomview.getOrCreateBadge(R.id.CartFragment).number = 0


            }
            mainViewModel?.getItemClicked()?.observe(this, Observer {
                if(it) {
                    supportActionBar!!.hide();
                    bottomview.visibility=View.VISIBLE
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







    private fun setupSmoothBottomMenu() {
        val popupMenu = PopupMenu(this, null)
        popupMenu.inflate(R.menu.bottom_nav_menu)
         bottomview=findViewById<BottomNavigationView>(R.id.bottomBar)
       val menu = popupMenu.menu
        bottomview.setupWithNavController( navController)





    }


    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
    interface passingSearchText{
        public fun sendingText(item: String)
    }

    override fun passingvalue(boolean: Boolean) {
     if(boolean)
     {
         supportActionBar!!.show();
         supportActionBar!!.setDisplayShowTitleEnabled(false);
         view.isIconifiedByDefault = true;
         view.isFocusable = true;
         view.isIconified = false;
         bottomview.visibility=View.GONE
         view.requestFocusFromTouch();

     }
    }
}