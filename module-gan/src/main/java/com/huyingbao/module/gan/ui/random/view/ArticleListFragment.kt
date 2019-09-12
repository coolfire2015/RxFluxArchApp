package com.huyingbao.module.gan.ui.random.view

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.huyingbao.core.arch.dispatcher.RxDispatcher
import com.huyingbao.core.arch.model.RxChange
import com.huyingbao.core.arch.model.RxLoading
import com.huyingbao.core.base.flux.fragment.BaseFluxFragment
import com.huyingbao.core.utils.RecyclerItemClickListener
import com.huyingbao.module.common.app.CommonAppConstants
import com.huyingbao.module.common.ui.web.view.WebActivity
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
 * Created by liujunfeng on 2019/1/1.
 */
class ArticleListFragment : BaseFluxFragment<RandomStore>() {
    @Inject
    lateinit var randomActionCreator: RandomActionCreator
    @Inject
    lateinit var rxDispatcher: RxDispatcher

    /**
     * 列表类别
     */
    private var category: String? = null
    /**
     * 列表页数
     */
    private var nextRequestPage = RandomStore.DEFAULT_PAGE

    private var rvContent: RecyclerView? = null
    private var refreshLayout: SmartRefreshLayout? = null

    private var articleAdapter: ArticleAdapter? = null

    companion object {
        fun newInstance(category: String) = ArticleListFragment().apply {
            arguments = Bundle().apply {
                putString(CommonAppConstants.Key.CATEGORY, category)
            }
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.common_fragment_list
    }

    override fun afterCreate(savedInstanceState: Bundle?) {
        category = arguments?.getString(CommonAppConstants.Key.CATEGORY)
        initAdapter()
        initRefreshView()
    }

    override fun onResume() {
        super.onResume()
        rxStore?.configLiveData?.value = Pair(
                category ?: RandomStore.DEFAULT_CATEGORY,
                nextRequestPage)
        //如果是首页，需要刷新
        if (nextRequestPage == RandomStore.DEFAULT_PAGE) {
            refreshLayout?.autoRefresh()
        }
    }

    /**
     * 设置Adapter
     */
    private fun initAdapter() {
        rvContent = view?.find(R.id.rv_content)
        //RecyclerView设置适配器
        rvContent?.adapter = ArticleAdapter().apply { articleAdapter = this }
        //RecyclerView设置点击事件
        rvContent?.addOnItemTouchListener(RecyclerItemClickListener(context, rvContent,
                object : RecyclerItemClickListener.OnItemClickListener {
                    override fun onItemClick(view: View, position: Int) {
                        context?.let {
                            //跳转网页
                            val intent = WebActivity.newIntent(it,
                                    articleAdapter?.getItem(position)?.url,
                                    articleAdapter?.getItem(position)?.desc)
                            startActivity(intent)
                        }
                    }
                }))
        //显示数据
        rxStore?.articleLiveData?.observe(this, Observer {
            if (TextUtils.equals(category, rxStore?.configLiveData?.value?.first)) {
                articleAdapter?.submitList(it)
            }
        })
    }

    /**
     * 初始化上下拉刷新View
     */
    private fun initRefreshView() {
        refreshLayout = view?.find(R.id.rfl_content)
        //下拉刷新监听器，设置获取最新一页数据
        refreshLayout?.setOnRefreshListener { getData() }
    }

    /**
     * 显示进度对话框
     * 接收[RxLoading]，粘性
     * 该方法不经过RxStore,
     * 由RxFluxView直接处理
     */
    @Subscribe(tags = [RandomAction.GET_DATA_LIST], sticky = true)
    fun onRxLoading(rxLoading: RxLoading) {
        if (!rxLoading.isLoading) {
            refreshLayout?.finishRefresh()
        }
    }

    @Subscribe(tags = [RandomAction.GET_DATA_LIST], sticky = true)
    fun onGetData(rxChange: RxChange) {
        if (TextUtils.equals(category, rxStore?.configLiveData?.value?.first)) {
            nextRequestPage++
        }
    }

    @Subscribe(tags = [RandomAction.GET_NEXT_PAGE], sticky = true)
    fun getNextPage(rxChange: RxChange) {
        if (TextUtils.equals(category, rxStore?.configLiveData?.value?.first)) {
            getData()
        }
    }

    private fun getData() {
        category?.let {
            randomActionCreator.getDataList(it, CommonAppConstants.Config.PAGE_SIZE, nextRequestPage)
        }
    }
}
