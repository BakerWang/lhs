<style>
    .ra_div a {
        display: block;
        border-bottom: dotted 1px #dedede;
        padding-bottom: 3px;
        text-decoration: none;
        color: #000;
        font-weight: normal;
        padding-top: 5px;
        padding-left: 15px;
    }
</style>
<div id="aa" class="easyui-accordion" data-options="fit:true"
     style="height: auto;">
	<@shiro.hasPermission name="/admin_menu_member">
	<div title="会员管理" class="ra_div">
		<@shiro.hasPermission name="/admin/member/member">
		<a id='66' href="javascript:void(0);" onclick="addTab('会员管理', '${domainUrlUtil.EJS_URL_RESOURCES}/admin/member/member')">会员管理</a>
		</@shiro.hasPermission>
		<@shiro.hasPermission name="/admin/member/member/bonus">
		<a id='574' href="javascript:void(0);" onclick="addTab('奖金管理', '${domainUrlUtil.EJS_URL_RESOURCES}/admin/member/member/bonus')">奖金管理</a>
		</@shiro.hasPermission>
		<@shiro.hasPermission name="/admin/member/member/relation">
		<a id='575' href="javascript:void(0);" onclick="addTab('关系管理', '${domainUrlUtil.EJS_URL_RESOURCES}/admin/member/member/relation')">关系管理</a>
		</@shiro.hasPermission>
		<@shiro.hasPermission name="/admin/member/member/upgradenew">
		<a id='576' href="javascript:void(0);" onclick="addTab('升级列表', '${domainUrlUtil.EJS_URL_RESOURCES}/admin/member/member/upgradenew')">升级列表</a>
		</@shiro.hasPermission>
		<@shiro.hasPermission name="/admin/member/settle">
		<a id='577' href="javascript:void(0);" onclick="addTab('结算列表', '${domainUrlUtil.EJS_URL_RESOURCES}/admin/member/settle')">结算列表</a>
		</@shiro.hasPermission>
	</div>
	</@shiro.hasPermission>

	<@shiro.hasPermission name="/admin_menu_system">
	<div title="系统管理" class="ra_div">
		<@shiro.hasPermission name="/admin/system/code">
		<a id='55' href="javascript:void(0);" onclick="addTab('数据字典', '${domainUrlUtil.EJS_URL_RESOURCES}/admin/system/code')">数据字典</a>
		</@shiro.hasPermission>
		<@shiro.hasPermission name="/admin/system/role">
		<a id='56' href="javascript:void(0);" onclick="addTab('角色管理', '${domainUrlUtil.EJS_URL_RESOURCES}/admin/system/role')">角色管理</a>
		</@shiro.hasPermission>
		<@shiro.hasPermission name="/admin/system/resource">
		<a id='57' href="javascript:void(0);" onclick="addTab('资源管理', '${domainUrlUtil.EJS_URL_RESOURCES}/admin/system/resource')">资源管理</a>
		</@shiro.hasPermission>
		<@shiro.hasPermission name="/admin/system/adminuser">
		<a id='82' href="javascript:void(0);" onclick="addTab('管理员管理', '${domainUrlUtil.EJS_URL_RESOURCES}/admin/system/adminuser')">管理员管理</a>
		</@shiro.hasPermission>
		<@shiro.hasPermission name="/admin/system/adminuser/editpwd">
		<a id='399' href="javascript:void(0);" onclick="addTab('修改密码', '${domainUrlUtil.EJS_URL_RESOURCES}/admin/system/adminuser/editpwd')">修改密码</a>
		</@shiro.hasPermission>
	</div>
	</@shiro.hasPermission>
</div>


