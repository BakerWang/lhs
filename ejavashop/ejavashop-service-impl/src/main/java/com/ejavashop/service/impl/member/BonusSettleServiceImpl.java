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
import com.ejavashop.entity.member.BonusSettle;
import com.ejavashop.model.member.BonusSettleModel;
import com.ejavashop.service.member.IBonusSettleService;

@Service(value = "bonusSettleService")
public class BonusSettleServiceImpl implements IBonusSettleService {
	private static Logger      log = LogManager.getLogger(BonusSettleServiceImpl.class);
	
	@Resource
	private BonusSettleModel bonusSettleModel;
    
     /**
     * 根据id取得bonus_settle对象
     * @param  bonusSettleId
     * @return
     */
    @Override
    public ServiceResult<BonusSettle> getBonusSettleById(Integer bonusSettleId) {
        ServiceResult<BonusSettle> result = new ServiceResult<BonusSettle>();
        try {
            result.setResult(bonusSettleModel.getBonusSettleById(bonusSettleId));
        } catch (BusinessException e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
            log.error("[IBonusSettleService][getBonusSettleById]根据id["+bonusSettleId+"]取得bonus_settle对象时出现未知异常：" + e.getMessage());
        } catch (Exception e) {
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("[IBonusSettleService][getBonusSettleById]根据id["+bonusSettleId+"]取得bonus_settle对象时出现未知异常：",
                e);
        }
        return result;
    }
    
    /**
     * 保存bonus_settle对象
     * @param  bonusSettle
     * @return
     */
     @Override
     public ServiceResult<Integer> saveBonusSettle(BonusSettle bonusSettle) {
     	ServiceResult<Integer> result = new ServiceResult<Integer>();
        try {
            result.setResult(bonusSettleModel.saveBonusSettle(bonusSettle));
        } catch (BusinessException e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
            log.error("[IBonusSettleService][saveBonusSettle]保存bonus_settle对象时出现未知异常：" + e.getMessage());
        } catch (Exception e) {
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("[IBonusSettleService][saveBonusSettle]保存bonus_settle对象时出现未知异常：",
                e);
        }
        return result;
     }
     
     /**
     * 更新bonus_settle对象
     * @param  bonusSettle
     * @return
     * @see com.wlsd.service.BonusSettleService#updateBonusSettle(BonusSettle)
     */
     @Override
     public ServiceResult<Integer> updateBonusSettle(BonusSettle bonusSettle) {
     	ServiceResult<Integer> result = new ServiceResult<Integer>();
        try {
            result.setResult(bonusSettleModel.updateBonusSettle(bonusSettle));
        } catch (BusinessException e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
            log.error("[IBonusSettleService][updateBonusSettle]更新bonus_settle对象时出现未知异常：" + e.getMessage());
        } catch (Exception e) {
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("[IBonusSettleService][updateBonusSettle]更新bonus_settle对象时出现未知异常：",
                e);
        }
        return result;
     }
     
     
     
     @Override
     public ServiceResult<List<BonusSettle>> getBonusSettleList(Map<String, String> queryMap, PagerInfo pager) {
         ServiceResult<List<BonusSettle>> serviceResult = new ServiceResult<List<BonusSettle>>();
         serviceResult.setPager(pager);
         try {
             Assert.notNull(bonusSettleModel, "Property 'bonusSettleModel' is required.");
             Integer start = 0, size = 0;
             if (pager != null) {
                 pager.setRowsCount(bonusSettleModel.getBonusSettleCount(queryMap));
                 start = pager.getStart();
                 size = pager.getPageSize();
             }
             serviceResult.setResult(bonusSettleModel.getBonusSettleByPage(queryMap, start, size));
         } catch (BusinessException e) {
             serviceResult.setSuccess(false);
             serviceResult.setMessage(e.getMessage());
             log.error("[IBonusSettleService][getBonusSettleList]查询结算表时出现异常：" + e.getMessage());
         } catch (Exception e) {
             serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
             log.error("[IBonusSettleService][getBonusSettleList]param1:" + JSON.toJSONString(queryMap)
                       + " &param2:" + JSON.toJSONString(pager));
             log.error("[IBonusSettleService][getBonusSettleList]查询结算信息发生异常:", e);
         }
         return serviceResult;
     }
}