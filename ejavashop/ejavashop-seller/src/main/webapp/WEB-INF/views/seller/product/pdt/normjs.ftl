<script language="javascript">
    $(function () {
        <#if product?? && (product.isNorm)?string == "2">
        $('#normDiv').show();
        $(".sku").hide();
        </#if>
        //是否启用规格
        $("input[name^='isNorm']").change(function () {
            if (2 == $(this).val()) {
                $('#normDiv').show();
                $('.sku').hide();
            } else {
                $('#normDiv').hide();
                $('.sku').show();
            }
        });
        $('.styleStock').live('blur', function () {
            if($(this).val() == ''){
                $(this).val(0);
            }
        });
        $('.stylePrice').live('blur', function () {
            if($(this).val() == ''){
                $(this).val('0.0');
            }
            if(parseFloat($(this).val()) < parseFloat($('#protectedPrice').val())){
                alert("货品价格不能小于保护价");
                $(this).focus();
            }
        });
        $('.styleStock').live('focus', function () {
            if($(this).val() == '0'){
                $(this).val('');
            }
        });
        $('.stylePrice').live('focus', function () {
            if($(this).val() == '0.0'){
                $(this).val('');
            }
        });

        //自定义规格事件
        var attrMap = {};
        <#if normList?? && normList?size == 2>
        <#assign map0 = normList[0]>
        <#assign map1 = normList[1]>
        <#assign title0 = map0.name>
        <#assign title1 = map1.name>
        <#assign list0 = map0.attrList>
        <#assign list1 = map1.attrList>
        <#list list0 as list0>
        <#assign id0 = list0.id>
        <#assign name0 = list0.name>
        <#if list0.checked?? && (list0.checked)?string=='true'>
        if (0 in attrMap) {
            attrMap[0]["names"].push('attr_${list0_index}');
        } else {
            attrMap[0] = {};
            attrMap[0]["title"] = '${title0!''}';
            attrMap[0]["names"] = new Array();
            attrMap[0]["names"].push('attr_${list0_index}');
        }
        </#if>
        </#list>

        <#list list1 as list1>
        <#assign id1 = list1.id>
        <#assign name1 = list1.name>
            <#if list1.checked?? && (list1.checked)?string=='true'>
            if (1 in attrMap) {
                attrMap[1]["names"].push('attr_${list1_index}');
            } else {
                attrMap[1] = {};
                attrMap[1]["title"] = '${title1!''}';
                attrMap[1]["names"] = new Array();
                attrMap[1]["names"].push('attr_${list1_index}');
            }
            </#if>
        </#list>

        </#if>
        $('input[name^="attr_"]').live('change', function () {
            var attrType = $(this).attr("attrtype");
            var name = $(this).attr("name");
            if ($(this).attr("checked")) {
                if (attrType in attrMap) {
                    attrMap[attrType]["names"].push(name);
                } else {
                    attrMap[attrType] = {};
                    var title = $($(this).parent().parent().parent().children()[0]).text().trim();
                    title = title.substring(0, title.length - 1);
                    attrMap[attrType]["title"] = title;
                    attrMap[attrType]["names"] = new Array();
                    attrMap[attrType]["names"].push(name);
                }
            } else {
                if (attrType in attrMap) {
                    attrMap[attrType]["names"].remove(name);
                    if (attrMap[attrType]["names"].length == 0) {
                        delete attrMap[attrType];
                    }
                }
            }

            var columnName = '';//循环构造列名
            var keys = Object.keys(attrMap);
            if (keys && keys.length > 0) {
                for (var i = 0; i < keys.length; i++) {
                    columnName += "<td width=\"10%\" style=\"padding:6px;\">" + attrMap[keys[i]]["title"] + "</td>";
                }
            }

            var htmlArray = new Array();
            getTrArray(htmlArray, '', attrMap, 0);

            $('div[name="dyTable"]').remove();//删除动态表格
            var tableHtml = "<div class=\"fluidbox\" name=\"dyTable\">" +
                    "<table name=\"normTable\" width=\"86%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" style=\"margin-left:140px\">" +
                    "<tbody>" +
                    "<tr style=\"background:#CCC;border:1px solid #e2e1e1;font-size:12px;\">" +
                    columnName +
                    "<td width=\"15%\" style=\"padding:6px;\">sku</td>" +
                    "<td width=\"15%\" style=\"padding:6px;\">库存</td>" +
                    "<td width=\"15%\" style=\"padding:6px;\">PC价格</td>" +
                    "<td width=\"15%\">mobile价格</td></tr>";

            if (htmlArray && htmlArray.length > 0) {
                for (var i = 0; i < htmlArray.length; i++) {
                    tableHtml += htmlArray[i];
                }
            }
            
            tableHtml += "</tbody></table></div>";
            $('#normDiv').append(tableHtml);
        });


        <#--规格属性编辑,只有一列选中的时候,第二列禁用-->
        var attrType0 = $("input[attrtype='0'");
        var attrType1 = $("input[attrtype='1'");
        var isReadOnly0 = false;
        var isReadOnly1 = false;
        if(attrType0 && attrType0.length > 0){
            for(var i = 0; i < attrType0.length; i ++){
                isReadOnly0 = $(attrType0[i]).is(':checked');
                if (isReadOnly0){
                    break;
                }
            }
        }
        if(attrType1 && attrType1.length > 0){
            for(var i = 0; i < attrType1.length; i ++){
                isReadOnly1 = $(attrType1[i]).is(':checked');
                if (isReadOnly1){
                    break;
                }
            }
        }

        if(!isReadOnly0 && isReadOnly1){
            for(var i=0; i < $("input[attrtype='0'").length; i ++){
                var attrtype = $("input[attrtype='0'")[i];
                $(attrtype).attr('disabled','true');
            }
        }

        if(isReadOnly0 && !isReadOnly1){
            for(var i=0; i < $("input[attrtype='1'").length; i ++){
                var attrtype = $("input[attrtype='1'")[i];
                $(attrtype).attr('disabled','true');
            }
        }
    })

    function getTrArray(html, tds, map, level) {
        var keys = Object.keys(map);
        if (keys && keys.length > 0) {
            var names = map[keys[level].toString()]["names"];
            for (var i = 0; i < names.length; i++) {
                var text = $("[name=" + names[i] + "][attrtype=" + keys[level].toString() + "]").parent().text().trim();
                var value = $("[name=" + names[i] + "][attrtype=" + keys[level].toString() + "]").parent().find('input').val();
                if (level == keys.length - 1) {
                    var tmptds = tds + "<td style='padding:10px'>" + text +
                            "<input name=\"normAttrId\" type=\"hidden\" value=\""+value+"\">" +
                            "</td>";
                    html.push(
                            "<tr style=\"border:1px solid #e2e1e1\" name=\"normAttrTr\">" + tmptds +
                                "<td style=\"padding:10px;\">" +
                                    "<input name=\"\" type=\"text\" id=\"inventory_details_sku_"+i+"\" style=\"border:1px solid #A7A6AA; height:20px; line-height:20px; padding-left:5px;margin-bottom:5px;\" value=\"\" class=\"styleSku\">" +
                                "</td>" +
                                "<td>" +
                                    "<input name=\"\" type=\"text\" id=\"inventory_details_stock_"+i+"\" style=\"border:1px solid #A7A6AA; height:20px; line-height:20px; padding-left:5px;margin-bottom:5px;\" value=\"0\" class=\"styleStock\">" +
                                "</td>" +
                                "<td>" +
                                    "<input name=\"\" type=\"text\" id=\"inventory_details_pprice_"+i+"\" style=\"border:1px solid #A7A6AA; height:20px; line-height:20px; padding-left:5px;margin-bottom:5px;\" value=\"0.0\" class=\"stylePrice\">" +
                                "</td>" +
                                "<td>" +
                                    "<input name=\"\" type=\"text\" id=\"inventory_details_mprice_"+i+"\" style=\"border:1px solid #A7A6AA; height:20px; line-height:20px; padding-left:5px;margin-bottom:5px;\" value=\"0.0\" class=\"stylePrice\">" +
                                "</td>" +
                            "</tr>");
                } else {
                    tds +=  "<td style='padding:10px'>" + text +
                            "<input name=\"normAttrId\" type=\"hidden\" value=\""+value+"\">" +
                            "</td>";
                    getTrArray(html, tds, map, level + 1);
                }
                if (level == 0) {
                    tds = '';
                }
            }
        }
    }

    Array.prototype.remove = function (val) {
        var index = this.indexOf(val);
        if (index > -1) {
            this.splice(index, 1);
        }
    };
    Array.prototype.indexOf = function (val) {
        for (var i = 0; i < this.length; i++) {
            if (this[i] == val) return i;
        }
        return -1;
    };
</script>