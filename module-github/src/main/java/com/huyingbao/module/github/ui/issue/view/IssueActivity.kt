package com.huyingbao.module.github.ui.issue.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.annotation.Route
import com.huyingbao.core.base.flux.activity.BaseFluxFragActivity
import com.huyingbao.module.common.app.CommonAppConstants
import com.huyingbao.module.github.ui.issue.store.IssueStore

/**
 * 问题模块
 *
 * Created by liujunfeng on 2019/6/10.
 */
@Route(path = CommonAppConstants.CommonRouter.IssueActivity)
class IssueActivity : BaseFluxFragActivity<IssueStore>() {
    override fun createFragment(): Fragment? {
        return IssueFragment.newInstance()
    }

    override fun afterCreate(savedInstanceState: Bundle?) {
    }
}
