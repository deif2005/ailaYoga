package com.dodsport.activity.financial;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import com.dodsport.R;
import com.dodsport.model.IncomeBean;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.interfaces.datasets.IDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.MPPointF;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.dodsport.R.id.chart;

/**
 * 收入管理 明细报表
 */
public class IncomeDetailActivity extends DemoBase implements  OnSeekBarChangeListener,View.OnClickListener, OnChartValueSelectedListener {

    private static final String TAG = "******";
    @Bind(R.id.head_ivBack)
    ImageView mHeadIvBack;
    @Bind(R.id.head_tvTitle)
    TextView mHeadTvTitle;
    @Bind(R.id.head_ivOK)
    ImageView mHeadIvOK;
    @Bind(R.id.head_tvOK)
    TextView mHeadTvOK;
    @Bind(R.id.relativeLayout)
    RelativeLayout mRelativeLayout;
    @Bind(chart)
    PieChart mChart;
    @Bind(R.id.chart1)
    BarChart mChart1;


    public static int dayValue = 0;
    public static int weekValue = 1;
    public static int monthValue = 2;
    private int index = 99;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_income_detail);
        ButterKnife.bind(this);


        initView();
        initPieChart();
        initBarChart();
    }

    /**
     * 初始化
     * */
    public void initView(){

        mHeadTvTitle.setVisibility(View.VISIBLE);
        mHeadIvBack.setVisibility(View.VISIBLE);
        mHeadIvBack.setOnClickListener(this);
        mHeadTvTitle.setText("收入");
    }

    /**
     * 饼状图表 设置属性
     * */
   public void initPieChart(){
       mChart.setUsePercentValues(true);
       mChart.getDescription().setEnabled(false);
       mChart.setExtraOffsets(5, 10, 5, 5);

       mChart.setDragDecelerationFrictionCoef(0.95f);

       mChart.setCenterTextTypeface(mTfLight);
       mChart.setCenterText(generateCenterSpannableText());

       mChart.setDrawHoleEnabled(true);
       mChart.setHoleColor(Color.WHITE);

       mChart.setTransparentCircleColor(Color.WHITE);
       mChart.setTransparentCircleAlpha(110);
       mChart.setHoleRadius(85f);//半径
       mChart.setTransparentCircleRadius(61f);

       mChart.setTouchEnabled(false);  //设置是否响应点击触摸
       mChart.setDrawCenterText(true);
       mChart.setRotationAngle(0);
       // enable rotation of the chart by touch
       mChart.setRotationEnabled(true);
       mChart.setHighlightPerTapEnabled(true);

       // mChart.setUnit(" €");
       // mChart.setDrawUnitsInChart(true);

       // add a selection listener
       mChart.setOnChartValueSelectedListener(this);

       setData(4, 100);

       mChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);
       // mChart.spin(2000, 0, 360);

       Legend l = mChart.getLegend();
//       l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
//       l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
//       l.setOrientation(Legend.LegendOrientation.VERTICAL);
//       l.setDrawInside(false);
//       l.setXEntrySpace(5f);//设置距离饼图的距离，防止与饼图重合
//       l.setYEntrySpace(5f);
//       l.setYOffset(2f);
       l.setEnabled(false);     //隐藏右上角的颜色比例图

       // entry label styling
       mChart.setEntryLabelColor(Color.WHITE);
       mChart.setEntryLabelTypeface(mTfRegular);
       mChart.setEntryLabelTextSize(12f);
       // 上面右图
       mChart.setCenterTextRadiusPercent(3.0f);
       //展开方式
       mChart.animateXY(1400, 1400);

       //是否空心
       if (mChart.isDrawHoleEnabled()) {
           mChart.setDrawHoleEnabled(false);
       } else {
           mChart.setDrawHoleEnabled(true);
       }
       if (mChart.isDrawCenterTextEnabled()){
           mChart.setDrawCenterText(false);
       } else{
           mChart.setDrawCenterText(true);
       }


       //扇状中是否显示text（不是百分比）
//       mChart.setDrawEntryLabels(!mChart.isDrawEntryLabelsEnabled());
       mChart.invalidate();

   }

    /**
     * 竖状型图表 属性
     * */
    private void initBarChart(){
        mChart1.getDescription().setEnabled(false);
        mChart1.setMaxVisibleValueCount(600);
        mChart1.setPinchZoom(false);
        mChart1.setDrawBarShadow(false);
        mChart1.setDrawGridBackground(false);

        XAxis xAxis = mChart1.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setTextColor(getResources().getColor(R.color.home_text_normal));
        xAxis.setTextSize(12f);
        xAxis.setGridColor(Color.parseColor("#30000000"));
        xAxis.setGridLineWidth(12f); // 设置该轴网格线的宽度。


//        xAxis.setTypeface(mTfRegular);
        // 设置x轴数据偏移量
        xAxis.setYOffset(1);

        // 不显示y轴右边的值
        mChart1.getAxisRight().setEnabled(false);
        // 显示X轴左边的值
        mChart1.getAxisLeft().setDrawGridLines(false);
        // 不可以缩放
        mChart1.setScaleEnabled(false);
        mChart1.setTouchEnabled(false);
        // 向左偏移15dp，抵消y轴向右偏移的30dp
        mChart1.setExtraLeftOffset(5);

        mChart1.animateY(2500);

        mChart1.getLegend().setEnabled(false);

    }


    /**
     * 赋值给竖状图表
     * */
    private  void  showChart(){
        List<IncomeBean> data = new ArrayList<>();
        String[] week = {"七月", "六月", "五月", "四月"};
        int[] week2 = {50, 150, 80, 20};
        for (int i=0;i!=4;i++){
            IncomeBean mIcomeBean = new IncomeBean();
            mIcomeBean.setProvinceCount(week2[i]);
            mIcomeBean.setProvinceName(week[i]);
            data.add(mIcomeBean);
        }

        final ArrayList<BarEntry> yVals = new ArrayList<BarEntry>();
        for (int i = 0; i < 3 + 1; i++) {
            float mult = (200 + 1);
            float val = (float) (Math.random() * mult) + mult / 3;
            yVals.add(new BarEntry(i, val,data.get(i)));
        }

        mChart1.getXAxis().setLabelCount(4);
        BarDataSet set1;
        if (mChart1.getData() != null &&
            mChart1.getData().getDataSetCount() > 0) {
            set1 = (BarDataSet)mChart1.getData().getDataSetByIndex(0);
            set1.setValues(yVals);
            mChart1.getData().notifyDataChanged();
            mChart1.notifyDataSetChanged();
            set1.setColors(ColorTemplate.LIBERTY_COLORS);
        } else {

            set1 = new BarDataSet(yVals, "");
            //竖状的大小
            set1.setBarBorderWidth(30f);
            set1.setBarBorderColor(getResources().getColor(R.color.white));
            set1.setColors(getResources().getColor(R.color.chart));
            //竖状Top的数据 float型
            for (IDataSet set : mChart.getData().getDataSets()){
                set.setDrawValues(!set.isDrawValuesEnabled());
            }

            // //竖状Top的数据 int型
//            set1.setValueFormatter(new IValueFormatter() {
//                @Override
//                public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
//
//                    DecimalFormat decimalFormat = new DecimalFormat("");
//                    String s = decimalFormat.format(value);
//                   // int i = Integer.parseInt(s.toString()+"");
//                    float v = Float.parseFloat(s);
//                    int yValue = (int) v;
//                    String yValueText = Integer.toString(yValue);
//                    return yValueText;
//                }
//            });

            /*绘制X轴的标签*/
            mChart1.getXAxis().setValueFormatter(new IAxisValueFormatter() {
                @Override
                public String getFormattedValue(float value, AxisBase axis) {
                    int xValue =(int)value;
                    String[] week = {"七月", "六月", "五月", "四月"};
                    return week[xValue];
                }
            });

            ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
            dataSets.add(set1);
            BarData datas = new BarData(dataSets);
            mChart1.setData(datas);
            mChart1.setFitBars(true);
            mChart1.fitScreen();
        }
        mChart1.invalidate();
    }


    private ArrayList<BarEntry> getData() {
        ArrayList<BarEntry> values = new ArrayList<>();
        values.add(new BarEntry(0, 35));
        values.add(new BarEntry(1, 125));
        values.add(new BarEntry(2, 70));
        values.add(new BarEntry(3, 180));
//        values.add(new Entry(4, 25));
//        values.add(new Entry(5, 20));
//        values.add(new Entry(6, 20));
        return values;
    }



    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        setData(4, 10);

    }


    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
    }

    private void setData(int count, float range) {

        float mult = range;

        ArrayList<PieEntry> entries = new ArrayList<PieEntry>();

        for (int i = 0; i < count; i++) {
            entries.add(new PieEntry((float) ((Math.random() * mult) + mult / 1),
                    mParties[i % mParties.length],
                    null));
        }

        PieDataSet dataSet = new PieDataSet(entries, "Election Results");
        dataSet.setDrawIcons(false);
        dataSet.setIconsOffset(new MPPointF(0, 40));
        dataSet.setSelectionShift(5f);
        dataSet.setSliceSpace(0f);  //扇状间距

        dataSet.setDrawValues(false);
        mChart.setDrawSliceText(false);
        mChart.highlightValues(null);
        // add a lot of colors

        //设置饼状Colors
        ArrayList<Integer> colors = new ArrayList<Integer>();

        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());

        dataSet.setColors(colors);
        //dataSet.setSelectionShift(0f);

        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(12f);
        data.setValueTextColor(getResources().getColor(R.color.home_text_normal));
//        data.setValueTypeface(mTfLight);
        // 上面右图
        mChart.setDrawSliceText(false);
        mChart.setData(data);

        // undo all highlights
        mChart.highlightValues(null);

        mChart.invalidate();

        showChart();
    }

    private SpannableString generateCenterSpannableText() {

        SpannableString s = new SpannableString("MPAndroidChart\ndeveloped by Philipp Jahoda");
        s.setSpan(new RelativeSizeSpan(1.7f), 0, 14, 0);
        s.setSpan(new StyleSpan(Typeface.NORMAL), 14, s.length() - 15, 0);
        s.setSpan(new ForegroundColorSpan(Color.GRAY), 14, s.length() - 15, 0);
        s.setSpan(new RelativeSizeSpan(.8f), 14, s.length() - 15, 0);
        s.setSpan(new StyleSpan(Typeface.ITALIC), s.length() - 14, s.length(), 0);
        s.setSpan(new ForegroundColorSpan(ColorTemplate.getHoloBlue()), s.length() - 14, s.length(), 0);
        return s;
    }



    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.head_ivBack:
                finish();       //返回
                break;
        }
    }

    //扇状的点击事件回调
    @Override
    public void onValueSelected(Entry e, Highlight h) {
        Log.i(TAG, "onStartTrackingTouch:点击饼状--3 \t"+e.toString()+"\n"+h.toString()+"");
        int indexs = (int)h.getX();
        if (indexs != indexs){
            index = indexs;
            mChart.setDrawEntryLabels(!mChart.isDrawEntryLabelsEnabled());
            mChart.invalidate();
        }
    }

    @Override
    public void onNothingSelected() {
    }
}
