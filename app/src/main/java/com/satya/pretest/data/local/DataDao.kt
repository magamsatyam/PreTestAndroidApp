package com.example.satya.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Single

@Dao
interface DataDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertData(data: List<DataEntity>)

    @Query("Select * from data")
    fun queryData(): Single<List<DataEntity>>
}