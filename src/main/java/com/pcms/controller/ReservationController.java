package com.pcms.controller;

import com.pcms.service.ReservationService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

/**
 * 予約関連の操作を処理するためのコントローラークラス。予約の表示、検証、送信のためのエンドポイントを提供します。
 */
@Controller
@RequestMapping("/reservation")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    /**
     * 指定されたPCの予約ページを、特定の日付に基づいて表示します。
     * 日付が未指定または過去の場合は、現在の日付を使用します。
     * 予約データは取得後、モデルに追加して画面に表示します。
     *
     * @param pc_id 予約の詳細を表示するPCのID。デフォルトまたは汎用の予約リストを取得する場合はnullにすることができます
     * @param dateStr 予約の詳細を表示する日付。nullまたは今日以前、日付が不正の場合は、現在の日付が使用されます
     * @param model ビューのレンダリングに必要な属性を追加するために使用される Spring モデル オブジェクト
     * @return レンダリングするビュー テンプレートの名前。具体的には「protected/reservation」です
     */
    @RequestMapping
    public String showReservation(
            @RequestParam(value = "pc_id", required = false) String pc_id,
            @RequestParam(value = "date", required = false) String dateStr,
            Model model
    ) {

        LocalDate date;

        try {
            date = dateStr == null || dateStr.isBlank()
                    ? LocalDate.now()
                    : LocalDate.parse(dateStr);
        } catch (DateTimeParseException e) {
            date = LocalDate.now();
        }
        
        model.addAttribute("date", date);
        model.addAttribute("pc_id", pc_id);

        var reservation = reservationService.getReservation(pc_id, date);
        model.addAttribute("reservation", reservation);

        return "protected/reservation";
    }

    /**
     * 指定されたパラメータに基づいて予約の詳細を確認し、少なくとも1つの時間帯が選択されているかどうかを検証します。
     * 時間帯が選択されていない場合は、モデルにエラーメッセージが追加され、ユーザーは予約ページにリダイレクトされます。
     * 選択されている場合は、予約チェックの詳細が処理され、モデルに追加されます。
     *
     * @param pc_id 予約を確認する PC の ID
     * @param date 予約を確認する日付
     * @param otherPurpose 予約の説明または理由
     * @param periods 予約に選択された時間帯（コマ）のリスト
     * @param model ビューにデータを渡すために使用される Spring モデル オブジェクト
     * @return レンダリングするビューテンプレートの名前。
     *          時間帯が選択されていない場合は「protected/reservation」を返し、
     *          検証に成功した場合は「protected/checkReservation」を返します。
     */
    @PostMapping("/check")
    public String checkReservation(
            @RequestParam("pc_id") int pc_id,
            @RequestParam("date") LocalDate date,
            @RequestParam("otherPurpose") String otherPurpose,
            @RequestParam("periods") List<Byte> periods,
            Model model
    ) {
        // 時限が1つも選択されていない場合
        if (periods == null || periods.isEmpty()) {
            model.addAttribute("pc_id", pc_id);
            model.addAttribute("date", date);
            model.addAttribute("otherPurpose", otherPurpose);
            model.addAttribute("error", "1つ以上の時間帯を選択してください");
            return "protected/reservation";
        }

        model.addAttribute("checkDTO", reservationService.checkReservation(pc_id, date, otherPurpose, periods));

        return "protected/checkReservation";
    }

    @GetMapping("/sendResult")
    public String sendResult() {

        return "protected/sendResult";

    }

    /**
     * 予約情報を送信して予約を作成し、結果ページにリダイレクトします。コマが指定されていない場合は、予約情報を検証し、予約確認ページを再表示します。
     *
     * @param model ビューに属性を渡すために使用される Spring モデル オブジェクト
     * @param session セッション属性から現在のユーザーの電子メールを取得するために使用される HttpSession オブジェクト
     * @param pc_id 予約する PC の ID
     * @param date 予約が行われる日付
     * @param otherPurpose 予約の説明または理由。
     * @param periods 予約が行われる期間（コマ）のリスト
     * @return ビューまたはリダイレクトルートの名前。コマが指定されていない場合は、予約確認ページを返します。
     *          ピリオドが指定されている場合は、予約結果ページにリダイレクトします。
     */
    @PostMapping("/sendResult")
    public String sendResult(
            Model model,
            HttpSession session,
            @RequestParam("pc_id") int pc_id,
            @RequestParam("date") LocalDate date,
            @RequestParam("otherPurpose") String otherPurpose,
            @RequestParam("periods") List<Byte> periods
    ) {

        if (periods == null || periods.isEmpty()) {
            return checkReservation(pc_id, date, otherPurpose, null, model);
        }

        String email = (String) session.getAttribute("LOGIN_EMAIL");
        reservationService.reserve(email, pc_id, date, otherPurpose, periods);

        return "redirect:/reservation/sendResult";
    }

}
