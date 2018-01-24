package com.rodvar.kotlinbase.base.utils.android

import android.Manifest
import android.app.Activity
import android.app.KeyguardManager
import android.content.Context
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.graphics.Point
import android.os.Build
import android.os.IBinder
import android.os.PowerManager
import android.support.v4.app.ActivityCompat
import android.support.v4.app.FragmentActivity
import android.support.v4.content.ContextCompat
import android.view.Display
import android.view.Surface
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import com.rodvar.kotlinbase.BuildConfig
import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader

/**
 * Created by rodvar on 8/9/17.
 *
 * Tools to get information about the running device (is Rooted? , is portrait? etc)
 */
class DeviceUtils {


    companion object {
        val SMALLEST_WIDTH_SEVEN_INCH = 600
        val RECEIVE_SMS_PERMISSION = Manifest.permission.RECEIVE_SMS
        val LOCATION_PERMISSION = Manifest.permission.ACCESS_FINE_LOCATION
        val CAMERA_PERMISSION = Manifest.permission.CAMERA

        val MY_RECEIVE_SMS_PERMISSION = 2
        val MY_LOCATION_PERMISSION = 3
        val MY_CAMERA_PERMISSION = 5

        private val TAG = DeviceUtils::class.java.simpleName
        private val NO_VALUE: Any? = null
        private var keyguardManager: KeyguardManager? = null

        @JvmStatic
        val isDeviceRooted: Boolean
            get() = checkRootMethod1() || checkRootMethod2() || checkRootMethod3()

        fun hideKeyboard(activity: Activity, windowToken: IBinder) {
            if (isReady(activity)) {
                val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(windowToken, 0)
            }
        }

        fun hideKeyboard(activity: Activity) {
            if (isReady(activity)) {
                activity.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
            }
        }

        @JvmStatic
        fun hideCurrentKeyboard(activity: Activity) {
            if (isReady(activity)) {
                val imm = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
                //Find the currently focused view, so we can grab the correct window token from it.
                var view = activity.currentFocus
                //If no view currently has focus, create a new one, just so we can grab a window token from it
                if (view == null) {
                    view = View(activity)
                }
                imm.hideSoftInputFromWindow(view.windowToken, 0)
            }
        }

        fun showKeyboard(activity: Activity) {
            if (isReady(activity)) {
                val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)
            }
        }

        private fun isReady(activity: Activity?): Boolean {
            return activity != null && !activity.isFinishing
        }

        fun isScreenOn(context: Context): Boolean {
            val pm = context.getSystemService(Context.POWER_SERVICE) as PowerManager
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                return pm.isInteractive
            else
                return pm.isScreenOn
        }

        fun isNfcAvailable(context: Context): Boolean {
            val pm = context.packageManager
            return pm.hasSystemFeature(PackageManager.FEATURE_NFC)
        }

        fun getKeyguardManager(context: Context): KeyguardManager? {
            if (keyguardManager === NO_VALUE) {
                keyguardManager = context.getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager
            }

            return keyguardManager
        }

        private fun checkRootMethod1(): Boolean {
            val buildTags = android.os.Build.TAGS

            return buildTags !== NO_VALUE && buildTags.contains("test-keys")
        }

        private fun checkRootMethod2(): Boolean {
            val paths = arrayOf("/system/app/Superuser.apk", "/sbin/su", "/system/bin/su", "/system/xbin/su", "/data/local/xbin/su", "/data/local/bin/su", "/system/sd/xbin/su", "/system/bin/failsafe/su", "/data/local/su")

            for (path in paths) {
                if (File(path).exists()) {
                    return true
                }
            }

            return false
        }

        private fun checkRootMethod3(): Boolean {
            var process: Process? = null

            try {
                process = Runtime.getRuntime().exec(arrayOf("/system/xbin/which", "su"))

                val `in` = BufferedReader(InputStreamReader(process!!.inputStream))
                return `in`.readLine() !== NO_VALUE
            } catch (t: Throwable) {
                return false
            } finally {
                if (process !== NO_VALUE) {
                    process!!.destroy()
                }
            }
        }

        fun isTablet(activityContext: Context): Boolean {
            return smallestWidth(activityContext) >= SMALLEST_WIDTH_SEVEN_INCH
        }

        fun isHandset(activityContext: Context): Boolean {
            return !isTablet(activityContext)
        }

        private fun convertSurface(rotation: Int): String {
            when (rotation) {
                Surface.ROTATION_0 -> return "ROTATION_0"

                Surface.ROTATION_90 -> return "ROTATION_90"

                Surface.ROTATION_180 -> return "ROTATION_180"

                Surface.ROTATION_270 -> return "ROTATION_270"

                else -> return "UNKNOWN"
            }
        }

        fun getDeviceDefaultOrientation(context: Activity): Int {
            val config = context.resources.configuration

            val rotation = context.windowManager.defaultDisplay.rotation

            Logger.debug(TAG, String.format("Surface Rotation is: '%s'", convertSurface(rotation)))
            Logger.debug(TAG, String.format("Configuration orientation is: '%s'", convertOrientation(config.orientation)))

            val defaultOrientation: Int

            if ((rotation == Surface.ROTATION_0 || rotation == Surface.ROTATION_180) && config.orientation == Configuration.ORIENTATION_LANDSCAPE || (rotation == Surface.ROTATION_90 || rotation == Surface.ROTATION_270) && config.orientation == Configuration.ORIENTATION_PORTRAIT) {
                defaultOrientation = Configuration.ORIENTATION_LANDSCAPE
            } else {
                defaultOrientation = Configuration.ORIENTATION_PORTRAIT
            }

            Logger.debug(TAG, String.format("Device default orientation is '%s'", convertOrientation(defaultOrientation)))

            return defaultOrientation
        }

        fun convertOrientation(orientation: Int): String {
            val undefined = "undefined"

            when (orientation) {
                0 -> return undefined

                1 -> return "portrait"

                2 -> return "landscape"

                3 -> return "square"

                else -> return undefined
            }
        }

        /**
         * @return Smallest width in DP
         */
        fun smallestWidth(activityContext: Context): Int {
            return activityContext.resources.configuration.smallestScreenWidthDp
        }

        fun isInPortraitOrientation(activity: Activity): Boolean {
            val defaultOrientation = getDeviceDefaultOrientation(activity)

            val rotation = activity.windowManager.defaultDisplay.rotation

            return if (defaultOrientation == Configuration.ORIENTATION_PORTRAIT) {
                rotation == Surface.ROTATION_0 || rotation == Surface.ROTATION_180
            } else rotation == Surface.ROTATION_90 || rotation == Surface.ROTATION_270

        }

        fun getRealDisplaySize(context: Activity): Point {
            val point = Point()
            val display = context.windowManager.defaultDisplay

            if (Build.VERSION.SDK_INT >= 17) {
                display.getRealSize(point)
            } else {
                // getRealSize was introduced in API 17, fallback
                var width: Int
                var height: Int

                try {
                    // Get height and width via reflection
                    val mGetRawH = Display::class.java.getMethod("getRawHeight")
                    val mGetRawW = Display::class.java.getMethod("getRawWidth")
                    width = mGetRawW.invoke(display) as Int
                    height = mGetRawH.invoke(display) as Int
                } catch (t: Throwable) {
                    // Will not take account of the soft button bar if present
                    width = context.resources.displayMetrics.widthPixels
                    height = context.resources.displayMetrics.heightPixels
                }

                // Set x and y to width and height
                point.set(width, height)
            }

            return point
        }

        val isMaterialFullyCompatible: Boolean
            get() {
                val useMaterial = Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1
                Logger.debug("AOSVERSION", String.format("Android API %s , material %s , use material %s",
                        Build.VERSION.SDK_INT, Build.VERSION_CODES.LOLLIPOP_MR1, useMaterial))
                return useMaterial
            }


        /**
         * You will receive a callback ewith the code requestPermissionCode to go ahead after permission
         * is granted
         *
         * @param activity            involved activity
         * @param requestedPermission
         * @return true if the permission has been already granted. false otherwise, and permission needs
         * to be verified on activiy#onRequestPermissionResult callback
         * @oaram requestedPermissionCode
         */
        fun requestDangerousPermission(activity: FragmentActivity,
                                       requestedPermission: String,
                                       requestPermissionCode: Int,
                                       permissionMessageResId: Int) {
            // Here, thisActivity is the current activity
            if (!hasPermission(activity, requestedPermission)) {
                // Should we show an explanation?
                if (ActivityCompat.shouldShowRequestPermissionRationale(activity,
                        requestedPermission)) {
//                TODO Refactor: dialog to use needs to be passed as paramenter
//                val dialog = NotificationDialog()
//                        .withTitle("Permission Request")
//                        .withMessage(activity.getString(permissionMessageResId))
//                        .withPositiveButtonConfiguration(activity.getString(R.string.okText),
//                                object : NotificationDialog.OnClickListener() {
//                                    fun onClick(dialog: DialogInterface, activity: FragmentActivity) {
//                                        executePermissionRequest(activity, requestedPermission, requestPermissionCode)
//                                        dialog.dismiss()
//                                    }
//                                })
//                dialog.show(activity.supportFragmentManager, "yesNo")

                } else {
                    // No explanation needed, we can request the permission.
                    executePermissionRequest(activity, requestedPermission, requestPermissionCode)
                }
            }
        }

        /**
         * @param activity
         * @param requestedPermission
         * @return true if permission has been granted
         */
        fun hasPermission(activity: FragmentActivity, requestedPermission: String): Boolean {
            return ContextCompat.checkSelfPermission(activity, requestedPermission) == PackageManager.PERMISSION_GRANTED
        }

        fun executePermissionRequest(activity: Activity, requestedPermission: String, requestPermissionCode: Int) {
            ActivityCompat.requestPermissions(activity,
                    arrayOf(requestedPermission),
                    requestPermissionCode)
        }

        /**
         * @param version constant from Build.VERSION_CODES.
         * @return true if current version is greater than version parameter
         */
        fun androidVersionGreaterOrEqual(version: Int): Boolean {
            return Build.VERSION.SDK_INT >= version
        }

        val isDebugBuild: Boolean
            get() = BuildConfig.DEBUG
    }

}