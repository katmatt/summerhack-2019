//package com.spaetimc
//
//import android.Manifest.permission.CAMERA
//import android.app.AlertDialog
//import android.content.DialogInterface
//import android.content.Intent
//import android.content.pm.PackageManager
//import android.hardware.Camera
//import android.net.Uri
//import android.os.Build
//import android.os.Bundle
//import android.util.Log
//import android.widget.Toast
//import androidx.appcompat.app.AppCompatActivity
//import androidx.core.app.ActivityCompat
//import androidx.core.content.ContextCompat
//import me.dm7.barcodescanner.zxing.ZXingScannerView
//
//class DeleteActivity : AppCompatActivity(), ZXingScannerView.ResultHandler {
//
//    var scannerView: ZXingScannerView? = null
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        scannerView = ZXingScannerView(this)
//        setContentView(scannerView)
//        val currentApiVersion = Build.VERSION.SDK_INT
//        if (currentApiVersion >= Build.VERSION_CODES.M) {
//            if (checkPermission()) {
//                Toast.makeText(applicationContext, "Permission already granted!", Toast.LENGTH_LONG).show()
//            } else {
//                requestPermission()
//            }
//        }
//    }
//
//    private fun checkPermission(): Boolean {
//        return ContextCompat.checkSelfPermission(applicationContext, CAMERA) == PackageManager.PERMISSION_GRANTED
//    }
//
//    private fun requestPermission() {
//        ActivityCompat.requestPermissions(this, arrayOf(CAMERA), REQUEST_CAMERA)
//    }
//
//    public override fun onResume() {
//        super.onResume()
//        val currentapiVersion = Build.VERSION.SDK_INT
//        if (currentapiVersion >= Build.VERSION_CODES.M) {
//            if (checkPermission()) {
//                if (scannerView == null) {
//                    scannerView = ZXingScannerView(this)
//                    setContentView(scannerView)
//                }
//                scannerView!!.setResultHandler(this)
//                scannerView!!.startCamera()
//            } else {
//                requestPermission()
//            }
//        }
//    }
//
//    public override fun onDestroy() {
//        super.onDestroy()
//        scannerView!!.stopCamera()
//    }
//
//    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
//        when (requestCode) {
//            REQUEST_CAMERA -> if (grantResults.isNotEmpty()) {
//                val cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED
//                if (cameraAccepted) {
//                    Toast.makeText(applicationContext, "Permission Granted, Now you can access camera", Toast.LENGTH_LONG)
//                        .show()
//                } else {
//                    Toast.makeText(applicationContext, "Permission Denied, You cannot access and camera", Toast.LENGTH_LONG)
//                        .show()
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                        if (shouldShowRequestPermissionRationale(CAMERA)) {
//                            showMessageOKCancel("You need to allow access to both the permissions",
//                                DialogInterface.OnClickListener { dialog, which ->
//                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                                        requestPermissions(
//                                            arrayOf(CAMERA),
//                                            REQUEST_CAMERA
//                                        )
//                                    }
//                                })
//                            return
//                        }
//                    }
//                }
//            }
//        }
//    }
//
//    private fun showMessageOKCancel(message: String, okListener: DialogInterface.OnClickListener) {
//        AlertDialog.Builder(this@DeleteActivity)
//            .setMessage(message)
//            .setPositiveButton("OK", okListener)
//            .setNegativeButton("Cancel", null)
//            .create()
//            .show()
//    }
//
//    override fun handleResult(result: Result) {
//        val myResult = result.getText()
//        Log.d("QRCodeScanner", result.getText())
//        Log.d("QRCodeScanner", result.getBarcodeFormat().toString())
//
//        with(AlertDialog.Builder(this)) {
//            setTitle("Scan Result")
//            setMessage(result.getText())
//            setPositiveButton("OK") { _, _ -> scannerView!!.resumeCameraPreview(this@DeleteActivity) }
//            setNeutralButton("Visit") { _, _ ->
//                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(myResult))
//                startActivity(browserIntent)
//            }
//            create().show()
//        }
//
//    }
//
//    companion object {
//        private const val REQUEST_CAMERA = 1
//        private val camId = Camera.CameraInfo.CAMERA_FACING_BACK
//    }
//
//}