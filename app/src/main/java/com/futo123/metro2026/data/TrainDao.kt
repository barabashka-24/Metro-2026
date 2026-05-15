package com.barabashka_24.metro2026.data

import androidx.room.*

@Dao
interface TrainDao {
    @Query("SELECT * FROM trains ORDER BY id ASC")
    fun getAll(): List<TrainEntity>

    @Query("SELECT * FROM trains WHERE id = :id LIMIT 1")
    fun getById(id: Int): TrainEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(trains: List<TrainEntity>)

    @Query("DELETE FROM trains")
    fun deleteAll()
}