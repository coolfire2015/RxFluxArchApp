package com.huyingbao.module.github.ui.repos.view

import android.os.Bundle
import com.huyingbao.core.base.flux.fragment.BaseFluxFragment
import com.huyingbao.module.github.R
import com.huyingbao.module.github.ui.repos.store.ReposStore

class ReposFragment : BaseFluxFragment<ReposStore>() {
    companion object {
        fun newInstance()= ReposFragment()
    }

    override fun getLayoutId() = R.layout.github_fragment_repos

    override fun afterCreate(savedInstanceState: Bundle?) {
    }
}
