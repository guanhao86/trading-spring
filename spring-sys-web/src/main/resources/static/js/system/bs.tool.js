/**
 * Created by Memory on 2016/9/29.
 */
/**
 * 格式化日期
 * @param format 格式 如：yyyy-MM-dd
 * @returns {*}
 */
Date.prototype.format = function (format) {
    var o = {
        "M+": this.getMonth() + 1, //month
        "d+": this.getDate(),    //day
        "h+": this.getHours(),   //hour
        "m+": this.getMinutes(), //minute
        "s+": this.getSeconds(), //second
        "q+": Math.floor((this.getMonth() + 3) / 3),  //quarter
        "S": this.getMilliseconds() //millisecond
    }
    if (/(y+)/.test(format))
        format = format.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
        if (new RegExp("(" + k + ")").test(format))
            format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k] : ("00" + o[k]).substr(("" + o[k]).length));
    return format;
}

var DATE_CONS = {
    DATE_COMPARE_BIG: 1,
    DATE_COMPARE_SMALL: 2,
    DATE_COMPARE_EQUAL: 3,
    DATE_COMPARE_BIG_EQUAL: 4,
    DATE_COMPARE_SMALL_EQUAL: 5,
}

Date.prototype.compare = function (date, type) {
    var startDate = new Date(date).getTime();
    var ret = false;
    switch (type) {
        case DATE_CONS.DATE_COMPARE_BIG:
            ret = this.getTime() > startDate;
            break;
        case DATE_CONS.DATE_COMPARE_SMALL:
            ret = this.getTime() < startDate;
            break;
        case DATE_CONS.DATE_COMPARE_SMALL_EQUAL:
            ret = this.getTime() <= startDate;
            break;
        case DATE_CONS.DATE_COMPARE_BIG_EQUAL:
            ret = this.getTime() >= startDate;
            break;
        default:
            ret = this.getTime() == startDate;
    }
    return ret;
}

Date.prototype.getDateDiff = function (date) {
    var startTime = this.getTime();
    var endTime = new Date(Date.parse(date.replace(/-/g, "/"))).getTime();
    var dates = Math.abs((startTime - endTime)) / (1000 * 60 * 60 * 24);
    return dates;
}

Array.prototype.removeByValue = function (val) {
    for (var i = 0; i < this.length; i++) {
        if (this[i] == val) {
            this.splice(i, 1);
            break;
        }
    }
}
Array.prototype.unique = function () {
    return unique(this);
    /*// 遍历arr，把元素分别放入tmp数组(不存在才放)
     var tmp = new Array();
     for(var i in this){
     //该元素在tmp内部不存在才允许追加
     if(tmp.indexOf(this[i])==-1){
     tmp.push(this[i]);
     }
     }
     return tmp;*/
}
Array.prototype.contains = function (val) {
    var i = this.length;
    while (i--) {
        if (this[i] === val) {
            return true;
        }
    }
    return false;
}

Array.prototype.inArray = function (e) {
    var r = new RegExp(',' + e + ',');
    return (r.test(',' + this.join(this.S) + ','));
}


/**
 * 显示*号
 * @param fromLen 星号开始位置
 * @param endLen 星号结束位置
 * @returns {*}
 */
String.prototype.plusStar = function (fromLen, endLen) {
    var len = this.toString().length - fromLen - endLen;
    var star = '';
    for (var i = 0; i < len; i++) {
        star += '*';
    }
    return this.substr(0, fromLen) + star + this.substr(this.length - endLen);
}

function dateFormat(format, timestamp) {
    var jsdate = ((timestamp) ? new Date(timestamp) : new Date())
    return jsdate.format(format);
}

function getDateStr(AddDayCount) {
    var dd = new Date();
    dd.setDate(dd.getDate() + AddDayCount);//获取AddDayCount天后的日期
    return dd;
}

function unique(arr) {
    var result = [], hash = {};
    for (var i = 0, elem; (elem = arr[i]) != null; i++) {
        if (!hash[elem]) {
            result.push(elem);
            hash[elem] = true;
        }
    }
    return result;
}

var browser = {
    versions: function () {
        var u = navigator.userAgent, app = navigator.appVersion;
        var browserName = navigator.userAgent.toLowerCase();
        return {
            //IE内核
            webKit: u.indexOf('AppleWebKit') > -1,//苹果 谷歌内核
            mac: u.indexOf('Macintosh') > -1,//mac
            gecko: u.indexOf('Gecko') > -1 && u.indexOf('KHTML') === -1,//火狐内核
            //是否为移动终端
            mobile: !!u.match(/AppleWebKit.*Moblie*.*/),
            ios: !!u.match(/\(i[^;]+;( u;)? CPU.+Mac OS X/),//ios终端
            android: u.indexOf('Android') > -1 || u.indexOf('Linux') > -1,//android终端或者uc浏览器
            phone: u.indexOf('Phone') > -1,//是否为iPhone或者QQHD浏览器
            pad: u.indexOf('Pad') > -1,//是否iPad
            webApp: u.indexOf('Safari') === -1,//是否web应用程序，没有头部与底部
            windows: u.indexOf('Windows') > -1,//是否windows
        };
    }(),
    language: (navigator.browserLanguage || navigator.language).toLowerCase(),
    ua: navigator.userAgent.toLowerCase()
}


//除法函数，用来得到精确的除法结果
//说明：JavaScript的除法结果会有误差，在两个浮点数相除的时候会比较明显。这个函数返回较为精确的除法结果。
//调用：accDiv(arg1,arg2)
//返回值：arg1除以arg2的精确结果
function accDiv(arg1, arg2) {
    var t1 = 0, t2 = 0, r1, r2;
    try {
        t1 = arg1.toString().split(".")[1].length
    } catch (e) {
    }
    try {
        t2 = arg2.toString().split(".")[1].length
    } catch (e) {
    }
    with (Math) {
        r1 = Number(arg1.toString().replace(".", ""))
        r2 = Number(arg2.toString().replace(".", ""))
        return (r1 / r2) * pow(10, t2 - t1);
    }
}

//给Number类型增加一个div方法，调用起来更加方便。
Number.prototype.div = function (arg) {
    return accDiv(this, arg);
}

//乘法函数，用来得到精确的乘法结果
//说明：javascript的乘法结果会有误差，在两个浮点数相乘的时候会比较明显。这个函数返回较为精确的乘法结果。
//调用：accMul(arg1,arg2)
//返回值：arg1乘以arg2的精确结果
function accMul(arg1, arg2) {
    var m = 0, s1 = arg1.toString(), s2 = arg2.toString();
    try {
        m += s1.split(".")[1].length
    } catch (e) {
    }
    try {
        m += s2.split(".")[1].length
    } catch (e) {
    }
    return Number(s1.replace(".", "")) * Number(s2.replace(".", "")) / Math.pow(10, m)
}

//给Number类型增加一个mul方法，调用起来更加方便。
Number.prototype.mul = function (arg) {
    return accMul(arg, this);
}

//加法函数，用来得到精确的加法结果
//说明：javascript的加法结果会有误差，在两个浮点数相加的时候会比较明显。这个函数返回较为精确的加法结果。
//调用：accAdd(arg1,arg2)
//返回值：arg1加上arg2的精确结果
function accAdd(arg1, arg2) {
    var r1, r2, m;
    try {
        r1 = arg1.toString().split(".")[1].length
    } catch (e) {
        r1 = 0
    }
    try {
        r2 = arg2.toString().split(".")[1].length
    } catch (e) {
        r2 = 0
    }
    m = Math.pow(10, Math.max(r1, r2))
    return (arg1 * m + arg2 * m) / m
}

//给Number类型增加一个add方法，调用起来更加方便。
Number.prototype.add = function (arg) {
    return accAdd(arg, this);
}

//减法函数
function accSub(arg1, arg2) {
    var r1, r2, m, n;
    try {
        r1 = arg1.toString().split(".")[1].length
    } catch (e) {
        r1 = 0
    }
    try {
        r2 = arg2.toString().split(".")[1].length
    } catch (e) {
        r2 = 0
    }
    m = Math.pow(10, Math.max(r1, r2));
//last modify by deeka
//动态控制精度长度
    n = (r1 >= r2) ? r1 : r2;
    return ((arg1 * m - arg2 * m) / m).toFixed(n);
}
Number.prototype.sub = function (arg) {
    return accSub(arg, this);
}