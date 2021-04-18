package com.music.social.models;

import java.io.Serializable;

public class AuthenticationResponse implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 6283584364633913183L;
    private final String token;

    public AuthenticationResponse(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
