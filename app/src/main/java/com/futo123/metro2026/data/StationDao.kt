package com.futo123.metro2026.data

import androidx.room.*

@Dao
interface StationDao {
    @Query("SELECT * FROM stations ORDER BY id")
    fun getAll(): List<StationEntity>

    @Query("SELECT * FROM stations WHERE id = :id LIMIT 1")
    fun getById(id: Int): StationEntity?

    @Query("SELECT * FROM stations WHERE lineId = :lineId ORDER BY id")
    fun getByLine(lineId: Int): List<StationEntity>

    @Query("DELETE FROM stations")
    fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(stations: List<StationEntity>)
}