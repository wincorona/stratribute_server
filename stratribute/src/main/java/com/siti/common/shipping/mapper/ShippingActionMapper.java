package com.siti.common.shipping.mapper;


import com.siti.common.shipping.po.ShippingAction;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * Created by 12293 on 2020/2/7.
 */
public interface ShippingActionMapper extends Mapper<ShippingAction> {

    @Select("<script>" +
            "select `id`, `shipping_need_id`, `company`, `name`, `mobile`, `tracking_id`, `user_id` from shipping_action " +
            "<if test = \"shippingNeedId !=null and shippingNeedId !=''\"> where shipping_need_id = #{shippingNeedId}</if>" +
            "</script>")
    List<ShippingAction> getShippingActionByNeedId(@Param("shippingNeedId") String shippingNeedId);

    @Select("select * from shipping_action where user_id = #{userId}")
    List<ShippingAction> listShippingAction(@Param("userId") Integer userId);
}
