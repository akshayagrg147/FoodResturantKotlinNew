package com.orders.resturantorder.Base

import android.annotation.TargetApi
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding


import androidx.navigation.NavController
import com.google.android.material.snackbar.Snackbar
import com.orders.resturantorder.R
import com.orders.resturantorder.Util.AppUtils
import com.orders.resturantorder.viewmodel.BaseFragment


abstract class BaseActivity<T : ViewDataBinding, V : BaseViewModel> : AppCompatActivity(),
    BaseFragment.Callback {

    var mProgressDialog: Dialog? = null
    private lateinit var mViewDataBinding: T
    private var mViewModel: V? = null
    fun getViewDataBinding(): T = mViewDataBinding
    abstract fun getBindingVariable(): Int
    abstract fun getLayoutId(): Int
    abstract fun getViewModel(): V
    override fun onFragmentAttached() {}
    override fun onFragmentDetached(tag: String) {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        performDataBinding()
    }


    private fun performDataBinding() {
        mViewDataBinding = DataBindingUtil.setContentView(this, getLayoutId())
        this.mViewModel = if (mViewModel == null) getViewModel() else mViewModel
        mViewDataBinding.setVariable(getBindingVariable(), mViewModel)
        mViewDataBinding.executePendingBindings()
    }

    fun hideLoading() {
        if (mProgressDialog != null && mProgressDialog?.isShowing!!) {
            mProgressDialog?.cancel()
        }
    }

    fun showLoading() {
        if (isNetworkConnected()) {
            hideLoading()
            //   mProgressDialog = AppUtils.showLoadingDialog(this)
        } else {
            redSnackBar()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        hideLoading()
    }

    private fun redSnackBar() {

    }

    fun hideKeyboard() {
        val view = this.currentFocus
        if (view != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
            imm?.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }


    fun isNetworkConnected(): Boolean {
        return AppUtils.isNetworkConnected(applicationContext)
    }

    fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    @TargetApi(Build.VERSION_CODES.M)
    fun hasPermission(permission: String): Boolean {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.M || ContextCompat.checkSelfPermission(
            this,
            permission
        ) == PackageManager.PERMISSION_GRANTED
    }

    @TargetApi(Build.VERSION_CODES.M)
    fun requestPermissionsSafely(permissions: Array<String>, requestCode: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permissions, requestCode)
        }
    }

    fun onError(message: String?) {
        if (message != null) {
            showSnackBar(message)
        } else {
            // showSnackBar(getString(R.string.some_error))
        }
    }

    private fun showSnackBar(message: String) {
        val snackbar = Snackbar.make(
            findViewById(android.R.id.content),
            message, Snackbar.LENGTH_SHORT
        )
        val sbView = snackbar.view
        val textView = sbView
            .findViewById<View>(R.id.snackbar_text) as TextView
        textView.setTextColor(ContextCompat.getColor(this, R.color.white))
        snackbar.show()
    }

//    /* replace fragment without refresh*/
//    fun replaceFragmentWithBackStack(fragment: Fragment, tag: String) {
//        val backStateName = fragment.javaClass.name
//        val manager: FragmentManager = supportFragmentManager
//        val fragmentPopped: Boolean = manager.popBackStackImmediate(backStateName, 0)
//        if (!fragmentPopped) { //fragment not in back stack, create it.
//            val ft: FragmentTransaction = manager.beginTransaction()
//            ft.replace(R.id.fragment_container, fragment, tag)
//            ft.addToBackStack(backStateName)
//            ft.commit()
//        }
//    }

//    fun addFragmentWithBackStack(fragment: Fragment, tag: String) {
//        val backStateName = fragment.javaClass.name
//        val manager: FragmentManager = supportFragmentManager
//        val fragmentPopped: Boolean = manager.popBackStackImmediate(backStateName, 0)
//        if (!fragmentPopped) { //fragment not in back stack, create it.
//            val ft: FragmentTransaction = manager.beginTransaction()
//            ft.add(R.id.fragment_container, fragment, tag)
//            ft.addToBackStack(backStateName)
//            ft.commit()
//        }
//    }

//    fun errorMessageLayout(view: View, title: String?, cause: String?, refreshButton: Boolean, isPopFragment: Boolean) {
//        val bundle = bundleOf(AppConstant.EXCEPTION_VIEW to UIException(title, cause, 1, refreshButton))
//        if (isPopFragment) {
//            Navigation.findNavController(view).popBackStack()
//        }
//
//        AppUtils.print("is Fragment In BackStack : ${Navigation.findNavController(view).isFragmentInBackStack(R.id.exception_fragment)}")
//        if (Navigation.findNavController(view).isFragmentInBackStack(R.id.exception_fragment)) {
//            Navigation.findNavController(view).popBackStack(R.id.exception_fragment, true)
//        }
//        Navigation.findNavController(view).navigate(R.id.exception_fragment, bundle, null)
//    }

//    fun replaceFragmentWithOutBackStack(fragment: Fragment, tag: String) {
//        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment, tag).commit()
//    }

//    /*replace fragment and also refresh the fragment*/
//    fun replaceFragmentWithRefresh(fragment: Fragment, tag: String) {
//        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment, tag).addToBackStack(
//            tag
//        ).commit()
//    }

    fun showMessageOKCancel(
        message: String,
        okListener: DialogInterface.OnClickListener,
        cancelButton: Boolean
    ) {
        val dialog = AlertDialog.Builder(this)
        dialog.setMessage(message).setPositiveButton("OK", okListener)
        if (cancelButton) {
            dialog.setNegativeButton("Cancel", null)
        }
        dialog.create().show()
    }

//    fun showActionSheet(title: String, msg: String, actionList: List<ActionItem>) {
//        ActionSheet.Builder()
//                .actions(actionList)
//                .title(title)
//                .message(msg)
//                .show(supportFragmentManager)
//    }

    fun NavController.isFragmentInBackStack(destinationId: Int) =
        try {
            getBackStackEntry(destinationId)
            true
        } catch (e: Exception) {
            false
        }
}