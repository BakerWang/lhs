package com.ejavashop.web.controller.member;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ejavashop.core.ConstantsEJS;
import com.ejavashop.core.ConvertUtil;
import com.ejavashop.core.HttpJsonResult;
import com.ejavashop.core.Md5;
import com.ejavashop.core.PagerInfo;
import com.ejavashop.core.ServiceResult;
import com.ejavashop.core.WebUtil;
import com.ejavashop.core.exception.BusinessException;
import com.ejavashop.entity.member.Bonus;
import com.ejavashop.entity.member.BonusSettle;
import com.ejavashop.entity.member.Member;
import com.ejavashop.entity.member.MemberAddress;
import com.ejavashop.entity.member.MemberBalanceLogs;
import com.ejavashop.entity.member.MemberGradeIntegralLogs;
import com.ejavashop.entity.member.MemberGradeUpLogs;
import com.ejavashop.entity.system.SystemAdmin;
import com.ejavashop.service.member.IBonusService;
import com.ejavashop.service.member.IBonusSettleService;
import com.ejavashop.service.member.IMemberAddressService;
import com.ejavashop.service.member.IMemberBalanceLogsService;
import com.ejavashop.service.member.IMemberService;
import com.ejavashop.web.controller.BaseController;
import com.ejavashop.web.util.WebAdminSession;

/**
 * 会员管理controller
 *
 * @Filename: AdminMemberController.java
 * @Version: 1.0
 * @Author: 陈万海
 * @Email: chenwanhai@sina.com
 *
 */
@Controller
@RequestMapping(value = "admin/member/member")
public class AdminMemberController extends BaseController {

    @Resource(name = "memberService")
    private IMemberService            memberService;

    @Resource(name = "memberBalanceLogsService")
    private IMemberBalanceLogsService memberBalanceLogsService;

    @Resource(name = "memberAddressService")
    private IMemberAddressService     memberAddressService;
    @Resource(name = "bonusService")
    private IBonusService     bonusService;
    @Resource(name = "bonusSettleService")
    private IBonusSettleService     bonusSettleService;

    /**
     * 会员管理页面初始化controller接口
     * @param dataMap
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "", method = { RequestMethod.GET })
    public String index(Map<String, Object> dataMap) throws Exception {
        dataMap.put("pageSize", ConstantsEJS.DEFAULT_PAGE_SIZE);
        return "admin/member/member/memberlist";
    }

    /**
     * 会员管理页面查询按钮controller接口
     * @param request
     * @param response
     * @param dataMap
     * @return
     */
    @RequestMapping(value = "list", method = { RequestMethod.GET })
    public @ResponseBody HttpJsonResult<List<Member>> list(HttpServletRequest request,
                                                           HttpServletResponse response,
                                                           Map<String, Object> dataMap) {

        Map<String, String> queryMap = WebUtil.handlerQueryMap(request);
        PagerInfo pager = WebUtil.handlerPagerInfo(request, dataMap);
        ServiceResult<List<Member>> serviceResult = memberService.getMembers(queryMap, pager);
        if (!serviceResult.getSuccess()) {
            if (ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR.equals(serviceResult.getCode())) {
                throw new RuntimeException(serviceResult.getMessage());
            } else {
                throw new BusinessException(serviceResult.getMessage());
            }
        }

        HttpJsonResult<List<Member>> jsonResult = new HttpJsonResult<List<Member>>();
        jsonResult.setRows(serviceResult.getResult());
        jsonResult.setTotal(pager.getRowsCount());
        return jsonResult;
    }

    /**
     * 会员管理页面经验值积分制操作controller接口
     * @param memberGradeIntegralLogs
     * @param request
     * @param dataMap
     * @return
     */
    @RequestMapping(value = "valueopt", method = { RequestMethod.POST })
    public @ResponseBody HttpJsonResult<Boolean> valueOpt(MemberGradeIntegralLogs memberGradeIntegralLogs,
                                                          HttpServletRequest request) {

        HttpJsonResult<Boolean> jsonResult = new HttpJsonResult<Boolean>();
        //  参数校验
        if (memberGradeIntegralLogs.getMemberId() == null) {
            jsonResult.setMessage("会员ID不能为空，请重试！");
            return jsonResult;
        } else if (memberGradeIntegralLogs.getValue() == null) {
            jsonResult.setMessage("经验值或积分值不能为空，请重试！");
            return jsonResult;
        } else if (memberGradeIntegralLogs.getOptType() == null) {
            jsonResult.setMessage("动作类型不能为空，请重试！");
            return jsonResult;
        } else if (memberGradeIntegralLogs.getType() == null) {
            jsonResult.setMessage("操作类型不能为空，请重试！");
            return jsonResult;
        }

        ServiceResult<Boolean> serviceResult = memberService
            .updateMemberValue(memberGradeIntegralLogs);
        if (!serviceResult.getSuccess()) {
            if (ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR.equals(serviceResult.getCode())) {
                throw new RuntimeException(serviceResult.getMessage());
            } else {
                jsonResult.setMessage(serviceResult.getMessage());
                return jsonResult;
            }
        }
        jsonResult.setData(true);
        return jsonResult;
    }

    /**
     * 会员管理页面经验值积分制操作controller接口
     * @param memberGradeIntegralLogs
     * @param request
     * @param dataMap
     * @return
     */
    @RequestMapping(value = "balanceopt", method = { RequestMethod.POST })
    public @ResponseBody HttpJsonResult<Boolean> balanceOpt(MemberBalanceLogs memberBalanceLogs,
                                                            HttpServletRequest request) {
        HttpJsonResult<Boolean> jsonResult = new HttpJsonResult<Boolean>();
        //  参数校验
        if (memberBalanceLogs.getMemberId() == null) {
            jsonResult.setMessage("会员ID不能为空，请重试！");
            return jsonResult;
        } else if (memberBalanceLogs.getMoney() == null) {
            jsonResult.setMessage("变更金额不能为空，请重试！");
            return jsonResult;
        } else if (memberBalanceLogs.getState() == null) {
            jsonResult.setMessage("动作类型不能为空，请重试！");
            return jsonResult;
        }
        SystemAdmin adminUser = WebAdminSession.getAdminUser(request);
        memberBalanceLogs.setOptId(adminUser.getId());
        memberBalanceLogs.setOptName(adminUser.getName());

        ServiceResult<Boolean> serviceResult = memberService.updateMemberBalance(memberBalanceLogs);
        if (!serviceResult.getSuccess()) {
            if (ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR.equals(serviceResult.getCode())) {
                throw new RuntimeException(serviceResult.getMessage());
            } else {
                jsonResult.setMessage(serviceResult.getMessage());
                return jsonResult;
            }
        }
        jsonResult.setData(true);
        return jsonResult;
    }

    /**
     * 会员管理页面升级日志按钮controller接口
     * @param request
     * @param response
     * @param dataMap
     * @return
     */
    @RequestMapping(value = "uploglist", method = { RequestMethod.GET })
    public @ResponseBody HttpJsonResult<List<MemberGradeUpLogs>> upLogList(HttpServletRequest request,
                                                                           HttpServletResponse response,
                                                                           Map<String, Object> dataMap) {
        String memberIdStr = request.getParameter("memberId");
        PagerInfo pager = WebUtil.handlerPagerInfo(request, dataMap);
        ServiceResult<List<MemberGradeUpLogs>> serviceResult = memberService
            .getMemberGradeUpLogs(ConvertUtil.toInt(memberIdStr, 0), pager);
        if (!serviceResult.getSuccess()) {
            if (ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR.equals(serviceResult.getCode())) {
                throw new RuntimeException(serviceResult.getMessage());
            } else {
                throw new BusinessException(serviceResult.getMessage());
            }
        }

        HttpJsonResult<List<MemberGradeUpLogs>> jsonResult = new HttpJsonResult<List<MemberGradeUpLogs>>();
        jsonResult.setRows(serviceResult.getResult());
        jsonResult.setTotal(pager.getRowsCount());
        return jsonResult;
    }

    /**
     * 会员管理页面经验值日志和积分值日志按钮controller接口
     * @param request
     * @param response
     * @param dataMap
     * @return
     */
    @RequestMapping(value = "grdIntloglist", method = { RequestMethod.GET })
    public @ResponseBody HttpJsonResult<List<MemberGradeIntegralLogs>> grdIntLogList(HttpServletRequest request,
                                                                                     HttpServletResponse response,
                                                                                     Map<String, Object> dataMap) {
        String memberIdStr = request.getParameter("memberId");
        // 类型：1、经验值；2、积分
        String typeStr = request.getParameter("type");
        PagerInfo pager = WebUtil.handlerPagerInfo(request, dataMap);
        ServiceResult<List<MemberGradeIntegralLogs>> serviceResult = memberService
            .getMemberGradeIntegralLogs(ConvertUtil.toInt(memberIdStr, 0),
                ConvertUtil.toInt(typeStr, 0), pager);
        if (!serviceResult.getSuccess()) {
            if (ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR.equals(serviceResult.getCode())) {
                throw new RuntimeException(serviceResult.getMessage());
            } else {
                throw new BusinessException(serviceResult.getMessage());
            }
        }

        HttpJsonResult<List<MemberGradeIntegralLogs>> jsonResult = new HttpJsonResult<List<MemberGradeIntegralLogs>>();
        jsonResult.setRows(serviceResult.getResult());
        jsonResult.setTotal(pager.getRowsCount());
        return jsonResult;
    }

    /**
     * 会员管理页面经验值日志和积分值日志按钮controller接口
     * @param request
     * @param response
     * @param dataMap
     * @return
     */
    @RequestMapping(value = "balanceloglist", method = { RequestMethod.GET })
    public @ResponseBody HttpJsonResult<List<MemberBalanceLogs>> balanceLogList(HttpServletRequest request,
                                                                                HttpServletResponse response,
                                                                                Map<String, Object> dataMap) {
        String memberIdStr = request.getParameter("memberId");
        PagerInfo pager = WebUtil.handlerPagerInfo(request, dataMap);
        ServiceResult<List<MemberBalanceLogs>> serviceResult = memberBalanceLogsService
            .getMemberBalanceLogs(ConvertUtil.toInt(memberIdStr, 0), pager);
        if (!serviceResult.getSuccess()) {
            if (ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR.equals(serviceResult.getCode())) {
                throw new RuntimeException(serviceResult.getMessage());
            } else {
                throw new BusinessException(serviceResult.getMessage());
            }
        }

        HttpJsonResult<List<MemberBalanceLogs>> jsonResult = new HttpJsonResult<List<MemberBalanceLogs>>();
        jsonResult.setRows(serviceResult.getResult());
        jsonResult.setTotal(pager.getRowsCount());
        return jsonResult;
    }

    /**
     * 会员管理页面收货地址按钮controller接口
     * @param request
     * @param response
     * @param dataMap
     * @return
     */
    @RequestMapping(value = "addresslist", method = { RequestMethod.GET })
    public @ResponseBody HttpJsonResult<List<MemberAddress>> addressList(HttpServletRequest request,
                                                                         HttpServletResponse response,
                                                                         Map<String, Object> dataMap) {
        String memberIdStr = request.getParameter("memberId");
        PagerInfo pager = WebUtil.handlerPagerInfo(request, dataMap);
        ServiceResult<List<MemberAddress>> serviceResult = memberAddressService
            .getMemberAddresses(ConvertUtil.toInt(memberIdStr, 0), pager);
        if (!serviceResult.getSuccess()) {
            if (ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR.equals(serviceResult.getCode())) {
                throw new RuntimeException(serviceResult.getMessage());
            } else {
                throw new BusinessException(serviceResult.getMessage());
            }
        }

        HttpJsonResult<List<MemberAddress>> jsonResult = new HttpJsonResult<List<MemberAddress>>();
        jsonResult.setRows(serviceResult.getResult());
        jsonResult.setTotal(pager.getRowsCount());
        return jsonResult;
    }
    
    
    @RequestMapping(value = "edit", method = { RequestMethod.POST })
    public @ResponseBody HttpJsonResult<Boolean> edit(Member member,
                                                            HttpServletRequest request) {
        HttpJsonResult<Boolean> jsonResult = new HttpJsonResult<Boolean>();
        long begin =System.currentTimeMillis();
        System.out.println(System.currentTimeMillis());
        ServiceResult<Member> serviceResult=null;
        //  参数校验
        if (StringUtils.isBlank(member.getMemberId())) {
            SystemAdmin adminUser = WebAdminSession.getAdminUser(request);
            member.setPassword(Md5.getMd5String("123456"));
            member.setGradeValue(0);
            member.setIntegral(0);
            member.setLastLoginIp(WebUtil.getIpAddr(request));
            member.setLoginNumber(0);
            member.setPwdErrCount(0);
            member.setSource(ConstantsEJS.SOURCE_1_PC);
            member.setBalance(new BigDecimal(0.00));
            member.setBalancePwd(Md5.getMd5String("123456"));
            member.setIsEmailVerify(ConstantsEJS.YES_NO_0);
            member.setIsSmsVerify(ConstantsEJS.YES_NO_0);
            member.setSmsVerifyCode("");
            member.setEmailVerifyCode("");
            member.setCanReceiveSms(1);
            member.setCanReceiveEmail(1);
            member.setStatus(Member.STATUS_1);
            member.setEmail("");
             serviceResult = memberService.memberRegister(member);
        }else{
        	serviceResult =memberService.getMemberById(Integer.valueOf(member.getMemberId()));
        	Member queryMember = serviceResult.getResult();
        	queryMember.setGrade(member.getGrade());
        	queryMember.setMobile(member.getMobile());
        	queryMember.setEmail(member.getEmail());
        	memberService.updateMember(queryMember);
        }
        long end = System.currentTimeMillis();
        System.out.println("asss===="+(begin-end));
        if (!serviceResult.getSuccess()) {
            if (ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR.equals(serviceResult.getCode())) {
                throw new RuntimeException(serviceResult.getMessage());
            } else {
                jsonResult.setMessage(serviceResult.getMessage());
                return jsonResult;
            }
        }
        jsonResult.setData(true);
        return jsonResult;
    }

    
    @RequestMapping(value = "bonus", method = { RequestMethod.GET })
    public String bonus(Map<String, Object> dataMap) throws Exception {
        dataMap.put("pageSize", ConstantsEJS.DEFAULT_PAGE_SIZE);
        return "admin/member/member/bonuslist";
    }

   /**
    * 奖金管理系统
    * @param request
    * @param response
    * @param dataMap
    * @return
    */
    @RequestMapping(value = "bonuslist", method = { RequestMethod.GET })
    public @ResponseBody HttpJsonResult<List<Bonus>> bonuslist(HttpServletRequest request,
                                                           HttpServletResponse response,
                                                           Map<String, Object> dataMap) {

        Map<String, String> queryMap = WebUtil.handlerQueryMap(request);
        PagerInfo pager = WebUtil.handlerPagerInfo(request, dataMap);
        ServiceResult<List<Bonus>> serviceResult = bonusService.getBonusList(queryMap, pager);
        if (!serviceResult.getSuccess()) {
            if (ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR.equals(serviceResult.getCode())) {
                throw new RuntimeException(serviceResult.getMessage());
            } else {
                throw new BusinessException(serviceResult.getMessage());
            }
        }

        HttpJsonResult<List<Bonus>> jsonResult = new HttpJsonResult<List<Bonus>>();
        jsonResult.setRows(serviceResult.getResult());
        jsonResult.setTotal(pager.getRowsCount());
        return jsonResult;
    }
  
    
    /**
     * 默认页面
     * @param dataMap
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "relation", method = { RequestMethod.GET })
    public String index(HttpServletRequest request, Map<String, Object> dataMap) throws Exception {
        Map<String, String> queryMap = WebUtil.handlerQueryMap(request);
        dataMap.put("queryMap", queryMap);
        return "admin/member/member/relationlist";
    }

    
    /**
     * gridDatalist数据
     * @param request
     * @param dataMap
     * @return
     */
    @RequestMapping(value = "relationlist", method = { RequestMethod.GET })
    public @ResponseBody List<Member> list(@RequestParam(value = "id", required = true) Integer pid,
                                                    HttpServletRequest request,
                                                    Map<String, Object> dataMap) {
    	  Map<String, String> queryMap = WebUtil.handlerQueryMap(request);
    	  if(StringUtils.isNotBlank(queryMap.get("q_mobile"))){
    		  ServiceResult<Member> memberResult=memberService.getMemberByMobile(queryMap.get("q_mobile"));
    		  if (!memberResult.getSuccess()) {
    	            if (ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR.equals(memberResult.getCode())) {
    	                throw new RuntimeException(memberResult.getMessage());
    	            } else {
    	                throw new BusinessException(memberResult.getMessage());
    	            }
    	       }
    		  pid=memberResult.getResult().getId();
    	  }else{
    		  queryMap.remove("q_mobile");
    	  }
    	  
        ServiceResult<List<Member>> serviceResult = memberService.getByPid(pid,queryMap);
//        List<SystemResources> list = new ArrayList<SystemResources>();
//        List<Member> memberList = serviceResult.getResult();
//        for(Member member:memberList){
//        	SystemResources resource = new SystemResources();
//        	resource.setId(member.getId());
//        	resource.setContent(member.getName());
//        	resource.setPid(member.getPid());
//        	resource.setGrade(member.getGrade());
//        	resource.setState(member.getState());
//        	if(member.getState().equals("open")){
//        		resource.setType(1);
//        		resource.setStatus(1);
//        	}
//        	list.add(resource);
//        }
        if (!serviceResult.getSuccess()) {
            if (ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR.equals(serviceResult.getCode())) {
                throw new RuntimeException(serviceResult.getMessage());
            } else {
                throw new BusinessException(serviceResult.getMessage());
            }
        }
        return serviceResult.getResult();
    }
    /**
     * 升级
     * @param userId
     * @param request
     * @return
     */
    @RequestMapping(value = "upgrade", method = { RequestMethod.GET })
    public @ResponseBody HttpJsonResult<Boolean> upGrade(@RequestParam(value = "userId") Integer userId,
                                                            HttpServletRequest request) {
        HttpJsonResult<Boolean> jsonResult = new HttpJsonResult<Boolean>();
        if (userId>0) {
        	 ServiceResult<Boolean>  serviceResult = memberService.upGradeByUserId(userId);
        }else {
        	long beginTime = System.currentTimeMillis();
        	ServiceResult<Boolean>  serviceResult = memberService.upGradeAll();
        	System.out.println("计算使用时常为："+(System.currentTimeMillis()-beginTime));
        }
        jsonResult.setData(true);
        return jsonResult;
    }
    
    @RequestMapping(value = "settleadd", method = { RequestMethod.GET })
    public @ResponseBody HttpJsonResult<Boolean> settle(HttpServletRequest request) {
        HttpJsonResult<Boolean> jsonResult = new HttpJsonResult<Boolean>();
        SystemAdmin admin=WebAdminSession.getAdminUser(request);
        BonusSettle bonusSettle = new BonusSettle();
        bonusSettle.setSettleDate(new Date());
        bonusSettle.setCreateUser(admin.getName());
        ServiceResult<Integer>  serviceResult = bonusSettleService.saveBonusSettle(bonusSettle);
        if(serviceResult.getSuccess() && serviceResult.getResult()>0){
        	 jsonResult.setData(true);
        }
        return jsonResult;
    }
    @RequestMapping(value = "upgradenew", method = { RequestMethod.GET })
    public String upgradeone(HttpServletRequest request, Map<String, Object> dataMap) throws Exception {
        return "admin/member/member/upgradelist";
    }
    @RequestMapping(value = "upgradelist", method = { RequestMethod.GET })
    public @ResponseBody List<Member> upGradeList( HttpServletRequest request) {
    	 Map<String, String> queryMap = WebUtil.handlerQueryMap(request);
        ServiceResult<List<Member>> result =memberService.getUpGradeList(queryMap);
        return result.getResult();
    }
    
    
    @RequestMapping(value = "test", method = { RequestMethod.GET })
    public @ResponseBody HttpJsonResult<Boolean> addWin(HttpServletRequest request, Map<String, Object> dataMap) throws Exception {
    	  HttpJsonResult<Boolean> jsonResult = new HttpJsonResult<Boolean>();
    	  for(int i=1000;i<2000;i++){
     		 Member member= new Member();
     		 member.setPassword(Md5.getMd5String("123456"));
              member.setGradeValue(0);
              member.setIntegral(0);
              member.setLastLoginIp("");
              member.setLoginNumber(0);
              member.setPwdErrCount(0);
              member.setSource(ConstantsEJS.SOURCE_1_PC);
              member.setBalance(new BigDecimal(0.00));
              member.setBalancePwd(Md5.getMd5String("123456"));
              member.setIsEmailVerify(ConstantsEJS.YES_NO_0);
              member.setIsSmsVerify(ConstantsEJS.YES_NO_0);
              member.setSmsVerifyCode("");
              member.setEmailVerifyCode("");
              member.setCanReceiveSms(1);
              member.setCanReceiveEmail(1);
              member.setStatus(Member.STATUS_1);
              member.setEmail("");
              member.setName(i+"");
              member.setMobile(""+i);
              member.setGrade(2);
              if(i==1000){
             	 member.setParentName("1");
             	 member.setPlaceName("1");
              }else{
             	 member.setParentName(""+(i-1));
             	 member.setPlaceName(""+(i-1));
              }
              ServiceResult serviceResult = memberService.memberRegister(member);
         }
        return jsonResult;
    }
    /**
     * 删除孩子节点
     * @param userId
     * @param request
     * @return
     */
    @RequestMapping(value = "delete", method = { RequestMethod.GET })
    public @ResponseBody HttpJsonResult<Boolean> deleteMember(@RequestParam(value = "userId") Integer userId,
                                                            HttpServletRequest request) {
    	HttpJsonResult<Boolean> jsonResult = new HttpJsonResult<Boolean>();
        ServiceResult<Boolean>  serviceResult = memberService.deleteByUserId(userId);
        if(serviceResult.getSuccess() && serviceResult.getResult()){
        	jsonResult.setData(true);
        }
        return jsonResult;
    }
}
