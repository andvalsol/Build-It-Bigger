package com.udacity.gradle.builditbigger.backend;

import com.example.javajokes.Joker;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;

/** An endpoint class we are exposing */
@Api(
        name = "myApi",
        version = "v1",
        namespace = @ApiNamespace(
                ownerDomain = "backend.builditbigger.gradle.udacity.com",
                ownerName = "backend.builditbigger.gradle.udacity.com"
        )
)
public class MyEndpoint {

    /** A simple endpoint method that returns a joke*/
    @ApiMethod(name = "getJoke")
    public MyJoke getJoke() {
        MyJoke response = new MyJoke();
        response.setData(new Joker().getJoke());

        return response;
    }

}
