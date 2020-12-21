package com.onsigna.arcascanflowapp

import android.Manifest.permission
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.onsinga.utils.BluetoothUtils

class Verifier : AppCompatActivity() {
    companion object {
        private val TAG = Verifier::class.qualifiedName
        private const val PERMISSION_REQUEST_CODE = 200

        fun checkPermission(baseContext: Context): Boolean {
            Log.d(TAG, "== checkPermission() ==")

            val result1 = ContextCompat.checkSelfPermission(baseContext, permission.ACCESS_FINE_LOCATION)
            val result2 = ContextCompat.checkSelfPermission(baseContext, permission.BLUETOOTH)
            val result3 = ContextCompat.checkSelfPermission(baseContext, permission.BLUETOOTH_ADMIN)

            return result1 == PackageManager.PERMISSION_GRANTED
                    && result2 == PackageManager.PERMISSION_GRANTED
                    && result3 == PackageManager.PERMISSION_GRANTED
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "== onCreate() ==");

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verifier)

        verify();
    }

    private fun verify() {
        Log.d(TAG, "== verify() ==")

        verifyBluetooth()
    }

    private fun verifyBluetooth() {
        if ( BluetoothUtils.isBluetoothEnabled() )
            initializesPermission()
        else
            BluetoothUtils.activeBluetooth(baseContext)
    }

    private fun initializesPermission() {
        Log.d(TAG, "== initializesPermission() ==")

        if ( !checkPermission(baseContext) )
            requestPermission()
        else
            finish()
    }

    private fun requestPermission() {
        Log.d(TAG, "== requestPermission() ==")

        ActivityCompat.requestPermissions(
            this,
            arrayOf(permission.ACCESS_FINE_LOCATION, permission.BLUETOOTH, permission.BLUETOOTH_ADMIN),
            PERMISSION_REQUEST_CODE
        )
    }
}