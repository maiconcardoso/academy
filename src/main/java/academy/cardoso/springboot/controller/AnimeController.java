package academy.cardoso.springboot.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import academy.cardoso.springboot.domain.Anime;
import academy.cardoso.springboot.service.AnimeService;
import academy.cardoso.springboot.util.DateUtil;
import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("animes")
@Log4j2
public class AnimeController {

    @Autowired
    private DateUtil dateUtil;

    @Autowired
    private AnimeService service;

    // public AnimeController(DateUtil dateUtil, AnimeService service) {
    //     this.dateUtil = dateUtil;
    //     this.service = service;
    // }

    @GetMapping
    public List<Anime> list() {
        log.info(dateUtil.formatLocalDateTimeToDataBaseStyle(LocalDateTime.now()));
        return service.listAll();
    }

}
