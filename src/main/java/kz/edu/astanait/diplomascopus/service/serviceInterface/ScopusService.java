package kz.edu.astanait.diplomascopus.service.serviceInterface;

import kz.edu.astanait.diplomascopus.model.Scopus;

import java.util.List;

public interface ScopusService {

    void save(Scopus scopus);

    boolean isExist(String scopusTitle);

    List<Scopus> getAllScopus();
}
