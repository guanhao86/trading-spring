package com.spring.free.common.util;

import org.apache.commons.lang.StringUtils;

import java.util.*;

public class WXUnifiedorder {
	
	private String key="SMarthzntKEji15155850410ijEKupGN";
	
	private String appid="wx3b2e0d97d7138e18";	//应用ID
	private String mch_id="1515585041";  //商户号
	private String device_info;//设备号
	private String nonce_str;//随机字符串
	private String sign;//签名
	private String sign_type;//签名类型
	private String body;//商品描述
	private String detail;//商品详情
	private String attach;//附加数据
	private String out_trade_no;//商户订单号
	private String fee_type;//货币类型
	private String total_fee;//总金额
	private String spbill_create_ip;//终端IP
	private String time_start;//交易起始时间
	private String time_expire;//交易结束时间
	private String goods_tag;//订单优惠标记
	private String notify_url;//通知地址
	private String trade_type;//交易类型
	private String limit_pay;//指定支付方式
	private String scene_info;//场景信息
	
	private String product_id;//商品ID(扫码)
	private String openid;//用户标识(扫码)
	
	private String url;//签名使用url
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public WXUnifiedorder(String body, String out_trade_no, String total_fee, String spbill_create_ip
			, String notify_url, String trade_type){


			key="HENGpuKEji1529956171ijEKupGNEH00";

			appid="wxf85a031b9b102e68";

			mch_id="1529956171";

		this.nonce_str = getNonceStr();
		this.body = body;
		this.out_trade_no = out_trade_no;
		this.total_fee = total_fee;
		this.spbill_create_ip = spbill_create_ip;
		this.notify_url = notify_url;
		this.trade_type = trade_type;
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
	public String getDevice_info() {
		return device_info;
	}
	public void setDevice_info(String device_info) {
		this.device_info = device_info;
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
	public String getSign_type() {
		return sign_type;
	}
	public void setSign_type(String sign_type) {
		this.sign_type = sign_type;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	public String getAttach() {
		return attach;
	}
	public void setAttach(String attach) {
		this.attach = attach;
	}
	public String getOut_trade_no() {
		return out_trade_no;
	}
	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}
	public String getFee_type() {
		return fee_type;
	}
	public void setFee_type(String fee_type) {
		this.fee_type = fee_type;
	}
	public String getTotal_fee() {
		return total_fee;
	}
	public void setTotal_fee(String total_fee) {
		this.total_fee = total_fee;
	}
	public String getSpbill_create_ip() {
		return spbill_create_ip;
	}
	public void setSpbill_create_ip(String spbill_create_ip) {
		this.spbill_create_ip = spbill_create_ip;
	}
	public String getTime_start() {
		return time_start;
	}
	public void setTime_start(String time_start) {
		this.time_start = time_start;
	}
	public String getTime_expire() {
		return time_expire;
	}
	public void setTime_expire(String time_expire) {
		this.time_expire = time_expire;
	}
	public String getGoods_tag() {
		return goods_tag;
	}
	public void setGoods_tag(String goods_tag) {
		this.goods_tag = goods_tag;
	}
	public String getNotify_url() {
		return notify_url;
	}
	public void setNotify_url(String notify_url) {
		this.notify_url = notify_url;
	}
	public String getTrade_type() {
		return trade_type;
	}
	public void setTrade_type(String trade_type) {
		this.trade_type = trade_type;
	}
	public String getLimit_pay() {
		return limit_pay;
	}
	public void setLimit_pay(String limit_pay) {
		this.limit_pay = limit_pay;
	}
	public String getScene_info() {
		return scene_info;
	}
	public void setScene_info(String scene_info) {
		this.scene_info = scene_info;
	}
	
	public String getProduct_id() {
		return product_id;
	}

	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
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
		if(!StringUtils.isBlank(this.device_info)){
			xml += "<device_info>" + this.device_info + "</device_info>";
			arr.add("device_info="+this.device_info);
		}
		if(!StringUtils.isBlank(this.nonce_str)){
			xml += "<nonce_str>" + this.nonce_str + "</nonce_str>";
			arr.add("nonce_str="+this.nonce_str);
		}
		if(!StringUtils.isBlank(this.sign)){
			xml += "<sign>" + this.sign + "</sign>";
			//arr.add("sign="+this.sign);
		}
		if(!StringUtils.isBlank(this.sign_type)){
			xml += "<sign_type>" + this.sign_type + "</sign_type>";
			arr.add("sign_type="+this.sign_type);
		}
		if(!StringUtils.isBlank(this.body)){
			xml += "<body>" + this.body + "</body>";
			arr.add("body="+this.body);
		}
		if(!StringUtils.isBlank(this.detail)){
			xml += "<detail>" + this.detail + "</detail>";
			arr.add("detail="+this.detail);
		}
		if(!StringUtils.isBlank(this.attach)){
			xml += "<attach>" + this.attach + "</attach>";
			arr.add("attach="+this.attach);
		}
		if(!StringUtils.isBlank(this.out_trade_no)){
			xml += "<out_trade_no>" + this.out_trade_no + "</out_trade_no>";
			arr.add("out_trade_no="+this.out_trade_no);
		}
		if(!StringUtils.isBlank(this.fee_type)){
			xml += "<fee_type>" + this.fee_type + "</fee_type>";
			arr.add("fee_type="+this.fee_type);
		}
		if(!StringUtils.isBlank(this.total_fee)){
			xml += "<total_fee>" + this.total_fee + "</total_fee>";
			arr.add("total_fee="+this.total_fee);
		}
		if(!StringUtils.isBlank(this.spbill_create_ip)){
			xml += "<spbill_create_ip>" + this.spbill_create_ip + "</spbill_create_ip>";
			arr.add("spbill_create_ip="+this.spbill_create_ip);
		}
		if(!StringUtils.isBlank(this.time_start)){
			xml += "<time_start>" + this.time_start + "</time_start>";
			arr.add("time_start="+this.time_start);
		}
		if(!StringUtils.isBlank(this.time_expire)){
			xml += "<time_expire>" + this.time_expire + "</time_expire>";
			arr.add("time_expire="+this.time_expire);
		}
		if(!StringUtils.isBlank(this.goods_tag)){
			xml += "<goods_tag>" + this.goods_tag + "</goods_tag>";
			arr.add("goods_tag="+this.goods_tag);
		}
		if(!StringUtils.isBlank(this.notify_url)){
			xml += "<notify_url>" + this.notify_url + "</notify_url>";
			arr.add("notify_url="+this.notify_url);
		}
		if(!StringUtils.isBlank(this.trade_type)){
			xml += "<trade_type>" + this.trade_type + "</trade_type>";
			arr.add("trade_type="+this.trade_type);
		}
		if(!StringUtils.isBlank(this.limit_pay)){
			xml += "<limit_pay>" + this.limit_pay + "</limit_pay>";
			arr.add("limit_pay="+this.limit_pay);
		}
		if(!StringUtils.isBlank(this.scene_info)){
			xml += "<scene_info>" + this.scene_info + "</scene_info>";
			arr.add("scene_info="+this.scene_info);
		}
		
		xml += end;
		
		this.setUrl(createSign(arr, key));
		
		map.put("xml", xml);
		map.put("data", arr);
		map.put("signUrl", this.getUrl());
		
		//System.out.println(this.getUrl());
		
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
