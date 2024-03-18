package studio.jasonsu.myfitness.util

/**
 * Covert duration in second to the format HH:mm:ss
 *
 * For example:
 *   - 10s -> 00:10
 *   - 60s -> 01:00
 *   - 70s -> 01:10
 *   - 3,600s -> 1:00:00
 *   - 3,670s -> 1:01:10
 */
fun Int.formattedDuration(): String {
    val seconds = this
    val hour = seconds / 3600
    val minute = (seconds % 3600) / 60
    val secondLeft = (seconds % 60)
    return if (hour < 1) {
        String.format("%02d:%02d", minute, secondLeft)
    } else {
        String.format("%d:%02d:%02d", hour, minute, secondLeft)
    }
}

/**
 * Covert duration in second to a spoken sentence in Chinese
 *
 * For example:
 *   - 10s -> 10秒
 *   - 60s -> 1分鐘
 *   - 70s -> 1分鐘10秒
 *   - 3,600s -> 1小時
 *   - 3,670s -> 1小時1分鐘10秒
 */
fun Int.spokenDuration(): String {
    if (this == 0) return ""
    val seconds = this
    val hour = seconds / 3600
    val minute = (seconds % 3600) / 60
    val secondLeft = (seconds % 60)
    return buildString {
        if (hour > 0) {
            append("${hour}小時")
        }
        if (minute > 0) {
            append("${minute}分鐘")
        }
        if (secondLeft > 0) {
            append("${secondLeft}秒")
        }
    }
}