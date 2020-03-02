package com.spring.test;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class ToolJava2Impl {

    public static void main(String[] args) {
        List<String> classList = new ArrayList();
        String path = "com.spring.fee.model.";
        classList.add(path + "TableRepurchaseDetail");

        for (String s : classList) {
            run(s);
        }
    }

    public static void run(String classPath){

        StringBuffer result = new StringBuffer();

        Class clazz3 = null;
        try {
            clazz3 = Class.forName(classPath);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        //取属性
        Field[] fields = clazz3.getDeclaredFields();
        for (Field f : fields) {
            String name = f.getName();
            String upName = name.substring(0,1).toUpperCase()+name.substring(1);
            System.out.println(name+","+f.getType());
            if (!"serialVersionUID".equals(name)) {
                if (f.getType().toString().indexOf("String") > -1) {
                    result.append("if (StringUtils.isNotEmpty(bo.get"+upName+"())) {");
                }else{
                    result.append("if (null != bo.get"+upName+"()) {");
                }
                result.append("\n");
                result.append("    criteria.and"+upName+"EqualTo(bo.get"+upName+"());");
                result.append("\n");
                result.append("}");
                result.append("\n");

            }
        }
        System.out.println(result);
        //System.err.println("通过类的全限定名:"+clazz3);
    }

}
