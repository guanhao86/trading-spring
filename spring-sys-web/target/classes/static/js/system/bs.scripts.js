/**
 * Created by Memory on 2016/9/14.
 */

var keyboard = {
    submitKey: function (e) {
        var theEvent = e || window.event;

        // var code = theEvent.keyCode || theEvent.which || theEvent.charCode;
        if (theEvent.ctrlKey && theEvent.keyCode == 13) {
            shade.showBg('dialog', '搜索中请稍后...');
            var formID = $('[data-button="save"]').attr("data-form-id");
            if (formID == undefined){
                formID = $('[data-button="search"]').attr("data-form-id");
            }
            $('#' + formID).submit();
        } else if (theEvent.keyCode == 39){
            var page = document.getElementById("next").getAttribute("next-page");
            toPage(page);
        } else if (theEvent.keyCode == 37){
            var page = document.getElementById("pre").getAttribute("pre-page");
            toPage(page);
        } else {
            $('.J_menuItem', parent.document).each(function () {
                var keyStr = $(this).attr("key-code");
                if (keyStr != "" && keyStr != "false"){
                    var code = keyStr.charCodeAt();
                    if (theEvent.ctrlKey && theEvent.altKey && theEvent.keyCode == code){
                        parent.window.keyboardP($(this).attr("title"));
                        return true;
                    }
                }

            })
        }
    }
}

$(function () {
    shade.hideBg("dialog");
    shade.hideProgress();

    $(document, parent.document).on('keydown', function (e) {
        keyboard.submitKey(e);
    })

    /*$(document).on('keydown' , function (e) {
        keyboard.pageKey(e, this);
    })*/

    $('.chosen-select').on('change', function () {
        $(this).focus();
        $(this).blur();
    })

    $(":checkbox[cBox]").on('click', function () {
        var cBoxValue = $(this).attr("cBox");
        var checked = $(this).prop("checked");
        $(":checkbox[pId='" + cBoxValue+ "']").prop("checked", checked);
    })

    $(":checkbox[pId]").on('click', function () {
        var pIdValue = $(this).attr("pId");
        var cBoxThis = $(":checkbox[cBox='" + pIdValue+ "']");
        var allCheckNum = $(":checkbox[pId='" + pIdValue + "']").length;
        var checkedNum = $(":checkbox[pId='" + pIdValue + "']:checked").length;
        switch(checkedNum) {
            case allCheckNum:
                cBoxThis.prop({"checked": true, "indeterminate": false});
                break;
            case 0:
                cBoxThis.prop({"checked": false, "indeterminate": false});
                break;
            default:
                cBoxThis.prop({"checked": false, "indeterminate": true});
        }
    })

    /* $.ajaxSetup({
     timeout: 3000,
     url: "/checkSession" , // 默认URL
     aysnc: false , // 默认同步加载
     type: "POST" , // 默认使用POST方式
     complete:function(XMLHttpRequest,textStatus){
     if(textStatus){
     window.location.href = '/login';
     }
     }
     })*/

    $("[disable-space]").on('keyup', function (e) {
        return this.value = this.value.replace(/\s/g, '');
    })

    /**
     * 加载 遮罩显示
     */
    $("[loading=true]").on('click', function () {
        var msg = $(this).attr("loading-msg");
        if (msg != undefined && !msg.isEmpty()){
            shade.showBg('dialog', msg);
        } else {
            shade.showBg('dialog', '执行中，请稍后...');
        }
    })

    //选择图标时，将选中的图标放到文本框中，并显示相应的图标图片
    $("[dialog-selected]").on('click', 'div', function () {
        var lVal = $(this).attr('lcon-val');
        var inputId = $(this).attr('input-id');
        var opt = $(this).attr('dialog-opt');
        $('#' + inputId).val(lVal);
        if (opt) {
            $('#' + opt).html('<i class="fa ' + lVal + '"></i>');
        }
    })
    //添加跳转
    $('[data-button="insert-pre"]').on("click", function () {
        shade.showBg('dialog', '正在努力奔跑中...请稍后');
        if (!handleAuth(this)) {
            return
        }
        dataHref(this);
    })

    //返回跳转
    $('[data-button="back"]').on("click", function () {
        shade.showBg('dialog', '正在努力奔跑中...请稍后');
        if (typeof($(this).attr("data-href")) == "undefined") {
            backHistory();
            return;
        }
        dataHref(this);
    })
    //进入跳转
    $('[data-button="skip"]').on('click', function () {
        shade.showBg('dialog', '正在努力奔跑中...请稍后');
        if (typeof($(this).attr("parent-iframe-id")) != "undefined") {
            return window.parent.dataHref(this);
        }
        return dataHref(this);
    })
    //显示弹出框
    $('[modal="show"]').on('click', function () {
        var modalID = $(this).attr('modal-id');
        $('#' + modalID).modal('show');
    })
    //隐藏弹出框
    $('[modal="hide"]').on('click', function () {
        var modalID = $(this).attr('modal-id');
        $('#' + modalID).modal('hide');
    })
    /**
     * 将选中的数据行添加到表格中
     */
    $('[data-button="checked"]').on('click', function () {
        var t = $(this);
        var dataName = t.attr('data-name');
        var dataCompanyName = t.attr('data-company-name');
        var dataOfficeName = t.attr('data-office-name');
        var dataId = t.attr('data-id');
        tableOp.addTableTbodyTr(dataId, dataName, dataCompanyName, dataOfficeName);
        t.parent('td').parent('tr').hide();
    })
    /**
     * 移除表格中选中数据
     */
    $('#tbody_tr').on('click', '[data-button="remove"]', function () {
        var rThis = this;
        var dataId = $(rThis).attr('id');
        tableOp.showTr(dataId, rThis);
    })

    /**
     * 保存事件
     */
    $('[data-button="save"]').on('click', function () {
        var formID = $(this).attr("data-form-id");
        $('#' + formID).submit();
    })

    $('#upload_modal_confirm').on('click', function () {
        $('#form_upload').submit();
    })
    $('[data-button="empty"]').on('click', function () {
        dataHref(this);
    })
    /**
     * 搜索事件
     */
    $('[data-button="search"]').on('click', function () {
        var formID = $(this).attr('data-form-id');
        var boo = $.antiSqlValid();
        if (!boo) {
            $('#goods_prompt').html(promptInfo.prompt("不能输入非法字符，该内容已被清除"));
            return;
        }
        shade.showBg('dialog', '正在努力奔跑中...请稍后');
        $('#' + formID).submit();
    })

    //搜索
    /*$('#dialog-search-content').on('input', function () {
     var contentVal = $(this).val();
     if(contentVal != ''){
     var searchHtml = $('[dialog-selected] div a:contains("' + contentVal+ '")').html();
     if(searchHtml != undefined){
     var lconVal = searchHtml.replace(/<.*?>/ig,"").trim();
     $('#medical').hide();
     $('#medical-search div').html('');
     $('#medical-search').show();
     $('#medical-search [dialog-selected]').html('<div aria-hidden="true" data-dismiss="modal" ' +
     'class="fa-hover col-md-4 col-sm-5" lcon-val="' + lconVal + '" input-id="default_lcon" ' +
     'dialog-opt="setting_menu_opt_a"><a href="#">' + searchHtml + '</a></div>');
     } else {
     $('#medical').show();
     $('#medical-search').hide();
     }
     } else {
     $('#medical').show();
     $('#medical-search').hide();
     }
     })*/
    //通过输入，获取到的图标，失去焦点后，显示获取到的图标图片
    /*$('#default_lcon').on('blur', function () {
     var val = $(this).val();
     $('#setting_menu_opt_a').html('<i class="fa ' + val + '"></i> 选择图标');
     })*/

    /**
     * 清空表单事件
     */
    $('[empty="true"]').on('click', function () {
        var formID = $(this).attr('reset-id');
        $(".chosen-select").chosen('destroy').trigger("chosen:updated.chosen");
        $('#' + formID).click(function () {
            $(':input')
                .not(':button, :submit, :reset, :hidden')
                .val('')
                .removeAttr('checked')
                .removeAttr('selected');
            var config = {
                ".chosen-select": {},
                ".chosen-select-deselect": {allow_single_deselect: !0},
                ".chosen-select-no-single": {disable_search_threshold: 10},
                ".chosen-select-no-results": {no_results_text: "Oops, nothing found!"},
                ".chosen-select-width": {width: "100%"}
            };
            $(".chosen-container").css("width", "100%");
            for (var selector in config)$(selector).chosen(config[selector]);
        })
        $('#' + formID).trigger("click");
        $.clearForm("search");
    })


    /**
     * 删除事件
     */
    $('[delete="true"]').on('click', function () {
        var boo = false;
        if (typeof($(this).attr("parent-iframe-id")) != "undefined") {
            return parentIFrameConfirm(boo, '确认要删除吗？');
        }
        if (!handleAuth(this)) {
            return
        }
        return windowConfirm(boo, '确认要删除吗？');
    })

    $('[remove="true"]').on('click', function () {
        var boo = false;
        if (typeof($(this).attr("parent-iframe-id")) != "undefined") {
            return parentIFrameConfirm(boo, '确认要移除吗？');
        }
        return windowConfirm(boo, '确认要移除吗？');
    })

    $('[confirm="true"]').on('click', function () {
        var boo = false;
        var msg = $(this).attr("message");
        if (typeof($(this).attr("parent-iframe-id")) != "undefined") {
            return parentIFrameConfirm(boo, msg);
        }
        return windowConfirm(boo, msg);
    })

    $('[prompt="true"]').on('click', function () {
        var value = "", href = "", obj = $(this);
        var message = obj.attr("message").replace("@_@", "\r\n");

        if (typeof($(this).attr("parent-iframe-id")) != "undefined") {
            value = window.parent.prompt(message, obj.attr("value"));
        } else {
            value = window.prompt(message, obj.attr("value"));
        }
        if (value != null && !value.isEmpty()){
            href = obj.attr("href");
            href += href.indexOf("?") != -1 ? "&" : "?";
            obj.attr("href", href + obj.attr("attribute") + "=" + value);
            shade.showBg('dialog', '正在努力奔跑中...请稍后');
            return true;
        }
        return false;
    })

    /**
     * 显示进度的提示框
     */
    $('[confirmP="true"]').on('click', function () {
        var boo = false;
        var msg = $(this).attr("message");
        if (typeof($(this).attr("parent-iframe-id")) != "undefined") {
            return parentIFrameConfirm(boo, msg);
        }
        return windowConfirm(boo, msg);
    })
    /**
     * 编辑事件
     */
    $('[edit="true"]').on('click', function () {
        var boo = false;
        if (typeof($(this).attr("parent-iframe-id")) != "undefined") {
            boo = window.parent.editHref(this.href);
            if (boo){
                shade.showBg('dialog', '正在努力奔跑中...请稍后');
            }
            return boo;
        }
        if (!handleAuth(this)) {
            return
        }
        boo = editHref(this.href);
        if (boo){
            shade.showBg('dialog', '正在努力奔跑中...请稍后');
        }
        return boo;
    })

    $('#sop_two_li').on('click', '#sop_two_a', function () {
        var navID = $(this).attr('nav-id');
        $('#' + navID).parent('ul').parent('li').addClass('nav-active');
    })

})

/**
 * 页面跳转
 * @param obj
 */
var dataHref = function (obj) {
    var dataHref = $(obj).attr('data-href');
    window.location.href = dataHref;
}

/**
 * 父类调用 提示框 确认后 有 遮罩 显示
 * @param boo
 * @param msg
 * @returns {boolean|*}
 */
var parentIFrameConfirm = function (boo, msg) {
    if (typeof($(this).attr("parent-iframe-id")) != "undefined") {
        boo = window.parent.confirmx(msg, this.href);
        if (boo){
            shade.showBg('dialog', '正在努力奔跑中...请稍后');
        }
        return boo;
    }
}

/**
 * 提示框 确认后 有 遮罩 显示
 * @param boo
 * @param msg
 * @returns {boolean|*}
 */
var windowConfirm = function (boo, msg) {
    boo = confirmx(msg, this.href);
    if (boo){
        shade.showBg('dialog', '正在努力奔跑中...请稍后');
    }
    return boo;
}

var editHref = function (href) {
    window.location.href = href;
}
var timeCal = function (tradeTime) {
    var day = dateFormat("yyyy-MM-dd");
    var currTime = dateFormat("yyyy-MM-dd hh:mm:ss");
    var cTime = new Date(currTime).getTime();
    var tTime = new Date((day + " " + tradeTime)).getTime();
    return cTime - tTime;
}
var handleAuth = function (obj) {
    if (typeof($(obj).attr("trader-day")) != "undefined") {
        var t = timeCal($(obj).attr("trader-day"));
        if (t < 0) {
            $('#goods_prompt').html(promptInfo.prompt("交易时间内，不能操作该功能"));
            return false;
        }
    }
    return true;
}
/**
 * 导航菜单显示状态
 * @param id
 * @param twoHTML
 * @param threeHTML
 */
var navigation = function (id) {
    $('#backstage_nav li').removeClass('active');
    /*清除所有菜单的选中状态*/
    $('#backstage_nav li').each(function () {
        var idVal = $(this).attr('id');
        /*获取每个菜单的ID值*/
        if (id === idVal) {
            $(this).addClass('active');
            /*添加菜单选中状态*/
            $(this).parent('ul').parent('li').addClass('nav-active');
            /*添加上级菜单选中状态*/
            var twoHTML = $(this).parent('ul').parent('li').children('a').children('span').html();
            /*获取选中导航菜单上级导航菜单的名称*/
            var threeHTML = $(this).children('a').html();
            /*获取选中导航菜单的名称*/
            sopNavigation(twoHTML, threeHTML, id);
            /*显示面包片导航目录*/
        }
    })
}

/**
 * 显示面包片目录事件
 * @param twoHTML
 * @param threeHTML
 * */
var sopNavigation = function (twoHTML, threeHTML, navID) {
    // $('#sop_two_li').html("<a href='javascript:void(0);' id='sop_two_a' nav-id='" + navID + "'>" + twoHTML + "</a>");/*向二级面包片导航目录添加选中的菜单名称*/
    $('#sop_two_li').html(twoHTML);
    /*向二级面包片导航目录添加选中的菜单名称*/
    $('#sop_three_li').html(threeHTML);
    /*向三级面包片导航目录添加选中的菜单名称*/
    $('#sop_two_li').show();
    /*显示二级菜单面包片导航目录*/
    $('#sop_three_li').show();
    /*显示三级级菜单面包片导航目录*/
}

/**
 * 提示框
 * @param mess
 * @param href
 * @returns {boolean}
 */
function confirmx(mess, href) {
    var con = confirm(mess);
    if (con) {
        if (typeof href == 'function') {
            href();
        } else {
            location = href;
        }
        return true;
    }
    return false;
}

var progressInfo = function () {
    setInterval(progressExecute, 100);
}
var progressExecute = function () {
    $('#progress_iframe').show();
    var px = 0;
    $('#progress_execute').css("width", (px++) + "%");
}
/**
 * 获取选中数据的相关属性值
 * @param obj
 * @param radioName
 */
var getRadioVale = function (obj, radioName) {
    var radioObj = $('input:radio[name="' + radioName + '"]:checked');
    var officeID = radioObj.val();
    var officeName = radioObj.attr("name-value");
    var inputID = radioObj.attr("input-id");
    var inputValID = radioObj.attr("input-val-id");
    modalChecked(obj, officeID, officeName, inputID, inputValID);
}
/**
 * 验证是否有选中的数据项
 * 将选中的数据项添加到相应的文本框中
 * @param obj
 * @param dataID
 * @param dataName
 * @param inputID
 * @param inputValID
 * @returns {boolean}
 */
var modalChecked = function (obj, dataID, dataName, inputID, inputValID) {
    if (dataID == null) {
        alert("没有选中项");
        return false;
    } else {
        var modalID = $(obj).attr("modal-id-hide");
        $('#' + inputID).val(dataID);
        $('#' + inputValID).val(dataName);
        $('#' + modalID).modal('hide');
    }
}

var backHistory = function () {
    window.history.go(-1);
}
/**
 * 树结构数据初始化
 * @param obj
 * @param setting
 * @param zNodes
 */
var treeInit = function (obj, setting, zNodes) {
    // 初始化树结构
    var tree = $.fn.zTree.init(obj, setting, zNodes);
    // 不选择父节点
    tree.setting.check.chkboxType = {"Y": "ps", "N": "s"};
    // 默认展开全部节点
    tree.expandAll(true);
    return tree;
}

/**
 * 获取选中的数据节点
 * @param tree
 */
var treeCheckedNodes = function (tree, inputId) {
    var nodes = tree.getCheckedNodes();
    var s = "";
    for (i = 0; i < nodes.length; i++) {
        if (s == "") {
            s = nodes[i].id;
        } else {
            s += "," + nodes[i].id;
        }
    }
    $('#' + inputId).val(s);
}

/**
 * Iframe高度自适应
 * @param obj
 */
var setCwinHeight = function (obj) {
    var cwin = obj;
    if (document.getElementById) {
        if (cwin && !window.opera) {
            if (cwin.contentDocument && cwin.contentDocument.body.offsetHeight)
            // cwin.height = document.documentElement.offsetHeight;
                cwin.height = cwin.contentDocument.body.offsetHeight + 50;
            else if (cwin.Document && cwin.Document.body.scrollHeight)
                cwin.height = cwin.Document.body.scrollHeight;
        }
    }
}

var uploadHref = function (formUploadAction, downloadHref) {
    $('#form_upload').attr("action", formUploadAction);
    $("#download_template").attr("href", downloadHref);
    $('#upload_modal').modal('show');
}

/**
 * 表格操作对象
 * @type {{addTableTbodyTr: tableOp.addTableTbodyTr, getRowObj: tableOp.getRowObj, getRowNo: tableOp.getRowNo, delRow: tableOp.delRow, showTr: tableOp.showTr}}
 */
var tableOp = {
    /*添加行和列*/
    addTableTbodyTr: function (dataId, dataName, dataCompanyName, dataOfficeName) {
        var newTr = document.getElementById("tbody_tr").insertRow();//添加一行
        // var newTd0 = newTr.insertCell();//添加列
        // var newTd1 = newTr.insertCell();//添加列
        var newTd2 = newTr.insertCell();//添加列
        var newTd3 = newTr.insertCell();//添加列

        // newTd0.innerText = dataCompanyName;
        // newTd1.innerText = dataOfficeName;
        newTd2.innerText = dataName;
        newTd3.innerHTML = '<a href="javascript:void(0);" class="fa fa-trash-o" id="' + dataId + '" data-button="remove" title="移除"> 移除</a>';
    },
    /*得到行对象*/
    getRowObj: function (obj) {
        var i = 0;
        while (obj.tagName.toLowerCase() != "tr") {
            obj = obj.parentNode;
            if (obj.tagName.toLowerCase() == "table")
                return null;
        }
        return obj;
    },
    //根据得到的行对象得到所在的行数
    getRowNo: function (obj) {
        var trObj = this.getRowObj(obj);
        var trArr = trObj.parentNode.children;
        for (var trNo = 0; trNo < trArr.length; trNo++) {
            if (trObj == trObj.parentNode.children[trNo]) {
                alert(trNo + 1);
            }
        }
    },
    /*删除行*/
    delRow: function (obj) {
        var tr = this.getRowObj(obj);
        if (tr != null) {
            tr.parentNode.removeChild(tr);
        } else {
            throw new Error("the given object is not contained by the table");
        }
    },
    /*显示行*/
    showTr: function (dataId, obj) {
        $('#tbody_tr_hide tr').each(function () {
            var t = $(this);
            if (t.css('display') == 'none') {
                var id = t.children('td').children('a').attr('data-id');
                if (dataId == id) {
                    t.show();
                    tableOp.delRow(obj);
                }
            }
        })
    }
}

var promptInfo = {
    prompt: function (msg) {
        var p = '<div class="alert alert-danger alert-block fade in" alert-id="msg">';
        p += '<button type="button" class="close close-sm" data-dismiss="alert">';
        p += '<i class="fa fa-times"></i></button>';
        p += '<p>' + msg + '</p></div>';
        return p;
    },
    promptSuccess: function (msg) {
        var p = '<div class="alert alert-success alert-block fade in" alert-id="msg">';
        p += '<button type="button" class="close close-sm" data-dismiss="alert">';
        p += '<i class="fa fa-times"></i></button>';
        p += '<p>' + msg + '</p></div>';
        return p;
    },
    getCheckBoxVal: function (checkBoxName, inputID) {
        var goodsArr = new Array();
        $("input:checkbox[name=" + checkBoxName + "]").each(function (index) {
            var check = $(this).prop("checked");
            if (check == true) {
                goodsArr[index] = $(this).attr("id");
            }
        })
        for (var i = 0; i < goodsArr.length; i++) {
            if (goodsArr[i] == "" || typeof(goodsArr[i]) == "undefined") {
                goodsArr.splice(i, 1);
                i = i - 1;
            }
        }
        $("#" + inputID).val(goodsArr.toString());
    },
    getCheckBoxHTML: function (checkBoxName, inputID) {
        var goodsArr = new Array();
        var html = '';
        $("input:checkbox[name=" + checkBoxName + "]").each(function (index) {
            var check = $(this).prop("checked");
            if (check == true) {
                goodsArr[index] = $(this).attr("id");
                html += '<tr><td>' + $(this).attr("id") + '</td>';
                html += '<td>' + $(this).attr("value-name") + '</td>';
                html += '<td><a href="javascript:void(0)" class="fa fa-trash-o" remove-code="true" goods-code="'
                    + $(this).attr("id") + '" title="移除" > 移除</a></td></tr>';
            }
        })
        for (var i = 0; i < goodsArr.length; i++) {
            if (goodsArr[i] == "" || typeof(goodsArr[i]) == "undefined") {
                goodsArr.splice(i, 1);
                i = i - 1;
            }
        }
        $("#" + inputID).val(goodsArr.toString());
        $("#goods_tbody").html(html);
    },
    getCheckBoxHTML2: function (html, checkBoxName, inputID) {
        var goodsArr = new Array();
        $("input:checkbox[name=" + checkBoxName + "]").each(function (index) {
            var check = $(this).prop("checked");
            if (check == true) {
                goodsArr[index] = $(this).attr("id");
                html += '<tr><td>' + $(this).attr("id") + '</td>';
                html += '<td>' + $(this).attr("value-name") + '</td>';
                html += '<td>' + $(this).attr("value-total") + '</td>';
                html += '<td>1</td>';
                html += '<td><a href="javascript:void(0)" class="fa fa-trash-o" remove-code="true" goods-code="'
                    + $(this).attr("id") + '" title="移除" > 移除</a></td></tr>';
            }
        })
        for (var i = 0; i < goodsArr.length; i++) {
            if (goodsArr[i] == "" || typeof(goodsArr[i]) == "undefined") {
                goodsArr.splice(i, 1);
                i = i - 1;
            }
        }
        $("#" + inputID).val(goodsArr.toString());
        $("#goods_tbody").html(html);
    }
}

var shade = {
    intervalClear: "",
    /*显示遮罩*/
    showBg: function (id, text) {
        var bh = $("body", window.parent.document).height();
        var bw = $("body", window.parent.document).width();
        $("#fullbg", window.parent.document).css({height: bh, width: bw, display: "block"});
        if (text != undefined && text != "") {
            $("#dialog-text", window.parent.document).html(text);
        }
        $("#" + id, window.parent.document).show();
    },
    /*隐藏遮罩*/
    hideBg: function (id) {
        var bh = $("body", window.parent.document).height();
        var bw = $("body", window.parent.document).width();
        $("#fullbg", window.parent.document).css({height: bh, width: bw, display: "none"});
        $("#" + id, window.parent.document).hide();
    },
    showProgress: function () {
        $('#progress_bar').parent().parent().show();
        /*var px = 0;
         this.intervalClear = setInterval(function () {
         $('#progress_bar').css('width', px + '%');
         px++;
         },100);*/
    },
    hideProgress: function () {
        // clearInterval(this.intervalClear);
        $('#progress_bar', window.parent.document).parent().parent().hide();
    }
}

$(function () {
    $.extend({
        checkRadio: function (val, inputID1, inputID2, inputID3, inputID4) {
            if (val == 0) {
                $('#' + inputID1).html("(%)");
                $('#' + inputID2).html("(%)");
                $('#' + inputID3).attr("min", 0).attr("max", 100);
                $('#' + inputID4).attr("min", 0).attr("max", 100);
            } else if (val == 1) {
                $('#' + inputID1).html("");
                $('#' + inputID2).html("");
                $('#' + inputID3).removeAttr("min", 0).removeAttr("max", 100);
                $('#' + inputID4).removeAttr("min", 0).removeAttr("max", 100);
            }
        },
        clearForm: function (id) {
            var objId = document.getElementById(id);
            if (objId == undefined) {
                return;
            }
            for (var i = 0; i < objId.elements.length; i++) {
                if (objId.elements[i].type == "text") {
                    objId.elements[i].value = "";
                } else if (objId.elements[i].type == "password") {
                    objId.elements[i].value = "";
                } else if (objId.elements[i].type == "radio") {
                    objId.elements[i].checked = false;
                } else if (objId.elements[i].type == "checkbox") {
                    objId.elements[i].checked = false;
                } else if (objId.elements[i].type == "select-one") {
                    objId.elements[i].options[0].selected = true;
                } else if (objId.elements[i].type == "select-multiple") {
                    for (var j = 0; j < objId.elements[i].options.length; j++) {
                        objId.elements[i].options[j].selected = false;
                    }
                } else if (objId.elements[i].type == "textarea") {
                    objId.elements[i].value = "";
                }
                //else if (objId.elements[i].type == "file") {
                // //objId.elements[i].select();
                // //document.selection.clear();
                // // for IE, Opera, Safari, Chrome
                // var file = objId.elements[i];
                // if (file.outerHTML) {
                // file.outerHTML = file.outerHTML;
                // } else {
                // file.value = ""; // FF(包括3.5)
                // }
                //}
            }
        },
        antiSqlValid: function () {
            // var pattern = new RegExp("[~'!@#$%^&*()-+_=:]");
            var pattern = new RegExp(/select|update|delete|exec|count|'|"|=|;|>|<|%/i);
            var form = document.forms[0];
            if (form.id == "search") {
                for (var i = 0; i < form.length; i++) {
                    var element = form[i];
                    if (element.value != "" && element.value != null) {
                        if (pattern.test(element.value)) {
                            element.value = "";
                            element.focus();
                            return false;
                        }
                    }
                }
            }
            return true;
        },
        /**
         * 禁止空格输入
         * @param e
         * @returns {Boolean}
         */
        banInputSapce: function (e) {
            var keynum;
            if (window.event) {
                keynum = e.keyCode
            } else if (e.which) {
                keynum = e.which
            }
            if (keynum == 32) {
                return false;
            }
            return true;
        },
        initDatePicker: function (ele) {
            var dp = ele.datepicker().on('changeDate', function (ev) {
                dp.hide();
            }).data('datepicker');
        }
    })
})

$(function () {
    /*  for (var f = 0; f < document.forms.length; f++) {
     var form = document.forms[f];
     if (form.id == "search") {
     //遍历指定form表单所有元素
     for (var i = 0; i < form.length; i++) {
     var element = form[i];
     alert(element.id);
     document.getElementById(element.id).onblur = $.antiSqlValid(element);
     }
     break;
     }
     }*/

})

var sortData = {
    sortIconDescInit: function (sortNames) {
        var sortName = $("input:hidden[sort-id=sort_name_hidden]").val();
        if (sortName != "") {
            for (var i = 0; i < sortNames.length; i++) {
                if (sortName == sortNames[i]) {
                    var th = $("#tree_table thead th").eq(i);
                    $(th).children("i").removeClass("fa-unsorted").removeClass("fa-sort-up").addClass("fa-sort-down")
                }
            }
        }
        // $("input:hidden[sort-id=sort_name_hidden]").val("");
    },
    sortIconInit: function (sortNames) {
        var sortName = $("input:hidden[sort-id=sort_name_hidden]").val();
        var sortOrder = $("input:hidden[sort-id=sort_order_hidden]").val();
        if (sortName != "" && sortOrder != "") {
            for (var i = 0; i < sortNames.length; i++) {
                if (sortName == sortNames[i]) {
                    var th = $("#tree_table thead th").eq(i);
                    if (sortOrder == "asc") {
                        $(th).children("i").removeClass("fa-unsorted").removeClass("fa-sort-down").addClass("fa-sort-up")
                    } else if (sortOrder == "desc") {
                        $(th).children("i").removeClass("fa-unsorted").removeClass("fa-sort-up").addClass("fa-sort-down")
                    }
                }
            }
        }
        // $("input:hidden[sort-id=sort_name_hidden]").val("");
        // $("input:hidden[sort-id=sort_order_hidden]").val("");
    },
    sortIconDesc: function (obj) {
        var icon = $(obj).children("i");
        $('input:hidden[sort-id="sort_order_hidden"]').eq(0).val("desc");
        $('[data-button="search"]').click();
    },
    sortIconSwitch: function (obj) {
        var icon = $(obj).children("i");
        if (icon.hasClass("fa-unsorted")) {
            $(icon).removeClass("fa-unsorted").addClass("fa-sort-up")
            $('input:hidden[sort-id="sort_order_hidden"]').eq(0).val("asc");
        } else if (icon.hasClass("fa-sort-up")) {
            $(icon).removeClass("fa-sort-up").addClass("fa-sort-down")
            $('input:hidden[sort-id="sort_order_hidden"]').eq(0).val("desc");
        } else if (icon.hasClass("fa-sort-down")) {
            $(icon).removeClass("fa-sort-down").addClass("fa-sort-up")
            $('input:hidden[sort-id="sort_order_hidden"]').eq(0).val("asc");
        }
        $('[data-button="search"]').click();
    },
    checkedData: function () {
        var options = "";
        $('input[name="dataCheck"]:checked').each(function () {
            var check = $(this).val();
            if (options == "") {
                options = check;
            } else {
                options += "," + check;
            }
        })
        return options;
    }
}