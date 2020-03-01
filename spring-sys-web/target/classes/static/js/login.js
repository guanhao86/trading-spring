/**
 * Created by Memory on 2016/10/19.
 */
$(function () {
    /*var addr = '';
     if (remote_ip_info.country != '' && remote_ip_info.country != null){
     addr += remote_ip_info.country;
     }
     if (remote_ip_info.province != '' && remote_ip_info.province != null){
     addr += "-" + remote_ip_info.province
     }
     if (remote_ip_info.city != '' && remote_ip_info.city != null){
     addr += "-" + remote_ip_info.city;
     }
     if (remote_ip_info.district != '' && remote_ip_info.district != null){
     addr += "-" + remote_ip_info.district;
     }*/
    // var webApp = '浏览器内核 = ' + browser.ua + "；语言 = " + browser.language + "；使用：" + JSON.stringify(browser.versions);
    /**
     * 获取IP 地址，浏览器内核，并保存到session中
     */
    /*$.ajax({url: "/indexAddress",type: "post",data: {/!*"ip":returnCitySN["cip"],"address":addr,*!/"webApp":webApp},dataType: "json",
     success: function(result){}
     })*/
    /**
     * 判断是否记住用户名和密码
     */
    /*if (window.localStorage) {
        $('#username').val(localStorage.username);
        $('#password').val(localStorage.password);
        if (localStorage.username != '' && localStorage.username != null
            && localStorage.password != '' && localStorage.password != null) {
            $('#remember_me').prop('checked', true);
        }
    }*/

    /*$('#username').on('input', function () {
        var val = $(this).val();
        if (val == localStorage.username) {
            $('#password').val(localStorage.password);
            $('#remember_me').prop('checked', true);
        } else {
            $('#password').val("");
            $('#remember_me').attr('checked', false);
        }
    })
    $('#password').on('input', function () {
        var val = $(this).val();
        var username = $('#username').val();
        if (val == localStorage.password && username == localStorage.username) {
            $('#remember_me').prop('checked', true);
        } else {
            $('#remember_me').prop('checked', false);
        }
    })*/
    /**
     * 记住用户名密码 和 取消记住用户名密码
     */
    /*$('#remember_me').on('click', function () {
        var checked = $(this).is(':checked');
        var username = $('#username').val();
        var password = $('#password').val();
        saveUserAndPwd(checked, username, password);
    })*/

    /**
     * 找回密码
     */
    /*$('#forgot_pwd_button').on('click', function () {
        var mail = $('#forgot_pwd_mail').val();
        sendMail(mail);
    })*/

})

/**
 * 发送找回密码邮件
 * @param mail
 */
/*var sendMail = function (mail) {
    $.post('/sendMailForgotPwd', {"email": mail}, function (result) {
        if (result.success == true) {
            alert(result.msg);
        } else {
            alert(result.msg);
        }
    }, 'json');
}*/
/**
 * 保存用户名和密码到本地浏览器
 * @param checked
 * @param username
 * @param password
 */
/*var saveUserAndPwd = function (checked, username, password) {
    if (window.localStorage) {
        if (checked) {
            localStorage.username = username;
            localStorage.password = password;
        } else {
            localStorage.removeItem('username');
            localStorage.removeItem('password');
        }
    }
}*/
/**
 * 登录
 * @param dd
 */
function login(dd) {
    var username = $('#username').val();
    var password = $('#password').val();
    if (username != "" && password != "") {
        if (username != ""){
            $('#error').html("");
        }
        if (password != ""){
            $('#error').html("");
        }
        if (dd == '13') {
            $('#login_form').submit();
            /*var webApp = '浏览器内核 = ' + browser.ua + "；语言 = " + browser.language + "；使用：" + JSON.stringify(browser.versions);
            $.ajax({
                url: "/indexAddress",
                type: "post",
                data: {/!*"ip":returnCitySN["cip"],"address":addr,*!/"webApp": webApp},
                dataType: "json",
                success: function (result) {

                }
            })*/
        }
    } else {
        if (dd == '13'){
            if (username == ""){
                $('#error').html("请填写用户名");
                return;
            }
            if (password == ""){
                $('#error').html("请填写密码");
                return;
            }
        }
    }
}