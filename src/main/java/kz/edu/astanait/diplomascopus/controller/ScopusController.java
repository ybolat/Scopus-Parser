package kz.edu.astanait.diplomascopus.controller;

import kz.edu.astanait.diplomascopus.model.Scopus;
import kz.edu.astanait.diplomascopus.service.serviceInterface.ScopusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ScopusController {

    private final ScopusService scopusService;

    @Autowired
    public ScopusController(ScopusService scopusService) {
        this.scopusService = scopusService;
    }

    @GetMapping("/scopus")
    public List<Scopus> getAllScopus() {
        return this.scopusService.getAllScopus();
    }
}
