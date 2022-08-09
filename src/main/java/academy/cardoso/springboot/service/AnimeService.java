package academy.cardoso.springboot.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import academy.cardoso.springboot.domain.Anime;
import academy.cardoso.springboot.repository.AnimeRepository;
import academy.cardoso.springboot.request.AnimePostRequestBody;
import academy.cardoso.springboot.request.AnimePutRequestBody;

@Service
public class AnimeService {

    @Autowired
    private AnimeRepository animeRepository;

    public List<Anime> findAll() {
        return animeRepository.findAll();
    }

    public Anime findByIdOrElseThrowException(Long id) {
        return animeRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Anime id not Found"));
    }

    public Anime save(AnimePostRequestBody animePostRequestBody) {
        Anime animeSaved = Anime.builder().name(animePostRequestBody.getName()).build();
        return animeRepository.save(animeSaved);
    }

    public void delete(Long id) {
        findByIdOrElseThrowException(id);
        animeRepository.deleteById(id);
    }

    public void replace(AnimePutRequestBody animePutRequestBody) {
        findByIdOrElseThrowException(animePutRequestBody.getId());
        Anime animeReplace = Anime.builder()
            .id(animePutRequestBody.getId())
            .name(animePutRequestBody.getName())
            .build();
        animeRepository.save(animeReplace);
    }

}
