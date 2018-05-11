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
import com.ejavashop.entity.member.BonusSettleDetail;
import com.ejavashop.model.member.BonusSettleDetailModel;
import com.ejavashop.service.member.IBonusSettleDetailService;

@Service(value = "bonusSettleDetailService")
public class BonusSettleDetailServiceImpl implements IBonusSettleDetailService {
	private static Logger      log = LogManager.getLogger(BonusSettleDetailServiceImpl.class);
	
	@Resource
	private BonusSettleDetailModel bonusSettleDetailModel;
    
     /**
     * 根据id取得bonus_settle_detail对象
     * @param  bonusSettleDetailId
     * @return
     */
    @Override
    public ServiceResult<BonusSettleDetail> getBonusSettleDetailById(Integer bonusSettleDetailId) {
        ServiceResult<BonusSettleDetail> result = new ServiceResult<BonusSettleDetail>();
        try {
            result.setResult(bonusSettleDetailModel.getBonusSettleDetailById(bonusSettleDetailId));
        } catch (BusinessException e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
            log.error("[IBonusSettleDetailService][getBonusSettleDetailById]根据id["+bonusSettleDetailId+"]取得bonus_settle_detail对象时出现未知异常：" + e.getMessage());
        } catch (Exception e) {
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("[IBonusSettleDetailService][getBonusSettleDetailById]根据id["+bonusSettleDetailId+"]取得bonus_settle_detail对象时出现未知异常：",
                e);
        }
        return result;
    }
    
    /**
     * 保存bonus_settle_detail对象
     * @param  bonusSettleDetail
     * @return
     */
     @Override
     public ServiceResult<Integer> saveBonusSettleDetail(BonusSettleDetail bonusSettleDetail) {
     	ServiceResult<Integer> result = new ServiceResult<Integer>();
        try {
            result.setResult(bonusSettleDetailModel.saveBonusSettleDetail(bonusSettleDetail));
        } catch (BusinessException e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
            log.error("[IBonusSettleDetailService][saveBonusSettleDetail]保存bonus_settle_detail对象时出现未知异常：" + e.getMessage());
        } catch (Exception e) {
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("[IBonusSettleDetailService][saveBonusSettleDetail]保存bonus_settle_detail对象时出现未知异常：",
                e);
        }
        return result;
     }
     
     /**
     * 更新bonus_settle_detail对象
     * @param  bonusSettleDetail
     * @return
     * @see com.wlsd.service.BonusSettleDetailService#updateBonusSettleDetail(BonusSettleDetail)
     */
     @Override
     public ServiceResult<Integer> updateBonusSettleDetail(BonusSettleDetail bonusSettleDetail) {
     	ServiceResult<Integer> result = new ServiceResult<Integer>();
        try {
            result.setResult(bonusSettleDetailModel.updateBonusSettleDetail(bonusSettleDetail));
        } catch (BusinessException e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
            log.error("[IBonusSettleDetailService][updateBonusSettleDetail]更新bonus_settle_detail对象时出现未知异常：" + e.getMessage());
        } catch (Exception e) {
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("[IBonusSettleDetailService][updateBonusSettleDetail]更新bonus_settle_detail对象时出现未知异常：",
                e);
        }
        return result;
     }
     
     
     @Override
     public ServiceResult<List<BonusSettleDetail>> getBonusSettleDetailList(Map<String, String> queryMap, PagerInfo pager) {
         ServiceResult<List<BonusSettleDetail>> serviceResult = new ServiceResult<List<BonusSettleDetail>>();
         serviceResult.setPager(pager);
         try {
             Assert.notNull(bonusSettleDetailModel, "Property 'bonusModel' is required.");
             Integer start = 0, size = 0;
             if (pager != null) {
                 pager.setRowsCount(bonusSettleDetailModel.getBonusSettleDetailCount(queryMap));
                 start = pager.getStart();
                 size = pager.getPageSize();
             }
             serviceResult.setResult(bonusSettleDetailModel.getBonusSettleDetailByPage(queryMap, start, size));
         } catch (BusinessException e) {
             serviceResult.setSuccess(false);
             serviceResult.setMessage(e.getMessage());
             log.error("[IBonusSettleDetailService][getBonusSettleDetailList]查询结算明细表时出现异常：" + e.getMessage());
         } catch (Exception e) {
             serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
             log.error("[IBonusSettleDetailService][getBonusSettleDetailList]param1:" + JSON.toJSONString(queryMap)
                       + " &param2:" + JSON.toJSONString(pager));
             log.error("[IBonusSettleDetailService][getBonusSettleDetailList]查询奖金信息发生异常:", e);
         }
         return serviceResult;
     }
}