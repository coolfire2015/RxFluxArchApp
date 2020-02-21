package com.huyingbao.module.epidemic.ui.main.view

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import com.huyingbao.core.arch.model.RxError
import com.huyingbao.core.arch.model.RxLoading
import com.huyingbao.core.base.flux.fragment.BaseFluxFragment
import com.huyingbao.core.base.setTitle
import com.huyingbao.module.common.utils.showCommonError
import com.huyingbao.module.epidemic.R
import com.huyingbao.module.epidemic.ui.main.action.MainAction
import com.huyingbao.module.epidemic.ui.main.action.MainActionCreator
import com.huyingbao.module.epidemic.ui.main.store.MainStore
import kotlinx.android.synthetic.main.epidemic_fragment_main.*
import org.greenrobot.eventbus.Subscribe
import org.jetbrains.anko.toast
import javax.inject.Inject

/**
 * 主页面
 */
class MainFragment : BaseFluxFragment<MainStore>() {
    @Inject
    lateinit var mainActionCreator: MainActionCreator

    companion object {
        fun newInstance() = MainFragment()
    }

    override fun getLayoutId() = R.layout.epidemic_fragment_main

    override fun afterCreate(savedInstanceState: Bundle?) {
        setTitle("肺炎疫情", false)
        initView()
        bt_province.setOnClickListener { Navigation.findNavController(it).navigate(R.id.action_mainFragment_to_provinceFragment) }
        bt_country.setOnClickListener { Navigation.findNavController(it).navigate(R.id.action_mainFragment_to_countryFragment) }
        bt_entries.setOnClickListener { Navigation.findNavController(it).navigate(R.id.action_mainFragment_to_entriesFragment) }
        bt_recommend.setOnClickListener { Navigation.findNavController(it).navigate(R.id.action_mainFragment_to_recommendFragment) }
        bt_rumor.setOnClickListener { Navigation.findNavController(it).navigate(R.id.action_mainFragment_to_rumorFragment) }
        bt_goods.setOnClickListener { Navigation.findNavController(it).navigate(R.id.action_mainFragment_to_goodsFragment) }
        bt_timeline.setOnClickListener { Navigation.findNavController(it).navigate(R.id.action_mainFragment_to_timelinFragment) }
        bt_wiki.setOnClickListener { Navigation.findNavController(it).navigate(R.id.action_mainFragment_to_wikiFragment) }
    }

    private fun initView() {
        //设置下拉刷新获取数据
        main_rfl_content.setOnRefreshListener {
            mainActionCreator.getOverAll()
        }
        //数据变化显示数据
        rxStore?.overAllLiveData?.observe(this, Observer {
            context?.toast(it.toString())
        })
    }

    override fun onResume() {
        super.onResume()
        if(rxStore?.overAllLiveData?.value==null){
            main_rfl_content.autoRefresh()
        }
    }

    /**
     * 显示进度对话框，接收[RxLoading]，粘性，该方法不经过RxStore，由RxFluxView直接处理
     */
    @Subscribe(tags = [MainAction.GET_OVER_ALL], sticky = true)
    fun onRxLoading(rxLoading: RxLoading) {
        if (!rxLoading.isLoading) {
            main_rfl_content?.finishRefresh()
        }
    }

    /**
     * 接收[RxError]，粘性
     */
    @Subscribe(tags = [MainAction.GET_OVER_ALL], sticky = true)
    fun onRxError(rxError: RxError) {
        activity?.let { showCommonError(it, rxError) }
    }
}