package com.siti.common.supply.biz;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.siti.common.supply.mapper.ProductionAbilityMapper;
import com.siti.common.supply.po.ProductionAbility;
import com.siti.common.supply.po.SupplierProducts;
import com.siti.tool.CommonConstant;
import com.siti.tool.ReturnResult;
import org.apache.ibatis.annotations.Param;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional

public class ProductionAbilityBiz {

    Logger logger = Logger.getLogger(ProductionAbilityBiz.class);

    @Resource
    ProductionAbilityMapper productionAbilityMapper;

    /**
     * 添加productionAbility
     *
     * @param ability
     */
    public ReturnResult addProductionAbility(ProductionAbility ability) {
        ReturnResult returnResult = new ReturnResult();
        try {
            if (ability.getTimestampStart() != null && ability.getTimestampEnd() != null
                    && ability.getTimestampStart().compareTo(ability.getTimestampEnd()) <= 0) {
                productionAbilityMapper.insertSelective(ability);
                returnResult.setCode(CommonConstant.CODE_OK);
                returnResult.setSuccess(true);
                returnResult.setMessage("添加成功!");
            } else {
                returnResult.setCode(CommonConstant.CODE_FAIL);
                returnResult.setSuccess(false);
                returnResult.setMessage("添加失败，请检查时间!");
            }
        } catch (Exception e) {
            logger.info(e.getMessage());
            returnResult.setCode(CommonConstant.CODE_FAIL);
            returnResult.setSuccess(false);
            returnResult.setMessage("添加失败，请检查!");
        }
        return returnResult;
    }

    /**
     * 修改productionAbility
     *
     * @param ability
     */
    public ReturnResult editProductionAbility(ProductionAbility ability) {
        ReturnResult returnResult = new ReturnResult();
        try {
            if (ability.getTimestampStart() != null && ability.getTimestampEnd() != null
                    && ability.getTimestampStart().compareTo(ability.getTimestampEnd()) <= 0) {
                productionAbilityMapper.updateByPrimaryKeySelective(ability);
                returnResult.setCode(CommonConstant.CODE_OK);
                returnResult.setSuccess(true);
                returnResult.setMessage("修改成功!");
            } else {
                returnResult.setCode(CommonConstant.CODE_FAIL);
                returnResult.setSuccess(false);
                returnResult.setMessage("修改失败，请检查时间!");
            }
        } catch (Exception e) {
            logger.info(e.getMessage());
            returnResult.setCode(CommonConstant.CODE_FAIL);
            returnResult.setSuccess(false);
            returnResult.setMessage("修改失败，请检查!");
        }
        return returnResult;
    }

    /**
     * 修改productionAbility
     *
     * @param bookerFacilityId
     * @param id
     * @param isBooked
     */
    public ReturnResult updateBook(Integer isBooked, Integer bookerFacilityId, Integer id) {
        ReturnResult returnResult = new ReturnResult();
        try {
            productionAbilityMapper.updateBook(isBooked, bookerFacilityId, id);
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
     * 查询productionAbility
     *
     * @param city
     * @param gpsLat
     * @param gpsLong
     * @param page
     * @param pageSize
     * @param province
     * @param productId
     */
    public ReturnResult findProductionByProduct(BigDecimal gpsLong, BigDecimal gpsLat, String province, String city, Integer productId,
                                                int page, int pageSize) {
        ReturnResult returnResult = new ReturnResult();
        PageHelper.startPage(page, pageSize);
        List<ProductionAbility> dataList = productionAbilityMapper.findProductionByProduct(gpsLong, gpsLat, province, city, productId);
        PageInfo<ProductionAbility> pageInfo = new PageInfo<>(dataList);
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
        List<ProductionAbility> dataList = productionAbilityMapper.findMyProducts(userId);
        PageInfo<ProductionAbility> pageInfo = new PageInfo<>(dataList);
        returnResult.setCode(CommonConstant.CODE_OK);
        returnResult.setSuccess(true);
        returnResult.setMessage("查询成功!");
        returnResult.setData(pageInfo);
        return returnResult;
    }
}
