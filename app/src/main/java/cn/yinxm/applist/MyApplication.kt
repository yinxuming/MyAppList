package cn.yinxm.applist

import android.app.Application
import cn.yinxm.applist.data.AppInfoRepository
import cn.yinxm.applist.data.AppInfoRoomDatabase

/**
 *
 *
 * <p>
 * @author yinxuming
 * @date 2021/8/27
 *
 */
class MyApplication : Application() {
    private val database by lazy { AppInfoRoomDatabase.getDatabase(this) }
    val repository by lazy { AppInfoRepository(database.appInfoDao()) }
}
