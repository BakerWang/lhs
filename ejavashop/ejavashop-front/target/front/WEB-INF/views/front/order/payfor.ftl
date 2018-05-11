<#include "/front/commons/_top.ftl" />
<link  rel="stylesheet" href='${(domainUrlUtil.EJS_STATIC_RESOURCES)!}/css/order.css'>
<link  rel="stylesheet" href='${(domainUrlUtil.EJS_STATIC_RESOURCES)!}/css/payfor.css'>
		<div class='w w1 header container'>
			<div class='ld' id='logo'>
				<a href='' target='_blank' class='link1'>
					<img src='${(domainUrlUtil.EJS_STATIC_RESOURCES)!}/img/logo.jpg'>
				</a>
			</div>	
			
		</div>
	
	<form id="payForm" method="GET" >
		<input type="hidden" name="paySessionstr" value="${paySessionstr!''}">
		<input type="hidden" name="relationOrderSn" id="relationOrderSn" value="${relationOrderSn!''}">
		<input type="hidden" name="fromType" id="fromType" value="${fromType!''}">
		<!-- <input type="hidden" name="payType"  value=""> --><!-- 支付方式 -->
	
		<!-- 支付 -->
		<div id='PayforBox'>
			<div class='container w'>
				<!-- 订单详情 -->
				<div class='order-information'>
					<div class='p-left'>
						<h3 class='p-title'>
							<#if fromType?? && fromType == 2>
								订单号：  
							<#else>
								订单提交成功，请您尽快付款！    订单号：  
							</#if>
							<#if relationOrderSn?? >
									${relationOrderSn}
							</#if>
						</h3>
						<p class='p-tips'>请您在提交订单后<span class='font-red'>24小时内</span>完成支付，否则订单会自动取消</p>
					</div>
					<div class='p-right'>
						<div class='pay-price'>
							<em>应付金额</em>
							<strong>${(payAmount)!'' }</strong>
							<em>元</em>
						</div>
					</div>
					
					<br>
					<div class='p-left'>
						<div class='form'>
							<input type='checkbox' id='selectOrderBalance' name="selectOrderBalance" autocomplete="off" <#if orderSuccessVO?? && orderSuccessVO.isBanlancePay> checked="checked"</#if>>
							<label id='canUsedBalanceId'>使用余额（账户当前余额：${(member.balance)!'0.00' }元）</label>
							支付密码：<input type='password' id='balancePassword' name="balancePassword" disabled <#if orderSuccessVO?? && orderSuccessVO.isBanlancePay> value="123456" </#if>>
						</div>
					</div>
					
					<div class='clr'></div>
					
				</div>
				
				<!-- end -->
				<!-- 支付方式 -->
				<div class='payment'>
					<div class='paybox'>
						<div class='p-wrap'>
							<ul>
								<li>
									<input type="radio" name="optionsRadios" id="optionsRadios2" value="alipay" checked="checked"> 
									<div class='img-pay'>
										<img width="130" height="45" src='${(domainUrlUtil.EJS_STATIC_RESOURCES)!}/img/l142.png'>
									</div>
								</li>
								<li>
									<input type="radio" name="optionsRadios" id="optionsRadios2" value="unionpay">
									<div class='img-pay'>
										<img width="130" height="40" src='${(domainUrlUtil.EJS_STATIC_RESOURCES)!}/img/unionpay.png'>
									</div>
								</li>
								<li>
									<input type="radio" name="optionsRadios" id="optionsRadios2" value="weixin">
									<div class='img-pay'>
										<img width="130" height="40" src='${(domainUrlUtil.EJS_STATIC_RESOURCES)!}/img/weixin_logo.png'>
									</div>
								</li>
							</ul>
						</div>
					</div>
					<!-- <div class='paybox bt0'>
						<div class='p-wrap'>
							<ul>
								<li>
									 <input type="radio" name="optionsRadios" id="optionsRadios2" value="option2"> 
									<div class='img-pay'>
										<img src='${(domainUrlUtil.EJS_STATIC_RESOURCES)!}/img/l144.png'>
									</div>
								</li>
								<li>
									<input type="radio" name="optionsRadios" id="optionsRadios2" value="option2">
									<div class='img-pay'>
										<img src='${(domainUrlUtil.EJS_STATIC_RESOURCES)!}/img/payafter.jpg'>
									</div> 
								</li>
							</ul>
						</div>
					</div> -->
					<div class='payment-column'>&nbsp;</div>
					<div class='pv-button'>
						<!-- 在线支付--> 
						<input type='button' value='立即支付'  class='PayforSubmit' id='PayButtom'> 
					</div>
				</div>
			</div>
		</div>
		
	</form>
		<!-- end -->
		<!-- footer -->
<script type="text/javascript">
$(function(){
	//选中余额checkbox 
	$("#selectOrderBalance").click(function(){
		//如果余额小于等于0 那么不允许选中
		<#if member??&&member.balance??>
			<#if member.balance<=0 >
				$(this).prop("checked", false);
				return;
			</#if>
		</#if>

		if($(this).prop("checked")){
			
			// 如果从下单跳转并且选择使用余额，则密码不让输入
			<#if orderSuccessVO?? && orderSuccessVO.isBanlancePay>
				$("#balancePassword").attr("disabled","disabled");
			<#else>
				$("#balancePassword").removeAttr("disabled");
			</#if>
		}else{
			<#if orderSuccessVO?? && orderSuccessVO.isBanlancePay>
				$("#balancePassword").attr("disabled","disabled");
			<#else>
				$("#balancePassword").attr("disabled","disabled");
				$("#balancePassword").val("");
			</#if>
		}
	});
	
	$("#PayButtom").click(function(){
		<#if orderSuccessVO?? && orderSuccessVO.isBanlancePay>
		<#else>
			var balancePwd = $("#balancePassword").val();
	  		if($("#selectOrderBalance").prop("checked")){
	  			if(isEmpty(balancePwd)){
	  				jAlert("密码不能为空");
	  				$("#balancePassword").focus();
	  				return false;
	  			}
	  			//验证支付密码
	  			var checkpwd = checkBalancePwd(balancePwd);
	  			if(!checkpwd){
	  				return false;
	  			}
	  		}
		</#if>
		
		// 支付提交
		//$("#payForm").attr("action", "${(domainUrlUtil.EJS_URL_RESOURCES)!}/order/dopay.html")
		$("#payForm").attr("action", "${(domainUrlUtil.EJS_URL_RESOURCES)!}/payindex.html")
			 .attr("method", "GET")
			 .submit();
	});
});


	//验证支付密码
	function checkBalancePwd(balancePwd){
		var correct = false;
		$.ajax({
			type : "GET",
			url :  domain+"/order/checkbalancepwd.html",
			data : {balancePwd:balancePwd},
			dataType : "json",
			async:false,
			success : function(data) {
				if(data.success){
					correct = data.data.correct;
					var errcount = parseInt(data.data.pwdErrCount);
				   	if(errcount>=6){
				   		jAlert("支付密码输错超过6次,请用其他方式支付");
						$(".toggle-title").click();
						return false;
				   	}
					if(!correct){
						jAlert("支付密码不正确，您最多还可以输入"+(6-errcount)+"次");
						return false;
					}
				}else {
					jAlert(data.message);
					return false;
				}
			},
			error : function() {
				jAlert("验证密码失败！");
			}
		});
		return correct;
	}
</script>
	
<#include "/front/commons/_end.ftl" />