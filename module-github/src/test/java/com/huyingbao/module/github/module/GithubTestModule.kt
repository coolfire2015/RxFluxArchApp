package com.huyingbao.module.github.module

import android.text.TextUtils
import com.huyingbao.core.test.annotations.InTest
import com.huyingbao.module.common.app.CommonAppStore
import com.huyingbao.module.common.app.CommonConstants
import com.huyingbao.module.common.app.CommonModule
import com.huyingbao.module.common.ui.update.action.FirApi
import com.huyingbao.module.github.app.GithubAppStore
import com.huyingbao.module.github.app.GithubContants
import com.huyingbao.module.github.ui.login.store.LoginStore
import com.huyingbao.module.github.ui.main.store.MainStore
import dagger.Component
import dagger.Module
import dagger.Provides
import it.cosenonjaviste.daggermock.DaggerMock
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import org.mockito.Mockito
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import javax.inject.Singleton

/**
 * 全局静态方法,提供依赖注入器实例对象。
 *
 * Created by liujunfeng on 2019/7/1.
 */
object GithubMockUtils {
    var githubTestComponent: GithubTestComponent? = null
}

/**
 * 依赖注入器,为测试代码提供方便测试的全局对象。
 */
@Singleton
@Component(modules = [GithubTestModule::class])
interface GithubTestComponent {
    /**
     * 提供实际创建的工具对象
     */
    @InTest
    fun getRetrofit(): Retrofit

    fun getFirApi(): FirApi
}

/**
 * 依赖注入仓库
 *
 * 1.提供[org.mockito.Spy]和[org.mockito.Mock]对象
 *
 * 2.提供测试代码需要的全局对象
 */
@Module(includes = [GithubAppModule::class])
class GithubTestModule {
    /**
     * 初始化Retrofit
     *
     * @param builder 来自[CommonModule]
     */
    @Singleton
    @Provides
    @InTest
    fun provideRetrofit(builder: OkHttpClient.Builder): Retrofit {
        //Head拦截器
        val headInterceptor = Interceptor { chain ->
            var request = chain.request()
            val token = request.header(CommonConstants.Header.AUTHORIZATION)
            if (TextUtils.isEmpty(token)) {
                //Header中添加Authorization token数据
                val url = request.url.toString()
                val requestBuilder = request.newBuilder()
                        .addHeader(CommonConstants.Header.AUTHORIZATION, "token 30052ef12d567c24863875805e76e8ac6ffde286")
                        .url(url)
                request = requestBuilder.build()
            }
            chain.proceed(request)
        }
        //初始化OkHttp，单元测试中不添加拦截器PageInfoInterceptor()
        builder.addInterceptor(headInterceptor)
        //初始化Retrofit
        val retrofitBuilder = Retrofit.Builder()
                .baseUrl(GithubContants.Url.BASE_API)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(builder.build())
        return retrofitBuilder.build()
    }

    @Singleton
    @Provides
    fun provideCommonAppStore(): CommonAppStore {
        return Mockito.mock(CommonAppStore::class.java)
    }

    @Singleton
    @Provides
    fun provideGithubAppStore(): GithubAppStore {
        return Mockito.mock(GithubAppStore::class.java)
    }

    @Singleton
    @Provides
    fun provideLoginStore(): LoginStore {
        return Mockito.mock(LoginStore::class.java)
    }

    @Singleton
    @Provides
    fun provideMainStore(): MainStore {
        return Mockito.mock(MainStore::class.java)
    }
}

/**
 * 动态创建 Module 子类的 JUnit 规则，初始化一个测试类里面的所有用@Mock field为mock对象。
 *
 * 完成的操作：要使用哪个Module、要build哪个Component、要把build好的Component放到哪
 *
 * 1.动态创建了一个[GithubTestModule]的子类，返回在测试中定义并使用[org.mockito.Mock]和[org.mockito.Spy]标注的虚拟对象 ，而不是真实的对象。
 *
 * 2.Mock [GithubTestModule]，通过反射的方式得到[GithubTestModule]的所有[dagger.Provides]方法，如果有某个方法的返回值是[org.mockito.Mock]和[org.mockito.Spy]标注的虚拟对象，
 * 那么就使用Mockito，让这个[dagger.Provides]方法被调用时，返回虚拟对象。
 *
 * 3.使用[GithubTestModule]来构建一个[GithubTestComponent]，并且放到[GithubMockUtils]里面去。
 */
fun githubMockDaggerRule() = DaggerMock.rule<GithubTestComponent>(GithubTestModule()) {
    set {
        GithubMockUtils.githubTestComponent = it
    }
}
