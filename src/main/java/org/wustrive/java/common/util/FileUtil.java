package org.wustrive.java.common.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;


import org.apache.commons.lang.StringUtils;
import org.wustrive.java.common.util.StringUtil;


/**
 * Description: 文件操作工具类
 *
 * @author: wubaoguo
 * @email: wustrive2008@gmail.com
 * @date: 2019/1/5 15:52
 * @Copyright: 2017-2018 dgztc Inc. All rights reserved.
 */
public class FileUtil {

    /**
     * 删除指定文件
     *
     * @param file
     * @return void
     * @Title deleteFile
     * @author zhaoqt
     * @date Jul 27, 2015 11:08:02 AM
     */
    public static void deleteFile(File file) {
        file.delete();
    }

    /**
     * 删除指定文件 根据path
     *
     * @param path
     */
    public static void deleteFile(String path) {
        File file = new File(path);
        file.delete();
    }

    /**
     * 获取文件扩展名
     *
     * @param path
     * @return String
     * @Title getFileExtendName
     * @author zhaoqt
     * @date Jul 27, 2015 11:07:52 AM
     */
    public static String getFileExtendName(String path) {
        return StringUtils.right(path, path.length() - path.lastIndexOf(".") - 1);
    }


    /**
     * 获取文件前缀名称，不包含扩展名
     *
     * @param path
     * @return
     */
    public static String getFilePrefixName(String path) {
        return StringUtils.left(path, path.lastIndexOf("."));
    }

    /**
     * 重命名文件
     *
     * @param oldName
     * @param newName
     * @return String
     * @Title rename
     * @author zhaoqt
     * @date Jul 27, 2015 11:08:27 AM
     */
    public static String rename(String oldName, String newName) {
        String extendName = getFileExtendName(oldName);
        if (StringUtils.isNotBlank(extendName)) {
            return newName + "." + extendName;
        }
        return newName;
    }

    /**
     * 带有路径的文件重命名
     *
     * @param filePath 文件路径
     * @param newName  新文件名称，不需要扩展名
     * @return String
     * @Title renameWithPath
     * @author zhaoqt
     * @date Jul 27, 2015 11:08:44 AM
     */
    public static String renameWithPath(String filePath, String newName) {
        String extendName = getFileExtendName(filePath);
        if (StringUtils.isNotBlank(extendName)) {
            return getFileDirectory(filePath) + File.separator + newName + "." + extendName;
        }
        return getFileDirectory(filePath) + File.separator + newName;
    }

    public static String getFileDirectory(String path) {
        return new File(path).getParent();
    }

    /**
     * 删除目录并删除该目录下所以文件
     *
     * @param dir
     * @return boolean
     * @Title deleteDirectory
     * @author zhaoqt
     * @date Jul 27, 2015 11:09:33 AM
     */
    public static boolean deleteDirectory(File dir) {
        if ((dir == null) || !dir.isDirectory()) {
            throw new RuntimeException("要删除的目录不存在，或者不是目录");
        }
        File[] files = dir.listFiles();
        int sz = files.length;

        for (int i = 0; i < sz; i++) {
            if (files[i].isDirectory()) {
                if (!deleteDirectory(files[i])) {
                    return false;
                }
            } else {
                if (!files[i].delete()) {
                    return false;
                }
            }
        }
        if (!dir.delete()) {
            return false;
        }
        return true;
    }

    /**
     * 文件 copy
     *
     * @param src
     * @param target
     * @return void
     * @throws IOException
     * @Title copyFile
     * @author zhaoqt
     * @date Jul 27, 2015 11:10:17 AM
     */
    public static void copyFile(String src, String target) throws IOException {
        FileInputStream in = new FileInputStream(src);
        File file = new File(target);
        if (!file.exists()) {
            file.getParentFile().mkdirs();
        }
        FileOutputStream out = new FileOutputStream(file);
        int c;
        byte buffer[] = new byte[1024];
        while ((c = in.read(buffer)) != -1) {
            for (int i = 0; i < c; i++)
                out.write(buffer[i]);
        }
        in.close();
        out.close();
    }

    /**
     * 功能描述：读取文件内容
     *
     * @param file
     * @param encoding html文件使用gbk编码，此处使用utf-8编码，不会出现乱码
     * @return String
     * @Title file2String
     * @author zhaoqt
     * @date Jul 27, 2015 11:11:19 AM
     */
    public static String file2String(File file, String encoding) {
        InputStreamReader reader = null;
        StringWriter writer = new StringWriter();
        try {
            if (encoding == null || "".equals(encoding.trim())) {
                reader = new InputStreamReader(new FileInputStream(file), encoding);
            } else {
                reader = new InputStreamReader(new FileInputStream(file));
            }
            // 将输入流写入输出流
            char[] buffer = new char[1024];
            int n = 0;
            while (-1 != (n = reader.read(buffer))) {
                writer.write(buffer, 0, n);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (reader != null)
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
        return writer.toString();
    }

    /**
     * touch 文件
     *
     * @param rootUrl
     * @param fileName
     * @param dirs
     * @return
     */
    public static File touchFile(String rootUrl, String fileName,
                                 String... dirs) {
        if (StringUtil.isNotBlank(dirs)) {
            File rootFolder = new File(rootUrl);
            return new File(mkdir(rootFolder, 0, dirs), fileName);
        }
        return null;
    }

    /**
     * 迭代创建 文件路径 并返回 最终路径 file
     *
     * @param folder
     * @param index
     * @param dirs
     * @return
     */
    public static File mkdir(File folder, int index, String... dirs) {
        index = index < 0 ? 0 : index;
        if (index < dirs.length) {
            File secondFolder = new File(folder, dirs[index]);
            if (!secondFolder.exists()) {
                secondFolder.mkdir();
            }
            index++;
            return mkdir(secondFolder, index, dirs);
        }
        return folder;
    }

    /**
     * 创建目录
     *
     * @param descDirName 目录名,包含路径
     * @return 如果创建成功，则返回true，否则返回false
     */
    public static boolean createDirectory(String descDirName) {
        String descDirNames = descDirName;
        if (!descDirNames.endsWith(File.separator)) {
            descDirNames = descDirNames + File.separator;
        }
        File descDir = new File(descDirNames);
        if (descDir.exists()) {
            return false;
        }
        // 创建目录
        if (descDir.mkdirs()) {
            return true;
        } else {
            return false;
        }

    }

    /**
     * 读取 tst file
     *
     * @param txtFile
     * @param encoding
     */
    public static String readTxtFile(File txtFile, String encoding) {
        StringBuffer buffer = new StringBuffer();
        try {
            if (txtFile.isFile() && txtFile.exists()) { //判断文件是否存在
                InputStreamReader read = new InputStreamReader(
                        new FileInputStream(txtFile), encoding);//考虑到编码格式
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt = null;

                while ((lineTxt = bufferedReader.readLine()) != null) {
                    buffer.append(lineTxt);
                }
                read.close();

                return buffer.toString();
            } else {
                System.out.println("找不到指定的文件");
            }
        } catch (Exception e) {
            System.out.println("读取文件内容出错");
            e.printStackTrace();
        }
        return buffer.toString();
    }
}
