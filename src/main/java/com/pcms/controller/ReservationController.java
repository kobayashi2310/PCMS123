package com.pcms.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@Controller
@RequestMapping("/reservation")
public class ReservationController {

    public String showReservation(
            @RequestParam("pc_id") Integer pc_id,
            @RequestParam("date") LocalDate date,
            Model model) {


        return "protected/reservation";
    }

}
