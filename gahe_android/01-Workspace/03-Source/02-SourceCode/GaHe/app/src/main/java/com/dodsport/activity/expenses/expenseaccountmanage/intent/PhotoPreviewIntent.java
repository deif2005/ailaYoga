package com.dodsport.activity.expenses.expenseaccountmanage.intent;

import android.content.Context;
import android.content.Intent;

import com.dodsport.activity.expenses.expenseaccountmanage.PhotoPreviewActivity;

import java.util.ArrayList;

/**
 * 预览照片
 */
public class PhotoPreviewIntent extends Intent{

    public PhotoPreviewIntent(Context packageContext) {
        super(packageContext, PhotoPreviewActivity.class);
    }

    /**
     * 照片地址
     * @param paths
     */
    public void setPhotoPaths(ArrayList<String> paths){
        this.putStringArrayListExtra(PhotoPreviewActivity.EXTRA_PHOTOS, paths);
    }

    /**
     * 当前照片的下标
     * @param currentItem
     */
    public void setCurrentItem(int currentItem){
        this.putExtra(PhotoPreviewActivity.EXTRA_CURRENT_ITEM, currentItem);
    }


    public void setCharacteristic(String  characteristic){
        this.putExtra(PhotoPreviewActivity.CLASS_CHARACTERISTIC, characteristic);
    }
}
