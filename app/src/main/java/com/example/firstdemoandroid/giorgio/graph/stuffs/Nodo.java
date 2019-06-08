package com.example.firstdemoandroid.giorgio.graph.stuffs;

public class Nodo {
    private int x;
    private int y;

    public Nodo(int x, int y){
        this.x = x;
        this.y = y;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public boolean equals(Object obj) {
        super.equals(obj);
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Nodo other = (Nodo) obj;

        return x == other.x && y == other.y;
    }



    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    @Override
    public String toString() {
        return "x = " + x + "; y = " + y;
    }
}
