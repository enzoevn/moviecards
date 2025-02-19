package com.lauracercas.moviecards.service.actor;


import com.lauracercas.moviecards.model.Actor;
import com.lauracercas.moviecards.util.ApiURL;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

/**
 * Autor: Laura Cercas Ramos
 * Proyecto: TFM Integración Continua con GitHub Actions
 * Fecha: 04/06/2024
 */
@Service
public class ActorServiceImpl implements ActorService {

    @Autowired
    RestTemplate restTemplate;

    String url = ApiURL.API_URL + "actors";

    @Override
    public List<Actor> getAllActors() {
        Actor[] actors = restTemplate.getForObject(url, Actor[].class);
        return Arrays.asList(actors);
    }

    @Override
    public Actor save(Actor actor) {
        if (actor.getId() != null && actor.getId() > 0) {
            restTemplate.put(url, actor);
        } else {
            actor.setId(0);
            restTemplate.postForObject(url, actor, Actor.class);
        }
        return actor;
    }

    @Override
    public Actor getActorById(Integer actorId) {
        return restTemplate.getForObject(url + "/" + actorId, Actor.class);
    }
}
