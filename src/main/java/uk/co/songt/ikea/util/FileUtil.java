package uk.co.songt.ikea.util;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class FileUtil {

    private static Logger LOG = LoggerFactory.getLogger(FileUtil.class);

    public static InputStream getFileFromClasspath(Class currentClass, String fileName) {
        ClassLoader classLoader = currentClass.getClassLoader();
        return classLoader.getResourceAsStream(fileName);
    }

    public static String getStringFromClasspath(Class currentClass, String fileName){

        InputStream inputStream = getFileFromClasspath(currentClass, fileName);
        try {
            return IOUtils.toString(inputStream,"UTF-8");
        } catch (IOException e) {
            LOG.error(e.getMessage());
        }
        return "";
    }

    public static String getStringFromPath(Class currentClass ,String path){
        return FileUtil.getStringFromClasspath(currentClass, path );
    }


    public static String getStringFromFile(String filePath) {

        try {
            FileInputStream fisTargetFile = new FileInputStream(new File("test.txt"));
            return  IOUtils.toString(fisTargetFile, "UTF-8");

        } catch (IOException e) {
            LOG.error(e.getMessage());
        }
        return "";

    }


}
