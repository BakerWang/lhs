package com.ejavashop.dao.shop.write.member;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.ejavashop.entity.member.BonusSettle;

@Repository
public interface BonusSettleWriteDao {
 
	BonusSettle get(java.lang.Integer id);
	
	Integer insert(BonusSettle bonusSettle);
	
	Integer update(BonusSettle bonusSettle);
	
	
	 List<BonusSettle> getBonusSettleByPage(@Param("queryMap") Map<String, String> queryMap,
             @Param("start") Integer start, @Param("size") Integer size);

	/**
	* 根据条件获取奖金数量
	* @param queryMap
	* @return
	*/
	Integer getBonusSettleCount(@Param("queryMap") Map<String, String> queryMap);
}