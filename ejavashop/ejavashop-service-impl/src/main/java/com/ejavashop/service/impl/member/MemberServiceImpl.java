package com.ejavashop.service.impl.member;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ejavashop.core.ConstantsEJS;
import com.ejavashop.core.JedisUtil;
import com.ejavashop.core.PagerInfo;
import com.ejavashop.core.ServiceResult;
import com.ejavashop.core.StringUtil;
import com.ejavashop.core.exception.BusinessException;
import com.ejavashop.core.freemarkerutil.DomainUrlUtil;
import com.ejavashop.entity.member.Member;
import com.ejavashop.entity.member.MemberBalanceLogs;
import com.ejavashop.entity.member.MemberCollectionProduct;
import com.ejavashop.entity.member.MemberCollectionSeller;
import com.ejavashop.entity.member.MemberGradeConfig;
import com.ejavashop.entity.member.MemberGradeIntegralLogs;
import com.ejavashop.entity.member.MemberGradeUpLogs;
import com.ejavashop.entity.member.MemberLoginLogs;
import com.ejavashop.entity.member.MemberRule;
import com.ejavashop.model.member.MemberModel;
import com.ejavashop.service.member.IMemberService;
import com.ejavashop.vo.member.FrontCheckPwdVO;
import com.ejavashop.vo.member.FrontMemberProductBehaveStatisticsVO;

import redis.clients.jedis.Jedis;

@Service(value = "memberService")
public class MemberServiceImpl implements IMemberService {
    private static Logger log = LogManager.getLogger(MemberServiceImpl.class);

    @Resource
    private MemberModel   memberModel;

    @Override
    public ServiceResult<Member> getMemberById(Integer memberId) {
        ServiceResult<Member> serviceResult = new ServiceResult<Member>();
        try {
            serviceResult.setResult(memberModel.getMemberById(memberId));
        } catch (BusinessException e) {
            serviceResult.setSuccess(false);
            serviceResult.setMessage(e.getMessage());
            log.error(
                "[MemberService][getMemberById]根据id[" + memberId + "]取得会员表时出现异常：" + e.getMessage());
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[MemberService][getMemberById]根据id[" + memberId + "]取得会员表时出现未知异常：", e);
        }
        return serviceResult;
    }
    @Override
    public ServiceResult<Member> getMemberByMobile(String mobile) {
    	ServiceResult<Member> serviceResult = new ServiceResult<Member>();
    	try {
    		serviceResult.setResult(memberModel.getMemberByMobile(mobile));
    	} catch (BusinessException e) {
    		serviceResult.setSuccess(false);
    		serviceResult.setMessage(e.getMessage());
    		log.error(
    				"[MemberService][getMemberByMobile]根据mobile[" + mobile + "]取得会员表时出现异常：" + e.getMessage());
    	} catch (Exception e) {
    		serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
    		log.error("[MemberService][getMemberByMobile]根据mobile[" + mobile + "]取得会员表时出现未知异常：", e);
    	}
    	return serviceResult;
    }

    @Override
    public ServiceResult<Integer> saveMember(Member member) {
        ServiceResult<Integer> serviceResult = new ServiceResult<Integer>();
        try {
            serviceResult.setResult(memberModel.saveMember(member));
        } catch (BusinessException e) {
            serviceResult.setSuccess(false);
            serviceResult.setMessage(e.getMessage());
            log.error("[MemberService][saveMember]保存会员表时出现异常：" + e.getMessage());
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[MemberService][saveMember] param:" + JSON.toJSONString(member));
            log.error("[MemberService][saveMember]保存会员表时出现未知异常：", e);
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<Boolean> updateMember(Member member) {
        ServiceResult<Boolean> serviceResult = new ServiceResult<Boolean>();
        try {
            serviceResult.setResult(memberModel.updateMember(member,true));
        } catch (BusinessException e) {
            serviceResult.setSuccess(false);
            serviceResult.setMessage(e.getMessage());
            log.error("[MemberService][updateMember]更新会员表时出现异常：" + e.getMessage());
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[MemberService][updateMember] param:" + JSON.toJSONString(member));
            log.error("[MemberService][updateMember]更新会员表时出现未知异常：", e);
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<List<Member>> getMembers(Map<String, String> queryMap, PagerInfo pager) {
        ServiceResult<List<Member>> serviceResult = new ServiceResult<List<Member>>();
        serviceResult.setPager(pager);
        try {
        	Assert.notNull(memberModel, "Property 'memberModel' is required.");
        	if(StringUtils.isEmpty(queryMap.get("q_pMobile"))){
        		Integer start = 0, size = 0;
        		if (pager != null) {
        			pager.setRowsCount(memberModel.getMembersCount(queryMap));
        			start = pager.getStart();
        			size = pager.getPageSize();
        		}
        		List<Member> memberList = memberModel.getMembers(queryMap, start, size);
        		serviceResult.setResult(memberList);
        	}else{
        		Member member =memberModel.getMemberByMobile(queryMap.get("q_pMobile"));
        		if(member != null ){
        			Integer start = 0, size = 0;
            		if (pager != null) {
            			pager.setRowsCount(memberModel.getMembersChildrenCount(member.getLeftValue(), member.getRightValue()));
            			start = pager.getStart();
            			size = pager.getPageSize();
            		}
            		List<Member> memberList = memberModel.getMembersChildren(member.getLeftValue(), member.getRightValue(), start, size);
            		serviceResult.setResult(memberList);
        		}
        	}
//            List<Member> resultList= new ArrayList<Member>();
//            for(Member member:memberList) {
////            	member.setUpGrade(upGradeTemp(member.getId()));
//            	resultList.add(member);
//            }
        } catch (BusinessException e) {
            serviceResult.setSuccess(false);
            serviceResult.setMessage(e.getMessage());
            log.error("[MemberService][getMembers]查询会员表时出现异常：" + e.getMessage());
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[MemberService][getMembers]param1:" + JSON.toJSONString(queryMap)
                      + " &param2:" + JSON.toJSONString(pager));
            log.error("[MemberService][getMembers]查询会员信息发生异常:", e);
        }
        return serviceResult;
    }
    
    @Override
    public ServiceResult<List<Member>> getByPid(Integer pid,Map<String, String> queryMap) {
    	ServiceResult<List<Member>> serviceResult = new ServiceResult<List<Member>>();
    	try {
    		Assert.notNull(memberModel, "Property 'memberModel' is required.");
    		serviceResult.setResult(memberModel.getByPid(pid,queryMap));
    	} catch (BusinessException e) {
    		serviceResult.setSuccess(false);
    		serviceResult.setMessage(e.getMessage());
    		log.error("[MemberService][getByPid]查询会员表时出现异常：" + e.getMessage());
    	} catch (Exception e) {
    		serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
    		log.error("[MemberService][getMembers]param1:" + JSON.toJSONString(queryMap)
    		+ " &param2:" + pid);
    		log.error("[MemberService][getByPid]查询会员信息发生异常:", e);
    	}
    	return serviceResult;
    }

    @Override
    public ServiceResult<Boolean> updateMemberValue(MemberGradeIntegralLogs logs) {
        ServiceResult<Boolean> serviceResult = new ServiceResult<Boolean>();
        try {
            Assert.notNull(memberModel, "Property 'memberModel' is required.");

            serviceResult.setResult(memberModel.updateMemberValue(logs));
            return serviceResult;
        } catch (BusinessException be) {
            serviceResult.setSuccess(false);
            serviceResult.setMessage(be.getMessage());
            log.error("[MemberService][updateMemberValue]更新会员经验值与积分发生异常:", be);
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[MemberService][updateMemberValue]更新会员经验值与积分发生异常:", e);
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<List<MemberGradeUpLogs>> getMemberGradeUpLogs(Integer memberId,
                                                                       PagerInfo pager) {
        ServiceResult<List<MemberGradeUpLogs>> serviceResult = new ServiceResult<List<MemberGradeUpLogs>>();
        serviceResult.setPager(pager);
        try {
            Assert.notNull(memberModel, "Property 'memberModel' is required.");
            Integer start = 0, size = 0;
            if (pager != null) {
                pager.setRowsCount(memberModel.getMemberGradeUpLogsCount(memberId));
                start = pager.getStart();
                size = pager.getPageSize();
            }
            serviceResult.setResult(memberModel.getMemberGradeUpLogs(memberId, start, size));
        } catch (BusinessException be) {
            serviceResult.setSuccess(false);
            serviceResult.setMessage(be.getMessage());
            log.error("[MemberService][getMemberGradeUpLogs]查询会员等级升级日志发生异常:" + be.getMessage());
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[MemberService][getMemberGradeUpLogs]查询会员等级升级日志发生异常:", e);
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<List<MemberGradeIntegralLogs>> getMemberGradeIntegralLogs(Integer memberId,
                                                                                   Integer type,
                                                                                   PagerInfo pager) {
        ServiceResult<List<MemberGradeIntegralLogs>> serviceResult = new ServiceResult<List<MemberGradeIntegralLogs>>();
        serviceResult.setPager(pager);
        try {
            Assert.notNull(memberModel, "Property 'memberModel' is required.");
            Integer start = 0, size = 0;
            if (pager != null) {
                pager.setRowsCount(memberModel.getMemberGradeIntegralLogsCount(memberId, type));
                start = pager.getStart();
                size = pager.getPageSize();
            }
            serviceResult
                .setResult(memberModel.getMemberGradeIntegralLogs(memberId, type, start, size));
        } catch (BusinessException be) {
            serviceResult.setSuccess(false);
            serviceResult.setMessage(be.getMessage());
            log.error("[MemberService][getMemberGradeIntegralLogs]根据会员ID和类型取得会员经验值积分值变更日志发生异常:"
                      + be.getMessage());
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[MemberService][getMemberGradeIntegralLogs]根据会员ID和类型取得会员经验值积分值变更日志发生异常:", e);
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<List<MemberLoginLogs>> getMemberLoginLogs(Integer memberId,
                                                                   PagerInfo pager) {
        ServiceResult<List<MemberLoginLogs>> serviceResult = new ServiceResult<List<MemberLoginLogs>>();
        serviceResult.setPager(pager);
        try {
            Assert.notNull(memberModel, "Property 'memberModel' is required.");
            Integer start = 0, size = 0;
            if (pager != null) {
                pager.setRowsCount(memberModel.getMemberLoginLogsCount(memberId));
                start = pager.getStart();
                size = pager.getPageSize();
            }
            serviceResult.setResult(memberModel.getMemberLoginLogs(memberId, start, size));
        } catch (BusinessException be) {
            serviceResult.setSuccess(false);
            serviceResult.setMessage(be.getMessage());
            log.error("[MemberService][getMemberLoginLogs]根据会员ID获取会员登录日志发生异常:" + be.getMessage());
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[MemberService][getMemberLoginLogs]根据会员ID获取会员登录日志发生异常:", e);
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<List<MemberCollectionSeller>> getMemberCollectionSellers(Integer memberId,
                                                                                  PagerInfo pager) {
        ServiceResult<List<MemberCollectionSeller>> serviceResult = new ServiceResult<List<MemberCollectionSeller>>();
        serviceResult.setPager(pager);
        try {
            Assert.notNull(memberModel, "Property 'memberModel' is required.");
            Integer start = 0, size = 0;
            if (pager != null) {
                pager.setRowsCount(memberModel.getMemberCollectionSellersCount(memberId));
                start = pager.getStart();
                size = pager.getPageSize();
            }
            serviceResult.setResult(memberModel.getMemberCollectionSellers(memberId, start, size));
        } catch (BusinessException be) {
            serviceResult.setSuccess(false);
            serviceResult.setMessage(be.getMessage());
            log.error(
                "[MemberService][getMemberCollectionSellers]根据会员ID获取会员收藏商铺发生异常:" + be.getMessage());
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[MemberService][getMemberCollectionSellers]根据会员ID获取会员收藏商铺发生异常:", e);
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<List<MemberCollectionProduct>> getMemberCollectionProducts(Integer memberId,
                                                                                    PagerInfo pager) {
        ServiceResult<List<MemberCollectionProduct>> serviceResult = new ServiceResult<List<MemberCollectionProduct>>();
        serviceResult.setPager(pager);
        try {
            Assert.notNull(memberModel, "Property 'memberModel' is required.");
            Integer start = 0, size = 0;
            if (pager != null) {
                pager.setRowsCount(memberModel.getMemberCollectionProductsCount(memberId));
                start = pager.getStart();
                size = pager.getPageSize();
            }
            serviceResult.setResult(memberModel.getMemberCollectionProducts(memberId, start, size));
        } catch (BusinessException be) {
            serviceResult.setSuccess(false);
            serviceResult.setMessage(be.getMessage());
            log.error("[MemberService][getMemberCollectionProducts]根据会员ID获取会员收藏商品发生异常:"
                      + be.getMessage());
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[MemberService][getMemberCollectionProducts]根据会员ID获取会员收藏商品发生异常:", e);
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<Boolean> updateMemberBalance(MemberBalanceLogs logs) {
        ServiceResult<Boolean> serviceResult = new ServiceResult<Boolean>();
        try {
            Assert.notNull(memberModel, "Property 'memberModel' is required.");

            serviceResult.setResult(memberModel.updateMemberBalance(logs));
            return serviceResult;
        } catch (BusinessException be) {
            serviceResult.setSuccess(false);
            serviceResult.setMessage(be.getMessage());
            log.error("[MemberService][updateMemberBalance]更新会员余额发生异常:" + be.getMessage());
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[MemberService][updateMemberBalance]更新会员余额发生异常:", e);
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<Member> memberLogin(String memberName, String password, String ip,
                                             Integer source) {
        ServiceResult<Member> serviceResult = new ServiceResult<Member>();
        try {
            serviceResult.setResult(memberModel.memberLogin(memberName, password, ip, source));
        } catch (BusinessException be) {
            serviceResult.setSuccess(false);
            serviceResult.setMessage(be.getMessage());
            log.error("[MemberService][memberLogin]会员登录时发生异常:" + be.getMessage());
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[MemberService][memberLogin]会员登录时发生异常:", e);
        }
        return serviceResult;

    }

    @Override
    public ServiceResult<Member> memberRegister(Member member) {
        ServiceResult<Member> serviceResult = new ServiceResult<Member>();
        try {
            serviceResult.setResult(memberModel.memberRegister(member));
        } catch (BusinessException be) {
            serviceResult.setSuccess(false);
            serviceResult.setMessage(be.getMessage());
            log.error("[MemberService][memberRegister]会员注册时发生异常:" + be.getMessage());
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[MemberService][memberRegister]member:" + JSON.toJSONString(member));
            log.error("[MemberService][memberRegister]会员注册时发生异常:", e);
        }
        return serviceResult;

    }

    @Override
    public ServiceResult<List<Member>> getMemberByName(String name) {
        ServiceResult<List<Member>> serviceResult = new ServiceResult<List<Member>>();
        try {
            serviceResult.setResult(memberModel.getMemberByName(name));
        } catch (BusinessException be) {
            serviceResult.setSuccess(false);
            serviceResult.setMessage(be.getMessage());
            log.error("[MemberService][getMemberByName]根据会员名称取会员时发生异常:" + be.getMessage());
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[MemberService][getMemberByName]name:" + name);
            log.error("[MemberService][getMemberByName]根据会员名称取会员时发生异常:", e);
        }
        return serviceResult;

    }

    @Override
    public ServiceResult<MemberGradeConfig> getMemberGradeConfig(Integer memberGradeConfigId) {
        ServiceResult<MemberGradeConfig> serviceResult = new ServiceResult<MemberGradeConfig>();
        try {
            serviceResult.setResult(memberModel.getMemberGradeConfig(memberGradeConfigId));
        } catch (BusinessException be) {
            serviceResult.setSuccess(false);
            serviceResult.setMessage(be.getMessage());
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[FrontMemberService][getMemberRule]根据ID获取商城会员等级配置发生异常:", e);
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<MemberRule> getMemberRule(Integer memberRuleId, Integer state) {
        ServiceResult<MemberRule> serviceResult = new ServiceResult<MemberRule>();
        try {
            serviceResult.setResult(memberModel.getMemberRule(memberRuleId, state));
        } catch (BusinessException be) {
            serviceResult.setSuccess(false);
            serviceResult.setMessage(be.getMessage());
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[FrontMemberService][getMemberRule]根据ID获取会员经验值积分规则发生异常:", e);
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<Boolean> memberRegistSendValue(Integer memberId, String memberName) {
        ServiceResult<Boolean> serviceResult = new ServiceResult<Boolean>();
        try {
            serviceResult.setResult(memberModel.memberRegistSendValue(memberId, memberName));
        } catch (BusinessException be) {
            serviceResult.setSuccess(false);
            serviceResult.setMessage(be.getMessage());
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[FrontMemberService][memberRegistSendValue]会员注册时送经验值与积分发生异常:", e);
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<Boolean> memberLoginSendValue(Integer memberId, String memberName) {
        ServiceResult<Boolean> serviceResult = new ServiceResult<Boolean>();
        try {
            serviceResult.setResult(memberModel.memberLoginSendValue(memberId, memberName));
        } catch (BusinessException be) {
            serviceResult.setSuccess(false);
            serviceResult.setMessage(be.getMessage());
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[FrontMemberService][memberLoginSendValue]会员登录时送经验值与积分发生异常:", e);
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<Boolean> memberOrderSendValue(Integer memberId, String memberName,
                                                       Integer orderId) {
        ServiceResult<Boolean> serviceResult = new ServiceResult<Boolean>();
        try {
            serviceResult
                .setResult(memberModel.memberOrderSendValue(memberId, memberName, orderId));
        } catch (BusinessException be) {
            serviceResult.setSuccess(false);
            serviceResult.setMessage(be.getMessage());
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[FrontMemberService][memberOrderSendValue]会员下单时送经验值与积分发生异常:", e);
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<Boolean> memberEvaluateSendValue(Integer memberId, String memberName,
                                                          Integer productId) {
        ServiceResult<Boolean> serviceResult = new ServiceResult<Boolean>();
        try {
            serviceResult
                .setResult(memberModel.memberEvaluateSendValue(memberId, memberName, productId));
        } catch (BusinessException be) {
            serviceResult.setSuccess(false);
            serviceResult.setMessage(be.getMessage());
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[FrontMemberService][memberEvaluateSendValue]会员评论时送经验值与积分发生异常:", e);
        }
        return serviceResult;
    }

    /**
     * 修改密码提交
     * @param oldPwd 旧密码
     * @param newPwd 新密码
     * @param request
     * @return
     */
    @Override
    public ServiceResult<Member> editPassword(String oldPwd, String newPwd, Member member) {
        ServiceResult<Member> serviceResult = new ServiceResult<Member>();
        try {
            serviceResult.setResult(memberModel.editPassword(oldPwd, newPwd, member));
        } catch (BusinessException be) {
            serviceResult.setSuccess(false);
            serviceResult.setMessage(be.getMessage());
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[FrontMemberExtendService][editPassword]修改密码时发生异常:", e);
        }
        return serviceResult;
    }

    /**
     * 根据产品获得用户评价数 
     * @param request
     * @param pager
     * @return
     */
    @Override
    public ServiceResult<FrontMemberProductBehaveStatisticsVO> getBehaveStatisticsByProductId(Integer productId,
                                                                                              Member member) {
        ServiceResult<FrontMemberProductBehaveStatisticsVO> serviceResult = new ServiceResult<FrontMemberProductBehaveStatisticsVO>();
        try {
            serviceResult.setResult(memberModel.getBehaveStatisticsByProductId(productId, member));
        } catch (BusinessException be) {
            serviceResult.setSuccess(false);
            serviceResult.setMessage(be.getMessage());
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[FrontMemberExtendService][getBehaveStatisticsByProductId]获得用户评价数时发生异常:", e);
        }
        return serviceResult;
    }

    /**
     * 判断支付密码是否正确
     * @param balancePwd
     * @param request
     * @return  返回错误次数
     */
    @Override
    public ServiceResult<FrontCheckPwdVO> checkcheckBalancePwd(String balancePwd,
                                                               Integer memberId) {
        ServiceResult<FrontCheckPwdVO> serviceResult = new ServiceResult<FrontCheckPwdVO>();
        try {
            serviceResult.setResult(memberModel.checkcheckBalancePwd(balancePwd, memberId));
        } catch (BusinessException be) {
            serviceResult.setSuccess(false);
            serviceResult.setMessage(be.getMessage());
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[FrontMemberExtendService][checkcheckBalancePwd]验证余额支付密码时发生异常:", e);
        }
        return serviceResult;
    }

    /**
     * 支付密码修改
     * @param oldPwd 旧密码
     * @param newPwd 新密码
     * @param request
     * @return
     */
    @Override
    public ServiceResult<Member> editBalancePassword(String oldPwd, String newPwd,
                                                     Integer memberId) {
        ServiceResult<Member> serviceResult = new ServiceResult<Member>();

        try {
            serviceResult.setResult(memberModel.editBalancePassword(oldPwd, newPwd, memberId));
        } catch (BusinessException be) {
            serviceResult.setSuccess(false);
            serviceResult.setMessage(be.getMessage());
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[FrontMemberExtendService][editBalancePassword]修改支付密码时发生异常:", e);
        }
        return serviceResult;
    }

    /**
     * 设置支付密码
     * @param password 支付密码
     * @param request
     * @return
     */
    @Override
    public ServiceResult<Member> addBalancePassword(String password, Member member) {
        ServiceResult<Member> serviceResult = new ServiceResult<Member>();
        try {
            serviceResult.setResult(memberModel.addBalancePassword(password, member));
        } catch (BusinessException be) {
            serviceResult.setSuccess(false);
            serviceResult.setMessage(be.getMessage());
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[FrontMemberExtendService][addBalancePassword]设置支付密码时发生异常:", e);
        }
        return serviceResult;
    }
    
	private void countMember(Jedis jedis,Map <String,Integer> countMap,int userId,int memberCount,int adviserCount,int managerCount,int majordomoCount,int outstandingMajordomoCount) {
		Set<String> memberList=jedis.zrange(String.format("member_list_%s", userId), 0,-1);
		System.out.println("计算1111为：");
		if(memberList ==null || memberList.size()==0){
			System.out.println("计算2222为：");
			return;
		}
		memberCount+=memberList.size();
		System.out.println("计算33333为："+memberCount);
		countMap.put("memberCount", memberCount);
		for(String str:memberList){
			String memStr =jedis.get(String.format("userInfo_%s", str));
			System.out.println("计算4444为："+memStr);
			Member member=JSONObject.parseObject(memStr, Member.class);
			System.out.println("计算6666为："+memStr);
			if(member.getGrade()==3){
				System.out.println("计算7777为："+memStr);
				adviserCount++;
				countMap.put("adviserCount", adviserCount);
			}else if(member.getGrade()==4){
				managerCount++;
				countMap.put("managerCount", managerCount);
			}else if(member.getGrade()==5){
				majordomoCount++;
				countMap.put("majordomoCount", majordomoCount);
			}else if(member.getGrade()==6){
				outstandingMajordomoCount++;
				countMap.put("outstandingMajordomoCount", outstandingMajordomoCount);
			}
			System.out.println("计算8888为："+memStr);
			countMember(jedis,countMap,member.getId(),memberCount,adviserCount,managerCount,majordomoCount,outstandingMajordomoCount);
		}
	}
	
	public ServiceResult<Boolean> upGradeAll() {
		ServiceResult<Boolean> result= new ServiceResult<Boolean>();
		try {
			Integer count =memberModel.getMembersCount(new HashMap());
			List<Member> memberList =memberModel.getMembers(new HashMap(), 0, count);
			long aa =System.currentTimeMillis();
			for(Member member:memberList) {
				long beginTime =System.currentTimeMillis();
				System.out.println(System.currentTimeMillis());
				this.upGradeByUserId(member.getId());
				System.out.println("单条计算时间为："+(System.currentTimeMillis()-beginTime));
			}
			System.out.println("总时间计算时间为："+(System.currentTimeMillis()-aa));
		}catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public void upGradeOne(Jedis jedis,Integer userId) {
			System.out.println(userId);
			Set<String> memberList=jedis.zrange(String.format("member_list_%s",userId), 0,-1);
			if(memberList==null || memberList.size()<=0) {
				return;
			}
			for(String str:memberList) {
				
				upGradeOne(jedis,Integer.valueOf(str));
			}
	
	}
	

	
	public void cacheCountMemberOne(Jedis jedis,int userId,int memberCount,int adviserCount,int managerCount,int majordomoCount,int outstandingMajordomoCount) {
		Set<String> memberList=jedis.zrange(String.format("member_list_%s", userId), 0,-1);
		System.out.println("用户ID为："+userId);
		Map <String,Integer> countMap = new HashMap<String,Integer>();
		countMap.put("memberCount", memberCount);
		countMap.put("adviserCount", adviserCount);
		countMap.put("managerCount", managerCount);
		countMap.put("majordomoCount", majordomoCount);
		countMap.put("outstandingMajordomoCount", outstandingMajordomoCount);
		for(String str:memberList) {
			Map <String,Integer> pMap = new HashMap<String,Integer>();
			pMap.put("memberCount", memberCount);
			pMap.put("adviserCount", adviserCount);
			pMap.put("managerCount", managerCount);
			pMap.put("majordomoCount", majordomoCount);
			pMap.put("outstandingMajordomoCount", outstandingMajordomoCount);
			countMember(jedis,pMap,Integer.parseInt(str),memberCount,adviserCount,managerCount,majordomoCount,outstandingMajordomoCount);
			
			if(pMap.get("adviserCount")>0) {
				countMap.put("adviserCount", countMap.get("adviserCount")+1);
			}
			if(pMap.get("managerCount")>0) {
				countMap.put("managerCount", countMap.get("managerCount")+1);
			}
			if(pMap.get("majordomoCount")>0) {
				countMap.put("majordomoCount", countMap.get("majordomoCount")+1);
			}
			if(pMap.get("outstandingMajordomoCount")>0) {
				countMap.put("outstandingMajordomoCount", countMap.get("outstandingMajordomoCount")+1);
			}
			countMap.put("memberCount", countMap.get("memberCount")+pMap.get("memberCount"));
			jedis.set(String.format("memerber_upgrade_flag_%s", userId), JSONObject.toJSONString(countMap));
			cacheCountMemberOne(jedis,Integer.parseInt(str),memberCount,adviserCount,managerCount,majordomoCount,outstandingMajordomoCount);
		}
	
	}
	
	
	
	 public ServiceResult<Boolean> upGradeByUserId(int userId) {
		ServiceResult<Boolean> serviceResult = new ServiceResult<Boolean>();
		Member member =memberModel.getMemberById(userId);
		int memberCount = (member.getRightValue().intValue()-member.getLeftValue().intValue())/2;
		int grade = member.getGrade();
		//顾问人数
		int adviserCount=0;
		//经理人数
		int managerCount=0;
		//总监人数
		int majordomoCount=0;
		//杰出总监人数
		int outstandingMajordomoCount=0;
		Boolean flag=true;
		if(memberCount >= 30 && memberCount<100){
			member.setGrade(3);
			if(grade<member.getGrade()) {
				flag=memberModel.updateMember(member,false);
				System.out.println("级别为："+member.getGrade());
			}
		}else if (memberCount>=100 && memberCount<350){
			member.setGrade(4);
			if(grade<member.getGrade()) {
				flag=memberModel.updateMember(member,false);
				System.out.println("级别为："+member.getGrade());
			}
		}else if(memberCount>=350 && memberCount<1200){
			List<Member> childList =memberModel.getParent(member);
			if(childList.size()>=3) {
				for(Member mm:childList) {
					if(mm.getGrade()==3) {
						adviserCount++;
						continue;
					}else if(mm.getGrade()==4) {
						managerCount++;
						continue;
					}
					List<Member> adviserList =memberModel.getFindDescendants(mm, 3);
					if(adviserList !=null && adviserList.size()>0) {
						adviserCount++;
					}
					List<Member> list =memberModel.getFindDescendants(mm, 4);
					if(list !=null && list.size()>0) {
						managerCount++;
					}
					
				}
				if(managerCount>=3 || (managerCount>=2 && adviserCount>=1)){
					member.setGrade(5);
				}else if(grade<4){
					member.setGrade(4);
				}
			}else if(grade<4){
				member.setGrade(4);
			}
			if(grade<member.getGrade()) {
				flag=memberModel.updateMember(member,false);
			}
		}else if(memberCount>=1200 && memberCount<7500){
			
			List<Member> childList =memberModel.getParent(member);
			if(childList.size()>=3) {
				for(Member mm:childList) {
					if(mm.getGrade()==3) {
						adviserCount++;
						continue;
					}else if(mm.getGrade()==4) {
						managerCount++;
						continue;
					}else if(mm.getGrade()==5) {
						majordomoCount++;
						continue;
					}
					List<Member> adviserList =memberModel.getFindDescendants(mm, 3);
					if(adviserList !=null && adviserList.size()>0) {
						adviserCount++;
					}
					List<Member> list =memberModel.getFindDescendants(mm, 4);
					if(list !=null && list.size()>0) {
						managerCount++;
					}
					List<Member> majorlist =memberModel.getFindDescendants(mm, 5);
					if(majorlist !=null && majorlist.size()>0) {
						majordomoCount++;
					}
				}
				boolean managerFlag=(managerCount>=2 && adviserCount>=1)||managerCount>=3 ||(adviserCount>=1 && managerCount>=1 && majordomoCount>=1) ||(adviserCount>=1 && majordomoCount>=2)||( managerCount>=2 && majordomoCount>=1)||( managerCount>=1 && majordomoCount>=2)||(majordomoCount>=3);

				if((majordomoCount>=3 && managerCount>=1)||majordomoCount>=4){
					member.setGrade(6);
				}else if(managerFlag){
						member.setGrade(5);
				}else {
						member.setGrade(4);
				}
			}else if(grade<4){
				member.setGrade(4);
			}
			if(grade<member.getGrade()) {
				flag=memberModel.updateMember(member,false);
			}
			
		}else if(memberCount>=7500 ){
			
			List<Member> childList =memberModel.getParent(member);
			if(childList.size()>=3) {
				for(Member mm:childList) {
					if(mm.getGrade()==3) {
						adviserCount++;
						continue;
					}else if(mm.getGrade()==4) {
						managerCount++;
						continue;
					}else if(mm.getGrade()==5) {
						majordomoCount++;
						continue;
					}else if(mm.getGrade()==6) {
						outstandingMajordomoCount++;
						continue;
					}
					List<Member> adviserList =memberModel.getFindDescendants(mm, 3);
					if(adviserList !=null && adviserList.size()>0) {
						adviserCount++;
					}
					List<Member> list =memberModel.getFindDescendants(mm, 4);
					if(list !=null && list.size()>0) {
						managerCount++;
					}
					List<Member> majorlist =memberModel.getFindDescendants(mm, 5);
					if(majorlist !=null && majorlist.size()>0) {
						majordomoCount++;
					}
					
					List<Member> outstandinglist =memberModel.getFindDescendants(mm, 6);
					if(outstandinglist !=null && outstandinglist.size()>0) {
						outstandingMajordomoCount++;
					}
				}
				boolean managerFlag=(managerCount>=2 && adviserCount>=1)||managerCount>=3 ||(adviserCount>=1 && managerCount>=1 && majordomoCount>=1) ||(adviserCount>=1 && majordomoCount>=2)||( managerCount>=2 && majordomoCount>=1)||( managerCount>=1 && majordomoCount>=2)||(majordomoCount>=3);

				if(outstandingMajordomoCount>=4){
					member.setGrade(7);
				}else if((majordomoCount>=3 && managerCount>=1) || majordomoCount>=4){
					member.setGrade(6);
				}else if(managerFlag){
					member.setGrade(5);
				}else {
					member.setGrade(4);
				}
			}else if(grade<4){
				member.setGrade(4);
			}
			if(grade<member.getGrade()) {
				flag=memberModel.updateMember(member,false);
			}
		}
		serviceResult.setSuccess(flag);
		return serviceResult;
	 }
	 
	 
	 /**
	  * 根据用户ID获取能否升级
	  * @param userId
	  * @return
	  */
	 public Boolean upGradeTemp(int userId) {
			Member member =memberModel.getMemberById(userId);
			int memberCount = (member.getRightValue().intValue()-member.getLeftValue().intValue()-1)/2;
			int grade = member.getGrade();
			if(grade==1){
				return false;
			}
			//顾问人数
			int adviserCount=0;
			//经理人数
			int managerCount=0;
			//总监人数
			int majordomoCount=0;
			//杰出总监人数
			int outstandingMajordomoCount=0;
			Boolean flag=false;
//			if(memberCount >= 30 && memberCount<100){
			if(memberCount >= 30 && grade<=3){
				member.setGrade(3);
				if(grade<member.getGrade()) {
					flag=true;
				}
//			}else if (memberCount>=100 && memberCount<350){
			}else if (memberCount>=100 && grade<4){
				member.setGrade(4);
				if(grade<member.getGrade()) {
					flag=true;
					System.out.println("级别为："+member.getGrade());
				}
			}else if(memberCount>=350 && memberCount<1200){
				List<Member> childList =memberModel.getParent(member);
				if(childList.size()>=3) {
					for(Member mm:childList) {
						if(mm.getGrade()==3) {
							adviserCount++;
							continue;
						}else if(mm.getGrade()==4) {
							managerCount++;
							continue;
						}
						List<Member> adviserList =memberModel.getFindDescendants(mm, 3);
						if(adviserList !=null && adviserList.size()>0) {
							adviserCount++;
						}
						List<Member> list =memberModel.getFindDescendants(mm, 4);
						if(list !=null && list.size()>0) {
							managerCount++;
						}
						
					}
					if(managerCount>=3 || (managerCount>=2 && adviserCount>=1)){
						member.setGrade(5);
					}else if(grade<4){
						member.setGrade(4);
					}
				}else if(grade<4){
					member.setGrade(4);
				}
				if(grade<member.getGrade()) {
					flag=true;
//					flag=memberModel.updateMember(member);
				}
			}else if(memberCount>=1200 && memberCount<7500){
				
				List<Member> childList =memberModel.getParent(member);
				if(childList.size()>=3) {
					for(Member mm:childList) {
						if(mm.getGrade()==3) {
							adviserCount++;
							continue;
						}else if(mm.getGrade()==4) {
							managerCount++;
							continue;
						}else if(mm.getGrade()==5) {
							majordomoCount++;
							continue;
						}
						List<Member> adviserList =memberModel.getFindDescendants(mm, 3);
						if(adviserList !=null && adviserList.size()>0) {
							adviserCount++;
						}
						List<Member> list =memberModel.getFindDescendants(mm, 4);
						if(list !=null && list.size()>0) {
							managerCount++;
						}
						List<Member> majorlist =memberModel.getFindDescendants(mm, 5);
						if(majorlist !=null && majorlist.size()>0) {
							majordomoCount++;
						}
					}
					boolean managerFlag=(managerCount>=2 && adviserCount>=1)||managerCount>=3 ||(adviserCount>=1 && managerCount>=1 && majordomoCount>=1) ||(adviserCount>=1 && majordomoCount>=2)||( managerCount>=2 && majordomoCount>=1)||( managerCount>=1 && majordomoCount>=2)||(majordomoCount>=3);
					if(majordomoCount>=4 || (majordomoCount==3 &&  managerCount>=1)){
						member.setGrade(6);
					}else if(managerFlag){
							member.setGrade(5);
					}else {
							member.setGrade(4);
					}
				}else if(grade<4){
					member.setGrade(4);
				}
				if(grade<member.getGrade()) {
					flag=true;
//					flag=memberModel.updateMember(member);
				}
				
			}else if(memberCount>=7500 ){
				
				List<Member> childList =memberModel.getParent(member);
				if(childList.size()>=3) {
					for(Member mm:childList) {
						if(mm.getGrade()==3) {
							adviserCount++;
							continue;
						}else if(mm.getGrade()==4) {
							managerCount++;
							continue;
						}else if(mm.getGrade()==5) {
							majordomoCount++;
							continue;
						}else if(mm.getGrade()==6) {
							outstandingMajordomoCount++;
							continue;
						}
						List<Member> adviserList =memberModel.getFindDescendants(mm, 3);
						if(adviserList !=null && adviserList.size()>0) {
							adviserCount++;
						}
						List<Member> list =memberModel.getFindDescendants(mm, 4);
						if(list !=null && list.size()>0) {
							managerCount++;
						}
						List<Member> majorlist =memberModel.getFindDescendants(mm, 5);
						if(majorlist !=null && majorlist.size()>0) {
							majordomoCount++;
						}
						
						List<Member> outstandinglist =memberModel.getFindDescendants(mm, 6);
						if(outstandinglist !=null && outstandinglist.size()>0) {
							outstandingMajordomoCount++;
						}
					}
					boolean managerFlag=(managerCount>=2 && adviserCount>=1)||managerCount>=3 ||(adviserCount>=1 && managerCount>=1 && majordomoCount>=1) ||(adviserCount>=1 && majordomoCount>=2)||( managerCount>=2 && majordomoCount>=1)||( managerCount>=1 && majordomoCount>=2)||(majordomoCount>=3);
					
					if(outstandingMajordomoCount>=4){
						member.setGrade(7);
					}else if(majordomoCount>=4 || (majordomoCount==3 &&  managerCount>=1)){
						member.setGrade(6);
					}else if(managerFlag){
						member.setGrade(5);
					}else {
						member.setGrade(4);
					}
				}else if(grade<4){
					member.setGrade(4);
				}
				if(grade<member.getGrade()) {
					flag=true;
//					flag=memberModel.updateMember(member);
				}
			}
			
			return flag;
		 }
	 
	 /**
	  * 查询需要升级的用户
	  * @return
	  */
	 public ServiceResult<List<Member>> getUpGradeList(Map<String, String> queryMap) {
	        ServiceResult<List<Member>> serviceResult = new ServiceResult<List<Member>>();
	        try {
	            Assert.notNull(memberModel, "Property 'memberModel' is required.");
	            List<Member> resultList= new ArrayList<Member>();
	           int count =memberModel.getMembersCount(queryMap);
	               
	            List<Member> memberList = memberModel.getMembers(queryMap, 0, count);
	            for(Member member:memberList) {
	            	boolean grade =upGradeTemp(member.getId());
	            	if(grade) {
	            		member.setUpGrade(grade);
	            		resultList.add(member);
	            	}
	            }
	            serviceResult.setResult(resultList);
	        } catch (BusinessException e) {
	            serviceResult.setSuccess(false);
	            serviceResult.setMessage(e.getMessage());
	            log.error("[MemberService][getUpGradeList]查询升级会员表时出现异常：" + e.getMessage());
	        } catch (Exception e) {
	            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
	            log.error("[MemberService][getUpGradeList]"
	                      );
	            log.error("[MemberService][getUpGradeList]查询升级会员信息发生异常:", e);
	        }
	        return serviceResult;
	    }
	 
	 
	 /**
	  * 删除用户
	  */
	 public ServiceResult<Boolean> deleteByUserId(int userId){
		 ServiceResult<Boolean> serviceResult = new ServiceResult<Boolean>();
	        try {
	            Assert.notNull(memberModel, "Property 'memberModel' is required.");
	            int re =memberModel.del(userId);
	            if(re>0){
	            	serviceResult.setResult(true);
	            }else{
	            	serviceResult.setResult(false);
	            }
	        } catch (BusinessException e) {
	            serviceResult.setSuccess(false);
	            serviceResult.setMessage(e.getMessage());
	            log.error("[MemberService][deleteByUserId]删除会员表时出现异常：" + e.getMessage());
	        } catch (Exception e) {
	        	serviceResult.setSuccess(false);
	            serviceResult.setMessage(e.getMessage());
//	            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
	            log.error("[MemberService][deleteByUserId]删除会员信息发生异常:", e);
	        }
	        return serviceResult;
	 }
	
}