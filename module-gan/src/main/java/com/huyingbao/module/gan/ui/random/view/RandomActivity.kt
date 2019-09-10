package com.huyingbao.module.gan.ui.random.view

import android.os.Bundle
import androidx.fragment.app.Fragment

import com.alibaba.android.arouter.facade.annotation.Route
import com.huyingbao.core.arch.model.RxChange
import com.huyingbao.core.base.FragmentOp
import com.huyingbao.core.base.flux.activity.BaseFluxFragActivity
import com.huyingbao.core.base.setFragment
import com.huyingbao.module.common.app.CommonAppConstants
import com.huyingbao.module.gan.R
import com.huyingbao.module.gan.ui.random.action.RandomAction
import com.huyingbao.module.gan.ui.random.store.RandomStore

import org.greenrobot.eventbus.Subscribe

/**
 * Created by liujunfeng on 2019/1/1.
 */
@Route(path = CommonAppConstants.Router.RandomActivity)
class RandomActivity : BaseFluxFragActivity<RandomStore>() {
    override fun createFragment(): Fragment? {
        return CategoryFragment.newInstance()
    }

    override fun afterCreate(savedInstanceState: Bundle?) {}

    /**
     * 跳转对应类别的列表数据页面
     */
    @Subscribe(tags = [RandomAction.TO_SHOW_DATA], sticky = true)
    fun toShowData(rxChange: RxChange) {
        setFragment(R.id.fl_container, ArticleListFragment.newInstance(), FragmentOp.OP_HIDE)
    }
}
