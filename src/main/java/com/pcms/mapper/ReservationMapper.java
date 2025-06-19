package com.pcms.mapper;

import com.pcms.dto.reservationList.ReservationListBuilder;
import com.pcms.model.Reservation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ReservationMapper {

    @Select("""
                SELECT
                    pc.pc_id,
                    pc.name as pc_name,
                    pc.status,
                    reservation.date,
                    user.name,
                    period.period_number,
                    period.start_time,
                    period.end_time
                FROM pc
                    LEFT JOIN reservation ON pc.pc_id = reservation.pc_id
                        AND reservation.date = #{date}
                        AND reservation.status <> 'cancelled'
                    LEFT JOIN period ON reservation.period_number = period.period_number
                    LEFT JOIN user ON reservation.user_id = user.user_id
                WHERE pc.status <> 'take_out' AND pc.status <> 'maintenance'
                ORDER BY pc.pc_id, period_number;
            """)
    List<ReservationListBuilder> findByDate(@Param("date") String date);

    @Select("""
        SELECT
            *
        FROM reservation
        WHERE
            pc_id = #{pc_id}
        AND
            date = #{date}
        AND
            status <> 'canceled'
    """)
    List<Reservation> findByIdAndDate(@Param("pc_id") String pc_id, @Param("date") String date);

}
