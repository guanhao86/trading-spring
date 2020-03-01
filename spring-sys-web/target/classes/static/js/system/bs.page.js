//执行跳转页面
function toPage(currentPage){
    $(".currentPage").each(function(){
        $(this).val(currentPage);
    });
    $(".key").each(function(){
        $(this).val($("#keymsg").val());
    });
    $(".keyWord").each(function(){
        $(this).val($("#keyWordMsg").val());
    });
    $(".pagingForm").each(function(){
        $(this).attr("action",$("#pagingFormUrl").val());
    });
    //获取我需要的参数值,用于分页条件过滤
    addParams();

    //提交
    $("#pagingForm").submit();
}

//多个pageBean的页面跳转函数
function toPages(currentPage,num){
    $(".currentPage"+num).each(function(){
        $(this).val(currentPage);
    });
    $(".key"+num).each(function(){
        $(this).val($("#keymsg").val());
    });
    $(".keyWord"+num).each(function(){
        $(this).val($("#keyWordMsg").val());
    });
    $(".pagingForm"+num).each(function(){
        $(this).attr("action",$("#pagingFormUrl").val());
    });
    $(".pageBeanNum"+num).each(function(){
        $(this).val($("#pageBeanNum_pageBean").val());
    });

    //获取我需要的参数值,用于分页条件过滤
    addParams();

    //提交
    $("#pagingForm"+num).submit();
}


//设置每页显示几条数据
function changePageSize(){
    $("#currentPage").val(1);
    $("#pagingForm").submit();

}

//获取表单提交的url
$(function(){
    $("#pagingForm").attr("action",$("#pagingFormUrl").val());
    addParams();
    //显示页面分页信息
});

//显示需要的分页信息
function showPageMsg(id){
    $(".pageForm").each(function(){
        $(this).hide();
    });
    $("#pagingForm"+id).show();
}


//为参数建立相应 的input框用于提交
function addParams(){
    //开始默认加载参数input框
    var toPageParametersDiv = "";
    $(".toPageParameters").each(function(){
        var th = $(this);
        var  parameterName = $(this).attr("name");
        var  parameterValue = "";
        if (th.is('input')){
            parameterValue = $(this).val();
        } else if(th.is('select')){
            parameterValue = $(this).children('option:selected').val();
        }
        if(parameterValue!=null&&parameterValue!=""){
            toPageParametersDiv+="<input type='hidden' name='"+parameterName+"' value='"+parameterValue+"'/>";
        }
    });
    $(".toPageParametersDiv").each(function(){
        $(this).html(toPageParametersDiv);
    });

}


//参数变化时默认调用的函数
function changeParams(){
    addParams();
    //调用分页函数
    toPage(1);
}

//设置pageBeanNum参数
function setpageBeanNum(obj){
    $("#pageBeanNum_pageBean").val(obj);
}







//流水号js
function gaveumber(value,long){
    var valueLong = long - (value+"").length;
    var result = "";
    if(valueLong>=0){
        for(var i=0;i<valueLong;i++){
            result+="0";
        }
        result+=value;
    }
    return result;
}


