package com.kou.promilling.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DatabaseSpiralContactLength(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    @ColumnInfo(name = "time_millis") val timeMillis: Long,
    @ColumnInfo(name = "tool_diameter") val toolDiameter: Double,
    @ColumnInfo(name = "spiral_angle") val spiralAngle: Double,
    @ColumnInfo(name = "cutting_depth") val cuttingDepth: Double,
    @ColumnInfo(name = "cutting_width") val cuttingWidth: Double,
    @ColumnInfo(name = "flute_count") val fluteCount: Int,
    @ColumnInfo(name = "flute_position") val flutePosition: Double,
    val result: Double
)

@Entity
data class DatabaseCuttingWidth(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    @ColumnInfo(name = "time_millis") val timeMillis: Long,
    @ColumnInfo(name = "tool_radius") val toolRadius: Double,
    @ColumnInfo(name = "curvature_radius") val curvatureRadius: Double,
    @ColumnInfo(name = "cutting_width") val cuttingWidth: Double,
    val result: Double
)

@Entity
data class DatabaseTrochoidWidth(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    @ColumnInfo(name = "time_millis") val timeMillis: Long,
    @ColumnInfo(name = "tool_radius") val toolRadius: Double,
    @ColumnInfo(name = "curvature_radius") val curvatureRadius: Double,
    @ColumnInfo(name = "trochoid_step") val trochoidStep: Double,
    val result: Double
)