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
@RequestMapping("/reservationList")
@RequiredArgsConstructor
public class ReservationListController {

    private final ReservationService reservationService;

    @GetMapping
    @PostMapping
    public String reservationList(Model model, @RequestParam(required = false) LocalDate date) {
        if (date == null) {
            date = LocalDate.now();
        }
        model.addAttribute("date", date);
        model.addAttribute("reservationMap", reservationService.getReservationMap(date));
        return "public/reservationList";
    }

}
