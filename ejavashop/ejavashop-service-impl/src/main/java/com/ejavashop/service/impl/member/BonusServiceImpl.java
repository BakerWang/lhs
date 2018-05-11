package com.ejavashop.service.impl.member;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.alibaba.fastjson.JSON;
import com.ejavashop.core.ConstantsEJS;
import com.ejavashop.core.PagerInfo;
import com.ejavashop.core.ServiceResult;
import com.ejavashop.core.exception.BusinessException;
import com.ejavashop.entity.member.Bonus;
import com.ejavashop.entity.member.Member;
import com.ejavashop.entity.member.MemberAddress;
import com.ejavashop.model.member.BonusModel;
import com.ejavashop.service.member.IBonusService;

@Service(value = "bonusService")
public class BonusServiceImpl implements IBonusService {
	private static Logger      log = LogManager.getLogger(BonusServiceImpl.class);
	
	@Resource
	private BonusModel bonusModel;
    
     /**
     * 根据id取得bonus对象
     * @param  bonusId
     * @return
     */
    @Override
    public ServiceResult<Bonus> getBonusById(Integer bonusId) {
        ServiceResult<Bonus> result = new ServiceResult<Bonus>();
        try {
            result.setResult(bonusModel.getBonusById(bonusId));
        } catch (BusinessException e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
            log.error("[IBonusService][getBonusById]根据id["+bonusId+"]取得bonus对象时出现未知异常：" + e.getMessage());
        } catch (Exception e) {
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("[IBonusService][getBonusById]根据id["+bonusId+"]取得bonus对象时出现未知异常：",
                e);
        }
        return result;
    }
    
    /**
     * 保存bonus对象
     * @param  bonus
     * @return
     */
     @Override
     public ServiceResult<Integer> saveBonus(Bonus bonus) {
     	ServiceResult<Integer> result = new ServiceResult<Integer>();
        try {
            result.setResult(bonusModel.saveBonus(bonus));
        } catch (BusinessException e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
            log.error("[IBonusService][saveBonus]保存bonus对象时出现未知异常：" + e.getMessage());
        } catch (Exception e) {
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("[IBonusService][saveBonus]保存bonus对象时出现未知异常：",
                e);
        }
        return result;
     }
     
     /**
     * 更新bonus对象
     * @param  bonus
     * @return
     * @see com.wlsd.service.BonusService#updateBonus(Bonus)
     */
     @Override
     public ServiceResult<Integer> updateBonus(Bonus bonus) {
     	ServiceResult<Integer> result = new ServiceResult<Integer>();
        try {
            result.setResult(bonusModel.updateBonus(bonus));
        } catch (BusinessException e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
            log.error("[IBonusService][updateBonus]更新bonus对象时出现未知异常：" + e.getMessage());
        } catch (Exception e) {
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("[IBonusService][updateBonus]更新bonus对象时出现未知异常：",
                e);
        }
        return result;
     }
     
     
     @Override
     public ServiceResult<List<Bonus>> getBonusList(Map<String, String> queryMap, PagerInfo pager) {
         ServiceResult<List<Bonus>> serviceResult = new ServiceResult<List<Bonus>>();
         serviceResult.setPager(pager);
         try {
             Assert.notNull(bonusModel, "Property 'bonusModel' is required.");
             Integer start = 0, size = 0;
             if (pager != null) {
                 pager.setRowsCount(bonusModel.getBonusCount(queryMap));
                 start = pager.getStart();
                 size = pager.getPageSize();
             }
             serviceResult.setResult(bonusModel.getBonusByPage(queryMap, start, size));
         } catch (BusinessException e) {
             serviceResult.setSuccess(false);
             serviceResult.setMessage(e.getMessage());
             log.error("[BonusService][getBonusList]查询奖金表时出现异常：" + e.getMessage());
         } catch (Exception e) {
             serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
             log.error("[BonusService][getBonusList]param1:" + JSON.toJSONString(queryMap)
                       + " &param2:" + JSON.toJSONString(pager));
             log.error("[BonusService][getBonusList]查询奖金信息发生异常:", e);
         }
         return serviceResult;
     }
}