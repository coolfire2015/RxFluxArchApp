package com.huyingbao.module.github.ui.main.view

import android.os.Bundle
import com.huyingbao.core.base.flux.fragment.BaseFluxFragment
import com.huyingbao.module.github.R
import com.huyingbao.module.github.ui.main.store.MainStore

/**
 * 我的页面
 *
 * Created by liujunfeng on 2019/6/10.
 */
class MineFragment : BaseFluxFragment<MainStore>() {
    companion object {
        fun newInstance()=MineFragment()
    }

    override fun getLayoutId() = R.layout.github_fragment_mine

    override fun afterCreate(savedInstanceState: Bundle?) {
    }
}