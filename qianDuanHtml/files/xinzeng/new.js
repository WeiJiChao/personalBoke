
//新增博客
function newBoke(){

  var bokeTitle = $("#bokeTitle").val();
  var bokeBody = $("#bokeBody").val();

  if (bokeTitle == ""){
      alert("请输入标题,至少一个字吧");
  }
    if (bokeBody == ""){
        alert("请输入内容,至少一个字吧");
    }

   // var url = "http://49.234.232.172:8081/myBlog/boKe/add";
   var url = "http://192.168.50.86:8090/myBlog/boKe/add";
     $.ajax({
     url : url,
     cache: false,
     type: "post",
    data :JSON.stringify({"bokeTitle":bokeTitle,"bokeBody":bokeBody}),
    contentType: "application/json;charset=utf-8",
    dataType : "json",
    success : function(data){
        alert("完美的增加好了666");
         // window.location.href="/htmlTestSee/boke.html";
         window.location.href="/qianDuanHtml/boke.html";

    },
    error : function(e){
      alert("新增错误，失败是成功他妈");
    }
  });
}

// 返回
function fanhui(){
    // window.location.href="/htmlTestSee/boke.html";
    window.location.href="/qianDuanHtml/boke.html";
}





 


 

  


       