package academy.cardoso.springboot.controller;

import java.time.LocalDateTime;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import academy.cardoso.springboot.domain.Anime;
import academy.cardoso.springboot.request.AnimePostRequestBody;
import academy.cardoso.springboot.request.AnimePutRequestBody;
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

    @GetMapping
    public ResponseEntity<Page<Anime>> list(Pageable pageable) {
        log.info(dateUtil.formatLocalDateTimeToDataBaseStyle(LocalDateTime.now()));
        return new ResponseEntity<>(service.findAll(pageable), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Anime> findById(@PathVariable Long id) {
        return new ResponseEntity<Anime>(service.findByIdOrElseThrowException(id), HttpStatus.OK);
    }

    @GetMapping("/find")
    public ResponseEntity<List<Anime>> findByName(@RequestParam(name = "name") String name) {
        return ResponseEntity.ok(service.AnimeByName(name));
    }

    @PostMapping
    public ResponseEntity<Anime> save(@Valid @RequestBody AnimePostRequestBody animePostRequestBody) {
        return new ResponseEntity<Anime>(service.save(animePostRequestBody), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping
    public ResponseEntity<Anime> replace(@RequestBody AnimePutRequestBody animePutRequestBody) {
        service.replace(animePutRequestBody);
        return new ResponseEntity<Anime>(HttpStatus.OK);
    }


}
