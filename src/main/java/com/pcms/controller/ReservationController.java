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

@Controller
@RequestMapping("/reservation")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping
    public String showReservation(
            @RequestParam("pc_id") String pc_id,
            @RequestParam("date") LocalDate date,
            Model model
    ) {
        if (date == null || date.isBefore(LocalDate.now())) {
            date = LocalDate.now();
        }

        var reservationList = reservationService.getReservation(pc_id, date);
        model.addAttribute("reservationList", reservationList);

        return "protected/reservation";
    }

}
