<#macro menuTree menus> 
  <#if menus?? && menus?size gt 0> 
   <#list menus as menu> 
    <tr> 
     <td> 
      <input type="checkbox" name="ids" value="${menu.id}" /> 
     </td> 
     <td> 
   <span title="${menu.name!}" style="margin-left: ${menu.leaf * 30}px;[#if menu.leaf == 0] color: #000000;[/#if]"> 
   ${menu.menuName!} 
   </span> 
     </td> 
     <td> 
     ${menu.url!} 
     </td> 
     <td> 
     ${menu.permissionText!} 
     </td> 
     <td> 
     ${menu.sortNo!} 
     </td> 
     <td> 
      <a href="edit.jhtml?id=${menu.id}">[编辑]</a> 
     </td> 
    </tr> 
   <#if menu.menuBeans?? && menu.menuBeans?size gt 0> 
    <@menuTree menus = menu.menuBeans/> 
   </#if> 
   </#list> 
  </#if> 
 </#macro> 
 <!-- 调用宏 生成递归树 -->
<@menuTree menus = dto /> 