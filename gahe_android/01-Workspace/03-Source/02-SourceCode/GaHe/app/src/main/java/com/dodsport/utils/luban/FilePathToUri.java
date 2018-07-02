package com.dodsport.utils.luban;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Administrator on 2017/1/5 0005.
 */

public class FilePathToUri {
    private static Uri mUri;

    public static Uri FilePathToUri(Context context, String path){
        new FilePathToUri(context,path);
        return mUri;
    }


    public FilePathToUri(Context context, String path) {
        Log.d("TAG", "filePath is " + path);
        if (path != null) {
            path = Uri.decode(path);
            Log.d("TAG", "path2 is " + path);
            ContentResolver cr = context.getContentResolver();
            StringBuffer buff = new StringBuffer();
            buff.append("(")
                    .append(MediaStore.Images.ImageColumns.DATA)
                    .append("=")
                    .append("'" + path + "'")
                    .append(")");
            Cursor cur = cr.query(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    new String[]{MediaStore.Images.ImageColumns._ID},
                    buff.toString(), null, null);
            int index = 0;
            for (cur.moveToFirst(); !cur.isAfterLast(); cur
                    .moveToNext()) {
                index = cur.getColumnIndex(MediaStore.Images.ImageColumns._ID);
                // set _id value
                index = cur.getInt(index);
            }
            if (index == 0) {
                //do nothing
            } else {
                Uri uri_temp = Uri
                        .parse("content://media/external/images/media/"
                                + index);
                if (uri_temp != null) {
                    mUri = uri_temp;
//                    return uri_temp;
                } else {
                    Toast.makeText(context, "获取图片失败，请重试！", Toast.LENGTH_SHORT).show();

                }
            }
        }

    }
}
