package com.huyingbao.module.epidemic.ui.news.action

import com.huyingbao.core.test.subscriber.BaseSubscriberTest
import com.huyingbao.module.epidemic.module.EpidemicMockUtils
import com.huyingbao.module.epidemic.module.epidemicMockDaggerRule
import com.huyingbao.module.epidemic.ui.news.store.NewsStore
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
    var mockDaggerRule = epidemicMockDaggerRule()

    private var newsStore: NewsStore = Mockito.mock(NewsStore::class.java)

    private var newsActionCreator: NewsActionCreator? = null

    override fun getSubscriberList(): List<Any> {
        return listOfNotNull(newsStore)
    }

    @Before
    fun setUp() {
        newsActionCreator = NewsActionCreator(rxDispatcher, rxActionManager, EpidemicMockUtils.epidemicTestComponent!!.newsApi)
    }


    @Test
    fun getNews() {
        newsActionCreator?.getNews(1)
        verify(rxDispatcher).postRxAction(any())
        verify(newsStore).onGetNews(any())
    }
}