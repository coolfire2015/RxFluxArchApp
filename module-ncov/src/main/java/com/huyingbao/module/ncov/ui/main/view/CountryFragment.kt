package com.huyingbao.module.ncov.ui.main.view

import android.os.Bundle
import com.huyingbao.core.base.flux.fragment.BaseFluxFragment
import com.huyingbao.module.ncov.R
import com.huyingbao.module.ncov.ui.main.store.MainStore

class CountryFragment : BaseFluxFragment<MainStore>() {
    companion object {
        fun newInstance() = CountryFragment()
    }

    override fun getLayoutId() = R.layout.ncov_fragment_country

    override fun afterCreate(savedInstanceState: Bundle?) {
    }
}
