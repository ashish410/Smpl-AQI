package com.ash.smplaqi.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class CityAqi(
    @PrimaryKey(autoGenerate = true) val id: Int?,
    @SerializedName("city") val city : String,
    @SerializedName("aqi") val aqi : Double,
    var aqiColor: Int?,
    var seconds: Long?,
    var lastUpdated: String?,
) {

    override fun equals(other: Any?): Boolean {
        if (other == null) {
            return false
        }
        if (other is CityAqi) {
            return this.city == other.city
        }
        return super.equals(other)
    }

    /**
     * Hash Code generation is required for equals() implementation
     */
    override fun hashCode(): Int {
        var result = city.hashCode()
        result = (result * 31) + aqi.hashCode()
        result = (result * 31) + aqiColor.hashCode()
        result = (result * 31) + lastUpdated.hashCode()
        return result
    }
}