package org.vitaliistf.blocktracker.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PagesController {

    @GetMapping("/")
    public String getLandingPage() {
        return "index";
    }

    @GetMapping("/coming_soon")
    public String getComingSoonPage() {
        return "coming-soon";
    }

    @GetMapping("/portfolios")
    public String getPortfoliosPage() {
        return "portfolios";
    }

    @GetMapping("/help")
    public String getHelpPage() {
        return "help";
    }

    @GetMapping("/watchlist")
    public String getWatchlistPage() {
        return "watchlist";
    }

}
