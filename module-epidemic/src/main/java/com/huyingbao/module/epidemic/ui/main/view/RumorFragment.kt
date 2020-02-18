package com.huyingbao.module.epidemic.ui.main.view

import android.os.Bundle
import com.huyingbao.core.base.flux.fragment.BaseFluxFragment
import com.huyingbao.module.epidemic.R
import com.huyingbao.module.epidemic.ui.main.store.MainStore

class RumorFragment : BaseFluxFragment<MainStore>() {
    companion object {
        fun newInstance() = RumorFragment()
    }

    override fun getLayoutId() = R.layout.epidemic_fragment_rumor

    override fun afterCreate(savedInstanceState: Bundle?) {
    }
}
