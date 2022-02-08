package com.orders.ResturantOrder.Activity

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.tasks.TaskExecutors
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.meetSuccess.FoodResturant.Model.MobileNumberExistCheck
import com.meetSuccess.FoodResturant.Model.MobileNumberPassingData
import com.meetSuccess.FoodResturant.Util.ApiState
import com.orders.ResturantOrder.MainActivity
import com.orders.ResturantOrder.R
import com.orders.ResturantOrder.SharedPreference.sharedpreferenceCommon
import com.orders.ResturantOrder.model.CountryData
import com.orders.ResturantOrder.viewmodel.AfterCategorySelectionViewModel
import com.orders.ResturantOrder.viewmodel.MainActivityViewModel
import com.orders.ResturantOrder.viewmodel.MobileNumberIntegrationViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_mobile_number_integration.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import java.util.EnumSet.of
import java.util.concurrent.TimeUnit


@AndroidEntryPoint
class MobileNumberIntegration : AppCompatActivity() {
    private var verificationId: String? = null
    private var firebaseAuth: FirebaseAuth? = null
    private var progressBar: ProgressBar? = null
    private var editText: EditText? = null
    private var phonenumber: String? = null
    private var mainViewModel: MobileNumberIntegrationViewModel? = null

    //
    private var spinner: Spinner? = null
    private var editText1: EditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mobile_number_integration)
       // getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
       // getActionBar()?.hide();
        mainViewModel = ViewModelProvider(this).get(MobileNumberIntegrationViewModel::class.java)


        firebaseAuth = FirebaseAuth.getInstance()
        progressBar = findViewById(R.id.progressbar)
        editText = findViewById(R.id.editTextCode)
        container_mobileNumber.visibility = View.VISIBLE
        //   profile_info.visibility=View.VISIBLE

        val cal = Calendar.getInstance()

        val dateSetListener = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            cal.set(Calendar.YEAR, year)
            cal.set(Calendar.MONTH, monthOfYear)
            cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            val myFormat = "yyyy/MM/dd" // mention the format you need
            val sdf = SimpleDateFormat(myFormat, Locale.US)
            dob.text = sdf.format(cal.time)

        }
        dob.setOnClickListener {
            DatePickerDialog(this@MobileNumberIntegration, dateSetListener,
                    cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH),
                    cal.get(Calendar.DAY_OF_MONTH)).show()
        }

        GetOtp.setOnClickListener {
            if (name_editText.text.isEmpty()) {
                Toast.makeText(
                        this@MobileNumberIntegration,
                        "Name should not be null",
                        Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener

            } else if (dob.text.isEmpty()) {
                Toast.makeText(
                        this@MobileNumberIntegration,
                        "Name should not be null",
                        Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener

            } else if (name_editText.text.isEmpty()) {
                Toast.makeText(
                        this@MobileNumberIntegration,
                        "Name should not be null",
                        Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener

            }

            val selected: Int = radioGroup1.getCheckedRadioButtonId()
            val gender = findViewById(selected) as RadioButton
            if (gender.isSelected) {
                Toast.makeText(
                        this@MobileNumberIntegration,
                        "Gender should be null",
                        Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }
            val mobileNumberData: MobileNumberPassingData = MobileNumberPassingData(name_editText.text.toString(), gender.text.toString(), phonenumber, dob.text.toString())

            mainViewModel!!.SaveUserInformations(mobileNumberData)

            CoroutineScope(Dispatchers.IO).launch {


                val mobilenumber = async {
                    mainViewModel?.savedinformationCheckFlowstate?.collect { it ->
                        when (it) {
                            is ApiState.Loading -> {

                            }
                            is ApiState.Failure -> {
                                Log.d("callingtest", "falure")

                            }
                            is ApiState.SuccessMobileRespnse -> {
                                Log.d("callingtest", "true")

//                                sharedpreferenceCommon(this@MobileNumberIntegration).setUserData(phonenumber!!)
//
//                                startActivity(Intent(this@MobileNumberIntegration, MainActivity::class.java))
//

                            }
                            is ApiState.Empty -> {

                            }
                        }
                    }
                }
            }


        }

        btnSignIn.setOnClickListener {

            val code = editText!!.text.toString()
            if (code.isEmpty() || code.length < 6) {
                Toast.makeText(this, "Enter Code", Toast.LENGTH_SHORT).show()
            }
            progressBar!!.visibility = View.VISIBLE
            btnSignIn.visibility=View.GONE
            verifyCode(code)
        }



        spinner = findViewById(R.id.spinnerCountries)
        editText1 = findViewById(R.id.inputMbl)

        spinner!!.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, CountryData.countryNames)

        btnCont.setOnClickListener {
            val code = CountryData.countryAreaCodes[spinner!!.selectedItemPosition]
            val number = editText1!!.text.toString()

            if (number.isEmpty() || number.length < 10) {
                Toast.makeText(this, "Invalid Number", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            findViewById<RelativeLayout>(R.id.container_mobileNumber).visibility = View.GONE
            findViewById<RelativeLayout>(R.id.waitingsms).visibility = View.VISIBLE

            val phnum = number
            phonenumber =  phnum
            sendCode("+91"+phonenumber!!)


        }


    }

    private fun sendCode(number: String) {

        PhoneAuthProvider.getInstance()
                .verifyPhoneNumber(
                        number,
                        60,
                        TimeUnit.SECONDS,
                        TaskExecutors.MAIN_THREAD,
                        mCallBack
                )
    }

    private val mCallBack = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        override fun onCodeSent(p0: String?, p1: PhoneAuthProvider.ForceResendingToken?) {
            super.onCodeSent(p0, p1)
            verificationId = p0
        }

        override fun onVerificationCompleted(p0: PhoneAuthCredential?) {
            val code = p0!!.smsCode
            if (code != null) {
                editText!!.setText(code)
                verifyCode(code)
            }
        }

        override fun onVerificationFailed(p0: FirebaseException?) {
            editText!!.setText("")
            progressBar!!.visibility = View.GONE
            btnSignIn.visibility=View.VISIBLE

            Toast.makeText(this@MobileNumberIntegration, p0?.localizedMessage,Toast.LENGTH_SHORT).show()
         Log.d("verification","varification faile")
        }

    }

    private fun verifyCode(code: String) {
        if(verificationId!=null)
        {
            val credential = PhoneAuthProvider.getCredential(verificationId!!, code)
            signIn(credential)
        }
        else
        {
            progressBar!!.visibility = View.GONE
            btnSignIn.visibility=View.VISIBLE
            Toast.makeText(
                this@MobileNumberIntegration,
                "Maximum tried reached",
                Toast.LENGTH_SHORT
            ).show()
        }

    }

    private fun signIn(credential: PhoneAuthCredential) {
        firebaseAuth!!.signInWithCredential(credential).addOnCompleteListener { task ->
            if (task.isSuccessful) {

                phoneno_editText.setText("+91"+phonenumber!!)
                mainViewModel?.CheckMobileNumberExist(phonenumber!!)
                CoroutineScope(Dispatchers.IO).launch {
                    mainViewModel?.mobilenumberCheckFlowstate?.collect { it ->
                        when (it) {
                            is ApiState.Loading -> {

                            }
                            is ApiState.Failure -> {
                                Log.d("callingtest", "falure"+it.msg)

                            }
                            is ApiState.CheckExistMobileRespnse -> {
                                Log.d("callingtest", "falure"+it.data.getexistance())

                                if (it.data.getexistance()) {
                                    sharedpreferenceCommon(this@MobileNumberIntegration).setUserData(phonenumber!!)

                                    startActivity(Intent(this@MobileNumberIntegration, MainActivity::class.java))
                                } else {
                                   // profile_info.visibility = View.VISIBLE
                                    findViewById<RelativeLayout>(R.id.profile_info).visibility = View.VISIBLE
                                    findViewById<RelativeLayout>(R.id.container_mobileNumber).visibility = View.GONE
                                   findViewById<RelativeLayout>(R.id.waitingsms).visibility = View.GONE

                                }




                            }
                            is ApiState.Empty -> {

                            }
                        }
                    }
                }


            }
        }
    }

}