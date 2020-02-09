package com.siti.common.supply.mapper;

import com.siti.common.supply.po.Supplier;
import com.siti.common.supply.po.SupplierProducts;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.math.BigDecimal;
import java.util.List;

public interface SupplierProductsMapper extends Mapper<SupplierProducts> {

    @Select({"<script>", "SELECT id,name,type*1 type,model,upc,if_must*1 ifMust,if_special*1 ifSpecial,unit_price," +
            "supplier_id,product_id,user_id,timestamp_post,timestamp_last_edit FROM supplier_products WHERE 1=1" +
            "<if test=\"gpsLong!=null\">  and gps_long=#{gpsLong} </if>" +
            "<if test=\"gpsLat!=null\">  and gps_lat=#{gpsLat} </if>" +
            "<if test=\"province!=null\">  and province=#{province} </if>" +
            "<if test=\"city!=null\">  and city=#{city} </if>" +
            "<if test=\"productId!=null\">  and product_id=#{productId} </if>", "</script>"})
    public List<SupplierProducts> findSupplierProducts(@Param("gpsLong") BigDecimal gpsLong, @Param("gpsLat") BigDecimal gpsLat,
                                                       @Param("province") String province, @Param("city") String city,
                                                       @Param("productId") Integer productId);

    /**
     * 根据用户userId获取我的全部库存列表
     *
     * @param userId
     */
    @Select("SELECT id,name,type*1 type,model,upc,if_must*1 ifMust,if_special*1 ifSpecial,unit_price,supplier_id," +
            "product_id,user_id,timestamp_post,timestamp_last_edit FROM supplier_products WHERE user_id=#{userId}")
    public List<SupplierProducts> findMyProducts(@Param("userId") Integer userId);
}
