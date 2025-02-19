package com.lauracercas.moviecards.service.movie;


import com.lauracercas.moviecards.model.Movie;
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
public class MovieServiceImpl implements MovieService {

    @Autowired
    RestTemplate restTemplate;

    String url = ApiURL.API_URL + "movies";

    @Override
    public List<Movie> getAllMovies() {
        Movie[] movies = restTemplate.getForObject(url, Movie[].class);
        return Arrays.asList(movies);
    }

    @Override
    public Movie save(Movie movie) {
        if (movie.getId() != null && movie.getId() > 0) {
            restTemplate.put(url, movie);
        } else {
            movie.setId(0);
            restTemplate.postForObject(url, movie, Movie.class);
        }
        return movie;
    }

    @Override
    public Movie getMovieById(Integer movieId) {
        return restTemplate.getForObject(url + "/" + movieId, Movie.class);
    }
}
