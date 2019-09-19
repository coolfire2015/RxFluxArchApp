package com.huyingbao.module.gan.ui.random.view

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.annotation.Route
import com.google.android.material.appbar.AppBarLayout
import com.huyingbao.core.base.flux.activity.BaseFluxFragActivity
import com.huyingbao.module.common.app.CommonAppAction
import com.huyingbao.module.common.app.CommonAppConstants
import com.huyingbao.module.common.utils.addFloatingActionButton
import com.huyingbao.module.common.utils.setAppBarElevation
import com.huyingbao.module.common.utils.setAppBarScroll
import com.huyingbao.module.gan.R
import com.huyingbao.module.gan.ui.random.store.RandomStore
import org.jetbrains.anko.find


/**
 * Created by liujunfeng on 2019/1/1.
 */
@Route(path = CommonAppConstants.Router.RandomActivity)
class RandomActivity : BaseFluxFragActivity<RandomStore>() {
    override fun createFragment(): Fragment? {
        return CategoryFragment.newInstance()
    }

    override fun afterCreate(savedInstanceState: Bundle?) {
        //消除阴影
        find<AppBarLayout>(R.id.abl_top).setAppBarElevation(0f)
        //设置联动
        find<Toolbar>(R.id.tlb_top).setAppBarScroll()
        //添加FloatingActionButton
        find<CoordinatorLayout>(R.id.cdl_content)
                .addFloatingActionButton(this, View.OnClickListener {
                    baseActionCreator.postLocalChange(CommonAppAction.SCROLL_TO_TOP)
                })
    }
}
