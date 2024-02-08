package ru.dev.gamedev.honest_investor

import android.app.Application
import android.content.Context
import android.telephony.TelephonyManager
import android.util.Log
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import androidx.work.WorkManager
import com.appsflyer.AppsFlyerConversionListener
import com.appsflyer.AppsFlyerLib
import com.appsflyer.attribution.AppsFlyerRequestListener
import dagger.hilt.android.HiltAndroidApp
import ru.dev.gamedev.honest_investor.repository.AppsData
import ru.dev.gamedev.honest_investor.utils.PreferencesManager
import timber.log.Timber
import java.net.Inet4Address
import java.net.InetAddress
import java.net.NetworkInterface
import java.util.Locale
import java.util.UUID
import javax.inject.Inject

@HiltAndroidApp
class App : Application(), Configuration.Provider {


    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    lateinit var workManager : WorkManager

    companion object {
        private var isMainActivityStarted: Boolean = false

        fun isMainActivityStarted(): Boolean {
            return isMainActivityStarted
        }

    }
    override fun onCreate() {
        super.onCreate()
        var cuid = EMPTY

        val preferencesManager = PreferencesManager(this)
        //Renegate CUID
        cuid = if(preferencesManager.getData(StaticKeys.CUID.key, EMPTY).isBlank()){
            preferencesManager.saveData(StaticKeys.CUID.key, UUID.randomUUID().toString())
            cuid
        }else{
            preferencesManager.getData(StaticKeys.CUID.key, EMPTY)
        }


        val conversionDataListener = object : AppsFlyerConversionListener {
            override fun onConversionDataSuccess(conversionData: Map<String, Any>?) {
                if (conversionData != null) {
                    val advertisingId = conversionData["advertising_id"] as String?
                    val campaignID = conversionData["campaign"] as String?
                    val afSiteid = conversionData["af_siteid"] as String?

                    appsData.copy(
                        advertisingId = advertisingId, campaign = campaignID, afSiteid = afSiteid
                    )

                    // Здесь вы можете использовать значение кампании
                }
            }

            override fun onConversionDataFail(errorMessage: String?) {
                Log.e("Appsflyer", "Conversion failed: $errorMessage")
            }

            override fun onAppOpenAttribution(attributionData: Map<String, String>?) {}

            override fun onAttributionFailure(errorMessage: String?) {}
        }

        AppsFlyerLib.getInstance().init(key, conversionDataListener, this);
        AppsFlyerLib.getInstance().setCustomerUserId(cuid)
        AppsFlyerLib.getInstance().start(this)

        appsData.copy(
            uuid = AppsFlyerLib.getInstance().getAppsFlyerUID(this)
        )



        Timber.plant(Timber.DebugTree())
        workManager = WorkManager.getInstance(this)
    }

    override val workManagerConfiguration: Configuration
        get() =Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()
}