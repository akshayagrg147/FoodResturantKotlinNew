package com.orders.resturantorder.Fragments

import android.app.Activity
import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.Navigation
import com.github.dhaval2404.imagepicker.ImagePicker
import com.orders.resturantorder.R
import com.orders.resturantorder.databinding.FragmentBlankBinding
import com.orders.resturantorder.databinding.FragmentMyOrdersBinding
import com.orders.resturantorder.databinding.FragmentProfileBinding
import com.orders.resturantorder.model.BR
import com.orders.resturantorder.viewmodel.BaseFragment
import com.orders.resturantorder.viewmodel.DashBoardCategoriesViewModal
import com.orders.resturantorder.viewmodel.FragmentMyOrderViewModal
import com.orders.resturantorder.viewmodel.FragmentProfileViewModal
import kotlinx.android.synthetic.main.fragment_profile.*
import java.io.ByteArrayOutputStream
import java.io.File

class ProfileFragment : BaseFragment<FragmentProfileBinding, FragmentProfileViewModal>(){

    private val mHomeViewModel: FragmentProfileViewModal by activityViewModels()
    override fun getBindingVariable(): Int = BR.viewModel
    override fun getLayoutId(): Int = R.layout.fragment_profile
    override fun getViewModel(): FragmentProfileViewModal = mHomeViewModel
    override fun getLifeCycleOwner(): LifecycleOwner = this
    private var fragmentmyorderbinding: FragmentProfileBinding? = null




    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentmyorderbinding = getViewDataBinding()
        // AddressBook_layout=view.findViewById(R.id.AddressBook_layout)
        AddressBook_layout?.let {
            it.setOnClickListener(View.OnClickListener {
                Navigation.findNavController(view).navigate(R.id.action_ProfileFragment_to_addressBookFragment);
                //   Navigation.findNavController(view).navigate(R.id.settingsFragment, bundle, AppUtils.getNavOptions())

            })


        }
        fragmentmyorderbinding!!.profileImageView.setOnClickListener{

        }
    }
//    private fun actionSheet() {
//        val cameraAction = ActionItem(title = "Camera", style = 0) { actionCamera() }
//        val galleryAction = ActionItem(title = "Gallery", style = 0) { actionGallery() }
//        val options = manageViewModel.getActionItem(cameraAction, galleryAction).toList()
//        if (options.isEmpty()) {
//            showMessage("Action not available")
//        } else {
//            showActionSheet("", "Options", options)
//        }
//    }

    private fun actionGallery() {

        if (hasPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == true && hasPermission(
                android.Manifest.permission.READ_EXTERNAL_STORAGE
            ) == true) {
            chooseImageFromLocal()
        } else {
            requestPermissionsSafely(
                arrayOf(
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE
                ), 12
            )
        }
    }

    private fun actionCamera() {

        if (hasPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == true && hasPermission(
                android.Manifest.permission.READ_EXTERNAL_STORAGE
            ) == true && hasPermission(android.Manifest.permission.CAMERA) == true) {
            dispatchTakePictureIntent()
        }else{
            requestPermissionsSafely(
                arrayOf(
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE,
                    android.Manifest.permission.CAMERA
                ), 12
            )
        }


    }

    private fun  dispatchTakePictureIntent() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        try {
           startActivityForResult(
                takePictureIntent,
               1
            )
        } catch (e: ActivityNotFoundException) {
            // display error state to the user
            e.printStackTrace()
        }

    }

    private fun chooseImageFromLocal() {
        ImagePicker.with(this)
            // Crop Image(User can choose Aspect Ratio)
            .crop()
            // User can only select image from Gallery
            .galleryOnly()

            .galleryMimeTypes( // no gif images at all
                mimeTypes = arrayOf(
                    "image/png",
                    "image/jpg",
                    "image/jpeg"
                )
            )
            // Image resolution will be less than 1080 x 1920
            //.maxResultSize(1080, 1920)
            // .saveDir(getExternalFilesDir(null)!!)
            .start(122)
    }


//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if (resultCode == Activity.RESULT_OK) {
//
//            if (requestCode == AppConstant.IMAGE_RESULT_CODE) {
//                lifecycleScope.launch{
//                    val uri = data?.data
//                    val file = File(uri?.path ?: "")
//                    val compressedImageFile = Compressor.compress(requireContext(), file) {
//                        resolution(200, 200)
//                        quality(20)
//                        format(Bitmap.CompressFormat.WEBP)
//                        // 0.8 MB
//                    }
//
//                    val bitmap = MediaStore.Images.Media.getBitmap(requireActivity().contentResolver, compressedImageFile.toUri())
//
//                    val imageBitmap = bitmap
//                    //----------------
//                    val bytearrayoutputstream = ByteArrayOutputStream()
//                    imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, bytearrayoutputstream)
//                    val byte: ByteArray = bytearrayoutputstream.toByteArray()
//                    val compressedImg = BitmapFactory.decodeByteArray(byte, 0, byte.size)
//
//
//                    convertBitmapToBase64AndUploading(compressedImg)
//
//
//                }
//
//            } else if (requestCode == AppConstant.REQUEST_IMAGE_CAPTURE) {
//
//                val imageBitmap = data?.extras?.get("data") as Bitmap
//                //----------------
//                val bytearrayoutputstream = ByteArrayOutputStream()
//                imageBitmap.compress(Bitmap.CompressFormat.JPEG, 50, bytearrayoutputstream)
//                val byte: ByteArray = bytearrayoutputstream.toByteArray()
//                val compressedImg = BitmapFactory.decodeByteArray(byte, 0, byte.size)
//
//                convertBitmapToBase64AndUploading(compressedImg)
//
//
//
//
//            }
//        }
//    }
    fun convertBitmapToBase64AndUploading(bitmap: Bitmap) {
        val outputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
        val fileSizeInKB: Double = (outputStream.toByteArray().count().div(1024).toDouble())
        val fileSizeInMB = fileSizeInKB / 1024
       // AppUtils.saveBase64Img(Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT),100,".png","kshay")

        if(fileSizeInMB<1)
         //   updateProfilePicture(Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT))
        else
        {
            showMoreThanOneMbAlert()
        }

    }
    private fun showMoreThanOneMbAlert() {
        val builder = AlertDialog.Builder(requireActivity())
        builder.setMessage("The maximum file size is 1MB. Logo formats should be jpeg/jpg/png. For best results, upload the largest logo you have, at least 140 pixels on its shortest side.")
        builder.setNegativeButton("ok") { dialog, _ -> dialog.dismiss() }
        val dialog = builder.create()
        if (!requireActivity().isFinishing) { dialog.show() }
    }

}