package com.ash.smplaqi

import com.ash.smplaqi.data.model.CityAqi
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.floor

const val DATE_FORMAT = "hh : mm a"
const val SECONDS_AGO = "seconds ago"
const val MINUTES_AGO = "minutes ago"
const val HOURS_AGO = "hours ago"

var simpleDateFormat = SimpleDateFormat(DATE_FORMAT, Locale.getDefault())

/**
 * @param dataList is the new city data list
 * @param adapterDataList is the current adapter data from RecyclerView
 */
fun mergeNewAndOldList(
    dataList: List<CityAqi>,
    adapterDataList: MutableList<CityAqi>
): MutableList<CityAqi> {
    addColorToDataList(dataList, adapterDataList)
    addTimeToNewAdapterList(adapterDataList)

    return adapterDataList
}

private fun addTimeToNewAdapterList(adapterDataList: MutableList<CityAqi>) {
    adapterDataList.forEach {
        it.seconds?.let { seconds ->
            val diffSeconds = calculateTimeDifference(seconds)
            when {
                diffSeconds < 60 -> {
                    it.lastUpdated = "$diffSeconds $SECONDS_AGO"
                }
                diffSeconds < 60 * 60 -> {
                    val minutes = floor(diffSeconds / 60.0)
                    it.lastUpdated = "$minutes $MINUTES_AGO"
                }
                diffSeconds < 60 * 60 * 60 -> {
                    val hours = floor(diffSeconds / 60.0 * 60)
                    it.lastUpdated = "$hours $HOURS_AGO"
                }
                diffSeconds > 60 * 60 * 60 -> {
                    it.lastUpdated = simpleDateFormat.format(diffSeconds * 1000)
                }
            }
        }

    }
}

private fun addColorToDataList(
    dataList: List<CityAqi>,
    adapterDataList: MutableList<CityAqi>
) {
    dataList.forEach {
        val itemIndex = adapterDataList.indexOf(it)
        it.aqiColor = setAqiColor(it.aqi)
        //-1 is returned when items are not matched
        if (itemIndex == -1) {
            adapterDataList.add(it)
        } else {
            adapterDataList[itemIndex] = it
        }
    }
}

/**
 * @param seconds is the current city object updated time in seconds
 * This methods returns the time difference w.r.t current System time
 */
fun calculateTimeDifference(seconds: Long) = System.currentTimeMillis() / 1000 - seconds

fun setAqiColor(aqi: Double): Int {
    if (aqi > 0 && aqi < 50) {
        return R.color.aqi_good //Good
    } else if (aqi > 50 && aqi < 100) {
        return R.color.aqi_moderate //Moderate
    } else if (aqi > 100 && aqi < 200) {
        return R.color.aqi_moderate //Unhealthy for Sensitive Groups
    } else if (aqi > 200 && aqi < 300) {
        return R.color.aqi_unhealthy //Unhealthy
    } else if (aqi > 300 && aqi < 400) {
        return R.color.aqi_very_unhealthy //Very Unhealthy
    } else if (aqi > 400 && aqi < 500) {
        return R.color.aqi_hazardous //Hazardous
    }
    return R.color.black
}