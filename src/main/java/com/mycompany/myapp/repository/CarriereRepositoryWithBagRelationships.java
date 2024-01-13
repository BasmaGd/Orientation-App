package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Carriere;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface CarriereRepositoryWithBagRelationships {
    Optional<Carriere> fetchBagRelationships(Optional<Carriere> carriere);

    List<Carriere> fetchBagRelationships(List<Carriere> carrieres);

    Page<Carriere> fetchBagRelationships(Page<Carriere> carrieres);
}
