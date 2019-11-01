// 进来应该有一个当下就访问的列表的接口
 $(document).ready(function() {
     var bokeId =  getUrlParam('id')
     // 根据这个id 查到具体的博客信息
     // var url = "http://192.168.50.86:8090/myBlog/boKe/detail";
     var url = "http://49.234.232.172:8081/myBlog/boKe/detail";
     $.ajax({
         url : url,
         cache: false,
         type: "post",
         data :JSON.stringify({"bokeId":bokeId}),
         contentType: "application/json;charset=utf-8",
         dataType : "json",
         success : function(data){
             // 插入到一个表格之中
             insertTable(data);
         },
         error : function(e){
             alert("失敗是成功之母");
         }
     });
 });


// 插入到列表中去
function insertTable(data){
    var selectString = new StringBuilder();
    var data = data.boKe;

        selectString.append('<tr>');
        selectString.append('<td align="center"><font size="2" color="#190000"><h1 style="font-family:YouYuan ">');
        selectString.append(data.title);
        selectString.append('</h1></font></td>');
        // selectString.append('<td style="color:#190000">'+data.createDate);
        // selectString.append('</td>');
        selectString.append('</tr>');
        // 换行
        selectString.append('<tr>');
        selectString.append('<td style="color:green">'+data.body);
        selectString.append('</tr>');

        $("#bokeDetail").empty();
        $("#bokeDetail").append(selectString.toString());


}

// 返回主页
function returnList(){
    // window.location.href="/qianDuanHtml/boke.html";
    window.location.href="/htmlTestSee/boke.html";
}


// 得到前端的传值参数id
    function getUrlParam(name) {
        var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
        var r = window.location.search.substr(1).match(reg);  //匹配目标参数
        if (r != null) return decodeURI(r[2]); return null; //返回参数值
    }





  
