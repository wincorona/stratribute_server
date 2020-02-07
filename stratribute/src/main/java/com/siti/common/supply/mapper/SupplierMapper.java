package com.siti.common.supply.mapper;

import com.siti.common.supply.po.Supplier;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.math.BigDecimal;
import java.util.List;

public interface SupplierMapper extends Mapper<Supplier> {

    @Select({"<script>", "SELECT id,name,province,city,is_factory*1 isFactory,wang_id,is_gov*1 isGov,need_customs*1 needCustoms," +
            "need_airshipping*1 needAirShipping,gps_long,gps_lat,type*1 type,notes,is_active*1 isActive FROM supplier WHERE 1=1" +
            "<if test=\"gpsLong!=null\">  and gps_long=#{gpsLong} </if>" +
            "<if test=\"gpsLat!=null\">  and gps_lat=#{gpsLat} </if>" +
            "<if test=\"province!=null\">  and province=#{province} </if>" +
            "<if test=\"city!=null\">  and city=#{city} </if>" +
            "<if test=\"type!=null\">  and type=#{type} </if>", "</script>"})
    public List<Supplier> findSupplier(@Param("gpsLong") BigDecimal gpsLong, @Param("gpsLat") BigDecimal gpsLat,
                                       @Param("province") String province, @Param("city") String city,
                                       @Param("type") Integer type);
}
