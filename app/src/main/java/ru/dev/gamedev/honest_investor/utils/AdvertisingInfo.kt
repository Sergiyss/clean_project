package ru.dev.gamedev.honest_investor.utils

import android.content.Context
import android.util.Log
import com.google.android.gms.ads.identifier.AdvertisingIdClient
import com.google.android.gms.common.GooglePlayServicesNotAvailableException
import com.google.android.gms.common.GooglePlayServicesRepairableException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException

class AdvertisingInfo(val context: Context) {

    fun getAdvertisingId(){
        val TAG = "aut_id"
        CoroutineScope(Dispatchers.IO).launch {

            var idInfo: AdvertisingIdClient.Info? = null
            try {
                idInfo = AdvertisingIdClient.getAdvertisingIdInfo(context.applicationContext)
            } catch (e: GooglePlayServicesNotAvailableException) {
                e.printStackTrace()
            } catch (e: GooglePlayServicesRepairableException) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            }
            var advertId: String? = null
            try {
                advertId = idInfo!!.id
            } catch (e: NullPointerException) {
                e.printStackTrace()
            }
            Log.d(TAG, "onCreate:AD ID $advertId")

        }
    }
}