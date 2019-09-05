package com.huyingbao.module.common.ui.update.module

import com.huyingbao.core.progress.DownloadApi
import com.huyingbao.core.progress.ProgressInterceptor
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory

/**
 * [com.huyingbao.module.common.ui.update.view.CommonUpdateDialog]的依赖注入仓库
 *
 * Created by liujunfeng on 2019/1/1.
 */
@Module
class CommonUpdateDialogModule {
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