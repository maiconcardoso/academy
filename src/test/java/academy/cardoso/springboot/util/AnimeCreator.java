package academy.cardoso.springboot.util;

import academy.cardoso.springboot.domain.Anime;

public class AnimeCreator {
    
    public static Anime createAnimeToBeSaved() {
        return Anime.builder()
                .name("Psyco pass")
                .build();
    }

    public static Anime createValidAnime() {
        return Anime.builder()
                .name("Psyco pass")
                .id(1L)
                .build();
    }

    public static Anime createValidUpdatedAnime() {
        return Anime.builder()
                .name("Psyco pass Movie")
                .id(1L)
                .build();
    }
}
