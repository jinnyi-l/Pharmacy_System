package com.yjx.util;

import com.yjx.controller.MedicineController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.UUID;

@Slf4j
@SuppressWarnings("all")
public class MyPictureUploadUtils {

    /**
     * 创建目录
     *
     * @param file    文件对象
     * @param dirName 要存的目录名称，例如要创建一个carousel文件夹，dirName="carousel"
     * @return
     */
    public String createDir(MultipartFile file, String dirName) {
        // 图像文件的后缀
        String imgFileSuffix = "";
        // 图片类型
        String fileType = file.getContentType();
        String[][] supportedFormatTable = {
                {"image/png", ".png"},
                {"image/bmp", ".bmp"},
                {"image/jpg", ".jpg"},
                {"image/gif", ".gif"},
                {"image/jpeg", ".jpg"}
        };
        // 循环判断图片类型
        for (String[] strings : supportedFormatTable) {
            if (strings[0].equals(fileType)) {
                imgFileSuffix = strings[1];
                break;
            }
        }
        // 文件的前缀，eg:20241025210226657626a6c
        String imgFilePrefix = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(Calendar.getInstance().getTime());
        String uuid = UUID.randomUUID().toString().substring(0, 6);
        // 新图片命名
        String newImgFileName = imgFilePrefix + uuid + imgFileSuffix;
        // 获取当前项目的根路径（编译后的）： /E:/GraduationProject/pharmacy/target/classes/
        String rootPath = MedicineController.class.getResource("/").getPath();
        // 获取存储的有效路径
        String effectivePath = rootPath + "static/img/";

        File correspondingDir = new File(effectivePath + dirName);
        // 如果对应的文件夹不存在就创建
        if (!correspondingDir.exists()) {
            boolean carouselMkdir = correspondingDir.mkdir();
            if (carouselMkdir) {
                log.info("文件夹创建成功！");
            }
        }
        // 获取当前年份
        int year = Calendar.getInstance().get(Calendar.YEAR);
        File dir = new File(effectivePath + dirName + "/" + year);
        // 判断文件夹是否存在，不存在就创建
        if (!dir.exists()) {
            boolean mkdir = dir.mkdir();
        }
        // 获取当前月份
        int month = Calendar.getInstance().get(Calendar.MONTH) + 1;
        int singleMonth = 10;
        dir = new File(effectivePath + dirName + "/" + year + "/" + month);
        if (!dir.exists()) {
            if (month < singleMonth) {
                dir = new File(effectivePath + dirName + "/" + year + "/" + 0 + month);
                boolean mkdir = dir.mkdir();
            } else {
                boolean mkdir = dir.mkdir();
            }
        }
        // 获取当前天数
        int day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        int singleDay = 10;
        if (month < singleMonth) {
            dir = new File(effectivePath + dirName + "/" + year + "/" + 0 + month + "/" + day);
            if (!dir.exists()) {
                if (day < singleDay) {
                    dir = new File(effectivePath + dirName + "/" + year + "/" + month + "/" + 0 + day);
                    boolean mkdir = dir.mkdir();
                } else {
                    boolean mkdir = dir.mkdir();
                }
            }
        } else {
            dir = new File(effectivePath + dirName + "/" + year + "/" + month + "/" + day);
            if (!dir.exists()) {
                if (day < singleDay) {
                    dir = new File(effectivePath + dirName + "/" + year + "/" + month + "/" + 0 + day);
                    boolean mkdir = dir.mkdir();
                } else {
                    boolean mkdir = dir.mkdir();
                }
            }
        }


        // 返回一个生成的路径：年/月/日/文件名
        if (month < singleMonth) {
            if (day < singleDay) {
                String path = dirName + "/" + year + "/" + 0 + month + "/" + 0 + day + "/" + newImgFileName;
                return path;
            } else {
                String path = dirName + "/" + year + "/" + 0 + month + "/" + day + "/" + newImgFileName;
                return path;
            }
        } else {
            if (day < singleDay) {
                String path = dirName + "/" + year + "/" + month + "/" + 0 + day + "/" + newImgFileName;
                return path;
            } else {
                String path = dirName + "/" + year + "/" + month + "/" + day + "/" + newImgFileName;
                return path;
            }
        }


    }

    /**
     * 保存图片到指定目录下
     *
     * @param file     file
     * @param savePath savePath
     * @return boolean
     */
    public boolean savePicture(MultipartFile file, String savePath) {
        try {
            byte[] data = new byte[1024];
            int len = 0;
            InputStream inputStream = file.getInputStream();
            OutputStream outputStream = new FileOutputStream(new File(savePath));
            while ((len = inputStream.read(data)) != -1) {
                outputStream.write(data, 0, len);
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
