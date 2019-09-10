package com.huyingbao.module.gan.app

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.room.Room
import androidx.room.RoomDatabase
import com.huyingbao.core.arch.scope.ActivityScope
import com.huyingbao.core.arch.store.RxStoreKey
import com.huyingbao.module.common.app.CommonAppModule
import com.huyingbao.module.gan.BuildConfig
import com.huyingbao.module.gan.ui.random.module.RandomActivityModule
import com.huyingbao.module.gan.ui.random.store.RandomStore
import com.huyingbao.module.gan.ui.random.view.RandomActivity
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
 * Module其实是一个简单工厂模式，Module里面的方法基本都是创建类实例的方法。
 *
 * 1.使用Dagger.Android自动生成Activity的依赖注入器[dagger.Subcomponent]，作用域为[ActivityScope]，并绑定对应[Module]。
 *
 * 2.伴生对象，提供[Singleton]全局单例对象
 *
 * Created by liujunfeng on 2019/1/1.
 */
@Module(includes = [CommonAppModule::class])
abstract class GanAppModule {
    /**
     * 快速生成[GanAppLifecycle]的依赖注入器。
     */
    @ContributesAndroidInjector
    abstract fun injectGanAppLifecycle(): GanAppLifecycle

    /**
     * [ContributesAndroidInjector]注解帮助我们生成方法的返回值类型[RandomActivity]）的注射器
     * 自动生成注射器[dagger.android.AndroidInjector]子类RandomActivitySubcomponent
     *
     * [ContributesAndroidInjector]用来简化Subcomponent的书写
     *
     * 在2.11之前，每一个Fragment或者Activity都需要有自己的Component
     * 然后在Component中声明inject方法，
     * 最后在Fragment或者Activity中进行注入
     *
     * ContributesAndroidInjector注解标注的方法必须是抽象方法
     * 并且返回值是具体的android framework的类型（比如Activity或者Fragment），
     * 此外，这个方法不能有参数
     *
     * ActivityScope标注生成的Subcomponet生命周期跟随Activity
     * [ContributesAndroidInjector]指定DaggerAndroidActivity的Subcomponent中需要安装的Module
     *
     * dagger.android会根据@ContributesAndroidInjector生成需要注入对应对象的SubComponent，
     * 并添加到map中
     *
     * [ActivityScope]标注自动生成的[GanAppModule_InjectRandomActivity.RandomActivitySubcomponent]注入器
     */
    @ActivityScope
    @ContributesAndroidInjector(modules = [RandomActivityModule::class])
    abstract fun injectRandomActivity(): RandomActivity

    /**
     * 创建类实例有2个维度可以创建：
     * 1.通过Module中的Provide注解或者Binds等注解来创建（以下简称Module维度）优先
     * 2.通过用Inject注解标注的构造函数来创建（以下简称Inject维度）
     *
     * Qualifier注解用来区分同一纬度下两种不同的创建实例的方法，类似MapKey
     *
     * Provider需要写明具体的实现，
     * Binds只是告诉Dagger2谁是谁实现的，
     *
     * Dagger2会根据这些信息自动生成一个关键的Map。
     * key为ViewModel的Class，
     * value则为提供ViewModel实例的Provider对象，
     * 通过provider.get()方法就可以获取到相应的ViewModel对象。
     * 注解Binds当参数和返回值类型相同时，将方法写成抽象方法,
     * 注解IntoMap可以让Dagger2将多个元素依赖注入到Map之中,
     */
    @Singleton
    @Binds
    @IntoMap
    @RxStoreKey(RandomStore::class)
    abstract fun bindRandomStore(randomStore: RandomStore): ViewModel

    /**
     * 伴生对象，提供全局单例对象
     */
    @Module
    companion object {
        /**
         * Api根路径
         */
        const val BASE_API = "http://gank.io/api/"
        /**
         * 需要创建的数据库名字
         */
        const val DATABASE_NAME = "gan-db"

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

        /**
         * 提供[RoomDatabase]单例对象，获得创建数据库的实例：
         */
        @JvmStatic
        @Singleton
        @Provides
        fun provideDataBase(application: Application): GanAppDatabase {
            val databaseBuilder = Room.databaseBuilder(
                    application,
                    GanAppDatabase::class.java,
                    DATABASE_NAME)
                    //允许Room破坏性地重新创建数据库表。
                    .fallbackToDestructiveMigration()
            return databaseBuilder.build()
        }
    }
}