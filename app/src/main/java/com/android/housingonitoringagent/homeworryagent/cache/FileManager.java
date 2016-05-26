package com.android.housingonitoringagent.homeworryagent.cache;


import com.android.housingonitoringagent.homeworryagent.App;

import java.io.File;
import java.util.UUID;

public class FileManager {

    //"/storage/emulated/0/Android/data/包名/files/camera/"
    public static final String CAMERA_DIR = "camera/";
    //"/storage/emulated/0/Android/data/包名/files/crop/"
    public static final String CROP_DIR = "crop/";
    //"/storage/emulated/0/Android/data/包名/files/compress/"
    public static final String COMPRESS_DIR = "compress/";

    /**
     *
     * @return 存储拍照的目录
     */
    public static File cameraDir() {
        return new File(App.getInstance().getExternalFilesDir(null), CAMERA_DIR);
    }

    /**
     *
     * @return 存储裁图的目录
     */
    public static File cropDir() {
        return new File(App.getInstance().getExternalFilesDir(null), CROP_DIR);
    }

    /**
     *
     * @return 存储压缩图片的目录
     */
    public static File compressDir() {
        return new File(App.getInstance().getExternalFilesDir(null), COMPRESS_DIR);
    }

    /**
     * 创建一个新的File，保存新的裁图
     * @return File对象
     */
    public static File newCropFile() {
        File dir = cropDir();
        if (!dir.exists()) {
            dir.mkdirs();
        }

        return new File(cropDir(), "housingonitoringplatform" + UUID.randomUUID() + ".png");
    }
}
