package com.siti.common.shipping.biz;


import com.siti.common.shipping.mapper.ShippingActionMapper;
import com.siti.common.shipping.mapper.ShippingNeedMapper;
import com.siti.common.shipping.po.ShippingAction;
import com.siti.common.shipping.po.ShippingNeed;
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
        int flag = shippingMapper.updateByPrimaryKey(shippingNeed);
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

    public ReturnResult listMyShippingNeed() {
        ReturnResult rr = new ReturnResult();
        Integer userId = 0;
        List<ShippingNeed> MyShippingNeedList = shippingMapper.listMyShippingNeed(userId);
        rr.setCode(CommonConstant.CODE_OK);
        rr.setSuccess(true);
        rr.setData(MyShippingNeedList);
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
    public ReturnResult findShippingNeed(Double startGpsLong, Double startGpsLat, double kmLimit, String provinceLimit) {
        ReturnResult rr = new ReturnResult();
        double[] around = LocationUtils.getAround(startGpsLat, startGpsLong, kmLimit);
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
                maxLng, provinceLimit);
        rr.setCode(CommonConstant.CODE_OK);
        rr.setSuccess(true);
        rr.setData(MyShippingNeedList);
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
        int flag = shippingActionMapper.updateByPrimaryKey(shippingAction);
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

    public ReturnResult getShippingAction(String shippingNeedId) {
        ReturnResult rr = new ReturnResult();
        List<ShippingAction> MyShippingNeedList = shippingActionMapper.getShippingActionByNeedId(shippingNeedId);
        rr.setCode(CommonConstant.CODE_OK);
        rr.setSuccess(true);
        rr.setData(MyShippingNeedList);
        return rr;
    }

    public ReturnResult listShippingAction() {
        ReturnResult rr = new ReturnResult();
        Integer userId = 0;
        List<ShippingAction> MyShippingNeedList = shippingActionMapper.listShippingAction(userId);
        rr.setCode(CommonConstant.CODE_OK);
        rr.setSuccess(true);
        rr.setData(MyShippingNeedList);
        return rr;
    }
}
