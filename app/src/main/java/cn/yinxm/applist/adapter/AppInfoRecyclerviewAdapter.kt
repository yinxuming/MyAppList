package cn.yinxm.applist.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import cn.yinxm.applist.R
import cn.yinxm.applist.data.AppInfo


/**
 *
 * 应用列表数据RecyclerView Adapter
 * <p>
 * @author yinxuming
 * @date 2021/8/27
 *
 */

private const val PKG_NAME = "包名："
private const val VERSION_NAME = "版本号："

internal class AppInfoRecyclerviewAdapter : Adapter<AppInfoItemHolder>() {
    private val list = mutableListOf<AppInfo>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppInfoItemHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_app_info, null)
        return AppInfoItemHolder(itemView)
    }

    override fun onBindViewHolder(holder: AppInfoItemHolder, position: Int) {
        holder.tvAppName.text = list[position].appName
        if (list[position].appIconUri.isNullOrBlank()) {
            holder.ivICon.setImageResource(R.mipmap.ic_launcher)
        } else {
            holder.ivICon.setImageURI(Uri.parse(list[position].appIconUri))
        }
        (PKG_NAME + list[position].pkgName).also { holder.tvPkgName.text = it }
        (VERSION_NAME + list[position].versionName).also { holder.tvVersion.text = it }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    /**
     * 更新列表数据
     */
    fun updateList(appList: List<AppInfo>) {
        list.clear()
        list.addAll(appList)
        notifyDataSetChanged()
    }
}

internal class AppInfoItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val tvAppName: TextView = itemView.findViewById(R.id.tv_app_name)
    val ivICon: ImageView = itemView.findViewById(R.id.iv_icon)
    val tvPkgName: TextView = itemView.findViewById(R.id.tv_pkg_name)
    val tvVersion: TextView = itemView.findViewById(R.id.tv_version)
}
