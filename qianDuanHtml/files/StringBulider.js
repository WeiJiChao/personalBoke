function StringBuilder(value) {
	this.strings = new Array("");
	this.append(value);
};
StringBuilder.prototype.append = function(value) {
	if (value)
		this.strings.push(value);
};
StringBuilder.prototype.clear = function() {
	this.strings.length = 1;
}
StringBuilder.prototype.toString = function() {
	return this.strings.join("");
}

String.prototype.replaceAll = function(reallyDo, replaceWith, ignoreCase) {
	if (!RegExp.prototype.isPrototypeOf(reallyDo)) {
		return this.replace(new RegExp(reallyDo, (ignoreCase ? "gi" : "g")), replaceWith);
	} else {
		return this.replace(reallyDo, replaceWith);
	}
}
String.prototype.repacefromurlcode = function() {
	return this.replaceAll("%3A", ":").replaceAll("%2B", "+").replaceAll("%2C", ",").replaceAll("%2F", "/").replaceAll("%3D", "=").replaceAll("%3B", ";").replaceAll("&#034;", '"').replaceAll("%23", '#').replaceAll("&lt;", '<').replaceAll("&gt;", '>').replaceAll("&amp;", '&');
}

/**
 * 参数说明： 根据长度截取先使用字符串，超长部分追加… str 对象字符串 len 目标字节长度 返回值： 处理结果字符串
 */
String.prototype.cutString = function(len, isadd) {
	// length属性读出来的汉字长度为1
	// if(str.length*2 <= len) {
	// return str;
	// }
	var str = this;
	var strlen = 0;
	var s = "";
	for (var i = 0; i < str.length; i++) {
		s = s + str.charAt(i);
		if (str.charCodeAt(i) > 128) {
			strlen = strlen + 2;
			if (strlen >= len) {
				return s.substring(0, s.length - 1) + (isadd ? "..." : "");
			}
		} else {
			strlen = strlen + 1;
			if (strlen >= len) {
				return s.substring(0, s.length - 2) + (isadd ? "..." : "");
			}
		}
	}
	return s;
}
function StringcutString(strobj, len, isadd) {
	// length属性读出来的汉字长度为1
	// if(str.length*2 <= len) {
	// return str;
	// }
	strobj=strtodafaultstr(strobj, "");
	var str = strobj.toString();
	var strlen = 0;
	var s = "";
	for (var i = 0; i < str.length; i++) {
		s = s + str.charAt(i);
		if (str.charCodeAt(i) > 128) {
			strlen = strlen + 2;
			if (strlen >= len) {
				return s.substring(0, s.length - 1) + (isadd ? "..." : "");
			}
		} else {
			strlen = strlen + 1;
			if (strlen >= len) {
				return s.substring(0, s.length - 2) + (isadd ? "..." : "");
			}
		}
	}
	return s;
}
function StringHideString(strobj,prefixLength,lastFixLength,hideStr) {
	strobj=strtodafaultstr(strobj, "");
	var str = strobj.toString();
	var strlen = 0;
	var s = "";
	for (var i = 0; i < str.length; i++) {
		if (i <prefixLength) {
			s = s + str.charAt(i);
		}else if (i >(str.length - lastFixLength - 1)) {
			s = s + str.charAt(i);
		}else{
			s = s + hideStr
		}
	}
	return s;
}
String.prototype.cutString2 = function(len, isadd) {
	var str = this;
	var strlen = 0;
	var s = "";
	for (var i = 0; i < str.length; i++) {
		if (str.charCodeAt(i) > 128) {
			strlen = strlen + 2;
			if (strlen >= len) {
				return "";
			}
		} else {
			strlen = strlen + 1;
			if (strlen >= len) {
				return "";
			}
		}
	}
	for (var i = strlen; i < len; i++) {
		s = s + " &nbsp;";
	}
	return s;
}
// 判断字符串是否为空，为空则转为空字符串
function jsonobjisnull(jsonobj) {
	return (typeof(jsonobj) == undefined || typeof(jsonobj) == "undefined" || jsonobj == null || jsonobj == "null");
}
// 判断字符串是否为空，为空则转为空字符串
String.prototype.strsubnull = function() {
	var str = this;
	return (str == null || str == "null" || typeof(str) == undefined || typeof(str) == "undefined" ? "" : str);
}
// 判断字符串是否为空，为空则转为0
String.prototype.strtonumstr = function() {
	var str = this;
	return (str == null || str == "null" || str == "" || typeof(str) == undefined || typeof(str) == "undefined" ? "0" : str);
}
// 判断字符串是否为空，为空则转为0
String.prototype.strtodafaultstr = function(defaultstr) {
	var str = this;
	return (str == null || str == "null" || str == "" || typeof(str) == undefined || typeof(str) == "undefined" ? defaultstr : str);
}
function strtodafaultstr(str, defaultstr) {
	return (str == null || str == "null" || str == "" || typeof(str) == undefined || typeof(str) == "undefined" || str == "undefined" ? defaultstr : str);
}
function objisnull(obj) {
	return (obj == null || obj == "null" || obj == "" || typeof(obj) == undefined || typeof(obj) == "undefined");
}
function objisnull2(obj) {
	return (obj == null || obj == "null"|| typeof(obj) == undefined || typeof(obj) == "undefined");
}
String.prototype.strtodate = function() {
	// length属性读出来的汉字长度为1
	// if(str.length*2 <= len) {
	// return str;
	// }
	var str = this;
	var arr1 = str.split(' ');
	var arr1 = arr1[0].split('-');

	return new Date(arr1[0], arr1[1] - 1, arr1[2]);
}
String.prototype.datestrformat = function(format) {
	// length属性读出来的汉字长度为1
	// if(str.length*2 <= len) {
	// return str;
	// }
	var str = this;
	str = str.replace("+", " ");
	var arr1 = str.split(' ');
	var arr1 = arr1[0].split('-');
	var date1 = new Date(arr1[0], arr1[1] - 1, arr1[2]);
	return date1.format(format);
}

changeTwoDecimal_f = function(floatvar) {
	var f_x = parseFloat(floatvar);
	if (isNaN(f_x)) {
		alert('function:changeTwoDecimal->parameter error');
		return false;
	}
	var f_x = Math.round(f_x * 100) / 100;
	var s_x = f_x.toString();
	var pos_decimal = s_x.indexOf('.');
	if (pos_decimal < 0) {
		pos_decimal = s_x.length;
		s_x += '.';
	}
	while (s_x.length <= pos_decimal + 2) {
		s_x += '0';
	}
	return s_x;
}
Date.prototype.format = function(format) {
	var o = {
		"M+" : this.getMonth() + 1, // month
		"d+" : this.getDate(), // day
		"h+" : this.getHours(), // hour
		"m+" : this.getMinutes(), // minute
		"s+" : this.getSeconds(), // second
		"q+" : Math.floor((this.getMonth() + 3) / 3), // quarter
		"S" : this.getMilliseconds()
		// millisecond
	}

	if (/(y+)/.test(format)) {
		format = format.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
	}

	for (var k in o) {
		if (new RegExp("(" + k + ")").test(format)) {
			format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k] : ("00" + o[k]).substr(("" + o[k]).length));
		}
	}
	return format;
}

function getsubtimetonow(strdate, strtype) {
	var d1 = new Date();
	var strdate = (typeof(strdate) == undefined || typeof(strdate) == "undefined" || strdate == null || strdate == "null" || strdate == "" ? "" : strdate);
	var d2=stringToDate(strdate);
	if (d2 == null || d2 == 'Invalid Date' || d1 > d2) {
		return "";
		// var d3=d1;
		// d1=d2;
		// d2=d3;
	}

	if (strtype == "d") {
		return Math.ceil((d2.getTime() - d1.getTime()) / (1000 * 3600 * 24)) + "天";
	} else if (strtype == "y") {
		return Math.ceil((d2.getTime() - d1.getTime()) / (1000 * 3600 * 24 * 365)) + "年";
	} else if (strtype == "h") {
		return Math.ceil((d2.getTime() - d1.getTime()) / (1000 * 3600)) + "小时";
	} else if (strtype == "m") {
		return Math.ceil((d2.getTime() - d1.getTime()) / (1000 * 60)) + "分";
	} else if (strtype == "all") {
		var year = Math.floor((d2.getTime() - d1.getTime()) / (1000 * 3600 * 24 * 365));
		var day = Math.floor((d2.getTime() - d1.getTime() - year * 1000 * 3600 * 24 * 365) / (1000 * 3600 * 24));
		var h = Math.floor((d2.getTime() - d1.getTime() - year * 1000 * 3600 * 24 * 365 - day * 1000 * 3600 * 24) / (1000 * 3600));
		var m = Math.floor((d2.getTime() - d1.getTime() - year * 1000 * 3600 * 24 * 365 - day * 1000 * 3600 * 24 - h * 1000 * 3600) / (1000 * 60));
		var rtn = "";
		if (year != 0) {
			rtn = year + "年";
		}
		if (day != 0) {
			rtn += day + "天";
		}
		if (h != 0) {
			rtn += h + "小时";
		}
		if (m != 0) {
			rtn += m + "分";
		}
		return rtn;
	}
}

function getSubDayBetweenTwoDateStr(strdateFrom, strdateTo) {
	if(typeof(strdateFrom) == undefined || typeof(strdateFrom) == "undefined" || strdateFrom == null || strdateFrom == "null" || strdateFrom == ""||
		typeof(strdateTo) == undefined || typeof(strdateTo) == "undefined" || strdateTo == null || strdateTo == "null" || strdateTo == ""){
		return -1;
	}
	var d1=stringToDate(strdateFrom);
	var d2=stringToDate(strdateTo);
	if (d1 == null || d1 == 'Invalid Date' || d2 == null || d2 == 'Invalid Date' || d1 > d2) {
		return -2;
	}
	return Math.ceil((d2.getTime() - d1.getTime()) / (1000 * 3600 * 24)) ;
}

function getdatetimefirst(strdate) {
	strdate = jsonDateFormat(strdate);
	return strdate.split(' ')[0];
}
/**
 * 计算字符长度
 */
var sizeof = function(str, charset) {
	var total = 0, charcode, i, len;
	charset = charset ? charset.toLowerCase() : '';
	if (charset == 'utf-16' || charset == 'utf16') {
		for (i = 0, len = str.length; i < len; i++) {
			charcode = str.charCodeAt(i);
			if (charcode <= 0xffff) {
				total += 2;
			} else {
				total += 4;
			}
		}
	} else {
		for (i = 0, len = str.length; i < len; i++) {
			charcode = str.charCodeAt(i);
			if (charcode <= 0x007f) {
				total += 1;
			} else if (charcode <= 0x07ff) {
				total += 2;
			} else if (charcode <= 0xffff) {
				total += 3;
			} else {
				total += 4;
			}
		}
	}
	return total;
}

function jsonDateFormat(jsonDate) {// json日期格式转换为正常格式
	try {// 出自http://www.cnblogs.com/ahjesus 尊重作者辛苦劳动成果,转载请注明出处,谢谢!
		var strdate = (typeof(jsonDate) == undefined || typeof(jsonDate) == "undefined" || jsonDate == null || jsonDate == "null" || jsonDate == "" ? "" : jsonDate);
		if (strdate == "")
			return "";
		var date;
		try {
			strdate = strdate.replace("+", " ");
			if (strdate.split(".").length > 1) {
				strdate = strdate.split(".")[0];
			}
			date = new Date(Date.parse(strdate.replace(/-/g, "/")));
		} catch (e) {
			try {
				strdate = strdate.date.replace("+", " ");
				if (strdate.split(".").length > 1) {
					strdate = strdate.split(".")[0];
				}
				date = new Date(Date.parse(strdate.replace(/-/g, "/")));
			} catch (e2) {
				try {
					date = new Date(strdate.time);
				} catch (e3) {
					alert(e3)
				}
			}
		}
		var month = date.getMonth() + 1 < 10 ? "0" + (date.getMonth() + 1) : date.getMonth() + 1;
		var day = date.getDate() < 10 ? "0" + date.getDate() : date.getDate();
		var hours = date.getHours();
		var minutes = date.getMinutes();
		var seconds = date.getSeconds();
		var milliseconds = date.getMilliseconds();
		return date.getFullYear() + "-" + month + "-" + day + " " + hours + ":" + minutes + ":" + seconds + "." + milliseconds;
	} catch (ex) {// 出自http://www.cnblogs.com/ahjesus 尊重作者辛苦劳动成果,转载请注明出处,谢谢!
		alert(ex);
		return "";
	}
}
function jsonDateFormat2(jsonDate) {// json日期格式转换为正常格式
	try {// 出自http://www.cnblogs.com/ahjesus 尊重作者辛苦劳动成果,转载请注明出处,谢谢!
		var strdate = (typeof(jsonDate) == undefined || typeof(jsonDate) == "undefined" || jsonDate == null || jsonDate == "null" || jsonDate == "" ? "" : jsonDate);
		if (strdate == "")
			return "";
		var date;
		try {
			strdate = strdate.replace("+", " ");
			if (strdate.split(".").length > 1) {
				strdate = strdate.split(".")[0];
			}
			date = new Date(Date.parse(strdate.replace(/-/g, "/")));
		} catch (e) {
			try {
				strdate = strdate.date.replace("+", " ");
				if (strdate.split(".").length > 1) {
					strdate = strdate.split(".")[0];
				}
				date = new Date(Date.parse(strdate.replace(/-/g, "/")));
			} catch (e2) {
				try {
					date = new Date(strdate.time);
				} catch (e3) {
					alert(e3)
				}
			}
		}
		var month = date.getMonth() + 1 < 10 ? "0" + (date.getMonth() + 1) : date.getMonth() + 1;
		var day = date.getDate() < 10 ? "0" + date.getDate() : date.getDate();
		var hours = date.getHours() < 10 ? "0" + date.getHours() : date.getHours();;
		var minutes = date.getMinutes() < 10 ? "0" + date.getMinutes() : date.getMinutes();;
		var seconds = date.getSeconds() < 10 ? "0" + date.getSeconds() : date.getSeconds();;
		var milliseconds = date.getMilliseconds();
		return date.getFullYear() + "-" + month + "-" + day + " " + hours + ":" + minutes + ":" + seconds;
	} catch (ex) {// 出自http://www.cnblogs.com/ahjesus 尊重作者辛苦劳动成果,转载请注明出处,谢谢!
		alert(ex);
		return "";
	}
}
function stringToDate(strdate){
	var date;
	try {
		strdate = strdate.replace("+", " ");
		if (strdate.split(".").length > 1) {
			strdate = strdate.split(".")[0];
		}
		date = new Date(Date.parse(strdate.replace(/-/g, "/")));
	} catch (e) {
		try {
			strdate = strdate.date.replace("+", " ");
			if (strdate.split(".").length > 1) {
				strdate = strdate.split(".")[0];
			}
			date = new Date(Date.parse(strdate.replace(/-/g, "/")));
		} catch (e2) {
			try {
				date = new Date(strdate.time);
			} catch (e3) {
			}
		}
	}
	return date;
}

function formatDateLong(time, format){
	var t = new Date(time);
	var tf = function(i){return (i < 10 ? '0' : '') + i};
	return format.replace(/yyyy|MM|dd|HH|mm|ss/g, function(a){
		switch(a){
			case 'yyyy':
				return tf(t.getFullYear());
				break;
			case 'MM':
				return tf(t.getMonth() + 1);
				break;
			case 'mm':
				return tf(t.getMinutes());
				break;
			case 'dd':
				return tf(t.getDate());
				break;
			case 'HH':
				return tf(t.getHours());
				break;
			case 'ss':
				return tf(t.getSeconds());
				break;
		};
	});
};
function formatDateShortLong(time, format){
	var t = new Date(parseInt(time)*1000);
	var tf = function(i){return (i < 10 ? '0' : '') + i};
	return format.replace(/yyyy|MM|dd|HH|mm|ss/g, function(a){
		switch(a){
			case 'yyyy':
				return tf(t.getFullYear());
				break;
			case 'MM':
				return tf(t.getMonth() + 1);
				break;
			case 'mm':
				return tf(t.getMinutes());
				break;
			case 'dd':
				return tf(t.getDate());
				break;
			case 'HH':
				return tf(t.getHours());
				break;
			case 'ss':
				return tf(t.getSeconds());
				break;
		};
	});
};

function myround(v, e) {
	var t = 1;
	for (; e > 0; t *= 10, e--);
	for (; e < 0; t /= 10, e++);
	return Math.round(v * t) / t;
}
/**
 * 复制到剪贴板
 * 
 * @param {Object}
 *            copyText
 */
function copy2clipboard(copyText) {
	if (window.clipboardData) {
		alert("1");
		window.clipboardData.setData("Text", copyText)
	} else {
		alert("2");
		var flashcopier = 'flashcopier';
		if (!document.getElementById(flashcopier)) {
			var divholder = document.createElement('div');
			divholder.id = flashcopier;
			document.body.appendChild(divholder);
		}
		document.getElementById(flashcopier).innerHTML = '';
		var divinfo = '<embed src="../js/_clipboard.swf" FlashVars="clipboard=' + encodeURIComponent(copyText) + '" width="0" height="0" type="application/x-shockwave-flash"></embed>';
		document.getElementById(flashcopier).innerHTML = divinfo;
	}
	alert('copy成功！');
}
function copyToClipBoard(s) {
	alert(s);
	if (window.clipboardData) {
		alert(1);
		window.clipboardData.setData("Text", s);
		alert("已经复制到剪切板！" + "\n" + s);
	} else if (navigator.userAgent.indexOf("Opera") != -1) {
		alert(2);
		window.location = s;

	} else if (window.netscape) {
		alert(3);
		try {
			netscape.security.PrivilegeManager.enablePrivilege("UniversalXPConnect");
		} catch (e) {
			alert("被浏览器拒绝！\n请在浏览器地址栏输入'about:config'并回车\n然后将'signed.applets.codebase_principal_support'设置为'true'");
		}
		var clip = Components.classes['@mozilla.org/widget/clipboard;1'].createInstance(Components.interfaces.nsIClipboard);
		if (!clip)
			return;
		var trans = Components.classes['@mozilla.org/widget/transferable;1'].createInstance(Components.interfaces.nsITransferable);
		if (!trans)
			return;
		trans.addDataFlavor('text/unicode');
		var str = new Object();
		var len = new Object();
		var str = Components.classes["@mozilla.org/supports-string;1"].createInstance(Components.interfaces.nsISupportsString);
		var copytext = s;
		str.data = copytext;
		trans.setTransferData("text/unicode", str, copytext.length * 2);
		var clipid = Components.interfaces.nsIClipboard;
		if (!clip)
			return false;
		clip.setData(trans, null, clipid.kGlobalClipboard);
		alert("已经复制到剪切板！" + "\n" + s)
	}
}

/**
 * 字符串左侧截取
 */
function left(mainStr, lngLen) {
	if (lngLen > 0) {
		return mainStr.substring(0, lngLen)
	} else {
		return null
	}
}
/**
 * 字符串右侧截取
 */
function right(mainStr, lngLen) {
	if (mainStr.length - lngLen >= 0 && mainStr.length >= 0 && mainStr.length - lngLen <= mainStr.length) {
		return mainStr.substring(mainStr.length - lngLen, mainStr.length)
	} else {
		return null
	}
}
/**
 * 字符串截取
 */
function mid(mainStr, starnum, endnum) {
	if (mainStr.length >= 0) {
		return mainStr.substr(starnum, endnum)
	} else {
		return null
	}
}

////页面加载完成
//$(document).ready(function(){
//    //禁止退格键 作用于Firefox、Opera
//    document.onkeypress = banBackSpace;
//    //禁止退格键 作用于IE、Chrome
//    document.onkeydown = banBackSpace;
//});
//处理键盘事件 禁止后退键（Backspace）密码或单行、多行文本框除外 
//function banBackSpace(e){
//    //alert(event.keyCode)
//    var ev = e || window.event;//获取event对象
//    var obj = ev.target || ev.srcElement;//获取事件源
//    var t = obj.type || obj.getAttribute('type');//获取事件源类型
//    //获取作为判断条件的事件类型
//    var vReadOnly = obj.readOnly;
//    var vDisabled = obj.disabled;
//    //处理undefined值情况
//    vReadOnly = (vReadOnly == undefined) ? false : vReadOnly;
//    vDisabled = (vDisabled == undefined) ? true : vDisabled;
//    //当敲Backspace键时，事件源类型为密码或单行、多行文本的，
//    //并且readOnly属性为true或disabled属性为true的，则退格键失效
//    var flag1 = ev.keyCode == 8 && (t == "password" || t == "text" || t == "textarea") && (vReadOnly == true || vDisabled == true);
//    //当敲Backspace键时，事件源类型非密码或单行、多行文本的，则退格键失效
//    var flag2 = ev.keyCode == 8 && t != "password" && t != "text" && t != "textarea";
//    //判断
//    if (flag2 || flag1)
//        event.returnValue = false;//这里如果写 return false 无法实现效果
//}
//删除某一项元素
Array.prototype.del=function(n) {　//n表示第几项，从0开始算起。
//prototype为对象原型，注意这里为对象增加自定义方法的方法。
	if(n<0)　//如果n<0，则不进行任何操作。
		return this;
	else
		return this.slice(0,n).concat(this.slice(n+1,this.length));
	/*
	 　　　concat方法：返回一个新数组，这个新数组是由两个或更多数组组合而成的。
	 　　　　　　　　　这里就是返回this.slice(0,n)/this.slice(n+1,this.length)
	 　　 　　　　　　组成的新数组，这中间，刚好少了第n项。
	 　　　slice方法： 返回一个数组的一段，两个参数，分别指定开始和结束的位置。
	 　　*/
}
//删除全部元素
Array.prototype.remove=function() {
	this.splice(0,this.length);
}

/**
 * 根据像素截取字符串
 * @param strObj 字符串
 * @param width  宽度
 * @param fontSize 字符大小
 * @param isAdd  是否加
 * @returns {*}
 * @constructor
 */
function StringCutStringffsetWidth(strObj,width,fontSize,isAdd) {
	strObj=strtodafaultstr(strObj, "");
	var str = strObj.toString();
	var s = "";
	for (var i = 0; i < str.length; i++) {
		s = s + str.charAt(i);
		if (str.charCodeAt(i) > 128) {
			if((s+ (isAdd ? "..." : "")).getWidth(fontSize)>width-(parseInt(fontSize)/2)){
				return s.substring(0, s.length - 1) + (isAdd ? "..." : "");
			}
		} else {
			if((s+ (isAdd ? "..." : "")).getWidth(fontSize)>width-(parseInt(fontSize)/2)){
				return s.substring(0, s.length - 2) + (isAdd ? "..." : "");
			}
		}
	}
	return s;
}

function StringSplitThenConcat(strObj,width,fontSize,isConcat) {
	strObj=strtodafaultstr(strObj, "");
	var strSource = strObj.toString();
	var strList=strSource.split(/[\r\n]/g);
	var rtnS="";
	for(var m=0;m<strList.length;m++){
		var str=strList[m];
		var s = "";
		for (var i = 0; i < str.length; i++) {
			s = s + str.charAt(i);
			if (str.charCodeAt(i) > 128) {
				if((s).getWidth(fontSize)>width-(parseInt(fontSize)/2)){
					rtnS+= s.substring(0, s.length - 1) + isConcat;
					s=s.substring(s.length-1, s.length)
				}
			} else {
				if((s).getWidth(fontSize)>width-(parseInt(fontSize)/2)){
					rtnS+= s.substring(0, s.length - 1) + isConcat;
					s=s.substring(s.length-1, s.length)
				}
			}
		}
		rtnS+=s;
		rtnS+="<br/>";
	}
	return rtnS;
}
/**
 * 获取所占像素
 * @param fontSize
 * @returns {number}
 */
String.prototype.getWidth = function(fontSize)
{
	var span = document.getElementById("__getwidth");
	if (span == null) {
		span = document.createElement("span");
		span.id = "__getwidth";
		document.body.appendChild(span);
		span.style.visibility = "hidden";
		span.style.whiteSpace = "nowrap";
	}
	span.innerText = this;
	span.style.fontSize = fontSize + "px";

	return span.offsetWidth;
}
// 将 1,2,3,4,转为A,B,C,D。26 -Z，27-AA，28-AB
function numToEnglishChar(i){
	i = parseInt(i);
	if(i==0){
		return "Z";
	}else if(i <= 26){
		return String.fromCharCode(i + 64);
	}else{
		if(i % 26 == 0){
			return numToEnglishChar((i-1) / 26) + numToEnglishChar(i % 26 );
		}else {
			return numToEnglishChar(i / 26) + numToEnglishChar(i % 26);
		}
	}
}

function getSubDayBetweenTwoDateStr2(strdateFrom, strdateTo) {
	if(typeof(strdateFrom) == undefined || typeof(strdateFrom) == "undefined" || strdateFrom == null || strdateFrom == "null" || strdateFrom == ""||
		typeof(strdateTo) == undefined || typeof(strdateTo) == "undefined" || strdateTo == null || strdateTo == "null" || strdateTo == ""){
		return -1;
	}
	var d1=stringToDate(strdateFrom);
	var d2=stringToDate(strdateTo);
	if (d1 == null || d1 == 'Invalid Date' || d2 == null || d2 == 'Invalid Date' || d1 > d2) {
		return -2;
	}
	return Math.floor((d2.getTime() - d1.getTime()) / (1000 * 3600 * 24))+1 ;
}