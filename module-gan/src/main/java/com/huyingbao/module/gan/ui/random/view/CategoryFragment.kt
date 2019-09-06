package com.huyingbao.module.gan.ui.random.view

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.huyingbao.core.base.flux.fragment.BaseFluxFragment
import com.huyingbao.core.base.setTitle
import com.huyingbao.module.gan.R
import com.huyingbao.module.gan.ui.random.action.RandomActionCreator
import com.huyingbao.module.gan.ui.random.adapter.CategoryAdapter
import com.huyingbao.module.gan.ui.random.store.RandomStore
import javax.inject.Inject

/**
 * 内容类型列表展示页面
 *
 * Created by liujunfeng on 2019/1/1.
 */
class CategoryFragment : BaseFluxFragment<RandomStore>() {
    @Inject
    lateinit var randomActionCreator: RandomActionCreator

    private var rvContent: RecyclerView? = null
    private var categoryAdapter: CategoryAdapter? = null

    private val dataList = ArrayList<String>()

    companion object {
        fun newInstance(): CategoryFragment {
            return CategoryFragment()
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.common_fragment_list
    }

    override fun afterCreate(savedInstanceState: Bundle?) {
        setTitle(R.string.app_label_gan, true)
        initAdapter()
    }

    /**
     * 实例化adapter
     */
    private fun initAdapter() {
        rvContent = view?.findViewById(R.id.rv_content)
        //初始数据
        dataList.addAll(listOf(*resources.getStringArray(R.array.gan_array_category)))
        //实例适配器
        categoryAdapter = CategoryAdapter(dataList)
        //view设置适配器
        rvContent?.adapter = categoryAdapter
        rvContent?.addOnItemTouchListener(object : OnItemClickListener() {
            override fun onSimpleItemClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
                randomActionCreator.toShowData(dataList[position])
            }
        })

    }
}
