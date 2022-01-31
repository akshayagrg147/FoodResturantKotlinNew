package com.orders.ResturantOrder.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.widget.AppCompatButton
import androidx.lifecycle.coroutineScope
import com.meetSuccess.Database.AddressItems
import com.meetSuccess.Database.ProductDatabase
import com.orders.ResturantOrder.R
import kotlinx.coroutines.launch

class AddNewAddressActivity : AppCompatActivity() {
    lateinit var database: ProductDatabase
    lateinit var name_editText:EditText
    lateinit var phoneno_editText:EditText
    lateinit var pincode_editText:EditText
    lateinit var address1_editText:EditText
    lateinit var address2_editText:EditText
    lateinit var lanMark:EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_new_address)
        name_editText=findViewById(R.id.name_editText)
        phoneno_editText=findViewById(R.id.phoneno_editText)
        pincode_editText=findViewById(R.id.pincode_editText)
        address1_editText=findViewById(R.id.address1_editText)
        address2_editText=findViewById(R.id.address2_editText)
        lanMark=findViewById(R.id.lanMark)


        database= ProductDatabase.getInstance(this@AddNewAddressActivity)

        val actionBar: ActionBar? = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.setHomeButtonEnabled(true)
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)


     findViewById<AppCompatButton>(R.id.address_update_button).setOnClickListener {
            lifecycle.coroutineScope.launch {
                if((name_editText.getText()
                        .isEmpty()))
                {
                    Toast.makeText(
                        this@AddNewAddressActivity,
                        "Name should not be null",
                        Toast.LENGTH_SHORT
                    ).show()
                    return@launch
                }
                else if((phoneno_editText.getText()
                        .isEmpty()))
                {
                    Toast.makeText(
                        this@AddNewAddressActivity,
                        "address1 should not be null",
                        Toast.LENGTH_SHORT
                    ).show()
                    return@launch
                }
                else  if((pincode_editText.getText()
                        .isEmpty()))
                {
                    Toast.makeText(
                        this@AddNewAddressActivity,
                        "address2 should not be null",
                        Toast.LENGTH_SHORT
                    ).show()
                    return@launch
                }
                else  if((address1_editText.getText()
                        .isEmpty()))
                {
                    Toast.makeText(
                        this@AddNewAddressActivity,
                        "LandMark should not be null",
                        Toast.LENGTH_SHORT
                    ).show()
                    return@launch
                }
                else  if((address2_editText.getText()
                        .isEmpty()))
                {
                    Toast.makeText(
                        this@AddNewAddressActivity,
                        "Phone number should not be null",
                        Toast.LENGTH_SHORT
                    ).show()
                    return@launch
                }
                else  if((lanMark.getText()
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