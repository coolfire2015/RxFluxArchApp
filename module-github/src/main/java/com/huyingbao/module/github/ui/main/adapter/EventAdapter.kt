package com.huyingbao.module.github.ui.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.huyingbao.core.image.ImageLoader
import com.huyingbao.core.image.ImageLoaderUtils
import com.huyingbao.core.utils.TimeUtils
import com.huyingbao.module.github.R
import com.huyingbao.module.github.ui.main.model.Event
import org.jetbrains.anko.find

/**
 * PagedListAdapter是一个[RecyclerView.Adapter]基类，它可以在RecyclerView中显示PagedLists的内容。
 * 它在用户滚动时请求新的页面，并通过计算后台线程上的列表差异来处理新的页面列表，
 * 并将最小的、有效的更新分派给RecyclerView，以确保最小的UI线程工作。
 *
 * 如果您想使用您自己的适配器基类，请尝试在适配器中使用[androidx.paging.AsyncPagedListDiffer]。
 *
 * @see androidx.paging.PagedListAdapter
 * @see androidx.paging.AsyncPagedListDiffer
 *
 * Created by liujunfeng on 2019/1/1.
 */
class EventAdapter : PagedListAdapter<Event, EventViewHolder>(diffCallback) {
    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        holder.bindTo(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder = EventViewHolder(parent)

    public override fun getItem(position: Int): Event? = super.getItem(position)

    companion object {
        /**
         * 这个diff回调通知PagedListAdapter如何在新的PagedLists到达时计算列表差异。
         *
         * 当您使用添加删除一项时，PagedListAdapter使用diffCallback来检测与之前只有一个项目不同，因此它只需要动画和重新绑定一个视图。
         *
         * @see androidx.recyclerview.widget.DiffUtil
         */
        private val diffCallback = object : DiffUtil.ItemCallback<Event>() {
            override fun areItemsTheSame(oldItem: Event, newItem: Event): Boolean = oldItem.id == newItem.id

            /**
             * 注意，在kotlin中，==检查数据类比较所有内容，但是在java中，通常您将实现object#equals，并使用它来比较对象内容。
             */
            override fun areContentsTheSame(oldItem: Event, newItem: Event): Boolean = oldItem == newItem
        }
    }
}

/**
 * 一个简单ViewHolder。它还接受null项，因为数据可能在绑定之前没有被获取。
 */
class EventViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.github_layout_item_event, parent, false)) {
    /**
     * 如果尚未分页项，则它们可能为空。PagedListAdapter将在加载项时重新绑定ViewHolder。
     */
    fun bindTo(article: Event?) {
        article?.let {
            itemView.run {
                find<TextView>(R.id.tv_event_action).text = it.type + "    " + it.repo?.name
                find<TextView>(R.id.tv_event_user_name).text = it.actor?.login ?: it.org?.name
                find<TextView>(R.id.tv_event_time).text = TimeUtils.getNewsTimeStr(it.createdAt)
                val imageLoader = ImageLoader.Builder<String>()
                imageLoader.isCircle = true
                imageLoader.resource = it.actor?.avatarUrl ?: it.org?.avatarUrl
                imageLoader.errorHolder = android.R.drawable.ic_menu_camera
                imageLoader.imgView = find(R.id.iv_event_user_head)
                ImageLoaderUtils.loadImage(context, imageLoader.build())
            }
        }
    }
}

