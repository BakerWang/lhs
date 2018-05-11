package com.ejavashop.dao.shop.write.member;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.ejavashop.entity.member.Bonus;

@Repository
public interface BonusDao {
 
	Bonus get(java.lang.Integer id);
	
	Integer insert(Bonus bonus);
	
	Integer update(Bonus bonus);
	
	/**
     * 根据条件获取奖金信息
     * @param queryMap
     * @param start
     * @param size
     * @return
     */
    List<Bonus> getBonusByPage(@Param("queryMap") Map<String, String> queryMap,
                            @Param("start") Integer start, @Param("size") Integer size);

    /**
     * 根据条件获取奖金数量
     * @param queryMap
     * @return
     */
    Integer getBonusCount(@Param("queryMap") Map<String, String> queryMap);
    
    
    Integer updateList(@Param("queryMap") Map<String, String> queryMap);
}