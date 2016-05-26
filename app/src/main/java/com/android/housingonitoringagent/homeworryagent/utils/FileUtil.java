package com.android.housingonitoringagent.homeworryagent.utils;

import android.app.Application;
import android.content.ContentResolver;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.util.Log;


import com.android.housingonitoringagent.homeworryagent.App;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileUtil {

    public static final String TAG = "FileUtil";
    public static String SDPATH = Environment.getExternalStorageDirectory().getAbsolutePath()
            + "/housePk/";
    public static void initDir(File file) {
        File dir = new File(file.getParent());
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }

    /**
     *
     * @param file
     * @return 文件/目录大小，单位：MB
     */
    public static double getLength(File file) {
        if (file != null && file.exists()) {
            //如果是目录则递归计算其内容的总大小
            if (file.isDirectory()) {
                File[] children = file.listFiles();

                double size = 0;
                for (File f : children)
                    size += getLength(f);

                return size;
            } else {//如果是文件则直接返回其大小
                return (double) file.length() / 1024 / 1024;
            }
        } else {
            Log.d(FileUtil.class.getName(), "文件或者文件夹不存在，请检查路径是否正确！");

            return 0.0;
        }
    }


    public static void saveBitmap(Bitmap bm, String picName) {
        Log.e("", "保存图片");
        try {
            if (!isFileExist("")) {
                File tempf = createSDDir("");
            }
            File f = new File(SDPATH, picName + ".JPEG");
            if (f.exists()) {
                f.delete();
            }
            FileOutputStream out = new FileOutputStream(f);
            bm.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();
            Log.e("", "已经保存");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean isFileExist(String fileName) {
        File file = new File(SDPATH + fileName);
        file.isFile();
        return file.exists();
    }

    public static File createSDDir(String dirName) throws IOException {
        File dir = new File(SDPATH + dirName);
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {

            System.out.println("createSDDir:" + dir.getAbsolutePath());
            System.out.println("createSDDir:" + dir.mkdir());
        }
        return dir;
    }

    /**
     *
     * @param file 保存的位置
     * @param bytes 文件内容
     * @return 是否保存成功
     */
    public static boolean saveFile(File file, byte[] bytes) {
        deleteFile(file);
        initDir(file);

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            fos.write(bytes);
        } catch (FileNotFoundException e) {
            e.printStackTrace();

            return false;
        } catch (IOException e) {
            e.printStackTrace();

            return false;
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return true;
    }

    public static boolean deleteFile(File file) {
        if (file.exists()) {
            file.delete();
            return true;
        }

        return false;
    }

    /**
     * 删除方法 如果传入的directory是个文件，将不做处理
     *
     * @param directory
     */
    public static void deleteFilesByDirectory(File directory) {
        if (directory != null && directory.exists() && directory.isDirectory()) {
            File[] files = directory.listFiles();
            if (files == null) {
                return;
            }
            for (File item : files) {
                if (item.isDirectory()) {
                    deleteFilesByDirectory(item);
                }
                item.delete();
            }
        }
    }

    public static String getSuffixName(File file) {
        if (file == null) {
            Log.e(TAG, "File is null.");
            return "";
        }
        return getSuffixName(file.getName());
    }

    public static String getSuffixName(String filename) {
        if (filename == null) {
            Log.e(TAG, "Filename is null.");
            return "";
        }

        int start = filename.lastIndexOf(".");
        if (start == -1 && start >= filename.length()) {
            Log.e(TAG, "Not found.");
            return "";
        }

        String suffix = filename.substring(start + 1, filename.length());
        return suffix;
    }

    // 一次最多读取字节数，10M
    public static final int MAX_BYTES_READ_SIZE = 10485760;

    public static byte[] getBytes(String path) throws FileSizeTooLargeException, IOException {
        return getBytes(new File(path));
    }

    public static byte[] getBytes(File file) throws FileSizeTooLargeException, IOException {
        long size = file.length();
        if (size > MAX_BYTES_READ_SIZE) {
            throw new FileSizeTooLargeException();
        }

        FileInputStream fis = new FileInputStream(file);

        byte[] bytes;
        try {
            bytes = getBytes(fis);
        } finally {
            fis.close();
        }

        return bytes;
    }

    public static byte[] getBytes(Uri uri) throws FileSizeTooLargeException, IOException {
        Application context = App.getInstance();

        ContentResolver contentResolver = context.getContentResolver();
        ParcelFileDescriptor parcelFileDescriptor = contentResolver.openFileDescriptor(uri, "r");

        byte[] bytes;
        try {
            bytes = getBytes(parcelFileDescriptor.getFileDescriptor());
        } finally {
            parcelFileDescriptor.close();
        }

        return bytes;
    }

    public static byte[] getBytes(FileDescriptor fileDescriptor) throws FileSizeTooLargeException, IOException {
        FileInputStream fis = new FileInputStream(fileDescriptor);

        byte[] bytes;
        try {
            bytes = getBytes(fis);
        } finally {
            fis.close();
        }

        return bytes;
    }

    // 注意在方法调用处关闭FileInputStream!!!
    public static byte[] getBytes(FileInputStream fis) throws FileSizeTooLargeException, IOException {
        byte[] bytes;

        long size = fis.getChannel().size();

        if (size > MAX_BYTES_READ_SIZE) {
            throw new FileSizeTooLargeException();
        }

        bytes = new byte[(int)size];
        fis.read(bytes);

        return bytes;
    }

    public static class FileSizeTooLargeException extends Exception {
        public FileSizeTooLargeException() {
            super("File size is too large.");
        }
    }
}
