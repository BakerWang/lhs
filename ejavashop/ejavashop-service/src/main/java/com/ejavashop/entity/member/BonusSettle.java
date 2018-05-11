package com.ejavashop.entity.member;

import java.io.Serializable;
/**
 * 
 * <p>Table: <strong>bonus_settle</strong>
 * <p><table class="er-mapping" cellspacing=0 cellpadding=0 style="border:solid 1 #666;padding:3px;">
 *   <tr style="background-color:#ddd;Text-align:Left;">
 *     <th nowrap>属性名</th><th nowrap>属性类型</th><th nowrap>字段名</th><th nowrap>字段类型</th><th nowrap>说明</th>
 *   </tr>
 *   <tr><td>id</td><td>{@link java.lang.Integer}</td><td>id</td><td>int</td><td>id</td></tr>
 *   <tr><td>settleDate</td><td>{@link java.util.Date}</td><td>settle_date</td><td>timestamp</td><td>settleDate</td></tr>
 *   <tr><td>createUser</td><td>{@link java.lang.String}</td><td>create_user</td><td>varchar</td><td>createUser</td></tr>
 * </table>
 *
 */
public class BonusSettle implements Serializable {
 
 	private java.lang.Integer id;
 	private java.util.Date settleDate;
 	private java.lang.String createUser;
 	
 		
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
     * 获取settleDate
     */
	public java.util.Date getSettleDate(){
		return this.settleDate;
	}
 		
	/**
     * 设置settleDate
     */
	public void setSettleDate(java.util.Date settleDate){
		this.settleDate = settleDate;
	}
 		
	/**
     * 获取createUser
     */
	public java.lang.String getCreateUser(){
		return this.createUser;
	}
 		
	/**
     * 设置createUser
     */
	public void setCreateUser(java.lang.String createUser){
		this.createUser = createUser;
	}
 }