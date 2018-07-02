package com.dodsport.consumer.weight;

import android.view.View;

import me.dkzwm.widget.srl.SmoothRefreshLayout;
import me.dkzwm.widget.srl.indicator.IIndicator;

/**
 *
 * @author Administrator
 * @date 2017/10/29
 *
 * 下拉刷新 上滑加载更多自定义接口
 */

public interface IRefreshView <T extends IIndicator> {

    byte TYPE_HEADER = 0;
    byte TYPE_FOOTER = 1;

    byte STYLE_DEFAULT = 0;
    byte STYLE_SCALE = 1;

    /**
     * 返回是头部视图还是尾部视图;
     */
    int getType();

    /**
     * 一般情况都是View实现本接口，所以返回this;
     */
    View getView();

    /**
     * 获取视图样式，自1.4.8版本后支持6种样式，STYLE_DEFAULT、STYLE_SCALE、STYLE_PIN、STYLE_FOLLOW_SCALE、STYLE_FOLLOW_PIN、STYLE_FOLLOW_CENTER;
     */
    int getStyle();

    /**
     * 获取视图的自定义高度，当视图样式为STYLE_SCALE和STYLE_FOLLOW_SCALE时，必须返回一个确切且大于0的值，使用横向刷新库时，该属性实际应该返回的是视图的宽度;
     */
    int getCustomHeight();

    /**
     * 手指离开屏幕;
     */
    void onFingerUp(SmoothRefreshLayout layout, T indicator);

    /**
     * 重置视图;
     */
    void onReset(SmoothRefreshLayout layout);

    /**
     * 重新配置视图，准备刷新;
     */
    void onRefreshPrepare(SmoothRefreshLayout layout);

    /**
     * 开始刷新;
     */
    void onRefreshBegin(SmoothRefreshLayout layout, T indicator);

    /**
     * 刷新完成;
     */
    void onRefreshComplete(SmoothRefreshLayout layout, boolean isSuccessful);

    /**
     * 当头部或者尾部视图发生位置变化;
     */
    void onRefreshPositionChanged(SmoothRefreshLayout layout, byte status, T indicator);

    /**
     * 当头部或者尾部视图仍然处于处理事务中，这时候移动其他刷新视图则会调用该方法;
     * 在1.4.6版本新加入;
     */
    void onPureScrollPositionChanged(SmoothRefreshLayout layout, byte status, T indicator);
}