package com.huyingbao.module.epidemic.module

import androidx.lifecycle.ViewModelProvider
import com.google.gson.GsonBuilder
import com.huyingbao.module.common.app.CommonAppModule
import com.huyingbao.module.epidemic.app.EpidemicAppModule
import com.huyingbao.module.epidemic.ui.main.store.MainStore
import dagger.Component
import dagger.Module
import dagger.Provides
import it.cosenonjaviste.daggermock.DaggerMock
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * 全局静态方法,提供依赖注入器实例对象。
 *
 * Created by liujunfeng on 2019/7/1.
 */
object EpidemicMockUtils {
    var epidemicTestComponent: EpidemicTestComponent? = null
}

/**
 * 依赖注入器,为测试代码提供方便测试的全局对象。
 */
@Singleton
@Component(modules = [EpidemicTestModule::class])
interface EpidemicTestComponent {
    /**
     * 提供实际创建的工具对象
     */
    val retrofit: Retrofit
}

/**
 * 依赖注入仓库
 *
 * 1.提供[org.mockito.Spy]和[org.mockito.Mock]对象
 *
 * 2.提供测试代码需要的全局对象
 */
@Module(includes = [EpidemicAppModule::class])
class EpidemicTestModule {
    /**
     * 返回实际创建的[MainStore]实例对象，但是DaggerMock会返回虚拟的Mock对象，参见[epidemicMockDaggerRule]方法。
     * 如果[EpidemicTestComponent]不定义该对象，该方法不会被调用。
     *
     * @param rxStoreFactory 来自[CommonAppModule]，[MainStore]对象来自[EpidemicAppModule]
     */
    @Singleton
    @Provides
    fun provideMainStore(rxStoreFactory: ViewModelProvider.Factory): MainStore {
        return rxStoreFactory.create(MainStore::class.java)
    }

    /**
     * 初始化Retrofit，覆盖[EpidemicAppModule.provideRetrofit]方法
     *
     * @param builder 来自[CommonAppModule]
     */
    @Singleton
    @Provides
    fun provideRetrofit(builder: OkHttpClient.Builder): Retrofit {
        val retrofitBuilder = Retrofit.Builder()
                .baseUrl(EpidemicAppModule.BASE_API)
                .addConverterFactory(GsonConverterFactory.create(GsonBuilder().serializeNulls().create()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(builder.build())
        return retrofitBuilder.build()
    }
}

/**
 * 动态创建 Module 子类的 JUnit 规则，初始化一个测试类里面的所有用@Mock field为mock对象。
 *
 * 完成的操作：要使用哪个Module、要build哪个Component、要把build好的Component放到哪
 *
 * 1.动态创建了一个[EpidemicTestModule]的子类，返回在测试中定义并使用[org.mockito.Mock]和[org.mockito.Spy]标注的虚拟对象 ，而不是真实的对象。
 *
 * 2.Mock [EpidemicTestModule]，通过反射的方式得到[EpidemicTestModule]的所有[dagger.Provides]方法，如果有某个方法的返回值是[org.mockito.Mock]和[org.mockito.Spy]标注的虚拟对象，
 * 那么就使用Mockito，让这个[dagger.Provides]方法被调用时，返回虚拟对象。
 *
 * 3.使用[EpidemicTestModule]来构建一个[EpidemicTestComponent]，并且放到[EpidemicMockUtils]里面去。
 */
fun epidemicMockDaggerRule() = DaggerMock.rule<EpidemicTestComponent>(EpidemicTestModule()) {
    set {
        EpidemicMockUtils.epidemicTestComponent = it
    }
}