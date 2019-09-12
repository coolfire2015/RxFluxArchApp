package com.huyingbao.module.github.ui.login.store

import com.huyingbao.core.arch.dispatcher.RxDispatcher
import com.huyingbao.core.arch.model.RxAction
import com.huyingbao.core.arch.model.RxChange
import com.huyingbao.core.arch.store.RxActivityStore
import com.huyingbao.core.utils.LocalStorageUtils
import com.huyingbao.module.common.app.CommonAppConstants
import com.huyingbao.module.github.ui.login.action.LoginAction
import com.huyingbao.module.github.ui.login.model.AccessToken
import org.greenrobot.eventbus.Subscribe
import javax.inject.Inject
import javax.inject.Singleton

/**
 * 登录模块数据处理Store
 *
 * Created by liujunfeng on 2019/1/1.
 */
@Singleton
class LoginStore @Inject constructor(
        rxDispatcher: RxDispatcher
) : RxActivityStore(rxDispatcher) {
    @Inject
    lateinit var localStorageUtils: LocalStorageUtils

    @Subscribe(tags = [LoginAction.LOGIN])
    fun onLogin(rxAction: RxAction) {
        val token = rxAction.getResponse<AccessToken?>()?.token ?: ""
        val userName: String = rxAction[CommonAppConstants.Key.USER_NAME] ?: ""
        val password: String = rxAction[CommonAppConstants.Key.PASSWORD] ?: ""
        localStorageUtils.setValue(CommonAppConstants.Key.ACCESS_TOKEN, token)
        localStorageUtils.setValue(CommonAppConstants.Key.USER_NAME, userName)
        localStorageUtils.setValue(CommonAppConstants.Key.PASSWORD, password)
        postChange(RxChange.newInstance(rxAction.tag))
    }
}
