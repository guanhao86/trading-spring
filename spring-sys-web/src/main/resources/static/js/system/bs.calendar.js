var calendar = {
    nowDay: function () {
        return new Date();
    },
    year: parseInt(new Date().getFullYear()),
    nowMonth: parseInt(new Date().getMonth()),
    month: ["01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"],
    week: {mon: "一", tue: "二", wed: "三", thu: "四", fri: "五", sat: "六", sun: "日"},
    dayobj: "",
    yearObj: "",
    chose: function (ele) {
        if (ele.id == "setYear") {
            this.year = ele.value;
        }
        var html = this.makeDays(this.year);
        $("#calendar_div").html(html);
    },
    makeDays: function (year, list) {
        var calHTML = '';
        var months = new Array();
        for (var y = 0; y < list.length; y++) {
            months.push(list[y].tradeMonth);
        }
        months = months.unique();
        for (var i = 0; i < this.month.length; i++) {
            var mon = months.contains(this.month[i]);
            var m = parseInt(this.month[i]) - 1;
            if (mon) {
                calHTML += this.setMonTable(m, mon, year, i, list);
            } else {
                calHTML += this.setMonTable(m, mon, year, i, list);
            }

        }
        return calHTML;
    },
    setMonTable: function (m, b, year, i, list) {
        var perm = $("#shiro_permission").val();
        var allowDays = new Array();
        var notAllowDays = new Array();
        for (var y = 0; y < list.length; y++) {
            if (this.month[i] == list[y].tradeMonth && list[y].tradeSts == "false") {
                notAllowDays.push(list[y].tradeDay);
            } else if (this.month[i] == list[y].tradeMonth && list[y].tradeSts == "true") {
                allowDays.push(list[y].tradeDay);
            }
        }
        allowDays = allowDays.unique();
        notAllowDays = notAllowDays.unique();
        var calHTML = '';
        var firstDay = new Date(year, m, 1);
        var firstDayBefore = parseInt(firstDay.getDay());//获得每月的前面空余的天数
        var lastWeek = this.getLastDateWeek(firstDay);
        calHTML += '<div class="col-md-3" style="height: 370px">';
        calHTML += '<table id="calendar_table" class="table" style="border: 0px">';
        calHTML += '<caption month="' + this.month[i] + '">' + year + '年' + this.month[i] + '月</caption><thead>';
        calHTML += '<tr><th style="color: red;background-color:rgba(247,247,247,0.5)">' + this.week.sun + '</th><th>' + this.week.mon + '</th><th>' + this.week.tue + '</th>' +
            '<th>' + this.week.wed + '</th><th>' + this.week.thu + '</th><th>' + this.week.fri + '</th><th style="color: red;background-color:rgba(247,247,247,0.5)">'
            + this.week.sat + '</th></tr></thead>';
        calHTML += '<tbody>';
        //显示每月的天数
        for (var k = 1; k <= this.getDayCountByYearAndMonth(year, m); k++) {
            var d = k < 10 ? ("0" + k) : (k + "");
            var allowD = allowDays.contains(d);
            var notAllowD = notAllowDays.contains(d);
            // alert("月" + m + "天" +　k　+ "   " + allowD + "  " + notAllowD);
            var day = new Date(year + "/" + this.month[i] + "/" + k).getDay();

            if ((day + 1) == 1) {
                calHTML += '<tr>';
            }
            if (k == 1) {
                //显示每月前面空余的天数
                var fdb = firstDayBefore;
                for (var j = 0; j < firstDayBefore; j++) {
                    var ym = this.getDayCountByYearAndMonth(year, (m-1));
                    if (j == 0) {
                        calHTML += '<td data-date="old" style="color: #CCCCCC;padding: 0px;background-color:rgba(247,247,247,0.5)"><div>' + (ym - fdb + 1) + '</div></td>';
                    } else {
                        calHTML += '<td data-date="old" style="color: #CCCCCC;padding: 0px"><div>' + (ym - fdb + 1) + '</div></td>';
                    }
                    fdb--;
                }
            }
            if (day == 0 || day == 6) {
                if (allowD) {
                    if (perm === "true") {
                        calHTML += '<td sts="true" style="padding: 0px;background-color:rgba(247,247,247,0.5)" id="' + year + '-' + this.month[i] + "-" + d + '" onclick="calendar.insCalendar(this);"><div>' + k + '</div></td>';
                    } else {
                        calHTML += '<td sts="true" style="padding: 0px;background-color:rgba(247,247,247,0.5)" id="' + year + '-' + this.month[i] + "-" + d + '" ><div>' + k + '</div></td>';
                    }
                } else {
                    if (perm === "true") {
                        calHTML += '<td class="red-color" sts="true" style="padding: 0px;background-color:rgba(247,247,247,0.5)" id="' + year + '-' + this.month[i] + "-" + d + '" onclick="calendar.insCalendar(this);"><div>' + k + '</div></td>';
                    } else {
                        calHTML += '<td class="red-color" sts="true" style="padding: 0px;background-color:rgba(247,247,247,0.5)" id="' + year + '-' + this.month[i] + "-" + d + '"><div>' + k + '</div></td>';
                    }
                }
            } else {
                if (notAllowD) {
                    if (perm === "true") {
                        calHTML += '<td style="color: red;padding: 0px" sts="false" id="' + year + '-' + this.month[i] + "-" + d + '" onclick="calendar.insCalendar(this);"><div>' + k + '</div></td>';
                    } else {
                        calHTML += '<td style="color: red;padding: 0px" sts="false" id="' + year + '-' + this.month[i] + "-" + d + '"><div>' + k + '</div></td>';
                    }
                } else {
                    if (perm === "true") {
                        calHTML += '<td sts="false" style="padding: 0px" id="' + year + '-' + this.month[i] + "-" + d + '" onclick="calendar.insCalendar(this);"><div>' + k + '</div></td>';
                    } else {
                        calHTML += '<td sts="false" style="padding: 0px" id="' + year + '-' + this.month[i] + "-" + d + '"><div>' + k + '</div></td>';
                    }
                }
            }
        }
        for (i = 0; i < (6 - lastWeek); i++) {
            if ((i + 1) == (6 - lastWeek)){
                calHTML += '<td data-date="new" style="color: #CCCCCC;padding: 0px;background-color:rgba(247,247,247,0.5)"><div>' + (i + 1) + '</div></td>';
            } else {
                calHTML += '<td data-date="new" style="color: #CCCCCC;padding: 0px"><div>' + (i + 1) + '</div></td>';
            }

        }
        if ((day + 1) == 7) {
            calHTML += '</tr>';
        }
        calHTML += '</tbody></table></div>';
        return calHTML;
    },
    getLastDateWeek: function (oDate) {
        oDate.setMonth(oDate.getMonth());
        oDate.setMonth(oDate.getMonth() + 1);
        oDate.setDate(1);
        oDate.setMonth(oDate.getMonth());
        oDate.setDate(0);
        return oDate.getDay()
    },
    getDayCountByYearAndMonth: function (year, month) {
        month++;
        if (month == 4 || month == 6 || month == 9 || month == 11)
            return 30;
        if (month == 2) {
            if ((year % 4 == 0) || (year % 400 == 0)) {
                return 29;
            }
            return 28;
        }
        return 31;
    },
    getCurrDayActive: function (id) {
        var date = new Date();
        var day = date.getDate();
        var month = date.getMonth() + 1;
        var year = date.getFullYear();
        var hYear = $("#h3_year").html();
        if (hYear == year){
            var monthArr = $("#" + id).find("caption");
            $(monthArr).each(function () {
                var mThis = $(this);
                if (month == mThis.attr("month")){
                    mThis.css({"color": "#0e9aef", "font-weight": "bold"});
                    var dayArr= $(mThis).parent().children("tbody").children("tr").children("td").children("div");
                    $(dayArr).each(function () {
                        if($(this).html() == day){
                            $(this).addClass("td-active");
                        };
                    })
                    $("[data-date=old] div").removeClass("td-active");
                    $("[data-date=new] div").removeClass("td-active");
                }
            })
        }
    },
    builtSetYearAndMonth: function (yearNum) {
        for (var i = -yearNum; i < yearNum; i++) {
            this.yearObj = document.createElement("option");
            this.yearObj.innerHTML = parseInt(new Date().getFullYear()) + i;
            this.yearObj.value = parseInt(new Date().getFullYear()) + i;
            document.getElementById("setYear").appendChild(this.yearObj);
        }
        document.getElementById("setYear").selectedIndex = yearNum + 1;
        $("#calendar_div").html(this.makeDays(parseInt(this.year)));
    },
    getCalendar: function (url, id) {
        $.ajax({
            type: "post",
            url: url,
            dataType: 'json',
            data: {"year": this.year},
            success: function (data) {
                $("#calendar_div").html(calendar.makeDays(parseInt(calendar.year), data.list));
                $('#yes_trade_day').html(data.yesTradeDay);
                $('#not_trade_day').html(data.notTradeDay);
                calendar.getCurrDayActive(id);
            }
        });
    },
    initSetYear: function (list) {
        $("#h3_year").html(parseInt(this.year));
        $("#calendar_div").html(this.makeDays(parseInt(this.year), list));
    },
    builtSetYearAdd: function (url, id) {
        this.year = parseInt($("#h3_year").html()) + 1;
        $("#h3_year").html(parseInt(this.year));
        this.getCalendar(url, id);
    },
    builtSetYearSub: function (url, id) {
        this.year = parseInt($("#h3_year").html()) - 1;
        $("#h3_year").html(parseInt(this.year));
        this.getCalendar(url, id);
    },
    insCalendar: function (obj) {
        var id = $(obj).attr("id");
        var data = {"tradeDate": id, "tradeSts": $(obj).attr("sts")};
        this.calendarAjax(data, obj, "ins");
    },
    insBatchCalendar: function (settings, year) {
        var data = {"setting": settings, "tradeYear": year};
        this.calendarAjax(data, "", "batch");
    },
    calendarAjax: function (data, obj, type) {
        shade.showBg("dialog", "正在更新日历...请稍后");
        $.ajax({
            type: "post",
            url: "/admin/tradeCalendarSetting/insertCalendar",
            dataType: 'json',
            data: data,
            success: function (data) {
                if (type == "ins") {
                    calendar.returnVal(data, obj);
                } else if (type == "batch") {
                    calendar.initSetYear(data.list);
                }
                $('#yes_trade_day').html(data.yesTradeDay);
                $('#not_trade_day').html(data.notTradeDay);
                // window.location.href = "/admin/tradeCalendarSetting/list";
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                if (XMLHttpRequest.responseText == "IsAjax") {
                    window.location.href = "/handle600";
                    return;
                }
            },
            complete: function () {
                shade.hideBg("dialog");
            }
        });
    },
    initAjax: function (url, id) {
        $.ajax({
            type: "post",
            url: url,
            dataType: 'json',
            success: function (data) {
                calendar.initSetYear(data.list);
                calendar.getCurrDayActive(id);
            }
        });
    },
    returnVal: function (data, obj) {
        if (data.msg && (data.week == "周六" || data.week == "周日")) {
            if (data.sts == "false") {
                $(obj).addClass("red-color");
            } else if (data.sts == "true") {
                $(obj).removeClass("red-color");
            }
        } else {
            if (data.sts == "false") {
                $(obj).css("color", "red");
            } else if (data.sts == "true") {
                $(obj).css("color", "");
            }
        }
    },
    prevDate: function (date) {
        return this.prevAndNextDate(date, "sub");
    },
    nextDate: function (date) {
        return this.prevAndNextDate(date, "add");
    },
    prevAndNextDate: function (date, type) {
        var d = new Date(date);
        if (type == "add") {
            d.setDate(d.getDate() + 1);
        } else if (type == "sub") {
            d.setDate(d.getDate() - 1);
        }
        var m = d.getMonth() + 1;
        m = m < 10 ? ('0' + m) : m;
        var dd = d.getDate() < 10 ? ('0' + d.getDate()) : d.getDate();
        return d.getFullYear() + '-' + m + '-' + dd;
    }
}