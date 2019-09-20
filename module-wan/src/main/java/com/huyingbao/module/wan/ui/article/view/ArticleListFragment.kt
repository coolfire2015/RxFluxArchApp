package com.huyingbao.module.wan.ui.article.view

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.launcher.ARouter
import com.huyingbao.core.arch.model.RxChange
import com.huyingbao.core.arch.model.RxError
import com.huyingbao.core.arch.model.RxLoading
import com.huyingbao.core.base.flux.fragment.BaseFluxFragment
import com.huyingbao.core.base.setTitle
import com.huyingbao.core.utils.RecyclerItemClickListener
import com.huyingbao.module.common.app.CommonAppAction
import com.huyingbao.module.common.app.CommonAppConstants
import com.huyingbao.module.common.utils.scrollToTop
import com.huyingbao.module.common.utils.showCommonError
import com.huyingbao.module.common.utils.startWebActivity
import com.huyingbao.module.wan.R
import com.huyingbao.module.wan.ui.article.action.ArticleAction
import com.huyingbao.module.wan.ui.article.action.ArticleActionCreator
import com.huyingbao.module.wan.ui.article.adapter.ArticleAdapter
import com.huyingbao.module.wan.ui.article.store.ArticleStore
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import org.greenrobot.eventbus.Subscribe
import org.jetbrains.anko.find
import javax.inject.Inject

/**
 * Created by liujunfeng on 2019/1/1.
 */
class ArticleListFragment : BaseFluxFragment<ArticleStore>() {
    @Inject
    lateinit var articleActionCreator: ArticleActionCreator

    private val rvContent by lazy {
        view?.find<RecyclerView>(R.id.rv_content)
    }
    private val refreshLayout by lazy {
        view?.find<SmartRefreshLayout>(R.id.rfl_content)
    }
    private val articleAdapter by lazy {
        ArticleAdapter()
    }

    companion object {
        fun newInstance(): ArticleListFragment {
            return ArticleListFragment()
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.common_fragment_list
    }

    override fun afterCreate(savedInstanceState: Bundle?) {
        setTitle(R.string.app_label_wan, true)
        initView()
    }

    /**
     * 初始化界面
     */
    private fun initView() {
        rvContent?.apply {
            //RecyclerView设置适配器
            adapter = articleAdapter
            //RecyclerView设置点击事件
            addOnItemTouchListener(RecyclerItemClickListener(context, this,
                    object : RecyclerItemClickListener.OnItemClickListener {
                        override fun onItemClick(view: View, position: Int) {
                            context?.startWebActivity(articleAdapter.getItem(position)?.link,
                                    articleAdapter.getItem(position)?.title)
                        }
                    }))
        }
        //下拉刷新监听器，设置获取最新一页数据
        refreshLayout?.setOnRefreshListener {
            rxStore?.pageLiveData?.value = ArticleStore.DEFAULT_PAGE
            getData(null)
        }
        //显示数据
        rxStore?.articleLiveData?.observe(this@ArticleListFragment, Observer {
            articleAdapter.submitList(it)
        })
    }

    override fun onResume() {
        super.onResume()
        //如果是第一页，需要刷新(排除屏幕旋转)
        if (rxStore?.pageLiveData?.value == ArticleStore.DEFAULT_PAGE) {
            refreshLayout?.autoRefresh()
        }
    }

    /**
     * 显示进度对话框，接收[RxLoading]，粘性，该方法不经过RxStore，由RxFluxView直接处理
     */
    @Subscribe(tags = [ArticleAction.GET_ARTICLE_LIST], sticky = true)
    fun onRxLoading(rxLoading: RxLoading) {
        if (!rxLoading.isLoading) {
            refreshLayout?.finishRefresh()
        }
    }

    /**
     * 接收[RxError]，粘性
     */
    @Subscribe(tags = [ArticleAction.GET_ARTICLE_LIST], sticky = true)
    fun onRxError(rxError: RxError) {
        activity?.let { showCommonError(it, rxError) }
    }

    /**
     * 获取数据
     */
    @Subscribe(tags = [CommonAppAction.GET_NEXT_PAGE], sticky = true)
    fun getData(rxChange: RxChange?) {
        rxStore?.pageLiveData?.value?.let {
            articleActionCreator.getArticleList(it)
        }
    }

    /**
     * 滑动到顶部
     */
    @Subscribe(tags = [CommonAppAction.SCROLL_TO_TOP], sticky = true)
    fun scrollToTop(rxChange: RxChange) {
        rvContent?.scrollToTop()
    }

    /**
     * fragment中创建menu
     */
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        //Menu布局文件名同界面布局文件名
        inflater.inflate(R.menu.wan_fragment_article_list, menu)
    }

    /**
     * menu点击事件
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when {
            item.itemId == R.id.menu_to_banner -> {
                //跳转BannerFragment
                baseActionCreator.postLocalChange(ArticleAction.TO_BANNER)
                true
            }
            item.itemId == R.id.menu_to_friend -> {
                //跳转FriendFragment
                baseActionCreator.postLocalChange(ArticleAction.TO_FRIEND)
                true
            }
            item.itemId == R.id.menu_to_gan -> {
                //跳转module-gan
                ARouter.getInstance().build(CommonAppConstants.Router.RandomActivity).navigation()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
