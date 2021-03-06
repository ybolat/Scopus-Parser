package kz.edu.astanait.diplomawork.controller;

import kz.edu.astanait.diplomawork.service.serviceImpl.ParseScopusServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("/api/v1/scopus")
public class ScopusController {

    private final ParseScopusServiceImpl parseScopusService;

    @Autowired
    public ScopusController(ParseScopusServiceImpl parseScopusService) {
        this.parseScopusService = parseScopusService;
    }

    @GetMapping("/get/author")
    public ResponseEntity<HashMap<String, String>> getInformationAboutAuthor(@RequestParam(name = "id") String id) throws InterruptedException {
        return new ResponseEntity<>(this.parseScopusService.getInformationAboutAuthor(id), HttpStatus.OK);
    }

    @GetMapping("/get/information")
    public ResponseEntity<HashMap<Integer, String>> getArticles(@RequestParam(name = "id") String id) throws InterruptedException {
        HashMap<Integer, String> result = this.parseScopusService.getArticles(id);
        if(!result.isEmpty()) return new ResponseEntity<>(result, HttpStatus.OK);
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
