/**
 * Created by spink on 2017/3/16.
 */
var msg = {
    required: "",
    remote: "",
    equalTo: ""
}
/*创建正则表达式对象*/
var regexpEvent = {
    //用户名正则表达式
    username_reg: /^([a-zA-Z][a-zA-Z0-9]*)|([0-9][0-9a-zA-Z]*)$/,
    //密码正则表达式
    password_reg: /^[a-zA-Z]+\d+[-`=\\\[\];',.\/~!@#$%^&*()_+|{}:\"<>?]+$/,
    //数字正则表达式
    number_reg: /^[0-9]*[0-9][0-9]*$/,
    //小数正则表达式
    float_reg_12: /^(0|[1-9]\d*)(\.\d{1,12})?$/,
    float_reg_4: /^(0|[1-9]\d*)(\.\d{1,4})?$/,
    float_reg_3: /^(0|[1-9]\d*)(\.\d{1,3})?$/,
    float_reg_8: /^(0|[1-9]\d*)(\.\d{1,8})?$/,
    //Email正则表达式
    email_reg: /^(\w)+(\.\w+)*@(\w)+((\.\w{2,3}){1,3})$/,
    //手机号码正则表达式
    cellphone_reg: /^(13[0-9]|14[5|7]|15[0|1|2|3|5|6|7|8|9]|17[0]|18[0|1|2|3|5|6|7|8|9])\d{8}$/,
    cell_reg: /^1\d{10}$/,
    id_card_reg: /^(\d{6})(\d{4})(\d{2})(\d{2})(\d{3})([0-9]|X|x)$/,
    //电话号码正则表达式
    //telephone_reg : /^(0\d{2,3})?-?(\d{5,9})$/,
    telephone_reg: /^(((0\d{3})?-?(\d{7,8}))|((0\d{2})?-?(\d{8})))(-\d{2,4})?$/,
    //传真正则表达式
    fax_reg: /^(0\d{2,3})?-?(\d{5,9})$/,
    //腾讯QQ号正则表达式
    qq_reg: /^[1-9][0-9]{4,}$/,
    //中国邮政编码正则表达式
    ems_reg: /^[1-9]\d{5}(?!\d)$/,
    //车牌号正则表达式
    car_reg: /^[\u4e00-\u9fa5]{1}[a-zA-Z]{1}[a-zA-Z_0-9]{5}$/,
    //普通文本验证
    /* ordinary_reg: /^([\u4e00-\u9fa5]+|[a-zA-Z0-9]+)$/,*/
    ordinary_reg: /^[\u4E00-\u9FA5A-Za-z0-9_]+$/,/**/
    //名称文本验证
    name_reg: /^[\u4E00-\u9FA5A-Za-z]+$/,
    //自定义正则表达式
    user_defined_reg: null
}

/**
 * 英文+数据验证
 */
$.validator.addMethod("engNum", function (value, element) {
    var username_reg = regexpEvent.username_reg;
    return this.optional(element) || (username_reg.test(value));
}, "只能输入英文和数字")

$.validator.addMethod("english", function (value, element) {
    return this.optional(element) || (/^[a-zA-Z]+$/.test(value));
}, function (params, element) {
    return $(element).attr('error-msg');
})

$.validator.addMethod("china2", function (value, element) {
    return this.optional(element) || (/^[a-zA-Z]+$/.test(value));
}, function (params, element) {
    return $(element).attr('不行');
})
/**
 * 普通文本验证验证
 */
$.validator.addMethod("ordinary", function (value, element) {
    var ordinary = regexpEvent.ordinary_reg;
    return this.optional(element) || (ordinary.test(value))
}, "只能输入汉字，字母，数字");
/**
 * 手机号码验证
 */
$.validator.addMethod("mobile", function (value, element) {
    var length = value.length;
    var mobile = regexpEvent.cellphone_reg;
    return this.optional(element) || (length == 11 && mobile.test(value));
}, "手机号码格式错误");
/**
 * 手机号码验证 只验证长度和首位
 */
$.validator.addMethod("mobile2", function (value, element) {
    var mobile = regexpEvent.cell_reg;
    var length = value.length;
    return this.optional(element) || (length == 11 && mobile.test(value));
}, function (params, element) {
    return $(element).attr("error-msg");
})

/**
 * 电话号码验证
 */
$.validator.addMethod("phone", function (value, element) {
    var tel = regexpEvent.telephone_reg;
    return this.optional(element) || (tel.test(value));
}, "电话号码格式错误");

/**
 * 传真验证
 */
$.validator.addMethod("fax", function (value, element) {
    var fax = regexpEvent.fax_reg;
    return this.optional(element) || (fax.test(value));
}, "传真格式错误");

/**
 * 中文验证
 */
$.validator.addMethod("china", function (value, element) {
    var re=/[^\u4e00-\u9fa5]/;
    return this.optional(element) || (!re.test(temp));
}, "请输入中文");

/**
 * 邮箱验证
*/
$.validator.addMethod("email", function (value, element) {
    var tel = regexpEvent.email_reg;
    return this.optional(element) || (tel.test(value));
}, "邮箱格式错误");

/**
 * 邮政编码验证
 */
$.validator.addMethod("zipCode", function (value, element) {
    var tel = regexpEvent.ems_reg;
    return this.optional(element) || (tel.test(value));
}, "邮政编码格式错误");

/**
 * QQ号码验证
 */
$.validator.addMethod("qq", function (value, element) {
    var tel = regexpEvent.qq_reg;
    return this.optional(element) || (tel.test(value));
}, "qq号码格式错误");

/**
 * IP地址验证
 */
$.validator.addMethod("ip", function (value, element) {
    var ip = /^(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$/;
    return this.optional(element) || (ip.test(value) && (RegExp.$1 < 256 && RegExp.$2 < 256 && RegExp.$3 < 256 && RegExp.$4 < 256));
}, "Ip地址格式错误");

/**
 * 字母和数字的验证
 */
$.validator.addMethod("chrnum", function (value, element) {
    var chrnum = /^([a-zA-Z0-9]+)$/;
    return this.optional(element) || (chrnum.test(value));
}, "只能输入数字和字母(字符A-Z, a-z, 0-9)");

/**
 * 中文的验证
 */
$.validator.addMethod("chinese", function (value, element) {
    var chinese = /^[\u4e00-\u9fa5]+$/;
    return this.optional(element) || (chinese.test(value));
}, "只能输入中文");

$.validator.addMethod("nochinese", function (value, element) {
    var chinese = /^[\u4e00-\u9fa5]+$/;

    if (/[\u4E00-\u9FA5]/i.test(value)) {
        return false;
    }else{
        return true;
    }
}, "不能输入中文");

$.validator.addMethod("money", function (value, element) {
    var exp = /(^[1-9]([0-9]+)?(\.[0-9]{1,2})?$)|(^(0){1}$)|(^[0-9]\.[0-9]([0-9])?$)/;

    if(exp.test(value)) {
        return true;
    } else {
        return false;
    }

}, "只能输入数字，小数点后只能保留两位");

$.validator.addMethod("charge", function (value, element) {
    var exp = /(^[0-9]([0-9]+)?(\.[0-9]{1})?$)/;

    if(exp.test(value)) {
        return true;
    } else {
        return false;
    }

}, "只能输入数字，小数点后只能保留一位");



$.validator.addMethod("passwordReg", function (value, element) {
    var chinese = /^\w+$/;
    return this.optional(element) || (chinese.test(value));
}, "密码输入格式错误");

/**
 * 数字验证
 */
$.validator.addMethod("number", function (value, element) {
    var number = regexpEvent.number_reg;
    return this.optional(element) || (number.test(value));
}, "只能输入数字");

/**
 * 数字验证2
 */
$.validator.addMethod("number2", function (value, element) {
    return this.optional(element) || (value == 0 || value >= 1);
}, "只能填写等于0或者大于等于1的值");

/**
 * 数字验证3
 */
$.validator.addMethod("bigZero", function (value, element) {
    return this.optional(element) || (value > 0);
}, "只能填写大于0的值");

/**
 * 百分比验证，
 */
$.validator.addMethod("perc", function (value, element) {
    return this.optional(element) || regexpEvent.number_reg.test(value) || (value < element.getAttribute("min")) || (value > element.getAttribute("max"));
}, "只能输入0-100数字");

/**
 * 小数验证,保留2位小数
 */

$.validator.addMethod("float", function (value, element) {
    var float = regexpEvent.float_reg_12;
    return this.optional(element) || (float.test(value));
}, "只能输入1-12位小数的数字");
$.validator.addMethod("float4", function (value, element) {
    var float = regexpEvent.float_reg_4;
    return this.optional(element) || (float.test(value));
}, "只能输入1-4位小数的数字");
$.validator.addMethod("float3", function (value, element) {
    var float = regexpEvent.float_reg_3;
    return this.optional(element) || (float.test(value));
}, "只能输入1-3位小数的数字");
$.validator.addMethod("float8", function (value, element) {
    var float = regexpEvent.float_reg_8;
    return this.optional(element) || (float.test(value));
}, "只能输入1-8位小数的数字");

/**
 * 下拉框验证
 */
$.validator.addMethod("selectNone", function (value, element) {
    if (value == "all" || value == null){
        return false;
    }
    return true;
}, "必须选择一项");

$.validator.addMethod("compareMax", function (value, element) {
    var valueId = element.getAttribute("value-id");
    var val = element.value;
    var valueName = $("#" + valueId).val();
    return this.optional(element) || (parseInt(val) <= parseInt(valueName));
}, "不能大于该值");

$.validator.addMethod("compareMin", function (value, element) {
    var valueId = element.getAttribute("value-id");
    var val = element.value;
    var uVal = $("#" + valueId).val();
    return this.optional(element) || (parseInt(val) >= parseInt(uVal));
}, "不能小于该值")

$.validator.addMethod("compareMin", function (value, element) {
    var valueId = element.getAttribute("value-id");
    var val = element.value;
    var uVal = $("#" + valueId).val();
    return this.optional(element) || (parseFloat(val) >= parseFloat(uVal)) || parseFloat(val) == 0;
}, "不能小于平台规定值")
/**
 * 字节长度验证
 */
$.validator.addMethod("byteRangeLength", function (value, element, param) {
    var length = value.length;
    for (var i = 0; i < value.length; i++) {
        if (value.charCodeAt(i) > 127) {
            length++;
        }
    }
    return this.optional(element) || (length >= param[0] && length <= param[1]);

}, $.validator.format("请确保输入的值在{0}-{1}个字节之间(一个中文字算2个字节)"));

/**
 * 验证身份证信息
 */
$.validator.addMethod("idCode", function (value, element) {
    var pass = IdentityCodeValid(value);
    if (!pass.pass){
        $(element).data("error-msg", pass.message);
    }
    return this.optional(element) || (pass.pass)
}, function (params, element) {
    var errorMsg = $(element).data('error-msg');
    return errorMsg == "" ? $.validator.format("请填写正确的身份证信息") : errorMsg;
})

/**
 * 验证身份证信息 只验证长度和末位是否X
 */
$.validator.addMethod("idCode2", function (value, element) {
    var idCard = regexpEvent.id_card_reg;
    return this.optional(element) || (idCard.test(value))
}, function (params, element) {
    return $(element).attr('error-msg');
})

$.validator.addMethod("isMax", function (value, element) {
    var uId = $(element).attr('u-id');
    var uVal = $('#' + uId).val();
    return this.optional(element) || (parseInt(value) <= parseInt(uVal))
}, $.validator.format("aaaaa"));

var typeOf = function (obj) {
    var _toString = Object.prototype.toString;
    var _type = {
        'undefined': 'undefined',
        'number': 'number',
        'boolean': 'boolean',
        'string': 'string',
        '[object Function]': 'function',
        '[object RegExp]': 'regexp',
        '[object Array]': 'array',
        '[object Date]': 'date',
        '[object Error]': 'error'
    }
    return _type[typeof obj] || _type[_toString.call(obj)] || (obj ? 'object' : 'null');
}
$.validator.setDefaults({
    submitHandler: function (form) {
        shade.showBg("dialog", "正在努力奔跑中...请稍后");
        form.submit();
    }
});

/*
 * Translated default messages for the jQuery validation plugin.
 * Language: CN
 * Author: Fayland Lam <fayland at gmail dot com>
 */
jQuery.extend(jQuery.validator.messages, {
    required: "必填字段",
    remote: "该字段不存在",
    email: "请输入正确格式的电子邮件",
    url: "请输入合法的网址",
    date: "请输入合法的日期",
    dateISO: "请输入合法的日期 (ISO).",
    number: "请输入合法的数字",
    digits: "只能输入整数",
    creditcard: "请输入合法的信用卡号",
    equalTo: "请再次输入相同的值",
    accept: "请输入拥有合法后缀名的字符串",
    maxlength: jQuery.format("请输入一个长度最多是 {0} 的字符串"),
    minlength: jQuery.format("请输入一个长度最少是 {0} 的字符串"),
    rangelength: jQuery.format("请输入一个长度介于 {0} 和 {1} 之间的字符串"),
    range: jQuery.format("请输入一个介于 {0} 和 {1} 之间的值"),
    max: jQuery.format("请输入一个最大为 {0} 的值"),
    min: jQuery.format("请输入一个最小为 {0} 的值")
});


/*
 根据〖中华人民共和国国家标准 GB 11643-1999〗中有关公民身份号码的规定，公民身份号码是特征组合码，由十七位数字本体码和一位数字校验码组成。排列顺序从左至右依次为：六位数字地址码，八位数字出生日期码，三位数字顺序码和一位数字校验码。
 地址码表示编码对象常住户口所在县(市、旗、区)的行政区划代码。
 出生日期码表示编码对象出生的年、月、日，其中年份用四位数字表示，年、月、日之间不用分隔符。
 顺序码表示同一地址码所标识的区域范围内，对同年、月、日出生的人员编定的顺序号。顺序码的奇数分给男性，偶数分给女性。
 校验码是根据前面十七位数字码，按照ISO 7064:1983.MOD 11-2校验码计算出来的检验码。

 出生日期计算方法。
 15位的身份证编码首先把出生年扩展为4位，简单的就是增加一个19或18,这样就包含了所有1800-1999年出生的人;
 2000年后出生的肯定都是18位的了没有这个烦恼，至于1800年前出生的,那啥那时应该还没身份证号这个东东，⊙﹏⊙b汗...
 下面是正则表达式:
 出生日期1800-2099  (18|19|20)?\d{2}(0[1-9]|1[12])(0[1-9]|[12]\d|3[01])
 身份证正则表达式 /^\d{6}(18|19|20)?\d{2}(0[1-9]|1[12])(0[1-9]|[12]\d|3[01])\d{3}(\d|X)$/i
 15位校验规则 6位地址编码+6位出生日期+3位顺序号
 18位校验规则 6位地址编码+8位出生日期+3位顺序号+1位校验位

 校验位规则     公式:∑(ai×Wi)(mod 11)……………………………………(1)
 公式(1)中：
 i----表示号码字符从由至左包括校验码在内的位置序号；
 ai----表示第i位置上的号码字符值；
 Wi----示第i位置上的加权因子，其数值依据公式Wi=2^(n-1）(mod 11)计算得出。
 i 18 17 16 15 14 13 12 11 10 9 8 7 6 5 4 3 2 1
 Wi 7 9 10 5 8 4 2 1 6 3 7 9 10 5 8 4 2 1

 */
//身份证号合法性验证
//支持15位和18位身份证号
//支持地址编码、出生日期、校验位验证
var IdentityCodeValid = function (code) {
    var city = {
        11: "北京",
        12: "天津",
        13: "河北",
        14: "山西",
        15: "内蒙古",
        21: "辽宁",
        22: "吉林",
        23: "黑龙江 ",
        31: "上海",
        32: "江苏",
        33: "浙江",
        34: "安徽",
        35: "福建",
        36: "江西",
        37: "山东",
        41: "河南",
        42: "湖北 ",
        43: "湖南",
        44: "广东",
        45: "广西",
        46: "海南",
        50: "重庆",
        51: "四川",
        52: "贵州",
        53: "云南",
        54: "西藏 ",
        61: "陕西",
        62: "甘肃",
        63: "青海",
        64: "宁夏",
        65: "新疆",
        71: "台湾",
        81: "香港",
        82: "澳门",
        91: "国外 "
    };
    var tip = "";
    var pass = true;

    ///^\d{6}(18|19|20)?\d{2}(0[1-9]|1[12])(0[1-9]|[12]\d|3[01])\d{3}(\d|X)$/
    if (!code || !/^(\d{6})(\d{4})(\d{2})(\d{2})(\d{3})([0-9]|X)$/.test(code)) {
        tip = "身份证号格式错误";
        pass = false;
    }

    else if (!city[code.substr(0, 2)]) {
        tip = "地址编码错误";
        pass = false;
    }
    else {
        //18位身份证需要验证最后一位校验位
        if (code.length == 18) {
            code = code.split('');
            //∑(ai×Wi)(mod 11)
            //加权因子
            var factor = [7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2];
            //校验位
            var parity = [1, 0, 'X', 9, 8, 7, 6, 5, 4, 3, 2];
            var sum = 0;
            var ai = 0;
            var wi = 0;
            for (var i = 0; i < 17; i++) {
                ai = code[i];
                wi = factor[i];
                sum += ai * wi;
            }
            var last = parity[sum % 11];
            if (parity[sum % 11] != code[17]) {
                tip = "校验位错误";
                pass = false;
            }
        }
    }
    // if (!pass) alert(tip);
    return {
        pass: pass,
        message: tip
    };
}