package com.orders.resturantorder.viewmodel

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import com.orders.resturantorder.Base.BaseActivity
import com.orders.resturantorder.Base.BaseViewModel


abstract class BaseFragment<T : ViewDataBinding, V : BaseViewModel> : Fragment()  {
    var mActivity: BaseActivity<T, V>? = null
    lateinit var mRootView: View
    lateinit var mViewDataBinding: T
    lateinit var mViewModel: V
    abstract fun getBindingVariable(): Int
    abstract fun getLayoutId(): Int
    abstract fun getViewModel(): V
    abstract fun getLifeCycleOwner(): LifecycleOwner

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is BaseActivity<*, *>) {
            mActivity = context as BaseActivity<T, V>?
            mActivity?.onFragmentAttached()
        }
    }


    override fun onDetach() {
        mActivity = null
        super.onDetach()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        mViewDataBinding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false)
        mRootView = mViewDataBinding.root
        return mRootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mViewDataBinding.setVariable(getBindingVariable(), getViewModel())
        mViewDataBinding.lifecycleOwner = getLifeCycleOwner()
        mViewDataBinding.executePendingBindings()

        // setup title
     //   setTitle(requireActivity().title as Any)

    }

//    fun setTitle(tile: Any) {
//        // (activity as MainActivity).setTitle(tile,true)
//        when (getViewModel()) {
//            is HomeViewModel -> {
//                (activity as MainActivity).setTitle(AppUtils.createGson()!!.fromJson(AppPreferences(mActivity!!).getUserData(), Users::class.java).organizationName!!, false, 0, "")
//            }
//
//            is BankAccountViewModel -> {
//                (activity as MainActivity).setTitle(tile.toString(), true, 1, "")
//            }
//
//            is PurchaseViewModel -> {
//                (activity as MainActivity).setTitle(getString(R.string.title_purchases), false, 1, "")
//            }
//
//            is ContactsViewModel -> {
//                (activity as MainActivity).setTitle(getString(R.string.title_contacts), false, 1, "")
//            }
//
//            is ContactDetailsHolderViewModel -> {
//                (activity as MainActivity).setTitle(getString(R.string.title_contacts), true, 1, "")
//            }
//
//            is SalesViewModel -> {
//                (activity as MainActivity).setTitle(getString(R.string.title_sales), false, 1, "")
//            }
//
//            is ServicesViewModel -> {
//                (activity as MainActivity).setTitle(getString(R.string.title_more_service), false, 1, "")
//            }
//
//            is SupportMainFragmentViewModel -> {
//                (activity as MainActivity).setTitle(getString(R.string.title_support), true, 1, "")
//            }
//
//            is SettingsViewModel -> {
//                (activity as MainActivity).setTitle(tile.toString(), true, 1, "")
//            }
//
//            is FAQViewModel -> {
//                (activity as MainActivity).setTitle(getString(R.string.title_faq), true, 1, "")
//            }
//
//            is MoreViewModel -> {
//                (activity as MainActivity).setTitle(getString(R.string.title_more), true, 1, "")
//            }
//
//            is OrganisationViewModel -> {
//                (activity as MainActivity).setTitle(getString(R.string.title_organisation), true, 1, "")
//            }
//
//            is CustomerViewModel -> {
//                (activity as MainActivity).setTitle(getString(R.string.title_customer), true, 1, "")
//            }
//
////            is ManageViewModel -> {
////                (activity as MainActivity).setTitle(getString(R.string.title_manage_account), true, 4, "")
////            }
//
//            /*is ReconciliationViewModel -> {
//                (activity as MainActivity).setTitle(getString(R.string.title_reconciliations),true, 2, tile)
//            }*/
//
//            is SalesDetailViewModel -> {
//                (activity as MainActivity).setTitle(getString(R.string.title_sales),true, 1, "")
//            }
//
//            is PurchaseDetailViewModel -> {
//                (activity as MainActivity).setTitle(getString(R.string.title_purchases),true, 1, "")
//            }
//
//            is BankAccountListViewModel -> {
//                (activity as MainActivity).setTitle(getString(R.string.title_bank_account),true, 1, "")
//            }
//
//            is ExceptionViewModel -> {
//                (activity as MainActivity).setTitle(tile.toString(),true, 1, "")
//            }
//
//            is AddNewContactHolderViewModel -> {
//                (activity as MainActivity).setTitle(getString(R.string.title_add_new_contact),true, 6, "")
//            }
//
//            is AddItemDetailViewModel -> {
//                (activity as MainActivity).setTitle(getString(R.string.add_new_item),true, 6, "")
//            }
//
//            is InventoryItemHolderViewModel -> {
//                (activity as MainActivity).setTitle("",true, 6, "")
//            }
//
//            is AddNewLineViewModel -> {
//                (activity as MainActivity).setTitle(getString(R.string.title_add_new_line),true, 1, "")
//            }
//            is NotificationViewModel -> {
//                (activity as MainActivity).setTitle(getString(R.string.title_notification), true, 1, "")
//            }
//
//            else -> {
//                //(activity as MainActivity).setTitle(getString(R.string.app_name), false, 0)
//            }
//        }
//    }

    fun getBaseActivity(): BaseActivity<T, V>? = mActivity

    fun getViewDataBinding(): T = mViewDataBinding

    fun showLoading() = mActivity?.showLoading()

    fun hideLoading() = mActivity?.hideLoading()

    fun hideKeyboard() = mActivity?.hideKeyboard()

    fun isNetworkConnected(): Boolean = mActivity != null && mActivity!!.isNetworkConnected()

    interface Callback {
        fun onFragmentAttached()
        fun onFragmentDetached(tag: String)
    }

    fun showMessage(message: String) {
        mActivity?.showMessage(message)
    }

    fun onError(message: String?) {
        mActivity?.onError(message)
    }

    fun hasPermission(permission: String) : Boolean? {
        return mActivity?.hasPermission(permission)
    }

    fun requestPermissionsSafely(permissions: Array<String>, requestCode: Int) {
        mActivity?.requestPermissionsSafely(permissions, requestCode)
    }

//    fun replaceFragmentWithBackStack(fragment: Fragment, tag: String) {
//        mActivity!!.replaceFragmentWithBackStack(fragment, tag)
//    }

//    fun addFragmentWithBackStack(fragment: Fragment, tag: String) {
//        mActivity!!.addFragmentWithBackStack(fragment, tag)
//    }

//    fun errorMessageLayout(view: View, title: String?, cause: String?, refreshButton: Boolean, isPopFragment: Boolean) {
//        mActivity!!.errorMessageLayout(view, title, cause, refreshButton, isPopFragment)
//    }

    fun showMessageOKCancel(message: String, okListener: DialogInterface.OnClickListener, cancelButton: Boolean) {
        mActivity?.showMessageOKCancel(message, okListener, cancelButton)
    }

//    fun showActionSheet(title: String, msg: String, actionList: List<ActionItem>) {
//        mActivity?.showActionSheet(title, msg, actionList)
//    }
}