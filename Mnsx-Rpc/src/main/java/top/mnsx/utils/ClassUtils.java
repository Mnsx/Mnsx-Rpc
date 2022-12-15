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
    public static List<Class<?>> getClassesByPackage(String packageName) {
        String packageDir = packageName.replace('.', '/');
        List<Class<?>> fileNames = new ArrayList<>();
        try {
            URL url = Thread.currentThread().getContextClassLoader().getResource(packageDir);
            if (url != null) {
                File file = new File(url.toString().replace("file:/", ""));
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
        File[] files = file.listFiles();
        for (File f : files) {
            if (f.isDirectory()) {
                walkFile(f, fileNames, packageName);
            } else {
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
