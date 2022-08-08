package academy.cardoso.springboot.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import academy.cardoso.springboot.domain.Anime;

@Service
public class AnimeService {

    private static List<Anime> animesList;

    static {
        animesList = new ArrayList<>(List.of(new Anime(1L, "Boku no Hero"), new Anime(2L, "Tate no Yusha no Nariagari"),
        new Anime(3L, "Dragon Quest: Adventure of Dai"))) ;
    }


    public List<Anime> listAll() {
        return animesList;
    }

    public Anime findById(Long id) {
        return animesList.stream()
                .filter(anime -> anime.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Anime id not found"));
    }

    public Anime save(Anime anime) {
        anime.setId(ThreadLocalRandom.current().nextLong(4, 100000));
        animesList.add(anime);
        return anime;
    }

    public void delete(Long id) {
        animesList.remove(findById(id));
    }
    
}
