package com.spring.free.util.constraints;

import java.util.HashMap;
import java.util.Map;

/**
 * 全局配置类
 * @author Memory
 * @version 1.0
 */
public class Global {

	/**当前对象实例*/
	private static Global global = new Global();

	/**全局路径设置*/
	public static final String ADMIN_PATH = "/admin";

	/**所有ALL*/
	public static final String ALL = "all";

	public static final String URL = "url";

	/**-1*/
	public static final String NEGATIVE_ONE = "-1";

	/**分隔符 逗号*/
	public static final String SEPARATOR_COMMA = ",";
	public static final String SEPARATOR_DOT = ".";
	public static final String SEPARATOR_DOT_2 = "\\.";
	public static final String SEPARATOR_BAR = "-";
	public static final String SEPARATOR_UNDER_BAR = "_";
	public static final String SEPARATOR_COLON = ":";

	public static final String PER = "%_";
	public static final String MONEY = "￥";

	/**admin*/
	public static final String ADMIN = "admin";

	/**保存全局属性值*/
	private static Map<String, String> map = new HashMap<String, String>();

	/**操作 新增/编辑/删除/查询*/
	public static final String ADD = "add";
	public static final String ADD_VAL = "addVal";
	public static final String ADD_SUB = "addSub";
	public static final String EDIT = "edit";
	public static final String DEL = "delete";
	public static final String QUERY = "query";
	public static final String BATCH = "batch";
	public static final String VIEW = "view";

	/**显示/隐藏*/
	public static final String SHOW = "1";
	public static final String HIDE = "0";

	/**是/否*/
	public static final String YES = "1";
	public static final String NO = "0";
	
	/**对/错*/
	public static final String TRUE = "true";
	public static final String FALSE = "false";

	/**EXCEL文件类型*/
	public static final String XLS = "xls";
	public static final String XLSX = "xlsx";

	public static final String UNKNOWN = "unknown";

	/**获取当前对象实例*/
	public static Global getInstance() {
		return global;
	}
	/**数字 1 到 10*/
	public static final String STR_NUMBER_ZERO = "0";
	public static final String STR_NUMBER_ONE = "1";
	public static final String STR_NUMBER_TWO = "2";
	public static final String STR_NUMBER_THREE = "3";
	public static final String STR_NUMBER_FOUR = "4";
	public static final String STR_NUMBER_FIVE = "5";
	public static final String STR_NUMBER_SIX = "6";
	public static final String STR_NUMBER_SEVEN = "7";
	public static final String STR_NUMBER_EIGHT = "8";
	public static final String STR_NUMBER_NINE = "9";
	public static final String STR_NUMBER_TEN = "10";
	public static final String STR_NUMBER_ELEVEN = "11";
	public static final String STR_FOUR_HUNDRED = "400";
	public static final String STR_THREE_HUNDRED_SIXITY_FIVE = "365";
	public static final String STR_THREE_HUNDRED_SIXITY_SIX = "366";

	public static final int NUMBER_ZERO = 0;
	public static final int NUMBER_ONE = 1;
	public static final int NUMBER_TWO = 2;
	public static final int NUMBER_THREE = 3;
	public static final int NUMBER_FOUR = 4;
	public static final int NUMBER_FIVE = 5;
	public static final int NUMBER_SIX = 6;
	public static final int NUMBER_SEVEN = 7;
	public static final int NUMBER_EIGHT = 8;
	public static final int NUMBER_NINE = 9;
	public static final int NUMBER_TEN = 10;
	public static final int NUMBER_ELEVEN = 11;
	public static final int FOUR_HUNDRED = 400;
	public static final int THREE_HUNDRED_SIXITY_FIVE = 365;
	public static final int THREE_HUNDRED_SIXITY_SIX = 366;

	public static final String MONDAY = "周一";
	public static final String TUESDAY = "周二";
	public static final String WEDNESDAY = "周三";
	public static final String THURSDAY = "周四";
	public static final String FRIDAY = "周五";
	public static final String SATURDAY = "周六";
	public static final String SUNDAY = "周日";

	public static final String START = "start";
	public static final String END = "end";
	public static final String START_END = "start_end";

	/**
	 *
	 */
	public static final String EXPORT_ALL = "export_all",
			EXPORT_CURR = "export_curr",
			EXPORT_CHECK = "export_check",
			EXPORT_RANGE = "export_range",
			EXPORT_SEARCH = "export_search";

	/**
	 *
	 */
	public static final int MAX_EXPORT = 10000;
}
