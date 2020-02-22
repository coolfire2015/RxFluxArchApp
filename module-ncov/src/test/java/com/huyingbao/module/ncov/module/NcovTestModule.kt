package com.huyingbao.module.ncov.module

import androidx.lifecycle.ViewModelProvider
import com.huyingbao.module.common.app.CommonAppModule
import com.huyingbao.module.ncov.app.DingApi
import com.huyingbao.module.ncov.app.NcovAppModule
import com.huyingbao.module.ncov.app.NewsApi
import com.huyingbao.module.ncov.ui.main.store.MainStore
import com.huyingbao.module.ncov.ui.news.store.NewsStore
import dagger.Component
import dagger.Module
import dagger.Provides
import it.cosenonjaviste.daggermock.DaggerMock
import javax.inject.Singleton

/**
 * 全局静态方法,提供依赖注入器实例对象。
 *
 * Created by liujunfeng on 2019/7/1.
 */
object NcovMockUtils {
    var ncovTestComponent: NcovTestComponent? = null
}

/**
 * 依赖注入器,为测试代码提供方便测试的全局对象。
 */
@Singleton
@Component(modules = [NcovTestModule::class])
interface NcovTestComponent {
    /**
     * 提供实际创建的工具对象
     */
    val dingApi: DingApi
    val newsApi: NewsApi
}

/**
 * 依赖注入仓库
 *
 * 1.提供[org.mockito.Spy]和[org.mockito.Mock]对象
 *
 * 2.提供测试代码需要的全局对象
 */
@Module(includes = [NcovAppModule::class])
class NcovTestModule {
    /**
     * 返回实际创建的[MainStore]实例对象，但是DaggerMock会返回虚拟的Mock对象，参见[ncovMockDaggerRule]方法。
     * 如果[NcovTestComponent]不定义该对象，该方法不会被调用。
     *
     * @param rxStoreFactory 来自[CommonAppModule]，[MainStore]对象来自[NcovAppModule]
     */
    @Singleton
    @Provides
    fun provideMainStore(rxStoreFactory: ViewModelProvider.Factory): MainStore {
        return rxStoreFactory.create(MainStore::class.java)
    }

    @Singleton
    @Provides
    fun provideNewsStore(rxStoreFactory: ViewModelProvider.Factory): NewsStore {
        return rxStoreFactory.create(NewsStore::class.java)
    }
}

/**
 * 动态创建 Module 子类的 JUnit 规则，初始化一个测试类里面的所有用@Mock field为mock对象。
 *
 * 完成的操作：要使用哪个Module、要build哪个Component、要把build好的Component放到哪
 *
 * 1.动态创建了一个[NcovTestModule]的子类，返回在测试中定义并使用[org.mockito.Mock]和[org.mockito.Spy]标注的虚拟对象 ，而不是真实的对象。
 *
 * 2.Mock [NcovTestModule]，通过反射的方式得到[NcovTestModule]的所有[dagger.Provides]方法，如果有某个方法的返回值是[org.mockito.Mock]和[org.mockito.Spy]标注的虚拟对象，
 * 那么就使用Mockito，让这个[dagger.Provides]方法被调用时，返回虚拟对象。
 *
 * 3.使用[NcovTestModule]来构建一个[NcovTestComponent]，并且放到[NcovMockUtils]里面去。
 */
fun ncovMockDaggerRule() = DaggerMock.rule<NcovTestComponent>(NcovTestModule()) {
    set {
        NcovMockUtils.ncovTestComponent = it
    }
}