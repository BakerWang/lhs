package com.ejavashop.entity.member;

import java.io.Serializable;
/**
 * 
 * <p>Table: <strong>bonus</strong>
 * <p><table class="er-mapping" cellspacing=0 cellpadding=0 style="border:solid 1 #666;padding:3px;">
 *   <tr style="background-color:#ddd;Text-align:Left;">
 *     <th nowrap>属性名</th><th nowrap>属性类型</th><th nowrap>字段名</th><th nowrap>字段类型</th><th nowrap>说明</th>
 *   </tr>
 *   <tr><td>id</td><td>{@link java.lang.Integer}</td><td>id</td><td>int</td><td>id</td></tr>
 *   <tr><td>fromUser</td><td>{@link java.lang.Integer}</td><td>from_user</td><td>int</td><td>fromUser</td></tr>
 *   <tr><td>toUser</td><td>{@link java.lang.Integer}</td><td>to_user</td><td>int</td><td>toUser</td></tr>
 *   <tr><td>money</td><td>{@link java.math.BigDecimal}</td><td>money</td><td>decimal</td><td>money</td></tr>
 *   <tr><td>createTime</td><td>{@link java.util.Date}</td><td>create_time</td><td>datetime</td><td>createTime</td></tr>
 *   <tr><td>status</td><td>{@link java.lang.Integer}</td><td>status</td><td>tinyint</td><td>是否结算</td></tr>
 * </table>
 *
 */
public class Bonus implements Serializable {
 
 	/**
	 * 
	 */
	private static final long serialVersionUID = 4856984503152893924L;
	private java.lang.Integer id;
 	private java.lang.Integer fromUser;
 	private java.lang.Integer toUser;
 	private java.math.BigDecimal money;
 	private java.util.Date createTime;
 	private java.lang.Integer status;
 	
 	private String fromUserName;
 	private String toUserName;
 	
 		
	public String getFromUserName() {
		return fromUserName;
	}

	public void setFromUserName(String fromUserName) {
		this.fromUserName = fromUserName;
	}

	public String getToUserName() {
		return toUserName;
	}

	public void setToUserName(String toUserName) {
		this.toUserName = toUserName;
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
     * 获取fromUser
     */
	public java.lang.Integer getFromUser(){
		return this.fromUser;
	}
 		
	/**
     * 设置fromUser
     */
	public void setFromUser(java.lang.Integer fromUser){
		this.fromUser = fromUser;
	}
 		
	/**
     * 获取toUser
     */
	public java.lang.Integer getToUser(){
		return this.toUser;
	}
 		
	/**
     * 设置toUser
     */
	public void setToUser(java.lang.Integer toUser){
		this.toUser = toUser;
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
 		
	/**
     * 获取是否结算
     */
	public java.lang.Integer getStatus(){
		return this.status;
	}
 		
	/**
     * 设置是否结算
     */
	public void setStatus(java.lang.Integer status){
		this.status = status;
	}
 }