package com.dodsport.utils.IDCardUtil;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Administrator on 2017/1/4 0004.
 */

public class Result {

    /**
     * 错误消息，为空时，代表验证通过
     */
    private String error;
    private boolean ID = false;

    public boolean isLegal() {
    //两个变量为默认值,即认为是合法的
        return error == null || error.equals("");
    }

    public String getError() {
        return error;
    }

    public void setError(String message) {
        this.error = message;
    }

    public boolean show(Context context) {
        if (!isLegal()){
            Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
            return true;
        }else {
            return false;
        }

    }
}
