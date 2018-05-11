package com.ejavashop.model.member;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import org.springframework.stereotype.Component;
import com.ejavashop.dao.shop.write.member.BonusDao;
import com.ejavashop.entity.member.Bonus;
import com.ejavashop.entity.member.Member;

@Component
public class BonusModel {

	private static org.apache.log4j.Logger log = org.apache.log4j.LogManager
                                                   .getLogger(BonusModel.class);
    
    @Resource
    private BonusDao bonusDao;
    
    /**
     * 根据id取得bonus对象
     * @param  bonusId
     * @return
     */
    public Bonus getBonusById(Integer bonusId) {
    	return bonusDao.get(bonusId);
    }
    
    /**
     * 保存bonus对象
     * @param  bonus
     * @return
     */
     public Integer saveBonus(Bonus bonus) {
     	return bonusDao.insert(bonus);
     }
     
     /**
     * 更新bonus对象
     * @param  bonus
     * @return
     */
     public Integer updateBonus(Bonus bonus) {
     	return bonusDao.update(bonus);
     }
     
     public List<Bonus> getBonusByPage(Map<String, String> queryMap,
            Integer start,Integer size){
    	 return bonusDao.getBonusByPage(queryMap, start, size);
     }

	/**
	* 根据条件获取奖金数量
	* @param queryMap
	* @return
	*/
     public  Integer getBonusCount(Map<String, String> queryMap){
		return bonusDao.getBonusCount(queryMap);
	}
}