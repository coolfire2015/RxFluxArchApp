package com.huyingbao.module.epidemic.ui.main.view

import android.os.Bundle
import com.huyingbao.core.base.flux.fragment.BaseFluxFragment
import com.huyingbao.module.epidemic.R
import com.huyingbao.module.epidemic.ui.main.store.MainStore

class EntriesFragment : BaseFluxFragment<MainStore>() {
    companion object {
        fun newInstance() = EntriesFragment()
    }

    override fun getLayoutId() = R.layout.epidemic_fragment_entries

    override fun afterCreate(savedInstanceState: Bundle?) {
    }
}
