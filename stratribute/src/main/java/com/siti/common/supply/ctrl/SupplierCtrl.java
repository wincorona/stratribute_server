package com.siti.common.supply.ctrl;

import com.siti.common.supply.biz.SupplierBiz;
import com.siti.common.supply.po.Supplier;
import com.siti.tool.ReturnResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.math.BigDecimal;

@RestController
@RequestMapping("supplier")
public class SupplierCtrl {

    @Resource
    SupplierBiz supplierBiz;

    /**
     * 添加supplier
     */
    @PostMapping("add_supplier")
    public ReturnResult addSupplier(@RequestBody Supplier supplier) {
        return supplierBiz.addSupplier(supplier);
    }

    /**
     * 修改supplier
     */
    @PostMapping("edit_supplier")
    public ReturnResult editSupplier(@RequestBody Supplier supplier) {
        return supplierBiz.editSupplier(supplier);
    }

    /**
     * 查询supplier
     */
    @GetMapping("find_supplier")
    public ReturnResult findSupplier(@RequestParam(value = "gpsLong", required = false) BigDecimal gpsLong,
                                     @RequestParam(value = "gpsLat", required = false) BigDecimal gpsLat,
                                     @RequestParam(value = "province", required = false) String province,
                                     @RequestParam(value = "city", required = false) String city,
                                     @RequestParam(value = "type", required = false) Integer type,
                                     int page, int pageSize) {
        return supplierBiz.findSupplier(gpsLong, gpsLat, province, city, type, page, pageSize);
    }
}
