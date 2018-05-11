<#import "/front/commons/_macro_controller.ftl" as cont/>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
	<title>ejavashop B2B2C</title>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1"/>
	<meta name="viewport" content="width=device-width, initial-scale=1.0,minimum-scale=1.0, maximum-scale=1.0, user-scalable=no"/>
	<link  rel="stylesheet" type="text/css" href='${(domainUrlUtil.EJS_STATIC_RESOURCES)!}/css/bootstrap.min.css'/>
	<link rel="stylesheet" type="text/css" href="${(domainUrlUtil.EJS_STATIC_RESOURCES)!}/css/details.css"/>
	<link rel="stylesheet" type="text/css" href="${(domainUrlUtil.EJS_STATIC_RESOURCES)!}/css/user.css"/>
	<link rel="stylesheet" type="text/css" href="${(domainUrlUtil.EJS_STATIC_RESOURCES)!}/css/base.css"/>
	<link rel="stylesheet" type="text/css" href="${(domainUrlUtil.EJS_STATIC_RESOURCES)!}/css/jquery.alerts.css"/>
	<script type="text/javascript" src='${(domainUrlUtil.EJS_STATIC_RESOURCES)!}/js/jquery-1.9.1.min.js'></script>
	<script type="text/javascript" src='${(domainUrlUtil.EJS_STATIC_RESOURCES)!}/js/bootstrap.min.js'></script>
	<script type="text/javascript" src='${(domainUrlUtil.EJS_STATIC_RESOURCES)!}/js/jquery.validate.min.js'></script>
	<script type="text/javascript" src="${(domainUrlUtil.EJS_STATIC_RESOURCES)!}/js/func.js"></script>
	<script type="text/javascript" src="${(domainUrlUtil.EJS_STATIC_RESOURCES)!}/js/checkvalue.js"></script>
	<script type="text/javascript" src="${(domainUrlUtil.EJS_STATIC_RESOURCES)!}/js/jquery.alerts.js"></script>
	<style type='text/css' rel="stylesheet">
	</style>
	<script type="text/javascript">
		var domain = '${(domainUrlUtil.EJS_URL_RESOURCES)!}';
	</script>
  </head>
	<body class='wp1200'>
	<#assign form=JspTaglibs["/WEB-INF/tld/spring-form.tld"]>
			<div class='wrapper'>
			<div class='container'>
				<ul class='collect lh'>
					<li class='fore1'>
						<a href='javascript:void(0)' onclick="AddFavorite(window.location,document.title);return false;">收藏ejavashop</a>
					</li>
				</ul>
				<ul class='shortcut-right lh'>
					<#if Session.memberSession??>
				   		<#assign user = Session.memberSession.member>
				   </#if>
				   <#if user??>
				   		<li class='fore1' id='loginbar'>
							<a href='${(domainUrlUtil.EJS_URL_RESOURCES)!}/member/order.html' target="_blank" class='login'>${(user.name)!''}</a>&nbsp;&nbsp;
							<a href='${(domainUrlUtil.EJS_URL_RESOURCES)!}/logout.html'  onclick="logout()" class='regist'>退出</a>
						</li>
						<li class='fore2 ld'>
							<span></span>
							<a href="${(domainUrlUtil.EJS_URL_RESOURCES)!}/member/order.html" target="_blank">我的订单</a>
						</li>
				   	<#else>
						<li class='fore1' id='loginbar'>
							<a href='${(domainUrlUtil.EJS_URL_RESOURCES)!}/login.html' class='login'>你好，请登录</a>&nbsp;&nbsp;
							<a href='${(domainUrlUtil.EJS_URL_RESOURCES)!}/register.html' class='regist'>免费注册</a>
						</li>
				   </#if>
					
					<li class='fore2-1 ld ff-vip' style='padding-left:12px;'>
						<span></span>
						<a href='${(domainUrlUtil.EJS_URL_RESOURCES)!}/member/index.html'>会员中心</a>
					</li>
					<li class='fore3 ld app-ff menu'>
						<span></span>
						<a href=''>手机商城</a>
					</li>
					<li class='fore5 ld menu' id='site-nav'>
						<span></span>
							网站导航
						<!-- <b></b> -->
					</li>
				</ul>
			</div>
		</div>
			<div class='top-head'>
			<div class='container' id='HeardTop'>
				<div class='ld' id='logo-img'>
					<a href="${(domainUrlUtil.EJS_URL_RESOURCES)!}/index.html" target="_blank">
						<img alt="" src="${domainUrlUtil.EJS_STATIC_RESOURCES!}/img/ejavashoplogo.png" >
					</a>
				</div>
				<div class='seach-box'>
					<div class='i-search ld'>
						<div class='form'>
							<form action="${(domainUrlUtil.EJS_URL_RESOURCES)!}/search.html" method="get">
								<input type='text' id='keyword' name="keyword" value="${(keyword)!''}" class='text' autocomplete="off" style='color:rgb(153,153,153);'>
								<input type='submit' value='搜索' class='button'>
							</form>
						</div>
					</div>
					<div id='Hotwords'>
						<strong>热门搜索：</strong>
						<div id="keywordIDs"></div>
					</div>
				</div>
				
				<div class='settleup'>
					<dl class>
						<!-- 如果没有商品这里显示0 -->
						<div class='addcart-goods-num'>0</div>
						<!--  -->
						<dt class='ld first-dt'>
							<s></s> <a href='${(domainUrlUtil.EJS_URL_RESOURCES)!}/cart/detail.html'
								target="_blank">我的购物车</a> <b></b>
						</dt>
						<dd id="priviewMycart">
							<!-- <div class="emptycart">
						      <div class="emptycart_line"></div>
						      <div class="emptycart_txt"><img src="imgttleup-nogoods.png" alt="">购物车中还没有商品，赶紧选购吧</div>
						   </div>-->
						</dd>
					</dl>
				</div>
			</div>
			<div class='container' id="allcate">
				<div class='nav'>
					<div id='CateGorys'>
						<div class='total-shop ld'>
							<h2>
								<a href=''>全部商品分类</a>
								<b></b>
							</h2>
						</div>
						<div class='yy-sortall' id="yy-sortall">
						</div>
					</div>
					<ul class='navitems'>
						<li class='fore1'>
							<a href='${(domainUrlUtil.EJS_URL_RESOURCES)!}/index.html'>首页</a>
						</li>
						<li class='fore1'>
							<a href='#'>家用电器</a>
						</li>
						<li class='fore1'>
							<a href='#'>服装城</a>
						</li>
					</ul>
				</div>

			</div>
		</div>
		<script type="text/javascript" src="${(domainUrlUtil.EJS_STATIC_RESOURCES)!}/js/common.js"></script>
	
	<script type="text/javascript">
		$(function(){
			//加载购物车数据
			refreshMycart();
			
			//加载导航数据
			$.ajax({
				type:"GET",
				url:domain+"/cateList.html",
				dataType:"html",
				async : false,
				success:function(data){
						//加载数据
						$("#yy-sortall").html(data);
				}
			});
			
		})
		
	</script>
		