package com.android.housingonitoringagent.homeworryagent.utils;


import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.content.CursorLoader;
import android.util.Log;
import android.webkit.MimeTypeMap;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class UriUtil {

    private static final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

    public static Uri fromResource(Context context, int resID) {
        return Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" +
                context.getResources().getResourcePackageName(resID) + '/' +
                context.getResources().getResourceTypeName(resID) + '/' +
                context.getResources().getResourceEntryName(resID));
    }

    public static String getExtensionFromMimeType(Context context, Uri uri) {
        if (uri != null) {
            String uriStr = uri.getEncodedPath();

            if (uriStr.startsWith("file:///")) {
                return FileUtil.getSuffixName(uriStr);
            } else {
                ContentResolver cR = context.getContentResolver();
                MimeTypeMap mime = MimeTypeMap.getSingleton();

                String extension = mime.getExtensionFromMimeType(cR.getType(uri));

                return extension == null ? "" : extension;
            }
        }

        return "";
    }


    public static byte[] getBytes(Context context, Uri uri) throws IOException {
        InputStream inputStream = context.getContentResolver().openInputStream(uri);
        if (inputStream == null) {
            return null;
        }

        return getBytes(inputStream);
    }

    public static byte[] getBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];

        int len;
        try {
            while ((len = inputStream.read(buffer)) != -1) {
                byteBuffer.write(buffer, 0, len);
            }
        } finally {
            inputStream.close();
        }

        return byteBuffer.toByteArray();
    }

    /**如果是对媒体文件，在android开机的时候回去扫描，然后把路径添加到数据库中。
     *由打印的contentUri可以看到：2种结构。正常的是：content://那么这种就要去数据库读取path。
     *另外一种是Uri是 file:///那么这种是 Uri.fromFile(File file);得到的
     */
    public static File toFile(Context context, Uri uri) {
        String uriPath;

        if (isKitKat) {
            uriPath = getRealFilePathInKitkat(context, uri);

            Log.d(UriUtil.class.getName(), "uriPath(4.4以上):" + uriPath);
        } else {
            String[] projection = {MediaStore.Images.Media.DATA};
            CursorLoader loader = new CursorLoader(context, uri, projection, null, null, null);
            Cursor cursor = null;
            try {
                cursor = loader.loadInBackground();
                int column_index = cursor.getColumnIndex(MediaStore.Images.Media.DATA);
                cursor.moveToFirst();
                uriPath = cursor.getString(column_index);

                //如果是正常的查询到数据库。然后返回结构
                return new File(uriPath);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }
            //如果是文件。Uri.fromFile(File file)生成的uri。那么下面这个方法可以得到结果
            uriPath = uri.getPath();

            Log.d(UriUtil.class.getName(), "uriPath(4.4以下):" + uriPath);
        }

        return new File(uriPath == null ? "" : uriPath);
    }

    public static String getRealFilePath(Context context, Uri uri) {
        if (uri == null) {
            return null;
        }

        String path = null;

        if (isKitKat) {
            path = getRealFilePathInKitkat(context, uri);
        } else {
            final String scheme = uri.getScheme();

            if (scheme == null)
                path = uri.getPath();
            else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
                path = uri.getPath();
            } else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
                Cursor cursor = context.getContentResolver().query(uri, new String[]{MediaStore.Images.ImageColumns.DATA}, null, null, null);
                if (null != cursor) {
                    if (cursor.moveToFirst()) {
                        int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                        if (index > -1) {
                            path = cursor.getString(index);
                        }
                    }
                    cursor.close();
                }
            }
        }

        return path;
    }

    @SuppressLint("NewApi")
    protected static String getRealFilePathInKitkat(final Context context, final Uri uri) {
        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }

                // TODO handle non-primary volumes
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{split[1]};

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context       The context.
     * @param uri           The Uri to query.
     * @param selection     (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    public static String getDataColumn(Context context, Uri uri, String selection,
                                       String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {column};

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }
}
