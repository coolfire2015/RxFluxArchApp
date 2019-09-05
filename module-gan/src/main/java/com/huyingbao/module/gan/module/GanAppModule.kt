package com.huyingbao.module.gan.module

import com.google.gson.GsonBuilder
import com.huyingbao.module.common.app.CommonAppModule
import com.huyingbao.module.gan.action.GanApi
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * Module其实是一个简单工厂模式，Module里面的方法基本都是创建类实例的方法。
 *
 *
 * Created by liujunfeng on 2019/1/1.
 */
@Module(includes = [
    GanInjectActivityModule::class,
    GanStoreModule::class,
    CommonAppModule::class])
class GanAppModule {
    @Singleton
    @Provides
    fun provideGanApi(builder: OkHttpClient.Builder): GanApi {
        val retrofit = Retrofit.Builder()
                .baseUrl("https://gank.io/api/")
                .addConverterFactory(GsonConverterFactory.create(GsonBuilder().serializeNulls().create()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(builder.build())
                .build()
        return retrofit.create(GanApi::class.java)
    }
}
