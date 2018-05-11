<div id="_memberedit" class="easyui-dialog popBox" title="修改会员信息"
	style="width: 500px; height: 350px;"
	data-options="resizable:true,closable:true,closed:true,cache: false,modal: true">
	<#--1.valueOptForm----------------->
	<div class="form-contbox">
		<@form.form method="post" class="validForm" id="balanceOptForm"
		name="balanceOptForm">
		<input type="hidden" id="id" name="id" value="" />
		<dl class="dl-group">
			<dt class="dt-group">
				<span class="s-icon"></span>基本信息
				<input type="hidden" id="id" name="id" value="" />
			</dt>
			<dd class="dd-group">
				<div class="fluidbox">
					<p class="p12 p-item">
						<label class="lab-item">姓名: </label>
						<input class="txt w200 easyui-validatebox" type="text" id="name"
							name="name" 
							data-options="required:true,validType:'length[1,50]'" readonly="readonly" />
					</p>
				</div>
				<div class="fluidbox">
					<p class="p12 p-item">
						<label class="lab-item">手机号: </label> <input
							class="txt w200 easyui-validatebox" id="mobile" name="mobile" value=""
							data-options="required:true">
					</p>
				</div>
				<div class="fluidbox">
					<p class="p12 p-item">
						<label class="lab-item">邮箱: </label> <input
							class="txt w200 easyui-validatebox" id="email" name="email" value=""
							data-options="required:true">
					</p>
				</div>
				<div class="fluidbox">
					<p class="p12 p-item">
						<label class="lab-item">安置人: </label> <input
							class="txt w200 easyui-validatebox" id="parent" name="parentName" value=""
							data-options="required:true">
					</p>
				</div>
				<div class="fluidbox">
					<p class="p12 p-item">
						<label class="lab-item">推荐人: </label> <input
							class="txt w200 easyui-validatebox" id="recommend" name="placeName" value=""
							data-options="required:true">
					</p>
				</div>
				<div class="fluidbox">
					<p class="p12 p-item">
						<label class="lab-item"><font class="red">*</font>会员级别: </label> 
						<select
							id="grade" class="drop" panelheight="auto" style="width: 100px"
							name="grade">
							<option value="1" >会员</option>
							<option value="2">代理</option>
						</select>
					</p>
				</div>

			</dd>
		</dl>
		<#--2.batch button-------------->
		<p class="p-item p-btn">
			<input type="button" id="memberEditOptOk" class="btn" value="确定" /> <input
				type="button" id="balanceOptCancel" class="btn" value="取消" />
		</p>
		</@form.form>
	</div>
</div>
