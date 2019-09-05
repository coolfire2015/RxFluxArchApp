package com.huyingbao.module.common.app

import com.huyingbao.core.arch.module.RxFluxModule
import com.huyingbao.core.arch.scope.ActivityScope
import com.huyingbao.module.common.BuildConfig
import com.huyingbao.module.common.ui.update.module.CommonUpdateDialogModule
import com.huyingbao.module.common.ui.update.view.CommonUpdateDialog
import dagger.Module
import dagger.Provides
import dagger.android.AndroidInjectionModule
import dagger.android.ContributesAndroidInjector
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * 基础依赖注入仓库，所有子模块中的AppModule中需要包括该类，实现Store注入和Dagger.Android注入
 *
 * Created by liujunfeng on 2019/1/1.
 */
@Module(includes = [
    RxFluxModule::class,
    AndroidInjectionModule::class
])
abstract class CommonAppModule {
    @ActivityScope
    @ContributesAndroidInjector
    abstract fun injectCommonAppLifecycle(): CommonAppLifecycle

    @ContributesAndroidInjector(modules = [CommonUpdateDialogModule::class])
    abstract fun injectCommonUpdateDialog(): CommonUpdateDialog

    @Module
    companion object {
        @JvmStatic
        @Singleton
        @Provides
        fun provideClientBuilder(): OkHttpClient.Builder {
            //设置日志拦截器
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = if (BuildConfig.DEBUG)
                HttpLoggingInterceptor.Level.BODY
            else
                HttpLoggingInterceptor.Level.NONE
            return OkHttpClient.Builder()
                    .connectTimeout(CommonAppConstants.Config.HTTP_TIME_OUT, TimeUnit.SECONDS)
                    .readTimeout(CommonAppConstants.Config.HTTP_TIME_OUT, TimeUnit.SECONDS)
                    .writeTimeout(CommonAppConstants.Config.HTTP_TIME_OUT, TimeUnit.SECONDS)
                    .addInterceptor(interceptor)
        }
    }
}