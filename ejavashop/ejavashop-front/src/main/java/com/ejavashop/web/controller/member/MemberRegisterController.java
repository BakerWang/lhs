package com.ejavashop.web.controller.member;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ejavashop.core.ConstantsEJS;
import com.ejavashop.core.HttpJsonResult;
import com.ejavashop.core.Md5;
import com.ejavashop.core.ServiceResult;
import com.ejavashop.core.WebUtil;
import com.ejavashop.entity.member.Member;
import com.ejavashop.service.member.IMemberService;
import com.ejavashop.web.controller.BaseController;
import com.ejavashop.web.util.WebFrontSession;

/**
 * 会员注册controller
 * 
 * @Filename: MemberRegisterController.java
 * @Version: 1.0
 * @Author: 陈万海
 * @Email: chenwanhai@sina.com
 *
 */
@Controller
public class MemberRegisterController extends BaseController {

    @Resource
    private IMemberService memberService;

    /**
     * 查看用户协议
     * @param request
     * @param response
     * @param stack
     * @return
     */
    @RequestMapping(value = "/service_protocol.html", method = { RequestMethod.GET })
    public String protocol(HttpServletRequest request, HttpServletResponse response,
                           Map<String, Object> stack) {
        return "front/member/serviceprotocol";
    }

    /**
     * 跳转到注册页面
     * @param request
     * @param response
     * @param stack
     * @return
     */
    @RequestMapping(value = "/register.html", method = { RequestMethod.GET })
    public String signup(HttpServletRequest request, HttpServletResponse response,
                         Map<String, Object> stack) {
        return "front/member/membersignup";
    }

    /**
     * 判断用户名是否已存在
     * @param request
     * @param response
     * @param member
     * @throws Exception 
     */
    @RequestMapping(value = "/nameIsExist.html", method = { RequestMethod.GET })
    public @ResponseBody HttpJsonResult<Boolean> verify_membername(HttpServletRequest request,
                                                                   HttpServletResponse response,
                                                                   @RequestParam(value = "name", required = true) String name) throws Exception {
        //判断用户名是否已存在
        ServiceResult<List<Member>> serviceResult = memberService.getMemberByName(name);
        if (!serviceResult.getSuccess()) {
            return new HttpJsonResult<Boolean>("用户名校验出错，请重试！");
        }

        if (serviceResult.getResult() != null && serviceResult.getResult().size() > 0) {
            return new HttpJsonResult<Boolean>("用户名重复，请重新输入！");
        }

        return new HttpJsonResult<Boolean>();
    }

    /**
     * 注册信息提交
     * @param request
     * @param response
     * @param stack
     * @param membervo
     * @throws Exception
     */
    @RequestMapping(value = "/doregister.html", method = { RequestMethod.POST })
    public @ResponseBody HttpJsonResult<Member> signupSubmit(HttpServletRequest request,
                                                             HttpServletResponse response,
                                                             Member member,
                                                             String verifyCode) throws Exception {
        //获得session中的验证码
        String verify_number = WebFrontSession.getVerifyNumber(request);
        if (verify_number == null || !verify_number.equalsIgnoreCase(verifyCode)) {
            return new HttpJsonResult<Member>("验证码不正确！");
        }

        // 初始化会员信息
        member.setPassword(Md5.getMd5String(member.getPassword()));
        member.setGrade(Member.GRADE_1);
        member.setGradeValue(0);
        member.setIntegral(0);
        member.setLastLoginIp(WebUtil.getIpAddr(request));
        member.setLoginNumber(0);
        member.setPwdErrCount(0);
        member.setSource(ConstantsEJS.SOURCE_1_PC);
        member.setBalance(new BigDecimal(0.00));
        member.setBalancePwd("");
        member.setIsEmailVerify(ConstantsEJS.YES_NO_0);
        member.setIsSmsVerify(ConstantsEJS.YES_NO_0);
        member.setSmsVerifyCode("");
        member.setEmailVerifyCode("");
        member.setCanReceiveSms(1);
        member.setCanReceiveEmail(1);
        member.setStatus(Member.STATUS_1);
        member.setEmail("");
        ServiceResult<Member> serviceResult = memberService.memberRegister(member);
        HttpJsonResult<Member> jsonResult = new HttpJsonResult<Member>();
        if (!serviceResult.getSuccess()) {
            if (ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR.equals(serviceResult.getCode())) {
                throw new RuntimeException(serviceResult.getMessage());
            } else {
                jsonResult = new HttpJsonResult<Member>(serviceResult.getMessage());
            }
        }

        WebFrontSession.putMemberSession(request, serviceResult.getResult());

        //注册时赠送经验值及积分
        memberService.memberRegistSendValue(member.getId(), member.getName());

        //注册成功后默认登录
        WebFrontSession.putMemberSession(request, member);

        return jsonResult;
    }

}
