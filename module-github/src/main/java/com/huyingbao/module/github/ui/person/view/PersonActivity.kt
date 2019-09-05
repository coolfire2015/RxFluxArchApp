package com.huyingbao.module.github.ui.person.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.annotation.Route
import com.huyingbao.core.base.flux.activity.BaseFluxFragActivity
import com.huyingbao.module.common.app.CommonAppConstants
import com.huyingbao.module.github.ui.person.store.PersonStore

/**
 * 用户模块
 *
 * Created by liujunfeng on 2019/6/10.
 */
@Route(path = CommonAppConstants.Router.PersonActivity)
class PersonActivity : BaseFluxFragActivity<PersonStore>() {
    override fun createFragment(): Fragment? {
        return PersonFragment.newInstance()
    }

    override fun afterCreate(savedInstanceState: Bundle?) {
    }
}
