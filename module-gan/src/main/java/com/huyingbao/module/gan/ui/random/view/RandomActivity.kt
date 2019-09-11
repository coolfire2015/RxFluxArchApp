package com.huyingbao.module.gan.ui.random.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.annotation.Route
import com.huyingbao.core.base.flux.activity.BaseFluxFragActivity
import com.huyingbao.module.common.app.CommonAppConstants
import com.huyingbao.module.gan.ui.random.store.RandomStore

/**
 * Created by liujunfeng on 2019/1/1.
 */
@Route(path = CommonAppConstants.Router.RandomActivity)
class RandomActivity : BaseFluxFragActivity<RandomStore>() {
    override fun createFragment(): Fragment? {
        return CategoryFragment.newInstance()
    }

    override fun afterCreate(savedInstanceState: Bundle?) {}
}
