package com.pcms.dataaccess.mapper;

import com.pcms.model.Pc;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface PcMapper {


    @Select("""
        SELECT
            pc_id,
            name
        FROM
            pc
        WHERE
            status = 'available'
    """)
    List<Pc> findByReservation();

}
