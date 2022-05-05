package kz.edu.astanait.diplomawork.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ParseScopusService {

    public List<List<String>> parse(String id) throws NoSuchElementException {
        String url = "https://www.scopus.com/authid/detail.uri?authorId=" + id;
        System.setProperty("webdriver.chrome.driver", "selenium\\chromedriver.exe");
        WebDriver webDriver = new ChromeDriver();
        webDriver.get(url);
        Document document = Jsoup.parse(webDriver.getPageSource());

        Elements elementsAuthor = document.getElementsByClass("AuthorHeader-module__syvlN");
        List<String> author = new ArrayList<>();
        author.add(elementsAuthor.get(0).ownText());

        Elements elementsArticleTypes = document.getElementsByClass("article-type-line");
        List<String> articleTypes = new ArrayList<>();

        for (Element element : elementsArticleTypes) {
            articleTypes.add(element.ownText());
        }

        Elements elementsTitles = document.getElementsByClass("list-title");
        List<String > titles = new ArrayList<>();

        for (Element element : elementsTitles) {
            titles.add(element.ownText());
        }
        List<List<String>> scopus = new ArrayList<>();
        scopus.add(author);
        scopus.add(articleTypes);
        scopus.add(titles);

        return scopus;
    }
}
