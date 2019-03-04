//提取url的参数
function getQuery(key, url) {
    var reg = new RegExp('^\\S*(\\?|&)' + key + '=([^&]*)\\S*$');
    var l = url || window.location.href;
    if (reg.test(l)) {
        return decodeURIComponent(l.replace(reg, '$2'));
    } else {
        return null;
    }
}
//设置url的参数
function setQuery(key, value, url) {
    var reg = new RegExp(key + '=[^&]*(?=&|$)');
    var l = url || window.location.href;
    if (reg.test(l)) {
        return l.replace(reg, key + '=' + encodeURIComponent(value));
    } else {
        return l + (/\?/.test(l) ? '&' : '?') + key + '=' + encodeURIComponent(value);
    }
}

function changeStyleByID(id, classname) {
    document.getElementById(id).className = classname;
}

function Input_ChangeStyle(obj, classname) {
    obj.className = classname;
}

//鼠标移到GRIDVIEW的ITEM上时颜色改变
var currentcolor;
var fontColor;
function SetSelectbgcolor(trID) {
    trID.style.backgroundColor = "#F5F8FA";
};
//鼠标离开GRIDVIEW的ITEM时颜色还原
function CancelSelectbgcolor(trID, RowAlternate) {
    trID.style.backgroundColor = "";
};

// 打开新窗口
function openNewWindow(url, name, width, height) {
    var newWin = null;
    newWin = window.open(
        url,
        name,
        "left=" + ((screen.availWidth - width) / 2) + ",top=" + ((window.screen.availHeight - height) / 2) + ",width=" + width + ",height=" + height +",location=0,resizable=1,status=no,titlebar=1,directories=0,toolbar=0,menubar=0,scrollbars=1"
    );
    if (newWin != null)
        newWin.focus();
}

// 打开新窗口
function openNewWindowNoBar(url, name, width, height) {
    var newWin = null;
    newWin = window.open(
        url,
        name,
        "left=" + ((screen.availWidth - width) / 2) + ",top=" + ((window.screen.availHeight - height) / 2) + ",width=" + width + ",height=" + height + ",location=0,resizable=1,status=no,titlebar=1,directories=0,toolbar=0,menubar=0,scrollbars=0"
    );
    if (newWin != null)
        newWin.focus();
}

//设置背景颜色
function SetBackColor(obj, color) {
    obj.style.backgroundColor = color;
}

//取消背景颜色
function CancelBackColor(obj, color) {
    obj.style.backgroundColor = "";
}

function beforeunload() {
    var n = window.event.screenX - window.screenLeft;
    var b = n > document.documentElement.scrollWidth - 20;
    var number = 0;
    if (b && window.event.clientY < 0 || window.event.altKey) {
        if (number < 15) {
            //window.event.returnValue = "如果此时关掉窗口，将不保存数据！！！您确定吗？"; 
        }
    }
}

 
 
//验证钱币类型
function IsDecimal(val) {
    var reg = new RegExp("^[0-9]+\.?[0-9]{0,2}$");
    return reg.test(val);
}
/** 
* @ 获取事件（鼠标、键盘）在火狐下触发函数不能带参数 
* @ 兼容 IE Mozilla Google Maxthon Opera 
* @ return key/null 
*/
function getEvent() {
    if (document.all) {
        return window.event; //IE  
    } else {//FF  
        func = getEvent.caller;
        while (func != null) {
            var arg0 = func.arguments[0];
            if (arg0)
                return arg0;
            func = func.caller;
        }
        return null;
    }
}
var RegControl =
{
    IsPassword: function (pwd, message) {
        var pwdlength = 8;
        var reg = /^(?![^a-zA-Z]+$)(?!\D+$).{8,}$/;
        if (!reg.test(pwd.val())) {
            if (message != null && message != "") {
                alert(message);
            } else {
                alert("Remind : The new password should contain alpha-numeric characters and be longer or equal to " + pwdlength + " !");
            }
            pwd.focus();
            pwd.select();
            return false;
        }
        return true;
    },
    IsDecimal: function (val) {//验证钱币类型
        //var reg = new RegExp("^[0-9]+\.?[0-9]{0,2}$");
        //var reg = new RegExp("^[1-9]\d*\.\d*|0\.\d*[1-9]\d*$");
        var reg = new RegExp("^(([0-9]+\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\.[0-9]+)|([1-9]*[1-9][0-9]*))$");
        return reg.test(val);
    },
    IsNumberAndZero: function (val) {//非负浮点数（正浮点数 + 0） 
        var reg = new RegExp("^[1-9]\d*\.\d*|0\.\d*[1-9]\d*|0?\.0+|0$");
        return reg.test(val);
    },
    isTime: function (val) {//验证时间
        var reg = new RegExp("^\d{4}-\d{2}-\d{2}\s\d{2}:\d{2}:\d{2}$");
        return reg.test(val);
    },
    isDate: function (val) {//验证日期

        var reg = new RegExp("^(([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)-(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-8]))))|((([0-9]{2})(0[48]|[2468][048]|[13579][26])|((0[48]|[2468][048]|[3579][26])00))-02-29)$");
        return reg.test(val);
    },
    isMobile: function (val) {
        var reg = new RegExp("^\d{11}$");
        return reg.test(val);
    },

    isTelephone: function (val) {
        var reg = new RegExp("^([0]{1}[0-9]{2,3}[-])?\d{7,8}$");
        return reg.test(val);
    },

    isEmail: function (email) {
        var reg = /([\w-\.]+)@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.)|(([\w-]+\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\]?)/;
        return reg.test(email);
    },
    IsKeyDecimal: function () {

        //获取事件 注意：在mozilla下 出发事件的函数不能带参数  
        var evt = getEvent();
        var rest = false;
        if (evt) {
            rest = true;
            //获取事件源的对象  
            var element = evt.srcElement || evt.target;
            //获取事件源的对象（这里的事件源对象是 文本框）  
            evt = evt.keyCode || evt.charCode;
            //获取 文本框的值  
            var text = element.value;
            if (evt != 8 && evt != 13) {
                //按下是否是 “.” 并只允许按下一个  
                if (evt == 46) {
                    if (text || text != '') {
                        if (!(/^\d+$/g.test(text)))
                            rest = false;
                    } else {
                        rest = false;
                    }
                    //按下是否是数字键 退格键 回车键  
                } else if (evt < 48 || evt > 57) {
                    rest = false;
                }
            }
        }
        return rest;
    },
    IsKeyInt: function (obj) {
        var val = obj.value;
        var one = val.substr(0, 1);
        if (one == '-') {
            obj.value = "";
        } else {
            obj.value = one != '-' ? (parseInt(val) || '') : one + (parseInt(val.substr(1, val.length)) || '');
        }
    },
    isNull: function (obj) {
        if (obj.value != "" && obj.value != null) {
            return true;
        }
        else {
            return false;
        }
    },

    isPhoto: function (file) {
        var ImageFileExtend = ".gif,.png,.jpg,.bmp ";
        if (file.val().length > 0) {
            //判断后缀        
            var fileExtend = file.val().substring(file.val().lastIndexOf('.')).toLowerCase();
            if (ImageFileExtend.indexOf(fileExtend) > -1) {
                return true;
            }
            else {
                file.select();
                document.execCommand('Delete');
                file.blur();
                alert("Remind : Please upload (" + ImageFileExtend + ") format picture!");
                return false;
            }
        }
    },
    isInt: function (val)///验证正整数
    {
        var reg = new RegExp("^[0-9]*[1-9][0-9]*$");
        return reg.test(val);
    },
    isNumber: function (val) {
        var reg = new RegExp("^([0-9])[0-9]*(\.\d*)?$");
        return reg.test(val);
    },
    //验证非负整数（0+正整数）
    isNonNegativeInt: function (val) {
        var reg = new RegExp("^[1-9]\d*");
        return reg.test(val);
    }
    ,
    //验证非负整数（0+正整数）
    isIntAndZero: function (val) {
        var reg = new RegExp("^[1-9]\d*|0$");
        return reg.test(val);
    }

};



var CommonJS =
{
    //随机数
    RndNum: function(length) {
        var rnd = "";
        for (var i = 0; i < length; i++)
            rnd += Math.floor(Math.random() * 10);
        return rnd;
    },
    //进度条
    ProgressBar: function () {
        $("#progressBar1").show();
        var bodyTop = 0;
        if (typeof window.pageYOffset != 'undefined') {
            bodyTop = window.pageYOffset;
        } else if (typeof document.compatMode != 'undefined' && document.compatMode != 'BackCompat') {
            bodyTop = document.documentElement.scrollTop;
        } else if (typeof document.body != 'undefined') {
            bodyTop = document.body.scrollTop;
        }
        $("#progressBar1").css("top", 130 + bodyTop);
    },
    //弹出层
    Tanchuceng: function (objName, width, height) {
        $("#" + objName).show();
        var winWinth = $(window).width();
        var winHeight = $(document).height();
        var tanchuLeft = $(window).width() / 2 - width / 2;
        var tanchuTop = $(window).height() / 2 - height / 2 + $(window).scrollTop();
        $("#" + objName).css({ width: width, height: height, border: "1px #86A5AD solid", opacity: "1", filter: "alpha(opacity = 100)", left: tanchuLeft, top: tanchuTop, background: "#fff", position: "absolute" });
        $('[id=btnClose]').click(function () {
            $("#" + objName).hide();
        });
    },
    WinOpen: function (url, width, height) {
        window.open(url, "", "height=" + height + ",width=" + width + ",top=180,left=380,toolbar=no,menubar=no,scrollbars=no,resizable=no, location=no,status=no");
    },
    WinOpenModalDialog: function (url, parame, width, height) {
        var sEdge = "Raised";
        var bCenter = "yes";
        var bResize = "no";
        var bStatus = "yes";

        var par = "dialogHeight: " + height + "px; dialogWidth: " + width + "px; dialogTop: 110px; dialogLeft: 110px;center: " + bCenter + "; resizable: " + bResize + "; status: " + bStatus + "; scroll:auto;";

        if (parame != null && parame.length > 0) {
            return window.showModalDialog(url + "?" + parame, "", par);
        }
        else {
            return window.showModalDialog(url, "", par);
        }
    },
    OpenModalDialog: function (url, parame, width, height, Top, Left) {
        var sEdge = "Raised";
        var bCenter = "yes";
        var bResize = "no";
        var bStatus = "yes";

        var par = "dialogHeight: " + height + "px; dialogWidth: " + width + "px; dialogTop: " + Top + "px; dialogLeft: " + Left + "px;center: " + bCenter + "; resizable: " + bResize + "; status: " + bStatus + "; scroll:auto;";

        if (parame != null && parame.length > 0) {
            return window.showModalDialog(url + "?" + parame, "", par);
        }
        else {
            return window.showModalDialog(url, "", par);
        }
    },
    OpenWindows: function (url, width, height, top, left) {
        window.open(url, "", "height=" + height + ",width=" + width + ",top=" + top + ",left=" + left + ",toolbar=no,menubar=no,scrollbars=yes,resizable=yes, location=no,status=yes");
    },
    OpenNewWindow: function (url, name, width, height) {// 打开新窗口
        var newWin = null;
        newWin = window.open
        (
        url,
        name,
        "left=" + (screen.width - width) / 2 + ",top=" + (screen.height - height) / 2 + ",width=" + width + ",height=" + height +
        ",location=0,resizable=1,status=yes,titlebar=1,directories=0,toolbar=0,menubar=0,scrollbars=1"
        );
        if (newWin != null) {
            newWin.focus();
        }
    },
    InitYearSelect: function (dropDownListId, startYear, currentSelect) {
        var ddlYear = document.getElementById(dropDownListId);
        var lastYear = new Date().getFullYear() + 4;
        for (var firstYear = lastYear; firstYear >= startYear; firstYear--) {
            ddlYear.options.add(new Option(firstYear, firstYear));
        }
        if (currentSelect != null) {
            //ddlYear.value = lastYear;
            ddlYear.value = new Date().getFullYear();
        }
    }
};

//判断两个时间的前后
function CompareTime(beginTime, endTime, msg) {
    var beginTimes = beginTime.substring(0, 10).split('-');
    var endTimes = endTime.substring(0, 10).split('-');

    //parse方法要求短日期可以使用“/”或“-”作为分隔符，但是必须用月/日/年的格式来表示
    //使用"/"兼容IE和Firefox
    var beginTime2 = beginTimes[1] + '/' + beginTimes[2] + '/' + beginTimes[0] + '/ ' + beginTime.substring(10, 19);
    var endTime2 = endTimes[1] + '/' + endTimes[2] + '/' + endTimes[0] + '/ ' + endTime.substring(10, 19);

    var a = (Date.parse(endTime2) - Date.parse(beginTime2)) / 3600 / 1000;
    if (a >= 0) {
        return true;
    }
    else {
        alert(msg);
        return false;
    }

}

function formatDate(date, format) {
    if (!date) {
        return;
    }
    if (!format) {
        format = "yyyy-MM-dd";
    }
    switch (typeof date) {
        case "string":
            date = new Date(date.replace(/-/, "/"));
            break;
        case "number":
            date = new Date(date);
            break;
    }
    if (!date instanceof Date) {
        return;
    }
    var dict =
    {
        "yyyy": date.getFullYear(),
        "M": date.getMonth() + 1,
        "d": date.getDate(),
        "H": date.getHours(),
        "m": date.getMinutes(),
        "s": date.getSeconds(),
        "MM": ("" + (date.getMonth() + 101)).substr(1),
        "dd": ("" + (date.getDate() + 100)).substr(1),
        "HH": ("" + (date.getHours() + 100)).substr(1),
        "mm": ("" + (date.getMinutes() + 100)).substr(1),
        "ss": ("" + (date.getSeconds() + 100)).substr(1)
    };
    return format.replace(/(yyyy|MM?|dd?|HH?|ss?|mm?)/g, function () {
        return dict[arguments[0]];
    });
}

String.prototype.Trim = function() {
    return this.replace(/(^\s*)|(\s*$)/g, "");
};
String.prototype.LTrim = function() {
    return this.replace(/(^\s*)/g, "");
};
String.prototype.Rtrim = function() {
    return this.replace(/(\s*$)/g, "");
};

/***************输入数字带小数点*******************/
function IsNumberOn(obj) {
    var ie = navigator.appName == "Microsoft Internet Explorer" ? true : false;
    if (ie) {
        if (window.event != null) {
            if (window.event.ctrlKey || window.event.shiftKey || window.event.altKey) {
                return false;
            }
            else {
                if (!(((window.event.keyCode >= 48) && (window.event.keyCode <= 57))
                || (window.event.keyCode == 13) || (window.event.keyCode == 46)
                || (window.event.keyCode == 45) || (window.event.keyCode == 8)
                || (window.event.keyCode == 136) || (window.event.keyCode == 190)
                || ((window.event.keyCode >= 96) && (window.event.keyCode <= 105))
                || (window.event.keyCode == 110))) {
                    window.event.keyCode = 0;
                }
            }
        }
    }
    else {
        if (event.which != null) {
            if (!(((window.event.which >= 48) && (window.event.which <= 57))
            || (window.event.which == 13) || (window.event.which == 46)
            || (window.event.which == 45) || (window.event.which == 8)
            || (window.event.which == 136) || (window.event.which == 190)
            || ((window.event.which >= 96) && (window.event.which <= 105))
            || (window.event.which == 110))) {
                window.event.keyCode = 0;
            }
        }
    }

};

//多语言界面用到的多语言验证
function ValidateLanguage() {
    var lan = "";
    $('input[lan="1"]').each(function (index, item) {
        if (item.value.replace('\s+\g', '') != '')
            lan += item;
    });

    if (lan == '') {
        alert('Remind : Please set a language at least !');
        $('input[lan="1"]').first().focus();
        return false;
    }

    return true;
}

//判断文本框是否有非法字符
function isValidStringFormInput(messsage) {
    $("input:text").focusout(function () {
        var patrn = /[`<>']/im;
        if (patrn.test($.trim($(this).val()))) {
            alert(messsage);
            $(this).val("");
            $(this).focus();
            return false;
        }
    });
}
//温馨提示：您输入的数据含有敏感字符！
//$(document).ready(function () { isValidStringFormInput('Remind : The data you entered contain illegal characters !'); });
$(document).ready(function () { isValidStringFormInput("温馨提示：您输入的数据可能含有(~、<、>、')敏感字符！"); });

//获取cookie值   
function getMenuCookie(keyCode) {
    // 首先我们检查下cookie是否存在.   
    // 如果不存在则document.cookie的长度为0   
    if (document.cookie.length > 0) {
        // 接着我们检查下cookie的名字是否存在于document.cookie   
        // 因为不止一个cookie值存储,所以即使document.cookie的长度不为0也不能保证我们想要的名字的cookie存在   
        //所以我们需要这一步看看是否有我们想要的cookie   
        //如果begin的变量值得到的是-1那么说明不存在   
        var begin = document.cookie.indexOf(keyCode + "=");
        if (begin != -1) {
            // 说明存在我们的cookie.  
            begin += keyCode.length + 1; //cookie值的初始位置   
            var end = document.cookie.indexOf(";", begin); //结束位置   
            if (end == -1) end = document.cookie.length; //没有;则end为字符串结束位置   
            return unescape(document.cookie.substring(begin, end));
        }
    }
    return "";
}

//为数字添加千分符
function comdify(n) {
    var re = /\d{1,3}(?=(\d{3})+$)/g;
    var n1 = n.toString().replace(/^(\d+)((\.\d+)?)$/, function (s, s1, s2) { return s1.replace(re, "$&,") + s2; });
    return n1;
}

//对HTML代码进行编码
function htmlencode(s) {
    var div = document.createElement('div');
    div.appendChild(document.createTextNode(s));
    return div.innerHTML;
}

//对HTML编码进行解码
function htmldecode(s) {
    var div = document.createElement('div');
    div.innerHTML = s;
    return div.innerText || div.textContent;
}