package com.kou.promilling.database

import android.os.Parcelable
import androidx.room.*
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize
import java.util.*

interface ResultItem {
    val id: Long
    val type: String
    val date: Date
    val result: Double
}

@Entity
@Parcelize
data class DatabaseSpiralContactLength(
    @PrimaryKey(autoGenerate = true)
    override val id: Long = 0L,
    override val date: Date,
    @ColumnInfo(name = "tool_diameter") val toolDiameter: Double,
    @ColumnInfo(name = "spiral_angle") val spiralAngle: Double,
    @ColumnInfo(name = "cutting_depth") val cuttingDepth: Double,
    @ColumnInfo(name = "cutting_width") val cuttingWidth: Double,
    @ColumnInfo(name = "flute_count") val fluteCount: Int,
    @ColumnInfo(name = "flute_position") val flutePosition: Double,
    override val result: Double
): ResultItem, Parcelable {
    @Ignore @IgnoredOnParcel override val type = "spiral_contact"
}

@Entity
@Parcelize
data class DatabaseCuttingWidth(
    @PrimaryKey(autoGenerate = true)
    override val id: Long = 0L,
    override val date: Date,
    @ColumnInfo(name = "tool_radius") val toolRadius: Double,
    @ColumnInfo(name = "curvature_radius") val curvatureRadius: Double,
    @ColumnInfo(name = "cutting_width") val cuttingWidth: Double,
    override val result: Double
): ResultItem, Parcelable {
    @Ignore @IgnoredOnParcel override val type = "cutting_width"
}

@Entity
@Parcelize
data class DatabaseTrochoidWidth(
    @PrimaryKey(autoGenerate = true)
    override val id: Long = 0L,
    override val date: Date,
    @ColumnInfo(name = "tool_radius") val toolRadius: Double,
    @ColumnInfo(name = "curvature_radius") val curvatureRadius: Double,
    @ColumnInfo(name = "trochoid_step") val trochoidStep: Double,
    override val result: Double
): ResultItem, Parcelable {
    @Ignore @IgnoredOnParcel override val type = "trochoid_width"
}

class DatabaseTypeConverters {
    @TypeConverter
    fun millisToDate(value: Long): Date = Date(value)

    @TypeConverter
    fun dateToMillis(date: Date): Long = date.time
}