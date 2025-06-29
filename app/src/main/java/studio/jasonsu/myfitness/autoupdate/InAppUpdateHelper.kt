package studio.jasonsu.myfitness.autoupdate

import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity.RESULT_OK
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.appupdate.AppUpdateOptions
import com.google.android.play.core.install.InstallStateUpdatedListener
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.InstallStatus
import com.google.android.play.core.install.model.UpdateAvailability
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import studio.jasonsu.myfitness.MainActivity
import studio.jasonsu.myfitness.MainActivity.Companion.TAG
import studio.jasonsu.myfitness.R
import kotlin.time.Duration.Companion.seconds

class InAppUpdateHelper(
    private val activity: MainActivity,
    private val scope: CoroutineScope
) {
    private val appUpdateManager by lazy { AppUpdateManagerFactory.create(activity.applicationContext) }

    private var appUpdateType = AppUpdateType.FLEXIBLE

    private val inAppUpdateLauncher: ActivityResultLauncher<IntentSenderRequest> by lazy {
        activity.registerForActivityResult(
            ActivityResultContracts.StartIntentSenderForResult()
        ) { result ->
            if (result.resultCode != RESULT_OK) {
                Log.e(TAG, "Update failed! Please check the update process for potential issues.")
                if (appUpdateType == AppUpdateType.IMMEDIATE) {
                    activity.finish()
                }
            }
        }
    }

    private val installStateUpdatedListener = InstallStateUpdatedListener { state ->
        if (state.installStatus() == InstallStatus.DOWNLOADED) {
            Toast.makeText(
                activity.applicationContext,
                activity.getString(R.string.in_app_update_success_message),
                Toast.LENGTH_LONG
            ).show()
            scope.launch {
                delay(5.seconds)
                appUpdateManager.completeUpdate()
            }
        }
    }

    private fun checkInAppUpdate() {
        appUpdateManager.appUpdateInfo.addOnSuccessListener { info ->
            val isUpdateAvailable = info.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
            val isUpdateAllowed = when (appUpdateType) {
                AppUpdateType.IMMEDIATE -> info.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)
                AppUpdateType.FLEXIBLE -> info.isUpdateTypeAllowed(AppUpdateType.FLEXIBLE)
                else -> false
            }
            if (isUpdateAvailable && isUpdateAllowed) {
                appUpdateManager.startUpdateFlowForResult(
                    info,
                    inAppUpdateLauncher,
                    AppUpdateOptions.newBuilder(appUpdateType).build()
                )
            }
        }
    }

    fun onCreate() {
        if (appUpdateType == AppUpdateType.FLEXIBLE) {
            appUpdateManager.registerListener(installStateUpdatedListener)
        }
        checkInAppUpdate()
    }

    fun onResume() {
        if (appUpdateType == AppUpdateType.IMMEDIATE) {
            appUpdateManager.appUpdateInfo.addOnSuccessListener { info ->
                if (info.updateAvailability() == UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS) {
                    appUpdateManager.startUpdateFlowForResult(
                        info,
                        inAppUpdateLauncher,
                        AppUpdateOptions.newBuilder(appUpdateType).build()
                    )
                }
            }
        }
    }

    fun onDestroy() {
        if (appUpdateType == AppUpdateType.FLEXIBLE) {
            appUpdateManager.unregisterListener(installStateUpdatedListener)
        }
    }
}