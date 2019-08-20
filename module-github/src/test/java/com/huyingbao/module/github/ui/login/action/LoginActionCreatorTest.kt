package com.huyingbao.module.github.ui.login.action

import com.huyingbao.module.github.BuildConfig
import com.huyingbao.module.github.app.GithubAppStore
import com.huyingbao.module.github.module.GithubMockUtils
import com.huyingbao.module.github.module.githubMockDaggerRule
import com.huyingbao.module.github.ui.login.store.LoginStore
import com.huyingbao.module.github.ui.login.view.LoginActivity
import com.huyingbao.core.test.subscriber.BaseSubscriberTest
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.verify
import org.junit.Before
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito

/**
 * Created by liujunfeng on 2019/4/3.
 */
class LoginActionCreatorTest : BaseSubscriberTest() {
    /**
     * 初始化DaggerMock
     */
    @get:Rule
    var mockDaggerRule = githubMockDaggerRule()

    private var loginStore: LoginStore = Mockito.mock(LoginStore::class.java)
    private var githubAppStore: GithubAppStore = Mockito.mock(GithubAppStore::class.java)
    private var loginActivity: LoginActivity = Mockito.mock(LoginActivity::class.java)

    private var loginActionCreator: LoginActionCreator? = null

    override fun getSubscriberList(): List<Any> {
        return listOfNotNull(loginStore, githubAppStore, loginActivity)
    }

    @Before
    @Throws(Exception::class)
    fun setUp() {
        loginActionCreator = LoginActionCreator(rxDispatcher, rxActionManager, GithubMockUtils.githubTestComponent!!.retrofit)
    }

    @Ignore("此方法会修改Auth Token，需先获取Log中的Token，放到MockModule中")
    @Test
    fun login() {
        //调用错误登录方法
        loginActionCreator?.login("asdf", "pwd")
        //验证方法失败，发送RxError
        verify(rxDispatcher).postRxError(any())
        //调用错误登录方法
        loginActionCreator?.login(BuildConfig.GIT_NAME, BuildConfig.GIT_PWD)
        //验证方法调用成功，发送RxAction
        verify(rxDispatcher).postRxAction(any())
        //验证store接收RxAction
        verify(loginStore).onLogin(any())
    }
}