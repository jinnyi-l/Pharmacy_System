package com.yjx.util;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.text.DecimalFormat;
import java.util.Date;

@Slf4j
public class FileUtils {
    /**
     * 定义GB的计算常量
     */
    private static final int GB = 1024 * 1024 * 1024;
    /**
     * 定义MB的计算常量
     */
    private static final int MB = 1024 * 1024;
    /**
     * 定义KB的计算常量
     */
    private static final int KB = 1024;
    /**
     * 格式化小数
     */
    private static final DecimalFormat DF = new DecimalFormat("0.00");

    /**
     * 默认最大大小 50M
     */
    public static final long DEFAULT_MAX_SIZE = 50 * 1024 * 1024;

    /**
     * 默认的文件名最大长度 256
     */
    public static final int DEFAULT_FILE_NAME_LENGTH = 256;


    /**
     * 获取文件的后缀名,不带"."
     *
     * @param fileName 文件名
     * @return 后缀名
     */
    public static String getExtensionName(String fileName) {
        if (StringUtils.isNotEmpty(fileName)) {
            int dot = fileName.lastIndexOf(".");
            if (dot > -1 && dot < (fileName.length() - 1)) {
                return fileName.substring(dot + 1);
            }
        }
        return fileName;
    }

    /**
     * 获取文件名称,不带后缀
     *
     * @param fileName 文件名
     * @return 文件名(不带后缀)
     */
    public static String getFileNameNoExtension(String fileName) {
        if (StringUtils.isNotEmpty(fileName)) {
            int dot = fileName.lastIndexOf(".");
            if (dot > -1 && dot < fileName.length()) {
                return fileName.substring(0, dot);
            }
        }
        return fileName;
    }

    /**
     * 大小转换,将字节大小转为对应的单位
     *
     * @param size 字节大小
     * @return 转换后的大小
     */
    public static String getSizeString(long size) {
        String resultSize = "";
        if (size / GB >= 1) {
            //如果当前Byte的值大于等于1GB
            resultSize = DF.format(size / (float) GB) + "GB";
        } else if (size / MB >= 1) {
            //如果当前Byte的值大于等于1MB
            resultSize = DF.format(size / (float) MB) + "MB";
        } else if (size / KB >= 1) {
            //如果当前Byte的值大于等于1KB
            resultSize = DF.format(size / (float) KB) + "KB";
        } else {
            resultSize = size + "B";
        }
        return resultSize;
    }

    /**
     * InputStream转为文件
     *
     * @param ins  inputStream
     * @param name 文件名
     * @return File
     * @throws Exception
     */
    public static File inputStreamToFile(InputStream ins, String name) throws Exception {
        File file = new File(System.getProperty("java.io.tmpdir") + File.separator + name);
        if (file.exists()) {
            return file;
        }
        OutputStream os = new FileOutputStream(file);
        int bytesRead;
        byte[] buffer = new byte[8192];
        while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
            os.write(buffer, 0, bytesRead);
        }
        os.close();
        ins.close();
        return file;
    }

    /**
     * 获取文件类型
     *
     * @param type 文件名
     * @return 文件类型
     */
    public static String getFileType(String type) {
        String documents = "txt doc pdf ppt pps xlsx xls docx";
        String music = "mp3 wav wma mpa ram ra aac aif m4a";
        String video = "avi mpg mpe mpeg asf wmv mov qt rm mp4 flv m4v webm ogv ogg";
        String image = "bmp dib pcp dif wmf gif jpg tif eps psd cdr iff tga pcd mpt png jpeg ico";
        if (image.contains(type)) {
            return "image";
        } else if (documents.contains(type)) {
            return "document";
        } else if (music.contains(type)) {
            return "music";
        } else if (video.contains(type)) {
            return "video";
        } else {
            return "other";
        }
    }

    /**
     * 删除文件<br>
     *
     * @param file 文件对象
     * @return 成功与否
     * @throws RuntimeException IO异常
     */
    public static boolean del(File file) throws RuntimeException {
        if (file == null || false == file.exists()) {
            // 如果文件不存在或已被删除，此处返回true表示删除成功
            return true;
        }
        if (file.isDirectory()) {
            return false;
        }
        // 删除文件或清空后的目录
        return file.delete();
    }

    /**
     * 删除文件<br>
     * 路径如果为相对路径，会转换为ClassPath路径！<br>
     * 某个文件删除失败会终止删除操作
     *
     * @param fullFileOrDirPath 文件或者目录的路径
     * @return 成功与否
     * @throws RuntimeException IO异常
     */
    public static boolean del(String fullFileOrDirPath) throws RuntimeException {
        return del(file(fullFileOrDirPath));
    }

    /**
     * 创建File对象
     *
     * @param path 文件路径
     * @return File
     */
    public static File file(String path) {
        if (null == path) {
            return null;
        }
        return new File(path);
    }

}
