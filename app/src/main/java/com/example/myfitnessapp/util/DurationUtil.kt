package com.example.myfitnessapp.util

/**
 * Covert duration in millis second to the format HH:mm:ss
 *
 * For example:
 *   - 10,000ms -> 00:10
 *   - 60,000ms -> 01:00
 *   - 70,000ms -> 01:10
 *   - 3,600,000,000 -> 1:00:00
 *   - 3,600,070,000 -> 1:01:10
 */
fun Long.formattedDuration(): String {
    val seconds = this / 1000
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
 * Covert duration in millis second to a speakable sentence in Chinese
 *
 * For example:
 *   - 10,000ms -> 10秒
 *   - 60,000ms -> 1分鐘
 *   - 70,000ms -> 1分鐘10秒
 *   - 3,600,000,000 -> 1小時
 *   - 3,600,070,000 -> 1小時1分鐘10秒
 */
fun Long.speakableDuration(): String {
    if (this == 0L) return ""
    val seconds = this / 1000
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