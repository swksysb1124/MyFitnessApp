package studio.jasonsu.myfitness.autoupdate

import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity.RESULT_OK
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.install.InstallStateUpdatedListener
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.InstallStatus
import com.google.android.play.core.install.model.UpdateAvailability
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import studio.jasonsu.myfitness.MainActivity
import studio.jasonsu.myfitness.MainActivity.Companion.TAG
import kotlin.time.Duration.Companion.seconds

class InAppUpdateHelper(
    private val activity: MainActivity,
    private val scope: CoroutineScope
) {
    private val appUpdateManager by lazy { AppUpdateManagerFactory.create(activity.applicationContext) }

    private var appUpdateType = AppUpdateType.FLEXIBLE

    private val installStateUpdatedListener = InstallStateUpdatedListener { state ->
        if (state.installStatus() == InstallStatus.DOWNLOADED) {
            Toast.makeText(
                activity.applicationContext,
                "下載成功. 將在5秒後自動重啟！",
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
                appUpdateManager.startUpdateFlowForResult(info, appUpdateType, activity, UPDATE_REQUEST_CODE)
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
                    appUpdateManager.startUpdateFlowForResult(info, appUpdateType, activity, 100)
                }
            }
        }
    }

    fun onDestroy() {
        if (appUpdateType == AppUpdateType.FLEXIBLE) {
            appUpdateManager.unregisterListener(installStateUpdatedListener)
        }
    }

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?): Boolean {
        if (requestCode == UPDATE_REQUEST_CODE) {
            if (resultCode != RESULT_OK) {
                Log.e(TAG, "Update failed with resultCode: $resultCode. Please check the update process for potential issues.")
                if (appUpdateType == AppUpdateType.IMMEDIATE) {
                    activity.finish()
                }
            }
            return true
        }
        return false
    }

    companion object {
        private const val UPDATE_REQUEST_CODE = 100
    }
}