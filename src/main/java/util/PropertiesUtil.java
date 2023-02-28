package util;

import com.sun.org.apache.xalan.internal.xsltc.compiler.SourceLoader;

import java.io.*;
import java.util.Properties;

public class PropertiesUtil {
    public static Properties properties = null;

    static {
        try {
            String fileName = "src/main/resources/db.properties";
            InputStream inputStream = new FileInputStream(fileName);
            BufferedReader bf = new BufferedReader(new InputStreamReader(inputStream));
            properties = new Properties();
            properties.load(bf);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Properties getProperties() {
        return properties;
    }

    public static void setProperties(Properties properties) {
        PropertiesUtil.properties = properties;
    }

    public static void main(String[] args) {
        System.out.println(PropertiesUtil.properties.getProperty("JBR_MC"));
    }
}
