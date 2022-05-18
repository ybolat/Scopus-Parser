package kz.edu.astanait.diplomawork.service.serviceImpl;

import kz.edu.astanait.diplomawork.service.serviceInterface.ParseScopusService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class ParseScopusServiceImpl implements ParseScopusService {

//    private static final String url = "https://www.scopus.com/authid/detail.uri?authorId=";

    @Override
    public HashMap<String, String> getInformationAboutAuthor(String id) throws InterruptedException {
        System.setProperty("webdriver.chrome.driver", "selenium\\chromedriver.exe");
        WebDriver webDriver = new ChromeDriver();
        webDriver.get(id);
        webDriver.navigate().refresh();
        Thread.sleep(3000);

        Document document = Jsoup.parse(webDriver.getPageSource());
        HashMap<String, String> result = new HashMap<>();

        Elements orcid = document.getElementsContainingOwnText("https://orcid.org/");
        String author_orcid = orcid.get(0).ownText();

        Elements elementsAuthor = document.getElementsByClass("AuthorHeader-module__syvlN");
        String author = elementsAuthor.get(0).ownText();

        result.put(author, author_orcid);
        return result;
    }

    @Override
    public HashMap<Integer, String> getArticles(String id) throws NoSuchElementException, InterruptedException {
        System.setProperty("webdriver.chrome.driver", "selenium\\chromedriver.exe");
        WebDriver webDriver = new ChromeDriver();
        webDriver.get(id);
        webDriver.navigate().refresh();
        Thread.sleep(3000);

        int count = 1;

        Document document = Jsoup.parse(webDriver.getPageSource());
        HashMap<Integer, String> result = new HashMap<>();

        try {
            Elements numberOfPages = document.getElementsByClass("button--link-black");
            String number = numberOfPages.get(numberOfPages.size() - 2).children().get(0).ownText();
            int num = 0;

            try {
                num = Integer. parseInt(number);
            }catch (Exception e) {
                e.printStackTrace();
            }

            for (int i = 0; i < num; i++) {
                Elements elementsArticleTypes = document.getElementsByClass("article-type-line");
                List<String> articleTypes = new ArrayList<>();

                for (Element element : elementsArticleTypes) {
                    String articleType = element.ownText();
                    if (!articleType.isEmpty()) articleTypes.add(element.ownText());
                }

                Elements elementsTitles = document.getElementsByClass("list-title");
                List<String> titles = new ArrayList<>();
                List<String> hrefs = new ArrayList<>();
                List<String> doi = new ArrayList<>();

                for (Element element : elementsTitles) {
                    String title = element.ownText();
                    if (!title.isEmpty()) {
                        titles.add(title);
                        hrefs.add(element.attr("href"));
                    }
                }

                for (String href : hrefs) {
                    doi.add(this.getDoi(href));
                }

                List<String> documentAuthors = new ArrayList<>();

                Elements documentAuthorsElements = document.getElementsByClass("author-list");
                for (Element element : documentAuthorsElements) {
                    Elements elements = element.children();
                    StringBuilder docAuthors = new StringBuilder();

                    for (Element authors : elements) {
                        docAuthors.append(authors.children().get(0).ownText());
                    }
                    documentAuthors.add(docAuthors.toString());
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

                List<String> resultList = new ArrayList<>();
                for (int q = 0; q < documentAuthors.size(); q++) {
                    resultList.add(titles.get(q) + "$" + articleTypes.get(q) + "<" + doi.get(q) + ">" + documentAuthors.get(q) +
                            "{" + sources.get(q));
                }

                for (String s : resultList) {
                    result.put(count, s);
                    count += 1;
                }

                if (num != i + 1) {
                    WebElement paginationBtn = webDriver.findElement(By.xpath("//*[@id=\"scopus-author-profile-page-control-microui__documents-panel\"]/els-stack/div/div[2]/div/els-results-layout/els-paginator/nav/ul/li[4]/button"));
                    paginationBtn.click();
                    Thread.sleep(3000);
                    document = Jsoup.parse(webDriver.getPageSource());
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public String getDoi(String url) throws IOException {
        Document document = Jsoup.connect(url).get();
        Elements elements = document.getElementsByAttributeValueMatching("NAME", "dc.identifier");

        return elements.get(0).attr("content");
    }
}
