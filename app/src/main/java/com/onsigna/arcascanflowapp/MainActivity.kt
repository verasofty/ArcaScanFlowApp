package com.onsigna.arcascanflowapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager.getDefaultSharedPreferences
import android.util.Log
import android.widget.Toast

import com.sf.upos.reader.*
import com.sf.upos.reader.dspread.bluetooth.QPOSReader
import com.sfmex.upos.reader.*

class MainActivity : AppCompatActivity(), HALReaderCallback {
    companion object {
        private val TAG = MainActivity::class.qualifiedName
        lateinit var reader : IHALReader
    }

    lateinit var readerDetected : String
    lateinit var viewController : MainActivityVC

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewController = MainActivityVC(this)
        execFlow();
    }

    private fun execFlow() {
        Log.d(TAG, "== execFlow() ==")

        applyVerifications()
        initializeReader()
        scanDevices()
    }

    private fun scanDevices() {
        Log.d(TAG, "== scanDevices() ==")
        reader.scan(this, this)
    }

    private fun initializeReader() {
        Log.d(TAG, "== initializeReader() ==")
        reader = ReaderMngr.getReader(ReaderMngr.HW_DSPREAD_QPOS)
    }

    private fun applyVerifications() {
        if ( !Verifier.checkPermission(baseContext) ) {
            val intent = Intent(this, Verifier::class.java)
            Toast.makeText(baseContext, "Checking permissions", Toast.LENGTH_LONG).show()
            startActivity(intent)
        } else
            Toast.makeText(baseContext, "Permissions already granted!!!!", Toast.LENGTH_SHORT).show()
    }

    //region HALReaderCallback Implementation
    override fun onFinishedTransaction(tdr: TransactionDataResult?) {
        Log.d(TAG, "== onFinishedTransaction() ==")

    }

    override fun onBrokenTransaction(p0: String?): TransactionDataResult {
        TODO("Not yet implemented")
    }

    override fun onSwipedCard(p0: TransactionData?) {
        TODO("Not yet implemented")
    }

    override fun onTestResult(p0: Boolean) {
        TODO("Not yet implemented")
    }

    override fun onFinishedInit(p0: MutableMap<String, String>?) {
        TODO("Not yet implemented")
    }

    override fun onStatusReader(statusReader: StatusReader?) {
        Log.d(TAG, "== onStatusReader() ==")
        Log.d(TAG, "status -> ${statusReader.toString()}")
    }

    override fun onReaderDetected(readerDetected: String?) {
        Log.d(TAG, "== onReaderDetected() ==")
        this.readerDetected = readerDetected!!
        reader.stop(this, this)
    }

    override fun onReaderConnected() {
        Log.d(TAG, "== onReaderConnected() ==")
        Toast.makeText(baseContext, "Reader is CONNECTED", Toast.LENGTH_LONG).show()
    }

    override fun onReaderNotConnected() {
        Log.d(TAG, "== onReaderNotConnected() ==")
        Toast.makeText(baseContext, "Reader NOT CONNECTED", Toast.LENGTH_LONG).show()
    }

    override fun onStopReader() {
        TODO("Not yet implemented")
    }

    override fun onStopScan() {
        Log.d(TAG, "== onStopScan() ==")

        getDefaultSharedPreferences(this)
            .edit().putString(QPOSReader.DEVICE_ID, readerDetected)
            .apply()

        reader.connect(this, this)
    }

    override fun onBatteryInfo(batteryLevel: String?) {
        Log.d(TAG, "== onBatteryInfo(level -> $batteryLevel) ==")
    }

    override fun updateDialog(dialogText: String?) {
        Log.d(TAG, "== updateDialog($dialogText) ==")
    }
    //endregion

}