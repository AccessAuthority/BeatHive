package com.ldt.BeatHive.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ldt.BeatHive.model.mp.ArtworkInfo
import com.ldt.BeatHive.model.mp.TopHitInfo

@Dao
interface CommonDAO {
    // ARTWORK INFO
    @Query("SELECT * FROM artwork_info") fun getAllArtworkInfo(): List<ArtworkInfo>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertArtworkInfo(info: ArtworkInfo)

    // TOP HIT INFO
    @Query("SELECT * FROM top_hit_info") fun getAllTopHitInfo(): List<TopHitInfo>


}