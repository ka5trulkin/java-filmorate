package ru.yandex.practicum.filmorate.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.model.IdHolder;
import ru.yandex.practicum.filmorate.storage.Service;

@Slf4j
@AllArgsConstructor
public abstract class AbstractController<S extends Service<IdHolder>> {
    protected final S service;
//
//    @Autowired
//    protected AbstractFilmsController(S service) {
//        this.service = service;
//    }
//
//    @PostMapping
//    @ResponseStatus(HttpStatus.CREATED)
//    public T add(@Valid @RequestBody T film) {
//        log.info(REQUEST_ADD_FILM.message(), film.getName());
//        return service.add(film);
//    }
//
//    @PutMapping
//    public T update(@Valid @RequestBody T film) {
//        log.info(REQUEST_UPDATE_FILM.message(), film.getId(), film.getName());
//        return service.update(film);
//    }
//
//    @GetMapping("/{id}")
//    public T get(@PathVariable("id") long id) {
//        log.info(REQUEST_GET_FILM.message(), id);
//        return service.get(id);
//    }
//
//    @GetMapping
//    public List<T> getList() {
//        log.info(REQUEST_GET_FILM_LIST.message());
//        return service.getList();
//    }
//
//    @PutMapping("/{id}/like/{userId}")
//    public void addLike(@PathVariable long id, @PathVariable long userId) {
//        log.info(REQUEST_ADD_FILM_LIKE.message(), id, userId);
//        service.addLike(id, userId);
//    }
//
//    @DeleteMapping("/{id}/like/{userId}")
//    public void removeLike(@PathVariable long id, @PathVariable long userId) {
//        log.info(REQUEST_REMOVE_FILM_LIKE_.message(), id, userId);
//        service.removeLike(id, userId);
//    }
//
//    @GetMapping("/popular")
//    public List<T> getPopularList(@RequestParam(defaultValue = "10") long count) {
//        log.info(REQUEST_GET_POPULAR_FILM_LIST.message());
//        return service.getPopularList(count);
//    }
}