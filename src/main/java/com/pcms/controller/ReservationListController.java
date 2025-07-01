package com.pcms.controller;

import com.pcms.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

@Controller
@RequestMapping("/reservationList")
@RequiredArgsConstructor
public class ReservationListController {

    private final ReservationService reservationService;

    /**
     * 指定された日付のPC予約状況のリストを取得して表示します。
     * 日付が指定されていない場合、または形式が無効な場合は、現在の日付がデフォルトで使用されます。
     *
     * @param model   ビューにデータを渡すために使用されるモデル属性
     * @param dateStr 予約情報が要求される日付。文字列としてフォーマットされます
     * @return 予約リストページをレンダリングするためのビューテンプレートの名前 (public/reservationList)
     */
    @GetMapping
    @PostMapping
    public String reservationList(
            Model model,
            @RequestParam(value = "date", required = false) String dateStr
    ) {

        LocalDate date;

        try {
            date = (dateStr == null || dateStr.isBlank())
                    ? LocalDate.now()
                    : LocalDate.parse(dateStr);
        } catch (DateTimeParseException e) {
            date = LocalDate.now();
        }

        model.addAttribute("date", date);
        model.addAttribute("reservationMap", reservationService.getReservation(date));
        return "public/reservationList";
    }

}
