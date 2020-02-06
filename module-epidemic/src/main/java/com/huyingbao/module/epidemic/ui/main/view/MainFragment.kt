package com.huyingbao.module.epidemic.ui.main.view

import android.graphics.Color
import android.os.Bundle
import com.github.mikephil.charting.components.*
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import com.huyingbao.core.arch.model.RxError
import com.huyingbao.core.arch.model.RxLoading
import com.huyingbao.core.base.flux.fragment.BaseFluxFragment
import com.huyingbao.core.base.setTitle
import com.huyingbao.module.common.utils.showCommonError
import com.huyingbao.module.epidemic.R
import com.huyingbao.module.epidemic.ui.main.action.MainAction
import com.huyingbao.module.epidemic.ui.main.action.MainActionCreator
import com.huyingbao.module.epidemic.ui.main.store.MainStore
import kotlinx.android.synthetic.main.epidemic_fragment_main.*
import org.greenrobot.eventbus.Subscribe
import java.text.DecimalFormat
import javax.inject.Inject


class MainFragment : BaseFluxFragment<MainStore>(), OnChartValueSelectedListener {
    @Inject
    lateinit var mainActionCreator: MainActionCreator

    companion object {
        fun newInstance() = MainFragment()
    }

    override fun getLayoutId() = R.layout.epidemic_fragment_main

    override fun afterCreate(savedInstanceState: Bundle?) {
        setTitle("省", true)
        initBarChart()
        initView()
    }

    /**
     * 初始化界面
     */
    private fun initView() {
        //下拉刷新监听器，设置获取最新一页数据
        epidemic_rfl_content?.setOnRefreshListener {
            mainActionCreator.getDingData()
        }
        //显示数据
        rxStore?.provinceLiveData?.observe(this, androidx.lifecycle.Observer {

        })
    }

    override fun onResume() {
        super.onResume()
        //如果store已经创建并获取到数据，说明是横屏等操作导致的Fragment重建，不需要重新获取数据
        if (rxStore?.provinceLiveData?.value == null) {
            epidemic_rfl_content?.autoRefresh()
        }
    }

    /**
     * 显示进度对话框，接收[RxLoading]，粘性，该方法不经过RxStore，由RxFluxView直接处理
     */
    @Subscribe(tags = [MainAction.GET_DING_DATA], sticky = true)
    fun onRxLoading(rxLoading: RxLoading) {
        if (!rxLoading.isLoading) {
            epidemic_rfl_content?.finishRefresh()
        }
    }

    /**
     * 接收[RxError]，粘性
     */
    @Subscribe(tags = [MainAction.GET_DING_DATA], sticky = true)
    fun onRxError(rxError: RxError) {
        activity?.let { showCommonError(it, rxError) }
    }

    /**
     * 初始化柱形图控件属性
     */
    private fun initBarChart() {
        bar_chart_province.apply {
            setDrawBarShadow(false)
            setDrawValueAboveBar(true)
            description.isEnabled = false
            setMaxVisibleValueCount(60)
            // scaling can now only be done on x- and y-axis separately
            setPinchZoom(false)
            setDrawGridBackground(false)
            //X轴
            xAxis.apply {
                position = XAxis.XAxisPosition.BOTTOM
                setDrawAxisLine(false)
                granularity = 1f
                valueFormatter = XAxisValueFormatter()
            }
            //获取到图形左边的Y轴
            axisLeft.apply {
                //设置限制临界线
                addLimitLine(LimitLine(3f, "临界点").apply {
                    lineColor = Color.GREEN
                    lineWidth = 1f
                    textColor = Color.GREEN
                })
                setLabelCount(8, false)
                valueFormatter = MyAxisValueFormatter()
                setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART)
                spaceTop = 15f
                axisMinimum = 0f
            }
            //获取到图形右边的Y轴，并设置为不显示
            axisRight.isEnabled = false
            //图例设置
            legend.apply {
                verticalAlignment = Legend.LegendVerticalAlignment.BOTTOM
                horizontalAlignment = Legend.LegendHorizontalAlignment.LEFT
                orientation = Legend.LegendOrientation.HORIZONTAL
                setDrawInside(false)
                form = Legend.LegendForm.SQUARE
                formSize = 9f
                textSize = 11f
                xEntrySpace = 4f
            }
        }
    }

    class MyAxisValueFormatter : ValueFormatter() {
        private val mFormat: DecimalFormat = DecimalFormat("###,###,###,##0.000")
        override fun getFormattedValue(value: Float, axis: AxisBase): String {
            return mFormat.format(value).toString() + " $"
        }

    }

    class XAxisValueFormatter : ValueFormatter() {
        private val xStrs = arrayOf("春", "夏", "秋", "冬")

        override fun getFormattedValue(value: Float, axis: AxisBase?): String? {
            var position = value.toInt()
            if (position >= 4) {
                position = 0
            }
            return xStrs[position]
        }
    }

    override fun onNothingSelected() {
    }

    override fun onValueSelected(e: Entry?, h: Highlight?) {
    }
}
