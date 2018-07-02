package com.dodsport.activity.financial;

/**
 * 图表工具
 * Created by yangle on 2016/11/29.
 */
public class ChartUtils {
/*
    public static int dayValue = 0;
    public static int weekValue = 1;
    public static int monthValue = 2;

    *//**
     * 初始化图表
     *
     * @param mChart1 原始图表
     * @return 初始化后的图表
     *//*
    public static BarChart initChart(BarChart mChart1) {
      *//*  // 不显示数据描述
        chart.getDescription().setEnabled(false);
        // 没有数据的时候，显示“暂无数据”
        chart.setNoDataText("暂无数据");
        // 不显示表格颜色
        chart.setDrawGridBackground(false);
        // 不可以缩放
        chart.setScaleEnabled(false);
        // 不显示y轴右边的值
        chart.getAxisRight().setEnabled(false);
        // 不显示图例
        Legend legend = chart.getLegend();
        legend.setEnabled(false);
        // 向左偏移15dp，抵消y轴向右偏移的30dp
        chart.setExtraLeftOffset(-15);

        XAxis xAxis = chart.getXAxis();
        // 不显示x轴
        xAxis.setDrawAxisLine(false);
        // 设置x轴数据的位置
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextColor(Color.WHITE);
        xAxis.setTextSize(12);
        xAxis.setGridColor(Color.parseColor("#30FFFFFF"));
        // 设置x轴数据偏移量
        xAxis.setYOffset(-12);

        YAxis yAxis = chart.getAxisLeft();
        // 不显示y轴
        yAxis.setDrawAxisLine(false);
        // 设置y轴数据的位置
        yAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        // 不从y轴发出横向直线
        yAxis.setDrawGridLines(false);
        yAxis.setTextColor(Color.WHITE);
        yAxis.setTextSize(12);
        // 设置y轴数据偏移量
        yAxis.setXOffset(30);
        yAxis.setYOffset(-3);
        yAxis.setAxisMinimum(0);

        //Matrix matrix = new Matrix();
        // x轴缩放1.5倍
        //matrix.postScale(1.5f, 1f);
        // 在图表动画显示之前进行缩放
        //chart.getViewPortHandler().refresh(matrix, chart, false);
        // x轴执行动画
        //chart.animateX(2000);
        chart.invalidate();
*//*



        mChart1.getDescription().setEnabled(false);

        // if more than 60 entries are displayed in the chart, no values will be
        // drawn
        mChart1.setMaxVisibleValueCount(500);

        // scaling can now only be done on x- and y-axis separately
        mChart1.setPinchZoom(false);

        mChart1.setDrawBarShadow(false);
        mChart1.setDrawGridBackground(false);

        XAxis xAxis = mChart1.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setTextColor(Color.WHITE);
        xAxis.setTextSize(12);
        xAxis.setGridColor(Color.parseColor("#30000000"));
        // 设置x轴数据偏移量
//        xAxis.setYOffset(12);


        // 不显示y轴右边的值
        mChart1.getAxisRight().setEnabled(false);
        // 显示X轴左边的值
        mChart1.getAxisLeft().setDrawGridLines(false);
        // 不可以缩放
        mChart1.setScaleEnabled(false);
        // 向左偏移15dp，抵消y轴向右偏移的30dp
        mChart1.setExtraLeftOffset(15);


        // add a nice and smooth animation
        mChart1.animateY(2500);

        mChart1.getLegend().setEnabled(false);



        return mChart1;
    }



    public static void  showChart(){
        ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();

        for (int i = 0; i <4 ; i++) {
            float mult = (100 + 1);
            float val = (float) (Math.random() * mult) + mult / 3;
            yVals1.add(new BarEntry(i, val));
        }

        BarDataSet set1;

        if (mChart1.getData() != null &&
                mChart1.getData().getDataSetCount() > 0) {
            set1 = (BarDataSet)mChart1.getData().getDataSetByIndex(0);
            set1.setValues(yVals1);
            mChart1.getData().notifyDataChanged();
            mChart1.notifyDataSetChanged();
        } else {
            set1 = new BarDataSet(yVals1, "Data Set");
            set1.setColors(ColorTemplate.VORDIPLOM_COLORS);
            set1.setDrawValues(false);

            ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
            dataSets.add(set1);

            BarData data = new BarData(dataSets);
            mChart1.setData(data);
            mChart1.setFitBars(true);
        }
        mChart1.invalidate();
    }

    *//**
     * 设置图表数据
     *
     * @param chart  图表
     * @param values 数据
     *//*
    public static void setChartData(BarChart chart, List<Entry> values) {
        LineDataSet lineDataSet;

        if (chart.getData() != null && chart.getData().getDataSetCount() > 0) {
            lineDataSet = (LineDataSet) chart.getData().getDataSetByIndex(0);
            lineDataSet.setValues(values);
            chart.getData().notifyDataChanged();
            chart.notifyDataSetChanged();
        } else {
            lineDataSet = new LineDataSet(values, "");
            // 设置曲线颜色
            lineDataSet.setColor(Color.parseColor("#FFFFFF"));
            // 设置平滑曲线
            lineDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
            // 不显示坐标点的小圆点
            lineDataSet.setDrawCircles(false);
            // 不显示坐标点的数据
            lineDataSet.setDrawValues(false);
            // 不显示定位线
            lineDataSet.setHighlightEnabled(false);

            LineData data = new LineData(lineDataSet);
            chart.setData(data);
            chart.invalidate();
        }
    }

    *//**
     * 更新图表
     *
     * @param chart     图表
     * @param values    数据
     * @param valueType 数据类型
     *//*
    public static void notifyDataSetChanged(BarChart chart, List<Entry> values,
                                            final int valueType) {
        chart.getXAxis().setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return xValuesProcess(valueType)[(int) value];
            }
        });

        chart.invalidate();
        setChartData(chart, values);
    }

    *//**
     * x轴数据处理
     *
     * @param valueType 数据类型
     * @return x轴数据
     *//*
    private static String[] xValuesProcess(int valueType) {
        String[] week = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};

        if (valueType == dayValue) { // 今日
            String[] dayValues = new String[7];
            long currentTime = System.currentTimeMillis();
            for (int i = 6; i >= 0; i--) {
                dayValues[i] = TimeUtils.dateToString(currentTime, TimeUtils.dateFormat_day);
                currentTime -= (3 * 60 * 60 * 1000);
            }
            return dayValues;

        } else if (valueType == weekValue) { // 本周
            String[] weekValues = new String[7];
            Calendar calendar = Calendar.getInstance();
            int currentWeek = calendar.get(Calendar.DAY_OF_WEEK);

            for (int i = 6; i >= 0; i--) {
                weekValues[i] = week[currentWeek - 1];
                if (currentWeek == 1) {
                    currentWeek = 7;
                } else {
                    currentWeek -= 1;
                }
            }
            return weekValues;

        } else if (valueType == monthValue) { // 本月
            String[] monthValues = new String[7];
            long currentTime = System.currentTimeMillis();
            for (int i = 6; i >= 0; i--) {
                monthValues[i] = TimeUtils.dateToString(currentTime, TimeUtils.dateFormat_month);
                currentTime -= (4 * 24 * 60 * 60 * 1000);
            }
            return monthValues;
        }
        return new String[]{};
    }*/
}
