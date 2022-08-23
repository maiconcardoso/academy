package academy.cardoso.springboot.integration;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import academy.cardoso.springboot.domain.Anime;
import academy.cardoso.springboot.repository.AnimeRepository;
import academy.cardoso.springboot.request.AnimePostRequestBody;
import academy.cardoso.springboot.util.AnimeCreator;
import academy.cardoso.springboot.util.AnimePostRequestBodyCreator;
import academy.cardoso.springboot.wrapper.PageableResponse;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class AnimeControllerIntegration {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private AnimeRepository animeRepository;

    @LocalServerPort
    private int port;

    @Test
    @DisplayName("findAll returns of animes inside page object when successful")
    public void findAll_ReturnsListOfAnimesInsidePageObject_WhenSuccessful() {

        Anime animeSaved = animeRepository.save(AnimeCreator.createAnimeToBeSaved());

        String expectedName = animeSaved.getName();

        PageableResponse<Anime> animePage = testRestTemplate.exchange("/animes", HttpMethod.GET, null,
                new ParameterizedTypeReference<PageableResponse<Anime>>() {
                }).getBody();

        Assertions.assertThat(animePage).isNotNull();
        Assertions.assertThat(animePage.toList())
                .isNotEmpty()
                .hasSize(1);
        Assertions.assertThat(animePage.toList().get(0).getName()).isEqualTo(expectedName);

    }

    @Test
    @DisplayName("ListAll returns of animes when successful")
    public void listAll_returnsOfAnimes_WhenSuccessful() {

        Anime animeSaved = animeRepository.save(AnimeCreator.createAnimeToBeSaved());

        String expectedName = animeSaved.getName();

        List<Anime> animes = testRestTemplate.exchange("/animes/all", HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Anime>>() {
                }).getBody();

        Assertions.assertThat(animes)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);

        Assertions.assertThat(animes.get(0).getName()).isEqualTo(expectedName);
    }

    @Test
    @DisplayName("findById return of anime when successful")
    public void findById_ReturnsOfAnime_WhenSuccessful() {
        Anime animeSaved = animeRepository.save(AnimeCreator.createAnimeToBeSaved());

        Long expectedId = animeSaved.getId();

        Anime anime = testRestTemplate.getForObject("/animes/{id}", Anime.class, expectedId);

        Assertions.assertThat(anime).isNotNull();

        Assertions.assertThat(anime.getId()).isEqualTo(expectedId);
    }

    @Test
    @DisplayName("findByName return a list of anime when successful")
    public void findByName_ReturnListOfAnime_WhenSuccessful() {
        Anime animeSaved = animeRepository.save(AnimeCreator.createAnimeToBeSaved());

        String expectedName = animeSaved.getName();

        String url = String.format("/animes/find?name=%s", expectedName);

        List<Anime> animes = testRestTemplate
                .exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<Anime>>() {
                }).getBody();

        Assertions.assertThat(animes)
                .isNotEmpty()
                .isNotNull()
                .hasSize(1);

        Assertions.assertThat(animes.get(0).getName()).isEqualTo(expectedName);
    }

    @Test
    @DisplayName("findByName returns an empty list of anime when is not found")
    public void findByName_ReturnsEmptyListOfAnime_WhenIsNotFound() {

        List<Anime> animes = testRestTemplate
                .exchange("/animes/find?name=dbz2", HttpMethod.GET, null,
                        new ParameterizedTypeReference<List<Anime>>() {
                        })
                .getBody();

        Assertions.assertThat(animes)
                .isNotNull()
                .isEmpty();
    }

    @Test
    @DisplayName("save return anime when successful")
    public void save_ReturnsAnime_WhenSuccessful() {
        AnimePostRequestBody animePostRequestBody = AnimePostRequestBodyCreator.createAnimePostRequestBody();
        ResponseEntity<Anime> animeResponseEntity = testRestTemplate.postForEntity("/animes", animePostRequestBody,
                Anime.class);

        Assertions.assertThat(animeResponseEntity).isNotNull();
        Assertions.assertThat(animeResponseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        Assertions.assertThat(animeResponseEntity.getBody().getId()).isNotNull();

    }

    @Test
    @DisplayName("replace updates anime when successful")
    public void replace_UpdatesAnime_whenSuccessful() {
        Anime animeSaved = animeRepository.save(AnimeCreator.createAnimeToBeSaved());
        animeSaved.setName("nome de anime alterado");
        ResponseEntity<Anime> animeResponseEntity = testRestTemplate.exchange("/animes", HttpMethod.PUT,
                new HttpEntity<>(animeSaved), Anime.class);

        Assertions.assertThat(animeResponseEntity).isNotNull();
        Assertions.assertThat(animeResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

    }

    @Test
    @DisplayName("delete removes anime when successful")
    public void delete_RemovesAnime_WhenSuccessful() {
        Anime animeSaved = animeRepository.save(AnimeCreator.createAnimeToBeSaved());

        ResponseEntity<Void> animeResponseEntity = testRestTemplate.exchange("/animes/{id}", HttpMethod.DELETE,
                null, Void.class, animeSaved.getId());

        Assertions.assertThat(animeResponseEntity).isNotNull();
        Assertions.assertThat(animeResponseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

    }
}
