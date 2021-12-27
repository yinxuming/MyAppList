package cn.yinxm.applist

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.inputmethod.InputMethodManager
import android.widget.CompoundButton
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cn.yinxm.applist.adapter.AppInfoRecyclerviewAdapter
import cn.yinxm.applist.data.AppInfoRepository
import cn.yinxm.applist.data.AppInfoRoomDatabase
import cn.yinxm.applist.databinding.ActivityMainBinding
import cn.yinxm.applist.viewmodel.AppInfoViewModel
import cn.yinxm.applist.viewmodel.AppInfoViewModelFactory

/**
 * 本机已安装应用信息首页
 */
internal class MainActivity : AppCompatActivity(), TextWatcher, CompoundButton.OnCheckedChangeListener {
    private var binding: ActivityMainBinding? = null
    private val appInfoViewModel: AppInfoViewModel by viewModels {
        val repository = (application as? MyApplication)?.repository ?: AppInfoRepository(
            AppInfoRoomDatabase.getDatabase(this).appInfoDao()
        )
        AppInfoViewModelFactory(repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        binding?.etAppName?.addTextChangedListener(this)
        binding?.etAppVersion?.addTextChangedListener(this)
        binding?.switchLike?.setOnCheckedChangeListener(this)
        binding?.root?.setOnClickListener {
            closeInput()
        }
        initRecyclerView()
        appInfoViewModel.updateInstallApp(applicationContext)
    }

    private fun initRecyclerView() {
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        val adapter = AppInfoRecyclerviewAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        appInfoViewModel.appList.observe(this) { allApps ->
            allApps.let { adapter.updateList(allApps) }
        }
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        // do nothing
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        // do nothing
    }

    override fun afterTextChanged(s: Editable?) {
        doQuery()
    }

    override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
        doQuery()
    }

    /**
     * 查询条件发生改变，发起查询
     */
    private fun doQuery() {
        appInfoViewModel.queryApps(
            binding?.etAppName?.text?.toString(), binding?.etAppVersion?.text?.toString(),
            binding?.switchLike?.isChecked ?: true
        )
    }

    /**
     * 点击空白收起键盘
     */
    private fun closeInput() {
        val imm = getSystemService(INPUT_METHOD_SERVICE) as? InputMethodManager
        imm?.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
    }
}
