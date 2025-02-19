package com.lauracercas.moviecards.unittest.service;

import com.lauracercas.moviecards.model.Movie;
import com.lauracercas.moviecards.service.movie.MovieServiceImpl;
import com.lauracercas.moviecards.util.ApiURL;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

/**
 * Autor: Laura Cercas Ramos
 * Proyecto: TFM Integración Continua con GitHub Actions
 * Fecha: 04/06/2024
 */
class MovieServiceImplTest {
    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private MovieServiceImpl sut = new MovieServiceImpl();

    private AutoCloseable closeable;

    @BeforeEach
    public void setUp() {
        closeable = openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }

    @Test
    public void shouldGetAllMovies() {
        Movie movies[] = new Movie[2];
        movies[0] = new Movie();
        movies[1] = new Movie();        

        when(restTemplate.getForObject(ApiURL.API_URL + "movies", Movie[].class)).thenReturn(movies);

        List<Movie> result = sut.getAllMovies();

        assertEquals(2, result.size());
    }

    @Test
    public void shouldGetMovieById() {
        Movie movie = new Movie();
        movie.setId(1);
        movie.setTitle("Sample Movie");

        when(restTemplate.getForObject(ApiURL.API_URL + "movies/" + movie.getId(), Movie.class)).thenReturn(movie);

        Movie result = sut.getMovieById(1);

        assertEquals(1, result.getId());
        assertEquals("Sample Movie", result.getTitle());
        verify(restTemplate).getForObject(ApiURL.API_URL + "movies/" + movie.getId(), Movie.class);

    }

    @Test
    void testGetAllMoviesUrl() {
        // Arrange
        Movie[] actors = new Movie[]{new Movie()};
        when(restTemplate.getForObject(ApiURL.API_URL + "movies", Movie[].class)).thenReturn(actors);

        // Act
        List<Movie> result = sut.getAllMovies();

        // Assert
        assertEquals(1, result.size());

        verify(restTemplate).getForObject(ApiURL.API_URL + "movies", Movie[].class);
    }

    @Test
    public void shouldSaveMovie() {
        Movie movie = new Movie();
        movie.setTitle("New Movie");

        when(restTemplate.postForObject(ApiURL.API_URL + "movies", movie, Movie.class)).thenReturn(movie);

        Movie result = sut.save(movie);

        assertEquals("New Movie", result.getTitle());
        verify(restTemplate).postForObject(ApiURL.API_URL + "movies", movie, Movie.class);
    }

}