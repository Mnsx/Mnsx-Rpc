package top.mnsx.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * @Author Mnsx_x xx1527030652@gmail.com
 */
public class ClassUtils {
    /**
     * 通过包路径获取包路径下的所有类的集合
     * @param packageName 包路径名称
     * @return 返回类集合
     */
    public static List<Class<?>> getClassesByPackage(String packageName) {
        // 转换为实际路径
        String packageDir = packageName.replace('.', '/');
        List<Class<?>> fileNames = new ArrayList<>();
        try {
            // 通过实际路径获取URL资源
            URL url = Thread.currentThread().getContextClassLoader().getResource(packageDir);
            if (url != null) {
                // 生成为File类
                File file = new File(url.toString().replace("file:/", ""));
                // 遍历该文件，如果为文件夹递归处理，如果是文件，将该文将的类转换成类加入集合
                walkFile(file, fileNames, packageName);
            } else {
                throw new RuntimeException("无法找到指定路径");
            }
            return fileNames;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


        return null;
    }

    private static void walkFile(File file, List<Class<?>> fileNames, String packageName) throws ClassNotFoundException {
        // 遍历该文件类下的所有文件类
        File[] files = file.listFiles();
        for (File f : files) {
            if (f.isDirectory()) {
                // 是文件递归
                walkFile(f, fileNames, packageName);
            } else {
                // 不是文件，那么将该类添加到类集合中
                fileNames.add(Thread.currentThread().getContextClassLoader().loadClass(handlerPath(f.getPath())));
            }
        }
    }

    private static String handlerPath(String path) {
        int index = path.indexOf("classes" + File.separator);
        // 去除开头的classes+File.separator，去除结尾的.class
        path = path.substring(index + 8, path.length() - 6);
        // 将文件路径转换为包路径
        path = path.replace(File.separator, ".");
        return path;
    }
}
