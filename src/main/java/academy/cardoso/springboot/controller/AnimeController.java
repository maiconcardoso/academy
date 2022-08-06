package academy.cardoso.springboot.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import academy.cardoso.springboot.domain.Anime;
import academy.cardoso.springboot.util.DateUtil;
import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("anime")
@Log4j2
public class AnimeController {

    @Autowired
    private DateUtil dateUtil;

    @GetMapping("list")
    public List<Anime> list() {
        log.info(dateUtil.formatLocalDateTimeToDataBaseStyle(LocalDateTime.now()));
        return List.of(new Anime("Boku no Hero"), new Anime("Tate no Yusha no Nariagari"), new Anime("Dragon Quest: Adventure of Dai"));
    }
    
}
