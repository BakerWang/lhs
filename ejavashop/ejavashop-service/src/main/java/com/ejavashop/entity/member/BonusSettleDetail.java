package com.ejavashop.entity.member;

import java.io.Serializable;
/**
 * 
 * <p>Table: <strong>bonus_settle_detail</strong>
 * <p><table class="er-mapping" cellspacing=0 cellpadding=0 style="border:solid 1 #666;padding:3px;">
 *   <tr style="background-color:#ddd;Text-align:Left;">
 *     <th nowrap>属性名</th><th nowrap>属性类型</th><th nowrap>字段名</th><th nowrap>字段类型</th><th nowrap>说明</th>
 *   </tr>
 *   <tr><td>id</td><td>{@link java.lang.Integer}</td><td>id</td><td>int</td><td>id</td></tr>
 *   <tr><td>memberId</td><td>{@link java.lang.Integer}</td><td>member_id</td><td>int</td><td>memberId</td></tr>
 *   <tr><td>money</td><td>{@link java.math.BigDecimal}</td><td>money</td><td>decimal</td><td>money</td></tr>
 *   <tr><td>createTime</td><td>{@link java.util.Date}</td><td>create_time</td><td>timestamp</td><td>createTime</td></tr>
 * </table>
 *
 */
public class BonusSettleDetail implements Serializable {
 
 	/**
	 * 
	 */
	private static final long serialVersionUID = 827261276424249744L;
	private java.lang.Integer id;
 	private java.lang.Integer memberId;
 	private java.math.BigDecimal money;
 	private java.util.Date createTime;
 	private String name;
 	
 	
 	
 		
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
     * 获取id
     */
	public java.lang.Integer getId(){
		return this.id;
	}
 		
	/**
     * 设置id
     */
	public void setId(java.lang.Integer id){
		this.id = id;
	}
 		
	/**
     * 获取memberId
     */
	public java.lang.Integer getMemberId(){
		return this.memberId;
	}
 		
	/**
     * 设置memberId
     */
	public void setMemberId(java.lang.Integer memberId){
		this.memberId = memberId;
	}
 		
	/**
     * 获取money
     */
	public java.math.BigDecimal getMoney(){
		return this.money;
	}
 		
	/**
     * 设置money
     */
	public void setMoney(java.math.BigDecimal money){
		this.money = money;
	}
 		
	/**
     * 获取createTime
     */
	public java.util.Date getCreateTime(){
		return this.createTime;
	}
 		
	/**
     * 设置createTime
     */
	public void setCreateTime(java.util.Date createTime){
		this.createTime = createTime;
	}
 }