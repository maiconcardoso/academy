package academy.cardoso.springboot.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import academy.cardoso.springboot.domain.Anime;
import academy.cardoso.springboot.request.AnimePostRequestBody;
import academy.cardoso.springboot.request.AnimePutRequestBody;

@Mapper(componentModel = "spring")
public abstract class AnimeMapper {

    public static final AnimeMapper INSTANCE = Mappers.getMapper(AnimeMapper.class);

    public abstract Anime toAnime(AnimePostRequestBody animePostRequestBody);

    public abstract Anime toAnime(AnimePutRequestBody animePutRequestBody);

    
}
