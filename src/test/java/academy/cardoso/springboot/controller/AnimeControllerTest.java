package academy.cardoso.springboot.controller;

import java.util.Collections;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import academy.cardoso.springboot.domain.Anime;
import academy.cardoso.springboot.request.AnimePostRequestBody;
import academy.cardoso.springboot.request.AnimePutRequestBody;
import academy.cardoso.springboot.service.AnimeService;
import academy.cardoso.springboot.util.AnimeCreator;
import academy.cardoso.springboot.util.AnimePostRequestBodyCreator;
import academy.cardoso.springboot.util.AnimePutRequestBodyCreator;

@ExtendWith(SpringExtension.class) // Usado para testar a aplicação sem executa-la.
public class AnimeControllerTest {

    @InjectMocks // Usado para injetar a dependência da classe ou interface que será testada.
    private AnimeController animeController;

    @Mock // Usado para injetar a dependência que está sendo usada dentro da classe que será testada.
    private AnimeService animeServiceMock;

    @BeforeEach
    void setUp() {
        PageImpl<Anime> animePage = new PageImpl<>(List.of(AnimeCreator.createValidAnime()));

        BDDMockito.when(this.animeServiceMock.findAll(ArgumentMatchers.any()))
            .thenReturn(animePage); 

        BDDMockito.when(this.animeServiceMock.findAllNoPageable())
            .thenReturn(List.of(AnimeCreator.createValidAnime()));

        BDDMockito.when(this.animeServiceMock.findByIdOrElseThrowException(ArgumentMatchers.anyLong()))
            .thenReturn(AnimeCreator.createValidAnime());

        BDDMockito.when(this.animeServiceMock.findByName(ArgumentMatchers.anyString()))
            .thenReturn(List.of(AnimeCreator.createValidAnime()));
        
        BDDMockito.when(this.animeServiceMock.save(ArgumentMatchers.any(AnimePostRequestBody.class)))
            .thenReturn(AnimeCreator.createValidAnime());

        BDDMockito.doNothing().when(this.animeServiceMock).replace(ArgumentMatchers.any(AnimePutRequestBody.class));

        BDDMockito.doNothing().when(this.animeServiceMock).delete(ArgumentMatchers.anyLong());
    }

    @Test
    @DisplayName("List returns of animes inside page object when successful")
    public void list_ReturnsListOfAnimesInsidePageObject_WhenSuccessful() {
        String expectedName = AnimeCreator.createValidAnime().getName();
        Page<Anime> animePage = animeController.list(null).getBody();

        Assertions.assertThat(animePage).isNotNull();
        Assertions.assertThat(animePage.toList())
            .isNotEmpty()
            .hasSize(1);
        Assertions.assertThat(animePage.toList().get(0).getName()).isEqualTo(expectedName);

    }

    @Test
    @DisplayName("ListAll returns of animes when successful")
    public void listAll_returnsOfAnimes_WhenSuccessful() {
        String expectedName = AnimeCreator.createValidAnime().getName();

        List<Anime> animes = animeController.listAllNoPageable().getBody();

        Assertions.assertThat(animes)
            .isNotNull()
            .isNotEmpty()
            .hasSize(1);

        Assertions.assertThat(animes.get(0).getName()).isEqualTo(expectedName);
    }

    @Test
    @DisplayName("findById return of anime when successful")
    public void findById_ReturnsOfAnime_WhenSuccessful() {
        Long expectedId = AnimeCreator.createValidAnime().getId();

        Anime anime = animeController.findById(1L).getBody();

        Assertions.assertThat(anime).isNotNull();

        Assertions.assertThat(anime.getId()).isEqualTo(expectedId);
    }

    @Test
    @DisplayName("findByName return a list of anime when successful")
    public void findByName_ReturnListOfAnime_WhenSuccessful() {
        String expectedName = AnimeCreator.createValidAnime().getName();

        List<Anime> animes = animeController.findByName("qualquer nome").getBody();

        Assertions.assertThat(animes) 
            .isNotEmpty()
            .isNotNull()
            .hasSize(1);

        Assertions.assertThat(animes.get(0).getName()).isEqualTo(expectedName);
    }

    @Test
    @DisplayName("findByName returns an empty list of anime when is not found")
    public void findByName_ReturnsEmptyListOfAnime_WhenIsNotFound() {
        BDDMockito.when(this.animeServiceMock.findByName(ArgumentMatchers.anyString()))
            .thenReturn(Collections.emptyList());

        List<Anime> animes = animeController.findByName("qualquer nome").getBody();
        
        Assertions.assertThat(animes)
            .isNotNull()
            .isEmpty();
    }

    @Test
    @DisplayName("save return anime when successful")
    public void save_ReturnsAnime_WhenSuccessful() {
        Anime anime = animeController.save(AnimePostRequestBodyCreator.createAnimePostRequestBody()).getBody();

        Assertions.assertThat(anime) 
            .isNotNull()
            .isEqualTo(AnimeCreator.createValidAnime());
    }
    
    @Test
    @DisplayName("replace updates anime when successful")
    public void replace_UpdatesAnime_whenSuccessful() {
        Assertions.assertThatCode(() -> this.animeController.replace(AnimePutRequestBodyCreator.createAnimePutRequestBody()))
            .doesNotThrowAnyException();

        ResponseEntity<Anime> entity = this.animeController.replace(AnimePutRequestBodyCreator.createAnimePutRequestBody());

        Assertions.assertThat(entity).isNotNull();

        Assertions.assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    @DisplayName("delete removes anime when successful")
    public void delete_RemovesAnime_WhenSuccessful() {
        Assertions.assertThatCode(() -> this.animeController.delete(1L))
            .doesNotThrowAnyException();;

        ResponseEntity<Void> entity = this.animeController.delete(1L);

        Assertions.assertThat(entity).isNotNull();

        Assertions.assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }
}
