package com.integradora;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Enemy {
    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    private float x;
    private float y;
    private float speed;
    private Image imagen;

    public Enemy(float x, float y) {
        this.x = x;
        this.y = y;
        this.speed = 1;

        this.imagen = new Image("EnemyWalk.gif");
    }

    public void tick() {
        // Actualizar la posición del enemigo
        this.x += speed;
        this.y += speed;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public Image getImagen() {
        return imagen;
    }
}
