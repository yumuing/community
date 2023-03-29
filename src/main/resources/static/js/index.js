$(function(){
	$("#publishBtn").click(publish);
});

function publish() {
	$("#publishModal").modal("hide");

	// 获取标题内容
	var title = $("#recipient-name").val();
	var content = $("#message-text").val();

	$.post(
		 "http://localhost:8888/discuss/add",
		{"title":title,"content":content},
		function (data){
			data = $.parseJSON(data);
			// 显示提示消息
			$("#hintBody").text(data.msg);
			// 显示提示框
			$("#hintModal").modal("show");
			// 2 秒后隐藏信息
			setTimeout(function(){
				$("#hintModal").modal("hide");
				// 刷新页面
				if (data.code == 200){
					window.location.reload();
				}
			}, 2000);
		}
	);


}