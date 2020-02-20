package com.huyingbao.module.epidemic.app

import androidx.lifecycle.ViewModel
import com.huyingbao.core.arch.scope.ActivityScope
import com.huyingbao.core.arch.store.RxStoreKey
import com.huyingbao.module.common.app.CommonAppModule
import com.huyingbao.module.epidemic.ui.main.module.MainActivityModule
import com.huyingbao.module.epidemic.ui.main.store.MainStore
import com.huyingbao.module.epidemic.ui.main.view.MainActivity
import com.huyingbao.module.epidemic.ui.news.module.NewsActivityModule
import com.huyingbao.module.epidemic.ui.news.store.NewsStore
import com.huyingbao.module.epidemic.ui.news.view.NewsActivity
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * Created by liujunfeng on 2020/1/29.
 */
@Module(includes = [CommonAppModule::class])
abstract class EpidemicAppModule {
    @ContributesAndroidInjector
    abstract fun injectEpidemicAppLifecycle(): EpidemicAppLifecycle

    @ActivityScope
    @ContributesAndroidInjector(modules = [MainActivityModule::class])
    abstract fun injectMainActivity(): MainActivity

    @Singleton
    @Binds
    @IntoMap
    @RxStoreKey(MainStore::class)
    abstract fun bindMainStore(mainStore: MainStore): ViewModel

    @ActivityScope
    @ContributesAndroidInjector(modules = [NewsActivityModule::class])
    abstract fun injectNewsActivity(): NewsActivity

    @Singleton
    @Binds
    @IntoMap
    @RxStoreKey(NewsStore::class)
    abstract fun bindNewsStore(newsStore: NewsStore): ViewModel

    @Module
    companion object {
        @JvmStatic
        @Singleton
        @Provides
        fun provideDingApi(builder: OkHttpClient.Builder): DingApi {
            return Retrofit.Builder()
                    .baseUrl(DingApi.DING_API)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(builder.build())
                    .build()
                    .create(DingApi::class.java)
        }

        @JvmStatic
        @Singleton
        @Provides
        fun provideNewsApi(builder: OkHttpClient.Builder): NewsApi {
            return Retrofit.Builder()
                    .baseUrl(NewsApi.NEWS_API)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(builder.build())
                    .build()
                    .create(NewsApi::class.java)
        }
    }
}