package com.dodsport.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.inputmethod.InputMethodManager;

import com.dodsport.request.CallServer;
import com.dodsport.utils.ToastUtils;
import com.yanzhenjie.nohttp.NoHttp;

import static com.dodsport.request.RequestMeansManager.cancelObject;
import static com.dodsport.request.RequestMeansManager.mQueue;
import static com.dodsport.request.RequestMeansManager.object;


/**
 * 碎片基类
 */

public abstract class BaseFragment extends Fragment {

     private static final String TAG = BaseFragment.class.getSimpleName();
     protected boolean isVisible;
     protected boolean isPrepared;
     protected boolean mHasLoadedOnce;
     private InputMethodManager manager;

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

        if (cancelObject!=null){
            // 在组件销毁的时候调用队列的按照sign取消的方法即可取消。
            CallServer.getInstance().cancelBySign(cancelObject);
        }

        if (mQueue !=null && object !=null){
            // 和声明周期绑定，退出时取消这个队列中的所有请求，当然可以在你想取消的时候取消也可以，不一定和声明周期绑定。
            mQueue.cancelBySign(object);
            // 因为回调函数持有了activity，所以退出activity时请停止队列。
            mQueue.stop();
        }

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
        // 初始化请求队列，传入的参数是请求并发值。
        mQueue = NoHttp.newRequestQueue(1);
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
