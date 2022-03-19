package com.orders.resturantorder.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBar
import androidx.appcompat.widget.AppCompatButton
import androidx.lifecycle.coroutineScope
import com.meetSuccess.Database.AddressItems
import com.meetSuccess.Database.ProductDatabase
import com.orders.resturantorder.Base.BaseActivity
import com.orders.resturantorder.R
import com.orders.resturantorder.databinding.ActivityAddNewAddressBinding
import com.orders.resturantorder.databinding.ActivityMainBinding
import com.orders.resturantorder.viewmodel.Mainactivityviewmodel
import kotlinx.coroutines.launch

class AddNewAddressActivity  : BaseActivity<ActivityAddNewAddressBinding, AddNewAddressViewModal>(){
    //,DashBoardCategories.passingclick


    private lateinit var mActivityMainBinding: ActivityAddNewAddressBinding

    private val mainViewModel: AddNewAddressViewModal by viewModels()

    override fun getBindingVariable(): Int = 2

    override fun getLayoutId(): Int = R.layout.activity_add_new_address

    override fun getViewModel(): AddNewAddressViewModal = mainViewModel
    lateinit var database: ProductDatabase
    lateinit var name_editText:EditText
    lateinit var phoneno_editText:EditText
    lateinit var pincode_editText:EditText
    lateinit var address1_editText:EditText
    lateinit var address2_editText:EditText
    lateinit var lanMark:EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mActivityMainBinding = getViewDataBinding()

        database= ProductDatabase.getInstance(this@AddNewAddressActivity)

        val actionBar: ActionBar? = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.setHomeButtonEnabled(true)
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)


        mActivityMainBinding.addressUpdateButton.setOnClickListener {
            lifecycle.coroutineScope.launch {
                if((mActivityMainBinding.nameEditText.getText()
                        .isEmpty()))
                {
                    Toast.makeText(
                        this@AddNewAddressActivity,
                        "Name should not be null",
                        Toast.LENGTH_SHORT
                    ).show()
                    return@launch
                }
                else if((mActivityMainBinding.phonenoEditText.getText()
                        .isEmpty()))
                {
                    Toast.makeText(
                        this@AddNewAddressActivity,
                        "address1 should not be null",
                        Toast.LENGTH_SHORT
                    ).show()
                    return@launch
                }
                else  if((mActivityMainBinding.pincodeEditText.getText()
                        .isEmpty()))
                {
                    Toast.makeText(
                        this@AddNewAddressActivity,
                        "address2 should not be null",
                        Toast.LENGTH_SHORT
                    ).show()
                    return@launch
                }
                else  if((mActivityMainBinding.address1EditText.getText()
                        .isEmpty()))
                {
                    Toast.makeText(
                        this@AddNewAddressActivity,
                        "LandMark should not be null",
                        Toast.LENGTH_SHORT
                    ).show()
                    return@launch
                }
                else  if((mActivityMainBinding.address2EditText.getText()
                        .isEmpty()))
                {
                    Toast.makeText(
                        this@AddNewAddressActivity,
                        "Phone number should not be null",
                        Toast.LENGTH_SHORT
                    ).show()
                    return@launch
                }
                else  if((mActivityMainBinding.lanMark.getText()
                        .isEmpty()))
                {
                    Toast.makeText(
                        this@AddNewAddressActivity,
                        "Pincode should not be null",
                        Toast.LENGTH_SHORT
                    ).show()
                    return@launch
                }
                else
                {


                    database.contactDao().insertAddressItem(
                        AddressItems(
                            name_editText.getText().toString(),
                            phoneno_editText.getText().toString(),
                            Integer.parseInt(pincode_editText.getText().toString()),
                            address1_editText.text.toString(),
                            address2_editText.text.toString(),
                            lanMark.text.toString()
                        )
                    )
                    Toast.makeText(
                        this@AddNewAddressActivity,
                        "Address Saved Successfully",
                        Toast.LENGTH_SHORT
                    ).show()
                    val intent = Intent(this@AddNewAddressActivity, ProceedToAddress::class.java);
                    this@AddNewAddressActivity.startActivity(intent);
//

                }


            }

        }
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}