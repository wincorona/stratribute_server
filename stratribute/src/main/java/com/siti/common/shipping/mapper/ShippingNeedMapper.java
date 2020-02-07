package com.siti.common.shipping.mapper;


import com.siti.common.shipping.po.ShippingNeed;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * Created by 12293 on 2020/2/6.
 */
public interface ShippingNeedMapper extends Mapper<ShippingNeed> {

    @Select("select * from shipping_need where match_id = #{userId}")
    List<ShippingNeed> listMyShippingNeed(@Param("userId") Integer userId);

    @Select("<script>" +
            " select * from shipping_need " +
            " where 1=1 " +
            " and sender_gps_long between #{minLng} and #{maxLng} " +
            " and sender_gps_lat between #{minLat} and #{maxLat} " +
            " <if test = \"provinceLimit!=null and provinceLimit!='' \"> and sender_address like '%${provinceLimit}%' </if>" +
            "</script>")
    List<ShippingNeed> findShippingNeed(@Param("minLat") Double minLat, @Param("maxLat") Double maxLat, @Param("minLng") Double minLng, @Param("maxLng") Double maxLng, @Param("provinceLimit") String provinceLimit);
}
