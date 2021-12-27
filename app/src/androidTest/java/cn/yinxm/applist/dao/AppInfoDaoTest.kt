package cn.yinxm.applist.dao


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import cn.yinxm.applist.data.AppInfo
import cn.yinxm.applist.data.AppInfoDao
import cn.yinxm.applist.data.AppInfoRoomDatabase
import cn.yinxm.applist.utilities.getValue
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


/**
 * Dao层接口单元测试
 *
 * <p>
 * @author yinxuming
 * @date 2021/8/29
 *
 */
@RunWith(AndroidJUnit4::class)
class AppInfoDaoTest {
    private lateinit var database: AppInfoRoomDatabase
    private lateinit var appInfoDao: AppInfoDao

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val fakeAppInfo = AppInfo(
        "cn.yinxm.applist", "我的应用",
        "android.resource://cn.yinxm.applist/2131492864", "1.0"
    )

    @Before
    fun createDb() = runBlocking {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        database = Room.inMemoryDatabaseBuilder(context, AppInfoRoomDatabase::class.java).build()
        appInfoDao = database.appInfoDao()

        appInfoDao.insertApps(listOf(fakeAppInfo))
    }

    @After
    fun closeDb() {
        database.close()
    }

    @Test
    fun testQueryAll() {
        val list = getValue(appInfoDao.queryAll())
        assertThat(list.size, equalTo(1))
        assertThat(list[0].pkgName, equalTo(fakeAppInfo.pkgName))
    }

    @Test
    fun testQuery() {
        val list = getValue(appInfoDao.query("", ""))
        assertThat(list.size, equalTo(1))
        assertThat(list[0].pkgName, equalTo(fakeAppInfo.pkgName))
    }

    @Test
    fun testQueryUseLike() {
        val list = getValue(appInfoDao.queryUseLike("应用", ""))
        assertThat(list.size, equalTo(1))
        assertThat(list[0].pkgName, equalTo(fakeAppInfo.pkgName))
    }
}
