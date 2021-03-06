package com.huyingbao.module.github.app

import android.app.Application
import android.text.TextUtils
import androidx.lifecycle.ViewModel
import androidx.room.Room
import androidx.room.RoomDatabase
import com.huyingbao.core.arch.scope.ActivityScope
import com.huyingbao.core.arch.store.RxStoreKey
import com.huyingbao.core.utils.LocalStorageUtils
import com.huyingbao.core.utils.PageInfoInterceptor
import com.huyingbao.module.common.app.CommonAppConstants
import com.huyingbao.module.common.app.CommonAppModule
import com.huyingbao.module.github.BuildConfig
import com.huyingbao.module.github.ui.login.module.LoginActivityModule
import com.huyingbao.module.github.ui.login.store.LoginStore
import com.huyingbao.module.github.ui.login.view.LoginActivity
import com.huyingbao.module.github.ui.main.module.MainActivityModule
import com.huyingbao.module.github.ui.main.store.MainStore
import com.huyingbao.module.github.ui.main.view.MainActivity
import com.huyingbao.module.github.ui.user.module.UserActivityModule
import com.huyingbao.module.github.ui.user.store.UserStore
import com.huyingbao.module.github.ui.user.view.UserActivity
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import javax.inject.Named
import javax.inject.Singleton

/**
 * 全局仓库
 *
 * Created by liujunfeng on 2019/1/1.
 */
@Module(includes = [CommonAppModule::class])
abstract class GithubAppModule {
    @ContributesAndroidInjector
    abstract fun injectGithubAppLifecycle(): GithubAppLifecycle

    @ActivityScope
    @ContributesAndroidInjector(modules = [LoginActivityModule::class])
    abstract fun injectLoginActivity(): LoginActivity

    @Singleton
    @Binds
    @IntoMap
    @RxStoreKey(LoginStore::class)
    abstract fun bindLoginStore(loginStore: LoginStore): ViewModel

    @ActivityScope
    @ContributesAndroidInjector(modules = [MainActivityModule::class])
    abstract fun injectMainActivity(): MainActivity

    @Singleton
    @Binds
    @IntoMap
    @RxStoreKey(MainStore::class)
    abstract fun bindMainStore(mainStore: MainStore): ViewModel

    @ActivityScope
    @ContributesAndroidInjector(modules = [UserActivityModule::class])
    abstract fun injectUserActivity(): UserActivity

    @Singleton
    @Binds
    @IntoMap
    @RxStoreKey(UserStore::class)
    abstract fun bindUserStore(userStore: UserStore): ViewModel

    @Module
    companion object {
        /**
         * Api根路径
         */
        const val BASE_API = "https://api.github.com/"
        /**
         * 需要创建的数据库名字
         */
        const val DATABASE_NAME = "github-db"

        /**
         * 提供[Retrofit]单例对象
         *
         * 每个子模块的Module中都提供[Retrofit]单例对象，使用[Named]注解，子模块提供的单例对象。
         */
        @JvmStatic
        @Singleton
        @Provides
        @Named(BuildConfig.MODULE_NAME)
        fun provideRetrofit(localStorageUtils: LocalStorageUtils, builder: OkHttpClient.Builder): Retrofit {
            //Head拦截器
            val headInterceptor = Interceptor {
                var request = it.request()
                val token = localStorageUtils.getValue(CommonAppConstants.Key.ACCESS_TOKEN, "")
                if (!TextUtils.isEmpty(token)) {
                    //Header中添加Authorization token数据
                    val url = request.url.toString()
                    val requestBuilder = request.newBuilder()
                            .addHeader("Authorization", "token $token")
                            .url(url)
                    request = requestBuilder.build()
                }
                it.proceed(request)
            }
            //初始化OkHttp
            val client = builder
                    .addInterceptor(headInterceptor)
                    .addInterceptor(PageInfoInterceptor())
                    .build()
            //初始化Retrofit
            val retrofitBuilder = Retrofit.Builder()
                    .baseUrl(BASE_API)
                    //入参Body中如果有参数为空，依然序列化
                    //.addConverterFactory(GsonConverterFactory.create(GsonBuilder().serializeNulls().create()))
                    //使用ScalarsConverterFactory，当接口返回类型为常规类型时，规范接口返回数据
                    //可用来处理接口返回XML数据转为String，然后再转为Json
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(client)
            return retrofitBuilder.build()
        }

        /**
         * 提供[RoomDatabase]单例对象，获得创建数据库的实例：
         */
        @JvmStatic
        @Singleton
        @Provides
        fun provideDataBase(application: Application): GithubAppDatabase {
            val databaseBuilder = Room.databaseBuilder(
                    application,
                    GithubAppDatabase::class.java,
                    DATABASE_NAME)
                    //允许Room破坏性地重新创建数据库表。
                    .fallbackToDestructiveMigration()
            return databaseBuilder.build()
        }
    }
}
