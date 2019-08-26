// 进来应该有一个当下就访问的列表的接口
 $(document).ready(function() {
          list();   
 });


function list(){
   var url = "http://49.234.232.172:8081/myBlog/boKe/list";
     $.ajax({
     url : url,
     cache: false,
     type: "post",
    success : function(data){        
          //放到列表中去
          insertTable(data); 
    },
    error : function(e){
      alert("列博查看错误，失败是成功Tama");
    }
  });
}

//新增博客
function newBoKeTiaoZhuan() {  

  window.location.href="/htmlTestSee/new.html";
}

// 插入到列表中去
function insertTable(data){
    var selectString = new StringBuilder();
    var pageString = new StringBuilder();
    var tableAStr = new StringBuilder();
    
    $.each(data.list, function(i, data) {
         selectString.append('<tr>');
         selectString.append('<td align="center"><font size="2" color="yellow"><h1 style="font-family:YouYuan ">'+data.title+'</h1></font></td>');
         selectString.append('</tr>');

         selectString.append('<tr>');
         selectString.append('<td style="font-family:STKaiti  ">'+data.body+'</td>');
         selectString.append('</tr>');

          selectString.append('<tr>');
          selectString.append('<td style="color:green">'+"创建日期："+data.createDate);
          selectString.append('<button style="color:red; width:100px;margin-left:20px;cursor:pointer" onclick="deleteBoke(this)">');
          selectString.append('删除');
          selectString.append('<input type="hidden" value="'+data.id+'">');
          selectString.append('<input type="hidden" value="'+data.title+'">');
          selectString.append('</button>');
          selectString.append('</td>');
          selectString.append('</tr>');     
    });
      
    $("#bokelist").empty();
    $("#bokelist").append(selectString.toString());
  
   
}

  //删除博客
function deleteBoke(obj){
    var bokeid=$(obj).find('input:eq(0)').val();
     var title=$(obj).find('input:eq(1)').val();
    var flag = confirm("确定要删除" + title + "的信息吗?");
    if(flag){
      var url = "http://49.234.232.172:8081/myBlog/boKe/deleteBoKe";
      $.ajax({
          url : url,
          cache: false,
          type: "post",
          data :JSON.stringify({"bokeId":bokeid}),
          contentType: "application/json;charset=utf-8",
          dataType : "json",
          success : function(data){
             alert("已经丢进垃圾桶~~~");
            window.location.href="/htmlTestSee/boke.html";
          },
          error : function(e){
          alert("失敗是成功之母");
        }
      });
    }
}

  
