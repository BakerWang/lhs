
<#if cartInfoVO?? && (cartInfoVO.cartListVOs??) && (cartInfoVO.cartListVOs?size &gt; 0) >
<div class='incart-goods-box ps-container'>
	<div class='incart-goods'>
		<div class='sub-title'>
			<h4>最新加入的商品</h4>
		</div>
		<#list cartInfoVO.cartListVOs as cartListVO>
			<#if (cartListVO.cartList??) && (cartListVO.cartList?size>0) >
               <#list cartListVO.cartList as cart>
                   <#assign product = cart.product />
                   <#assign productGoods = cart.productGoods />
				<dl>
					<dt class='shop-googs-name'>
						<a href='${(domainUrlUtil.EJS_URL_RESOURCES)!}/product/${cart.productId!0}.html' target="_blank">${(product.name1)!'' }&nbsp;${(cart.specInfo)!'' }</a>
					</dt>
					<dd class='mcart-mj'>
						<a href='' title="${(product.name1)!'' }">
						<img src="${(domainUrlUtil.EJS_IMAGE_RESOURCES)!}${(product.masterImg)!''}"></a>
					</dd>
					<dd class='mcart-price'>
						<em>¥${productGoods.mallPcPrice!0}×${(cart.count)!0}</em>
					</dd>
					<!-- <dd class='handle'>
						<em><a href='javascript:void(0);'>删除</a></em>
					</dd> -->
				</dl>
			</#list>
			</#if>
		</#list>
	</div>
</div>
<div class='checkout'>
	<span class='checkout-price'> 共<i>${(cartInfoVO.totalNumber)!0}</i>种商品&nbsp;&nbsp;总计金额： <em>¥${cartInfoVO.cartAmount!'0.00'}</em>
	</span>
	<span>
		<a href='${(domainUrlUtil.EJS_URL_RESOURCES)!}/cart/detail.html' class='btn btn-danger' target='_blank' style='color: #fff; padding: 4px 9px; margin-top: 10px;'>去购物车</a>
	</span>
</div>
<input type="hidden" id="totalNumber" name="totalNumber" value="${cartInfoVO.totalNumber!0}"/>
<#else>
	<!-- 如果没有商品的话显示这个 -->
	<div class='no-order'>
		<div class="emptycart">
	      <div class="emptycart_line"></div>
	      <div class="emptycart_txt"><img src="${(domainUrlUtil.EJS_STATIC_RESOURCES)!}/img/settleup-nogoods.png" alt="">购物车中还没有商品，赶紧选购吧</div>
	   </div>
	</div>
	<input type="hidden" id="totalNumber" name="totalNumber" value="0"/>
</#if>

