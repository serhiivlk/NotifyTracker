package com.fuun.notihub.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.fuun.notihub.BuildConfig
import com.fuun.notihub.data.db.converter.OffsetDateTimeConverter
import com.fuun.notihub.data.db.dao.NotifyDao
import com.fuun.notihub.data.db.model.NotifyCachedRaw

@Database(
    entities = [NotifyCachedRaw::class],
    version = 7
)
@TypeConverters(OffsetDateTimeConverter::class)
abstract class NotifyDatabase : RoomDatabase() {

    abstract fun notifyDao(): NotifyDao

    companion object Factory {
        fun create(context: Context): NotifyDatabase {
            return Room.databaseBuilder(context, NotifyDatabase::class.java, "app.db")
                .apply {
                    if (BuildConfig.DEBUG) {
                        fallbackToDestructiveMigration()
                    }
                }
                .build()
        }
    }
}
