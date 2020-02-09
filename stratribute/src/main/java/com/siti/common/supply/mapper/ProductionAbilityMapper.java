package com.siti.common.supply.mapper;

import com.siti.common.supply.po.ProductionAbility;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import tk.mybatis.mapper.common.Mapper;

import java.math.BigDecimal;
import java.util.List;

public interface ProductionAbilityMapper extends Mapper<ProductionAbility> {

    @Update("UPDATE production_ability SET is_booked=#{isBooked}, booker_facility_id=#{bookerFacilityId} where id=#{id}")
    void updateBook(@Param("isBooked") Integer isBooked, @Param("bookerFacilityId") Integer bookerFacilityId,
                    @Param("id") Integer id);

    @Select({"<script>", "SELECT id,name,type*1 type,model,upc,if_must*1 ifMust,if_special*1 ifSpecial,unit_price," +
            "supplier_id,product_id,supplier_product_id,user_id,timestamp_post,timestamp_last_edit,timestamp_start," +
            "timestamp_end,is_booked*1 isBooked,booker_facility_id,amount FROM production_ability WHERE 1=1 " +
            "<if test=\"gpsLong!=null\">  and gps_long=#{gpsLong} </if>" +
            "<if test=\"gpsLat!=null\">  and gps_lat=#{gpsLat} </if>" +
            "<if test=\"province!=null\">  and province=#{province} </if>" +
            "<if test=\"city!=null\">  and city=#{city} </if>" +
            "<if test=\"productId!=null\">  and product_id=#{productId} </if>", "</script>"})
    List<ProductionAbility> findProductionByProduct(@Param("gpsLong") BigDecimal gpsLong, @Param("gpsLat") BigDecimal gpsLat,
                                                    @Param("province") String province, @Param("city") String city,
                                                    @Param("productId") Integer productId);

    /**
     * 查看我的库存
     *
     * @param userId
     */
    @Select("SELECT id,name,type*1 type,model,upc,if_must*1 ifMust,if_special*1 ifSpecial,unit_price," +
            "supplier_id,product_id,supplier_product_id,user_id,timestamp_post,timestamp_last_edit,timestamp_start," +
            "timestamp_end,is_booked*1 isBooked,booker_facility_id,amount FROM production_ability WHERE user_id=#{userId}")
    List<ProductionAbility> findMyProducts(@Param("userId") Integer userId);
}
