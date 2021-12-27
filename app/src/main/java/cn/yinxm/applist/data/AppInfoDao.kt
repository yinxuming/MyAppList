package cn.yinxm.applist.data

import androidx.lifecycle.LiveData
import androidx.room.*

/**
 *
 * 应用信息数据库的数据访问层（Dao），对外暴露操作数据库的接口
 * <p>
 * @author yinxuming
 * @date 2021/8/24
 *
 */
@Dao
interface AppInfoDao {
    /**
     * 查询所有应用
     */
    @Query("SELECT * FROM $TABLE_NAME_APP ORDER BY $COLUMN_APP_NAME ASC")
    fun queryAll(): LiveData<List<AppInfo>>

    /**
     * 根据应用名精确查询所有应用
     */
    @Query("SELECT * FROM $TABLE_NAME_APP WHERE $COLUMN_APP_NAME=:appName ORDER BY $COLUMN_APP_NAME ASC")
    fun queryByAppName(appName: String): LiveData<List<AppInfo>>

    /**
     * 根据应用版本号精确查询所有应用
     */
    @Query("SELECT * FROM $TABLE_NAME_APP WHERE $COLUMN_VERSION_NAME=:appVersion ORDER BY $COLUMN_APP_NAME ASC")
    fun queryByVerName(appVersion: String): LiveData<List<AppInfo>>

    /**
     * 根据应用名、应用版本号精确查询所有应用；条件为空时，返回忽略该条件的所有结果
     * @param appName
     * @param appVersion
     */
    @Query(
        "SELECT * FROM $TABLE_NAME_APP " +
                "WHERE (:appName='' OR $COLUMN_APP_NAME=:appName) " +
                "AND (:appVersion='' OR $COLUMN_VERSION_NAME=:appVersion) " +
                "ORDER BY $COLUMN_APP_NAME ASC"
    )
    fun query(appName: String, appVersion: String): LiveData<List<AppInfo>>

    /**
     * 根据应用名、应用版本号模糊查询所有应用；条件为空时，返回忽略该条件的所有结果
     * @param appName
     * @param appVersion
     */
    @Query(
        "SELECT * FROM $TABLE_NAME_APP " +
                "WHERE (:appName='' OR $COLUMN_APP_NAME LIKE '%' || :appName || '%') " +
                "AND (:appVersion='' OR $COLUMN_VERSION_NAME LIKE '%' || :appVersion || '%' ) " +
                "ORDER BY $COLUMN_APP_NAME ASC"
    )
    fun queryUseLike(appName: String, appVersion: String): LiveData<List<AppInfo>>

    /**
     * 增加一条记录
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertApps(appInfo: List<AppInfo>)

    /**
     * 更新一条记录
     */
    @Update
    suspend fun updateApps(appInfo: List<AppInfo>)

    /**
     * 删除一条记录
     */
    @Delete
    suspend fun deleteApps(appInfo: List<AppInfo>)

    /**
     * 删除所有记录
     */
    @Query("DELETE FROM $TABLE_NAME_APP")
    suspend fun deleteAll()
}

