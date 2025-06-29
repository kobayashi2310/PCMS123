package com.pcms.dto.reservation;

import java.time.LocalDate;
import java.util.List;

/**
 * ReservationCheckDTO は、予約の空き状況を確認するために必要な詳細情報を表すデータ転送オブジェクトです。
 * このクラスは、ID で識別される特定のパーソナルコンピュータ (PC)、予約確認日、予約目的（指定されている場合）、
 * および予約期間の詳細に関する情報をカプセル化します。
 * <p>
 *
 *フィールド:<br>
 * - pc_id: 予約をチェックする PC の一意の識別子<br>
 * - localDate: 予約をチェックする特定の日付<br>
 * - otherPurpose: 予約のオプションの説明または目的<br>
 * - periodGroups: 予約期間のグループ化に関するブロック情報を表すバイト値を含むネストされたリスト<br>
 * - startTimes: 予約期間の開始時刻を示す文字列のリスト<br>
 * - endTimes: 予約期間の終了時刻を示す文字列のリスト
 */
public record ReservationCheckDTO(
    int pc_id,
    LocalDate localDate,
    String otherPurpose,
    List<List<Byte>> periodGroups,
    List<String> startTimes,
    List<String> endTimes
) { }
