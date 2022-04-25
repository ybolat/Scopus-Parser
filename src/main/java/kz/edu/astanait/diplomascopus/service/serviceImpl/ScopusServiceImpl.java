package kz.edu.astanait.diplomascopus.service.serviceImpl;

import kz.edu.astanait.diplomascopus.model.Scopus;
import kz.edu.astanait.diplomascopus.repository.ScopusRepository;
import kz.edu.astanait.diplomascopus.service.serviceInterface.ScopusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScopusServiceImpl implements ScopusService {

    private final ScopusRepository scopusRepository;

    @Autowired
    public ScopusServiceImpl(ScopusRepository scopusRepository) {
        this.scopusRepository = scopusRepository;
    }

    @Override
    public void save(Scopus scopus) {
        this.scopusRepository.save(scopus);
    }

    @Override
    public boolean isExist(String scopusTitle) {
        List<Scopus> scopusList = this.scopusRepository.findAll();

        for (Scopus scopus : scopusList) {
            if (scopus.getScopusTitle().equals(scopusTitle)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public List<Scopus> getAllScopus() {
        return this.scopusRepository.findAll();
    }
}
