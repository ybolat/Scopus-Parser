package kz.edu.astanait.diplomawork.service.serviceInterface;

import org.openqa.selenium.NoSuchElementException;

import java.util.HashMap;

public interface ParseScopusService {

    HashMap<String, String> getInformationAboutAuthor(String id) throws InterruptedException;

    HashMap<Integer, String> getArticles(String id) throws NoSuchElementException, InterruptedException;
}
