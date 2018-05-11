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
			<div class='nc-login-layout'>
				<div class='nc-signup'>
					<div class='signup-title'>
						<h3>用户注册<span  style="float:right;font-size:12px;margin-right:30px;"><a href='${(domainUrlUtil.EJS_URL_RESOURCES)!}/login.html'>已有账号，请登录</a></span></h3>
					</div>
					<div class='signup-content'>
						<!-- <form class="form-horizontal forms-group" id='form'  action="${(domainUrlUtil.EJS_STATIC_RESOURCES)!}/doregister.html" method="POST"> -->
						<form class="form-horizontal forms-group" id='form' method="POST">
							<div class='reg-switch-wrap'>
								<div class="form-group reg-email reg-switch " >
								    <label for="inputEmail3" class="form-label">用户名：</label>
								    <div class="login-box">
								      	<input type="text" class="form-control" id="inputEmail" name='name'>
								      	<div class='tip signup-message-email'>
							      			<p></p>
							      		</div>
								    </div>
							  	</div>
							</div>
						  	<div class="form-group">
							    <label for="inputPasswordl3" class="form-label">设置密码：</label>
							    <div class="login-box">
							      	<input type="password" class="form-control" id="setPassword" name='password'>
							      	<div class='tip signup-message-password'>
							      		<p></p>
							      	</div>
							    </div>
						  	</div>
						  	<div class="form-group">
							    <label for="inputPasswordl3" class="form-label">确认密码：</label>
							    <div class="login-box">
							      	<input type="password" class="form-control" id="confirmPsw" name='repassword'>
							      	<div class='tip signup-message-confirmpsw'>
							      		<p></p>
							      	</div>
							    </div>
						  	</div>
						  	<div class="form-group">
							    <label for="inputPasswordl3" class="form-label"> 验证码：</label>
							    <div class="code-box">
							      	<input type="text" class="form-control" id="inputCode3" name="verifyCode">
							      	<div class='tip signup-message-code'>
							      		<p></p>
							      	</div>
							    </div>
							    <div class='fl' style='margin-left:2px;'>
							    	<img style="cursor:pointer;" src="${(domainUrlUtil.EJS_URL_RESOURCES)!}/verify.html" id="code_img" onclick="refreshCode();" width="59" height="27" />
							    	 <a href='javascript:void(0);' onclick="refreshCode();">看不清，换一张</a>
							    </div>
						  	</div>
						  	<div class='form-group'>
						  		<div class='ml70'>
						  			<a href='javascript:void(0)' id="signupButton"  class='btn btn-danger'>立即注册</a>
						  		</div>
						  	</div>
						  	<div class='form-group'>
						  		<div class='ml70'>
								  	<input type='checkbox' class='agree' name="acceptProtocol" id="acceptProtocol">
								  	<span class='protocol'>阅读并同意<a href='./service_protocol.html'>服务协议</a></span>
								</div>
							</div>
						  	
						</form>
					</div>
				</div>
				<div class='nc-login-bottom'></div>
				<div class='nc-login-left'></div>
			</div>
			</div>
			<#include "/front/commons/_end.ftl" />