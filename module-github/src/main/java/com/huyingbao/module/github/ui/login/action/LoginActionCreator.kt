package com.huyingbao.module.github.ui.login.action

import android.util.Base64
import com.huyingbao.core.arch.action.RxActionManager
import com.huyingbao.core.arch.dispatcher.RxDispatcher
import com.huyingbao.core.arch.scope.ActivityScope
import com.huyingbao.module.common.app.CommonAppConstants
import com.huyingbao.module.github.BuildConfig
import com.huyingbao.module.github.app.GithubActionCreator
import com.huyingbao.module.github.ui.login.model.LoginRequest
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Named

/**
 * 登录模块
 *
 * Created by liujunfeng on 2019/1/1.
 */
@ActivityScope
class LoginActionCreator @Inject constructor(
        rxDispatcher: RxDispatcher,
        rxActionManager: RxActionManager,
        @param:Named(BuildConfig.MODULE_NAME) private val retrofit: Retrofit
) : GithubActionCreator(rxDispatcher, rxActionManager), LoginAction {
    override fun login(username: String, password: String) {
        // 生成RxAction实例
        val rxAction = newRxAction(LoginAction.LOGIN,
                CommonAppConstants.Key.USER_NAME, username,
                CommonAppConstants.Key.PASSWORD, password)
        // 用户名密码转换,可以链式转换
        val basicCode = Base64
                .encodeToString("$username:$password".toByteArray(), Base64.NO_WRAP)
                .replace("\\+", "%2B")
        //调用接口
        postHttpLoadingAction(rxAction, retrofit.create(LoginApi::class.java)
                // 调用接口1：Auth认证，获取登录token
                .authorizations("Basic $basicCode", LoginRequest.generate()))
    }

    override fun getLoginUserInfo() {
        val rxAction = newRxAction(LoginAction.GET_LOGIN_USER_INFO)
        postHttpAction(rxAction, retrofit.create(LoginApi::class.java).getLoginUserInfo())
    }
}
