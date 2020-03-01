package com.spring.free.common.util;

import java.util.*;

import org.apache.commons.lang.StringUtils;


public class WXOrderquery {
	
	private String key="SMarthzntKEji15155850410ijEKupGN";
	
	private String appid="wx3b2e0d97d7138e18";	//应用ID
	private String mch_id="1515585041";  //商户号
	private String out_trade_no;//商户订单号
	private String nonce_str;//随机字符串
	private String sign;//签名
	
	private String url;//签名使用url

	public WXOrderquery(String out_trade_no){
		this.out_trade_no = out_trade_no;
		this.nonce_str= getNonceStr();
	}
	
	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public String getMch_id() {
		return mch_id;
	}

	public void setMch_id(String mch_id) {
		this.mch_id = mch_id;
	}

	public String getOut_trade_no() {
		return out_trade_no;
	}

	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}

	public String getNonce_str() {
		return nonce_str;
	}

	public void setNonce_str(String nonce_str) {
		this.nonce_str = nonce_str;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	/**
	 * 组织xml报文
	 * @return
	 */
	public Map<String, Object> createParam(){
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		String xml = "";
		String head = "<xml>";
		String end = "</xml>";
		
		ArrayList<String> arr = new ArrayList<String>();
		
		xml += head;
		
		if(!StringUtils.isBlank(this.appid)){
			xml += "<appid>" + this.appid + "</appid>";
			arr.add("appid="+this.appid);
		}
		if(!StringUtils.isBlank(this.mch_id)){
			xml += "<mch_id>" + this.mch_id + "</mch_id>";
			arr.add("mch_id="+this.mch_id);
		}
		if(!StringUtils.isBlank(this.out_trade_no )){
			xml += "<out_trade_no>" + this.out_trade_no + "</out_trade_no>";
			arr.add("out_trade_no="+this.out_trade_no );
		}
		if(!StringUtils.isBlank(this.nonce_str)){
			xml += "<nonce_str>" + this.nonce_str + "</nonce_str>";
			arr.add("nonce_str="+this.nonce_str);
		}
		if(!StringUtils.isBlank(this.sign)){
			xml += "<sign>" + this.sign + "</sign>";
			//arr.add("sign="+this.sign);
		}
				
		xml += end;
		
		this.setUrl(createSign(arr, key));
		
		map.put("xml", xml);
		map.put("data", arr);
		map.put("signUrl", this.getUrl());
		
		return map;
	}

	/**
	 * 获得随机字符串
	 * @return
	 */
	public static String getNonceStr(){
		Random random = new Random();
		long val = random.nextLong();
		String res = MD5.getMessageDigest((val+"yzx").getBytes()).toUpperCase();
		if(32<res.length()) return res.substring(0,32);
		else return res;
	}

	/**
	 * 获取签名
	 * @param arr
	 * @return
	 */
	public static String createSign(ArrayList<String> arr, String key){
		String signUrl = "";

		Collections.sort(arr,new Comparator<String>(){
			public int compare(String o1, String o2) {
				return (o1.compareTo(o2));
			}
		});

		for(int i = 0; i < arr.size(); i++){
			if(i == 0){
				signUrl += arr.get(i);
			}else{
				signUrl += "&" + arr.get(i);
			}
		}
		signUrl += "&key="+key;

		return signUrl;
	}
	
}
