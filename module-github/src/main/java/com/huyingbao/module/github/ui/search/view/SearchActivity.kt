package com.huyingbao.module.github.ui.search.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.annotation.Route
import com.huyingbao.core.base.flux.activity.BaseFluxFragActivity
import com.huyingbao.module.common.app.CommonAppConstants
import com.huyingbao.module.github.ui.search.store.SearchStore

/**
 * 搜索模块
 *
 * Created by liujunfeng on 2019/6/10.
 */
@Route(path = CommonAppConstants.Router.SearchActivity)
class SearchActivity : BaseFluxFragActivity<SearchStore>() {
    override fun createFragment(): Fragment? {
        return SearchFragment.newInstance()
    }

    override fun afterCreate(savedInstanceState: Bundle?) {
    }
}
