package com.fuun.notihub.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.fuun.notihub.data.db.model.NotifyCachedRaw
import kotlinx.coroutines.flow.Flow

@Dao
interface NotifyDao {
    @Query("""SELECT * FROM notifications ORDER BY datetime(created_date) DESC""")
    fun observeAll(): Flow<List<NotifyCachedRaw>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: NotifyCachedRaw)
}
