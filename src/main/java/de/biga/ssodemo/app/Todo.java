package de.biga.ssodemo.app;

import lombok.Getter;
import lombok.Setter;

public class Todo {
    @Getter
    @Setter
    private int id;
    @Getter
    @Setter
    private String title;
    @Getter
    @Setter
    private String description;
    @Getter
    @Setter
    private boolean done;
}
