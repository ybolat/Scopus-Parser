package kz.edu.astanait.diplomawork.service;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class ParseScopusService {

    public HashMap<String, List<String>> parse(String id) throws NoSuchElementException, InterruptedException {
        String url = "https://www.scopus.com/authid/detail.uri?authorId=" + id;
        System.setProperty("webdriver.chrome.driver", "selenium\\chromedriver.exe");

        WebDriver webDriver = new ChromeDriver();
        webDriver.get(url);

        webDriver.navigate().refresh();
        Thread.sleep(5000);

        Document document = Jsoup.parse(webDriver.getPageSource());
        HashMap<String, List<String>> result = new HashMap<>();

        try {
            Elements orcid = document.getElementsContainingOwnText("https://orcid.org/");
            List<String> author_orcid = new ArrayList<>();

            author_orcid.add(orcid.get(0).ownText());
            result.put("orcid", author_orcid);

            Elements numberOfPages = document.getElementsByClass("button--link-black");

            String number = numberOfPages.get(numberOfPages.size() -2).children().get(0).ownText();
            int num = 0;

            try {
                num = Integer. parseInt(number);
            }catch (Exception e) {
                e.printStackTrace();
            }

            Elements elementsAuthor = document.getElementsByClass("AuthorHeader-module__syvlN");
            List<String> author = new ArrayList<>();
            author.add(elementsAuthor.get(0).ownText());

            result.put("author", author);

            for (int i = 0; i < num; i++) {
                int page = i + 1;
                Elements elementsArticleTypes = document.getElementsByClass("article-type-line");
                List<String> articleTypes = new ArrayList<>();

                for (Element element : elementsArticleTypes) {
                    articleTypes.add(element.ownText());
                }

                Elements elementsTitles = document.getElementsByClass("list-title");
                List<String> titles = new ArrayList<>();

                for (Element element : elementsTitles) {
                    titles.add(element.ownText());
//                System.out.println(element.attr("href"));
                }

                Elements documentAuthorsElements = document.getElementsByClass("author-list");
                int j = 1;
                for (Element element : documentAuthorsElements) {
                    Elements elements = element.children();
                    List<String> documentAuthors = new ArrayList<>();

                    for (Element authors : elements) {
                        documentAuthors.add(authors.children().get(0).ownText());
                    }
                    result.put("documentAuthors " + j + " " + page, documentAuthors);
                    j++;
                }

                Elements sourceElements = document.getElementsByAttributeValueMatching("data-component", "document-source");
                List<String> sources = new ArrayList<>();

                for (Element element : sourceElements) {
                    Elements elements = element.children();
                    if (!elements.get(0).ownText().isEmpty()) {
                        sources.add(elements.get(0).ownText() + " " + elements.get(1).ownText());
                    }else{
                        sources.add(elements.get(0).children().get(0).ownText() + " " + elements.get(1).ownText());
                    }
                }

                result.put("publisher " + page, sources);
                result.put("articleType " + page, articleTypes);
                result.put("titles " + page, titles);
            }

            return result;
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
