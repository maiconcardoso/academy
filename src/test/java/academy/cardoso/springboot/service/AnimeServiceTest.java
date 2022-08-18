package academy.cardoso.springboot.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

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
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import academy.cardoso.springboot.domain.Anime;
import academy.cardoso.springboot.exceptions.BadRequestException;
import academy.cardoso.springboot.repository.AnimeRepository;
import academy.cardoso.springboot.util.AnimeCreator;
import academy.cardoso.springboot.util.AnimePostRequestBodyCreator;
import academy.cardoso.springboot.util.AnimePutRequestBodyCreator;

@ExtendWith(SpringExtension.class) // Usado para testar a aplicação sem executa-la.
public class AnimeServiceTest {

    @InjectMocks // Usado para injetar a dependência da classe ou interface que será testada.
    private AnimeService animeService;

    @Mock // Usado para injetar a dependência que está sendo usada dentro da classe que será testada.
    private AnimeRepository animeRepositoryMock;

    @BeforeEach
    void setUp() {
        PageImpl<Anime> animePage = new PageImpl<>(List.of(AnimeCreator.createValidAnime()));

        BDDMockito.when(this.animeRepositoryMock.findAll(ArgumentMatchers.any(PageRequest.class)))
            .thenReturn(animePage); 

        BDDMockito.when(this.animeRepositoryMock.findAll()) // faço a chamada do método;
            .thenReturn(List.of(AnimeCreator.createValidAnime())); // especifico o tipo de retorno.

        BDDMockito.when(this.animeRepositoryMock.findById(ArgumentMatchers.anyLong()))
            .thenReturn(Optional.of(AnimeCreator.createValidAnime()));

        BDDMockito.when(this.animeRepositoryMock.findByName(ArgumentMatchers.anyString()))
            .thenReturn(List.of(AnimeCreator.createValidAnime()));
        
        BDDMockito.when(this.animeRepositoryMock.save(ArgumentMatchers.any()))
            .thenReturn(AnimeCreator.createValidAnime());

        BDDMockito.doNothing().when(this.animeRepositoryMock).delete(ArgumentMatchers.any());
    }

    @Test
    @DisplayName("findAll returns of animes inside page object when successful")
    public void findAll_ReturnsListOfAnimesInsidePageObject_WhenSuccessful() {
        String expectedName = AnimeCreator.createValidAnime().getName();
        Page<Anime> animePage = animeService.findAll(PageRequest.of(1, 1));

        Assertions.assertThat(animePage).isNotNull();
        Assertions.assertThat(animePage.toList())
            .isNotEmpty()
            .hasSize(1);
        Assertions.assertThat(animePage.toList().get(0).getName()).isEqualTo(expectedName);

    }

    @Test
    @DisplayName("findAllNoPageable returns of animes when successful")
    public void findAllNoPageable_returnsOfAnimes_WhenSuccessful() {
        String expectedName = AnimeCreator.createValidAnime().getName();

        List<Anime> animes = animeService.findAllNoPageable();

        Assertions.assertThat(animes)
            .isNotNull()
            .isNotEmpty()
            .hasSize(1);

        Assertions.assertThat(animes.get(0).getName()).isEqualTo(expectedName);
    }

    @Test
    @DisplayName("findByIdOrElseThrowException return of anime when successful")
    public void findByIdOrElseThrowException_ReturnsOfAnime_WhenSuccessful() {
        Long expectedId = AnimeCreator.createValidAnime().getId();

        Anime anime = animeService.findByIdOrElseThrowException(1L);

        Assertions.assertThat(anime).isNotNull();

        Assertions.assertThat(anime.getId()).isEqualTo(expectedId);
    }

    @Test
    @DisplayName("findByIdOrElseThrowException throws BadRequestException when anime is not found")
    public void findByIdOrElseThrowException_ThrowsBadRequestException_WhenAnimeIsNotFound() {
        BDDMockito.when(this.animeRepositoryMock.findById(ArgumentMatchers.anyLong())) 
            .thenReturn(Optional.empty());

        Assertions.assertThatExceptionOfType(BadRequestException.class)
            .isThrownBy(() -> animeService.findByIdOrElseThrowException(1L));
        
    }

    @Test
    @DisplayName("findByName return a list of anime when successful")
    public void findByName_ReturnListOfAnime_WhenSuccessful() {
        String expectedName = AnimeCreator.createValidAnime().getName();

        List<Anime> animes = animeService.findByName("qualquer nome");

        Assertions.assertThat(animes) 
            .isNotEmpty()
            .isNotNull()
            .hasSize(1);

        Assertions.assertThat(animes.get(0).getName()).isEqualTo(expectedName);
    }

    @Test
    @DisplayName("findByName returns an empty list of anime when is not found")
    public void findByName_ReturnsEmptyListOfAnime_WhenIsNotFound() {
        BDDMockito.when(this.animeRepositoryMock.findByName(ArgumentMatchers.anyString()))
            .thenReturn(Collections.emptyList());

        List<Anime> animes = animeService.findByName("qualquer nome");
        
        Assertions.assertThat(animes)
            .isNotNull()
            .isEmpty();
    }

    @Test
    @DisplayName("save return anime when successful")
    public void save_ReturnsAnime_WhenSuccessful() {
        Anime anime = animeService.save(AnimePostRequestBodyCreator.createAnimePostRequestBody());

        Assertions.assertThat(anime) 
            .isNotNull()
            .isEqualTo(AnimeCreator.createValidAnime());
    }
    
    @Test
    @DisplayName("replace updates anime when successful")
    public void replace_UpdatesAnime_whenSuccessful() {
        Assertions.assertThatCode(() -> this.animeService.replace(AnimePutRequestBodyCreator.createAnimePutRequestBody()))
            .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("delete removes anime when successful")
    public void delete_RemovesAnime_WhenSuccessful() {
        Assertions.assertThatCode(() -> this.animeService.delete(1L))
            .doesNotThrowAnyException();;
    }
}
