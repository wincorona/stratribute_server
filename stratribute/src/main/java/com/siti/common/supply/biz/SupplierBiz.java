package com.siti.common.supply.biz;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.siti.common.supply.mapper.SupplierMapper;
import com.siti.common.supply.po.Supplier;
import com.siti.tool.CommonConstant;
import com.siti.tool.ReturnResult;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional

public class SupplierBiz {

    Logger logger = Logger.getLogger(SupplierBiz.class);

    @Resource
    SupplierMapper supplierMapper;

    /**
     * 添加supplier
     *
     * @param supplier
     */
    public ReturnResult addSupplier(Supplier supplier) {
        ReturnResult returnResult = new ReturnResult();
        try {
            supplierMapper.insertSelective(supplier);
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
     * 修改supplier信息
     *
     * @param supplier
     */
    public ReturnResult editSupplier(Supplier supplier) {
        ReturnResult returnResult = new ReturnResult();
        try {
            supplierMapper.updateByPrimaryKeySelective(supplier);
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
     * 查询supplier
     *
     * @param city
     * @param gpsLat
     * @param gpsLong
     * @param page
     * @param pageSize
     * @param province
     * @param type
     */
    public ReturnResult findSupplier(BigDecimal gpsLong, BigDecimal gpsLat, String province, String city, Integer type,
                                     int page, int pageSize) {
        ReturnResult returnResult = new ReturnResult();
        PageHelper.startPage(page, pageSize);
        List<Supplier> dataList = supplierMapper.findSupplier(gpsLong, gpsLat, province, city, type);
        PageInfo<Supplier> pageInfo = new PageInfo<>(dataList);
        returnResult.setCode(CommonConstant.CODE_OK);
        returnResult.setSuccess(true);
        returnResult.setMessage("查询成功!");
        returnResult.setData(pageInfo);
        return returnResult;
    }
}
