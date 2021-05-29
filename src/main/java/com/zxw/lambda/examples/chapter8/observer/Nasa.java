package com.zxw.lambda.examples.chapter8.observer;

// BEGIN Nasa
public class Nasa implements LandingObserver {
    @Override
    public void observeLanding(String name) {
        if (name.contains("Apollo")) {
            System.out.println("We made it!");
        }
    }
}
// END Nasa
