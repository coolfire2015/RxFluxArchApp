package com.huyingbao.module.epidemic.ui.main.view

import android.os.Bundle
import android.view.View
import com.huyingbao.core.arch.model.RxChange
import com.huyingbao.core.arch.model.RxError
import com.huyingbao.core.base.flux.fragment.BaseFluxFragment
import com.huyingbao.core.base.setTitle
import com.huyingbao.core.utils.RecyclerItemClickListener
import com.huyingbao.module.common.app.CommonAppAction
import com.huyingbao.module.common.utils.scrollToTop
import com.huyingbao.module.common.utils.showCommonError
import com.huyingbao.module.epidemic.R
import com.huyingbao.module.epidemic.ui.main.action.MainAction
import com.huyingbao.module.epidemic.ui.main.action.MainActionCreator
import com.huyingbao.module.epidemic.ui.main.adapter.ProvinceAdapter
import com.huyingbao.module.epidemic.ui.main.store.MainStore
import kotlinx.android.synthetic.main.epidemic_fragment_province.*
import org.greenrobot.eventbus.Subscribe
import javax.inject.Inject


/**
 * 省数据
 */
class ProvinceFragment : BaseFluxFragment<MainStore>() {
    @Inject
    lateinit var mainActionCreator: MainActionCreator

    private val epidemicAdapter by lazy {
        ProvinceAdapter(ArrayList())
    }

    companion object {
        fun newInstance() = ProvinceFragment()
    }

    override fun getLayoutId() = R.layout.epidemic_fragment_province

    override fun afterCreate(savedInstanceState: Bundle?) {
        setTitle("省", true)
        initView()
    }

    /**
     * 初始化界面
     */
    private fun initView() {
        //设置RecyclerView
        rv_content?.apply {
            //RecyclerView设置适配器
            adapter = epidemicAdapter
            //RecyclerView设置点击事件
            addOnItemTouchListener(RecyclerItemClickListener(context, this,
                    object : RecyclerItemClickListener.OnItemClickListener {
                        override fun onItemClick(view: View, position: Int) {
                        }
                    }))
        }
        //显示数据
        rxStore?.areaLiveData?.observe(this, androidx.lifecycle.Observer {
            epidemicAdapter.setNewData(it)
        })
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
