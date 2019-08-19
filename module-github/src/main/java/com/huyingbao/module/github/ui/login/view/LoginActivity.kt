package com.huyingbao.module.github.ui.login.view

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import com.alibaba.android.arouter.facade.annotation.Route
import com.huyingbao.core.arch.model.RxChange
import com.huyingbao.core.base.flux.activity.BaseFluxActivity
import com.huyingbao.core.base.initFragment
import com.huyingbao.module.common.app.CommonRouter
import com.huyingbao.module.github.R
import com.huyingbao.module.github.app.GithubAppStore
import com.huyingbao.module.github.ui.login.action.LoginAction
import com.huyingbao.module.github.ui.login.action.LoginActionCreator
import com.huyingbao.module.github.ui.login.store.LoginStore
import com.huyingbao.module.github.ui.main.view.MainActivity
import org.greenrobot.eventbus.Subscribe
import javax.inject.Inject

/**
 * 登录页面，使用singleTask模式启动，在登录失效，跳转该页面时，清空ActivityTask中的Activity
 *
 * Created by liujunfeng on 2019/1/1.
 */
@Route(path = CommonRouter.LoginActivity)
class LoginActivity : BaseFluxActivity<LoginStore>() {
    @Inject
    lateinit var loginActionCreator: LoginActionCreator

    @Inject
    lateinit var githubAppStore: GithubAppStore

    override fun getLayoutId(): Int {
        return R.layout.base_activity_frag
    }

    override fun afterCreate(savedInstanceState: Bundle?) {
        if (TextUtils.isEmpty(githubAppStore.getAccessToken())) {//未登录，显示登录页面
            initFragment(R.id.fl_container, LoginFragment.newInstance())
        } else {//已登录，获取当前登录用户信息，跳转主页面
            //获取当前登录用户信息
            loginActionCreator.getLoginUserInfo()
            //跳转主页面
            toMainActivity(null)
        }
    }

    /**
     * 登录成功，跳转主页面
     */
    @Subscribe(tags = [LoginAction.LOGIN], sticky = true)
    fun toMainActivity(rxChange: RxChange?) {
        loginActionCreator.getLoginUserInfo()
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}
