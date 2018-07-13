package vn.tiki.android.androidhometest.data.api.response

import java.util.Date

data class Deal(
    val productName: String,
    val productThumbnail: String,
    val productPrice: Double,
    val startedDate: Date,
    val endDate: Date
) {
    fun priceCurrency(): String = "$$productPrice"
    fun remainingTime(): String {
        val now = Date().time
        val expiredTime = endDate.time
        val diff = (expiredTime - now) / 1000L
        val days = (diff / 86400L).toInt()
        val hours = (diff % 86400L / 3600).toInt()
        val minutes = (diff % 3600L / 60).toInt()
        val seconds = (diff % 3600L % 60).toInt()

        return "$days days $hours hours $minutes minutes $seconds seconds"
    }

    fun isAvailable(): Boolean = endDate.time >= Date().time + 1000L
}
