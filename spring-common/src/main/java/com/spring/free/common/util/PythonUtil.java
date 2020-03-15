package com.spring.free.common.util;

import org.python.core.*;
import org.python.util.PythonInterpreter;
import java.util.Properties;

/**
 * python工具类
 */
public class PythonUtil {

    /**
     * 运行python
     * @return
     */
    public static String runPy(String pyPath, String file, String method, String adminId){
        // TODO Auto-generated method stub

        //System.setProperty("python.home", "D:\\Program Files (x86)\\Python\\Python37-32\\");





        Properties props = new Properties();
        props.put("python.home", "path to the Lib folder");
        props.put("python.console.encoding", "UTF-8");

        props.put("python.security.respectJavaAccessibility", "false");

        props.put("python.import.site", "false");

        Properties preprops = System.getProperties();

        PySystemState sys = Py.getSystemState();
        sys.path.add(pyPath);

        PythonInterpreter.initialize(preprops, props, new String[0]);
        PythonInterpreter interpreter = new PythonInterpreter();

        interpreter.exec("import config_reader");
        interpreter.exec("import dj_db_config");
        interpreter.exec("import dj_db_writer");
        interpreter.exec("import pymysql");

        interpreter.execfile(pyPath+"/"+file);

        // 第一个参数为期望获得的函数（变量）的名字，第二个参数为期望返回的对象类型
        PyFunction pyFunction = interpreter.get(method, PyFunction.class);
        //调用函数，如果函数需要参数，在Java中必须先将参数转化为对应的“Python类型”
        PyObject pyobj = pyFunction.__call__(new PyString(adminId), new PyString(""));
        System.out.println("java调用python返回结果"+pyobj.toString());
        return pyobj.toString();
    }

    public static void main(String[] args) {
        PythonUtil.runPy("E:\\工作\\01 需求\\20191014 麦子科技\\东家嗨团\\结算代码\\结算代码", "dj_balance_accounts.py", "balance_gogogo", "ggh");

        //PythonUtil.runPy("E:\\工作\\05 学习资料\\python\\testPython\\test-函数-1.py", "mySum", "");
    }
}
