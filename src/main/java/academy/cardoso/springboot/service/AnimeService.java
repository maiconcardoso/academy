package academy.cardoso.springboot.service;

import java.util.List;

import org.springframework.stereotype.Service;

import academy.cardoso.springboot.domain.Anime;

@Service
public class AnimeService {

    public List<Anime> listAll() {
        return List.of(new Anime(1, "Boku no Hero"), new Anime(2, "Tate no Yusha no Nariagari"),
        new Anime(3, "Dragon Quest: Adventure of Dai"));
    }
    
}
