package com.lauracercas.moviecards.util;

public class ApiURL {
    public static final String API_URL = "https://moviecards-service-viacava.azurewebsites.net/";

    // Private constructor to hide the implicit public one
    private ApiURL() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
}