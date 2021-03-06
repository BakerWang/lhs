package com.ejavashop.web.controller.order;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ejavashop.core.ConstantsEJS;
import com.ejavashop.core.ConvertUtil;
import com.ejavashop.core.HttpJsonResult;
import com.ejavashop.core.HttpJsonResultForAjax;
import com.ejavashop.core.Md5;
import com.ejavashop.core.ServiceResult;
import com.ejavashop.core.StringUtil;
import com.ejavashop.core.WebUtil;
import com.ejavashop.entity.member.Member;
import com.ejavashop.entity.member.MemberAddress;
import com.ejavashop.entity.operate.Config;
import com.ejavashop.entity.order.Invoice;
import com.ejavashop.entity.order.Orders;
import com.ejavashop.entity.product.Product;
import com.ejavashop.entity.product.ProductGoods;
import com.ejavashop.entity.promotion.coupon.CouponUser;
import com.ejavashop.entity.promotion.flash.ActFlashSale;
import com.ejavashop.entity.seller.Seller;
import com.ejavashop.service.cart.ICartService;
import com.ejavashop.service.member.IInvoiceService;
import com.ejavashop.service.member.IMemberAddressService;
import com.ejavashop.service.member.IMemberService;
import com.ejavashop.service.operate.IConfigService;
import com.ejavashop.service.order.IOrdersProductService;
import com.ejavashop.service.order.IOrdersService;
import com.ejavashop.service.product.IProductGoodsService;
import com.ejavashop.service.product.IProductService;
import com.ejavashop.service.promotion.IActFlashSaleService;
import com.ejavashop.service.promotion.ICouponService;
import com.ejavashop.service.seller.ISellerService;
import com.ejavashop.service.seller.ISellerTransportService;
import com.ejavashop.vo.cart.CartInfoVO;
import com.ejavashop.vo.member.FrontCheckPwdVO;
import com.ejavashop.vo.order.OrderCommitVO;
import com.ejavashop.vo.order.OrderCouponVO;
import com.ejavashop.vo.order.OrderSuccessVO;
import com.ejavashop.web.controller.BaseController;
import com.ejavashop.web.csrf.CsrfTokenManager;
import com.ejavashop.web.util.CommUtil;
import com.ejavashop.web.util.WebFrontSession;

/**
 * 购物流程-订单<br>
 * 本controller中得请求都需要登录才能访问
 * 
 * @Filename: FrontOrdersController.java
 * @Version: 1.0
 * @Author: 陈万海
 * @Email: chenwanhai@sina.com
 *
 */
@Controller
public class OrdersController extends BaseController {

    @Resource
    private IOrdersService          ordersService;
    @Resource
    private IOrdersProductService   ordersProductService;
    @Resource
    private ICartService            cartService;
    @Resource
    private IMemberAddressService   memberAddressService;
    @Resource
    private IInvoiceService         invoiceService;
    @Resource
    private IMemberService          memberService;
    @Resource
    private IConfigService          configservice;
    @Resource
    private ICouponService          couponService;
    @Resource
    private IActFlashSaleService    actFlashSaleService;
    @Resource
    private ISellerTransportService sellerTransportService;
    @Resource
    private ISellerService          sellerService;
    @Resource
    private IProductService         productService;
    @Resource
    private IProductGoodsService    productGoodsService;

    /**
     * 跳转到提交订单页面 计算总金额,运费、货品小计，按店铺拆分订单
     * @param request
     * @param response
     * @param map
     * @return
     * @throws IOException 
     */
    @RequestMapping(value = "order/info.html", method = { RequestMethod.GET })
    public String toOrderSubmit(HttpServletRequest request, ModelMap map,
                                HttpServletResponse response) {
        Member member = WebFrontSession.getLoginedUser(request);
        // 收货地址信息
        ServiceResult<List<MemberAddress>> serviceResult = memberAddressService
            .getMemberAddressByMId(member.getId());
        List<MemberAddress> addressList = null;
        MemberAddress defaultAddress = null;
        // 是否有默认地址
        String hasDefaultAdd = "no";
        // 获取默认收货地址，如果没有取第一个
        if (serviceResult.getSuccess()) {
            addressList = serviceResult.getResult();
            if (addressList != null && addressList.size() > 0) {
                defaultAddress = addressList.get(0);
                for (MemberAddress address : addressList) {
                    if (address.getState() == MemberAddress.STATE_1) {
                        defaultAddress = address;
                        hasDefaultAdd = "yes";
                        break;
                    }
                }
            }
        }
        map.put("hasDefaultAdd", hasDefaultAdd);
        map.put("addressList", addressList);
        map.put("defaultAddress", defaultAddress);
        // 构建默认值 ，默认在线支付。收货地址为默认地址，发票默认为不开发票
        OrderCommitVO orderCommitVO = new OrderCommitVO();
        orderCommitVO.setInvoiceType("");
        orderCommitVO.setInvoiceTitle("");
        orderCommitVO.setPaymentName("在线支付");
        orderCommitVO.setPaymentCode(ConstantsEJS.PAYMENT_CODE_ONLINE);
        map.put("orderCommitVO", orderCommitVO);

        // 取购物车信息  产品价格 按照商家来区分
        // 查询购物车
        ServiceResult<CartInfoVO> cartServiceResult = cartService.getCartInfoByMId(member.getId(),
            defaultAddress, ConstantsEJS.SOURCE_1_PC, 2);
        map.put("cartInfoVO", cartServiceResult.getResult());

        // 获取发票信息
        ServiceResult<List<Invoice>> invoiceResult = invoiceService.getInvoiceByMId(member.getId());
        map.put("invoiceList", invoiceResult.getResult());

        //取会员余额信息
        ServiceResult<Member> memberResult = memberService.getMemberById(member.getId());
        if (memberResult.getResult() == null) {
            map.put("info", "会员信息获取失败。");
            return "front/commons/error";
        }
        map.put("member", memberResult.getResult());

        ServiceResult<Config> configById = configservice.getConfigById(ConstantsEJS.CONFIG_ID);
        if (configById.getResult() != null) {
            Config config = configById.getResult();
            if (config.getIntegralScale() > 0) {
                map.put("config", config);
            }
        }
        return "front/order/shoporder";
    }

    /**
     * 根据地址ID计算新的运费信息
     * @param request
     * @param response
     * @param map
     * @param addressId
     * @return
     */
    @RequestMapping(value = "order/calculateTransFee.html", method = { RequestMethod.POST })
    public @ResponseBody HttpJsonResult<CartInfoVO> calculateTransFee(HttpServletRequest request,
                                                                      HttpServletResponse response,
                                                                      ModelMap map,
                                                                      Integer addressId) {
        Member member = WebFrontSession.getLoginedUser(request);
        ServiceResult<MemberAddress> memberAddressRlt = memberAddressService
            .getMemberAddressById(addressId);
        if (!memberAddressRlt.getSuccess() || memberAddressRlt.getResult() == null) {
            return new HttpJsonResult<>("收获地址信息获取失败！");
        }
        // 查询购物车
        ServiceResult<CartInfoVO> cartServiceResult = cartService.getCartInfoByMId(member.getId(),
            memberAddressRlt.getResult(), ConstantsEJS.SOURCE_1_PC, 2);
        if (!cartServiceResult.getSuccess() || cartServiceResult.getResult() == null) {
            return new HttpJsonResult<>("计算运费信息失败！");
        }
        HttpJsonResult<CartInfoVO> jsonResult = new HttpJsonResult<CartInfoVO>();
        jsonResult.setData(cartServiceResult.getResult());
        return jsonResult;
    }

    /**
     * 提交订单 计算总金额 按店铺拆分订单
     * @param request
     * @param response
     * @param map
     * @return
     * @throws IOException 
     */
    @RequestMapping(value = "order/ordercommit.html", method = { RequestMethod.POST })
    public @ResponseBody HttpJsonResultForAjax<OrderSuccessVO> orderSubmit(HttpServletRequest request,
                                                                           HttpServletResponse response,
                                                                           OrderCommitVO orderCommitVO,
                                                                           ModelMap map) {
        Member member = WebFrontSession.getLoginedUser(request);
        orderCommitVO.setMemberId(member.getId());

        if (orderCommitVO.getInvoiceStatus() == null) {
            // 默认不开发票
            orderCommitVO.setInvoiceStatus(Orders.INVOICE_STATUS_0);
        }
        // 设定IP地址
        orderCommitVO.setIp(WebUtil.getIpAddr(request));
        // 设定来源
        orderCommitVO.setSource(ConstantsEJS.SOURCE_1_PC);
        orderCommitVO.setRemark("");

        // 获取优惠券使用信息
        Map<Integer, OrderCouponVO> sellerCouponMap = new HashMap<Integer, OrderCouponVO>();
        String useCouponSellerIds = request.getParameter("useCouponSellerIds");
        if (!StringUtil.isEmpty(useCouponSellerIds, true)) {
            String[] split = useCouponSellerIds.split(",");
            for (String sellerIdStr : split) {
                if (!StringUtil.isEmpty(sellerIdStr, true)) {
                    Integer sellerId = ConvertUtil.toInt(sellerIdStr, 0);
                    String couponTypeStr = request.getParameter("couponType" + sellerId);
                    Integer couponType = ConvertUtil.toInt(couponTypeStr, 0);
                    String couponSn = request.getParameter("couponSn" + sellerId);
                    String couponPassword = request.getParameter("couponPassword" + sellerId);
                    OrderCouponVO orderCouponVO = new OrderCouponVO();
                    orderCouponVO.setSellerId(sellerId);
                    orderCouponVO.setCouponType(couponType);
                    orderCouponVO.setCouponSn(couponSn);
                    orderCouponVO.setCouponPassword(couponPassword);
                    sellerCouponMap.put(sellerId, orderCouponVO);
                }
            }
        }
        orderCommitVO.setSellerCouponMap(sellerCouponMap);

        // 提交订单
        ServiceResult<OrderSuccessVO> serviceResult = ordersService.orderCommit(orderCommitVO);

        HttpJsonResultForAjax<OrderSuccessVO> jsonResult = new HttpJsonResultForAjax<OrderSuccessVO>();
        if (!serviceResult.getSuccess()) {
            if (ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR.equals(serviceResult.getCode())) {
                throw new RuntimeException(serviceResult.getMessage());
            } else {
                jsonResult = new HttpJsonResultForAjax<OrderSuccessVO>(null,
                    CsrfTokenManager.getTokenForSession(
                        CsrfTokenManager.getMemkeyFromRequest(request), request.getSession()));
                jsonResult.setMessage(serviceResult.getMessage());
                return jsonResult;
            }
        }
        //订单提交后返回结果
        OrderSuccessVO orderSuccessVO = serviceResult.getResult();
        if (orderSuccessVO.getIsPaid()) {
            // 如果已经付过款，则调用下单送积分方法
            for (Orders order : orderSuccessVO.getOrdersList()) {
                memberService.memberOrderSendValue(member.getId(), member.getName(), order.getId());
            }
        }
        //支付随机码 避免重复提交
        String order_session = CommUtil.randomString(32);
        // 存入session，支付时取出后与参数传入的对比，判断是否二次提交
        request.getSession(false).setAttribute("order_session", order_session);
        request.getSession(false).setAttribute("order_success_vo", orderSuccessVO);
        orderSuccessVO.setPaySessionstr(order_session);

        jsonResult.setData(orderSuccessVO);

        return jsonResult;
    }

    /**
     * 跳转到提交订单成功页面 （货到付款）
     * @param request
     * @param response
     * @param dataMap
     * @return
     */
    @RequestMapping(value = "order/success.html", method = { RequestMethod.GET })
    public String toOrderSuccess(HttpServletRequest request, HttpServletResponse response,
                                 Map<String, Object> dataMap,
                                 @RequestParam(value = "relationOrderSn", required = true) String relationOrderSn) {
        Member member = WebFrontSession.getLoginedUser(request);
        //根据关联订单 查询订单信息
        ServiceResult<OrderSuccessVO> serviceResult = ordersService
            .getOrdersByRelationOrderSn(relationOrderSn, member.getId());
        dataMap.put("ordervo", serviceResult.getResult());

        return "front/order/ordersuccess";
    }

    /**
     * 跳转到支付页面 （在线支付）
     * @param request
     * @param response
     * @param dataMap
     * @return
     */
    @RequestMapping(value = "order/pay.html", method = { RequestMethod.GET })
    public String toPayfor(HttpServletRequest request, HttpServletResponse response,
                           Map<String, Object> dataMap, String relationOrderSn,
                           String paySessionstr) {

        if (StringUtil.isEmpty(relationOrderSn, true)) {
            dataMap.put("info", "请选择要支付的订单，谢谢！");
            return "front/commons/error";
        }
        Member member = WebFrontSession.getLoginedUser(request);
        ServiceResult<Member> memberResult = memberService.getMemberById(member.getId());
        if (memberResult.getResult() == null) {
            dataMap.put("info", "会员信息获取失败。");
            return "front/commons/error";
        }
        dataMap.put("member", memberResult.getResult());

        // 如果paySessionstr不为空，则是从下单后直接跳转过来，否则是从订单列表页跳转而来
        if (!StringUtil.isEmpty(paySessionstr, true)) {

            String order_session = CommUtil
                .null2String(request.getSession(false).getAttribute("order_session"));
            if (!order_session.equals(paySessionstr)) {
                dataMap.put("info", "请使用正常方式支付订单，谢谢！");
                return "front/commons/error";
            }
            OrderSuccessVO orderSuccessVO = (OrderSuccessVO) request.getSession(false)
                .getAttribute("order_success_vo");
            if (orderSuccessVO == null) {
                dataMap.put("info", "session异常，请到订单中心支付订单，谢谢！");
                return "front/commons/error";
            }

            dataMap.put("fromType", 1);

            dataMap.put("paySessionstr", paySessionstr);
            dataMap.put("relationOrderSn", relationOrderSn);
            dataMap.put("payAmount", orderSuccessVO.getPayAmount());
            dataMap.put("orderSuccessVO", orderSuccessVO);
        } else {

            dataMap.put("fromType", 2);

            // 支付随机码 避免重复提交
            String order_session = CommUtil.randomString(32);
            request.getSession(false).setAttribute("order_session", order_session);

            dataMap.put("paySessionstr", order_session);

            ServiceResult<OrderSuccessVO> orderSuccessVOResult = ordersService
                .getOrdersByRelationOrderSn(relationOrderSn, member.getId());
            if (!orderSuccessVOResult.getSuccess()) {
                dataMap.put("info", "订单信息获取出错，请稍后再试！");
                return "front/commons/error";
            }
            OrderSuccessVO orderSuccessVO = orderSuccessVOResult.getResult();
            dataMap.put("relationOrderSn", relationOrderSn);
            dataMap.put("payAmount", orderSuccessVO.getPayAmount());
        }

        return "front/order/payfor";
    }

    /**
     * 保存发票抬头
     * @param request
     * @param response
     * @param map
     * @return
     * @throws IOException 
     */
    @RequestMapping(value = "order/saveinvoice.html", method = { RequestMethod.POST })
    public @ResponseBody HttpJsonResult<Boolean> saveInvoice(HttpServletRequest request,
                                                             HttpServletResponse response,
                                                             Invoice invoice) {

        Member member = WebFrontSession.getLoginedUser(request);

        ServiceResult<Integer> serviceResult = new ServiceResult<Integer>();
        serviceResult = invoiceService.saveInvoice(invoice, member.getId());

        HttpJsonResult<Boolean> jsonResult = new HttpJsonResult<Boolean>();
        if (!serviceResult.getSuccess()) {
            if (ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR.equals(serviceResult.getCode())) {
                throw new RuntimeException(serviceResult.getMessage());
            } else {
                jsonResult = new HttpJsonResult<Boolean>(serviceResult.getMessage());
            }
        }
        return jsonResult;
    }

    /**
     * 更新发票抬头
     * @param request
     * @param response
     * @param map
     * @return
     * @throws IOException 
     */
    @RequestMapping(value = "order/updateinvoice.html", method = { RequestMethod.POST })
    public @ResponseBody HttpJsonResult<Boolean> updateInvoice(HttpServletRequest request,
                                                               HttpServletResponse response,
                                                               Invoice invoice) {
        ServiceResult<Integer> serviceResult = new ServiceResult<Integer>();
        serviceResult = invoiceService.updateInvoice(invoice);

        HttpJsonResult<Boolean> jsonResult = new HttpJsonResult<Boolean>();
        if (!serviceResult.getSuccess()) {
            if (ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR.equals(serviceResult.getCode())) {
                throw new RuntimeException(serviceResult.getMessage());
            } else {
                jsonResult = new HttpJsonResult<Boolean>(serviceResult.getMessage());
            }
        }
        return jsonResult;
    }

    /**
     * 删除发票
     * @param request
     * @param response
     * @param map
     * @return
     * @throws IOException 
     */
    @RequestMapping(value = "order/deleteinvoice.html", method = { RequestMethod.GET })
    public @ResponseBody HttpJsonResult<Boolean> delInvoice(HttpServletRequest request,
                                                            HttpServletResponse response,
                                                            Invoice invoice,
                                                            @RequestParam(value = "invoiceId", required = true) Integer invoiceId) {
        ServiceResult<Boolean> serviceResult = new ServiceResult<Boolean>();
        serviceResult = invoiceService.delInvoice(invoiceId);

        HttpJsonResult<Boolean> jsonResult = new HttpJsonResult<Boolean>();
        if (!serviceResult.getSuccess()) {
            if (ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR.equals(serviceResult.getCode())) {
                throw new RuntimeException(serviceResult.getMessage());
            } else {
                jsonResult = new HttpJsonResult<Boolean>(serviceResult.getMessage());
            }
        }
        return jsonResult;
    }

    /**
     * 判断 余额支付密码是否正确
     * @param request
     * @param response
     * @param map
     * @return
     * @throws IOException 
     */
    @RequestMapping(value = "order/checkbalancepwd.html", method = { RequestMethod.GET })
    public @ResponseBody HttpJsonResult<FrontCheckPwdVO> checkcheckBalancePwd(HttpServletRequest request,
                                                                              HttpServletResponse response,
                                                                              @RequestParam(value = "balancePwd", required = true) String balancePwd) {

        Member member = WebFrontSession.getLoginedUser(request);

        ServiceResult<FrontCheckPwdVO> serviceResult = new ServiceResult<FrontCheckPwdVO>();
        serviceResult = memberService.checkcheckBalancePwd(balancePwd, member.getId());

        HttpJsonResult<FrontCheckPwdVO> jsonResult = new HttpJsonResult<FrontCheckPwdVO>();
        if (!serviceResult.getSuccess()) {
            if (ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR.equals(serviceResult.getCode())) {
                throw new RuntimeException(serviceResult.getMessage());
            } else {
                jsonResult = new HttpJsonResult<FrontCheckPwdVO>(serviceResult.getMessage());
            }
        }
        jsonResult.setData(serviceResult.getResult());
        return jsonResult;
    }

    /**
     * 获取用户当前可用的已绑定的优惠券
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "order/getsellercoupon.html", method = { RequestMethod.GET })
    public @ResponseBody HttpJsonResult<List<CouponUser>> getSellerCoupon(HttpServletRequest request,
                                                                          HttpServletResponse response) {

        Member member = WebFrontSession.getLoginedUser(request);

        Integer sellerId = ConvertUtil.toInt(request.getParameter("sellerId"), 0);

        ServiceResult<List<CouponUser>> serviceResult = couponService
            .getEffectiveByMemberIdAndSellerId(member.getId(), sellerId);

        HttpJsonResult<List<CouponUser>> jsonResult = new HttpJsonResult<List<CouponUser>>();
        if (!serviceResult.getSuccess()) {
            jsonResult = new HttpJsonResult<List<CouponUser>>(serviceResult.getMessage());
        }
        jsonResult.setData(serviceResult.getResult());
        return jsonResult;
    }

    /**
     * 检查优惠券的可用性
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "order/checksellercoupon.html", method = { RequestMethod.GET })
    public @ResponseBody HttpJsonResult<CouponUser> checkSellerCoupon(HttpServletRequest request,
                                                                      HttpServletResponse response) {

        Member member = WebFrontSession.getLoginedUser(request);

        String orderAmount = request.getParameter("orderAmount");
        String couponTypeStr = request.getParameter("couponType");
        Integer couponType = ConvertUtil.toInt(couponTypeStr, 0);
        String couponSn = request.getParameter("couponSn");
        String couponPassword = request.getParameter("couponPassword");
        Integer sellerId = ConvertUtil.toInt(request.getParameter("sellerId"), 0);

        ServiceResult<CouponUser> couponUserRlt = couponService
            .getCouponUserOnlyByCouponSn(couponSn);
        if (!couponUserRlt.getSuccess()) {
            return new HttpJsonResult<CouponUser>(couponUserRlt.getMessage());
        }
        if (couponUserRlt.getResult() == null) {
            return new HttpJsonResult<CouponUser>("优惠券不存在，请确认是否输入正确。");
        }
        CouponUser couponUser = couponUserRlt.getResult();

        Integer memberId = member.getId();

        if (!sellerId.equals(couponUser.getSellerId())) {
            return new HttpJsonResult<CouponUser>(
                "优惠券【" + couponUser.getCouponSn() + "】只能购买" + couponUser.getSellerName() + "的商品。");
        }

        if (couponType == OrderCouponVO.COUPON_TYPE_1) {
            // 检查优惠券所属用户
            if (!memberId.equals(couponUser.getMemberId())) {
                return new HttpJsonResult<CouponUser>(
                    "优惠券【" + couponUser.getCouponSn() + "】不是属于您的优惠券，不能使用。");
            }
        } else if (couponType == OrderCouponVO.COUPON_TYPE_2) {
            // 校验密码
            if (couponUser.getPassword() == null
                || !couponUser.getPassword().equals(Md5.getMd5String(couponPassword))) {
                return new HttpJsonResult<CouponUser>(
                    "优惠券【" + couponUser.getCouponSn() + "】密码不对，请重新输入。");
            }
            // 检查优惠券所属用户
            if (couponUser.getMemberId() > 0 && !couponUser.getMemberId().equals(memberId)) {
                return new HttpJsonResult<CouponUser>(
                    "优惠券【" + couponUser.getCouponSn() + "】不是属于您的优惠券，不能使用。");
            }
        }

        // 优惠券可使用次数
        if (couponUser.getCanUse() < 1) {
            return new HttpJsonResult<CouponUser>(
                "优惠券【" + couponUser.getCouponSn() + "】已使用过，不能再次使用。");
        }

        // 优惠券用户关联的优惠券信息校验
        // 适用最低金额校验
        if (couponUser.getMinAmount().compareTo(new BigDecimal(orderAmount)) > 0) {
            return new HttpJsonResult<CouponUser>(
                "优惠券【" + couponUser.getCouponSn() + "】最低适用订单金额不得小于" + couponUser.getMinAmount()
                                                  + "元。");
        }
        // 优惠券使用时间校验
        if (couponUser.getUseStartTime().after(new Date())) {
            return new HttpJsonResult<CouponUser>(
                "优惠券【" + couponUser.getCouponSn() + "】还没有到可使用时间。");
        }
        if (couponUser.getUseEndTime().before(new Date())) {
            return new HttpJsonResult<CouponUser>("优惠券【" + couponUser.getCouponSn() + "】已过期。");
        }

        // 使用渠道校验
        if (couponUser.getChannel().intValue() != ConstantsEJS.CHANNEL_1
            && ConstantsEJS.CHANNEL_2 != couponUser.getChannel().intValue()) {
            String channelStr = couponUser.getChannel().intValue() == ConstantsEJS.CHANNEL_2 ? "电脑端"
                : "移动端";
            return new HttpJsonResult<CouponUser>(
                "优惠券【" + couponUser.getCouponSn() + "】只能在" + channelStr + "使用。");
        }

        HttpJsonResult<CouponUser> jsonResult = new HttpJsonResult<CouponUser>();
        jsonResult.setData(couponUser);
        return jsonResult;
    }

    // -------------------------限时抢购-------------------------
    /**
     * 限时抢购时跳转到提交订单页面 计算总金额,运费、货品小计，按店铺拆分订单
     * @param request
     * @param response
     * @param map
     * @return
     * @throws IOException 
     */
    @RequestMapping(value = "order/flashsale-{visitPath}.html", method = { RequestMethod.GET })
    public String flashSale(@PathVariable String visitPath, HttpServletRequest request,
                            ModelMap map, HttpServletResponse response) {

        // visitPath为1-1-1形式，第一个为商品ID，第二个为货品ID，第三个为商家ID
        String[] arrVisitPath = visitPath.split("-");
        int arrVisitPathLength = arrVisitPath.length;
        if (arrVisitPathLength != 3) { //长度不等于3url错误
            return "redirect:/error.html";
        }

        Member member = WebFrontSession.getLoginedUser(request);
        // 收货地址信息
        ServiceResult<List<MemberAddress>> serviceResult = memberAddressService
            .getMemberAddressByMId(member.getId());
        List<MemberAddress> addressList = null;
        MemberAddress defaultAddress = null;
        // 是否有默认地址
        String hasDefaultAdd = "no";
        // 获取默认收货地址，如果没有取第一个
        if (serviceResult.getSuccess()) {
            addressList = serviceResult.getResult();
            if (addressList != null && addressList.size() > 0) {
                defaultAddress = addressList.get(0);
                for (MemberAddress address : addressList) {
                    if (address.getState() == MemberAddress.STATE_1) {
                        defaultAddress = address;
                        hasDefaultAdd = "yes";
                        break;
                    }
                }
            }
        }
        map.put("hasDefaultAdd", hasDefaultAdd);
        map.put("addressList", addressList);
        map.put("defaultAddress", defaultAddress);
        // 构建默认值 ，默认在线支付。收货地址为默认地址，发票默认为不开发票
        OrderCommitVO orderCommitVO = new OrderCommitVO();
        orderCommitVO.setInvoiceType("");
        orderCommitVO.setInvoiceTitle("");
        orderCommitVO.setPaymentName("在线支付");
        orderCommitVO.setPaymentCode(ConstantsEJS.PAYMENT_CODE_ONLINE);
        map.put("orderCommitVO", orderCommitVO);

        //        // 取购物车信息  产品价格 按照商家来区分
        //        // 查询购物车
        //        ServiceResult<CartInfoVO> cartServiceResult = cartService.getCartInfoByMId(member.getId(),
        //            defaultAddress, ConstantsEJS.SOURCE_1_PC, 2);
        //        map.put("cartInfoVO", cartServiceResult.getResult());

        String productIdStr = arrVisitPath[0];
        String productGoodsIdStr = arrVisitPath[1];
        String sellerIdStr = arrVisitPath[2];
        Integer productId = ConvertUtil.toInt(productIdStr, 0);
        Integer productGoodsId = ConvertUtil.toInt(productGoodsIdStr, 0);
        Integer sellerId = ConvertUtil.toInt(sellerIdStr, 0);

        // 取商家信息
        ServiceResult<Seller> sellerRlt = sellerService.getSellerById(sellerId);
        if (!sellerRlt.getSuccess()) {
            map.put("info", sellerRlt.getMessage());
            return "front/commons/error";
        }
        map.put("seller", sellerRlt.getResult());

        // 取商品信息
        ServiceResult<Product> productRlt = productService.getProductById(productId);
        if (!productRlt.getSuccess()) {
            map.put("info", productRlt.getMessage());
            return "front/commons/error";
        }
        map.put("product", productRlt.getResult());

        // 取货品信息
        ServiceResult<ProductGoods> goodRlt = productGoodsService
            .getProductGoodsById(productGoodsId);
        if (!goodRlt.getSuccess()) {
            map.put("info", goodRlt.getMessage());
            return "front/commons/error";
        }
        map.put("productGoods", goodRlt.getResult());

        // 根据商品ID、渠道取得当前时间点的该商品参加的限时抢购活动、阶段、活动商品信息（上架的活动，如果有多个，只取最新的一个）
        ServiceResult<ActFlashSale> flashSaleResult = actFlashSaleService
            .getCurrEffectiveActFlashSale(productId, ConstantsEJS.CHANNEL_2);
        if (!flashSaleResult.getSuccess()) {
            map.put("info", flashSaleResult.getMessage());
            return "front/commons/error";
        }

        // 抢购活动信息，不为空则显示，为空则提示当前时间无抢购活动
        ActFlashSale actFlashSale = flashSaleResult.getResult();
        if (actFlashSale != null) {
            map.put("actFlashSale", actFlashSale);
            map.put("actFlashSaleStage", actFlashSale.getStageList().get(0));
            map.put("actFlashSaleProduct",
                actFlashSale.getStageList().get(0).getProductList().get(0));
        } else {
            return "redirect:/error.html";
        }
        // 计算运费
        ServiceResult<BigDecimal> transFeeRlt = sellerTransportService.calculateTransFee(sellerId,
            1, defaultAddress.getCityId());
        if (!transFeeRlt.getSuccess()) {
            map.put("info", "运费计算失败，请稍后再试。");
            return "front/commons/error";
        }
        BigDecimal transFee = transFeeRlt.getResult();
        map.put("transFee", transFee.compareTo(BigDecimal.ZERO) < 1 ? BigDecimal.ZERO : transFee);

        // 获取发票信息
        ServiceResult<List<Invoice>> invoiceResult = invoiceService.getInvoiceByMId(member.getId());
        map.put("invoiceList", invoiceResult.getResult());

        //取会员余额信息
        ServiceResult<Member> memberResult = memberService.getMemberById(member.getId());
        if (memberResult.getResult() == null) {
            map.put("info", "会员信息获取失败。");
            return "front/commons/error";
        }
        map.put("member", memberResult.getResult());

        ServiceResult<Config> configById = configservice.getConfigById(ConstantsEJS.CONFIG_ID);
        if (configById.getResult() != null) {
            Config config = configById.getResult();
            if (config.getIntegralScale() > 0) {
                map.put("config", config);
            }
        }
        return "front/order/shoporderflashsale";
    }

    /**
     * 根据地址ID计算新的运费信息(为限时抢购结算页使用)
     * @param request
     * @param response
     * @param map
     * @param addressId
     * @return
     */
    @RequestMapping(value = "order/calculateTransFeeForFlash.html", method = { RequestMethod.POST })
    public @ResponseBody HttpJsonResult<BigDecimal> calculateTransFeeForFlash(HttpServletRequest request,
                                                                              HttpServletResponse response,
                                                                              ModelMap map,
                                                                              Integer sellerId,
                                                                              Integer addressId) {
        // 获取地址
        ServiceResult<MemberAddress> memberAddressRlt = memberAddressService
            .getMemberAddressById(addressId);
        if (!memberAddressRlt.getSuccess() || memberAddressRlt.getResult() == null) {
            return new HttpJsonResult<>("收获地址信息获取失败！");
        }
        // 计算运费
        ServiceResult<BigDecimal> transFeeRlt = sellerTransportService.calculateTransFee(sellerId,
            1, memberAddressRlt.getResult().getCityId());
        if (!transFeeRlt.getSuccess()) {
            return new HttpJsonResult<>(transFeeRlt.getMessage());
        }
        BigDecimal transFee = transFeeRlt.getResult();
        transFee = transFee.compareTo(BigDecimal.ZERO) < 1 ? BigDecimal.ZERO : transFee;

        HttpJsonResult<BigDecimal> jsonResult = new HttpJsonResult<BigDecimal>();
        jsonResult.setData(transFee);
        return jsonResult;
    }

    /**
     * 限时抢购提交订单
     * @param request
     * @param response
     * @param map
     * @return
     * @throws IOException 
     */
    @RequestMapping(value = "order/ordercommitforflash.html", method = { RequestMethod.POST })
    public @ResponseBody HttpJsonResultForAjax<OrderSuccessVO> orderSubmitForFlash(HttpServletRequest request,
                                                                                   HttpServletResponse response,
                                                                                   OrderCommitVO orderCommitVO,
                                                                                   ModelMap map) {
        Member member = WebFrontSession.getLoginedUser(request);
        orderCommitVO.setMemberId(member.getId());

        if (orderCommitVO.getInvoiceStatus() == null) {
            // 默认不开发票
            orderCommitVO.setInvoiceStatus(Orders.INVOICE_STATUS_0);
        }
        // 设定IP地址
        orderCommitVO.setIp(WebUtil.getIpAddr(request));
        // 设定来源
        orderCommitVO.setSource(ConstantsEJS.SOURCE_1_PC);
        orderCommitVO.setRemark("");

        // 提交订单
        ServiceResult<OrderSuccessVO> serviceResult = ordersService
            .orderCommitForFlash(orderCommitVO);

        HttpJsonResultForAjax<OrderSuccessVO> jsonResult = new HttpJsonResultForAjax<OrderSuccessVO>();
        if (!serviceResult.getSuccess()) {
            jsonResult = new HttpJsonResultForAjax<OrderSuccessVO>(null,
                CsrfTokenManager.getTokenForSession(CsrfTokenManager.getMemkeyFromRequest(request),
                    request.getSession()));
            jsonResult.setMessage(serviceResult.getMessage());
            return jsonResult;
        }
        //订单提交后返回结果
        OrderSuccessVO orderSuccessVO = serviceResult.getResult();
        if (orderSuccessVO.getIsPaid()) {
            // 如果已经付过款，则调用下单送积分方法
            for (Orders order : orderSuccessVO.getOrdersList()) {
                memberService.memberOrderSendValue(member.getId(), member.getName(), order.getId());
            }
        }
        //支付随机码 避免重复提交
        String order_session = CommUtil.randomString(32);
        // 存入session，支付时取出后与参数传入的对比，判断是否二次提交
        request.getSession(false).setAttribute("order_session", order_session);
        request.getSession(false).setAttribute("order_success_vo", orderSuccessVO);
        orderSuccessVO.setPaySessionstr(order_session);

        jsonResult.setData(orderSuccessVO);

        return jsonResult;
    }
}
