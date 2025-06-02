package com.pcms.repository;

import com.pcms.dto.reservation.ReservationPeriodDTO;
import com.pcms.dto.reservationList.ReservationListBuilder;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface ReservationRepository {

    @Select("""
        SELECT
            pc.pc_id,
            pc.name as pc_name,
            pc.status,
            reservation.date,
            period.start_time,
            period.end_time
        FROM pc
            LEFT JOIN reservation ON pc.pc_id = reservation.pc_id
                AND reservation.date = #{date}
                AND reservation.status <> 'cancelled'
            LEFT JOIN period ON reservation.period_number = period.period_number
        ORDER BY pc.pc_id;
    """)
    List<ReservationListBuilder> findByDate(@Param("date") String date);

    @Select("""
        SELECT
            r.period_number,
            p.start_time,
            p.end_time
        FROM reservation as r 
        JOIN period as p
            ON r.
    """)
    List<ReservationPeriodDTO> findReservationPeriod(
      @Param("pc_id") int pc_id,
      @Param("date") LocalDate date
    );

}
