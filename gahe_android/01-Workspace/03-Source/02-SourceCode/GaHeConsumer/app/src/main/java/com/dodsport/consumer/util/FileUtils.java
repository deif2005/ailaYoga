package com.dodsport.consumer.util;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.os.StatFs;
import android.provider.MediaStore;
import android.text.format.Formatter;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *文件工具类
 * @author Administrator
 * @date 2016/12/20 0020
 */

public class FileUtils {

    private final static String TAG = FileUtils.class.getSimpleName();
    //双sd卡时，根据”设置“里面的数据存储位置选择，获得的是内置sd卡或外置sd卡 储根目录
    public final static String DEFAULT_FILE_DIR = Environment.getExternalStorageDirectory() + "/com.iota.livetv";

    //存放临时图片
    public final static String DEFAULT_FILE = DEFAULT_FILE_DIR + "/tempImage/";

    //存放cache
    public final static String DEFAULT_CACHE = DEFAULT_FILE_DIR + "/cache/";
    //文件
    public final static String DEFAULT_FILE_NEW = DEFAULT_FILE_DIR + "/files";
    //文件
    public final static String DEFAULT_FILE_NEWA = DEFAULT_FILE_DIR + "/filea";

    public final static String DEFAULT_FILE_NEWDEX = DEFAULT_FILE_DIR + "/filedex";
    //数据库
    public final static String DB_PATH = DEFAULT_FILE_NEW+"/mydata";

    private FileUtils() {
        throw new AssertionError("不可实例化的类");
    }

    /**
     * 获取存储设备根目录
     */
    public static String getRootPath() {
        return DEFAULT_FILE_DIR;
    }

    public static boolean isExist(String fileName) {
        String filePath = DEFAULT_FILE_DIR + fileName;
        File file = new File(filePath);
        return file.exists();
    }

    /**
     * 读取文件内容
     *
     * @param filePath 文件路径
     * @return 文件内容字符串
     */
    public static String readFile(String filePath) {
        StringBuilder sb = new StringBuilder();
        try {
            File file = new File(filePath);
            InputStream in;
            in = new FileInputStream(file);
            int tmpbyte;
            while ((tmpbyte = in.read()) != -1) {
                sb.append((char) tmpbyte);
            }
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    /**
     * 从Assets读取文件
     *
     * @param fileName 文件名称
     * @return 文件内容
     */
    public static String getFromAssets(Context context, String fileName) {
        try {
            InputStreamReader inputReader = new InputStreamReader(context.getAssets().open(fileName));
            BufferedReader bufReader = new BufferedReader(inputReader);
            String line;
            StringBuilder Result = new StringBuilder();
            while ((line = bufReader.readLine()) != null)
                Result.append(line);
            return Result.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 保存json字符串到sd卡
     *
     * @param object   json对象
     * @param fileName 保存的文件名，后缀为json
     */
    public static void writeJSONObjectToFile(JSONObject object, String fileName) {
        String sdStatus = Environment.getExternalStorageState();
        if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) {
            return;
        }
        try {
            File path = new File(DEFAULT_FILE_DIR);
            File file = new File(DEFAULT_FILE_DIR + fileName);
            if (!path.exists()) {
                path.mkdir();
            }
            if (file.exists()) {
                file.delete();
            }
            file.createNewFile();
            FileOutputStream stream = new FileOutputStream(file);
            byte[] buf = object.toString().getBytes();
            stream.write(buf);
            stream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String readFile(Context context, String fileName) {
        String result;
        try {
            FileInputStream stream = context.openFileInput(fileName);
            byte[] b = new byte[stream.available()];
            stream.read(b);
            result = new String(b);
        } catch (FileNotFoundException e) {
            Log.d("TestFile", "File not found.");
            return null;
        } catch (IOException e) {
            Log.d("TestFile", "File write error.");
            return null;
        }
        return result;
    }

    public static void writeFile(Context context, String jsonString, String fileName) {
        try {
            FileOutputStream stream = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            byte[] buf = jsonString.getBytes();
            stream.write(buf);
            stream.close();
        } catch (FileNotFoundException e) {
            Log.d("TestFile", "File not found.");
        } catch (IOException e) {
            Log.d("TestFile", "File write error.");
        }
    }

    /**
     * 删除应用中文件
     *
     * @param context  上下文
     * @param fileName 文件名称
     */
    public static void deleteFile(Context context, String fileName) {
        context.deleteFile(fileName);
    }

    /**
     * 清除应用中所有文件
     *
     * @param context 上下文
     */
    public static void deleteAllFileInApp(Context context) {
        File fileDir = context.getFilesDir();
        if (fileDir.isDirectory()) {
            for (File file : fileDir.listFiles()) {
                file.delete();
            }
        }
    }

    /**
     * 在SD卡或手机内存上创建目录，如存在就删除原目录，再创建
     */
    public static File createDir(String dirName) {
        File dir = null;
        try {
            if (dirName.indexOf(DEFAULT_FILE_DIR) == -1)
                dirName = DEFAULT_FILE_DIR + dirName;
            dir = new File(dirName);
            if (dir.exists())
                dir.delete();
            dir.mkdirs();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dir;
    }



    /**
     * 在SD卡或手机内存上创建目录，如存在就删除原目录，再创建
     *
     */
    public static boolean createDirs(String dirName) {
        boolean zai = false;
        File dir = null;
        try {
            if (dirName.indexOf(DEFAULT_FILE_DIR) == -1)
                dirName = DEFAULT_FILE_DIR + dirName;
            dir = new File(dirName);
            if (!dir.exists()){
                //dir.mkdirs();//创建文件夹
                zai = true;

            }

//                dir.delete();//删除文件夹

        } catch (Exception e) {
            e.printStackTrace();
        }
        return zai;
    }


    /**
     * 在SD卡内存上创建目录，如存在就删除原目录，再创建
     */
    public static File dataDir(String dirName, String pathName) {
        File dir = null;
        try {
            if (dirName.indexOf(pathName) == -1)
                dirName = pathName + dirName;
            dir = new File(dirName);
            if (dir.exists())
                dir.delete();
            dir.mkdirs();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dir;
    }


    /**
     * 删除保存到SD卡的文件
     *
     * @param fileName 保存的文件名，后缀为json
     */
    public static void deleteFile(String fileName) {
        String sdStatus = Environment.getExternalStorageState();
        if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) {
            return;
        }
        try {
            File path = new File(DEFAULT_FILE_DIR);
            File file = new File(DEFAULT_FILE_DIR + fileName);
            if (!path.exists()) {
                return;
            }
            if (file.exists()) {
                file.delete();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取文件夹大小
     *
     * @param file File实例
     * @return long
     */
    public static long getFolderSize(File file) {
        long size = 0;
        try {
            File[] fileList = file.listFiles();
            if(fileList!=null && fileList.length>0)
            for (File localFile : fileList) {
                if (localFile.isDirectory()) {
                    size = size + getFolderSize(localFile);
                } else {
                    size = size + localFile.length();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return size;
    }

    /**
     * 获取文件夹size
     * */
    public static String formatFileSize(long fileS) {// 转换文件大小
        DecimalFormat df = new DecimalFormat("#.00");
        DecimalFormat dft = new DecimalFormat("#00");
        String fileSizeString;
        if (fileS == 0) {
            fileSizeString = "0.00B";
        } else if (fileS < 1024) {
            fileSizeString = df.format((double) fileS) + "B";

        } else if (fileS < 1048576) {
            fileSizeString = df.format((double) fileS / 1024) + "K";
        } else if (fileS < 1073741824) {
            fileSizeString = df.format((double) fileS / 1048576) + "M";
            String format = dft.format((double) fileS / 1048576);
            int i = Integer.parseInt(format);
//            Log.i("******", "转换后的大小 "+format+"");

        } else {
            fileSizeString = df.format((double) fileS / 1073741824) + "G";
        }
        return fileSizeString;
    }


    /**
     *  递归方式 计算文件的大小
     */
    public static long getTotalSizeOfFilesInDir(File file) {
        if (file.isFile())
            return file.length();
        final File[] children = file.listFiles();
        long total = 0;
        if (children != null)
            for (final File child : children)
                total += getTotalSizeOfFilesInDir(child);
        return total;
    }



    /**
     * 删除指定目录下文件及目录
     *
     * @param deleteThisPath true 删除该路径
     * @param filePath       文件夹路径
     */
    public static void deleteFolderFile(String filePath, boolean deleteThisPath) {
        if (!filePath.isEmpty()) {
            try {
                File file = new File(filePath);
                if (file.isDirectory()) {// 处理目录
                    File[] files = file.listFiles();
                    for (File localFile : files) {
                        deleteFolderFile(localFile.getAbsolutePath(), true);
                    }
                }
                if (deleteThisPath) {
                    if (!file.isDirectory()) {// 如果是文件，删除
                        file.delete();
                    } else {// 目录
                        if (file.listFiles().length == 0) {// 目录下没有文件或者目录，删除
                            file.delete();
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    // 删除文件夹
    // param folderPath 文件夹完整绝对路径
    public static void delFolder(String folderPath) {
        try {
//            delAllFile(folderPath); // 删除完里面所有内容
            String filePath = folderPath;
            filePath = filePath.toString();
            File myFilePath = new File(filePath);
            myFilePath.delete(); // 删除空文件夹
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /* Checks if external storage is available for read and write */
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    /* Checks if external storage is available to at least read */
    public boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }
    /**
     * 针对小米手机获取图片
     * */
    public static Uri geturi(android.content.Intent intent, Activity activity) {
        Uri uri = intent.getData();
        String type = intent.getType();
        if (uri.getScheme().equals("file") && (type.contains("image/"))) {
            String path = uri.getEncodedPath();
            if (path != null) {
                path = Uri.decode(path);
                ContentResolver cr = activity.getContentResolver();
                StringBuffer buff = new StringBuffer();
                buff.append("(").append(MediaStore.Images.ImageColumns.DATA).append("=")
                        .append("'" + path + "'").append(")");
                Cursor cur = cr.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        new String[]{MediaStore.Images.ImageColumns._ID},
                        buff.toString(), null, null);
                int index = 0;
                for (cur.moveToFirst(); !cur.isAfterLast(); cur.moveToNext()) {
                    index = cur.getColumnIndex(MediaStore.Images.ImageColumns._ID);
                    // set _id value
                    index = cur.getInt(index);
                }
                if (index == 0) {
                    // do nothing
                } else {
                    Uri uri_temp = Uri
                            .parse("content://media/external/images/media/"
                                    + index);
                    if (uri_temp != null) {
                        uri = uri_temp;
                    }
                }
            }
        }
        return uri;
    }

    /**
     * 判断是否是汉字
     * */
    public static boolean isChinese(String str) {
        String regEx = "[\u4e00-\u9fa5]";
        Pattern pat = Pattern.compile(regEx);
        Matcher matcher = pat.matcher(str);
        boolean flg = false;
        if (matcher.find())
            flg = true;

        return flg;
    }



    /**
     * Bitmap保存成Jpeg保存到SdCard
     */
    public static void saveBitmapToSdcard(String filePath, Bitmap img) {
        try {
            if (filePath.equals(""))
                return;
            deleteFile(filePath);
            File myCaptureFile = new File(filePath);
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
            img.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
            bos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     *不使用递归遍历文件
     * */
    public static LinkedList<File> traverseFolder1(String path) {
        int fileNum = 0, folderNum = 0;
        File file = new File(path);
        if (file.exists()) {
            LinkedList<File> list = new LinkedList<File>();
            File[] files = file.listFiles();
            for (File file2 : files) {
                if (file2.isDirectory()) {
//                    Log.i("******", "文件夹111 "+ file2.getAbsolutePath());
//                    System.out.println("文件夹:111" + file2.getAbsolutePath());
                    list.add(file2);
                    fileNum++;

                } else {
//                    Log.i("******", "文件444 "+ file2.getAbsolutePath());
//                    System.out.println("文件:4444" + file2.getAbsolutePath());
                    folderNum++;
                }
            }
            File temp_file;
            while (!list.isEmpty()) {
                temp_file = list.removeFirst();
                files = temp_file.listFiles();
                for (File file2 : files) {
                    if (file2.isDirectory()) {
//                        Log.i("******", "文件夹333 "+ file2.getAbsolutePath());
//                        System.out.println("文件夹333:" + file2.getAbsolutePath());
                        list.add(file2);
                        fileNum++;

                    } else {
//                        Log.i("******", "文件222 "+ file2.getAbsolutePath());
//                        System.out.println("文件222:" + file2.getAbsolutePath());
                        folderNum++;
                    }
                }


            }

            return list;
        } else {
            Log.i("******", "文件不存在 ");
            System.out.println("文件不存在!");
            return null;
        }
//        Log.i("******", "文件夹共有:" + folderNum + ",文件共有:" + fileNum);
//        System.out.println("文件夹共有:" + folderNum + ",文件共有:" + fileNum);
//
//        return null;
    }

    /**
     * 使用递归遍历文件
     *
     * */
    public static LinkedList<File> traverseFolder2(String path) {
        LinkedList<File> filea = new LinkedList<File>();
        File file = new File(path);
        if (file.exists()) {
            File[] files = file.listFiles();
            if (files.length == 0) {
//                System.out.println("文件夹是空的!");
                return null;
            } else {
                for (File file2 : files) {
                    if (file2.isDirectory()) {
//                        System.out.println("文件夹:" + file2.getAbsolutePath());
                        traverseFolder2(file2.getAbsolutePath());

                    } else {
//                        System.out.println("文件:" + file2.getAbsolutePath());
                    filea.add(file2);
                    }
                }
            }
        } else {
            System.out.println("文件不存在!");
        }
        return filea;
    }


    /**读取本地文件*/
    public static List<File> getFiles(String path){
        File root = new File(path);
        List<File> files = new ArrayList<File>();
        if(!root.isDirectory()){
            files.add(root);
        }else{
            File[] subFiles = root.listFiles();
            for(File f : subFiles){
                files.addAll(getFiles(f.getAbsolutePath()));
            }
        }
        return files;
    }




    /**
     * 查找指定目录下的 指定类型文件
     *
     * */
    public static List<File> getFileList(String strPath) {
        List<File> filelist = new ArrayList<>();
        File dir = new File(strPath);
        File[] files = dir.listFiles(); // 该文件目录下文件全部放入数组
        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                String fileName = files[i].getName();
                if (files[i].isDirectory()) { // 判断是文件还是文件夹
                    getFileList(files[i].getAbsolutePath()); // 获取文件绝对路径
                } else if (fileName.endsWith("png")) { // 判断文件名是否以.avi结尾
                    String strFileName = files[i].getAbsolutePath();
                    System.out.println("---" + strFileName);
                    filelist.add(files[i]);
                } else {
                    continue;
                }
            }

        }
        return filelist;
    }





    // 从sd卡获取图片资源
    public static List<String> getImagePathFromSD(Context context ) {

        // 图片列表
        List<String> picList = new ArrayList<String>();

        File cacheDir = context.getCacheDir();

        // 得到sd卡内路径
        String imagePath =cacheDir + "/.nomedia";
//                Environment.getExternalStorageDirectory().toString()
//                        + "/image";

        // 得到该路径文件夹下所有的文件
        File mfile = new File(imagePath);
        File[] files = mfile.listFiles();

        // 将所有的文件存入ArrayList中,并过滤所有图片格式的文件
        for (int i = 0; i < files.length; i++) {
            File file = files[i];
            if (checkIsImageFile(file.getPath())) {
                picList.add(file.getPath());
            }

        }

        // 返回得到的图片列表
        return picList;

    }

    // 检查扩展名，得到图片格式的文件
    private static boolean checkIsImageFile(String fName) {
        boolean isImageFile = false;

        // 获取扩展名
        String FileEnd = fName.substring(fName.lastIndexOf(".") + 1,
                fName.length()).toLowerCase();
        if (FileEnd.equals("jpg") || FileEnd.equals("gif")
                || FileEnd.equals("png") || FileEnd.equals("jpeg")
                || FileEnd.equals("bmp")) {
            isImageFile = true;
        } else {
            isImageFile = false;
        }

        return isImageFile;

    }


    /**
     * 查看SD卡 是否存在
     * */
    public static boolean ExistSDCard() {
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            return true;
        } else
            return false;
    }



    /**
     * 根据路径获取内存状态
     * @param path
     * @return
     */
    public static long getMemoryInfo(Context context, File path) {
        // 获得一个磁盘状态对象
        StatFs stat = new StatFs(path.getPath());

        long blockSize = stat.getBlockSize();   // 获得一个扇区的大小

        long totalBlocks = stat.getBlockCount();    // 获得扇区的总数

        long availableBlocks = stat.getAvailableBlocks();   // 获得可用的扇区数量

        // 总空间
        String totalMemory =  Formatter.formatFileSize(context, totalBlocks * blockSize);
        // 可用空间 GB
        String availableMemory = Formatter.formatFileSize(context, availableBlocks * blockSize);

        long AvailableBlocks = availableBlocks * blockSize;
//        Log.i("*****", "内存状态"+()+"");

        return AvailableBlocks;

}


    // 查看所有的sd路径
    public static String getSDCardPathEx() {
        String mount = new String();
        try {
            Runtime runtime = Runtime.getRuntime();
            Process proc = runtime.exec("mount");
            InputStream is = proc.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            String line;
            BufferedReader br = new BufferedReader(isr);
            while ((line = br.readLine()) != null) {
                if (line.contains("secure"))
                    continue;
                if (line.contains("asec"))
                    continue;

                if (line.contains("fat")) {
                    String columns[] = line.split(" ");
                    if (columns != null && columns.length > 1) {
                        mount = mount.concat("*" + columns[1] + "\n");
                    }
                } else if (line.contains("fuse")) {
                    String columns[] = line.split(" ");
                    if (columns != null && columns.length > 1) {
                        mount = mount.concat(columns[1] + "\n");
                    }
                }
            }
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return mount;
    }



    /** 获取外置SD卡路径
     * @return  应该就一条记录或空
     */
    public static List<String> getExtSDCardPath()
    {
        List<String> lResult = new ArrayList<String>();
        try {
            Runtime rt = Runtime.getRuntime();
            Process proc = rt.exec("mount");
            InputStream is = proc.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String line;
            while ((line = br.readLine()) != null) {
                if (line.contains("extSdCard"))
                {
                    String[] arr = line.split(" ");
                    String path = arr[1];
                    File file = new File(path);
                    if (file.isDirectory())
                    {
                        lResult.add(path);
                    }
                }
            }
            isr.close();
        } catch (Exception e) {
        }
        return lResult;
    }


}
