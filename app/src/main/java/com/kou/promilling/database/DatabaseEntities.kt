package com.kou.promilling.database

import android.os.Parcelable
import androidx.room.*
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize
import java.util.*

enum class EntityType {
    TYPE_SPIRAL_CONTACT,
    TYPE_CUTTING_WIDTH,
    TYPE_TROCHOID_WIDTH
}

interface ResultItem {
    val id: Long
    val type: EntityType
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
    @Ignore @IgnoredOnParcel override val type = EntityType.TYPE_SPIRAL_CONTACT
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
    @Ignore @IgnoredOnParcel override val type = EntityType.TYPE_CUTTING_WIDTH
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
    @Ignore @IgnoredOnParcel override val type = EntityType.TYPE_TROCHOID_WIDTH
}

class DatabaseTypeConverters {
    @TypeConverter
    fun millisToDate(value: Long): Date = Date(value)

    @TypeConverter
    fun dateToMillis(date: Date): Long = date.time
}