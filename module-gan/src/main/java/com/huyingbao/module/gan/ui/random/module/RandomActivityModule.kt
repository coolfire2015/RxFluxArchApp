package com.huyingbao.module.gan.ui.random.module


import com.huyingbao.core.arch.scope.FragmentScope
import com.huyingbao.module.gan.ui.random.view.CategoryFragment
import com.huyingbao.module.gan.ui.random.view.ArticleListFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * [com.huyingbao.module.gan.ui.random.view.RandomActivity]的依赖注入仓库。
 *
 * 1.使用Dagger.Android自动生成Fragment的依赖注入器[dagger.Subcomponent]，作用域为[FragmentScope]，并绑定对应[Module]。
 *
 * 2.伴生对象，提供[com.huyingbao.core.arch.scope.ActivityScope]作用域对象
 *
 * Created by liujunfeng on 2019/1/1.
 */
@Module
internal abstract class RandomActivityModule {
    @FragmentScope
    @ContributesAndroidInjector
    internal abstract fun injectCategoryFragment(): CategoryFragment

    @FragmentScope
    @ContributesAndroidInjector
    internal abstract fun injectProductFragment(): ArticleListFragment
}
