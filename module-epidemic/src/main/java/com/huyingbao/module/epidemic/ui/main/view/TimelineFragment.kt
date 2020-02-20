package com.huyingbao.module.epidemic.ui.main.view

import android.os.Bundle
import com.huyingbao.core.base.flux.fragment.BaseFluxFragment
import com.huyingbao.module.epidemic.R
import com.huyingbao.module.epidemic.ui.main.store.MainStore

/**
 * 时间线
 */
class TimelineFragment : BaseFluxFragment<MainStore>() {
    companion object {
        fun newInstance() = TimelineFragment()
    }

    override fun getLayoutId() = R.layout.epidemic_fragment_timeline

    override fun afterCreate(savedInstanceState: Bundle?) {
    }
}
