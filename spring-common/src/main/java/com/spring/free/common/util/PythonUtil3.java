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
    public static String runPy(String exePath, String pyPath, String file, String adminId, String param1){
        String result = "";
        try {
            System.out.println("运行python");
            System.out.println(pyPath+"/"+file);
            //测试         /usr/bin/python2.7
            //正式         /usr/maitao/Python-3.7.0/python
            String[] args = new String[] { exePath, pyPath+"/"+file, adminId, param1};
            Process proc = Runtime.getRuntime().exec(args);// 执行py文件
            System.out.println("Runtime.getRuntime().exec(args)---->end");
            BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            System.out.println("获取返回结果：");
            System.out.println(in);
            String line = null;

            while ((line = in.readLine()) != null) {
                result = line;
                System.out.println("python---->"+line);
            }
            System.out.println("line---->"+line);
            in.close();
            proc.waitFor();
        } catch (IOException e) {
            System.out.println("IOException");
            e.printStackTrace();
        } catch (InterruptedException e) {
            System.out.println("InterruptedException");
            e.printStackTrace();
        }
        return result;
    }

}
