package com.huyingbao.module.gan.ui.random.view

import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.huyingbao.core.base.flux.fragment.BaseFluxFragment
import com.huyingbao.core.base.setTitle
import com.huyingbao.module.common.app.CommonAppConstants.Config.PAGE_SIZE
import com.huyingbao.module.gan.R
import com.huyingbao.module.gan.ui.random.action.RandomActionCreator
import com.huyingbao.module.gan.ui.random.adapter.ArticleAdapter
import com.huyingbao.module.gan.ui.random.model.Article
import com.huyingbao.module.gan.ui.random.store.RandomStore
import com.scwang.smartrefresh.layout.SmartRefreshLayout

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
        setTitle(rxStore?.category, true)
        initRecyclerView()
        initAdapter()
        showData()
        //如果store已经创建并获取到数据，说明是横屏等操作导致的Fragment重建，不需要重新获取数据
        if (rxStore!!.productListLiveData.value != null) {
            return
        }
        refresh()
    }

    /**
     * 实例化RecyclerView
     */
    private fun initRecyclerView() {
        rvContent = view!!.findViewById(R.id.rv_content)
        rvContent?.layoutManager = LinearLayoutManager(activity)
        rvContent?.setHasFixedSize(true)
    }

    /**
     * 实例化adapter
     */
    private fun initAdapter() {
        articleAdapter = ArticleAdapter(null)
        //设置加载更多监听器
        articleAdapter!!.setOnLoadMoreListener({ loadMore() }, rvContent)
        //view设置适配器
        rvContent?.adapter = articleAdapter
    }

    /**
     * 显示数据
     */
    private fun showData() {
        rxStore!!.productListLiveData.observe(this, Observer { productList ->
            //判断获取回来的数据是否是刷新的数据
            val isRefresh = rxStore!!.nextRequestPage == 1
            setData(isRefresh, productList)
            articleAdapter!!.setEnableLoadMore(true)
        })
    }

    /**
     * 刷新
     */
    private fun refresh() {
        rxStore!!.nextRequestPage = 1
        //这里的作用是防止下拉刷新的时候还可以上拉加载
        articleAdapter!!.setEnableLoadMore(false)
        rxStore!!.category?.let {
            randomActionCreator.getDataList(it, PAGE_SIZE, rxStore!!.nextRequestPage)
        }
    }

    /**
     * 加载更多
     */
    private fun loadMore() {
        rxStore!!.category?.let {
            randomActionCreator.getDataList(it, PAGE_SIZE, rxStore!!.nextRequestPage)
        }
    }

    /**
     * 设置数据
     *
     * @param isRefresh
     * @param data
     */
    private fun setData(isRefresh: Boolean, data: List<Article>?) {
        val size = if (data == null || data.size == 0) 0 else data.size % PAGE_SIZE
        articleAdapter!!.setNewData(data)
        if (size == 0) {
            articleAdapter!!.loadMoreComplete()
        } else {//最后一页取回的数据不到PAGE_SIZE，说明没有更多数据，结束加载更多操作
            //第一页如果不够一页就不显示没有更多数据布局
            articleAdapter!!.loadMoreEnd(isRefresh)
            Toast.makeText(context, "no more data", Toast.LENGTH_SHORT).show()
        }
    }
}
