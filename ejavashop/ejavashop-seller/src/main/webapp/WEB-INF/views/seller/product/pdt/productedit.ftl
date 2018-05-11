<#include "/seller/commons/_detailheader.ftl" />
<#assign currentBaseUrl="${domainUrlUtil.EJS_URL_RESOURCES}/seller/product/"/>

<#include "productimgcss.ftl"/>

<script language="javascript">
    $(function () {

    <#if (product.description)??>
        UM.getEditor('myEditor').setContent(<#noescape>'${(product.description)}'</#noescape>, true);
    </#if>

    <#--加载图片上传控件-->
        $("[name=uploadImg]").multiupload();
        var backUrl = "${currentBaseUrl}";
        var options = {
            url: '${currentBaseUrl}update',
            type: 'post',
            success: function (data) {
            if (data && null != data.success && data.success == true) {
                alert("修改商品成功");
                window.location.href='${domainUrlUtil.EJS_URL_RESOURCES}/seller/product/waitSale';
            }else if(data.backUrl != null){
                alert(data.message);
                window.location.href=data.backUrl;
            }else{
                refrushCSRFToken(data.csrfToken);
                $.messager.alert('提示',data.message);
            }
            }
        };
        $("#back").click(function () {
            window.location.href = '${domainUrlUtil.EJS_URL_RESOURCES}/seller/product/waitSale';
        });
        $("#add").click(function () {
        <#--货品信息-->
            var isNorm = $('input[name="isNorm"]').filter(':checked').val();
            if (isNorm == 2){
                var normAttrTr = $('tr[name="normAttrTr"]');
                if(normAttrTr && normAttrTr.length > 0){
                    var productGoods = '';
                    for(var i = 0; i < normAttrTr.length; i ++){
                        var normAttrTrInput = $(normAttrTr[i]).find('input');
                        if(normAttrTrInput && normAttrTrInput.length > 0){
                            var normAttrTrInputVal = '';
                            for(var j = 0; j < normAttrTrInput.length; j++){
                                normAttrTrInputVal += $(normAttrTrInput[j]).val() + ',';
                            }
                            normAttrTrInputVal = normAttrTrInputVal.substr(0, normAttrTrInputVal.length -1);
                            productGoods += normAttrTrInputVal + ';';
                        }
                    }
                    productGoods = productGoods.substr(0, productGoods.length -1);
                    $('#productGoods').val(productGoods);
                }
            }
            //var name1 = $('#name1').val();//商品名称
            //$('#productBrandId').val($('#brandId').val());//品牌id
            //var productCateId = $('#productCateId').val();//商品分类id
            //var name2 = $('#name2').val();//促销信息
            //var keyword = $('#keyword1').val();//关键字
            var costPrice = $('#costPrice').val();//成本价
            var protectedPrice = $('#protectedPrice').val();//保护价
            var marketPrice = $('#marketPrice').val();//市场价
            var mallPcPrice = $('#mallPcPrice').val();//pc商城价
            var virtualSales = $('#virtualSales').val();//虚拟销量
            var actualSales = $('#actualSales').val();//实际销量
            var todayLimitNum = $('#todayLimitNum').val();//商品库存
            var upTime = $('#upTime').val();//上架时间
            var isInventedProduct = $("input[name='isInventedProduct'][checked]").val();//是否虚拟商品
            var description = UM.getEditor('myEditor').getContent();
            if(description == ''){
                $.messager.alert('提示',"请填写商品描述");
                return;
            }
            $('#description').val(description);//商品描述信息
            var packing = $('#packing').val();//包装清单
            if(packing == ''){
                $.messager.alert('提示',"请填写包装清单");
                return;
            }

            var sellerCateId = $("select[name^='sellerCate_']").last().val();//商家分类

        <#--商品图片-->
            var image = $('img[name^=prev_]');
            if (image && image.length > 0) {
                var imageSrc = '';
                for (var i = 0; i < image.length; i++) {
                    var imgSrc = $(image[i]).attr('src');
                    if (imgSrc.indexOf("resources") != -1)
                        continue;
                    imageSrc += imgSrc + ';';
                }
                if ('' != imageSrc) {
                    $('#imageSrc').val(imageSrc);//商品图片
                }
            }else{
                $.messager.alert('提示',"商品图片,至少传一张");
                return;
            }

        <#--商品属性-->
            var queryType = $('select[name=queryType]');
            if (queryType && queryType.length > 0) {
                var queryTypeVal = '';
                for (var i = 0; i < queryType.length; i++) {
                    queryTypeVal += $(queryType[i]).val() + ';';
                }
                $('#queryType').val(queryTypeVal);//商品属性
            }
        <#--自定义属性-->
            var custType = $('input[name=custType]');
            if (custType && custType.length > 0) {
                var custTypeVal = '';
                for (var i = 0; i < custType.length; i++) {
                    custTypeVal += $(custType[i]).attr('data') + ',' + $(custType[i]).val() + ';';
                }
                $('#custAttr').val(custTypeVal);//自定义属性
            }

            if (upTime == '') {
                $.messager.alert('提示', '上架时间必填。');
                return;
            }
                if (mallPcPrice < protectedPrice) {
                    $.messager.alert('提示', '商城价不能低于保护价');
                    return;
                }
            if(sellerCateId == null || sellerCateId == "-1" || sellerCateId == ""){
                $.messager.alert('提示', '请选择商家分类');
                return;
            }else{
                $('#sellerCateId').val(sellerCateId);
            }

            if ($('#addForm').form('validate')) {
                $('#addForm').ajaxSubmit(options);
            }
        });

        $('#mallPcPrice').blur(function () {
            var mallPcPrice = $(this).val();//pc商城价
            var protectedPrice = $('#protectedPrice').val();//保护价
            if (mallPcPrice && protectedPrice && mallPcPrice < protectedPrice) {
                $.messager.alert('提示', '商城价不能低于保护价');
                return;
            }
        });
        $('#protectedPrice').blur(function () {
            var mallPcPrice = $('#mallPcPrice').val();//pc商城价
            var protectedPrice = $(this).val();//保护价
            if (mallPcPrice && protectedPrice && mallPcPrice < protectedPrice) {
                $.messager.alert('提示', '商城价不能低于保护价');
                return;
            }
        });

    <#--鼠标移入移出图片-->
        $('.img').live('mouseover', function () {
            $(this).find('.img-box').show();
        }).live('mouseout', function () {
            $(this).find('.img-box').hide();
        })
    <#--删除图片-->
        $('.del-img').live('click', function () {
            $(this).parent().parent().parent().remove();
            $('[name=fileIndex]').val($('[name=fileIndex]').val() - 1);
            if ($('[name=fileIndex]').val() == 0) {
                $('#previewImgBox').hide();
            }
        })
    <#if pic?? && pic?size &gt; 0>
        $('#previewImgBox').show();
    </#if>
        $("select[name^='sellerCate_']").live("change", function () {
            var level = $(this).attr("level");
            var id = $(this).attr("id");
            var parentId = $(this).val();

            $("select[name^='sellerCate_']").each(function () {
                var subLevel = $(this).attr("level");
                if (parseInt(subLevel) > parseInt(level)) {
                    $(this).remove();
                }
            })
            $.ajax({
                type: "get",
                url: "${currentBaseUrl}sellerCate/getByPid?id=" + parentId,
                dataType: "json",
                cache: false,
                success: function (data) {
                    if (data && data.length > 0) {
                        $('#sellerCate_1').remove();
                        var children = "<select id='sellerCate_" + parseInt(level + 1) + "' name='sellerCate_" + parseInt(level + 1) + "' level=" + parseInt(level + 1) + " class='w210'>";
                        children += "<option value=''>请选择</option>";
                        $.each(data, function (i, n) {
                            children += "<option value=" + n.id + ">" + n.name + "</option>";
                        })
                        children += "</select>"
                        $('#' + id).after('&nbsp;&nbsp;&nbsp;' + children);
                    }
                }
            });
        });
    })
    function callback1(result) {
        //console.log("1"+result.names[0]);
    }
</script>
<#include "normjs.ftl"/>

<div class="wrapper">
    <div class="formbox-a">
        <h2 class="h2-title">发布商品-修改商品详细信息
            <span class="s-poar">
                <a class="a-back" href="${domainUrlUtil.EJS_URL_RESOURCES}/seller/product/waitSale">返回</a>
            </span>
        </h2>

    <#--1.addForm----------------->
        <div class="form-contbox">
        <@form.form method="post" class="validForm" id="addForm" name="addForm">
            <input type="hidden" name="id" value="#{product.id}"/>
            <dl class="dl-group">
                <dt class="dt-group"><span class="s-icon"></span>商品基本信息</dt>
                <dd class="dd-group">
                    <div class="fluidbox">
                        <p class="p12 p-item">
                            <label class="lab-item"><font class="red">*</font>商品分类: </label>
                            <#if catePath??>
                            ${catePath!''}
                            </#if>
                            <input type="hidden" id="productCateId" name="productCateId" value="${(product.productCateId)!''}"/>
                        </p>
                    </div>

                    <div class="fluidbox">
                        <p class="p12 p-item">
                            <label class="lab-item"><font class="red">*</font>商品品牌: </label>
                            <#if brand??>
                                <select id="brandId" name="brandId" level="0" class="w210">
                                        <option>${brand!''}</option>
                                </select>
                            </#if>
                            <input type="hidden" id="productBrandId" name="productBrandId" value="${(product.productBrandId)!''}"/>
                        </p>
                    </div>

                    <div class="fluidbox">
                        <p class="p12 p-item">
                            <label class="lab-item"><font class="red">*</font>商品名称: </label>
                            <input type="text" id="name1" name="name1" value="${(product.name1)!''}" class="txt w200 easyui-validatebox" missingMessage="商品名称必填，3-100个字符" data-options="required:true,validType:'length[3,100]'"/>
                        </p>
                    </div>

                    <div class="fluidbox">
                        <p class="p12 p-item">
                            <label class="lab-item">&nbsp;</label>
                            <font style="color: #808080">
                                商品标题名称长度至少3个字符，最长100个字符
                            </font>
                        </p>
                    </div>
                    
                    <div class="fluidbox">
                        <p class="p12 p-item">
                            <label class="lab-item"><font class="red">*</font>SPU: </label>
                            <input type="text" id="productCode" name="productCode" value="${(product.productCode)!''}" class="txt w200 easyui-validatebox" missingMessage="SPU必填，3-20个字符" data-options="required:true,validType:'length[3,20]'"/>
                        </p>
                    </div>

                    <div class="fluidbox">
                        <p class="p12 p-item">
                            <label class="lab-item">&nbsp;</label>
                            <font style="color: #808080">
                                商品编码名称长度至少3个字符，最长20个字符
                            </font>
                        </p>
                    </div>

                    <div class="fluidbox">
                        <p class="p12 p-item">
                            <label class="lab-item"><font class="red">*</font>关键字: </label>
                            <input type="text" id="keyword1" name="keyword1" value="${(product.keyword)!''}" class="txt w200 easyui-validatebox" missingMessage="关键字必填，2-50个字符" data-options="required:true,validType:'length[2,50]'"/>
                        </p>
                    </div>

                    <div class="fluidbox">
                        <p class="p12 p-item">
                            <label class="lab-item">&nbsp;</label>
                            <font style="color: #808080">
                                商品关键字，用户检索检索商品，多个用逗号分割
                            </font>
                        </p>
                    </div>

                    <div class="fluidbox">
                        <p class="p12 p-item">
                            <label class="lab-item"><font class="red">*</font>促销信息: </label>
                            <input type="text" id="name2" name="name2" value="${(product.name2)!''}" class="txt w200 easyui-validatebox" missingMessage="促销信息必填，2-100个字符" data-options="required:true,validType:'length[2,100]'"/>
                        </p>
                    </div>

                    <div class="fluidbox">
                        <p class="p12 p-item">
                            <label class="lab-item"><font class="red">*</font>成本价: </label>
                            <input type="text" id="costPrice" name="costPrice" value="${(product.costPrice)!''}" class="txt w200 easyui-numberbox" data-options="min:1,max:99999,precision:2,required:true"/>
                        </p>
                    </div>

                    <div class="fluidbox">
                        <p class="p12 p-item">
                            <label class="lab-item">&nbsp;</label>
                            <font style="color: #808080">
                                价格必须是0.00~9999999之间的数字，此价格为商户对所销售的商品实际成本价格进行备注记录，非必填选项，不会在前台销售页面中显示
                            </font>
                        </p>
                    </div>

                    <div class="fluidbox">
                        <p class="p12 p-item">
                            <label class="lab-item"><font class="red">*</font>保护价: </label>
                            <input type="text" id="protectedPrice" name="protectedPrice" value="${(product.protectedPrice)!''}" class="txt w200 easyui-numberbox" data-options="min:1,max:99999,precision:2,required:true"/>
                        </p>
                    </div>

                    <div class="fluidbox">
                        <p class="p12 p-item">
                            <label class="lab-item"><font class="red">*</font>市场价: </label>
                            <input type="text" id="marketPrice" name="marketPrice" value="${(product.marketPrice)!''}" class="txt w200 easyui-numberbox" data-options="min:1,max:99999,precision:2,required:true"/>
                        </p>
                    </div>

                    <div class="fluidbox">
                        <p class="p12 p-item">
                            <label class="lab-item">&nbsp;</label>
                            <font style="color: #808080">
                                价格必须是0.01~9999999之间的数字，此价格仅为市场参考售价，请根据该实际情况认真填写
                            </font>
                        </p>
                    </div>

                    <div class="fluidbox">
                        <p class="p12 p-item">
                            <label class="lab-item"><font class="red">*</font>商城价: </label>
                            <input type="text" id="mallPcPrice" name="mallPcPrice" value="${(product.mallPcPrice)!''}" class="txt w200 easyui-numberbox" data-options="min:1,max:99999,precision:2,required:true"/>
                        </p>
                    </div>

                    <div class="fluidbox">
                        <p class="p12 p-item">
                            <label class="lab-item">&nbsp;</label>
                            <font style="color: #808080">
                                价格必须是0.01~9999999之间的数字，且不能高于市场价。
                                此价格为商品实际销售价格，如果商品存在规格，该价格显示最低价格
                            </font>
                        </p>
                    </div>

                    <div class="fluidbox">
                        <p class="p12 p-item">
                            <label class="lab-item"><font class="red">*</font>商城价(mobile): </label>
                            <input type="text" id="malMobilePrice" name="malMobilePrice"
                                   value="${(product.malMobilePrice)!''}" class="txt w200 easyui-numberbox"
                                   data-options="min:1,max:99999,precision:2,required:true"/>
                        </p>
                    </div>

                    <div class="fluidbox">
                        <p class="p12 p-item">
                            <label class="lab-item">&nbsp;</label>
                            <font style="color: #808080">
                                价格必须是0.01~9999999之间的数字，且不能高于市场价。
                                此价格为手机商城商品实际销售价格，如果商品存在规格，该价格显示最低价格
                            </font>
                        </p>
                    </div>

                    <#if seller??>
                    <div class="fluidbox">
                        <p class="p12 p-item">
                            <label class="lab-item"><font class="red">*</font>虚拟销量: </label>
                            <input type="text" id="virtualSales" name="virtualSales" value="${(product.virtualSales)!''}" class="txt w200 easyui-numberbox" data-options="min:0,max:99999,precision:0,required:true"/>
                        </p>
                    </div>
                    </#if>

                    <#--<div class="fluidbox">-->
                        <#--<p class="p12 p-item">-->
                            <#--<label class="lab-item"><font class="red">*</font>实际销量: </label>-->
                            <#--<input type="text" id="actualSales" name="actualSales"-->
                                   <#--value="${(product.actualSales)!''}" class="txt w200"/>-->
                        <#--</p>-->
                    <#--</div>-->
                    <input type="hidden" id="actualSales" name="actualSales" value="${(product.actualSales)!''}">
                    <input type="hidden" id="commentsNumber" name="commentsNumber" value="${(product.commentsNumber)!''}">

                    <div class="fluidbox">
                        <p class="p12 p-item">
                            <label class="lab-item"><font class="red">*</font>商品库存: </label>
                            <input type="text" id="productStock" name="productStock"
                                   missingMessage="请输入商品库存"
                                   data-options="required:true"
                                   value="${(product.productStock)!''}" class="txt w200 easyui-numberbox"/>
                        </p>
                    </div>

                    <div class="fluidbox">
                        <p class="p12 p-item">
                            <label class="lab-item">&nbsp;</label>
                            <font style="color: #808080">
                                0~999999999之间的整数，用户显示在单品页下，发生交易，系统会自动计算库存
                            </font>
                        </p>
                    </div>

                    <div class="fluidbox">
                        <p class="p12 p-item">
                            <label class="lab-item"><font class="red">*</font>上架时间: </label>
                            <input type="text" id="upTime" name="upTime" value="${(product.upTime)?string('yyy-MM-dd HH:mm:ss')!''}" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" class="txt w200" data-options="required:true"/>
                        </p>
                    </div>

                    <div class="fluidbox">
                        <p class="p12 p-item">
                            <label class="lab-item">&nbsp;</label>
                            <font style="color: #808080">
                                商品预计上线销售时间
                            </font>
                        </p>
                    </div>

                    <div class="fluidbox">
                        <p class="p12 p-item">
                            <label class="lab-item">&nbsp;</label>
                            <font style="color: #808080">
                            </font>
                        </p>
                    </div>

                    <#--<div class="fluidbox">-->
                        <#--<p class="p12 p-item">-->
                            <#--<label class="lab-item"><font class="red">*</font>是否虚拟商品: </label>-->
                            <#--<@cont.radio id="isInventedProduct" value="${(product.isInventedProduct)!''}" codeDiv="YES_NO" />-->
                        <#--</p>-->
                    <#--</div>-->

                    <#--<div class="fluidbox">-->
                        <#--<p class="p12 p-item">-->
                            <#--<label class="lab-item">&nbsp;</label>-->
                            <#--<font style="color: #808080">-->
                                <#--虚拟商品不以实物方式出售，例如手机费-->
                            <#--</font>-->
                        <#--</p>-->
                    <#--</div>-->

                    <input type="hidden" name="sellerId" id="sellerId"/>
                    <div class="fluidbox">
                        <p class="p12 p-item">
                            <label class="lab-item"><font class="red">*</font>商家自有商品分类: </label>
                            <#if sellerCate?? && sellerCate?size&gt; 0>
                                <select id="sellerCate_0" name="sellerCate_0" missingMessage="请选择本店分类" data-options="required:true" level="0" class="w210" disabled>
                                    <option value="-1">请选择</option>
                                    <#list sellerCate as sellerCate>
                                        <option value="${sellerCate.id}" <#if cateId?? && (sellerCate.id) == cateId>selected</#if>>
                                        ${sellerCate.name}
                                        </option>
                                    </#list>
                                </select>
                            </#if>
                            <#if secondSellerCate?? && secondSellerCate?size &gt; 0>
                                <select id="sellerCate_1" name="sellerCate_1" missingMessage="请选择本店分类" data-options="required:true" level="0" class="w210" disabled>
                                    <option value="-1">请选择</option>
                                    <#list secondSellerCate as sellerCate>
                                        <option value="${sellerCate.id}" <#if secondCateId?? && (sellerCate.id) == secondCateId>selected</#if>>
                                        ${sellerCate.name}
                                        </option>
                                    </#list>
                                </select>
                            </#if>
                            <input type="hidden" id="sellerCateId" name="sellerCateId" value="${(product.sellerCateId)!''}"/>
                        </p>
                    </div>

                    <div class="fluidbox">
                        <p class="p12 p-item">
                            <label class="lab-item">&nbsp;</label>
                            <font style="color: #808080">
                                商品可以从属于店铺的多个分类之下，店铺分类可以由 "商家管理后台系统 -> 商品管理 -> 店铺分类" 中自定义
                            </font>
                        </p>
                    </div>

                    <div class="fluidbox">
                        <p class="p12 p-item">
                            <label class="lab-item"><font class="red">*</font>是否商家推荐: </label>
                            <@cont.radio id="sellerIsTop" value="${(product.sellerIsTop)!''}" codeDiv="PRODUCT_IS_TOP" />
                        </p>
                    </div>

                    <div class="fluidbox">
                        <p class="p12 p-item">
                            <label class="lab-item">&nbsp;</label>
                            <font style="color: #808080">
                                被推荐的商品会显示在店铺首页
                            </font>
                        </p>
                    </div>

                    <#include "incnorm.ftl"/>



                </dd>
            </dl>
        <dl class="dl-group">
            <dt class="dt-group"><span class="s-icon"></span>商品类型描述</dt>
        <dd class="dd-group">
            <#if queryTypeAttr?? && queryTypeAttr?size &gt; 0>
                <dl class="dl-group" style="margin:20px 10px 20px 10px">
                <dt class="dt-group"><span class="s-icon"></span>商品属性</dt>
                <input type="hidden" id="queryType" name="queryType"/>
                <#list queryTypeAttr as queryTypeAttr>
                    <dd class="dd-group">
                        <div class="fluidbox">
                            <p class="p12 p-item">
                                <label class="lab-item">${(queryTypeAttr.attrName)!''}: </label>
                                <select name="queryType" level="0" class="w210">
                                    <option value="${(queryTypeAttr.attrId)!''},${(queryTypeAttr.attrName)!''},-1">不限</option>
                                    <#list queryTypeAttr.attrValList as attr>
                                        <option value="${(queryTypeAttr.attrId)!''},${(queryTypeAttr.attrName)!''},${(attr)!''}" <#if (queryTypeAttr.dbAttr)?? && attr == (queryTypeAttr.dbAttr)>selected</#if>>${(attr)!''}</option>
                                    </#list>
                                </select>
                            </p>
                        </div>
                    </dd>
                </#list>
            </dl>
            </#if>
            <#if custTypeAttr?? && custTypeAttr?size &gt; 0>
                <dl class="dl-group" style="margin:20px 10px 10px 10px">
                    <dt class="dt-group"><span class="s-icon"></span>自定义属性</dt>
                    <input type="hidden" id="custAttr" name="custAttr"/>
                    <#list custTypeAttr as custTypeAttr>
                        <dd class="dd-group">
                            <div class="fluidbox">
                                <p class="p12 p-item">
                                    <label class="lab-item">${(custTypeAttr.attrName)!''}: </label>
                                    <input type="text" name="custType" data="${(custTypeAttr.attrId)!''},${(custTypeAttr.attrName)!''}" value="${(custTypeAttr.dbAttr)!''}" class="txt w200"/>
                                </p>
                            </div>
                        </dd>
                    </#list>
                </dl>
            </#if>
            </dd>
            </dl>
            <dl class="dl-group">
                <dt class="dt-group"><span class="s-icon"></span>商品详情描述</dt>
                <input type="hidden" id="description" name="description"/>
                <dd class="dd-group">
                    <div class="fluidbox">
                        <p class="p12 p-item">
                            <label class="lab-item"><font class="red">*</font>商品描述: </label>
                        <div style="padding-left: 140px;padding-top: 2px;"><#include "productdesc.ftl"/></div>
                        </p>
                    </div>
                    <div class="fluidbox">
                        <p class="p12 p-item">
                            <label class="lab-item"><font class="red">*</font>包装清单: </label>
                        <div style="padding-left: 140px">
                            <textarea id="packing" name="packing" rows="8" cols="83" value="${(product.packing)!''}">${(product.packing)!''}</textarea>
                        </div>
                        </p>
                    </div>
                </dd>
            </dl>
            <dl class="dl-group">
                <dt class="dt-group"><span class="s-icon"></span>商品图片</dt>
                <dd class="dd-group">
                    <span class="s-showbtn">
                        <div name="uploadImg" action="" index="${waitShow_index!''}" multiparam='{"url":"${currentBaseUrl}uploadFiles","validate":{"fileSize":{"value":2048000,"errMsg":"上传图片不允许超过2M"}, "fileMaxNum":{"value":5,"errMsg":"上传图片最多不能超过5张"},"fileType":{"value":"img","errMsg":"上传图片后缀只支持:image、gif、jpeg、jpg、png"}},"callback":"callback1"}' class="upload-img">
                            <a href="#" class="txt_white">添加图片</a>
                            <input type="hidden" name="fileIndex" value="<#if pic?? && pic?size &gt; 0>${pic?size}<#else>0</#if>"/>
                        </div>
                        <div class="fluidbox">
	                        <p class="p12 p-item">
	                            <label class="lab-item">&nbsp;</label>
	                            <font style="color: #808080">
	                                图片建议像素（或保持该比例）：宽800，高800
	                            </font>
	                        </p>
	                    </div>
                    </span>
                </dd>
                <!-- 预览图片 -->
                <dd id='previewImgBox' style="display: none">
                    <input type="hidden" id="imageSrc" name="imageSrc"/>
                    <ul class='preview-img' id="preview-img">
                        <li style="display: none" id="prewtemplage">
                            <div class='img'>
                                <img width='150' height='150'>
                                <div class='img-box'>
                                    <a class='del-img' href='javascript:void(0);'>删除</a>
                                </div>
                            </div>
                        </li>
                        <#if pic?? && pic?size &gt; 0>
                            <#list pic as pic>
                                <li>
                                    <div class='img'>
                                        <img id="prev_${pic_index}" name="prev_${pic_index}" src='${(pic.imagePath)!''}' width='150' height='150'>
                                        <div class='img-box'>
                                            <a class='del-img' href='javascript:void(0);'>删除</a>
                                        </div>
                                    </div>
                                </li>
                            </#list>
                        </#if>
                    </ul>
                </dd>
                <!-- end -->
            </dl>

            <dl class="dl-group">
                <dt class="dt-group"><span class="s-icon"></span>帮助</dt>
                <dd class="dd-group">
                    <div class="fluidbox">
                        <label class="lab-item">帮助信息。</label>
                    </div>
                </dd>
            </dl>

            <p class="p-item p-btn">
                <input type="button" id="add" class="btn" value="修改"/>
                <input type="button" id="back" class="btn" value="返回"/>
            </p>
        </@form.form>
        </div>
    </div>
</div>
<#include "/seller/commons/_detailfooter.ftl" />