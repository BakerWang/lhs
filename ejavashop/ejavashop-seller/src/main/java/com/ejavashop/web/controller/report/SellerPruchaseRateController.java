package com.ejavashop.web.controller.report;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ejavashop.service.order.IOrdersProductService;
import com.ejavashop.service.report.IStatisticsService;
import com.ejavashop.web.controller.BaseController;

/**
 * 购买率统计Controller
 *                       
 * @Filename: SellerPruchaseRateController.java
 * @Version: 1.0
 * @Author: zhaihl
 * @Email: zhaihl_0@126.com
 *
 */
@Controller
@RequestMapping(value = "seller/report/product")
public class SellerPruchaseRateController extends BaseController {
    @Resource
    private IOrdersProductService ordersProductService;
    @Resource
    private IStatisticsService    statisticsService;

    /**
     * 购买率统计
     * 购买率=有效订单数/总访客数
     * TODO
     * @param request
     * @param dataMap
     * @return
     */
    @RequestMapping(value = "phurchaseRate", method = RequestMethod.GET)
    public String phurchaseRate(HttpServletRequest request, ModelMap dataMap, String model,
                                Integer year, Integer month) {

        return "seller/report/product/phurchaseRate";
    }
}
