let index = {
	
	init:function(){
		$("#btn-save").on("click",()=> {
			this.save();
		});
	},
	save:function(){
		let data = {
			username:$("#username").val(),
			password:$("#password").val(),
			email:$("#email").val()
		};
		
	 $.ajax({
			type : "POST",
			url : "/blog/api/user",
			data : JSON.stringify(data),
			contentType: "application/json; charset=utf-8",
			dataType : "json"
		}).done(function(resp){
			alert("회원가입잉완료");
		console.log(resp);
			location.href = "/blog";
		}).fail(function(error){
			alert(JSON.stringify(error));
		});
		

	}
};

index.init();