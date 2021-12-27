package cn.yinxm.applist.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * 数据库名
 */
const val TABLE_NAME_APP = "app_install_list"
/**
 * 数据库列：包名
 */
const val COLUMN_PKG_NAME = "pkg_name"
/**
 * 数据库列：应用名
 */
const val COLUMN_APP_NAME = "app_name"
/**
 * 数据库列：应用图标
 */
const val COLUMN_APP_ICON = "app_icon"
/**
 * 数据库列：应用版本
 */
const val COLUMN_VERSION_NAME = "version_name"

/**
 *
 * 应用信息的数据库实体
 * <p>
 * @author yinxuming
 * @date 2021/8/24
 *
 */
@Entity(tableName = TABLE_NAME_APP)
class AppInfo(
    @PrimaryKey @ColumnInfo(name = COLUMN_PKG_NAME) val pkgName: String,
    @ColumnInfo(name = COLUMN_APP_NAME) val appName: String,
    @ColumnInfo(name = COLUMN_APP_ICON) val appIconUri: String?,
    @ColumnInfo(name = COLUMN_VERSION_NAME) val versionName: String
)
