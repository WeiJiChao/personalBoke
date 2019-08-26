
function login() {
// 获取用户名称 和密码的value
  var userName = $("input[type='text']").val();
  var passWord = $("#u13_input").val();

  var url = "http://49.234.232.172:8081/myBlog/login/login";
   
  $.ajax({
  	url : url,
  	type: "post",
  	data :JSON.stringify({"userName":userName,"passWord":passWord}),
  	contentType: "application/json;charset=utf-8",
  	dataType : "json",
  	success : function(data){
  		if (data) {
  			// 跳轉到博客的页面中
		window.location.href="/htmlTestSee/boke.html";
  		}else{
        alert("用户名或者密码错误");
      }
  	},
  	error : function(e){
  		alert("请求失败，想想办法");
  	}
  });
}


       