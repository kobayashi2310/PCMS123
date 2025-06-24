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
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/reservation")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping
    public String showReservation(
            @RequestParam(value = "pc_id", required = false) String pc_id,
            @RequestParam(value = "date", required = false) LocalDate date,
            Model model
    ) {

        if (date == null || date.isBefore(LocalDate.now())) {
            date = LocalDate.now();
        }
        
        model.addAttribute("date", date);
        model.addAttribute("pc_id", pc_id);

        var reservation = reservationService.getReservation(pc_id, date);
        model.addAttribute("reservation", reservation);

        return "protected/reservation";
    }

    @PostMapping("/check")
    public String checkReservation(
            @RequestParam("pc_id") String pc_id,
            @RequestParam("date") LocalDate date,
            @RequestParam("otherPurpose") String otherPurpose,
            @RequestParam("periods") List<String> periods,
            Model model
    ) {

        model.addAttribute("checkDTO", reservationService.checkReservation(pc_id, date, otherPurpose, periods));

        return "protected/checkReservation";
    }

}
