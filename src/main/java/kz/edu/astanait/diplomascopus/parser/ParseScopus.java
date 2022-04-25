package kz.edu.astanait.diplomascopus.parser;

import kz.edu.astanait.diplomascopus.model.Scopus;
import kz.edu.astanait.diplomascopus.service.serviceInterface.ScopusService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class ParseScopus {

    private final ScopusService scopusService;

    @Autowired
    public ParseScopus(ScopusService scopusService) {
        this.scopusService = scopusService;
    }

    @Scheduled(fixedDelay = 10000)
    public void parse() {
        String url = "https://www.scopus.com/authid/detail.uri?authorId=56522634900";

        try {
            Document document = Jsoup.connect(url)
                    .userAgent("Chrome")
                    .timeout(5000)
                    .referrer("https://google.com")
                    .get();
            Elements elements = document.getElementsByClass("checkbox-label");
            List<Scopus> scopusList = new ArrayList<>();
            for (Element element : elements) {
                String title = element.ownText();
                if (!this.scopusService.isExist(title)) {
                    Scopus scopus = new Scopus();
                    scopus.setScopusTitle(title);
                    scopusList.add(scopus);
                }
            }
            if (!scopusList.isEmpty()) {
                this.scopusService.save(scopusList);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
