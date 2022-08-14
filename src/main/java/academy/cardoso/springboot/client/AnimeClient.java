package academy.cardoso.springboot.client;

import java.util.Arrays;
import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import academy.cardoso.springboot.domain.Anime;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class AnimeClient {
        public static void main(String[] args) {
                ResponseEntity<Anime> entity = new RestTemplate().getForEntity("http://localhost:8080/animes/{id}",
                                Anime.class,
                                2);
                log.info(entity);

                Anime object = new RestTemplate().getForObject("http://localhost:8080/animes/{id}", Anime.class, 2);
                log.info(object);

                Anime[] arrayAnimes = new RestTemplate().getForObject("http://localhost:8080/animes/all",
                                Anime[].class);
                log.info(Arrays.toString(arrayAnimes));

                ResponseEntity<List<Anime>> exchange = new RestTemplate().exchange("http://localhost:8080/animes/all",
                                HttpMethod.GET, null, new ParameterizedTypeReference<List<Anime>>() {
                                });
                log.info(exchange.getBody());

                // Anime kingdom = Anime.builder().name("Kingdom").build();
                // Anime animeSaved = new
                // RestTemplate().postForObject("http://localhost:8080/animes/", kingdom,
                // Anime.class);
                // log.info(animeSaved);

                Anime talesOfArise = Anime.builder().name("Tales Of Arise").build();
                ResponseEntity<Anime> tales = new RestTemplate().exchange("http://localhost:8080/animes/",
                                HttpMethod.POST,
                                new HttpEntity<>(talesOfArise), Anime.class);
                log.info("Anime saved {}", tales);

                Anime animeToBeUpdated = tales.getBody();
                animeToBeUpdated.setName("Tales Of Arise Deluxe Edition");

                ResponseEntity<Void> animeUpdated = new RestTemplate().exchange("http://localhost:8080/animes/",
                                HttpMethod.PUT, new HttpEntity<>(animeToBeUpdated), Void.class);
                log.info(animeUpdated);

                Anime animeToBeDeleted = tales.getBody();

                ResponseEntity<Void> animeDeleted = new RestTemplate().exchange("http://localhost:8080/animes/{id}",
                                HttpMethod.DELETE, null, Void.class, animeToBeDeleted.getId());
                log.info(animeDeleted);

        }
}
