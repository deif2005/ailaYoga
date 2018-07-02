package com.dodsport.consumer.view;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.dodsport.consumer.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 页面加载页面
 *
 */

public class LoadingView extends RelativeLayout{


    private static final String TAG_LOADING = "ProgressActivity.TAG_LOADING";
    private static final String TAG_EMPTY = "ProgressActivity.TAG_EMPTY";
    private static final String TAG_ERROR = "ProgressActivity.TAG_ERROR";

    LayoutInflater inflater;
    View view;
    LayoutParams layoutParams;

    List<View> contentViews = new ArrayList<>();

    LinearLayout loadingStateRelativeLayout;
    ProgressBar loadingStateProgressBar;

    LinearLayout emptyStateRelativeLayout;

    LinearLayout errorStateRelativeLayout;
    Button errorStateButton;

    int loadingStateBackgroundColor;


    private State state = State.CONTENT;
    public LoadingView(Context context) {
        super(context);
    }

    public LoadingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    private void init(AttributeSet attrs) {

        inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public LoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    @Override
    public void addView(View child) {
        super.addView(child);

        if (child.getTag() == null || (!child.getTag().equals(TAG_LOADING) && !child.getTag().equals(TAG_EMPTY) && !child.getTag().equals(TAG_ERROR))) {
            contentViews.add(child);
        }
    }


    /**
     * Get which state is set
     *
     * @return State
     */
    public State getState() {
        return state;
    }

    /**
     * Check if content is shown
     *
     * @return boolean
     */
    public boolean isContent() {
        return state == State.CONTENT;
    }

    /**
     * Check if loading state is shown
     *
     * @return boolean
     */
    public boolean isLoading() {
        return state == State.LOADING;
    }

    /**
     * Check if empty state is shown
     *
     * @return boolean
     */
    public boolean isEmpty() {
        return state == State.EMPTY;
    }

    /**
     * Check if error state is shown
     *
     * @return boolean
     */
    public boolean isError() {
        return state == State.ERROR;
    }

    /**
     * 正在加载页面布局
     */
    private void setLoadingView() {

        view =  inflater.inflate(R.layout.loading_view,null);
        loadingStateRelativeLayout = (LinearLayout) view.findViewById(R.id.loading_rl);
        loadingStateRelativeLayout.setTag(TAG_LOADING);

        loadingStateProgressBar = (ProgressBar) view.findViewById(R.id.loading_progress);

        /*loadingStateProgressBar.getLayoutParams().width = loadingStateProgressBarWidth;
        loadingStateProgressBar.getLayoutParams().height = loadingStateProgressBarHeight;
        loadingStateProgressBar.requestLayout();*/

        //Set background color if not TRANSPARENT
        if (loadingStateBackgroundColor != Color.TRANSPARENT) {
            loadingStateRelativeLayout.setBackgroundColor(loadingStateBackgroundColor);
        }

        layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        layoutParams.addRule(CENTER_IN_PARENT);

        addView(loadingStateRelativeLayout, layoutParams);
    }

    /**
     * 空页面
     */
    private void setEmptyView() {
        view = inflater.inflate(R.layout.empty_view, null);
        emptyStateRelativeLayout = (LinearLayout) view.findViewById(R.id.empty_rl);
        emptyStateRelativeLayout.setTag(TAG_EMPTY);
        layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        layoutParams.addRule(CENTER_IN_PARENT);

        addView(emptyStateRelativeLayout, layoutParams);
    }


    /**
     * 错误页面
     */
    private void setErrorView() {
        view = inflater.inflate(R.layout.error_view, null);

        errorStateRelativeLayout = (LinearLayout) view.findViewById(R.id.error_rl);
       // errorStateRelativeLayout.setFocusable(true);
        errorStateRelativeLayout.setTag(TAG_ERROR);
        errorStateButton  = (Button) view.findViewById(R.id.error_bt);
        layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        layoutParams.addRule(CENTER_IN_PARENT);

        addView(errorStateRelativeLayout, layoutParams);
    }


    private void setContentVisibility(boolean visible, List<Integer> skipIds) {
        for (View v : contentViews) {
            if (!skipIds.contains(v.getId())) {
                v.setVisibility(visible ? View.VISIBLE : View.GONE);
            }
        }
    }

    private void hideLoadingView() {
        if (loadingStateRelativeLayout != null) {
            loadingStateRelativeLayout.setVisibility(GONE);
        }
    }

    private void hideEmptyView() {
        if (emptyStateRelativeLayout != null) {
            emptyStateRelativeLayout.setVisibility(GONE);
        }
    }


    /**
     * 显示加载页面
     */
    public void showLoadingView(){

        setLoadingView();
        hideEmptyView();
        hideErrorView();


    }
    private void switchState(State state, OnClickListener onClickListener, List<Integer> skipIds) {
        this.state = state;

        switch (state) {
            case CONTENT:
                //Hide all state views to display content
                hideLoadingView();
                hideEmptyView();
                hideErrorView();

                setContentVisibility(true, skipIds);
                break;
            case LOADING:
                hideEmptyView();
                hideErrorView();

                setLoadingView();
                setContentVisibility(false, skipIds);
                break;
            case EMPTY:
                hideLoadingView();
                hideErrorView();

                setEmptyView();
                setContentVisibility(false, skipIds);
                break;
            case ERROR:
                hideLoadingView();
                hideEmptyView();
                setErrorView();
                setContentVisibility(false, skipIds);
                break;
                default:
                    break;
        }
    }

    /**
     * 显示错误页面
     */
    public void showErrorView(final OnClickListener onClickListener){

        switchState(State.ERROR,null, Collections.<Integer>emptyList());

         errorStateButton.setOnClickListener(new OnClickListener() {
             @Override
             public void onClick(View v) {
                 onClickListener.onClick(v);
             }
         });


    }
    /**
     * 显示空页面
     */
    public void showEmptyView(){

        switchState(State.EMPTY, null, Collections.<Integer>emptyList());

    }



    /**
     * 显示内容页面
     */
    public void showContentView(){
        switchState(State.CONTENT,null, Collections.<Integer>emptyList());
    }

    private void hideErrorView() {
        if (errorStateRelativeLayout != null) {
            errorStateRelativeLayout.setVisibility(GONE);
        }
    }


    public static enum State {
        CONTENT, LOADING, EMPTY, ERROR
    }
}
