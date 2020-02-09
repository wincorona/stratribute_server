package com.siti.common.supply.ctrl;

import com.siti.common.supply.biz.ProductionAbilityBiz;
import com.siti.common.supply.po.ProductionAbility;
import com.siti.tool.ReturnResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.math.BigDecimal;

@RestController
@RequestMapping("product_ability")

public class ProductionAbilityCtrl {

    @Resource
    ProductionAbilityBiz abilityBiz;

    /**
     * 添加productionAbility
     * TODO 用户登录
     */
    @PostMapping("add_prouction_ability")
    public ReturnResult addProductionAbility(@RequestBody ProductionAbility ability) {
        return abilityBiz.addProductionAbility(ability);
    }

    /**
     * 修改productionAbility
     * TODO 用户登录
     */
    @PostMapping("edit_prouction_ability")
    public ReturnResult editProductionAbility(@RequestBody ProductionAbility ability) {
        return abilityBiz.editProductionAbility(ability);
    }

    /**
     * 修改是否预定
     */
    @PostMapping("book_production_ability")
    public ReturnResult bookProductionAbility(Integer isBooked, Integer bookerFacilityId, Integer id) {
        return abilityBiz.updateBook(isBooked, bookerFacilityId, id);
    }

    /**
     * 查询supplierProducts
     */
    @GetMapping("find_production_by_product")
    public ReturnResult findProductionByProduct(@RequestParam(value = "gpsLong", required = false) BigDecimal gpsLong,
                                                @RequestParam(value = "gpsLat", required = false) BigDecimal gpsLat,
                                                @RequestParam(value = "province", required = false) String province,
                                                @RequestParam(value = "city", required = false) String city,
                                                @RequestParam(value = "productId", required = false) Integer productId,
                                                int page, int pageSize) {
        return abilityBiz.findProductionByProduct(gpsLong, gpsLat, province, city, productId, page, pageSize);
    }

    /**
     * 查询我的库存
     */
    @GetMapping("list_my_production")
    public ReturnResult listMyProduction(int page, int pageSize) {
        // TODO 获取userId
        Integer userId = null;
        return abilityBiz.findMyProducts(userId, page, pageSize);
    }
}
