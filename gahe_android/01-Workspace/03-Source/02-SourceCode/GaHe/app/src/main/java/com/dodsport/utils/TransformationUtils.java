package com.dodsport.utils;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.squareup.picasso.Transformation;

/**
 * Created by Administrator on 2017/2/15 0015.
 */

public class TransformationUtils {


    /**
     * Picasso 压缩图片
     * @param mImg   图片
     */
    public static Transformation zipImage(final ImageView mImg)
    {
        Transformation transformation = new Transformation() {

            @Override
            public Bitmap transform(Bitmap source) {

                int targetWidth = mImg.getWidth();
                //  Log.i("","source.getHeight()="+source.getHeight()+",source.getWidth()="+source.getWidth()+",targetWidth="+targetWidth);

                if(source.getWidth()==0){
                    return source;
                }

                //如果图片小于设置的宽度，则返回原图
                if(source.getWidth()<targetWidth){
                    return source;
                }else{
                    //如果图片大小大于等于设置的宽度，则按照设置的宽度比例来缩放
                    double aspectRatio = (double) source.getHeight() / (double) source.getWidth();
                    int targetHeight = (int) (targetWidth * aspectRatio);
                    if (targetHeight != 0 && targetWidth != 0) {
                        Bitmap result = Bitmap.createScaledBitmap(source, targetWidth, targetHeight, false);
                        if (result != source) {
                            // Same bitmap is returned if sizes are the same
                            source.recycle();
                        }
                        return result;
                    } else {
                        return source;
                    }
                }

            }

            @Override
            public String key() {
                return "transformation" + " desiredWidth";
            }
        };
        return transformation;
    }

}
