package kz.edu.astanait.diplomascopus.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "scopus")
@Data
public class Scopus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "scopus_title")
    private String scopusTitle;
}
