package com.huyingbao.module.github.ui.main.view

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.huyingbao.core.arch.model.RxChange
import com.huyingbao.core.base.flux.fragment.BaseFluxFragment
import com.huyingbao.module.common.app.CommonAppAction
import com.huyingbao.module.common.utils.scrollToTop
import com.huyingbao.module.github.R
import com.huyingbao.module.github.ui.main.action.MainActionCreator
import com.huyingbao.module.github.ui.main.adapter.ReposAdapter
import com.huyingbao.module.github.ui.main.store.MainStore
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener
import org.greenrobot.eventbus.Subscribe
import org.jetbrains.anko.find
import javax.inject.Inject

/**
 * 推荐趋势页面
 *
 * Created by liujunfeng on 2019/6/10.
 */
class TrendFragment : BaseFluxFragment<MainStore>() {
    @Inject
    lateinit var mainActionCreator: MainActionCreator

    private var reposAdapter: ReposAdapter? = null
    private var rvContent: RecyclerView? = null
    private var rflContent: SmartRefreshLayout? = null

    companion object {
        fun newInstance()=TrendFragment()
    }

    override fun getLayoutId() = R.layout.common_fragment_list

    override fun afterCreate(savedInstanceState: Bundle?) {
        initRefreshView()
        initRecyclerView()
        initAdapter()
        showData()
    }

    /**
     * 初始化上下拉刷新View
     */
    private fun initRefreshView() {
        rflContent = view?.find(R.id.rfl_content)
        rvContent = view?.find(R.id.rv_content)
        rflContent?.setOnRefreshLoadMoreListener(object : OnRefreshLoadMoreListener {
            override fun onLoadMore(refreshLayout: RefreshLayout) {
                mainActionCreator.getTrendData("Kotlin", "monthly")
            }

            override fun onRefresh(refreshLayout: RefreshLayout) {
                mainActionCreator.getTrendData("Kotlin", "monthly")
            }
        })
        rflContent?.autoRefresh()
    }

    /**
     * 初始化RecyclerView
     */
    private fun initRecyclerView() {
        rvContent?.layoutManager = LinearLayoutManager(activity)
        //当Item的改变不会影响RecyclerView的宽高的时候可以设置setHasFixedSize(true)，
        //并通过Adapter的增删改插方法去刷新RecyclerView，而不是通过notifyDataSetChanged()。
        //（其实可以直接设置为true，当需要改变宽高的时候就用notifyDataSetChanged()去整体刷新一下）
        rvContent?.setHasFixedSize(true)
    }

    /**
     * 初始化adapter
     */
    private fun initAdapter() {
        reposAdapter = ReposAdapter(null)
        rvContent?.adapter = reposAdapter
    }

    /**
     * 显示数据
     */
    private fun showData() {
        rxStore!!.trendListLiveData.observe(this, Observer { articleArrayList ->
            if (articleArrayList != null) {
                reposAdapter!!.setNewData(articleArrayList)
                //关闭下拉
                rflContent?.finishRefresh()
                rflContent?.finishLoadMore()
            }
        })
    }

    /**
     * 滑动到顶部
     */
    @Subscribe(tags = [CommonAppAction.SCROLL_TO_TOP], sticky = true)
    fun scrollToTop(rxChange: RxChange) {
        rvContent?.scrollToTop()
    }
}