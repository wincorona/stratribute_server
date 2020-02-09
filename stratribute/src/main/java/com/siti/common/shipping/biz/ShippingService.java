package com.siti.common.shipping.biz;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.siti.common.shipping.mapper.ShippingActionMapper;
import com.siti.common.shipping.mapper.ShippingNeedMapper;
import com.siti.common.shipping.po.ShippingAction;
import com.siti.common.shipping.po.ShippingNeed;
import com.siti.common.shipping.vo.ScopeInfo;
import com.siti.common.supply.po.ProductionAbility;
import com.siti.common.util.LocationUtils;
import com.siti.tool.CommonConstant;
import com.siti.tool.ReturnResult;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by 12293 on 2020/2/6.
 */
@Service
public class ShippingService {


    @Resource
    private ShippingNeedMapper shippingMapper;
    @Resource
    private ShippingActionMapper shippingActionMapper;

    public ReturnResult addShippingNeed(ShippingNeed shippingNeed) {
        ReturnResult rr = new ReturnResult();

        Integer userId = 0;
        shippingNeed.setMatchId(userId);

        int flag = shippingMapper.insert(shippingNeed);
        if (flag > 0) {
            rr.setCode(CommonConstant.CODE_OK);
            rr.setSuccess(true);
            return rr;
        } else {
            rr.setCode(CommonConstant.CODE_FAIL);
            rr.setSuccess(false);
            return rr;
        }
    }

    public ReturnResult editShippingNeed(ShippingNeed shippingNeed) {
        ReturnResult rr = new ReturnResult();
        int flag = shippingMapper.updateByPrimaryKeySelective(shippingNeed);
        if (flag > 0) {
            rr.setCode(CommonConstant.CODE_OK);
            rr.setSuccess(true);
            return rr;
        } else {
            rr.setCode(CommonConstant.CODE_FAIL);
            rr.setSuccess(false);
            return rr;
        }
    }

    public ReturnResult listMyShippingNeed(Integer page,Integer pageSize) {
        ReturnResult rr = new ReturnResult();
        PageHelper.startPage(page, pageSize);
        Integer userId = 0;
        List<ShippingNeed> MyShippingNeedList = shippingMapper.listMyShippingNeed(userId);
        PageInfo<ShippingNeed> pageInfo = new PageInfo<>(MyShippingNeedList);
        rr.setCode(CommonConstant.CODE_OK);
        rr.setSuccess(true);
        rr.setData(pageInfo);
        return rr;

    }

    /**
     * 根据物流人经纬度信息及取货距离查询附近的供方信息
     *
     * @Param startGpsLong
     * @Param startGpsLat
     * @Param kmLimit    单位千米
     * @Param provinceLimit  省份筛选
     */
    public ReturnResult findShippingNeed(ScopeInfo scopeInfo, Integer page, Integer pageSize) {
        ReturnResult rr = new ReturnResult();
        PageHelper.startPage(page, pageSize);

        double[] around = LocationUtils.getAround(scopeInfo.getStartGpsLat(),
                scopeInfo.getStartGpsLong(),scopeInfo.getKmLimit());

        Double minLat;
        Double maxLat;
        Double minLng;
        Double maxLng;
        try {
            minLat = around[0];
            maxLat = around[2];
            minLng = around[1];
            maxLng = around[3];
        } catch (Exception e) {
            rr.setCode(CommonConstant.CODE_FAIL);
            rr.setSuccess(false);
            return rr;
        }
        List<ShippingNeed> MyShippingNeedList = shippingMapper.findShippingNeed(minLat,
                maxLat,
                minLng,
                maxLng, scopeInfo.getProvinceLimit());
        PageInfo<ShippingNeed> pageInfo = new PageInfo<>(MyShippingNeedList);
        rr.setCode(CommonConstant.CODE_OK);
        rr.setSuccess(true);
        rr.setData(pageInfo);
        return rr;
    }

    public ReturnResult addShippingAction(ShippingAction shippingAction) {
        ReturnResult rr = new ReturnResult();
        int flag = shippingActionMapper.insert(shippingAction);
        if (flag > 0) {
            rr.setCode(CommonConstant.CODE_OK);
            rr.setSuccess(true);
            return rr;
        } else {
            rr.setCode(CommonConstant.CODE_FAIL);
            rr.setSuccess(false);
            return rr;
        }
    }

    public ReturnResult editShippingAction(ShippingAction shippingAction) {
        ReturnResult rr = new ReturnResult();
        int flag = shippingActionMapper.updateByPrimaryKeySelective(shippingAction);
        if (flag > 0) {
            rr.setCode(CommonConstant.CODE_OK);
            rr.setSuccess(true);
            return rr;
        } else {
            rr.setCode(CommonConstant.CODE_FAIL);
            rr.setSuccess(false);
            return rr;
        }
    }

    public ReturnResult getShippingAction(String shippingNeedId,Integer page,Integer pageSize) {
        ReturnResult rr = new ReturnResult();
        PageHelper.startPage(page, pageSize);
        List<ShippingAction> MyShippingNeedList = shippingActionMapper.getShippingActionByNeedId(shippingNeedId);
        PageInfo<ShippingAction> pageInfo = new PageInfo<>(MyShippingNeedList);
        rr.setCode(CommonConstant.CODE_OK);
        rr.setSuccess(true);
        rr.setData(pageInfo);
        return rr;
    }

    public ReturnResult listShippingAction(Integer page,Integer pageSize) {
        ReturnResult rr = new ReturnResult();
        PageHelper.startPage(page, pageSize);
        Integer userId = 0; // TODO 获取userId
        List<ShippingAction> MyShippingNeedList = shippingActionMapper.listShippingAction(userId);
        PageInfo<ShippingAction> pageInfo = new PageInfo<>(MyShippingNeedList);
        rr.setCode(CommonConstant.CODE_OK);
        rr.setSuccess(true);
        rr.setData(pageInfo);
        return rr;
    }
}
