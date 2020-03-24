package com.spring.free.common.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * python工具类
 */
public class PythonUtil3 {

    /**
     * 运行python
     * @return
     */
    public static String runPy(String pyPath, String file, String adminId, String param1){
        String result = "";
        try {
            System.out.println("运行python");
            System.out.println(pyPath+"/"+file);
            String[] args = new String[] { "/usr/maitao/Python-3.7.0/python", pyPath+"/"+file, adminId};
            Process proc = Runtime.getRuntime().exec(args);// 执行py文件

            BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            String line = null;

            while ((line = in.readLine()) != null) {
                result = line;
                System.out.println("python---->"+line);
            }
            in.close();
            proc.waitFor();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return result;
    }

}
