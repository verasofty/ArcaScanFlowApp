package com.onsinga.utils

import android.bluetooth.BluetoothAdapter
import android.content.Context
import android.util.Log
import android.widget.Toast
import com.onsigna.arcascanflowapp.Verifier

class BluetoothUtils {
    companion object {
        fun activeBluetooth(baseContext: Context) {
            Log.d(TAG, "== activeBluetooth() ==")

            Toast.makeText(baseContext, "Active el bluetooth", Toast.LENGTH_LONG).show()
        }

        fun isBluetoothEnabled(): Boolean {
            var btAdapter: BluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
            Log.d(TAG, "bluetooth.state -> ${btAdapter.isEnabled}")

            return btAdapter.isEnabled
        }

        private val TAG = BluetoothUtils::class.qualifiedName
    }

}