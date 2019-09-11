package com.huyingbao.module.gan.ui.random.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.huyingbao.core.image.ImageLoader
import com.huyingbao.core.image.ImageLoaderUtils
import com.huyingbao.module.gan.R
import com.huyingbao.module.gan.ui.random.model.Article
import org.jetbrains.anko.find

/**
 * 一个简单的PagedListAdapter，它将Article item 绑定到CardView。
 *
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
class ArticleAdapter : PagedListAdapter<Article, ArticleViewHolder>(diffCallback) {
    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        holder.bindTo(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder = ArticleViewHolder(parent)

    public override fun getItem(position: Int): Article? = super.getItem(position)

    companion object {
        /**
         * 这个diff回调通知PagedListAdapter如何在新的PagedLists到达时计算列表差异。
         *
         * 当您使用添加删除一项时，PagedListAdapter使用diffCallback来检测与之前只有一个项目不同，因此它只需要动画和重新绑定一个视图。
         *
         * @see androidx.recyclerview.widget.DiffUtil
         */
        private val diffCallback = object : DiffUtil.ItemCallback<Article>() {
            override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean = oldItem.createdAt == newItem.createdAt
            /**
             * 注意，在kotlin中，==检查数据类比较所有内容，但是在java中，通常您将实现object#equals，并使用它来比较对象内容。
             */
            override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean = oldItem == newItem
        }
    }
}

/**
 * 一个简单ViewHolder。它还接受null项，因为数据可能在绑定之前没有被获取。
 */
class ArticleViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.gan_recycle_item_product, parent, false)) {
    /**
     * 如果尚未分页项，则它们可能为空。PagedListAdapter将在加载项时重新绑定ViewHolder。
     */
    fun bindTo(article: Article?) {
        article?.let {
            itemView.run {
                val imageLoader = ImageLoader.Builder<String>()
                imageLoader.isCircle = true
                if (article.images != null && article.images!!.isNotEmpty()) {
                    imageLoader.resource = article.images!![0]
                } else {
                    imageLoader.resource = article.url
                }
                imageLoader.errorHolder = android.R.drawable.ic_menu_camera
                imageLoader.imgView = find(R.id.iv_product_img)
                ImageLoaderUtils.loadImage(itemView.context, imageLoader.build())
                find<TextView>(R.id.tv_product_name).text = it.desc
                find<TextView>(R.id.tv_product_description).text = it.createdAt
            }
        }
    }
}