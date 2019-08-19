package com.huyingbao.module.github.module

import android.text.TextUtils
import com.huyingbao.module.common.app.CommonConstants
import com.huyingbao.module.common.app.CommonModule
import com.huyingbao.module.github.app.GithubContants
import dagger.Component
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import javax.inject.Singleton

/**
 * 依赖注入器,为测试代码提供方便测试的全局对象。
 *
 * Created by liujunfeng on 2019/7/1.
 */
@Singleton
@Component(modules = [MockModule::class])
interface MockComponent {
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
@Module(includes = [CommonModule::class])
class MockModule {
    /**
     * 初始化Retrofit
     *
     * @param builder 来自[CommonModule]
     */
    @Singleton
    @Provides
    fun provideRetrofit(builder: OkHttpClient.Builder): Retrofit {
        //Head拦截器
        val headInterceptor = Interceptor { chain ->
            var request = chain.request()
            val token = request.header(CommonConstants.Header.AUTHORIZATION)
            if (TextUtils.isEmpty(token)) {
                //Header中添加Authorization token数据
                val url = request.url.toString()
                val requestBuilder = request.newBuilder()
                        .addHeader(CommonConstants.Header.AUTHORIZATION, "token 8006a8ae5ed083f2aa8eee59e478e09032bd1ece")
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

    /**
     * 初始化根Url
     */
    private fun initBaseUrl(): String {
        return GithubContants.Url.BASE_API
    }
}
