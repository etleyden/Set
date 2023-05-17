package edu;
import java.io.ObjectOutputStream;

class Player {
    String name;
    Gui gui;
    int score;
    ObjectOutputStream outputStream; // New field for the ObjectOutputStream

    Player(String name, ObjectOutputStream outputStream) {
        System.out.println("Constructing Player: " + name);
        this.name = name;
        this.score = 0;
        System.out.println("Constructing GUI for: " + name);
        this.gui = new Gui(this);
        this.outputStream = outputStream; // Assigning the ObjectOutputStream
    }

    String getName() {
        return name;
    }

    Gui getGui() {
        return this.gui;
    }

    int getScore() {
        return this.score;
    }

    void incrementScore() {
        this.score++;
    }

    ObjectOutputStream getOutputStream() {
        return outputStream;
    }
    
    // Optionally, you can also set the ObjectOutputStream for a player
    void setOutputStream(ObjectOutputStream outputStream) {
        this.outputStream = outputStream;
    }
    
    void start() {

    }
}
