package com.ejavashop.model.member;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ejavashop.dao.shop.write.member.BonusSettleDetailWriteDao;
import com.ejavashop.entity.member.Bonus;
import com.ejavashop.entity.member.BonusSettleDetail;

@Component
public class BonusSettleDetailModel {

	private static org.apache.log4j.Logger log = org.apache.log4j.LogManager
                                                   .getLogger(BonusSettleDetailModel.class);
    
    @Resource
    private BonusSettleDetailWriteDao bonusSettleDetailWriteDao;
    
    /**
     * 根据id取得bonus_settle_detail对象
     * @param  bonusSettleDetailId
     * @return
     */
    public BonusSettleDetail getBonusSettleDetailById(Integer bonusSettleDetailId) {
    	return bonusSettleDetailWriteDao.get(bonusSettleDetailId);
    }
    
    /**
     * 保存bonus_settle_detail对象
     * @param  bonusSettleDetail
     * @return
     */
     public Integer saveBonusSettleDetail(BonusSettleDetail bonusSettleDetail) {
     	this.dbConstrains(bonusSettleDetail);
     	return bonusSettleDetailWriteDao.insert(bonusSettleDetail);
     }
     
     /**
     * 更新bonus_settle_detail对象
     * @param  bonusSettleDetail
     * @return
     */
     public Integer updateBonusSettleDetail(BonusSettleDetail bonusSettleDetail) {
     	this.dbConstrains(bonusSettleDetail);
     	return bonusSettleDetailWriteDao.update(bonusSettleDetail);
     }
     
     private void dbConstrains(BonusSettleDetail bonusSettleDetail) {
     }
     
     
     public List<BonusSettleDetail> getBonusSettleDetailByPage(Map<String, String> queryMap,
             Integer start,Integer size){
     	 return bonusSettleDetailWriteDao.getBonusDetailByPage(queryMap, start, size);
      }

 	/**
 	* 根据条件获取奖金数量
 	* @param queryMap
 	* @return
 	*/
      public  Integer getBonusSettleDetailCount(Map<String, String> queryMap){
 		return bonusSettleDetailWriteDao.getBonusDetailCount(queryMap);
 	}
}