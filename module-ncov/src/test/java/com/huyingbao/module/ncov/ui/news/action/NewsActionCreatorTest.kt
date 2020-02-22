package com.huyingbao.module.ncov.ui.news.action

import com.huyingbao.core.test.subscriber.BaseSubscriberTest
import com.huyingbao.module.ncov.module.NcovMockUtils
import com.huyingbao.module.ncov.module.ncovMockDaggerRule
import com.huyingbao.module.ncov.ui.news.store.NewsStore
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.verify
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito

class NewsActionCreatorTest : BaseSubscriberTest() {
    /**
     * 初始化DaggerMock
     */
    @get:Rule
    var mockDaggerRule = ncovMockDaggerRule()

    private var newsStore: NewsStore = Mockito.mock(NewsStore::class.java)

    private var newsActionCreator: NewsActionCreator? = null

    override fun getSubscriberList(): List<Any> {
        return listOfNotNull(newsStore)
    }

    @Before
    fun setUp() {
        newsActionCreator = NewsActionCreator(rxDispatcher, rxActionManager, NcovMockUtils.ncovTestComponent!!.newsApi)
    }


    @Test
    fun getNews() {
        newsActionCreator?.getNews(1)
        verify(rxDispatcher).postRxAction(any())
        verify(newsStore).onGetNews(any())
    }
}