package com.huyingbao.module.github.ui.login.action

import com.huyingbao.core.test.subscriber.BaseSubscriberTest
import com.huyingbao.module.common.app.CommonAppStore
import com.huyingbao.module.github.BuildConfig
import com.huyingbao.module.github.app.GithubAppStore
import com.huyingbao.module.github.module.GithubMockUtils
import com.huyingbao.module.github.module.githubMockDaggerRule
import com.huyingbao.module.github.ui.login.store.LoginStore
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.times
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
    private var commonAppStore: CommonAppStore = Mockito.mock(CommonAppStore::class.java)

    private var loginActionCreator: LoginActionCreator? = null

    override fun getSubscriberList(): List<Any> {
        return listOfNotNull(loginStore, githubAppStore, commonAppStore)
    }

    @Before
    @Throws(Exception::class)
    fun setUp() {
        loginActionCreator = LoginActionCreator(rxDispatcher, rxActionManager, GithubMockUtils.githubTestComponent!!.getRetrofit())
    }

    @Ignore("此方法会修改Auth Token，需先获取Log中的Token，放到MockModule中")
    @Test
    fun login() {
        //调用登录方法，输入错误用户名和密码
        loginActionCreator?.login("asdf", "pwd")
        //验证全局处理Loading
        verify(commonAppStore, times(2)).onRxLoading(any())
        //验证全局处理Error
        verify(commonAppStore).onRxError(any())

        //调用登录方法，输入正确用户名和密码
        loginActionCreator?.login(BuildConfig.GIT_NAME, BuildConfig.GIT_PWD)
        //验证正确接收数据
        verify(loginStore).onLogin(any())
    }
}