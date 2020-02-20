package com.huyingbao.module.epidemic.ui.main.action

import com.huyingbao.core.test.subscriber.BaseSubscriberTest
import com.huyingbao.module.epidemic.module.EpidemicMockUtils
import com.huyingbao.module.epidemic.module.epidemicMockDaggerRule
import com.huyingbao.module.epidemic.ui.main.store.MainStore
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.verify
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito

/**
 * 实际调用接口方法，Mock[com.huyingbao.core.arch.store.RxStore]和[com.huyingbao.core.arch.view.RxSubscriberView]，
 * 验证方法调用之后[com.huyingbao.core.arch.store.RxStore]和[com.huyingbao.core.arch.view.RxSubscriberView]得到响应。
 *
 * Created by liujunfeng on 2019/3/28.
 */
class MainActionCreatorTest : BaseSubscriberTest() {
    /**
     * 初始化DaggerMock
     */
    @get:Rule
    var mockDaggerRule = epidemicMockDaggerRule()

    private var mainStore: MainStore = Mockito.mock(MainStore::class.java)

    private var mainActionCreator: MainActionCreator? = null

    override fun getSubscriberList(): List<Any> {
        return listOfNotNull(mainStore)
    }

    @Before
    fun setUp() {
        mainActionCreator = MainActionCreator(rxDispatcher, rxActionManager, EpidemicMockUtils.epidemicTestComponent!!.dingApi)
    }

    @Test
    fun getOverAll() {
        //调用接口
        mainActionCreator?.getOverAll()
        //验证接口调用成功，发送数据
        verify(rxDispatcher).postRxAction(any())
        //验证RxStore接收到数据，因为RxStore是mock的，故该方法并不会通知View更新数据
        verify(mainStore).onGetOverAll(any())
    }

    @Test
    fun getAreaData() {
        //调用接口
        mainActionCreator?.getAreaData(null)
        //验证接口调用成功，发送数据
        verify(rxDispatcher).postRxAction(any())
        //验证RxStore接收到数据，因为RxStore是mock的，故该方法并不会通知View更新数据
        verify(mainStore).onGetAreaData(any())
    }

    @Test
    fun getRumors() {
        mainActionCreator?.getRumors()
        verify(rxDispatcher).postRxAction(any())
        verify(mainStore).onGetRumors(any())
    }
}