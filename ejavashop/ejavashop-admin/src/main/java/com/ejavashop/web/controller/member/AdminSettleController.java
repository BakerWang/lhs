package com.ejavashop.web.controller.member;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ejavashop.core.ConstantsEJS;
import com.ejavashop.core.HttpJsonResult;
import com.ejavashop.core.PagerInfo;
import com.ejavashop.core.ServiceResult;
import com.ejavashop.core.WebUtil;
import com.ejavashop.core.exception.BusinessException;
import com.ejavashop.entity.member.BonusSettle;
import com.ejavashop.entity.member.BonusSettleDetail;
import com.ejavashop.entity.system.SystemAdmin;
import com.ejavashop.service.member.IBonusSettleDetailService;
import com.ejavashop.service.member.IBonusSettleService;
import com.ejavashop.web.controller.BaseController;
import com.ejavashop.web.util.WebAdminSession;

/**
 * 
 * @author rainy
 *
 */
@Controller
@RequestMapping(value = "admin/member/settle")
public class AdminSettleController extends BaseController{
	@Resource(name="bonusSettleService")
	private IBonusSettleService bonusSettleService;
	@Resource(name="bonusSettleDetailService")
	private IBonusSettleDetailService bonusSettleDetailService;
	
    @RequestMapping(value = "", method = { RequestMethod.GET })
    public String index(Map<String, Object> dataMap) throws Exception {
        dataMap.put("pageSize", ConstantsEJS.DEFAULT_PAGE_SIZE);
        return "admin/settle/list";
    }
    
    @RequestMapping(value = "list", method = { RequestMethod.GET })
    public @ResponseBody HttpJsonResult<List<BonusSettle>> list(HttpServletRequest request,
                                                           HttpServletResponse response,
                                                           Map<String, Object> dataMap) {

        Map<String, String> queryMap = WebUtil.handlerQueryMap(request);
        PagerInfo pager = WebUtil.handlerPagerInfo(request, dataMap);
        ServiceResult<List<BonusSettle>> serviceResult = bonusSettleService.getBonusSettleList(queryMap, pager);
        if (!serviceResult.getSuccess()) {
            if (ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR.equals(serviceResult.getCode())) {
                throw new RuntimeException(serviceResult.getMessage());
            } else {
                throw new BusinessException(serviceResult.getMessage());
            }
        }

        HttpJsonResult<List<BonusSettle>> jsonResult = new HttpJsonResult<List<BonusSettle>>();
        jsonResult.setRows(serviceResult.getResult());
        jsonResult.setTotal(pager.getRowsCount());
        return jsonResult;
    }
    
    
    @RequestMapping(value = "detail", method = { RequestMethod.GET })
    public String detail(Map<String, Object> dataMap,Integer id) throws Exception {
        dataMap.put("pageSize", ConstantsEJS.DEFAULT_PAGE_SIZE);
        dataMap.put("settleId", id);
        return "admin/settle/listdetail";
    }
    
    @RequestMapping(value = "detaillist", method = { RequestMethod.GET })
    public @ResponseBody HttpJsonResult<List<BonusSettleDetail>> detailList(HttpServletRequest request,
                                                           HttpServletResponse response,
                                                           Map<String, Object> dataMap) {

        Map<String, String> queryMap = WebUtil.handlerQueryMap(request);
        PagerInfo pager = WebUtil.handlerPagerInfo(request, dataMap);
        ServiceResult<List<BonusSettleDetail>> serviceResult = bonusSettleDetailService.getBonusSettleDetailList(queryMap, pager);
        if (!serviceResult.getSuccess()) {
            if (ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR.equals(serviceResult.getCode())) {
                throw new RuntimeException(serviceResult.getMessage());
            } else {
                throw new BusinessException(serviceResult.getMessage());
            }
        }

        HttpJsonResult<List<BonusSettleDetail>> jsonResult = new HttpJsonResult<List<BonusSettleDetail>>();
        jsonResult.setRows(serviceResult.getResult());
        jsonResult.setTotal(pager.getRowsCount());
        return jsonResult;
    }
    
    
    @RequestMapping(value = "settleadd", method = { RequestMethod.GET })
    public @ResponseBody HttpJsonResult<Boolean> upGrade(@RequestParam(value = "userId") Integer userId,
                                                            HttpServletRequest request) {
        HttpJsonResult<Boolean> jsonResult = new HttpJsonResult<Boolean>();
        SystemAdmin admin =WebAdminSession.getAdminUser(request);
        BonusSettle bonusSettle = new BonusSettle();
        bonusSettle.setSettleDate(new Date());
        bonusSettle.setCreateUser(admin.getName());
        ServiceResult<Integer>  serviceResult = bonusSettleService.saveBonusSettle(bonusSettle);
        if(serviceResult.getSuccess() && serviceResult.getResult()>0){
        	 jsonResult.setData(true);
        }
        return jsonResult;
    }
    
    
}
