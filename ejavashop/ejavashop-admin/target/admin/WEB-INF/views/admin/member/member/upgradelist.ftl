<#include "/admin/commons/_detailheader.ftl" />

<script language="javascript">
	var yesNoBox;
	var gradeBox;
	var sourceBox;
	var genderBox;
	var statusBox;
	var optTypeBox;
	var balanceStateBox;
	var addressStateBox;
	$(function(){
		//为客户端装配本页面需要的字典数据,多个用逗号分隔
		<#noescape>
			yesNoBox = eval('(${initJSCodeContainer("YES_NO")})');
			gradeBox = eval('(${initJSCodeContainer("MEMBER_GRADE")})');
			sourceBox = eval('(${initJSCodeContainer("MEMBER_SOURCE")})');
			genderBox = eval('(${initJSCodeContainer("GENDER")})');
			statusBox = eval('(${initJSCodeContainer("MEMBER_STATUS")})');
			optTypeBox = eval('(${initJSCodeContainer("MEMBER_VAL_OPT_TYPE")})');
			balanceStateBox = eval('(${initJSCodeContainer("MEMBER_BALANCE_STATE")})');
			addressStateBox = eval('(${initJSCodeContainer("MEMBER_ADDRESS_STATE")})');
		</#noescape>
		// 查询按钮
		$('#a-gridSearch').click(function(){
			$('#dataGrid').datagrid('reload',queryParamsHandler());
		});
	 	// 余额操作
		$("#a-gridadd").click(function(){
			$("#id").val('');
			$("#name").val('');
			$("#mobile").val('');
			$("#email").val('');
			$("#parent").val('');
			$("#recommend").val('');
			$('#_memberbalanceopt').dialog('open');
	 	});
		$("#a-gridedit").click(function(){
			var selected = $('#dataGrid').datagrid('getSelected');
	 		if(!selected){
				$.messager.alert('提示','请选择操作行。');
				return;
			}
			$("#id").val(selected.id);
			$("#name").val(selected.name);
			$("#mobile").val(selected.mobile);
			$("#email").val(selected.email);
			$("#parent").val(selected.parentName);
			$("#recommend").val(selected.placeName);
			$('#_memberedit').dialog('open');
			
	 	});

		// 余额操作界面确定按钮
		$("#balanceOptOk").click(function(){
			if($("#balanceOptForm").form('validate')){
				$.messager.progress({text:"提交中..."});
				$.ajax({
					type:"POST",
					url: "${domainUrlUtil.EJS_URL_RESOURCES}/admin/member/member/edit",
					dataType: "json",
					data: $('#balanceOptForm').serialize(),// + "&" + getCSRFTokenParam(),
					cache:false,
					success:function(data, textStatus){
						if (data.success) {
							$.messager.progress('close');
							$.messager.alert('提示','新增成功。');
							
							$('#_memberbalanceopt').dialog('close');
							$('#dataGrid').datagrid('reload',queryParamsHandler());
							return;
						} else {
							$.messager.progress('close');
							$.messager.alert("提示",data.message);
						}
					}
				});
	  		}
		});
		
		// 余额操作界面确定按钮
		$("#memberEditOptOk").click(function(){
			if($("#balanceOptForm").form('validate')){
				$.messager.progress({text:"提交中..."});
				$.ajax({
					type:"POST",
					url: "${domainUrlUtil.EJS_URL_RESOURCES}/admin/member/member/edit",
					dataType: "json",
					data: $('#balanceOptForm').serialize(),// + "&" + getCSRFTokenParam(),
					cache:false,
					success:function(data, textStatus){
						if (data.success) {
							$.messager.progress('close');
							$.messager.alert('提示','新增成功。');
							
							$('#_memberedit').dialog('close');
							$('#dataGrid').datagrid('reload',queryParamsHandler());
							return;
						} else {
							$.messager.progress('close');
							$.messager.alert("提示",data.message);
						}
					}
				});
	  		}
		});
		
		
		//用户升级
		$("#a-upgrade").click(function(){
			var selected = $('#dataGrid').treegrid('getSelected');
			if(selected !=null && selected !=''){
				$.messager.progress({text:"提交中..."});
				$.ajax({
					type:"GET",
					url: "${domainUrlUtil.EJS_URL_RESOURCES}/admin/member/member/upgrade?userId="+selected.id,
					cache:false,
					success:function(data, textStatus){
						if (data.success) {
							$.messager.progress('close');
							$.messager.alert('提示','修改成功。');
							$('#dataGrid').datagrid('reload',queryParamsHandler());
							return;
						} else {
							$.messager.progress('close');
							$.messager.alert("提示",data.message);
							//refrushCSRFToken(data.csrfToken);
						}
					}
				});
			}else{
				$.messager.progress({text:"提交中..."});
				$.ajax({
					type:"GET",
					url: "${domainUrlUtil.EJS_URL_RESOURCES}/admin/member/member/upgrade?userId=0",
					cache:false,
					success:function(data, textStatus){
						if (data.success) {
							$.messager.progress('close');
							$.messager.alert('提示','修改成功。');
							$('#dataGrid').datagrid('reload',queryParamsHandler());
							return;
						} else {
							$.messager.progress('close');
							$.messager.alert("提示",data.message);
							//refrushCSRFToken(data.csrfToken);
						}
					}
				});
			}
			
		});
		
		
		$("#a-test").click(function(){
				$.ajax({
					type:"GET",
					url: "${domainUrlUtil.EJS_URL_RESOURCES}/admin/member/member/test",
					cache:false,
					success:function(data, textStatus){
						if (data.success) {
							$.messager.alert('提示','修改成功。');
							$('#dataGrid').datagrid('reload',queryParamsHandler());
							return;
						} else {
							$.messager.alert("提示",data.message);
							//refrushCSRFToken(data.csrfToken);
						}
					}
				});
		});
		

		// 余额操作界面取消按钮
		$("#balanceOptCancel").click(function(){
			$('#_memberbalanceopt').dialog('close');
		});
	})

	function yesNoFormat(value,row,index){
		return yesNoBox["YES_NO"][value];
	}
	function gradeFormat(value,row,index){
		return gradeBox["MEMBER_GRADE"][value];
	}
	function sourceFormat(value,row,index){
		return sourceBox["MEMBER_SOURCE"][value];
	}
	function genderFormat(value,row,index){
		return genderBox["GENDER"][value];
	}
	function statusFormat(value,row,index){
		return statusBox["MEMBER_STATUS"][value];
	}
	function optTypeFormat(value,row,index){
		return optTypeBox["MEMBER_VAL_OPT_TYPE"][value];
	}
	function balanceStateFormat(value,row,index){
		return balanceStateBox["MEMBER_BALANCE_STATE"][value];
	}
	function addressStateFormat(value,row,index){
		return addressStateBox["MEMBER_ADDRESS_STATE"][value];
	}
	

</script>

<#--1.queryForm----------------->
<div id="searchbar" data-options="region:'north'" style="margin:0 auto;" border="false">
	<h2 class="h2-title">升级列表 <span class="s-poar"><a class="a-extend" href="#">收起</a></span></h2>
	<div id="searchbox" class="head-seachbox" >
		<div class="w-p99 marauto searchCont">
		<form class="form-search" action="doForm" method="post" id="queryForm" name="queryForm">
			<div class="fluidbox"><!-- 不分隔 -->
				<p class="p4 p-item">
					<label class="lab-item">姓名 :</label>
					<input type="text" class="txt" id="q_name" name="q_name" value="${q_name!''}"/>
				</p>
				<p class="p4 p-item">
					<label class="lab-item">手机号 :</label>
					<input type="text" class="txt" id="q_mobile" name="q_mobile" value="${q_mobile!''}"/>
				</p>
			</div>
		</form>
		</div>
	</div>
</div>

<#--2.datagrid----------------->
<div data-options="region:'center'" border="false">
	<table id="dataGrid" class="easyui-datagrid" 
			data-options="rownumbers:true
						,singleSelect:true
						,autoRowHeight:false
						,fitColumns:false
						,collapsible:true
						,toolbar:'#gridTools'
						,striped:true
						,pagination:true
						,fit:true
    					,url:'${(domainUrlUtil.EJS_URL_RESOURCES)!}/admin/member/member/upgradelist'
    					,queryParams:queryParamsHandler()
    					,onLoadSuccess:dataGridLoadSuccess
    					,method:'get'
    					">
		<thead>
			<tr>
				<th field="name" width="150" align="left" halign="center">姓名</th>  
	            <th field="grade" width="80" align="left" halign="center" formatter="gradeFormat">等级</th>  
	            <th field="parentName" width="80" align="left" halign="center" >上级姓名</th>  
	            <th field="placeName" width="80" align="left" halign="center" >推荐人</th>  
	            <th field="email" width="110" align="left" halign="center">邮箱</th>  
	            <th field="mobile" width="110" align="left" halign="center">手机号</th>  
	            <th field="registerTime" width="150" align="left" halign="center">注册时间</th>  
<!-- 	            <th field="status" width="70" align="left" halign="center" formatter="statusFormat">使用状态</th>   -->
			</tr>
		</thead>
	</table>

	<#--3.function button----------------->
	<div id="gridTools">
		<a id="a-gridSearch" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true">查询</a>
<!-- 		<@shiro.hasPermission name="/admin/member/member/valueopt"> -->
<!-- 		<a id="a-gridOptIntegral" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true">经验积分管理</a> -->
<!-- 		</@shiro.hasPermission> -->
<!-- 		<@shiro.hasPermission name="/admin/member/member/balanceopt"> -->
<!-- 		<a id="a-gridOptBalance" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true">余额管理</a> -->
<!-- 		</@shiro.hasPermission> -->
<!-- 		<@shiro.hasPermission name="/admin/member/member/grdIntloglist"> -->
<!-- 		<a id="a-gridGradeLog" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true">经验值日志</a> -->
<!-- 		</@shiro.hasPermission> -->
<!-- 		<@shiro.hasPermission name="/admin/member/member/grdIntloglist"> -->
<!-- 		<a id="a-gridIntegralLog" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true">积分值日志</a> -->
<!-- 		</@shiro.hasPermission> -->
<!-- 		<@shiro.hasPermission name="/admin/member/member/balanceloglist"> -->
<!-- 		<a id="a-gridBalanceLog" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true">余额日志</a> -->
<!-- 		</@shiro.hasPermission> -->
   		    <@shiro.hasPermission name="/admin/member/member/upgrade">
 		    <a id="a-upgrade" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-basket_up" plain="true">升级</a>
   		   </@shiro.hasPermission>
	</div>
</div>
<#----会员余额变更-------------->
<#include "/admin/member/member/memberbalanceopt.ftl" />
<#include "/admin/commons/_detailfooter.ftl" />