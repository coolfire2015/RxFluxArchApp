package com.huyingbao.module.github.ui.code.view

import android.os.Bundle
import com.huyingbao.core.base.flux.fragment.BaseFluxFragment
import com.huyingbao.module.github.R
import com.huyingbao.module.github.ui.code.store.CodeStore

/**
 * 代码模块
 *
 * Created by liujunfeng on 2019/6/10.
 */
class CodeFragment : BaseFluxFragment<CodeStore>() {
    companion object {
        fun newInstance()=CodeFragment()
    }

    override fun getLayoutId() = R.layout.github_fragment_code

    override fun afterCreate(savedInstanceState: Bundle?) {
    }
}
