package com.huyingbao.module.ncov.ui.main.view

import android.os.Bundle
import androidx.lifecycle.Observer
import com.huyingbao.core.arch.model.RxError
import com.huyingbao.core.arch.model.RxLoading
import com.huyingbao.core.base.flux.fragment.BaseFluxFragment
import com.huyingbao.core.base.setTitle
import com.huyingbao.module.common.utils.showCommonError
import com.huyingbao.module.ncov.R
import com.huyingbao.module.ncov.ui.main.action.MainAction
import com.huyingbao.module.ncov.ui.main.action.MainActionCreator
import com.huyingbao.module.ncov.ui.main.store.MainStore
import kotlinx.android.synthetic.main.ncov_fragment_main.*
import org.greenrobot.eventbus.Subscribe
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

    override fun getLayoutId() = R.layout.ncov_fragment_main

    override fun afterCreate(savedInstanceState: Bundle?) {
        setTitle("肺炎疫情", false)
        initView()
//        bt_province.setOnClickListener { Navigation.findNavController(it).navigate(R.id.action_mainFragment_to_provinceFragment) }
//        bt_country.setOnClickListener { Navigation.findNavController(it).navigate(R.id.action_mainFragment_to_countryFragment) }
//        bt_entries.setOnClickListener { Navigation.findNavController(it).navigate(R.id.action_mainFragment_to_entriesFragment) }
//        bt_recommend.setOnClickListener { Navigation.findNavController(it).navigate(R.id.action_mainFragment_to_recommendFragment) }
//        bt_rumor.setOnClickListener { Navigation.findNavController(it).navigate(R.id.action_mainFragment_to_rumorFragment) }
//        bt_goods.setOnClickListener { Navigation.findNavController(it).navigate(R.id.action_mainFragment_to_goodsFragment) }
//        bt_timeline.setOnClickListener { Navigation.findNavController(it).navigate(R.id.action_mainFragment_to_timelinFragment) }
//        bt_wiki.setOnClickListener { Navigation.findNavController(it).navigate(R.id.action_mainFragment_to_wikiFragment) }
    }

    private fun initView() {
        //设置下拉刷新获取数据
        main_rfl_content.setOnRefreshListener {
            mainActionCreator.getOverAll()
        }
        //数据变化显示数据

        rxStore?.overAllLiveData?.observe(this, Observer {
            main_tv_note1.text = it.note1
            main_tv_note2.text = it.note2
            main_tv_note3.text = it.note3
//            main_tv_remark1.text = it.remark1
//            main_tv_remark2.text = it.remark2
//            main_tv_remark3.text = it.remark3
            main_tv_current.text = getString(R.string.ncov_count_current) + "\n" + it.currentConfirmedCount.toString()
            main_tv_suspected.text = getString(R.string.ncov_count_suspected) + "\n" + it.suspectedCount.toString()
            main_tv_serious.text = getString(R.string.ncov_count_serious) + "\n" + it.seriousCount.toString()
            main_tv_confirmed.text = getString(R.string.ncov_count_confirmed) + "\n" + it.confirmedCount.toString()
            main_tv_dead.text = getString(R.string.ncov_count_dead) + "\n" + it.deadCount.toString()
            main_tv_cured.text = getString(R.string.ncov_count_cured) + "\n" + it.curedCount.toString()
        })
    }

    override fun onResume() {
        super.onResume()
        if (rxStore?.overAllLiveData?.value == null) {
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