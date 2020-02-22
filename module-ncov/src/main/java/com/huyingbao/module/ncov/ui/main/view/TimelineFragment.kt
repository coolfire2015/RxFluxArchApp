package com.huyingbao.module.ncov.ui.main.view

import android.os.Bundle
import com.huyingbao.core.base.flux.fragment.BaseFluxFragment
import com.huyingbao.module.ncov.R
import com.huyingbao.module.ncov.ui.main.store.MainStore

/**
 * 时间线
 */
class TimelineFragment : BaseFluxFragment<MainStore>() {
    companion object {
        fun newInstance() = TimelineFragment()
    }

    override fun getLayoutId() = R.layout.ncov_fragment_timeline

    override fun afterCreate(savedInstanceState: Bundle?) {
    }
}
