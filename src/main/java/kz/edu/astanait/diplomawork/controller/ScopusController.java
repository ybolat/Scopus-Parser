package kz.edu.astanait.diplomawork.controller;

import kz.edu.astanait.diplomawork.service.ParseScopusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/v1/scopus")
public class ScopusController {

    private final ParseScopusService parseScopusService;

    @Autowired
    public ScopusController(ParseScopusService parseScopusService) {
        this.parseScopusService = parseScopusService;
    }

    @GetMapping("/get-information")
    public ResponseEntity<HashMap<String, List<String>>> getInformation(@RequestParam(name = "id") String id) throws InterruptedException {
        HashMap<String, List<String>> information = this.parseScopusService.parse(id);
        return new ResponseEntity<>(information, HttpStatus.OK);
    }

    @GetMapping("/get-doi")
    public ResponseEntity<String> getDoi(@RequestParam(name = "url") String url) throws IOException {
        String doi = this.parseScopusService.getDoi(url);
        return new ResponseEntity<>(doi, HttpStatus.OK);
    }
}
