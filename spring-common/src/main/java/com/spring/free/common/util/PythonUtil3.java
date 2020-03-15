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

    public static void main(String[] args) {
        PythonUtil3.runPy("E:\\工作\\01 需求\\20191014 麦子科技\\东家嗨团\\结算代码\\结算代码", "aaa.py", "1111", "");
        //PythonUtil.runPy("E:\\工作\\05 学习资料\\python\\testPython\\test-函数-1.py", "mySum", "");
    }
}
