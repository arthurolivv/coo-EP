package game.entities;

import game.util.Vector2D;
import game.util.Status;

import java.awt.*;
import java.util.Optional;

public abstract class EnemyGeneric extends GameObject {

  private double rotationVelocity;
  private double velocity;
  private int health;
  private int damage;
  private double angle;
  private long fireCooldown;
  private long nextShotTime;

  // --- Construtor ---
  public EnemyGeneric(
          Color color,
          Vector2D position,
          double velocity,
          double radius,
          double angle,
          int health,
          int damage,
          long fireCooldown,
          double explosionStart,
          double explosionEnd,
          double rotationVelocity
  ) {
    super(
            color,
            position,
            radius
    );

    this.health = health;
    this.damage = damage;
    this.angle = angle;
    this.fireCooldown = fireCooldown;
    this.rotationVelocity = rotationVelocity;
    this.setExplosionStart(explosionStart);
    this.setExplosionEnd(explosionEnd);
    this.setStatus(Status.ACTIVE);
    this.nextShotTime = 0;
    this.velocity = velocity;
  }

  // --- Getters e Setters ---
  public double getRotationVelocity() {
    return rotationVelocity;
  }

  public double getVelocity() {
    return velocity;
  }

  public void setVelocity(double velocity) {
    this.velocity = velocity;
  }

  public void setRotationVelocity(double rotationVelocity) {
    this.rotationVelocity = rotationVelocity;
  }

  public int getHealth() {
    return health;
  }

  public void setHealth(int health) {
    this.health = health;
  }

  public int getDamage() {
    return damage;
  }

  public void setDamage(int damage) {
    this.damage = damage;
  }

  public double getAngle() {
    return angle;
  }

  public void setAngle(double angle) {
    this.angle = angle;
  }

  public long getFireCooldown() {
    return fireCooldown;
  }

  public void setFireCooldown(long fireCooldown) {
    this.fireCooldown = fireCooldown;
  }

  public long getNextShotTime() {
    return nextShotTime;
  }

  public void setNextShotTime(long nextShotTime) {
    this.nextShotTime = nextShotTime;
  }

  // --- Lógica de disparo do inimigo ---
  public abstract Optional<ProjectileEnemy> tryToShoot(long currentTime, double playerY);

  // --- Métodos abstratos obrigatórios ---
  @Override
  public abstract void update(long delta);

  @Override
  public abstract void render(long currentTime);
}
