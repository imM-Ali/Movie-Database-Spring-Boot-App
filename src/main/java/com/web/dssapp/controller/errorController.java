package com.web.dssapp.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class errorController implements ErrorController {

	@RequestMapping("/error")
    public String showErorPage() {       
        return "error";
    }
}
