package com.ejavashop.service.member;

import java.util.List;
import java.util.Map;

import com.ejavashop.core.PagerInfo;
import com.ejavashop.core.ServiceResult;
import com.ejavashop.entity.member.Bonus;

public interface IBonusService {

	/**
     * 根据id取得bonus对象
     * @param  bonusId
     * @return
     */
    ServiceResult<Bonus> getBonusById(Integer bonusId);
    
    /**
     * 保存bonus对象
     * @param  bonus
     * @return
     */
     ServiceResult<Integer> saveBonus(Bonus bonus);
     
     /**
     * 更新bonus对象
     * @param  bonus
     * @return
     */
     ServiceResult<Integer> updateBonus(Bonus bonus);
     
     
     
     public ServiceResult<List<Bonus>> getBonusList(Map<String, String> queryMap, PagerInfo pager);
}