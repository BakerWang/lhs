package com.ejavashop.service.member;

import java.util.List;
import java.util.Map;

import com.ejavashop.core.PagerInfo;
import com.ejavashop.core.ServiceResult;
import com.ejavashop.entity.member.Bonus;
import com.ejavashop.entity.member.BonusSettle;

public interface IBonusSettleService {

	/**
     * 根据id取得bonus_settle对象
     * @param  bonusSettleId
     * @return
     */
    ServiceResult<BonusSettle> getBonusSettleById(Integer bonusSettleId);
    
    /**
     * 保存bonus_settle对象
     * @param  bonusSettle
     * @return
     */
     ServiceResult<Integer> saveBonusSettle(BonusSettle bonusSettle);
     
     /**
     * 更新bonus_settle对象
     * @param  bonusSettle
     * @return
     */
     ServiceResult<Integer> updateBonusSettle(BonusSettle bonusSettle);
     
     
     ServiceResult<List<BonusSettle>> getBonusSettleList(Map<String, String> queryMap, PagerInfo pager);
}