package com.orders.resturantorder.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.meetSuccess.Database.AddressItems
import com.meetSuccess.Database.ProductDatabase
import com.orders.resturantorder.R
import com.orders.resturantorder.adapter.AddressItemssAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_proceed_to_address2.*
@AndroidEntryPoint
class ProceedToAddress :  AppCompatActivity(),AddressItemssAdapter.AddressChosen{
    lateinit var database: ProductDatabase
    lateinit var ListAddress: List<AddressItems>
    lateinit var addnewaddress:TextView
    lateinit var confirmorder:TextView


    private lateinit var categorySelectAdapter: AddressItemssAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_proceed_to_address2)
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true);
        database= ProductDatabase.getInstance(this@ProceedToAddress)

        addnewaddress=findViewById(R.id.addnewaddress)
        confirmorder=findViewById(R.id.confirmorder)


        val actionBar: ActionBar? = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.setHomeButtonEnabled(true)
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)

       addnewaddress.setOnClickListener {
            val intent = Intent(this,AddNewAddressActivity::class.java);
            this.startActivity(intent);
        }
        confirmorder.setOnClickListener{
//            val intent = Intent(this,OrderPlaced::class.java);
//            this.startActivity(intent);
//            finish()

        }
        // initRecyclerview()


        database.contactDao().getAllAddress().observe(this@ProceedToAddress,{
            ListAddress=it
            categorySelectAdapter= AddressItemssAdapter(it,this)

          recyclerCategory.isVisible = true
            //  binding.shimmerCategoryListItems.shimmerCategory.isVisible = false

          recyclerCategory.apply {
                setHasFixedSize(true)
                layoutManager= LinearLayoutManager(this@ProceedToAddress)
                adapter=categorySelectAdapter
            }
        })


    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun itemChossen(int: Int) {
        Toast.makeText(this@ProceedToAddress,ListAddress.get(int).Address1, Toast.LENGTH_SHORT).show()
    }

}