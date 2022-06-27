package com.example.demo;

import org.springframework.stereotype.Controller;

@Controller
public class ReactRoutingForwarding {

    @RequestMapping(value = "/**/{[path:[^\\.]*}")
    public String forwardToRouteUrl() {
        return "forward:/";
    }
}
