package com.dodsport.consumer.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.inputmethod.InputMethodManager;

import com.dodsport.consumer.util.ToastUtils;


/**
 * 碎片基类
 * @author Administrator
 */

public abstract class BaseFragment extends Fragment {

     private static final String TAG = BaseFragment.class.getSimpleName();
     protected boolean isVisible;
     protected boolean isPrepared;
     protected boolean mHasLoadedOnce;
     private InputMethodManager manager;
    private Activity mActivity;

    /**
      * 在这里实现Fragment数据的缓加载.
      * @param isVisibleToUser
      */
     @Override
     public void setUserVisibleHint(boolean isVisibleToUser) {
          super.setUserVisibleHint(isVisibleToUser);
          if(getUserVisibleHint()) {
               isVisible = true;
               onVisible();
          } else {
               isVisible = false;
               onInvisible();
          }
     }

     @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

//        isPrepared = true;
    }



    @Override
    public void onDestroy() {


        super.onDestroy();

    }

    /**
     * 可见时
     */
     protected void onVisible(){
          lazyLoad();
     }

    /**
     *延迟加载
     * 子类必须重写此方法
     */
     protected abstract void lazyLoad();

    /**
     * 不可见
     */
    protected void onInvisible(){}

    /**
     * Toast
     * @param msg
     */
    protected void Toast(String msg)
     {
          ToastUtils.showShort(getContext(),msg);
     }

    /**
     * 日志
     * @param msg
     */
   // protected void Log(String msg)
     {
      //    LogUtils.i(msg);
     }



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /**
         * 点击空白地方 键盘关闭
         * */
        manager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = activity;
    }


    //
//    /**
//     * 点击空白地方 键盘退出
//     * */
//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        // TODO Auto-generated method stub
//        if (event.getAction() == MotionEvent.ACTION_DOWN) {
//            if (getActivity().getCurrentFocus() != null && getActivity().getCurrentFocus().getWindowToken() != null) {
//                manager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
//            }
//        }
//        return super.onTouchEvent(event);
//    }

}
