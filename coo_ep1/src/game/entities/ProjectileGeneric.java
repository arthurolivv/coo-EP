package game.entities;

import game.util.Vector2D;

import java.awt.Color;

public abstract class ProjectileGeneric extends GameObject {

    protected int damage;
    protected Vector2D velocity;


    // --- Construtor ---
    public ProjectileGeneric(
            Color color,
            Vector2D position,
            Vector2D velocity,
            double radius,
            int damage
    ) {
        super(
                color,
                position,
                radius
        );
        this.velocity = velocity;
        this.damage = damage;
    }

    public Vector2D getVelocity() {
        return velocity;
    }

    public void setVelocity(Vector2D velocity) {
        this.velocity = velocity;
    }

    // --- Getters e Setters ---
    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    // --- Métodos abstratos ---
    @Override
    public abstract void update(long delta);

    @Override
    public abstract void render(long currentTime);
}
