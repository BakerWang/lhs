<!Doctype html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
		<meta name="viewport" content="width=device-width, initial-scale=1.0,minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
		<link  rel="stylesheet" href='${(domainUrlUtil.EJS_STATIC_RESOURCES)!}/css/bootstrap.min.css'>
		<link rel="stylesheet" type="text/css" href="${(domainUrlUtil.EJS_STATIC_RESOURCES)!}/css/user.css">
		<link rel="stylesheet" type="text/css" href="${(domainUrlUtil.EJS_STATIC_RESOURCES)!}/css/base.css">
		<link rel="stylesheet" type="text/css" href="${(domainUrlUtil.EJS_STATIC_RESOURCES)!}/css/register.css">
		<link rel="stylesheet" type="text/css" href="${(domainUrlUtil.EJS_STATIC_RESOURCES)!}/css/jquery.alerts.css"/>
		<script type="text/javascript" src='${(domainUrlUtil.EJS_STATIC_RESOURCES)!}/js/jquery-1.9.1.min.js'></script>
		<script type="text/javascript" src='${(domainUrlUtil.EJS_STATIC_RESOURCES)!}/js/bootstrap.min.js'></script>
		<script type="text/javascript" src='${(domainUrlUtil.EJS_STATIC_RESOURCES)!}/js/jquery.validate.min.js'></script>
		<script type="text/javascript" src='${(domainUrlUtil.EJS_STATIC_RESOURCES)!}/js/member/members.js'></script>
		<script type="text/javascript" src='${(domainUrlUtil.EJS_STATIC_RESOURCES)!}/js/common.js'></script>
		<script type="text/javascript" src="${(domainUrlUtil.EJS_STATIC_RESOURCES)!}/js/jquery.alerts.js"></script>
		<script type="text/javascript">
			var domain = '${(domainUrlUtil.EJS_URL_RESOURCES)!}';
		</script>
	</head>
	<body>
		<div class='head-layout'>
			<div class='container'>
				<div class='header-wrap'>
					<div class='snlogo'>
						<a href='${(domainUrlUtil.EJS_URL_RESOURCES)!}/index.html'>
							<img src='${(domainUrlUtil.EJS_STATIC_RESOURCES)!}/img/ejavashoplogo.png'>
						</a>
					</div>
				</div>
			</div>
		</div>
		<div class='nc-login-layout'>
			<div class='nc-signup'>
				<div class='signup-title'>
					<h3>忘记密码</h3>
				</div>
				<div class='signup-content'>
					<form class="form-horizontal" id='forgetform'>
					  	<div class="form-group">
						    <!-- <label for="inputUsernamel3" class="col-sm-3 control-label">登录帐号：</label>
						    <div class="col-sm-7">
						      	<input type="text" class="form-control" id="inputUsername3" name='name'>
						      	<div class='tip signup-message-email'>
					      			<p></p>
					      		</div>
						    </div> -->
						    
						    <label class="form-label" for="inputUsernamel3">登录帐号：</label>
						    <div class="login-box">
						      	<input type="text" name="name" id="inputUsername3" class="form-control">
						      	<div class="tip signup-message-email">
					      			<p></p>
					      		</div>
						    </div>
					  	</div>
					  	<div class="form-group reg-email reg-switch ">
						    <!-- <label for="inputEmail3" class="col-sm-3 control-label">电子邮箱：</label>
						    <div class="col-sm-7">
						      	<input type="text" class="form-control" id="inputEmail" name='email'>
						      	<p class='forget-tip'>电子邮箱错误</p>
						      	<div class='tip signup-message-email'>
					      			<p></p>
					      		</div>
						    </div> -->
						    
						    <label class="form-label" for="inputEmail3">电子邮箱：</label>
						    <div class="login-box">
						      	<input type="text" name="email" id="inputEmail" class="form-control">
						      	<p class="forget-tip">电子邮箱错误</p>
						      	<div class="tip signup-message-email">
					      			<p></p>
					      		</div>
						    </div>
					  	</div>
					  	<div class="form-group">
						    <!-- <label for="inputPasswordl3" class="form-label"> 验证码：</label>
						    <div class="col-sm-3">
						      	<input type="text" class="form-control" id="inputCode3" name="verifyCode">
						      	<div class='tip signup-message-code'>
						      		<p></p>
						      	</div>
						    </div>
						    <div class='fl' style='margin-left:2px;'>
						    	<img style="cursor:pointer;" src="${(domainUrlUtil.EJS_URL_RESOURCES)!}/verify.html" id="code_img" onclick="refreshCode();" width="59" height="27" />
						    	 <a href='javascript:void(0);' onclick="refreshCode();">看不清，换一张</a>
						    </div> -->
						    
						    <label class="form-label" for="inputPasswordl3"> 验证码：</label>
						    <div class="fl">
						      	<input type="text" name="verifyCode" id="inputCode3" class="form-control" style="width: 64px;">
						      	<div class="tip signup-message-code">
						      		<p></p>
						      	</div>
						    </div>
						    <div style="margin-left:2px;" class="fl">
							   	<img style="cursor:pointer;" src="${(domainUrlUtil.EJS_URL_RESOURCES)!}/verify.html" id="code_img" onclick="refreshCode();" width="59" height="27" />
						    	<a href='javascript:void(0);' onclick="refreshCode();">看不清，换一张</a>
						    </div>

					  	</div>
					  	<div class='form-group'>
					  		<label for="inputPasswordl3" class="form-label"> &nbsp;</label>
					  		<!-- <div class='col-sm-4 col-md-offset-3'> -->
					  		<div class=''>
					  			<!-- <a href='javascript:void(0)' id="forgetPasswordBtn" class='btn btn-danger2'>重置密码</a> -->
					  			<a href="javascript:void(0)" id="forgetPasswordBtn" class="btn btn-default">重置密码</a>
					  		</div>
					  	</div>
					</form>
				</div>
			</div>
			<div class='nc-login-bottom'></div>
			<div class='nc-login-left'></div>
		</div>
<script type="text/javascript">


$(function(){

	jQuery("#forgetform").validate({
		errorPlacement : function(error, element) {
			var obj = element.siblings(".tip").css('display', 'block')
					.find('p').addClass('error');
			error.appendTo(obj);
		},
        rules : {
            "name":{required:true},//验证邮箱
            "email":{required:true,isEmail:true},//验证邮箱
            "verifyCode":{required:true}
        },
        messages:{
        	"name":{required:"请输入用户名"},
        	"email":{required:"请输入邮箱"},
        	"verifyCode":{required:"请输验证码"}
        }
    }); 
	
	$("#forgetPasswordBtn").click(function(){

		if ($("#forgetform").valid()) {
			$(".btn-danger").attr("disabled","disabled");
			var params = $('#forgetform').serialize();
			$.ajax({
				type:"POST",
				url:domain+"/doforgetpassword.html",
				dataType:"json",
				async : false,
				data : params,
				success:function(data){
					if(data.success){
						//jAlert("密码重置成功，请查收您的邮件！");
						//window.location=domain+"/index.html";
						
						jAlert('密码重置成功，请查收您的邮件！', '提示',function(){
							window.location=domain+"/index.html"
						});
					}else{
						jAlert(data.message);
						refreshCode();//刷新验证码
						$(".btn-danger").removeAttr("disabled");
					}
				}/* ,
				error:function(){
					jAlert("异常，请重试！");
					$(".btn-danger").removeAttr("disabled");
				} */
			});
		}
		
	});
});
</script>
		<#include "/front/commons/_end.ftl" />