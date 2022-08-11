package academy.cardoso.springboot.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import academy.cardoso.springboot.domain.Anime;
import academy.cardoso.springboot.exceptions.BadRequestException;
import academy.cardoso.springboot.mappers.AnimeMapper;
import academy.cardoso.springboot.repository.AnimeRepository;
import academy.cardoso.springboot.request.AnimePostRequestBody;
import academy.cardoso.springboot.request.AnimePutRequestBody;

@Service
public class AnimeService {

    @Autowired
    private AnimeRepository animeRepository;

    // public List<Anime> findAll() {
    //     return animeRepository.findAll();
    // }

    public Page<Anime> findAll(Pageable pageable) {
        return animeRepository.findAll(pageable);

    }

    public Anime findByIdOrElseThrowException(Long id) {
        return animeRepository.findById(id)
                .orElseThrow(() -> new BadRequestException(BadRequestException.MESSAGE));
    }

    public List<Anime> AnimeByName(String name) {
        return animeRepository.findByName(name);
    }

    @Transactional // Em caso de exceções do tipo unchecked o transactional impedirá a finalização do método.
    public Anime save(AnimePostRequestBody animePostRequestBody) {
        Anime animeSaved = AnimeMapper.INSTANCE.toAnime(animePostRequestBody);
        return animeRepository.save(animeSaved);
    }

    public void delete(Long id) {
        findByIdOrElseThrowException(id);
        animeRepository.deleteById(id);
    }

    public void replace(AnimePutRequestBody animePutRequestBody) {
        Anime animeById = findByIdOrElseThrowException(animePutRequestBody.getId());
        Anime animeReplace = AnimeMapper.INSTANCE.toAnime(animePutRequestBody);
        animeReplace.setId(animeById.getId());
        animeRepository.save(animeReplace);
    }

}
