package com.huyingbao.module.epidemic.ui.main.view

import android.os.Bundle
import com.huyingbao.core.base.flux.fragment.BaseFluxFragment
import com.huyingbao.module.epidemic.R
import com.huyingbao.module.epidemic.ui.main.store.MainStore

class GoodsFragment : BaseFluxFragment<MainStore>() {
    companion object {
        fun newInstance() = GoodsFragment()
    }

    override fun getLayoutId() = R.layout.epidemic_fragment_goods

    override fun afterCreate(savedInstanceState: Bundle?) {
    }
}
