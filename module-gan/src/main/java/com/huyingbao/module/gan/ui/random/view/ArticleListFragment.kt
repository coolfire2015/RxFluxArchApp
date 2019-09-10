package com.huyingbao.module.gan.ui.random.view

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.huyingbao.core.arch.model.RxLoading
import com.huyingbao.core.base.flux.fragment.BaseFluxFragment
import com.huyingbao.core.base.setTitle
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

    private var rvContent: RecyclerView? = null
    private var refreshLayout: SmartRefreshLayout? = null

    private var articleAdapter: ArticleAdapter? = null

    companion object {
        fun newInstance(): ArticleListFragment {
            return ArticleListFragment()
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.common_fragment_list
    }

    override fun afterCreate(savedInstanceState: Bundle?) {
        setTitle(rxStore?.categoryLiveData?.value, true)
        initAdapter()
        initRefreshView()
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
            articleAdapter?.submitList(it)
        })
    }

    /**
     * 初始化上下拉刷新View
     */
    private fun initRefreshView() {
        refreshLayout = view?.find(R.id.rfl_content)
        //下拉刷新监听器，设置获取最新一页数据
        refreshLayout?.setOnRefreshListener {
            rxStore?.categoryLiveData?.value?.let { category ->
                randomActionCreator.getDataList(
                        category,
                        CommonAppConstants.Config.PAGE_SIZE,
                        RandomStore.DEFAULT_PAGE)
            }
        }
        //如果是新创建，调用刷新方法，排除屏幕旋转
        if (rxStore?.nextRequestPage == RandomStore.DEFAULT_PAGE) {
            refreshLayout?.autoRefresh()
        }
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
}
