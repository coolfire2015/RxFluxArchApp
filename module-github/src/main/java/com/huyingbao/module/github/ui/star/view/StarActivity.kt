package com.huyingbao.module.github.ui.star.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.annotation.Route
import com.huyingbao.core.base.flux.activity.BaseFluxFragActivity
import com.huyingbao.module.common.app.CommonRouter
import com.huyingbao.module.github.ui.star.store.StarStore

/**
 * 点赞
 *
 * Created by liujunfeng on 2019/6/10.
 */
@Route(path = CommonRouter.StarActivity)
class StarActivity : BaseFluxFragActivity<StarStore>() {
    override fun createFragment(): Fragment? {
        return StarFragment.newInstance()
    }

    override fun afterCreate(savedInstanceState: Bundle?) {
    }
}
