package academy.cardoso.springboot.controller;

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
import org.springframework.test.context.junit.jupiter.SpringExtension;

import academy.cardoso.springboot.domain.Anime;
import academy.cardoso.springboot.service.AnimeService;
import academy.cardoso.springboot.util.AnimeCreator;

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
    
}
