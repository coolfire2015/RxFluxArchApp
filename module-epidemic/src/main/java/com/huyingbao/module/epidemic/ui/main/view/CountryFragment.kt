package com.huyingbao.module.epidemic.ui.main.view

import android.os.Bundle
import com.huyingbao.core.base.flux.fragment.BaseFluxFragment
import com.huyingbao.module.epidemic.R
import com.huyingbao.module.epidemic.ui.main.store.MainStore

class CountryFragment : BaseFluxFragment<MainStore>() {
    companion object {
        fun newInstance() = CountryFragment()
    }

    override fun getLayoutId() = R.layout.epidemic_fragment_country

    override fun afterCreate(savedInstanceState: Bundle?) {
    }
}
