package academy.cardoso.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import academy.cardoso.springboot.domain.Anime;

public interface AnimeRepository extends JpaRepository<Anime, Long>{
    
}
