<#include "/admin/commons/_detailheader.ftl" /> <#assign
currentBaseUrl="${domainUrlUtil.EJS_URL_RESOURCES}/admin/member/member"/>
<script language="javascript">
	var gendeBox;
	$(function() {
		//为客户端装配本页面需要的字典数据,多个用逗号分隔
		<#noescape>
			gendeBox = eval('(${initJSCodeContainer("MEMBER_GRADE")})');
		</#noescape>

		$('#a-gridSearch').click(function() {
			$('#dataGrid').datagrid('reload', queryParamsHandler());
		});
	})

	function afterDataGridLoaded() {
	}

	function dataGridLoadFail() {
		alert("服务器异常");
	}

	function dataGridLoadSuccess(row, data) {
		$('#dataGrid').treegrid('expand',1);
	}

	function cc(row) {
	}

	function bl(row, param) {
		if (!row) {
			param.id = 0;
		}
	}
	
	function gradeFormat(value,row,index){
		return gendeBox["MEMBER_GRADE"][value];
	}
</script>

<div id="searchbar" data-options="region:'north'"
	style="margin: 0 auto;" border="false">
	<h2 class="h2-title">
		关系列表 <span class="s-poar"><a class="a-extend" href="#">收起</a></span>
	</h2>
	<div id="searchbox" class="head-seachbox" >
		<div class="w-p99 marauto searchCont"  >
			<form class="form-search" action="doForm" method="post"
				id="queryForm" name="queryForm">
				<div class="fluidbox">
					<p class="p4 p-item">
						<label class="lab-item">手机号 :</label> <input type="text"
							class="txt" id="q_name" name="q_mobile" value="${q_mobile!''}" />
					</p>
				</div>
			</form>
		</div>
	</div>
</div>

<div data-options="region:'center'" border="false">
	<table id="dataGrid" class="easyui-treegrid"
		data-options="rownumbers:false
						,singleSelect:true
						,autoRowHeight:true
						,animate:true
						,fitColumns:true
						,toolbar:'#gridTools'
						,striped:true
						,pagination:false
						,pageSize:'2'
						,fit:true
    					,url:'${currentBaseUrl}/relationlist'
    					,queryParams:queryParamsHandler()
    					,onLoadSuccess:dataGridLoadSuccess
    					,onLoadError:dataGridLoadFail
						,onBeforeExpand:cc
						,treeField:'content'
						,idField:'id'
    					,method:'get'
						,onBeforeLoad:bl">
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

	<div id="gridTools">
		<#noescape>
			${buttons!}
		</#noescape>
		<@shiro.hasPermission name="/admin/system/resource/addWin">
		<a id="a-gridAdd" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true">添加</a>
		</@shiro.hasPermission>
		<@shiro.hasPermission name="/admin/system/resource/editWin">
		<a id="a-gridEdit" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true">编辑</a>
		</@shiro.hasPermission>
		<@shiro.hasPermission name="/admin/system/resource/del">
		<a id="a-gridRemove" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true">删除</a>
		</@shiro.hasPermission>
	</div>

	<div class="wrapper" id="addResources">
		
	</div>
</div>

<#include "/admin/commons/_detailfooter.ftl" />
