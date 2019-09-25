package com.huyingbao.module.gan.ui.random.view

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.huyingbao.core.arch.model.RxChange
import com.huyingbao.core.arch.model.RxError
import com.huyingbao.core.arch.model.RxLoading
import com.huyingbao.core.base.flux.fragment.BaseFluxFragment
import com.huyingbao.core.utils.RecyclerItemClickListener
import com.huyingbao.module.common.app.CommonAppAction
import com.huyingbao.module.common.app.CommonAppConstants
import com.huyingbao.module.common.utils.scrollToTop
import com.huyingbao.module.common.utils.showCommonError
import com.huyingbao.module.common.utils.startWebActivity
import com.huyingbao.module.gan.R
import com.huyingbao.module.gan.ui.random.action.RandomAction
import com.huyingbao.module.gan.ui.random.action.RandomActionCreator
import com.huyingbao.module.gan.ui.random.adapter.ArticleAdapter
import com.huyingbao.module.gan.ui.random.store.RandomStore
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import org.greenrobot.eventbus.Subscribe
import org.jetbrains.anko.find
import javax.inject.Inject

/**
 * TODO 未解决旋转屏幕重新获取数据问题
 *
 * Created by liujunfeng on 2019/1/1.
 */
class ArticleListFragment : BaseFluxFragment<RandomStore>() {
    @Inject
    lateinit var randomActionCreator: RandomActionCreator

    /**
     * 列表类别
     */
    private var category: String = RandomStore.DEFAULT_CATEGORY
    /**
     * 列表页数
     */
    private var page = RandomStore.DEFAULT_PAGE

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
        fun newInstance(category: String) = ArticleListFragment().apply {
            arguments = Bundle().apply {
                putString(CommonAppConstants.Key.CATEGORY, category)
            }
        }
    }

    override fun getLayoutId() = R.layout.common_fragment_list

    override fun afterCreate(savedInstanceState: Bundle?) {
        category = arguments?.getString(CommonAppConstants.Key.CATEGORY)
                ?: RandomStore.DEFAULT_CATEGORY
        initView()
    }

    /**
     * 初始化View
     */
    private fun initView() {
        //初始化列表
        rvContent?.apply {
            //RecyclerView设置适配器
            adapter = articleAdapter
            //RecyclerView设置点击事件
            addOnItemTouchListener(RecyclerItemClickListener(context, this,
                    object : RecyclerItemClickListener.OnItemClickListener {
                        override fun onItemClick(view: View, position: Int) {
                            context?.startWebActivity(articleAdapter.getItem(position)?.url,
                                    articleAdapter.getItem(position)?.desc)
                        }
                    }))
        }
        //下拉刷新监听器，设置获取最新一页数据
        refreshLayout?.setOnRefreshListener {
            page = RandomStore.DEFAULT_PAGE
            getData(null)
        }
        //显示数据
        rxStore?.getArticleLiveData(category)?.observe(this, Observer {
            if (it.size > 0) {
                //适配器添加数据
                articleAdapter.submitList(it)
            }
        })
    }

    /**
     * ViewPager2左右滑动，该方法会被调用
     */
    override fun onResume() {
        super.onResume()
        //如果是第一页，需要刷新
        if (page == RandomStore.DEFAULT_PAGE) {
            refreshLayout?.autoRefresh()
        }
    }

    /**
     * 显示进度对话框，接收[RxLoading]，粘性，该方法不经过RxStore，由RxFluxView直接处理
     */
    @Subscribe(tags = [RandomAction.GET_DATA_LIST], sticky = true)
    fun onRxLoading(rxLoading: RxLoading) {
        if (!rxLoading.isLoading) {
            refreshLayout?.finishRefresh()
        }
    }

    /**
     * 接收[RxError]，粘性
     */
    @Subscribe(tags = [RandomAction.GET_DATA_LIST], sticky = true)
    fun onRxError(rxError: RxError) {
        activity?.let { showCommonError(it, rxError) }
    }

    /**
     * 获取网络数据成功，页码更新
     */
    @Subscribe(tags = [RandomAction.GET_DATA_LIST], sticky = true)
    fun onGetDataList(rxChange: RxChange) {
        page++
    }

    /**
     * 获取数据
     */
    @Subscribe(tags = [CommonAppAction.GET_NEXT_PAGE], sticky = true)
    fun getData(rxChange: RxChange?) {
        randomActionCreator.getDataList(category, CommonAppConstants.Config.PAGE_SIZE, page)
    }

    /**
     * 滑动到顶部
     */
    @Subscribe(tags = [CommonAppAction.SCROLL_TO_TOP], sticky = true)
    fun scrollToTop(rxChange: RxChange) {
        rvContent?.scrollToTop()
    }
}
