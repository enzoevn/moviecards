package com.lauracercas.moviecards.unittest.service;

import com.lauracercas.moviecards.model.Actor;
import com.lauracercas.moviecards.service.actor.ActorServiceImpl;
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
class ActorServiceImplTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private ActorServiceImpl sut = new ActorServiceImpl();

    private AutoCloseable closeable;

    @BeforeEach
    void setUp() {
        closeable = openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }

    @Test
    public void shouldGetAllActors() {
        Actor actors[] = new Actor[2];
        actors[0] = new Actor();
        actors[1] = new Actor();

        when(restTemplate.getForObject(anyString(), any())).thenReturn(actors);

        List<Actor> result = sut.getAllActors();

        assertEquals(2, result.size());
    }

    @Test
    public void shouldGetActorById() {
        Actor actor = new Actor();
        actor.setId(1);
        actor.setName("Sample Actor");

        when(restTemplate.getForObject(anyString(), any())).thenReturn(actor);

        Actor result = sut.getActorById(1);

        assertEquals(1, result.getId());
        assertEquals("Sample Actor", result.getName());
    }

    @Test
    void testGetAllActorsUrl() {
        // Arrange
        Actor[] actors = new Actor[]{new Actor()};
        when(restTemplate.getForObject(ApiURL.API_URL + "actors", Actor[].class)).thenReturn(actors);

        // Act
        List<Actor> result = sut.getAllActors();

        // Assert
        assertEquals(1, result.size());

        verify(restTemplate).getForObject(ApiURL.API_URL + "actors", Actor[].class);
    }

    @Test
    public void shouldSaveActor() {
        Actor actor = new Actor();
        actor.setName("New Actor");

        when(restTemplate.postForObject(anyString(), any(Actor.class), eq(Actor.class))).thenReturn(actor);

        Actor result = sut.save(actor);

        assertEquals("New Actor", result.getName());
    }

}