package com.siti.common.supply.biz;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.siti.common.supply.mapper.SupplierMapper;
import com.siti.common.supply.mapper.SupplierProductsMapper;
import com.siti.common.supply.po.Supplier;
import com.siti.common.supply.po.SupplierProducts;
import com.siti.tool.CommonConstant;
import com.siti.tool.ReturnResult;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;

@Service
@Transactional

public class SupplierProductsBiz {

    Logger logger = Logger.getLogger(SupplierProductsBiz.class);

    @Resource
    SupplierProductsMapper supplierProductsMapper;

    /**
     * 添加supplierProduct
     *
     * @param supplierProducts
     */
    public ReturnResult addSupplierProduct(SupplierProducts supplierProducts) {
        ReturnResult returnResult = new ReturnResult();
        try {
            supplierProductsMapper.insertSelective(supplierProducts);
            returnResult.setCode(CommonConstant.CODE_OK);
            returnResult.setSuccess(true);
            returnResult.setMessage("添加成功!");
        } catch (Exception e) {
            logger.info(e.getMessage());
            returnResult.setCode(CommonConstant.CODE_FAIL);
            returnResult.setSuccess(false);
            returnResult.setMessage("添加失败，请检查!");
        }
        return returnResult;
    }

    /**
     * 修改supplierProduct
     *
     * @param supplierProducts
     */
    public ReturnResult editSupplierProduct(SupplierProducts supplierProducts) {
        ReturnResult returnResult = new ReturnResult();
        try {
            supplierProductsMapper.updateByPrimaryKeySelective(supplierProducts);
            returnResult.setCode(CommonConstant.CODE_OK);
            returnResult.setSuccess(true);
            returnResult.setMessage("修改成功!");
        } catch (Exception e) {
            logger.info(e.getMessage());
            returnResult.setCode(CommonConstant.CODE_FAIL);
            returnResult.setSuccess(false);
            returnResult.setMessage("修改失败，请检查!");
        }
        return returnResult;
    }

    /**
     * 查询supplierProduct
     *
     * @param city
     * @param gpsLat
     * @param gpsLong
     * @param page
     * @param pageSize
     * @param province
     * @param productId
     */
    public ReturnResult findSupplierProducts(BigDecimal gpsLong, BigDecimal gpsLat, String province, String city, Integer productId,
                                             int page, int pageSize) {
        ReturnResult returnResult = new ReturnResult();
        PageHelper.startPage(page, pageSize);
        List<SupplierProducts> dataList = supplierProductsMapper.findSupplierProducts(gpsLong, gpsLat, province, city, productId);
        PageInfo<SupplierProducts> pageInfo = new PageInfo<>(dataList);
        returnResult.setCode(CommonConstant.CODE_OK);
        returnResult.setSuccess(true);
        returnResult.setMessage("查询成功!");
        returnResult.setData(pageInfo);
        return returnResult;
    }

    /**
     * 根据用户userId获取我的全部库存列表
     *
     * @param userId
     */
    public ReturnResult findMyProducts(Integer userId, int page, int pageSize) {
        ReturnResult returnResult = new ReturnResult();
        PageHelper.startPage(page, pageSize);
        List<SupplierProducts> dataList = supplierProductsMapper.findMyProducts(userId);
        PageInfo<SupplierProducts> pageInfo = new PageInfo<>(dataList);
        returnResult.setCode(CommonConstant.CODE_OK);
        returnResult.setSuccess(true);
        returnResult.setMessage("查询成功!");
        returnResult.setData(pageInfo);
        return returnResult;
    }
}
