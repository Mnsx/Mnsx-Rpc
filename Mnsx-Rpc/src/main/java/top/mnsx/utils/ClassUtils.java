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
            Enumeration<URL> resource = Thread.currentThread().getContextClassLoader().getResources(packageDir);
            while (resource.hasMoreElements()) {
                URL url = resource.nextElement();
                String protocol = url.getProtocol();
                if ("file".equals(protocol)) {
                    String path = URLDecoder.decode(url.getFile(), "UTF-8");
                    File file = new File(path);
                    walkFile(file, fileNames, packageName);
                }
            }
            return fileNames;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }


        return null;
    }

    // TODO: 2022/12/2  可以多级获取类
    private static void walkFile(File file, List<Class<?>> fileNames, String packageName) throws ClassNotFoundException {
        File[] files = file.listFiles();
        for (File f : files) {
            if (f.isDirectory()) {
                walkFile(f, fileNames, packageName);
            } else {
                fileNames.add(Thread.currentThread().getContextClassLoader().loadClass(packageName + "." + f.getName().substring(0, f.getName().length() - 6)));
            }
        }
    }
}
