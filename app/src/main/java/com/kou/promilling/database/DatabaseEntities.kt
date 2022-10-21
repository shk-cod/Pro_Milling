package com.kou.promilling.database

import android.os.Parcelable
import androidx.room.*
import kotlinx.parcelize.Parcelize
import java.util.*

class EntityType {
    companion object {
        const val TYPE_SPIRAL_CONTACT = 1
        const val TYPE_CUTTING_WIDTH = 2
        const val TYPE_TROCHOID_WIDTH = 3
    }
}



@Entity
@Parcelize
data class ResultItem(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val date: Date,
    @ColumnInfo(name = "tool_diameter") val toolDiameter: Double = 0.0,
    @ColumnInfo(name = "tool_radius") val toolRadius: Double = 0.0,
    @ColumnInfo(name = "spiral_angle") val spiralAngle: Double = 0.0,
    @ColumnInfo(name = "cutting_depth") val cuttingDepth: Double = 0.0,
    @ColumnInfo(name = "cutting_width") val cuttingWidth: Double = 0.0,
    @ColumnInfo(name = "curvature_radius") val curvatureRadius: Double = 0.0,
    @ColumnInfo(name = "trochoid_step") val trochoidStep: Double = 0.0,
    @ColumnInfo(name = "flute_count") val fluteCount: Int = 0,
    @ColumnInfo(name = "flute_position") val flutePosition: Double = 0.0,
    val result: Double,
    var type: Int = 0
): Parcelable


class DatabaseTypeConverters {
    @TypeConverter
    fun millisToDate(value: Long): Date = Date(value)

    @TypeConverter
    fun dateToMillis(date: Date): Long = date.time
}