package kz.edu.astanait.diplomascopus.repository;

import kz.edu.astanait.diplomascopus.model.Scopus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScopusRepository extends JpaRepository<Scopus, Long> {
}
