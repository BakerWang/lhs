
$(function(){
	//服务协议默认勾选
	$("#acceptProtocol").attr("checked","checked");
	
	/**
	 * 增加手机号校验
	 */
	jQuery.validator.addMethod('isMobile', function(value, element) {
        return this.optional(element) || auth_phone(value);
    },"请输入正确的手机号");

	jQuery.validator.addMethod('passwordLength', function(value, element) {
	    return this.optional(element) || auth_password(value);
	},"密码长度在6～20字符之间");
	
	/**
	 * 增加邮箱校验
	 */
	jQuery.validator.addMethod('isEmail', function(value, element) {
        return this.optional(element) || auth_email(value);
    },"请输入正确的邮箱地址");

	/**
	 * 增加用户名重复校验
	 */
	jQuery.validator.addMethod('nameIsNotExist', function(value, element) {
        return this.optional(element) || nameIsNotExist(value);
    },"此用户名已存在");

	
	function auth_email(value){
		// 验证邮箱
		var flag = false;
		var re = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/;
		if(re.test(value)==false){
		}else{
		   flag = true;
		}
		return flag;
	}
	
	function auth_password(value){
		var flag = false;
		var password = value.replace(/' '/g, '');
		if (password.length < 6 || password.length > 20) {
		} else {
			flag = true;
		}
		return flag;
	}


	//验证手机号码
	function auth_phone(phone){
	    var re = /(^0{0,1}1[3|4|5|6|7|8|9][0-9]{9}$)/;
	    return re.test(phone);
	} 
	
	//验证用户名是否已存在
	function nameIsNotExist(value){
		var nameIsNotExist = false;
		$.ajax({
			type:"GET",
			url:domain+"/nameIsExist.html",
			dataType:"json",
			async : false,
			data : {name:value},
			success:function(data){
				if(data.success){
					nameIsNotExist = true;
				} else {
					jAlert(data.message);
				}
			}
		});
		
		return nameIsNotExist;
	}

	
	jQuery("#form").validate({
		errorPlacement : function(error, element) {
			var obj = element.siblings(".tip").css('display', 'block')
					.find('p').addClass('error');
			error.appendTo(obj);
		},
        rules : {
            "email":{required:false,isEmail:true},//验证邮箱
            "name":{required:true,nameIsNotExist:true},//验证邮箱
            "password":{required:true,passwordLength:true},
            "repassword":{required:true,equalTo:"#setPassword"},
            "verifyCode":{required:true},
            "acceptProtocol":{required:true}
        },
        messages:{
        	"email":{required:"请输入邮箱"},
        	"name":{required:"请输入用户名"},
        	"password":{required:"请输入密码"},
        	"repassword":{required:"请输入确认密码",equalTo:"两次密码不一致"},
        	"verifyCode":{required:"请输验证码"},
        	"acceptProtocol":{required:"请接受服务条款"}
        }
    }); 
	
	/**
	 * 提交注册信息
	 */
	$("#signupButton").click(function(){

		if ($("#form").valid()) {
			$(".btn-danger").attr("disabled","disabled");
			var params = $('#form').serialize();
			$.ajax({
				type:"POST",
				url:domain+"/doregister.html",
				dataType:"json",
				async : false,
				data : params,
				success:function(data){
					if(data.success){
						jAlert('注册成功！', '提示',function(){
							window.location=domain+"/member/order.html"
						});
					}else{
						jAlert(data.message);
						refreshCode();//刷新验证码
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


	


