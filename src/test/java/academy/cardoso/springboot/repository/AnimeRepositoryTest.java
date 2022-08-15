package academy.cardoso.springboot.repository;

import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import academy.cardoso.springboot.domain.Anime;

@DataJpaTest
@DisplayName("Tests for anime repository")
public class AnimeRepositoryTest {

    @Autowired
    private AnimeRepository animeRepository;

    @Test
    @DisplayName("Save creates anime when succesful")
    public void save_PersistAnime_WhenSuccessful() {
        Anime animeToBeSaved = createAnime();
        Anime animeSaved = animeRepository.save(animeToBeSaved);
        Assertions.assertThat(animeSaved).isNotNull();
        Assertions.assertThat(animeSaved.getId()).isNotNull();
        Assertions.assertThat(animeSaved.getName()).isEqualTo(animeToBeSaved.getName());
    }

    @Test
    @DisplayName("Save updated anime when succesful")
    public void update_PersistAnime_WhenSuccesful() {
        Anime animeToBeSaved = createAnime();
        Anime animeSaved = animeRepository.save(animeToBeSaved);
        animeSaved.setName("Akira");
        Anime animeUpdated = this.animeRepository.save(animeSaved);
        Assertions.assertThat(animeUpdated).isNotNull();
        Assertions.assertThat(animeUpdated.getName()).isNotNull();
        Assertions.assertThat(animeUpdated.getName()).isEqualTo(animeSaved.getName());
        Assertions.assertThat(animeUpdated.getName()).isNotEqualTo(animeToBeSaved);
    }

    @Test
    @DisplayName("Delete removes anime when succesful")
    public void delete_RemovesAnime_WhenSuccesful() {
        Anime animeToBeSaved = createAnime();
        Anime animeSaved = animeRepository.save(animeToBeSaved);
        animeRepository.delete(animeSaved);
        Optional<Anime> animeOptional = animeRepository.findById(animeSaved.getId());
        Assertions.assertThat(animeOptional).isEmpty();
    }

    @Test
    @DisplayName("Find By Name returns list of animes when successful")
    public void findByName_ReturnsListOfAnime_WhenSuccessful() {
        Anime animeToBeSaved = createAnime();
        Anime animeSaved = animeRepository.save(animeToBeSaved);
        String name = animeSaved.getName();
        List<Anime> animes = animeRepository.findByName(name);
        Assertions.assertThat(animes)
                .isNotEmpty()
                .contains(animeSaved);
    }

    @Test
    @DisplayName("Find By Name returns empty list of animes when successful")
    public void findByName_ReturnsEmpytListOfAnime_WhenSuccessful() {
        List<Anime> animeListEmpty = animeRepository.findByName("animes test");
        Assertions.assertThat(animeListEmpty).isEmpty();
    }

    private Anime createAnime() {
        return Anime.builder()
                .name("Psyco pass")
                .build();
    }

}
