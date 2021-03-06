package com.ejavashop.web.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.ejavashop.entity.seller.SellerUser;

/**
 * 商家Session管理
 *                       
 * @Filename: WebSellerSession.java
 * @Version: 1.0
 * @Author: 王朋
 * @Email: wpjava@163.com
 *
 */
public class WebSellerSession {

    public static final String SESSION_SELLER_USER = "SESSION_SELLER_USER";

    /**
     * 放入Session
     * @param request
     * @param sellerUser
     */
    public static void putSellerUser(HttpServletRequest request, SellerUser sellerUser) {
        HttpSession session = request.getSession();
        session.setAttribute(SESSION_SELLER_USER, sellerUser);
    }

    /**
     * 取得管理端Session
     * @param request
     * @return
     */
    public static SellerUser getSellerUser(HttpServletRequest request) {
        HttpSession session = request.getSession();
        SellerUser sellerUser = (SellerUser) session.getAttribute(SESSION_SELLER_USER);
        return sellerUser;
    }
}
