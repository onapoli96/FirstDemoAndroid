package com.example.firstdemoandroid.giorgio.Helper;


import java.util.Objects;

public class Nodo {
    private int x;
    private int y;

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

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
            System.out.println("Per qualche motivo vado qui ");
            return false;
        }
        if (getClass() != obj.getClass()) {
            System.out.println("Anzi qui ");
            return false;
        }
        Nodo other = (Nodo) obj;
        if(this.x== other.getX() && this.y == other.getY()){
            return true;
        }
        else
        {
            return false;
        }
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
