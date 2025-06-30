package com.pcms.dataaccess.mapper;

import com.pcms.dto.reservationList.ReservationListBuilder;
import com.pcms.model.Reservation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ReservationMapper {

    /**
     * 指定された日付の予約リストを取得します
     *
     * @param date 予約を取得する必要がある日付（文字列形式）
     * @return 指定された日付の予約詳細を含む {@code ReservationListBuilder} レコードのリスト
     */
    List<ReservationListBuilder> findByDate(@Param("date") String date);

    /**
     * ステータスが「take_out」の PC を除いて、特定の PC と日付の予約リストを取得します。
     *
     * @param pc_id 予約を取得する PC の識別子
     * @param date  予約を取得する日付を文字列形式で指定します
     * @return 指定された PC と日付の予約の詳細を含む {@code Reservation} オブジェクトのリスト。
     */
    List<Reservation> findByIdAndDate(@Param("pc_id") String pc_id, @Param("date") String date);

    /**
     * 指定された予約リストをシステム内に予約します
     *
     * @param reservations 追加する予約を表す {@code Reservation} オブジェクトのリスト
     * @return 予約に成功した数
     */
    int reserve(@Param("reservations") List<Reservation> reservations);

}
