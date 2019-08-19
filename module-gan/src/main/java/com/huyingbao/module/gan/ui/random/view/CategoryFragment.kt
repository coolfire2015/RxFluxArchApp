package com.huyingbao.module.gan.ui.random.view

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.huyingbao.core.base.flux.fragment.BaseFluxFragment
import com.huyingbao.core.base.setTitle
import com.huyingbao.module.gan.R
import com.huyingbao.module.gan.ui.random.action.RandomActionCreator
import com.huyingbao.module.gan.ui.random.adapter.CategoryAdapter
import com.huyingbao.module.gan.ui.random.store.RandomStore
import java.util.*
import javax.inject.Inject

/**
 * 内容类型列表展示页面
 *
 *
 * Created by liujunfeng on 2019/1/1.
 */
class CategoryFragment : BaseFluxFragment<RandomStore>() {
    @Inject
    lateinit var mRandomActionCreator: RandomActionCreator

    private var mRvContent: RecyclerView? = null
    private var mDataList: MutableList<String>? = null
    private var mAdapter: CategoryAdapter? = null

    override fun getLayoutId(): Int {
        return R.layout.common_fragment_list
    }

    override fun afterCreate(savedInstanceState: Bundle?) {
        setTitle(R.string.app_label_gan, true)
        initRecyclerView()
        initAdapter()
        showData()
    }

    /**
     * 实例化RecyclerView,并设置adapter
     */
    private fun initRecyclerView() {
        mRvContent = view!!.findViewById(R.id.rv_content)
        mRvContent!!.layoutManager = LinearLayoutManager(activity)
        mRvContent!!.setHasFixedSize(true)
        mRvContent!!.addOnItemTouchListener(object : OnItemClickListener() {
            override fun onSimpleItemClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
                //TODO：点击事件
                //base.postLocalAction(RandomAction.TO_SHOW_DATA, GanConstants.Key.CATEGORY, mDataList.get(position));
            }
        })
    }

    /**
     * 实例化adapter
     */
    private fun initAdapter() {
        mDataList = ArrayList()
        mAdapter = CategoryAdapter(mDataList)
        //view设置适配器
        mRvContent!!.adapter = mAdapter
    }

    /**
     * 显示数据
     */
    private fun showData() {
        mDataList!!.addAll(Arrays.asList(*resources.getStringArray(R.array.gan_array_category)))
        mAdapter!!.notifyDataSetChanged()
    }

    companion object {

        fun newInstance(): CategoryFragment {
            return CategoryFragment()
        }
    }
}
