package com.spring.free.common.util;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

/**
 * Created by Administrator on 2017/11/7 0007.
 */
public class Xml2JsonUtil {
    /**
     * 转换一个xml格式的字符串到json格式
     *
     * @param xml
     *            xml格式的字符串
     * @return 成功返回json 格式的字符串;失败反回null
     */
    public static  String xml2JSON(String xml) {
        JSONObject obj = new JSONObject();
        try {
            InputStream is = new ByteArrayInputStream(xml.getBytes("utf-8"));
            SAXBuilder sb = new SAXBuilder();
            Document doc = sb.build(is);
            Element root = doc.getRootElement();
            obj.put(root.getName(), iterateElement(root));
            return obj.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    /**
     * 转换一个xml格式的字符串到json格式
     *
     * @param xml
     *            xml格式的字符串
     * @return 成功返回json 格式的字符串;失败反回null
     */
    public static  Map xml2Map(String xml) {
        JSONObject obj = new JSONObject();
        try {
            InputStream is = new ByteArrayInputStream(xml.getBytes("utf-8"));
            SAXBuilder sb = new SAXBuilder();
            Document doc = sb.build(is);
            Element root = doc.getRootElement();
            obj.put(root.getName(), iterateElement(root));
            return iterateElement(root);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 转换一个xml格式的字符串到json格式
     *
     * @param file
     *            java.io.File实例是一个有效的xml文件
     * @return 成功反回json 格式的字符串;失败反回null
     */
    public static String xml2JSON(File file) {
        JSONObject obj = new JSONObject();
        try {
            SAXBuilder sb = new SAXBuilder();
            Document doc = sb.build(file);
            Element root = doc.getRootElement();
            obj.put(root.getName(), iterateElement(root));
            return obj.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 一个迭代方法
     *
     * @param element
     *            : org.jdom.Element
     * @return java.util.Map 实例
     */
    @SuppressWarnings("unchecked")
    private static Map  iterateElement(Element element) {
        List jiedian = element.getChildren();
        Element et = null;
        Map obj = new HashMap();
        List list = null;
        for (int i = 0; i < jiedian.size(); i++) {
            list = new LinkedList();
            et = (Element) jiedian.get(i);
            if (et.getTextTrim().equals("")) {
                if (et.getChildren().size() == 0){
                    continue;
                }
                if (obj.containsKey(et.getName())) {
                    list = (List) obj.get(et.getName());
                }
                list.add(iterateElement(et));
                obj.put(et.getName(), list);
            } else {
                if (obj.containsKey(et.getName())) {
                    list = (List) obj.get(et.getName());
                }
                list.add(et.getTextTrim());
                obj.put(et.getName(), list);
            }
        }
        return obj;
    }

    // 测试
    public static void main(String[] args) {
        System.out.println(  Xml2JsonUtil.xml2JSON("<MapSet>"
                + "<MapGroup id='Sheboygan'>" + "<Map>"
                + "<Type>MapGuideddddddd</Type>"
                + "<SingleTile>true</SingleTile>" + "<Extension>"
                + "<ResourceId>ddd</ResourceId>" + "</Extension>" + "</Map>"
                + "<Map>" + "<Type>ccc</Type>" + "<SingleTile>ggg</SingleTile>"
                + "<Extension>" + "<ResourceId>aaa</ResourceId>"
                + "</Extension>" + "</Map>" + "<Extension />" + "</MapGroup>"
                + "<ddd>" + "33333333" + "</ddd>" + "<ddd>" + "444" + "</ddd>"
                + "</MapSet>"));


        System.out.println(  Xml2JsonUtil.xml2JSON( "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                "\n" +
                "<xml>\n" +
                "  <return_code><![CDATA[SUCCESS]]></return_code>\\n\n" +
                "  <return_msg><![CDATA[OK]]></return_msg>\\n\n" +
                "  <appid><![CDATA[wx7b988ec35df700c8]]></appid>\\n\n" +
                "  <mch_id><![CDATA[1490441992]]></mch_id>\\n\n" +
                "  <nonce_str><![CDATA[29tdKwsqvTJyGsfb]]></nonce_str>\\n\n" +
                "  <sign><![CDATA[DCFA695C5D57D0580BC3A92124372FFE]]></sign>\\n\n" +
                "  <result_code><![CDATA[SUCCESS]]></result_code>\\n\n" +
                "  <openid><![CDATA[oOich0jXXNxVIL9NWH6bG3A9yV1g]]></openid>\\n\n" +
                "  <is_subscribe><![CDATA[N]]></is_subscribe>\\n\n" +
                "  <trade_type><![CDATA[APP]]></trade_type>\\n\n" +
                "  <bank_type><![CDATA[CFT]]></bank_type>\\n\n" +
                "  <total_fee>1</total_fee>\\n\n" +
                "  <fee_type><![CDATA[CNY]]></fee_type>\\n\n" +
                "  <transaction_id><![CDATA[4200000007201711062943654035]]></transaction_id>\\n\n" +
                "  <out_trade_no><![CDATA[1415659990]]></out_trade_no>\\n\n" +
                "  <attach><![CDATA[]]></attach>\\n\n" +
                "  <time_end><![CDATA[20171106230544]]></time_end>\\n\n" +
                "  <trade_state><![CDATA[SUCCESS]]></trade_state>\\n\n" +
                "  <cash_fee>1</cash_fee>\\n\n" +
                "</xml>\n"));

    }
}