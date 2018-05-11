package com.ejavashop.web.controller.index;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ejavashop.core.ConstantsEJS;
import com.ejavashop.core.ServiceResult;
import com.ejavashop.entity.member.Member;
import com.ejavashop.entity.shopm.pcindex.PcIndexBanner;
import com.ejavashop.entity.shopm.pcindex.PcIndexFloor;
import com.ejavashop.entity.shopm.pcindex.PcRecommend;
import com.ejavashop.service.cart.ICartService;
import com.ejavashop.service.news.INewsService;
import com.ejavashop.service.pcindex.IPcIndexBannerService;
import com.ejavashop.service.pcindex.IPcIndexFloorService;
import com.ejavashop.service.pcindex.IPcRecommendService;
import com.ejavashop.service.product.IProductCateService;
import com.ejavashop.vo.cart.CartInfoVO;
import com.ejavashop.vo.product.FrontProductCateVO;
import com.ejavashop.web.controller.BaseController;
import com.ejavashop.web.util.WebFrontSession;

/**
 * 首页controller
 * 首页初始化，以及一些公共的url
 * @Filename: IndexController.java
 * @Version: 1.0
 * @Author: 陈万海
 * @Email: chenwanhai@sina.com
 *
 */
@Controller
public class IndexController extends BaseController {

    Logger                        log = Logger.getLogger(this.getClass());

    @Resource
    private INewsService          newsService;
    @Resource
    private IProductCateService   productCateService;
    @Resource
    private ICartService          cartService;
    @Resource
    private IPcIndexBannerService pcIndexBannerService;
    @Resource
    private IPcIndexFloorService  pcIndexFloorService;
    @Resource
    private IPcRecommendService   pcRecommendService;

    @RequestMapping(value = "", method = { RequestMethod.GET })
    public String index(HttpServletRequest request, HttpServletResponse response,
                        Map<String, Object> dataMap) throws IOException {
        // 取得定时任务存入ServletContext中的首页缓存html字符串
        Object indexObj = request.getServletContext()
            .getAttribute(ConstantsEJS.PC_INDEX_HTML_CACHE);
        if (indexObj != null && indexObj.toString().length() > 0) {
            log.info("-------------缓存取得首页html");
            // 如果对象不为空，直接打印内容
            response.setContentType("text/html; charset=utf-8");
            PrintWriter out = response.getWriter();
            out.println(indexObj.toString());
            return null;
        } else {
            log.error("-------------直接打开页面");
            // 如果对象为空，说明ServletContext中还未缓存，则直接取数据库数据返回页面打开首页
            initIndex(dataMap, false);
            return "front/index/index";
        }

    }

    @RequestMapping(value = "index.html", method = { RequestMethod.GET })
    public String def(HttpServletRequest request, HttpServletResponse response,
                      Map<String, Object> dataMap) {
        return "redirect:/";
    }

    /**
     * 缓存时调用
     * @param request
     * @param response
     * @param dataMap
     * @return
     */
    @RequestMapping(value = "cacheIndex.html", method = { RequestMethod.GET })
    public String cacheIndex(HttpServletRequest request, HttpServletResponse response,
                             Map<String, Object> dataMap) {
        initIndex(dataMap, false);
        return "front/index/index";
    }

    @RequestMapping(value = "previewindex", method = { RequestMethod.GET })
    public String previewIndex(HttpServletRequest request, HttpServletResponse response,
                               Map<String, Object> dataMap) {
        initIndex(dataMap, true);
        return "front/index/index";
    }

    /**
     * 首页初始化方法
     * @param dataMap
     * @param isPreview
     */
    private void initIndex(Map<String, Object> dataMap, boolean isPreview) {
        // 首页轮播图
        ServiceResult<List<PcIndexBanner>> bannerResult = pcIndexBannerService
            .getPcIndexBannerForView(isPreview);
        if (!bannerResult.getSuccess()) {
            log.error(bannerResult.getMessage());
        }
        dataMap.put("bannerList", bannerResult.getResult());

        // 首页今日推荐
        ServiceResult<List<PcRecommend>> todayRecommendResult = pcRecommendService
            .getPcRecommendForView(PcRecommend.RECOMMEND_TYPE_2, isPreview);
        if (!todayRecommendResult.getSuccess()) {
            log.error(todayRecommendResult.getMessage());
        }
        dataMap.put("todayList", todayRecommendResult.getResult());

        // 首页热销推荐
        ServiceResult<List<PcRecommend>> hotRecommendResult = pcRecommendService
            .getPcRecommendForView(PcRecommend.RECOMMEND_TYPE_1, isPreview);
        if (!hotRecommendResult.getSuccess()) {
            log.error(hotRecommendResult.getMessage());
        }
        dataMap.put("hotList", hotRecommendResult.getResult());

        //首页楼层
        ServiceResult<List<PcIndexFloor>> floorResult = pcIndexFloorService
            .getPcIndexFloorForView(isPreview);
        if (!floorResult.getSuccess()) {
            log.error(floorResult.getMessage());
        }
        dataMap.put("floorList", floorResult.getResult());

        // 分类
        Map<String, Object> queryMap = new HashMap<String, Object>();
        ServiceResult<List<FrontProductCateVO>> serviceResult = productCateService
            .getProductCateList(queryMap);

        dataMap.put("cateList", serviceResult.getResult());
    }

    /**
     * 导航所有商品分类 
     * @param request
     * @param response
     * @param dataMap
     * @return
     */
    @RequestMapping(value = "/cateList.html", method = { RequestMethod.GET })
    public String getProductCateList(HttpServletRequest request, HttpServletResponse response,
                                     Map<String, Object> dataMap) {

        Map<String, Object> queryMap = new HashMap<String, Object>();
        ServiceResult<List<FrontProductCateVO>> serviceResult = new ServiceResult<List<FrontProductCateVO>>();
        serviceResult = productCateService.getProductCateList(queryMap);

        dataMap.put("cateList", serviceResult.getResult());

        return "front/commons/cateList";
    }

    /**
     * 右上角 我的购物车
     * @param request
     * @param response
     * @param dataMap
     * @return
     */
    @RequestMapping(value = "/previewMyCart.html", method = { RequestMethod.GET })
    public String previewMyCart(HttpServletRequest request, HttpServletResponse response,
                                Map<String, Object> dataMap) {
        Member member = WebFrontSession.getLoginedUser(request);
        //取购物车信息  产品价格 按照商家来区分
        //查询购物车
        if (member != null) {
            ServiceResult<CartInfoVO> serviceResult = cartService.getCartInfoByMId(member.getId(),
                null, ConstantsEJS.SOURCE_1_PC, 1);
            dataMap.put("cartInfoVO", serviceResult.getResult());
        }
        return "front/cart/previewcart";
    }

}
