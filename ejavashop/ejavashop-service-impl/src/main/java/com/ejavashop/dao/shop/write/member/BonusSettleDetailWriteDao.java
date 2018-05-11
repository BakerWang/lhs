package com.ejavashop.dao.shop.write.member;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.ejavashop.entity.member.BonusSettleDetail;

@Repository
public interface BonusSettleDetailWriteDao {
 
	BonusSettleDetail get(java.lang.Integer id);
	
	Integer insert(BonusSettleDetail bonusSettleDetail);
	
	Integer insertSettle(@Param("queryMap") Map<String,String> queryMap);
	
	Integer update(BonusSettleDetail bonusSettleDetail);
	
	List<BonusSettleDetail> getBonusDetailByPage(@Param("queryMap") Map<String, String> queryMap,
             @Param("start") Integer start, @Param("size") Integer size);


	Integer getBonusDetailCount(@Param("queryMap") Map<String, String> queryMap);
}