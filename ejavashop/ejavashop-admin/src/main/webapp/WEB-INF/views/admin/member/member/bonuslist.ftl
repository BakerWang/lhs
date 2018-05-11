<#include "/admin/commons/_detailheader.ftl" />

<script language="javascript">
	
	var statusBox;
	$(function(){
		//为客户端装配本页面需要的字典数据,多个用逗号分隔
		<#noescape>
			statusBox = eval('(${initJSCodeContainer("SETTLEMEMENT_STATUS")})');
		</#noescape>
		// 查询按钮
		$('#a-gridSearch').click(function(){
			$('#dataGrid').datagrid('reload',queryParamsHandler());
		});
		
		$("#a-gridSettle").click(function(){
				$.ajax({
					type:"GET",
					url: "${domainUrlUtil.EJS_URL_RESOURCES}/admin/member/member/settleadd",
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
	})

	
	function statusFormat(value,row,index){
		return statusBox["SETTLEMEMENT_STATUS"][value];
	}

</script>

<#--1.queryForm----------------->
<div id="searchbar" data-options="region:'north'" style="margin:0 auto;" border="false">
	<h2 class="h2-title">奖金列表 <span class="s-poar"><a class="a-extend" href="#">收起</a></span></h2>
	<div id="searchbox" class="head-seachbox">
		<div class="w-p99 marauto searchCont">
		<form class="form-search" action="doForm" method="post" id="queryForm" name="queryForm">
			<div class="fluidbox"><!-- 不分隔 -->
				<p class="p4 p-item">
					<label class="lab-item">姓名 :</label>
					<input type="text" class="txt" id="q_name" name="q_name" value="${q_name!''}"/>
				</p>
				<p class="p6 p-item">
                    	<label class="lab-item">查询时间 :</label>
                        <input type="text" id="q_startTime" name="q_startTime"
                               onfocus="WdatePicker({startDate:'%y-%M-%d 00:00:00',dateFmt:'yyyy-MM-dd HH:mm:ss'})" class="txt w180"/>
                        -
                        <input type="text" id="q_endTime" name="q_endTime"
                               onfocus="WdatePicker({startDate:'%y-%M-%d 23:59:59',dateFmt:'yyyy-MM-dd HH:mm:ss'})" class="txt w180"/>
                    </p>
				<p class="p4 p-item">
					<label class="lab-item">提供者手机号 :</label>
					<input type="text" class="txt" id="q_mobile" name="q_mobile" value="${q_mobile!''}"/>
				</p>
				<p class="p4 p-item">
					<label class="lab-item">获得者手机号 :</label>
					<input type="text" class="txt" id="q_toMobile" name="q_toMobile" value="${q_toMobile!''}"/>
				</p>
				<p class="p4 p-item">
					<label class="lab-item">结算状态 :</label>
					<@cont.select id="q_status" codeDiv="SETTLEMEMENT_STATUS" style="width:80px"/>
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
						,pageSize:'${pageSize}'
						,fit:true
    					,url:'${(domainUrlUtil.EJS_URL_RESOURCES)!}/admin/member/member/bonuslist'
    					,queryParams:queryParamsHandler()
    					,onLoadSuccess:dataGridLoadSuccess
    					,method:'get'">
		<thead>
			<tr>
				<th field="toUserName" width="150" align="left" halign="center">获得者</th>  
	            <th field="fromUserName" width="150" align="left" halign="center" >提供者</th>  
	            <th field="money" width="80" align="left" halign="center" >金额</th>  
	            <th field="createTime" width="80" align="left" halign="center" >时间</th>  
	            <th field="status" width="70" align="left" halign="center" formatter="statusFormat">结算状态</th>  
			</tr>
		</thead>
	</table>

	<#--3.function button----------------->
	<div id="gridTools">
		<a id="a-gridSearch" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true">查询</a>
 		<@shiro.hasPermission name="/admin/member/member/settleadd">
		<a id="a-gridSettle" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true">结算</a>
		</@shiro.hasPermission> 
<!-- 		<@shiro.hasPermission name="/admin/member/member/balanceopt"> -->
<!-- 		<a id="a-gridOptBalance" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true">余额管理</a> -->
<!-- 		</@shiro.hasPermission> -->
<!-- 			<@shiro.hasPermission name="/admin/member/member/add"> -->
<!-- 			<a id="a-gridadd" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true">新增用户</a> -->
<!-- 			</@shiro.hasPermission> -->
<!-- 		<@shiro.hasPermission name="/admin/member/member/grdIntloglist"> -->
<!-- 		<a id="a-gridGradeLog" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true">经验值日志</a> -->
<!-- 		</@shiro.hasPermission> -->
<!-- 		<@shiro.hasPermission name="/admin/member/member/grdIntloglist"> -->
<!-- 		<a id="a-gridIntegralLog" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true">积分值日志</a> -->
<!-- 		</@shiro.hasPermission> -->
<!-- 		<@shiro.hasPermission name="/admin/member/member/balanceloglist"> -->
<!-- 		<a id="a-gridBalanceLog" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true">余额日志</a> -->
<!-- 		</@shiro.hasPermission> -->
<!-- 		<@shiro.hasPermission name="/admin/member/member/addresslist"> -->
<!-- 		<a id="a-gridAddress" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true">收货地址</a> -->
<!-- 		</@shiro.hasPermission> -->
	</div>
</div>

<#include "/admin/commons/_detailfooter.ftl" />