package com.huyingbao.module.gan.ui.random.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.huyingbao.core.base.flux.fragment.BaseFluxFragment
import com.huyingbao.core.base.setTitle
import com.huyingbao.module.gan.R
import com.huyingbao.module.gan.ui.random.store.RandomStore
import kotlinx.android.synthetic.main.gan_fragment_category.*

/**
 * 内容类型列表展示页面
 *
 * Created by liujunfeng on 2019/1/1.
 */
class CategoryFragment : BaseFluxFragment<RandomStore>() {
    companion object {
        fun newInstance() = CategoryFragment()
    }

    override fun getLayoutId(): Int {
        return R.layout.gan_fragment_category
    }

    override fun afterCreate(savedInstanceState: Bundle?) {
        setTitle(R.string.app_label_gan, true)
        initViewPager()
    }

    /**
     * 初始化ViewPager
     */
    private fun initViewPager() {
        //获取要展示的类别
        val categoryArray = resources.getStringArray(R.array.gan_array_category)
        //设置应该保留在当前可见页面两侧的页面数量。超过此限制的页面将在需要时从适配器重新创建。
        view_pager_content.offscreenPageLimit = 7
        //设置适配器，显示对应的Fragment
        view_pager_content.adapter = object : FragmentStateAdapter(this) {
            override fun createFragment(position: Int): Fragment {
                return ArticleListFragment.newInstance(categoryArray[position])
            }

            override fun getItemCount(): Int {
                return categoryArray.size
            }
        }
        //ViewPager同TabLayout联动
        TabLayoutMediator(tab_layout_top, view_pager_content) { tab, position ->
            tab.text = categoryArray[position]
        }.attach()
    }
}
