package com.huyingbao.module.github.ui.main.action

import com.huyingbao.core.test.subscriber.BaseSubscriberTest
import com.huyingbao.module.common.app.CommonAppStore
import com.huyingbao.module.github.BuildConfig
import com.huyingbao.module.github.app.GithubAppStore
import com.huyingbao.module.github.module.GithubMockUtils
import com.huyingbao.module.github.module.githubMockDaggerRule
import com.huyingbao.module.github.ui.main.store.MainStore
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.verify
import org.junit.Before
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito

class MainActionCreatorTest : BaseSubscriberTest() {
    /**
     * 初始化DaggerMock
     */
    @get:Rule
    var mockDaggerRule = githubMockDaggerRule()

    private var mainStore: MainStore = Mockito.mock(MainStore::class.java)
    private var githubAppStore: GithubAppStore = Mockito.mock(GithubAppStore::class.java)
    private var commonAppStore: CommonAppStore = Mockito.mock(CommonAppStore::class.java)

    private var mainActionCreator: MainActionCreator? = null

    override fun getSubscriberList(): List<Any> {
        return listOfNotNull(mainStore, githubAppStore, commonAppStore)
    }

    @Before
    fun setUp() {
        mainActionCreator = MainActionCreator(rxDispatcher, rxActionManager, GithubMockUtils.githubTestComponent!!.getRetrofit())
        mainActionCreator?.firApi = GithubMockUtils.githubTestComponent!!.getFirApi()
    }

    @Ignore("不需要向仓库中提交issue")
    @Test
    fun feedback() {
        mainActionCreator?.feedback("test")
        verify(rxDispatcher).postRxAction(any())
    }

    @Test
    fun getNewsEvent() {
        mainActionCreator?.getNewsEvent("coolfire2015", 1)
        verify(rxDispatcher).postRxAction(any())
        verify(mainStore).onGetNewsEvent(any())
    }

    @Test
    fun getTrendData() {
        mainActionCreator?.getTrendData("Kotlin", "monthly")
        verify(mainStore).onGetTrend(any())
    }

    @Test
    fun getAppLatest() {
        mainActionCreator?.getAppLatest(BuildConfig.FIR_ID, BuildConfig.FIR_TOKEN)
        verify(commonAppStore).onGetAppLatest(any())
    }
}