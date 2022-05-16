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

@RestController
@RequestMapping("/api/v1/scopus")
public class ScopusController {

    private final ParseScopusService parseScopusService;

    @Autowired
    public ScopusController(ParseScopusService parseScopusService) {
        this.parseScopusService = parseScopusService;
    }

    @GetMapping("/get/author")
    public ResponseEntity<HashMap<String, String>> getInformationAboutAuthor(@RequestParam(name = "url") String url) throws InterruptedException {
        return new ResponseEntity<>(this.parseScopusService.getInformationAboutAuthor(url), HttpStatus.OK);
    }

    @GetMapping("/get/information")
    public ResponseEntity<HashMap<Integer, String>> getArticles(@RequestParam(name = "id") String id) throws InterruptedException {
        HashMap<Integer, String> result = this.parseScopusService.getArticles(id);
        if(!result.isEmpty()) return new ResponseEntity<>(result, HttpStatus.OK);
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/get-doi")
    public ResponseEntity<String> getDoi(@RequestParam(name = "url") String url) throws IOException {
        String doi = this.parseScopusService.getDoi(url);
        return new ResponseEntity<>(doi, HttpStatus.OK);
    }
}
