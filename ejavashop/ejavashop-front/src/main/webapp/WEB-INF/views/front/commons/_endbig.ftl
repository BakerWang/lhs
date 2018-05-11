  		<!-- footer -->
		<!-- 页脚 -->
		<div class='footer '>
			<div class='wraper '>
				<ul id="newstypes">
				
				</ul>
			</div>
		</div>
		<div class='wraper' id='footer'>
			<p>
				<a href='${(domainUrlUtil.EJS_URL_RESOURCES)!}/index.html'>首页</a>
						|
				<a href=''>招聘英才</a>
						|
				<a href=''>合作及洽谈</a>
						|
				<a href=''>联系我们</a>
						|
				<a href='http://wwww.ejavashop.com'>关于ejavashop</a>
			</p>

  				Copyright 2015 ejavashop Inc.,All rights reserved.
  			<br>

  				Powered by 
  				<span class='vol'>
  					<font class='b'>ejavashop</font>
  					<font class='o'></font>
  				</span>
		</div>
		<!-- footer -->
		<script type="text/javascript">
			$(function(){
				$.ajax({
					url:'${(domainUrlUtil.EJS_URL_RESOURCES)!}/news/footerNews.html',
					dataType: "json",
        			cache:false,
        			type:"get",
					success:function(data){
						if (data.success) {
							var html = "";
							$.each(data.data, function(i, newstype){
								html += "<li><dl>"+
										"<dt>"+newstype.name+"</dt>";
										$.each(newstype.news,function(nidx,nw){
											html += 
											"<dd>"+
												"<i></i>"+
												"<a href='${(domainUrlUtil.EJS_URL_RESOURCES)!}/news/"+
														nw.id+".html' title='"+nw.title+"' target='_blank'>"+nw.title+"</a>"+
											"</dd>";
										});
								html += "</dl></li>";
							});
							$("#newstypes").html(html);
						}
					}
				});
				
				$.ajax({
					url:'${(domainUrlUtil.EJS_URL_RESOURCES)!}/searchKeyword.html',
					dataType: "json",
        			cache:false,
        			type:"get",
					success:function(data){
						if (data.success) {
							var html = "";
							$.each(data.data, function(i, kd){
								html += "<a href='${(domainUrlUtil.EJS_URL_RESOURCES)!}/search.html?keyword=" + kd + "' target='_blank'>" + kd + "</a>";
							});
							$("#keywordIDs").html(html);
						}
					}
				});
				
			});
		</script>
  </body> 
</html>


<script type="text/javascript">
var memberId = 0;
<#if user??>
	memberId = ${(user.id)!0};
</#if>
function getBrowserInfo() {
	var agent = navigator.userAgent.toLowerCase() ;
	var regStr_ie = /msie [\d.]+;/gi;
	var regStr_ff = /firefox\/[\d.]+/gi;
	var regStr_chrome = /chrome\/[\d.]+/gi;
	var regStr_saf = /safari\/[\d.]+/gi;
	
	if(agent.indexOf("msie") > 0) {
		return agent.match(regStr_ie) ;
	}

	//firefox
	if(agent.indexOf("firefox") > 0) {
		return agent.match(regStr_ff) ;
	}

	//Chrome
	if(agent.indexOf("chrome") > 0) {
		return agent.match(regStr_chrome) ;
	}

	//Safari
	if(agent.indexOf("safari") > 0 && agent.indexOf("chrome") < 0) {
		return agent.match(regStr_saf) ;
	}
}
var browser = getBrowserInfo() ;
var verinfo = (browser+"").replace(/[^0-9.]/ig,"");

var ref = document.referrer;
var hrf = window.location.href;
document.write('<img width="1" height="1" style="position:absolute;" src="${(domainUrlUtil.EJS_URL_RESOURCES)!}/browse_Logs.html?ref='+ref+'&hrf='+ hrf + '&memberId='+ memberId + '&browser='+ browser + '&verinfo=' + verinfo + '" />');
</script>
