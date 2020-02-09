package com.siti.common.shipping.ctrl;



import com.siti.common.shipping.po.ShippingAction;
import com.siti.common.shipping.biz.ShippingService;
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

    /**
     * 添加需求动作
     */
    @PostMapping("add_shipping_action")
    public ReturnResult addShippingAction(@RequestBody ShippingAction shippingAction){

        return shippingService.addShippingAction(shippingAction);
    }

    /**
     * 编辑
     * */
    @PostMapping("edit_shipping_action")
    public ReturnResult editShippingAction(@RequestBody ShippingAction shippingAction){

        return shippingService.editShippingAction(shippingAction);
    }


    /**
     * 查找某物流需求的运送行为
     * @Param shippingNeedId
     * */
    @GetMapping("get_shipping_action")
    public ReturnResult getShippingAction(String shippingNeedId,Integer page,Integer pageSize){

        return shippingService.getShippingAction(shippingNeedId,page,pageSize);
    }

    /**
     * 显示某用户的所有执行的物流任务
     * */
    @GetMapping("list_shipping_action")
    public ReturnResult listShippingAction(Integer page,Integer pageSize){

        return shippingService.listShippingAction(page,pageSize);
    }
}
