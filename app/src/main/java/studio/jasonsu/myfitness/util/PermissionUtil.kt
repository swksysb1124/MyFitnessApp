package studio.jasonsu.myfitness.util

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import java.lang.ref.WeakReference

class PermissionUtil(
    activity: ComponentActivity,
    onNotificationRequestCallback: (isGranted: Boolean) -> Unit = {}
) {
    private val activityRef = WeakReference(activity)

    private val requestNotificationLauncher =
        activityRef.get()?.registerForActivityResult(
            ActivityResultContracts.RequestPermission(),
            onNotificationRequestCallback
        )

    fun requestNotificationPermission() {
        activityRef.get()?.let { activity ->
            if (ContextCompat.checkSelfPermission(
                    activity,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
                && Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU
            ) {
                requestNotificationLauncher?.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }
}