package cn.yinxm.applist.viewmodel

import android.content.ContentResolver
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import androidx.lifecycle.*
import cn.yinxm.applist.data.AppInfo
import cn.yinxm.applist.data.AppInfoRepository
import kotlinx.coroutines.launch
import kotlin.jvm.Throws

/**
 *
 * 应用信息ViewModel层
 *
 * <p>
 * @author yinxuming
 * @date 2021/8/23
 *
 */
internal class AppInfoViewModel(private val repository: AppInfoRepository) : ViewModel() {
    /**
     * 应用信息查询参数
     */
    private val queryParam = MutableLiveData(QueryParam("", "", true))

    /**
     * 应用信息查询结果集
     */
    val appList: LiveData<List<AppInfo>> = queryParam.switchMap {
        repository.query(it.appName, it.versionName, it.isQueryLike)
    }

    /**
     * 更新手机上所有已安装应用到本地应用信息数据库
     */
    fun updateInstallApp(context: Context) {
        viewModelScope.launch {
            repository.insert(getAppList(context.applicationContext))
        }
    }

    private fun getAppList(context: Context): List<AppInfo> {
        val list = mutableListOf<AppInfo>()
        val pm: PackageManager = context.packageManager
        val packages = pm.getInstalledPackages(0)
        for (packageInfo in packages) {
            val pkgName = packageInfo.packageName ?: continue
            val appName = packageInfo.applicationInfo.loadLabel(pm).toString()
            var iconUri = ""
            if (packageInfo.applicationInfo.icon != 0) {
                iconUri =
                    Uri.parse(
                        ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + pkgName
                                + "/" + packageInfo.applicationInfo.icon
                    ).toString()
            }
            val versionName = packageInfo.versionName ?: ""
            val info = AppInfo(pkgName, appName, iconUri, versionName)
            list.add(info)
        }
        return list
    }

    /**
     * 根据应用名、版本号、是否使用模糊查询条件，查询应用信息
     */
    fun queryApps(appName: String?, versionName: String?, isQueryLike: Boolean) {
        queryParam.postValue(QueryParam(appName, versionName, isQueryLike))
    }
}

/**
 * 数据库查询参数组合
 */
internal data class QueryParam(val appName: String?, val versionName: String?, val isQueryLike: Boolean)

/**
 * ViewModel实例工厂
 */
internal class AppInfoViewModelFactory(private val repository: AppInfoRepository) : ViewModelProvider.Factory {

    @Throws(IllegalArgumentException::class)
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AppInfoViewModel::class.java)) {
            return AppInfoViewModel(repository) as? T ?: throw IllegalArgumentException("create Model error")
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
