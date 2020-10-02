package com.example.pogoda;

class MessageEvent {
    public String city;

    public MessageEvent(String message) {
        city = message;
    }

    public String getMessage() {
        return city;
    }
}
