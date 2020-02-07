package com.siti.common.shipping.controller;



import com.siti.common.shipping.po.ShippingAction;
import com.siti.common.shipping.service.ShippingService;
import com.siti.tool.ReturnResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * Created by 12293 on 2020/2/7.
 */
@RestController
@RequestMapping("shipping_action")
public class ShippingActionController {

    @Resource
    private ShippingService shippingService;

    @PostMapping("add_shipping_action")
    public ReturnResult addShippingAction(@RequestBody ShippingAction shippingAction){

        return shippingService.addShippingAction(shippingAction);
    }

    @PostMapping("edit_shipping_action")
    public ReturnResult editShippingAction(@RequestBody ShippingAction shippingAction){

        return shippingService.editShippingAction(shippingAction);
    }

    @GetMapping("get_shipping_action")
    public ReturnResult getShippingAction(String shippingNeedId){

        return shippingService.getShippingAction(shippingNeedId);
    }

    @GetMapping("list_shipping_action")
    public ReturnResult listShippingAction(){

        return shippingService.listShippingAction();
    }
}
