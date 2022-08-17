package academy.cardoso.springboot.util;

import academy.cardoso.springboot.request.AnimePostRequestBody;

public class AnimePostRequestBodyCreator {
    
    public static AnimePostRequestBody createAnimePostRequestBody() {
        return AnimePostRequestBody.builder()
                .name(AnimeCreator.createValidAnime().getName())
                .build();
    }
}
