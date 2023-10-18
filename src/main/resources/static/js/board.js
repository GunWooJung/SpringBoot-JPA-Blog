let index = {
	
	init:function(){
		$("#btn-save").on("click",()=> {
			this.save();
		});
		$("#btn-delete").on("click",()=> {
			this.deleteById();
		});
	},
	save:function(){
		let data = {
			title:$("#title").val(),
			content:$("#content").val()
		};
		
	 $.ajax({
			type : "POST",
			url : "/api/board",
			data : JSON.stringify(data),
			contentType: "application/json; charset=utf-8",
			dataType : "json"
		}).done(function(resp){
			alert("글쓰기 완료");
		console.log(resp);
			location.href = "/";
		}).fail(function(error){
			alert(JSON.stringify(error));
		});
	},
	
	deleteById:function(){
	var id = $("#id").text(); 
	 $.ajax({
			type : "DELETE",
			url : "/api/board/"+id,
			dataType : "json"
		}).done(function(resp){
			alert("삭제 완료");
		console.log(resp);
			location.href = "/";
		}).fail(function(error){
			alert(JSON.stringify(error));
		});
	}
}

index.init();