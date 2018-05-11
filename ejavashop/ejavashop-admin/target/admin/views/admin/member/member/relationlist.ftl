<#include "/admin/commons/_detailheader.ftl" /> <#assign
currentBaseUrl="${domainUrlUtil.EJS_URL_RESOURCES}/admin/member/member"/>
<script language="javascript">
	var codeBox;
	$(function() {
		//为客户端装配本页面需要的字典数据,多个用逗号分隔
		<#noescape>
			codeBox = eval('(${initJSCodeContainer("MEMBER_GRADE")})');
		</#noescape>

		$('#a-gridSearch').click(function() {
			$('#dataGrid').datagrid('reload', queryParamsHandler());
		});
	})
	function gradeFormat(value, row, index) {
		return codeBox["MEMBER_GRADE"][value];
	}

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
</script>

<div id="searchbar" data-options="region:'north'"
	style="margin: 0 auto;" border="false">
	<h2 class="h2-title">
		资源列表 <span class="s-poar"><a class="a-extend" href="#">收起</a></span>
	</h2>
	<div id="searchbox" class="head-seachbox">
		<div class="w-p99 marauto searchCont" style="display:none">
			<form class="form-search" action="doForm" method="post"
				id="queryForm" name="queryForm">
				<div class="fluidbox">
					<p class="p4 p-item">
						<label class="lab-item">手机号 :</label> <input type="text"
							class="txt" id="q_mobile" name="q_mobile" value="${q_mobile!''}" />
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
						,treeField:'name'
						,idField:'id'
    					,method:'get'
						,onBeforeLoad:bl">
		<thead>
			<tr>
				<th field="name" width="120" align="left" halign="center">姓名</th>
				<th field="mobile" width="120" align="left" halign="center">手机号</th>
				<th field="grade" width="60" align="center" formatter="gradeFormat">等级</th>
			</tr>
		</thead>
	</table>

	<div id="gridTools">
		<#noescape>
			${buttons!}
		</#noescape>
	<a id="a-gridSearch" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true">查询</a>
	</div>

	<div class="wrapper" id="addResources">
		
	</div>
</div>

<#include "/admin/commons/_detailfooter.ftl" />
