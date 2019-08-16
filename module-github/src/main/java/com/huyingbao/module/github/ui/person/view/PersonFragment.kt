package com.huyingbao.module.github.ui.person.view

import android.os.Bundle
import com.huyingbao.core.base.flux.fragment.BaseFluxFragment
import com.huyingbao.module.github.R
import com.huyingbao.module.github.ui.person.store.PersonStore

/**
 * 用户模块
 *
 * Created by liujunfeng on 2019/6/10.
 */
class PersonFragment : BaseFluxFragment<PersonStore>() {
    companion object {
        fun newInstance(): PersonFragment {
            return PersonFragment()
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.github_fragment_person
    }

    override fun afterCreate(savedInstanceState: Bundle?) {
    }
}
