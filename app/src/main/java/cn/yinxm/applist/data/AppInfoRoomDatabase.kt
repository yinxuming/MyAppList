package cn.yinxm.applist.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 *
 * Room数据库实例
 *
 * <p>
 * @author yinxuming
 * @date 2021/8/25
 *
 */
private const val DATA_BASE_NAME = "my_app"

@Database(entities = [AppInfo::class], version = 1, exportSchema = false)
abstract class AppInfoRoomDatabase : RoomDatabase() {

    abstract fun appInfoDao(): AppInfoDao

    companion object {

        @Volatile
        private var INSTANCE: AppInfoRoomDatabase? = null

        fun getDatabase(context: Context): AppInfoRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppInfoRoomDatabase::class.java,
                    DATA_BASE_NAME
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
