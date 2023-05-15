package edu;

class Player {
    String name;
    Gui gui;
    int score;

    Player(String name) {
    	System.out.println("Constructing Player: " + name);
        this.name = name;
        this.score = 0;
        System.out.println("Constructing GUI for: " + name);
        this.gui = new Gui(this);
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
    
	public void main(String name) {
		
	}

}
