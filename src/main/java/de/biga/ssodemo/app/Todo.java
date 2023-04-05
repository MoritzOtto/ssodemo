package de.biga.ssodemo.app;

import lombok.Getter;
import lombok.Setter;

public class Todo {
    @Getter
    @Setter
    private int id;
    @Getter
    @Setter
    private String header;
    @Getter
    @Setter
    private String body;
    @Getter
    @Setter
    private boolean done;
}
