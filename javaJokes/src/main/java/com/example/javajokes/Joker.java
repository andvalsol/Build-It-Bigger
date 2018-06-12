package com.example.javajokes;

import java.util.Random;

public class Joker {
    
    public String getJoke() {
        String[] jokes = {
                "A burglar stole all the lamps in my house. I know I should be more upset, but I'm absolutely delighted",
                "People always tell me I'm condescending.\n" +
                        "\n" +
                        "(That means talking down to people.)",
                "Q. Why did the invisible man turn down the job offer?\n" +
                        "\n" +
                        "A. Because he just couldn't see himself doing it.",
                "Two fish are in a tank. One says to the other, \"Do you know how to drive this thing?\"",
               "Q. Why was the belt sent to jail?\n" +
                       "\n" +
                       "A. For holding up a pair of pants!"
        };
    
    
        //Return the joke
        return jokes[new Random().nextInt(jokes.length)];
    }
}