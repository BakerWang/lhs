package com.ejavashop.service.member;

import java.util.List;
import java.util.Map;

import com.ejavashop.core.PagerInfo;
import com.ejavashop.core.ServiceResult;
import com.ejavashop.entity.member.BonusSettleDetail;

public interface IBonusSettleDetailService {

	/**
     * 根据id取得bonus_settle_detail对象
     * @param  bonusSettleDetailId
     * @return
     */
    ServiceResult<BonusSettleDetail> getBonusSettleDetailById(Integer bonusSettleDetailId);
    
    /**
     * 保存bonus_settle_detail对象
     * @param  bonusSettleDetail
     * @return
     */
     ServiceResult<Integer> saveBonusSettleDetail(BonusSettleDetail bonusSettleDetail);
     
     /**
     * 更新bonus_settle_detail对象
     * @param  bonusSettleDetail
     * @return
     */
     ServiceResult<Integer> updateBonusSettleDetail(BonusSettleDetail bonusSettleDetail);
     
     
     ServiceResult<List<BonusSettleDetail>> getBonusSettleDetailList(Map<String, String> queryMap, PagerInfo pager);
}