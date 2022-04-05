package com.orders.resturantorder.Util

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.icu.text.CompactDecimalFormat
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Base64
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import android.view.inputmethod.InputMethodManager
import androidx.navigation.NavOptions
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonSyntaxException
import com.orders.resturantorder.R

import java.io.*
import java.lang.reflect.Type
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.abs


class AppUtils {
    companion object {

//        @SuppressLint("InflateParams")
//        fun showLoadingDialog(context: Context): Dialog {
//            val dialog = Dialog(context)
//            val inflate = LayoutInflater.from(context)
//                    .inflate(R.layout.progress_dialog, null)
//            dialog.setContentView(inflate)
//            dialog.setCancelable(false)
//            //dialog.window.setBackgroundDrawableResource(android.R.drawable.screen_background_dark_transparent)
//            dialog.window!!.setBackgroundDrawable(
//                ColorDrawable(Color.TRANSPARENT)
//            )
//            dialog.show()
//            return dialog
//        }

        fun isNetworkConnected(context: Context): Boolean {
            val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
            if (cm != null)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    val network: Network = cm.activeNetwork ?: return false
                    val capabilities: NetworkCapabilities =
                            cm.getNetworkCapabilities(network) ?: return false
                    return (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
                            || capabilities.hasTransport(
                        NetworkCapabilities.TRANSPORT_CELLULAR
                    ))
                } else {
                    val activeNetwork = cm.activeNetworkInfo
                    return activeNetwork != null && activeNetwork.isConnectedOrConnecting
                }
            return false
        }

        fun print(message: String) {
            Log.w(AppConstant.DEBUG_TAG, "### print : $message")
        }

        fun getNavOptions(): NavOptions {
            return NavOptions.Builder()
                .setEnterAnim(R.anim.slide_in_right)
                .setExitAnim(R.anim.slide_out_left)
                .setPopEnterAnim(R.anim.slide_in_left)
                .setPopExitAnim(R.anim.slide_out_right)
                .build()
        }

        fun setUpRecyclerItemLayout(context: Context, recyclerView: RecyclerView) {
            val mLayoutManager: RecyclerView.LayoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
            recyclerView.layoutManager = mLayoutManager
            setRecyclerItemAnimation(context, recyclerView)
        }
        fun setUpRecyclerItemLayoutStaggered(context: Context, recyclerView: RecyclerView) {

            val mLayoutManager: RecyclerView.LayoutManager = GridLayoutManager(context,3)
            recyclerView.layoutManager = mLayoutManager
            setRecyclerItemAnimation(context, recyclerView)
        }

        fun setRecyclerItemAnimation(context: Context, recyclerView: RecyclerView) {
            val list = listOf(
                R.anim.layout_animation_fall_down,
                R.anim.layout_animation_from_bottom,
                R.anim.layout_animation_from_left,
                R.anim.layout_animation_from_right
            )
            val animation = AnimationUtils.loadLayoutAnimation(context, list.random())
            recyclerView.layoutAnimation = animation
            recyclerView.scheduleLayoutAnimation()
        }

        fun getBase64(input: String): String? {
            return Base64.encodeToString(input.toByteArray(), Base64.DEFAULT)
        }

        fun base64ToBitmap(base64: String?): Bitmap?{
            if(base64!!.isEmpty()){
                throw IllegalArgumentException("Base64 Encoded String cannot be Empty")
            }

            val byteArray = Base64.decode(base64, Base64.DEFAULT)
            return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
        }

        fun base64ToDrawable(base64: String): Drawable{
            if(base64.isEmpty()){
                throw IllegalArgumentException("Base64 Encoded String cannot be Empty")
            }

            return BitmapDrawable(Resources.getSystem(), base64ToBitmap(base64))
        }

        fun bitmapToByte(bitmap: Bitmap): ByteArray? {
            val stream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
            return stream.toByteArray()
        }

        fun encodeTobase64(image: Bitmap): String? {
            val baos = ByteArrayOutputStream()
            image.compress(Bitmap.CompressFormat.JPEG, 100, baos)
            val b = baos.toByteArray()
            val imageEncoded = Base64.encodeToString(b, Base64.DEFAULT)
            Log.e("LOOK", imageEncoded)
            return imageEncoded
        }

//        fun saveBase64Img(
//            base64: String?,
//            quality: Int? = 100,
//            fileExtension: String?,
//            fileName: String?
//        ) {
//            if(quality == null || quality < 1 && quality <= 100){
//                throw java.lang.IllegalArgumentException("Quality must be between 1 to 100")
//            }
//
//            if(base64 == null || base64.isEmpty()){
//                throw IllegalArgumentException("Base64 Encoded String should't be Null or Empty")
//            }
//            val decodedString: ByteArray = Base64.decode(base64, Base64.DEFAULT)
//            val bitmap: Bitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
//                ?: throw NullPointerException("Decoding Base64 String results in Null Bitmap")
//
//            if(fileExtension == null){
//                throw NullPointerException("File Extension Null. Use withExtension() Method")
//            }
//
//            if(fileName == null){
//                throw NullPointerException("File Name Null. Use setFileName() Method")
//            }
//
//            if(!writePermissionGranted()){
//                throw SecurityException("Write External Storage Permission Not Granted")
//            }
//
//            val dir = File("${context!!.getExternalFilesDir(null)?.absolutePath}/${AppConstant.DIRECTORY_NAME}")
//
//            //Create Directory If Doesn't Exist
//            if(!dir.exists()){
//                dir.mkdirs()
//            }
//
//            val file = File(dir, "$fileName$fileExtension")
//
//            val outStream: FileOutputStream
//
//            try{
//                outStream = FileOutputStream(file)
//                if(fileExtension.equals(".png", true)){
//                    bitmap.compress(Bitmap.CompressFormat.PNG, quality, outStream)
//                }
//                if(fileExtension.equals(".jpeg", true)){
//                    bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outStream)
//                }
//                if(fileExtension.equals(".webp", true)){
//                    bitmap.compress(Bitmap.CompressFormat.WEBP, quality, outStream)
//                }
//                outStream.flush()
//                outStream.close()
//            }catch (e: FileNotFoundException){
//                e.printStackTrace()
//            }
//            catch (e: IOException){
//                e.printStackTrace()
//            }
//
//        }

//        fun loadImages(fileExtension: String?, fileName: String?): Bitmap?{
//            if(fileExtension == null){
//                throw NullPointerException("File Extension Null. Use withExtension() Method")
//            }
//
//            if(fileName == null){
//                throw NullPointerException("File Name Null. Use setFileName() Method")
//            }
//
//            if(!readPermissionGranted()){
//                throw SecurityException("Write External Storage Permission Not Granted")
//            }
//
//            val dir = File("${context!!.getExternalFilesDir(null)?.absolutePath}/${AppConstant.DIRECTORY_NAME}")
//
//            // There is no file to fetch if Directory Doesn't Exist
//            if(!dir.exists()){
//                return null
//            }
//
//            val file = File(dir, "$fileName$fileExtension")
//
//            return BitmapFactory.decodeFile(file.path)
//        }
//
//        fun getFile(fileExtension: String?, fileName: String?): File?{
//            if(fileExtension == null){
//                throw NullPointerException("File Extension Null. Use withExtension() Method")
//            }
//
//            if(fileName == null){
//                throw NullPointerException("File Name Null. Use setFileName() Method")
//            }
//
//            if(!readPermissionGranted()){
//                throw SecurityException("Write External Storage Permission Not Granted")
//            }
//
//            val dir = File("${context!!.getExternalFilesDir(null)?.absolutePath}/${AppConstant.DIRECTORY_NAME}")
//
//            // There is no file to fetch if Directory Doesn't Exist
//            if(!dir.exists()){
//                return null
//            }
//
//            return File(dir, "$fileName.$fileExtension")
//        }
//
//        fun deleteImage(fileExtension: String?, fileName: String?):Boolean{
//            if(fileExtension == null){
//                throw NullPointerException("File Extension Null. Use withExtension() Method")
//            }
//
//            if(fileName == null){
//                throw NullPointerException("File Name Null. Use setFileName() Method")
//            }
//
//            if(!readAndWritePermissionGranted()){
//                throw SecurityException("Make sure user has granted Read and Write External Storage Permission Not Granted")
//            }
//
//            val dir = File("${context!!.getExternalFilesDir(null)?.absolutePath}/${AppConstant.DIRECTORY_NAME}")
//            // There is no file to fetch if Directory Doesn't Exist
//            if(!dir.exists()){
//                return false
//            }
//
//            //Fetch the file from the
//            val file = File(dir, "$fileName$fileExtension")
//
//            //If file doesn't exist return false
//            if(!file.exists()){
//                return false
//            }
//            //Return the boolean if System is able to delete the File or not.
//            return file.delete()
//
//        }
//
//        private fun writePermissionGranted(): Boolean {
//            if(ContextCompat.checkSelfPermission(
//                    context!!.applicationContext,
//                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE
//                ) == PermissionChecker.PERMISSION_GRANTED){
//                return true
//            }
//            return false
//        }
//
//        private fun readPermissionGranted(): Boolean{
//            if(ContextCompat.checkSelfPermission(
//                    context!!.applicationContext,
//                    android.Manifest.permission.READ_EXTERNAL_STORAGE
//                ) == PermissionChecker.PERMISSION_GRANTED){
//                return true
//            }
//            return false
//        }
//
//        private fun readAndWritePermissionGranted(): Boolean{
//            if(ContextCompat.checkSelfPermission(
//                    context!!.applicationContext,
//                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE
//                ) == PermissionChecker.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
//                    context!!.applicationContext, android.Manifest.permission.READ_EXTERNAL_STORAGE
//                ) == PermissionChecker.PERMISSION_GRANTED){
//                return true
//            }
//            return false
//        }
//
//        fun getAccessRights(dataManager: DataManager, displayName: String, pageName: String) : Pair<Boolean, Boolean> {
//            val data = dataManager.getUserData()?.menuAccessRights?.firstOrNull{ e -> e.displayName == displayName && e.pageName ==  pageName}
//           // return Pair(data == null || data.canView, data == null || data.canApprove)
//            return Pair( data?.canView?:false, data?.canApprove?:false)
//
//        }
//
//        fun getAccessRights(menuAccessRights: List<MenuAccessRights>, page: String) : Pair<Boolean, Boolean> {
//            val data = menuAccessRights.firstOrNull{ e -> e.displayName == page }
//            return Pair(data?.canView ?: false, data?.canApprove ?: false)
//        }

        fun hideKeyboard(view: View) {
            val inputMethodManager: InputMethodManager? =
                view.context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager?
            inputMethodManager!!.hideSoftInputFromWindow(view.windowToken, 0)
        }

        private val sGson = createGson()

        fun createGson(): Gson? {
            synchronized(Gson::class.java) {
                return sGson ?: GsonBuilder().serializeNulls().create()!!
            }
        }


        @Throws(JsonSyntaxException::class)
        fun <T> fromJson(response: String?, type: Type?): List<T> {
            return createGson()?.fromJson(response, type) ?: emptyList()
        }


        fun getCurrencySymbol(currencyCode: String) : String {
           /* val data = AppConstant.CURRENCIES.firstOrNull { it.first == currencyCode }
            return data?.third ?: ""*/
            return currencyCode
        }

        @SuppressLint("SimpleDateFormat")
        fun formatStrToDate(dateString: String?, format: String): Date? {
            return if (dateString != null && dateString.isNotEmpty()) {
                SimpleDateFormat(format).parse(dateString)
            } else {
                null
            }
        }

        @SuppressLint("SimpleDateFormat")
        fun getFormattedDate(dateString: String?, actualFormat: String, requiredFormat: String): String {
            val parser =  SimpleDateFormat(actualFormat)
            val formatter = SimpleDateFormat(requiredFormat)
            return try {
                val date = parser.parse(dateString!!)
                formatter.format(date ?: "")
            } catch (e: RuntimeException) { "" }
        }

        @SuppressLint("SimpleDateFormat")
        fun getCurrentORMinusDate(format: String, minus: Int, type: Int): String{
            val cal = Calendar.getInstance() // type 1 : minus from month else minus from year
            if (type == 1) cal.add(Calendar.MONTH, -minus) else cal.add(Calendar.YEAR, -minus)
            val sdf = SimpleDateFormat(format)
            print("Date : ${sdf.format(cal.time)}")
            return sdf.format(cal.time)
        }



        fun prettyCount(number: Float): String {
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                val formattedNumber = CompactDecimalFormat.getInstance(
                    Locale.getDefault(),
                    CompactDecimalFormat.CompactStyle.SHORT
                ).format(number)
                formattedNumber.toString()
            } else {
                return getFormattedNumber(number)
            }
        }

        private fun getFormattedNumber(number: Float): String {
            val df = DecimalFormat("#.#")
            return when {
                abs(number / 1000000) > 1 -> df.format(number / 1000000).toString() + "m"
                abs(number / 1000) > 1 -> df.format(number / 1000).toString() + "k"
                else -> df.format(number).toString()
            }
        }

        fun getDaysDifference(fromDate: Date?, toDate: Date?): Int {
            return if (fromDate == null || toDate == null) 0 else ((toDate.time - fromDate.time) / (1000 * 60 * 60 * 24)).toInt()
        }

        @SuppressLint("SimpleDateFormat")
        fun parseTODate(date: String, format: String): Date? {
            return try {
                val formatter = SimpleDateFormat(format)
                formatter.parse(date)
            } catch (ex: ParseException) {
                null
            }
        }



        fun String.removeWhitespaces() = replace(" ", "") //extension for remove white space from string

        fun toBigInt(amount: Double): String {

//            val df = DecimalFormat("#,###.00", DecimalFormatSymbols.getInstance(Locale.ENGLISH))
//            df.maximumFractionDigits = 340
//            return df.format(amount)
            var returvalue:String="0.00"


            val df = DecimalFormat("####.##", DecimalFormatSymbols.getInstance(Locale.ENGLISH))
            df.maximumFractionDigits = 340


            if(df.format(amount)!="0")
            {
                returvalue=df.format(amount)
                if(!returvalue.contains("."))
                    returvalue=df.format(amount)+".00"
            }
            return returvalue
        }


//
//        fun NotificationManager.sendNotification(
//            messageTitle: String,
//            messageBody: String,
//            applicationContext: Context
//        ) {
//            // TODO: Step 1.11 create intent
//            val contentIntent = Intent(applicationContext, MainActivity::class.java)
//            // TODO: Step 1.12 create PendingIntent
//            val contentPendingIntent = PendingIntent.getActivity(
//                applicationContext,
//                AppConstant.NOTIFICATION_ID,
//                contentIntent,
//                PendingIntent.FLAG_UPDATE_CURRENT
//            )
//            // TODO: You can add style here
//
//            // TODO: Step 1.2 get an instance of NotificationCompat.Builder
//
//            // Build the notification
//            val builder = NotificationCompat.Builder(
//                applicationContext,
//                // TODO: Step 1.8 use a notification channel
//                applicationContext.getString(R.string.app_notification_channel_id)
//            )
//                // TODO: Step 1.3 set title, text and icon to builder
//                .setSmallIcon(R.drawable.ic_notification_24dp).setContentTitle(messageTitle).setContentText(
//                    messageBody
//                )
//                // TODO: Step 1.13 set content intent
//                .setContentIntent(contentPendingIntent)
//                // TODO: Step 2.5 set priority
//                .setPriority(NotificationCompat.PRIORITY_HIGH).setAutoCancel(true)
//
//            // TODO Step 1.4 call notify
//            // Deliver the notification
//            notify(AppConstant.NOTIFICATION_ID, builder.build())
//        }

        fun storeFileLocal(dir: File, attachment: String) {
            val pdfAsBytes: ByteArray = Base64.decode(attachment, Base64.DEFAULT)
            val os = FileOutputStream(dir, false)
            os.write(pdfAsBytes)
            os.flush()
            os.close()
        }



        @SuppressLint("SimpleDateFormat")
        fun convertTimeToDate(time: Long, requiredFormat: String): String {
            val date = Date(time)
            val format = SimpleDateFormat(requiredFormat)
            return format.format(date)
        }

    }
}