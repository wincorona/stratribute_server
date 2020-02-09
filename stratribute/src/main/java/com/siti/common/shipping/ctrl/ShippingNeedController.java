package com.siti.common.shipping.ctrl;


import com.siti.common.shipping.po.ShippingNeed;
import com.siti.common.shipping.biz.ShippingService;
import com.siti.tool.ReturnResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * Created by 12293 on 2020/2/6.
 */
@RestController
@RequestMapping("shipping_need")
public class ShippingNeedController {

    @Resource
    private ShippingService shippingService;

    /**
     * 志愿者 配对成功并add_match之后，填写物流需求信息，需填写如上信息。
     * */
    @PostMapping("add_shipping_need")
    public ReturnResult addShippingNeed(@RequestBody ShippingNeed shippingNeed){

        return shippingService.addShippingNeed(shippingNeed);
    }

    /**
     * 志愿者修改自己填写的物流需求
     * */
    @PostMapping("edit_shipping_need")
    public ReturnResult editShippingNeed(@RequestBody ShippingNeed shippingNeed){

        return shippingService.editShippingNeed(shippingNeed);
    }

    /**
     * 志愿者查看自己的物流信息，用于前端界面显示
     * */
    @GetMapping("list_my_shipping_need")
    public ReturnResult listMyShippingNeed(Integer page,Integer pageSize){

        return shippingService.listMyShippingNeed(page,pageSize);
    }

    /**
     * 物流公司查需, 根据起始点，方圆几公里内可解，限制省份等信息查找可以做的单。
     * @Param startGpsLong 起始经度
     * @Param startGpsLat 纬度
     * @Param kmLimit 限制距离
     * @Param provinceLimit 限制省份
     * */
    @GetMapping("find_shipping need")
    public ReturnResult findShippingNeed(Double startGpsLong,
                                         Double startGpsLat,
                                         Double kmLimit,
                                         String provinceLimit,
                                         Integer page,Integer pageSize){

        return shippingService.findShippingNeed(startGpsLong,startGpsLat,kmLimit,provinceLimit,page,pageSize);
    }


}
