// 进来应该有一个当下就访问的列表的接口
$(document).ready(function() {
    list();
});


function list(){
    // var url = "http://49.234.232.172:8081/myBlog/boKe/list";
    var url = "http://192.168.50.86:8090/myBlog/boKe/list";
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

    // window.location.href="/htmlTestSee/new.html";
    window.location.href="/qianDuanHtml/new.html";
}

// 插入到列表中去
function insertTable(data){
    var selectString = new StringBuilder();
    var pageString = new StringBuilder();
    var tableAStr = new StringBuilder();

    $.each(data.list, function(i, data) {
        selectString.append('<tr>');


        selectString.append('<td align="left"><font size="2" color="#6666FF"  ><h1>');
        selectString.append('<a  onclick="bokeDetail(this)" style="cursor: pointer;">');
        selectString.append(data.title);
        selectString.append('<input type="hidden" value="'+data.id+'">');
        selectString.append('</a>');
        selectString.append('</h1></font></td>');
        selectString.append('</tr>');
        selectString.append('<tr>');
        selectString.append('</tr>');

        // 进来应该有一个当下就访问的列表的接口
        $(document).ready(function() {
            list();
        });


        function list(){
            // var url = "http://49.234.232.172:8081/myBlog/boKe/list";
            var url = "http://192.168.50.86:8090/myBlog/boKe/list";
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

            // window.location.href="/htmlTestSee/new.html";
            window.location.href="/qianDuanHtml/new.html";
        }

// 插入到列表中去
        function insertTable(data){
            var selectString = new StringBuilder();
            var pageString = new StringBuilder();
            var tableAStr = new StringBuilder();

            $.each(data.list, function(i, data) {
                selectString.append('<tr>');


                selectString.append('<td align="left"><font size="2" color="#6666FF"  ><h1>');
                selectString.append('<a  onclick="bokeDetail(this)" style="cursor: pointer;">');
                selectString.append(data.title);
                selectString.append('<input type="hidden" value="'+data.id+'">');
                selectString.append('</a>');
                selectString.append('</h1></font></td>');
                selectString.append('</tr>');
                selectString.append('<tr>');
                selectString.append('</tr>');
                selectString.append('<tr>');


                selectString.append('<td style="color:green" align="right">'+"创建日期："+data.createDate);
                selectString.append('<button style="color:red; width:100px;margin-left:20px;cursor:pointer" onclick="deleteBoke(this)">');
                selectString.append('删除');
                selectString.append('<input type="hidden" value="'+data.id+'">');
                selectString.append('<input type="hidden" value="'+data.title+'">');
                selectString.append('</button>');
                selectString.append('</td>');
                selectString.append('</tr>');
                // 换行
                selectString.append('<tr>');
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
                // var url = "http://49.234.232.172:8081/myBlog/boKe/deleteBoKe";
                var url = "http://192.168.50.86:8090/myBlog/boKe/deleteBoKe";
                $.ajax({
                    url : url,
                    cache: false,
                    type: "post",
                    data :JSON.stringify({"bokeId":bokeid}),
                    contentType: "application/json;charset=utf-8",
                    dataType : "json",
                    success : function(data){
                        alert("已经丢进垃圾桶~~~");
                        // window.location.href="/htmlTestSee/boke.html";
                        window.location.href="/qianDuanHtml/boke.html";
                    },
                    error : function(e){
                        alert("失敗是成功之母");
                    }
                });
            }
        }

// 博客详情
        function bokeDetail(obj) {
            var bokeid=$(obj).find('input:eq(0)').val();
            // 通過id去查找 这个博客的 具体的信息  跳转页面
            window.location.href = 'http://192.168.50.86:8090/qianDuanHtml/bokeDetail.html?id='+bokeid;
        }



        selectString.append('<td style="color:green" align="right">'+"创建日期："+data.createDate);
        selectString.append('<button style="color:red; width:100px;margin-left:20px;cursor:pointer" onclick="deleteBoke(this)">');
        selectString.append('删除');
        selectString.append('<input type="hidden" value="'+data.id+'">');
        selectString.append('<input type="hidden" value="'+data.title+'">');
        selectString.append('</button>');
        selectString.append('</td>');
        selectString.append('</tr>');
        // 换行
        selectString.append('<tr>');
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
        // var url = "http://49.234.232.172:8081/myBlog/boKe/deleteBoKe";
        var url = "http://192.168.50.86:8090/myBlog/boKe/deleteBoKe";
        $.ajax({
            url : url,
            cache: false,
            type: "post",
            data :JSON.stringify({"bokeId":bokeid}),
            contentType: "application/json;charset=utf-8",
            dataType : "json",
            success : function(data){
                alert("已经丢进垃圾桶~~~");
                // window.location.href="/htmlTestSee/boke.html";
                window.location.href="/qianDuanHtml/boke.html";
            },
            error : function(e){
                alert("失敗是成功之母");
            }
        });
    }
}

// 博客详情
function bokeDetail(obj) {
    var bokeid=$(obj).find('input:eq(0)').val();
    // 通過id去查找 这个博客的 具体的信息  跳转页面
    window.location.href = 'http://192.168.50.86:8090/qianDuanHtml/bokeDetail.html?id='+bokeid;
}

  
