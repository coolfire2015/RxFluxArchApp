package com.huyingbao.module.common.ui.update.action

import com.huyingbao.core.arch.scope.ActivityScope
import com.huyingbao.core.arch.scope.FragmentScope
import com.huyingbao.core.progress.DownloadApi
import com.huyingbao.core.progress.ProgressInterceptor
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory

/**
 * 基础依赖注入仓库，所有子模块中的AppModule中需要包括该类，实现Store注入和Dagger.Android注入
 *
 * Created by liujunfeng on 2019/1/1.
 */
@Module
class UpdateModule {
    @Provides
    fun provideDownloadApi(
            builder: OkHttpClient.Builder,
            progressInterceptor: ProgressInterceptor
    ): DownloadApi {
        //初始化OkHttp
        val client = builder
                .addInterceptor(progressInterceptor)
                .retryOnConnectionFailure(true)
                .build()
        //初始化Retrofit
        val retrofit = Retrofit.Builder()
                .baseUrl("https://www.debug.com/")
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
        return retrofit.create(DownloadApi::class.java)
    }
}