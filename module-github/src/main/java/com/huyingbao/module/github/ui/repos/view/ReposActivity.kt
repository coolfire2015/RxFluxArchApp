package com.huyingbao.module.github.ui.repos.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.annotation.Route
import com.huyingbao.core.base.flux.activity.BaseFluxFragActivity
import com.huyingbao.module.common.app.CommonRouter
import com.huyingbao.module.github.ui.repos.store.ReposStore

/**
 * 仓库模块
 *
 * Created by liujunfeng on 2019/6/10.
 */
@Route(path = CommonRouter.ReposActivity)
class ReposActivity : BaseFluxFragActivity<ReposStore>() {
    override fun createFragment(): Fragment? {
        return ReposFragment.newInstance()
    }

    override fun afterCreate(savedInstanceState: Bundle?) {
    }
}
