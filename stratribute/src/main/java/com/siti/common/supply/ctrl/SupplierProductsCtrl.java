package com.siti.common.supply.ctrl;

import com.siti.common.supply.biz.SupplierProductsBiz;
import com.siti.common.supply.po.SupplierProducts;
import com.siti.tool.ReturnResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.math.BigDecimal;

@RestController
@RequestMapping("supplier_products")

/**
 * 供应商产品库存
 */

public class SupplierProductsCtrl {

    @Resource
    SupplierProductsBiz supplierProductsBiz;

    /**
     * 添加supplierProducts
     * TODO 用户登录
     */
    @PostMapping("add_supplier_product")
    public ReturnResult addSupplierProducts(@RequestBody SupplierProducts supplierProducts) {
        return supplierProductsBiz.addSupplierProduct(supplierProducts);
    }

    /**
     * 修改supplierProducts
     * TODO 用户登录
     */
    @PostMapping("edit_supplier_product")
    public ReturnResult editSupplier(@RequestBody SupplierProducts supplierProducts) {
        return supplierProductsBiz.editSupplierProduct(supplierProducts);
    }

    /**
     * 查询supplierProducts
     */
    @GetMapping("find_supplier_by_product")
    public ReturnResult findSupplierByProduct(@RequestParam(value = "gpsLong", required = false) BigDecimal gpsLong,
                                              @RequestParam(value = "gpsLat", required = false) BigDecimal gpsLat,
                                              @RequestParam(value = "province", required = false) String province,
                                              @RequestParam(value = "city", required = false) String city,
                                              @RequestParam(value = "productId", required = false) Integer productId,
                                              int page, int pageSize) {
        return supplierProductsBiz.findSupplierProducts(gpsLong, gpsLat, province, city, productId, page, pageSize);
    }

    /**
     * 查询我的库存
     */
    @GetMapping("list_my_products")
    public ReturnResult findSupplierByProduct(int page, int pageSize) {
        // TODO 获取userId
        Integer userId = null;
        return supplierProductsBiz.findMyProducts(userId, page, pageSize);
    }
}
