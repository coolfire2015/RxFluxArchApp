package com.huyingbao.module.github.ui.main.module

import com.huyingbao.core.arch.scope.ActivityScope
import com.huyingbao.core.arch.scope.FragmentScope
import com.huyingbao.core.utils.PageInfoInterceptor
import com.huyingbao.module.common.ui.update.action.FirApi
import com.huyingbao.module.github.ui.main.view.DynamicFragment
import com.huyingbao.module.github.ui.main.view.MineFragment
import com.huyingbao.module.github.ui.main.view.TrendFragment
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@Module
abstract class MainActivityModule {
    @FragmentScope
    @ContributesAndroidInjector
    abstract fun injectDynamicFragment(): DynamicFragment

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun injectRecommendFragment(): TrendFragment

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun injectMineFragment(): MineFragment

    @Module
    companion object {
        @JvmStatic
        @ActivityScope
        @Provides
        fun provideFirApi(builder: OkHttpClient.Builder): FirApi {
            //初始化OkHttp
            val client = builder
                    .addInterceptor(PageInfoInterceptor())
                    .build()
            //初始化Retrofit
            val retrofit = Retrofit.Builder()
                    .baseUrl("http://api.fir.im/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(client)
                    .build()
            return retrofit.create(FirApi::class.java)
        }
    }
}