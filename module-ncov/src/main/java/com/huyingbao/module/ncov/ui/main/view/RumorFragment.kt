package com.huyingbao.module.ncov.ui.main.view

import android.os.Bundle
import com.huyingbao.core.base.flux.fragment.BaseFluxFragment
import com.huyingbao.module.ncov.R
import com.huyingbao.module.ncov.ui.main.store.MainStore

class RumorFragment : BaseFluxFragment<MainStore>() {
    companion object {
        fun newInstance() = RumorFragment()
    }

    override fun getLayoutId() = R.layout.ncov_fragment_rumor

    override fun afterCreate(savedInstanceState: Bundle?) {
    }
}
