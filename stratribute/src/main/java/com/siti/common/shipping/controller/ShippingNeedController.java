package com.siti.common.shipping.controller;


import com.siti.common.shipping.po.ShippingNeed;
import com.siti.common.shipping.service.ShippingService;
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

    @PostMapping("add_shipping_need")
    public ReturnResult addShippingNeed(@RequestBody ShippingNeed shippingNeed){

        return shippingService.addShippingNeed(shippingNeed);
    }

    @PostMapping("edit_shipping_need")
    public ReturnResult editShippingNeed(@RequestBody ShippingNeed shippingNeed){

        return shippingService.editShippingNeed(shippingNeed);
    }

    @GetMapping("list_my_shipping_need")
    public ReturnResult listMyShippingNeed(){

        return shippingService.listMyShippingNeed();
    }

    @GetMapping("find_shipping need")
    public ReturnResult findShippingNeed(Double startGpsLong,Double startGpsLat,Double kmLimit,String provinceLimit){

        return shippingService.findShippingNeed(startGpsLong,startGpsLat,kmLimit,provinceLimit);
    }


}
