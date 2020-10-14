package com.daemonw.deviceinfo.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * Created by daemonw on 16-7-5.
 */
public class FileUtil {
    private static final String LOG_TAG = FileUtil.class.getSimpleName();

    /******
     * 从绝对路径中获取文件名
     *
     * @param filePath 文件路径
     * @return 文件名
     */
    public static String getName(String filePath) {
        return new File(filePath).getName();
    }


    /********
     * 以byte数组的形式读取文件内容
     *
     * @param filepath 文件路径
     * @return 文件内容
     */
    public static byte[] readAll(String filepath) {
        byte[] data = null;
        try {
            FileInputStream fin = new FileInputStream(filepath);
            data = IOUtil.readAll(fin);
        } catch (Exception e) {
            e.printStackTrace();
            data = null;
        }
        return data;
    }


    /*********
     * 以字符串形式读取文件内容
     *
     * @param filepath 文件路径
     * @param charName 字符串编码
     * @return 文件内容, 字符串形式
     */
    public static String readString(String filepath, String charName) {
        byte[] data = readAll(filepath);
        String content = null;
        try {
            content = new String(data, charName);
        } catch (Exception e) {
            e.printStackTrace();
            content = null;
        }
        return content;
    }

    /*********
     * 将byte数据写入文件
     *
     * @param data     待写入内容
     * @param filepath 文件路径, 若文件不存在,则创建一个新文件
     */
    public static void writeBytes(byte[] data, String filepath) {
        try {
            File file = new File(filepath);
            if (!file.exists()) {
                file.createNewFile();
            }
            IOUtil.save(data, new FileOutputStream(file));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void clearFolder(String folderPath, int eraseCount) {
        File file = new File(folderPath);
        if (file.exists()) {
            File[] subFiles = file.listFiles();
            if (subFiles != null && subFiles.length > 0) {
                for (File f : subFiles) {
                    f.delete();
                }
            }
        }
    }

}