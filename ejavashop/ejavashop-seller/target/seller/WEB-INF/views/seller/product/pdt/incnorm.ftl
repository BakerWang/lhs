                    <div class="fluidbox">
                        <p class="p12 p-item">
                            <label class="lab-item"><font class="red">*</font>是否启用规格: </label>
                            <#if edit??>
                            <@cont.radio1 id="isNorm" value="${(product.isNorm)!''}" codeDiv="PRODUCT_IS_NORM"/>
                            <input type="hidden" name="isNormHidden" id="isNormHidden" value="${(product.isNorm)!''}"/>
                            <#else>
                            <@cont.radio id="isNorm" value="${(product.isNorm)!''}" codeDiv="PRODUCT_IS_NORM"/>
                            </#if>
                            <input type="hidden" name="productGoods" id="productGoods" />
                        </p>
                    </div>
                    <div class="fluidbox">
                        <p class="p12 p-item">
                            <label class="lab-item">&nbsp;</label>
                            <font style="color: #808080">
                                商品新建之后规格永久固定，不能编辑规格。
                            </font>
                        </p>
                    </div>
                    <div id="normDiv" style="display: none">
                        <#if normList?? && normList?size &gt; 0>
                            <#list normList as norm>
                            <#assign i = 0>
                            <div class="fluidbox">
                                <p class="p12 p-item" style="width: 5%;">
                                    <label class="lab-item">${(norm.name)!''}: </label>
                                </p>
                                <#if norm.attrList?? && norm.attrList?size &gt; 0>
                                <ul style="padding-left: 72px;float:left;">
                                    <#list norm.attrList as attr>
                                    <li style="float: left;list-style: none;width: 100px;">
                                        <input name="attr_${attr_index}" type="checkbox" id="attr_${attr_index}" attrtype="${norm_index}" value="${(attr.id)!''}" <#if edit??> disabled </#if> <#if (attr.checked)?? && (attr.checked)?string == "true">checked</#if>>${(attr.name)!''}
                                    </li>
                                    </#list>
                                </ul>
                                </#if>
                            </div>
                            </#list>
                        </#if>

                            <div class="fluidbox" name="dyTable">
                                <table width="86%" border="0" cellspacing="0" cellpadding="0" style="margin-left:140px">
                                    <tbody>
                                    <tr style="background:#CCC;border:1px solid #e2e1e1;font-size:12px;">
                                        <#if column?? && column?size &gt; 0>
                                        <#list column as col>
                                            <td width="15%" style="padding:6px;">${col!''}</td>
                                        </#list>
                                        </#if>
                                        <td width="15%" style="padding:6px;">sku</td>
                                        <td width="15%" style="padding:6px;">库存</td>
                                        <td width="15%">PC价格</td>
                                        <td width="15%">mobile价格</td>
                                    </tr>
                                    <#if goods?? && goods?size &gt; 0>
                                    <#list goods as good>
                                    <tr style="border:1px solid #e2e1e1" name="normAttrTr">
                                        <#if good.normId1??>
                                        <td style="padding:6px">
                                            ${(good.normName1)!''}<input name="normAttrId" type="hidden" value="${(good.normId1)!''}">
                                        </td>
                                        </#if>
                                        <#if good.normId2??>
                                            <td style="padding:6px">${(good.normName2)!''}<input name="normAttrId" type="hidden" value="${(good.normId2)!''}">
                                            </td>
                                        </#if>
                                        <td style="padding:6px;">
                                            <input name="" type="text" id="inventory_details_count_" style="border:1px solid #A7A6AA; height:20px; line-height:20px; padding-left:5px;margin-bottom:5px;" value="${(good.sku)!''}" class="styleSku">
                                        </td>
                                        <td>
                                            <input name="" type="text" id="inventory_details_count_" style="border:1px solid #A7A6AA; height:20px; line-height:20px; padding-left:5px;margin-bottom:5px;" value="${(good.stock)!''}" class="styleStock">
                                        </td>
                                        <td>
                                            <input name="" type="text" id="inventory_details_price_" style="border:1px solid #A7A6AA; height:20px; line-height:20px; padding-left:5px;margin-bottom:5px;" value="${(good.pcPrice)!''}" class="stylePrice">
                                        </td>
                                        <td>
                                            <input name="" type="text" id="inventory_details_price_" style="border:1px solid #A7A6AA; height:20px; line-height:20px; padding-left:5px;margin-bottom:5px;" value="${(good.mobilePrice)!''}" class="stylePrice">
                                        </td>
                                    </tr>
                                    </#list>
                                    </#if>
                                    </tbody>
                                </table>
                            </div>

                    </div>

                    <div class="fluidbox sku">
                        <p class="p12 p-item">
                            <label class="lab-item"><font class="red">*</font>sku: </label>
                            <input type="text" id="sku" name="sku" value="${(product.sku)!''}" class="txt w200 "/>
                        </p>
                    </div>