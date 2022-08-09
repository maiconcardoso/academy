package academy.cardoso.springboot.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import academy.cardoso.springboot.domain.Anime;

public interface AnimeRepository extends JpaRepository<Anime, Long>{

    List<Anime> findByName(String name);
    
}
