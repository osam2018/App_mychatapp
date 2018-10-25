package io.github.reverince.q4u;

import android.graphics.Color;

public class Message {
    private String name;
    private String message;
    private int color;

    public Message(String name, String message, String color) {
        this.name = name;
        this.message = message;
        this.color = Color.parseColor(color);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) { this.name = name; }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getColor() { return color; }

    public void setColor(String color) { this.color = Color.parseColor(color); }
}
