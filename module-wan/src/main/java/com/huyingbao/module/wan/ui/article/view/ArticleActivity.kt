package com.huyingbao.module.wan.ui.article.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.annotation.Route
import com.huyingbao.core.arch.model.RxChange
import com.huyingbao.core.base.FragmentOp
import com.huyingbao.core.base.flux.activity.BaseFluxFragActivity
import com.huyingbao.core.base.setFragment
import com.huyingbao.module.common.app.CommonAppConstants
import com.huyingbao.module.wan.R
import com.huyingbao.module.wan.ui.article.action.ArticleAction
import com.huyingbao.module.wan.ui.article.store.ArticleStore
import com.huyingbao.module.wan.ui.friend.view.FriendFragment
import org.greenrobot.eventbus.Subscribe

/**
 * Created by liujunfeng on 2019/1/1.
 */
@Route(path = CommonAppConstants.CommonRouter.ArticleActivity)
class ArticleActivity : BaseFluxFragActivity<ArticleStore>() {
    override fun createFragment(): Fragment? {
        return ArticleListFragment.newInstance()
    }

    override fun afterCreate(savedInstanceState: Bundle?) {}

    @Subscribe(tags = [ArticleAction.TO_FRIEND], sticky = true)
    fun toFriend(rxChange: RxChange) {
        setFragment(R.id.fl_container, FriendFragment.newInstance(), FragmentOp.OP_HIDE)
    }

    @Subscribe(tags = [ArticleAction.TO_BANNER], sticky = true)
    fun toBanner(rxChange: RxChange) {
        setFragment(R.id.fl_container, BannerFragment.newInstance(), FragmentOp.OP_HIDE)
    }
}
