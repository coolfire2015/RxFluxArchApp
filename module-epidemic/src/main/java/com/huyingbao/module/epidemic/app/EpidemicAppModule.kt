package com.huyingbao.module.epidemic.app

import androidx.lifecycle.ViewModel
import com.huyingbao.core.arch.scope.ActivityScope
import com.huyingbao.core.arch.store.RxStoreKey
import com.huyingbao.module.common.app.CommonAppModule
import com.huyingbao.module.epidemic.BuildConfig
import com.huyingbao.module.epidemic.ui.main.module.MainActivityModule
import com.huyingbao.module.epidemic.ui.main.store.MainStore
import com.huyingbao.module.epidemic.ui.main.view.MainActivity
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
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

    @Module
    companion object {
        /**
         * Api根路径
         */
        const val BASE_API = "https://service-0gg71fu4-1252957949.gz.apigw.tencentcs.com/"
        /**
         * 提供[Retrofit]单例对象
         *
         * 每个子模块的Module中都提供[Retrofit]单例对象，使用[Named]注解，子模块提供的单例对象。
         */
        @JvmStatic
        @Singleton
        @Provides
        @Named(BuildConfig.MODULE_NAME)
        fun provideRetrofit(builder: OkHttpClient.Builder): Retrofit {
            return Retrofit.Builder()
                    .baseUrl(BASE_API)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(builder.build())
                    .build()
        }
    }
}