package com.ash.smplaqi.data

import com.ash.smplaqi.R
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CityAqi(
    @SerialName("aqi")
    val aqi: Double?,
    @SerialName("city")
    val city: String?,
    var aqiColor: Int?
) {
    fun setAqiColor() {
        if (aqi == null) return
        if (aqi > 0 && aqi < 50) {
            aqiColor = R.color.aqi_good //Good
        } else if (aqi > 50 && aqi < 100) {
            aqiColor = R.color.aqi_moderate //Moderate
        } else if (aqi > 100 && aqi < 200) {
            aqiColor = R.color.aqi_moderate //Unhealthy for Sensitive Groups
        } else if (aqi > 200 && aqi < 300) {
            aqiColor = R.color.aqi_unhealthy //Unhealthy
        } else if (aqi > 300 && aqi < 400) {
            aqiColor = R.color.aqi_very_unhealthy //Very Unhealthy
        } else if (aqi > 400 && aqi < 500) {
            aqiColor = R.color.aqi_hazardous //Hazardous
        }
    }
}