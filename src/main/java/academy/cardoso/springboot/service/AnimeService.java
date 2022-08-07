package academy.cardoso.springboot.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import academy.cardoso.springboot.domain.Anime;

@Service
public class AnimeService {

    private List<Anime> anime = List.of(new Anime(1, "Boku no Hero"), new Anime(2, "Tate no Yusha no Nariagari"),
    new Anime(3, "Dragon Quest: Adventure of Dai"));

    public List<Anime> listAll() {
        return anime;
    }

    public Anime findById(Integer id) {
        return anime.stream()
                .filter(anime -> anime.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Anime id not found"));
    }
    
}
