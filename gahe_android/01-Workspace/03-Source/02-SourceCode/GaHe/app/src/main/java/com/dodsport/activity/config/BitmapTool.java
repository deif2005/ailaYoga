package com.dodsport.activity.config;



import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * 裁剪图片 让图片与屏幕适配
 *
 */
public class BitmapTool {

    public static final int UNCONSTRAINED = -1;

    public static Bitmap getBitmap(String filePath, int width, int height) throws FileNotFoundException {
        File file = new File(filePath);
        if (!file.exists()) {
            throw new FileNotFoundException();
        }
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inJustDecodeBounds = true;
        InputStream is = new FileInputStream(file);
        BitmapFactory.decodeStream(is, null, opts);

        opts.inPreferredConfig = Bitmap.Config.RGB_565;
        opts.inPurgeable = true;
        opts.inInputShareable = true;
        opts.inSampleSize = computeSampleSize(opts, width > height ? width
                : height, width * height);
        opts.inJustDecodeBounds = false;
        return BitmapFactory.decodeStream(is, null, opts);
    }

    private static int computeSampleSize(BitmapFactory.Options options,
                                         int minSideLength, int maxNumOfPixels) {
        int initialSize = computeInitialSampleSize(options, minSideLength,
                maxNumOfPixels);

        int roundedSize;
        if (initialSize <= 8) {
            roundedSize = 1;
            while (roundedSize < initialSize) {
                roundedSize <<= 1;
            }
        } else {
            roundedSize = (initialSize + 7) / 8 * 8;
        }
        return roundedSize;
    }

    private static int computeInitialSampleSize(BitmapFactory.Options options,
                                                int minSideLength, int maxNumOfPixels) {
        double w = options.outWidth;
        double h = options.outHeight;
        int lowerBound = (maxNumOfPixels == UNCONSTRAINED) ? 1 : (int) Math
                .ceil(Math.sqrt(w * h / maxNumOfPixels));
        int upperBound = (minSideLength == UNCONSTRAINED) ? 128 : (int) Math
                .min(Math.floor(w / minSideLength),
                        Math.floor(h / minSideLength));
        if (upperBound < lowerBound) {
            return lowerBound;
        }
        if ((maxNumOfPixels == UNCONSTRAINED)
                && (minSideLength == UNCONSTRAINED)) {
            return 1;
        } else if (minSideLength == UNCONSTRAINED) {
            return lowerBound;
        } else {
            return upperBound;
        }
    }

    public static Bitmap getBitmap(Resources resources, int resID, int width, int height) {
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(resources, resID, opts);

        opts.inPreferredConfig = Bitmap.Config.ARGB_8888;
        opts.inPurgeable = true;
        opts.inInputShareable = true;
        opts.inSampleSize = computeSampleSize(opts, width > height ? width
                : height, width * height);
        opts.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(resources, resID, opts);
    }
}

