package cn.yinxm.applist.data

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData

/**
 * 应用信息数据仓库层
 *
 * <p>
 * @author yinxuming
 * @date 2021/8/25
 *
 */
class AppInfoRepository(private val appInfoDao: AppInfoDao) {

    @WorkerThread
    suspend fun insert(appList: List<AppInfo>) {
        appInfoDao.insertApps(appList)
    }

    /**
     * 查询应用信息，返回LiveData包裹的应用数据
     * @param appName 应用名
     * @param versionName 应用版本号
     * @param isQueryLike 是否开启模糊查询
     */
    fun query(appName: String?, versionName: String?, isQueryLike: Boolean): LiveData<List<AppInfo>> {
        val queryName = appName ?: ""
        val queryVersion = versionName ?: ""
        return if (isQueryLike) {
            appInfoDao.queryUseLike(queryName, queryVersion)
        } else {
            appInfoDao.query(queryName, queryVersion)
        }
    }
}
