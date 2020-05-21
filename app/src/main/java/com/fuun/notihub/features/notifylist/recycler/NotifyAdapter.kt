package com.fuun.notihub.features.notifylist.recycler

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.fuun.notihub.databinding.ViewHolderNotifyBinding
import com.fuun.notihub.domain.entities.Notify
import java.time.format.DateTimeFormatter

class NotifyAdapter :
    ListAdapter<Notify, NotifyAdapter.NotifyViewHolder>(DiffCallback()) {

    class NotifyViewHolder(private val binding: ViewHolderNotifyBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Notify) = with(binding) {
            appName.text = appName.context.getAppNameFromPackageName(item.appPackage)
            notifyText.text = item.text
            appIcon.load(appIcon.context.getAppIconFromPackageName(item.appPackage))

            val dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yy")
            val timeFormatter = DateTimeFormatter.ofPattern("HH:mm")
            date.text = item.date.format(dateFormatter)
            time.text = item.date.format(timeFormatter)
        }

        private fun Context.getAppNameFromPackageName(packageName: String) =
            packageManager.runCatching {
                getApplicationInfo(packageName, 0)
            }.getOrNull()?.let(packageManager::getApplicationLabel)

        private fun Context.getAppIconFromPackageName(packageName: String): Drawable? {
            return packageManager.runCatching { getApplicationIcon(packageName) }.getOrNull()
        }

    }

    class DiffCallback : DiffUtil.ItemCallback<Notify>() {
        override fun areItemsTheSame(
            oldItem: Notify,
            newItem: Notify
        ): Boolean {
            return oldItem.key == newItem.key
        }

        override fun areContentsTheSame(
            oldItem: Notify,
            newItem: Notify
        ): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotifyViewHolder {
        val binding =
            ViewHolderNotifyBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NotifyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NotifyViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }
}