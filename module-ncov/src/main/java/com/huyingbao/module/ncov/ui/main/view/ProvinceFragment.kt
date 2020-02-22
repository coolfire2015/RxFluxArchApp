package com.huyingbao.module.ncov.ui.main.view

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.huyingbao.core.arch.model.RxChange
import com.huyingbao.core.arch.model.RxError
import com.huyingbao.core.arch.model.RxLoading
import com.huyingbao.core.base.flux.fragment.BaseFluxFragment
import com.huyingbao.core.base.setTitle
import com.huyingbao.core.utils.RecyclerItemClickListener
import com.huyingbao.module.common.app.CommonAppAction
import com.huyingbao.module.common.utils.scrollToTop
import com.huyingbao.module.common.utils.showCommonError
import com.huyingbao.module.ncov.R
import com.huyingbao.module.ncov.ui.main.action.MainAction
import com.huyingbao.module.ncov.ui.main.action.MainActionCreator
import com.huyingbao.module.ncov.ui.main.adapter.ProvinceAdapter
import com.huyingbao.module.ncov.ui.main.store.MainStore
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import kotlinx.android.synthetic.main.ncov_fragment_province.*
import org.greenrobot.eventbus.Subscribe
import org.jetbrains.anko.find
import javax.inject.Inject


/**
 * 省数据
 */
class ProvinceFragment : BaseFluxFragment<MainStore>() {
    @Inject
    lateinit var mainActionCreator: MainActionCreator

    private val rvContent by lazy {
        view?.find<RecyclerView>(R.id.rv_content)
    }
    private val refreshLayout by lazy {
        view?.find<SmartRefreshLayout>(R.id.rfl_content)
    }

    private val ncovAdapter by lazy {
        ProvinceAdapter(ArrayList())
    }

    companion object {
        fun newInstance() = ProvinceFragment()
    }

    override fun getLayoutId() = R.layout.common_fragment_list

    override fun afterCreate(savedInstanceState: Bundle?) {
        setTitle("省", true)
        initView()
    }

    /**
     * 初始化界面
     */
    private fun initView() {
        //设置RecyclerView
        rvContent?.apply {
            //RecyclerView设置适配器
            adapter = ncovAdapter
            //RecyclerView设置点击事件
            addOnItemTouchListener(RecyclerItemClickListener(context, this,
                    object : RecyclerItemClickListener.OnItemClickListener {
                        override fun onItemClick(view: View, position: Int) {
                        }
                    }))
        }
        //下拉刷新监听器，设置获取最新一页数据
        refreshLayout?.setOnRefreshListener {
            mainActionCreator.getAreaData(null)
        }
        //显示数据
        rxStore?.areaLiveData?.observe(this, Observer {
            ncovAdapter.setNewData(it)
        })
    }

    override fun onResume() {
        super.onResume()
        //如果store已经创建并获取到数据，说明是横屏等操作导致的Fragment重建，不需要重新获取数据
        if (rxStore?.areaLiveData?.value == null) {
            refreshLayout?.autoRefresh()
        }
    }

    /**
     * 显示进度对话框，接收[RxLoading]，粘性，该方法不经过RxStore，由RxFluxView直接处理
     */
    @Subscribe(tags = [MainAction.GET_AREA_DATA], sticky = true)
    fun onRxLoading(rxLoading: RxLoading) {
        if (!rxLoading.isLoading) {
            refreshLayout?.finishRefresh()
        }
    }

    /**
     * 接收[RxError]，粘性
     */
    @Subscribe(tags = [MainAction.GET_AREA_DATA], sticky = true)
    fun onRxError(rxError: RxError) {
        activity?.let { showCommonError(it, rxError) }
    }

    /**
     * 滑动到顶部
     */
    @Subscribe(tags = [CommonAppAction.SCROLL_TO_TOP], sticky = true)
    fun scrollToTop(rxChange: RxChange) {
        rv_content?.scrollToTop()
    }
}
