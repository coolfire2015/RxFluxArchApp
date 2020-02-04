package com.huyingbao.module.epidemic.ui.main.view

import android.os.Bundle
import com.huyingbao.core.base.flux.fragment.BaseFluxFragment
import com.huyingbao.module.epidemic.R
import com.huyingbao.module.epidemic.ui.main.store.MainStore

class MainFragment : BaseFluxFragment<MainStore>() {
    companion object {
        fun newInstance(): MainFragment {
            return MainFragment()
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.epidemic_fragment_main
    }

    override fun afterCreate(savedInstanceState: Bundle?) {
    }
}
