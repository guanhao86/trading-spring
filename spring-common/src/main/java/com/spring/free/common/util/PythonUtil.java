package com.spring.free.common.util;

import org.python.core.PyFunction;
import org.python.core.PyObject;
import org.python.core.PyString;
import org.python.util.PythonInterpreter;

/**
 * python工具类
 */
public class PythonUtil {

    /**
     * 运行python
     * @return
     */
    public static String runPy(String pyPath, String method){
        // TODO Auto-generated method stub
        PythonInterpreter interpreter = new PythonInterpreter();
        interpreter.execfile(pyPath);

        // 第一个参数为期望获得的函数（变量）的名字，第二个参数为期望返回的对象类型
        PyFunction pyFunction = interpreter.get(method, PyFunction.class);
        int a = 5, b = 10;
        //调用函数，如果函数需要参数，在Java中必须先将参数转化为对应的“Python类型”
        PyObject pyobj = pyFunction.__call__(new PyString("888000000000"), new PyString(""));
        System.out.println("java调用python");
        System.out.println(a + "+" + b + "=" + pyobj);
        return pyobj.toString();
    }

    public static void main(String[] args) {
        PythonUtil.runPy("E:\\工作\\01 需求\\20191014 麦子科技\\东家嗨团\\结算代码\\结算代码\\dj_balance_accounts.py", "balance_gogogo");
    }
}
