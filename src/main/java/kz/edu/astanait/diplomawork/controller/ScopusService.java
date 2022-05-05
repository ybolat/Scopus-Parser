package kz.edu.astanait.diplomawork.controller;

import kz.edu.astanait.diplomawork.service.ParseScopusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/scopus")
public class ScopusService {

    private final ParseScopusService parseScopusService;

    @Autowired
    public ScopusService(ParseScopusService parseScopusService) {
        this.parseScopusService = parseScopusService;
    }

    @GetMapping("/getInformation")
    public ResponseEntity<List<List<String>>> getInformation(@RequestParam(name = "id") String id) {
        List<List<String>> information = this.parseScopusService.parse(id);
        return new ResponseEntity<>(information, HttpStatus.OK);
    }
}
