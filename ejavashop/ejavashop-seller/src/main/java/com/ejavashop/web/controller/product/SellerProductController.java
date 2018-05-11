package com.ejavashop.web.controller.product;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.ejavashop.core.ConstantsEJS;
import com.ejavashop.core.ConvertUtil;
import com.ejavashop.core.HttpJsonResult;
import com.ejavashop.core.HttpJsonResultForAjax;
import com.ejavashop.core.PagerInfo;
import com.ejavashop.core.ServiceResult;
import com.ejavashop.core.StringUtil;
import com.ejavashop.core.WebUtil;
import com.ejavashop.core.exception.BusinessException;
import com.ejavashop.core.freemarkerutil.DomainUrlUtil;
import com.ejavashop.entity.product.Product;
import com.ejavashop.entity.product.ProductAttr;
import com.ejavashop.entity.product.ProductBrand;
import com.ejavashop.entity.product.ProductCate;
import com.ejavashop.entity.product.ProductGoods;
import com.ejavashop.entity.product.ProductNorm;
import com.ejavashop.entity.product.ProductNormAttr;
import com.ejavashop.entity.product.ProductPicture;
import com.ejavashop.entity.product.ProductType;
import com.ejavashop.entity.product.ProductTypeAttr;
import com.ejavashop.entity.seller.Seller;
import com.ejavashop.entity.seller.SellerCate;
import com.ejavashop.entity.seller.SellerUser;
import com.ejavashop.service.product.IProductAttrService;
import com.ejavashop.service.product.IProductBrandService;
import com.ejavashop.service.product.IProductCateService;
import com.ejavashop.service.product.IProductGoodsService;
import com.ejavashop.service.product.IProductNormService;
import com.ejavashop.service.product.IProductPictureService;
import com.ejavashop.service.product.IProductService;
import com.ejavashop.service.product.IProductTypeAttrService;
import com.ejavashop.service.product.IProductTypeService;
import com.ejavashop.service.product.ISellerApplyBrandService;
import com.ejavashop.service.product.ISellerCateService;
import com.ejavashop.service.product.ISellerManageCateService;
import com.ejavashop.service.seller.ISellerTransportService;
import com.ejavashop.web.basic.CsrfTokenManager;
import com.ejavashop.web.controller.BaseController;
import com.ejavashop.web.util.UploadUtil;
import com.ejavashop.web.util.WebSellerSession;

/**
 * 商品管理controller
 *                       
 * @Filename: SellerProductController.java
 * @Version: 1.0
 * @Author: 陈万海
 * @Email: chenwanhai@sina.com
 *
 */
@Controller
@RequestMapping(value = "seller/product")
public class SellerProductController extends BaseController {
    @Resource
    private ISellerManageCateService sellerManageCateService;
    @Resource
    private ISellerApplyBrandService sellerApplyBrandService;
    @Resource
    private IProductCateService      productCateService;
    @Resource
    private IProductTypeAttrService  sellerProductTypeAttrService;
    @Resource
    private IProductTypeService      sellerProductTypeService;
    @Resource
    private IProductNormService      sellerProductNormService;
    @Resource
    private IProductBrandService     productBrandService;
    @Resource
    private ISellerCateService       sellerCateService;
    @Resource
    private IProductService          productService;
    @Resource
    private ISellerTransportService  sellerTransportService;
    @Resource
    private IProductPictureService   productPicService;
    @Resource
    private IProductAttrService      productAttrService;
    @Resource
    private IProductGoodsService     productGoodsService;

    private String baseUrl = "seller/product/pdt/";
    private Logger log     = Logger.getLogger(SellerProductController.class);

    /**
     * 库存价格设置
     * @param request
     * @param dataMap
     * @return
     */
    @RequestMapping(value = "goodsSet", method = { RequestMethod.GET })
    public String goodsSet(HttpServletRequest request, Map<String, Object> dataMap, Integer id,
                           String from) {
        //查询商品信息 
        ServiceResult<Product> productServiceResult = productService.getProductById(id);
        if (!productServiceResult.getSuccess() || null == productServiceResult.getResult()) {
            dataMap.put("error", "商品不存在");
        }
        Product product = productServiceResult.getResult();

        ServiceResult<List<ProductGoods>> productGoodsServiceResult = productGoodsService
            .getGoodSByProductId(id);
        List<ProductGoods> goodslist = productGoodsServiceResult.getResult();

        dataMap.put("product", product);
        dataMap.put("goodslist", goodslist);
        dataMap.put("from", from);

        return baseUrl + "goodsset";
    }

    /**
     * @param request
     * @param dataMap
     * @param productinfo
     * @param from
     * @param productgoodsInfo 货品信息<br>
     * <i>格式：货品之间以"!@|@!"分隔，
     * 货品信息格式为：id,productStock,mallPcPrice,mallMobilePrice</i>
     * @return
     */
    @RequestMapping(value = "goodssetSumit", method = { RequestMethod.POST })
    public String goodssetSumit(HttpServletRequest request, Map<String, Object> dataMap,
                                Product productinfo,
                                @RequestParam(value = "goodsinfo") String productgoodsInfo,
                                String from) {

        Product product = productService.getProductById(productinfo.getId()).getResult();
        product.setName2(productinfo.getName2());
        product.setMallPcPrice(productinfo.getMallPcPrice());
        product.setMalMobilePrice(productinfo.getMalMobilePrice());
        product.setProductStock(productinfo.getProductStock());
        //更新此商品价格库存信息
        productService.updateProduct(product);
        if (!isNull(productgoodsInfo)) {
            //组装货品信息
            String[] goodsinfo = productgoodsInfo.split("!@\\|@!");
            List<ProductGoods> goodslist = new ArrayList<ProductGoods>(goodsinfo.length);
            for (String data : goodsinfo) {
                String[] str = data.split(",");
                ProductGoods goods = new ProductGoods();
                goods.setId(Integer.valueOf(str[0]));
                //                goods.setSku(str[1]);
                goods.setProductStock(Integer.valueOf(str[1]));
                goods.setMallPcPrice(new BigDecimal(str[2]));
                goods.setMallMobilePrice(new BigDecimal(str[3]));

                goodslist.add(goods);
            }
            //更新此商品对应的所有货品信息
            productGoodsService.updateProductGoods(goodslist);
        }

        //根据from重定向至相应页面
        return "redirect:/seller/product/" + from;
    }

    //待售商品(刚创建、提交审核、审核通过、下架)
    @RequestMapping(value = "waitSale", method = { RequestMethod.GET })
    public String waitSale(HttpServletRequest request, Map<String, Object> dataMap) {
        dataMap.put("q_useYn", "1");
        dataMap.put("q_state", "1,2,3,4,7");//1、刚创建；2、提交审核；3、审核通过；4、申请驳回；7、下架
        dataMap.put("pageSize", ConstantsEJS.DEFAULT_PAGE_SIZE);
        return baseUrl + "listwaitsale";
    }

    @RequestMapping(value = "saleAll", method = { RequestMethod.GET })
    public String saleAll(HttpServletRequest request, Map<String, Object> dataMap) {
        dataMap.put("q_useYn", "1");
        dataMap.put("pageSize", ConstantsEJS.DEFAULT_PAGE_SIZE);
        dataMap.put("q_state", "1,2,3,4,5,6,7");//1、刚创建；2、提交审核；3、审核通过；4、申请驳回；7、下架
        return baseUrl + "listall";
    }

    //在售商品(上架商品) 可以下架
    @RequestMapping(value = "onSale", method = { RequestMethod.GET })
    public String onSale(HttpServletRequest request, Map<String, Object> dataMap) {
        dataMap.put("q_useYn", "1");
        dataMap.put("q_state", "6");//6、上架；
        dataMap.put("pageSize", ConstantsEJS.DEFAULT_PAGE_SIZE);
        return baseUrl + "listonsale";
    }

    //已经删除商品(删除) 只读
    @RequestMapping(value = "delSale", method = { RequestMethod.GET })
    public String delSale(HttpServletRequest request, Map<String, Object> dataMap) {
        dataMap.put("q_useYn", "1");
        dataMap.put("q_state", "5");//5、商品删除；
        dataMap.put("pageSize", ConstantsEJS.DEFAULT_PAGE_SIZE);
        return baseUrl + "listdelsale";
    }

    /**
     * 商品列表
     * @param request
     * @param dataMap
     * @return
     */
    @RequestMapping(value = "list", method = { RequestMethod.GET })
    public @ResponseBody HttpJsonResult<List<Product>> list(HttpServletRequest request,
                                                            Map<String, Object> dataMap) {
        Map<String, String> queryMap = WebUtil.handlerQueryMap(request);
        String state = request.getParameter("q_state1");
        if (!StringUtil.isEmpty(state)) {
            queryMap.put("q_state", state);
        }
        PagerInfo pager = WebUtil.handlerPagerInfo(request, dataMap);
        queryMap.put("q_sellerId", WebSellerSession.getSellerUser(request).getSellerId() + "");
        ServiceResult<List<Product>> serviceResult = productService.pageProduct(queryMap, pager);
        if (!serviceResult.getSuccess()) {
            if (ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR.equals(serviceResult.getCode())) {
                throw new RuntimeException(serviceResult.getMessage());
            } else {
                throw new BusinessException(serviceResult.getMessage());
            }
        }

        HttpJsonResult<List<Product>> jsonResult = new HttpJsonResult<List<Product>>();
        jsonResult.setRows((List<Product>) serviceResult.getResult());
        jsonResult.setTotal(pager.getRowsCount());
        return jsonResult;
    }

    /**
     * 根据商品ID查询货品
     * @param request
     * @param dataMap
     * @return
     */
    @RequestMapping(value = "list_goods", method = { RequestMethod.GET })
    public @ResponseBody HttpJsonResult<List<ProductGoods>> listGoods(Integer productId,
                                                                      HttpServletRequest request,
                                                                      Map<String, Object> dataMap) {
        ServiceResult<List<ProductGoods>> serviceResult = productGoodsService
            .getGoodSByProductId(productId);

        if (!serviceResult.getSuccess()) {
            if (ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR.equals(serviceResult.getCode())) {
                throw new RuntimeException(serviceResult.getMessage());
            } else {
                throw new BusinessException(serviceResult.getMessage());
            }
        }

        HttpJsonResult<List<ProductGoods>> jsonResult = new HttpJsonResult<List<ProductGoods>>();
        jsonResult.setRows(serviceResult.getResult());
        return jsonResult;
    }

    /**
     * 提交审核
     * @param request
     * @return
     */
    @RequestMapping(value = "commit", method = { RequestMethod.POST })
    public void commit(HttpServletRequest request, HttpServletResponse response, String ids) {
        response.setContentType("text/plain;charset=utf-8");
        String msg = "提交成功,请耐心等待,审核通过后商品才能上架";
        PrintWriter pw = null;
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("state", Product.STATE_2);
        try {
            productService.updateByIds(map, StringUtil.string2IntegerList(ids));
            pw = response.getWriter();
        } catch (Exception e) {
            e.printStackTrace();
            msg = "提交失败,请稍后重试";
        }
        pw.print(msg);
    }

    /**
     * 发布商品-选择分类
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "chooseCate", method = { RequestMethod.GET })
    public String chooseCate(HttpServletRequest request, Map<String, Object> dataMap) {
        SellerUser sellerUser = WebSellerSession.getSellerUser(request);
        if (null == sellerUser) {
            return DomainUrlUtil.getEJS_URL_RESOURCES() + "/seller/login.html";
        }
        ServiceResult<List<ProductCate>> serviceResult = productCateService
            .getCateBySellerId(sellerUser.getSellerId());
        if (serviceResult.getSuccess() == true && serviceResult.getResult() != null) {
            dataMap.put("cate", serviceResult.getResult());
        }
        return baseUrl + "choosecate";
    }

    /**
     * 根据商品分类id组装商品类型属性、商品规格属性
     *
     * @param productCateId
     * @param dataMap
     */
    private void assembleProp(Integer productCateId, Map<String, Object> dataMap,
                              Integer productId) {
        ServiceResult<ProductCate> cate = productCateService.getProductCateById(productCateId);
        if (cate.getSuccess() == true && cate.getResult() != null) {

            Integer typeId = cate.getResult().getProductTypeId();
            ServiceResult<List<ProductTypeAttr>> typeAttr = sellerProductTypeAttrService
                .getProductTypeAttrByTypeId(typeId);

            /**组装商品类型属性*/
            if (typeAttr.getSuccess() == true && typeAttr.getResult() != null
                && typeAttr.getResult().size() > 0) {
                List<ProductTypeAttr> attrList = typeAttr.getResult();
                List<Map<String, Object>> queryTypeAttr = new ArrayList<Map<String, Object>>();
                List<Map<String, Object>> custTypeAttr = new ArrayList<Map<String, Object>>();

                List<ProductAttr> productAttrList = null;
                if (null != productId) {
                    //编辑商品时候使用
                    ServiceResult<List<ProductAttr>> attrServiceResult = productAttrService
                        .getProductAttrByProductId(productId);
                    if (attrServiceResult.getSuccess() && attrServiceResult.getResult() != null) {
                        productAttrList = attrServiceResult.getResult();
                    }
                }
                for (ProductTypeAttr attr1 : attrList) {
                    Map<String, Object> attrMap = new HashMap<String, Object>();
                    if (attr1.getType() == 1) {
                        processAttr(attr1, attrMap, productAttrList);
                        queryTypeAttr.add(attrMap);//查询属性
                    } else {
                        processAttr(attr1, attrMap, productAttrList);
                        custTypeAttr.add(attrMap);//自定义属性
                    }
                }
                dataMap.put("queryTypeAttr", queryTypeAttr);
                dataMap.put("custTypeAttr", custTypeAttr);
            }

            /**组装商品属性信息**/
            ServiceResult<ProductType> serviceResult = sellerProductTypeService
                .getProductTypeById(typeId);
            if (serviceResult.getSuccess() && serviceResult.getResult() != null) {
                ProductType productType = serviceResult.getResult();
                String productNormIds = productType.getProductNormIds();
                if (!StringUtil.isEmpty(productNormIds)) {
                    String[] normIds = productNormIds.split(",");
                    List<Map<String, Object>> normList = new ArrayList<Map<String, Object>>(
                        normIds.length);
                    for (String normId : normIds) {
                        Map<String, Object> attrMap = null;
                        Integer id = Integer.valueOf(normId);
                        ServiceResult<ProductNorm> normResult = sellerProductNormService
                            .getNormById(id);

                        if (normResult.getSuccess() && normResult.getResult() != null) {
                            attrMap = new HashMap<String, Object>(2);
                            ProductNorm result = normResult.getResult();
                            List<ProductNormAttr> list = result.getAttrList();
                            List<Map<String, Object>> attrList = new ArrayList<Map<String, Object>>(
                                result.getAttrList().size());

                            /**构造货品属性**/
                            List<String> normAttrIdList = new ArrayList<String>();
                            if (null != productId) {
                                Map<String, String> queryMap = new HashMap<String, String>(1);
                                queryMap.put("q_productId", String.valueOf(productId));
                                ServiceResult<List<ProductGoods>> listServiceResult = productGoodsService
                                    .pageProductGoods(queryMap, null);
                                if (listServiceResult.getSuccess()
                                    && listServiceResult.getResult() != null
                                    && listServiceResult.getResult().size() > 0) {
                                    List<ProductGoods> goodsList = listServiceResult.getResult();

                                    processGoods(goodsList, dataMap);//货品信息

                                    for (ProductGoods goods : goodsList) {
                                        String normAttrId = goods.getNormAttrId();
                                        if (!StringUtil.isEmpty(normAttrId)) {
                                            String[] split = normAttrId.split(",");
                                            for (String str : split) {
                                                if (!StringUtil.isEmpty(str)) {
                                                    normAttrIdList.add(str);
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                            if (normAttrIdList.size() > 0) {
                                Set<String> set = new HashSet<String>();
                                set.addAll(normAttrIdList);
                                normAttrIdList.clear();
                                normAttrIdList.addAll(set);
                            }

                            attrMap.put("name", result.getName());
                            attrMap.put("attrList", attrList);
                            if (null != list && list.size() > 0) {
                                Map<String, Object> map = null;
                                for (ProductNormAttr attr : list) {
                                    map = new HashMap<String, Object>(3);
                                    map.put("id", attr.getId());
                                    map.put("name", attr.getName());
                                    if (normAttrIdList.size() > 0) {
                                        for (String str : normAttrIdList) {
                                            if (String.valueOf(attr.getId()).equals(str)) {
                                                map.put("checked", "true");
                                            }
                                        }
                                    }
                                    attrList.add(map);
                                }
                            }
                            normList.add(attrMap);
                        }
                    }
                    dataMap.put("normList", normList);
                }
            }
        }
    }

    /**
     * 构造货品信息
     *
     * @param goodsList
     * @param dataMap
     */
    private void processGoods(List<ProductGoods> goodsList, Map<String, Object> dataMap) {
        if (null != goodsList && goodsList.size() > 0) {
            List<Map<String, Object>> goods = new ArrayList<Map<String, Object>>(goodsList.size());
            Map<String, Object> goodMap = null;
            List<String> columnList = new ArrayList<String>();

            for (ProductGoods good : goodsList) {
                goodMap = new HashMap<String, Object>();
                String normAttrId = good.getNormAttrId();
                if (!StringUtil.isEmpty(normAttrId)) {
                    String[] split = normAttrId.split(",");

                    if (null != split && split.length == 1) {//一列规格
                        goodMap.put("normId1", normAttrId);
                        String normName = good.getNormName();
                        goodMap.put("normName1", normName.split(",")[1]);
                        columnList.add(normName.split(",")[0]);
                    } else if (null != split && split.length == 2) {//两列规格
                        goodMap.put("normId1", split[0]);
                        goodMap.put("normId2", split[1]);
                        String normName = good.getNormName();
                        String column1 = normName.split(";")[0];
                        column1 = column1.substring(0, column1.indexOf(","));
                        String column2 = normName.split(";")[1];
                        column2 = column2.substring(0, column2.indexOf(","));
                        String normName1 = normName.split(";")[0];
                        normName1 = normName1.substring(normName1.indexOf(",") + 1,
                            normName1.length());
                        String normName2 = normName.split(";")[1];
                        normName2 = normName2.substring(normName2.indexOf(",") + 1,
                            normName2.length());

                        goodMap.put("normName1", normName1);
                        goodMap.put("normName2", normName2);
                        columnList.add(column1);
                        columnList.add(column2);
                    }

                    goodMap.put("sku", good.getSku());
                    goodMap.put("stock", good.getProductStock());
                    goodMap.put("mobilePrice", good.getMallMobilePrice());
                    goodMap.put("pcPrice", good.getMallPcPrice());

                    goods.add(goodMap);

                    Set<String> set = new HashSet<String>();
                    set.addAll(columnList);
                    columnList.clear();
                    columnList.addAll(set);
                    dataMap.put("goods", goods);
                    dataMap.put("column", columnList);
                }
            }
        }
    }

    private void processAttr(ProductTypeAttr attr1, Map<String, Object> attrMap,
                             List<ProductAttr> productAttrList) {
        List<String> attrValList = new ArrayList<String>();
        String attrVal = attr1.getValue();
        if (!StringUtil.isEmpty(attrVal)) {
            String[] split = attrVal.split(",");
            for (String str : split) {
                if (StringUtil.isEmpty(str))
                    continue;
                attrValList.add(str);
            }
        }

        attrMap.put("attrId", attr1.getId());
        attrMap.put("attrName", attr1.getName());
        attrMap.put("attrValList", attrValList);

        //编辑商品时候使用
        if (null != productAttrList && productAttrList.size() > 0) {
            for (ProductAttr attr : productAttrList) {
                if (attr.getProductTypeAttrId().equals(attr1.getId())) {
                    attrMap.put("dbAttr", attr.getValue());
                }
            }
        }
    }

    /**
     * 添加商品详细信息
     *
     * @param request
     * @param dataMap
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "add", method = { RequestMethod.GET })
    public String add(HttpServletRequest request, Map<String, Object> dataMap) throws Exception {
        String rtnPath = baseUrl + "productadd";
        /**1.判断是否登录*/
        SellerUser user = WebSellerSession.getSellerUser(request);
        if (null == user) {
            return DomainUrlUtil.getEJS_URL_RESOURCES() + "/seller/login.html";
        }
        /**2.判断是否选择商品分类*/
        String cateId = request.getParameter("cateId");
        if (StringUtil.isEmpty(cateId)) {
            dataMap.put("cate",
                DomainUrlUtil.getEJS_URL_RESOURCES() + "/seller/product/chooseCate");
            return baseUrl + "choosecate";
        }
        /**3.构造商品分类路径*/
        ServiceResult<String> catePathResult = productCateService
            .getCatePathStrById(Integer.valueOf(cateId));
        if (catePathResult.getSuccess() && catePathResult.getResult() != null) {
            dataMap.put("catePath", catePathResult.getResult());
            dataMap.put("cateId", cateId);
        }

        /**4.获取商家可以经营的品牌**/
        ServiceResult<ProductCate> cateResult = productCateService
            .getProductCateById(Integer.valueOf(cateId));
        if (!cateResult.getSuccess() || cateResult.getResult() == null) {
            dataMap.put("message", "该分类下无可以经营的品牌，请重新选择分类，或者联系商城管理员");
            return rtnPath;
        }
        ServiceResult<List<ProductBrand>> brandResult = productBrandService
            .getBrandByTypeId(cateResult.getResult().getProductTypeId());
        if (brandResult.getSuccess() && brandResult.getResult() != null) {
            dataMap.put("brand", brandResult.getResult());
        }
        /**5.组装商品类型信息（单品页商品介绍下的内容，商品属性）、组装商品规格信息（单品页商品图片右侧选择信息）*/
        assembleProp(Integer.valueOf(cateId), dataMap, null);

        /**6.本店分类(1级)**/
        ServiceResult<List<SellerCate>> serviceResult = sellerCateService.getByPid(0,
            user.getSellerId());
        if (serviceResult.getSuccess() && null != serviceResult.getResult()) {
            dataMap.put("sellerCate", serviceResult.getResult());
        }

        /**7.判断自营还是商家 1自营**/
        if (user.getSellerId() == 1) {
            dataMap.put("seller", 1);
        }
        dataMap.put("transport", sellerTransportService.getTransportBySellerId(user.getSellerId()));

        return rtnPath;
    }

    /**
     * 保存商品
     *
     * @param product
     * @param request
     * @param dataMap
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "create", method = { RequestMethod.POST })
    @ResponseBody
    public HttpJsonResult<Object> create(Product product, HttpServletRequest request,
                                         Map<String, Object> dataMap) throws IOException {
        HttpJsonResult<Object> jsonResult = new HttpJsonResultForAjax<Object>(true,
            CsrfTokenManager.createTokenForSession(request.getSession()));
        SellerUser user = WebSellerSession.getSellerUser(request);
        if (null == user) {
            jsonResult.setMessage("登录超时，请重新登录");
            jsonResult.setBackUrl(DomainUrlUtil.getEJS_URL_RESOURCES() + "/seller/login.html");
            return jsonResult;
        }

        ServiceResult<Boolean> serviceResult = createOrUpdateProduct(product, request,
            user.getSeller(), "C", user);
        if (serviceResult.getSuccess() && serviceResult.getResult() == true) {
        } else {
            jsonResult.setMessage(serviceResult.getMessage());
        }
        return jsonResult;
    }

    /**
     * 编辑商品
     */
    @RequestMapping(value = "edit", method = { RequestMethod.GET })
    public String edit(HttpServletRequest request,
                       @RequestParam(value = "id", required = true) Integer id,
                       Map<String, Object> dataMap) {
        String rtnPath = baseUrl + "productedit";
        /**1.判断是否登录*/
        SellerUser user = WebSellerSession.getSellerUser(request);
        //if (null == user) {
        //    return DomainUrlUtil.getJM_URL_RESOURCES() + "/seller/login.html";
        //}
        /**查询商品信息**/
        ServiceResult<Product> productServiceResult = productService.getProductById(id);
        if (!productServiceResult.getSuccess() || null == productServiceResult.getResult()) {
            dataMap.put("message", "编辑商品失败，商品不存在");
            return rtnPath;//
        }
        Product product = productServiceResult.getResult();
        dataMap.put("product", product);
        /**分类名称**/
        ServiceResult<String> catePathResult = productCateService
            .getCatePathStrById(product.getProductCateId());
        if (catePathResult.getSuccess() && catePathResult.getResult() != null) {
            dataMap.put("catePath", catePathResult.getResult());
        }
        /**品牌名称**/
        ServiceResult<ProductBrand> brandResult = productBrandService
            .getById(product.getProductBrandId());
        if (brandResult.getSuccess() && brandResult.getResult() != null) {
            dataMap.put("brand",
                brandResult.getResult().getNameFirst() + " " + brandResult.getResult().getName());
        }
        /**商品类型*/
        assembleProp(Integer.valueOf(product.getProductCateId()), dataMap, product.getId());

        /**商品图片**/
        ServiceResult<List<ProductPicture>> pictureServiceResult = productPicService
            .getProductPictureByProductId(product.getId());
        if (pictureServiceResult.getSuccess() && pictureServiceResult.getResult() != null) {
            for (ProductPicture pic : pictureServiceResult.getResult()) {
                String path = pic.getImagePath();
                path = DomainUrlUtil.getEJS_IMAGE_RESOURCES() + path;
                pic.setImagePath(path);
            }
            dataMap.put("pic", pictureServiceResult.getResult());
        }

        Integer sellerCateId = product.getSellerCateId();
        boolean isFirst = false;
        /**本店分类**/
        ServiceResult<List<SellerCate>> serviceResult = sellerCateService.getByPid(0,
            user.getSellerId());
        if (serviceResult.getSuccess() && null != serviceResult.getResult()) {
            dataMap.put("sellerCate", serviceResult.getResult());//一级分类
            //过滤一级目录
            for (SellerCate sellerCate : serviceResult.getResult()) {
                if (sellerCate.getId() == sellerCateId) {
                    dataMap.put("cateId", sellerCate.getId());//一级分类id
                    isFirst = true;
                }
            }
        }

        if (!isFirst) {
            ServiceResult<SellerCate> sellerCateResult = sellerCateService
                .getSellerCateById(sellerCateId);
            if (sellerCateResult.getSuccess() && sellerCateResult.getResult() != null) {
                SellerCate sellerCate = sellerCateResult.getResult();//当前分类信息
                ServiceResult<List<SellerCate>> secondCateResult = sellerCateService
                    .getByPid(sellerCate.getPid(), user.getSellerId());//二级分类
                if (secondCateResult.getSuccess() && secondCateResult.getResult() != null) {
                    dataMap.put("secondSellerCate", secondCateResult.getResult());
                    dataMap.put("cateId", sellerCate.getPid());//一级分类id
                    dataMap.put("secondCateId", sellerCateId);//二级分类id
                }
            }
        }

        /**判断自营还是商家 1自营**/
        if (user.getSellerId() == 1) {
            dataMap.put("seller", 1);
        }
        dataMap.put("edit", "edit");

        return baseUrl + "productedit";
    }

    /**
     * 更新商品
     *
     * @param product
     * @param request
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "update", method = { RequestMethod.POST })
    @ResponseBody
    public HttpJsonResult<Object> update(Product product,
                                         HttpServletRequest request) throws IOException {
        HttpJsonResult<Object> jsonResult = new HttpJsonResultForAjax<Object>(true,
            CsrfTokenManager.createTokenForSession(request.getSession()));
        SellerUser user = WebSellerSession.getSellerUser(request);
        if (null == user) {
            jsonResult.setMessage("登录超时，请重新登录");
            jsonResult.setBackUrl(DomainUrlUtil.getEJS_URL_RESOURCES() + "/seller/login.html");
            return jsonResult;
        }

        ServiceResult<Boolean> serviceResult = createOrUpdateProduct(product, request,
            user.getSeller(), "U", user);
        if (!serviceResult.getSuccess()) {
            jsonResult.setMessage(serviceResult.getMessage());
        }
        return jsonResult;
    }

    /**
     * 删除商品
     *
     * @param request
     * @param id
     * @return
     */
    @RequestMapping(value = "del", method = RequestMethod.POST)
    public @ResponseBody HttpJsonResult<Object> del(HttpServletRequest request,
                                                    @RequestParam("id") Integer id) {
        HttpJsonResult<Object> jsonResult = new HttpJsonResult<Object>();
        ServiceResult<Boolean> serviceResult = productService.delProduct(id);
        if (!serviceResult.getSuccess()) {
            jsonResult.setMessage(serviceResult.getMessage());
        }
        return jsonResult;
    }

    /**
     * 上下架操作
     *
     * @param request
     * @param response
     * @param ids
     */
    @RequestMapping(value = "handler", method = { RequestMethod.POST })
    public void handler(HttpServletRequest request, HttpServletResponse response, String ids,
                        Integer type) {
        response.setContentType("text/plain;charset=utf-8");
        String msg = "";
        PrintWriter pw = null;
        try {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("state", Product.STATE_2);
            if (type == Product.STATE_6) {
                map.put("state", Product.STATE_6);
                msg = "上架成功";
            } else if (type == Product.STATE_7) {
                map.put("state", Product.STATE_7);
                msg = "下架成功";
            } else
                throw new BusinessException("未知操作");
            productService.updateByIds(map, StringUtil.string2IntegerList(ids));
            pw = response.getWriter();
        } catch (Exception e) {
            e.printStackTrace();
            msg = e.getMessage();
        }
        pw.print(msg);
    }

    /**
     * ajax获取二级、三级分类
     *
     * @param request
     * @param id
     * @return
     */
    @RequestMapping(value = "getCateById", method = { RequestMethod.GET })
    @ResponseBody
    public Object getCateById(HttpServletRequest request, @RequestParam("id") Integer id) {
        HttpJsonResult<List<ProductCate>> jsonResult = new HttpJsonResult<List<ProductCate>>();
        SellerUser user = WebSellerSession.getSellerUser(request);
        if (null == user) {
            //DomainUrlUtil.getEJS_URL_RESOURCES() + "/seller/login.html"

        }
        ServiceResult<List<ProductCate>> serviceResult = productCateService
            .getCateBySellerId(user.getSellerId(), id);
        if (serviceResult.getSuccess() == true && serviceResult.getResult() != null) {
            jsonResult.setRows(serviceResult.getResult());
        }

        return jsonResult;
    }

    /**
     * ajax商品图片上传
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "uploadFiles", method = { RequestMethod.POST })
    public @ResponseBody Object uploadImage(MultipartHttpServletRequest request,
                                            HttpServletResponse response, String fileIndex) {
        log.info("UploadImageController uploadImage start");
        HttpJsonResult<Map<String, Object>> jsonResult = new HttpJsonResult<Map<String, Object>>();
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            MultiValueMap<String, MultipartFile> map = request.getMultiFileMap();
            if (map != null) {
                Iterator<String> iter = map.keySet().iterator();
                while (iter.hasNext()) {
                    String str = (String) iter.next();
                    List<MultipartFile> fileList = map.get(str);
                    for (MultipartFile mpf : fileList) {
                        String originalFilename = mpf.getOriginalFilename();
                        File tmpFile = new File(buildImgPath(request) + "/" + UUID.randomUUID()
                                                + originalFilename.substring(
                                                    originalFilename.lastIndexOf("."),
                                                    originalFilename.length()));
                        if (!mpf.isEmpty()) {
                            byte[] bytes = mpf.getBytes();
                            BufferedOutputStream stream = new BufferedOutputStream(
                                new FileOutputStream(tmpFile));
                            stream.write(bytes);
                            stream.close();
                        }

                        String url = UploadUtil.getInstance().productUploaderImage(tmpFile,
                            request);

                        tmpFile.deleteOnExit();

                        //规范路径,以避免浏览器兼容问题
                        url = url.replaceAll("\\\\", "/");
                        result.put("url", DomainUrlUtil.getEJS_IMAGE_RESOURCES() + url);
                        result.put("fileIndex", fileIndex);
                        jsonResult.setData(result);

                        log.debug("url==================" + url);
                        log.debug("fileIndex==================" + fileIndex);

                        return jsonResult;
                    }
                }
            }
        } catch (Exception e) {
            log.error("[shoppingmall-memer-web][UploadImageController][uploadImage] exception:", e);
            jsonResult.setMessage(e.getMessage());
            return jsonResult;
        }
        return null;
    }

    /**
     * 保存或者更新商品
     *
     * @param product
     * @param request
     * @param seller
     * @param type    C:保存 U:更新
     * @return
     */
    private ServiceResult<Boolean> createOrUpdateProduct(Product product,
                                                         HttpServletRequest request, Seller seller,
                                                         String type, SellerUser sellerUser) {
        ServiceResult<Boolean> result = new ServiceResult<Boolean>();
        try {
            if (seller.getId() == 1) {
                product.setIsSelf(Product.IsSelfEnum.SELF.getValue());//自营
                product.setVirtualSales(Integer.valueOf(request.getParameter("virtualSales")));
            } else {
                product.setIsSelf(Product.IsSelfEnum.SELLER.getValue());//商家
            }
            Integer productBrandId = ConvertUtil.toInt(request.getParameter("productBrandId"), 0);
            if (0 == productBrandId) {
                //请选择品牌
                //return;
            }
            Integer sellerCateId = ConvertUtil.toInt(request.getParameter("sellerCateId"), 0);
            if (0 == sellerCateId) {
                //请选择商家分类
                //return;
            }
            String isNormStr = request.getParameter("isNorm");
            if (isNormStr == null) {
                // 编辑商品时表单为disabled，取不到值
                isNormStr = request.getParameter("isNormHidden");
            }
            Integer isNorm = ConvertUtil.toInt(isNormStr, 1);
            if (2 == isNorm) {
                processGoods(request.getParameter("productGoods"), product);
            } else {
                product.setSku(request.getParameter("sku"));
            }

            product.setProductBrandId(productBrandId);
            product.setKeyword(request.getParameter("keyword1"));
            Integer auditStatus = seller.getAuditStatus();
            if (auditStatus.intValue() == Seller.AUDIT_STATE_2_DONE) {
                auditStatus = Product.SELLER_STATE_1;
            } else {
                auditStatus = Product.SELLER_STATE_2;
            }
            product.setSellerState(auditStatus);// 店铺状态
            product.setCommentsNumber(ConvertUtil.toInt(request.getParameter("commentsNumber"), 0));
            product.setSellerId(seller.getId());
            product.setSellerCateId(sellerCateId);
            product.setVirtualSales(ConvertUtil.toInt(request.getParameter("virtualSales"), 0));
            product.setActualSales(ConvertUtil.toInt(request.getParameter("actualSales"), 0));
            product.setCreateId(sellerUser.getId());
            product.setKeyword(request.getParameter("keyword1"));
            product.setProductCateState(1);//分类正常
            product.setIsTop(1);//不推荐
            if (!StringUtil.isEmpty(product.getDescription())) {
                String description = product.getDescription();
                description = description.replaceAll(System.getProperty("line.separator"), "");
                product.setDescription(description);
            }
            // 是否是虚拟商品
            product.setIsInventedProduct(
                ConvertUtil.toInt(request.getParameter("isInventedProduct"), 1));

            List<ProductPicture> picList = new ArrayList<ProductPicture>();
            List<ProductAttr> attrList = new ArrayList<ProductAttr>();
            //product picture
            String pics = request.getParameter("imageSrc");
            if (!StringUtil.isEmpty(pics)) {
                String[] split = pics.split(";");
                for (int i = 0; i < split.length; i++) {
                    ProductPicture picture = new ProductPicture();
                    String img = split[i];
                    //String tmpStr = DomainUrlUtil.getEJS_IMAGE_RESOURCES().substring(
                    //    DomainUrlUtil.getEJS_IMAGE_RESOURCES().lastIndexOf("/"),
                    //    DomainUrlUtil.getEJS_IMAGE_RESOURCES().length());
                    //img = img.substring(img.indexOf(tmpStr) + 9, img.length());
                    img = img.replace(DomainUrlUtil.getEJS_IMAGE_RESOURCES(), "");
                    picture.setImagePath(img);
                    picture.setSort(i);
                    picture.setCreateId(sellerUser.getId());
                    picture.setState(1);
                    picture.setSellerId(seller.getId());
                    if (i == 0) {
                        picture.setProductLead(1);
                        product.setMasterImg(img);
                    } else {
                        picture.setProductLead(2);
                    }
                    picList.add(picture);
                }
            }
            //商品查询属性
            String queryType = request.getParameter("queryType");
            if (!StringUtil.isEmpty(queryType)) {
                String[] split = queryType.split(";");//productTypeAttrId,name,value
                for (String str : split) {
                    String[] split1 = str.split(",");
                    if (split1.length != 3)
                        continue;
                    ProductAttr productAttr = new ProductAttr();
                    productAttr.setProductTypeAttrId(Integer.valueOf(split1[0]));
                    productAttr.setName(split1[1]);
                    if ("-1".equals(split1[2])) {
                        productAttr.setValue("");
                    } else {
                        productAttr.setValue(split1[2]);
                    }
                    productAttr.setState(1);//检索属性
                    attrList.add(productAttr);
                }
            }
            //商品自定义属性
            String custAttr = request.getParameter("custAttr");
            if (!StringUtil.isEmpty(custAttr)) {
                String[] split = custAttr.split(";");//productTypeAttrId,name,value
                for (String str : split) {
                    String[] split1 = str.split(",");
                    ProductAttr productAttr = new ProductAttr();
                    productAttr.setProductTypeAttrId(Integer.valueOf(split1[0]));
                    productAttr.setName(split1[1]);
                    if (split1.length == 2) {
                        productAttr.setValue("");
                    } else {
                        productAttr.setValue(split1[2]);
                    }
                    productAttr.setState(3);//商品自定义属性
                    attrList.add(productAttr);
                }
            }

            if ("C".equals(type)) {
                product.setState(Product.StateEnum.CREATE.getValue());
                result = productService.saveProduct(product, picList, attrList);
            }
            if ("U".equals(type)) {
                result = productService.updateProduct(product, picList, attrList);
            }

        } catch (BusinessException e) {
            result.setMessage(e.getMessage());
            result.setSuccess(false);
        } catch (Exception e) {
            result.setMessage("操作商品发生错误,请联系管理员");
            result.setSuccess(false);
        }
        return result;
    }

    private String buildImgPath(HttpServletRequest request) {
        String path = "upload";
        SimpleDateFormat formater = new SimpleDateFormat("yyyyMMdd");
        path += "/" + formater.format(new Date());
        path = request.getRealPath(path);
        File dir = new File(path);
        if (!dir.exists()) {
            try {
                dir.mkdirs();
            } catch (Exception e) {
                log.error("error", e);
            }
        }
        return path;
    }

    /**
     * 组装货品信息
     *
     * @param productGoodStr
     * @param product
     */
    private void processGoods(String productGoodStr, Product product) throws BusinessException {
        if (!StringUtil.isEmpty(productGoodStr)) {
            String[] goods = productGoodStr.split(";");
            ArrayList<ProductGoods> goodList = new ArrayList<ProductGoods>();
            product.setGoodsList(goodList);
            if (null != goods && goods.length > 0 && goods.length == 1) {
                //一个货品.例如:67,12,21
                goodList.add(processProductGoods(new ProductGoods(), goods[0]));
            } else if (null != goods && goods.length > 0 && goods.length > 1) {
                //多个货品.例如:67,72,12,21;67,73,23,32......
                for (String str : goods) {
                    goodList.add(processProductGoods(new ProductGoods(), str));
                }
            }
        }
    }

    private ProductGoods processProductGoods(ProductGoods productGoods,
                                             String str) throws BusinessException {
        String[] split = str.split(",");
        if (split.length == 5) {
            productGoods.setNormAttrId(split[0]);//规格属性值ID
            productGoods.setSku(split[1]);
            Integer stock = ConvertUtil.toInt(split[2], -1);
            if (-1 == stock) {
                throw new BusinessException("库存输入有误,请重新输入");
            }
            productGoods.setProductStock(stock);//库存
            try {
                BigDecimal bigDecimal = new BigDecimal(split[3]);
                productGoods.setMallPcPrice(bigDecimal);//PC价格
            } catch (Exception e) {
                throw new BusinessException("PC价格输入有误,请重新输入");
            }
            try {
                BigDecimal bigDecimal = new BigDecimal(split[4]);
                productGoods.setMallMobilePrice(bigDecimal);//mobile价格
            } catch (Exception e) {
                throw new BusinessException("mobile价格输入有误,请重新输入");
            }
        } else if (split.length == 6) {
            productGoods.setNormAttrId(split[0] + "," + split[1]);//规格属性值ID
            productGoods.setSku(split[2]);
            Integer stock = ConvertUtil.toInt(split[3], -1);
            if (-1 == stock) {
                throw new BusinessException("库存输入有误,请重新输入");
            }
            productGoods.setProductStock(stock);//库存
            try {
                BigDecimal bigDecimal = new BigDecimal(split[4]);
                productGoods.setMallPcPrice(bigDecimal);//PC价格
            } catch (Exception e) {
                throw new BusinessException("PC价格输入有误,请重新输入");
            }
            try {
                BigDecimal bigDecimal = new BigDecimal(split[5]);
                productGoods.setMallMobilePrice(bigDecimal);//mobile价格
            } catch (Exception e) {
                throw new BusinessException("mobile价格输入有误,请重新输入");
            }
        }
        productGoods.setProductStockWarning(999);//TODO
        productGoods.setActualSales(0);
        return productGoods;
    }

    @RequestMapping(value = "addsuccess", method = { RequestMethod.GET })
    public String createSuc(HttpServletRequest request) {
        return baseUrl + "addsuccess";
    }

    /**
     * 商品列表无分页
     * @param request
     * @param dataMap
     * @return
     */
    @RequestMapping(value = "listnopage", method = { RequestMethod.GET })
    public @ResponseBody HttpJsonResult<List<Product>> listNoPage(HttpServletRequest request,
                                                                  Map<String, Object> dataMap) {

        Map<String, String> queryMap = WebUtil.handlerQueryMap(request);
        ServiceResult<List<Product>> serviceResult = productService.pageProduct(queryMap, null);
        if (!serviceResult.getSuccess()) {
            if (ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR.equals(serviceResult.getCode())) {
                throw new RuntimeException(serviceResult.getMessage());
            } else {
                throw new BusinessException(serviceResult.getMessage());
            }
        }

        HttpJsonResult<List<Product>> jsonResult = new HttpJsonResult<List<Product>>();
        jsonResult.setRows(serviceResult.getResult());
        return jsonResult;
    }
}
