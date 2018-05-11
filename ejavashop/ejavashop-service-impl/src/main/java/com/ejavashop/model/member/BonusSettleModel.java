package com.ejavashop.model.member;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.ejavashop.core.StringUtil;
import com.ejavashop.core.TimeUtil;
import com.ejavashop.core.exception.BusinessException;
import com.ejavashop.dao.shop.write.member.BonusDao;
import com.ejavashop.dao.shop.write.member.BonusSettleDetailWriteDao;
import com.ejavashop.dao.shop.write.member.BonusSettleWriteDao;
import com.ejavashop.entity.member.BonusSettle;

@Component
public class BonusSettleModel {

	private static org.apache.log4j.Logger log = org.apache.log4j.LogManager
                                                   .getLogger(BonusSettleModel.class);
    
    @Resource
    private BonusSettleWriteDao bonusSettleWriteDao;
    @Resource
    private BonusSettleDetailWriteDao bonusSettleDetailWriteDao;
    
    @Resource
    private BonusDao bonusDao;
    
    @Resource
    private DataSourceTransactionManager    transactionManager;
    
    /**
     * 根据id取得bonus_settle对象
     * @param  bonusSettleId
     * @return
     */
    public BonusSettle getBonusSettleById(Integer bonusSettleId) {
    	return bonusSettleWriteDao.get(bonusSettleId);
    }
    
    /**
     * 保存bonus_settle对象
     * @param  bonusSettle
     * @return
     */
     public Integer saveBonusSettle(BonusSettle bonusSettle) {
    	 // 事务管理
         DefaultTransactionDefinition def = new DefaultTransactionDefinition();
         def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
         TransactionStatus status = transactionManager.getTransaction(def);
         try{
	         Integer settleId =bonusSettleWriteDao.insert(bonusSettle);
	         if(settleId !=null && settleId>0){
	        	 String sat = TimeUtil.getFri();
	        	 String lastsat = TimeUtil.getSat();
	        	 Map<String,String> map = new HashMap<String,String>();
	        	 map.put("settleId", String.valueOf(bonusSettle.getId()));
	        	 map.put("startTime", lastsat);
	        	 map.put("endTime", sat);
	        	 int aa = bonusSettleDetailWriteDao.insertSettle(map);
	        	 int count= bonusDao.updateList(map);
	         }
	         transactionManager.commit(status);
	          return settleId;
	      } catch (BusinessException be) {
	          transactionManager.rollback(status);
	          throw be;
	      } catch (Exception e) {
	          transactionManager.rollback(status);
	          throw e;
	      }
     }
     
     /**
     * 更新bonus_settle对象
     * @param  bonusSettle
     * @return
     */
     public Integer updateBonusSettle(BonusSettle bonusSettle) {
     	this.dbConstrains(bonusSettle);
     	return bonusSettleWriteDao.update(bonusSettle);
     }
     
     private void dbConstrains(BonusSettle bonusSettle) {
		bonusSettle.setCreateUser(StringUtil.dbSafeString(bonusSettle.getCreateUser(), true, 100));
     }
     
     
     public List<BonusSettle> getBonusSettleByPage(Map<String, String> queryMap,
             Integer start,Integer size){
     	 return bonusSettleWriteDao.getBonusSettleByPage(queryMap, start, size);
      }

 	/**
 	* 根据条件获取奖金数量
 	* @param queryMap
 	* @return
 	*/
      public  Integer getBonusSettleCount(Map<String, String> queryMap){
 		return bonusSettleWriteDao.getBonusSettleCount(queryMap);
 	}
}