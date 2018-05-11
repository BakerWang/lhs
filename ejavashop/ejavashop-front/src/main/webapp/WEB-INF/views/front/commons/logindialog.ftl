
<script type="text/javascript" src="${(domainUrlUtil.EJS_STATIC_RESOURCES)!}/js/layer_pop.js"></script>
<script>
	$(function(){
		$('#loginclose').on('click',function(){
			closeLayer('ui-dialog')
		});
		
		jQuery("#dialogLoginForm").validate({
			errorPlacement : function(error, element) {
				var obj = element.siblings(".tip").css('display', 'block')
						.find('p').addClass('error');
				error.appendTo(obj);
			},
	        rules : {
	            "name":{required:true},
	            "password":{required:true,passwordLength:true}
	        },
	        messages:{
	        	"name":{required:"请输入用户名"},
	        	"password":{required:"请输入密码"}
	        }
	    });
		
		$("#dialogLoginButton").click(function(){

			if ($("#dialogLoginForm").valid()) {
				$(".btn-danger").attr("disabled","disabled");
				var params = $('#dialogLoginForm').serialize();
				$.ajax({
					type:"POST",
					url:domain+"/dodialoglogin.html",
					dataType:"json",
					async : false,
					data : params,
					success:function(data){
						if(data.success){
							//jAlert("登录成功！");
							closeLayer('ui-dialog');
							location.reload();
						}else{
							jAlert(data.message);
							$(".btn-danger").removeAttr("disabled");
						}
					},
					error:function(){
						jAlert("异常，请重试！");
						$(".btn-danger").removeAttr("disabled");
					}
				});
			}
		});
	});
	
	function isUserLogin() {
		var isLogin = false;
		$.ajax({
			type:"POST",
			url:domain+"/isuserlogin.html",
			async : false,
			success:function(data){
				if(data.success){
					if (data.data) {
						isLogin = true;
					} else {
						isLogin = false;
					}
				}else{
					isLogin = false;
				}
			},
			error:function(){
				isLogin = false;
			}
		});
		return isLogin;
	}
</script>

<!-- 弹层 -->
<div class="ui-dialog" id="ui-dialog">
	<h2>
		<div class="fl">您尚未登陆</div>
		<div class="fr"><img src="${(domainUrlUtil.EJS_STATIC_RESOURCES)!}/img/dialog.png" alt="" id="loginclose"></div>
	</h2>
	<div class="signup-title">
		<h3>用户登录</h3>
	</div>
	<div class="signup-content">
		<form id="dialogLoginForm" class="form-horizontal">
		  	<div class="form-group">
			    <label class="form-label" for="inputUsernamel3">登录帐号：</label>
			    <div class="login-box">
			      	<input type="text" name="name" id="inputUsername3" class="form-control">
			      	<div class="tip">
				      	<p></p>
				    </div>
			    </div>
		  	</div>
		  	
		  	<div class="form-group">
			    <label class="form-label" for="inputPassword3">密码：</label>
			    <div class="login-box">
			      	<input type="password" name="password" id="inputPassword3" class="form-control">
			      	<div class="tip">
				      	<p></p>
				    </div>
			    </div>
		  	</div>
		  
		  	<div class="lg-tip clearfix">
		  		<a class="forget-password" href="${(domainUrlUtil.EJS_URL_RESOURCES)!}/forgetpassword.html">忘记密码？</a>
		  	</div>
		  	<div class="form-group mb0">
		  		<div style="margin-left:83px;">
		  			<a class="btn btn-danger" id="dialogLoginButton" href="javascript:void(0)">登录</a>
		  		</div>
		  	</div>
		  	<div class="form-group">
		  		<div class="inside-box">
		  			<span style="line-height:30px;">还不是本站会员？立即</span>
		  		</div>
		  		<a style="padding:3px 10px;" class="regists" href="${(domainUrlUtil.EJS_URL_RESOURCES)!}/register.html">注册</a>
		  	</div>
		</form>
	</div>
</div>
