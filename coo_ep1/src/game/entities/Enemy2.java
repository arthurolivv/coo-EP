package game.entities;

import game.GameLib;
import game.util.Vector2D;
import game.util.Status;

import java.awt.Color;
import java.util.Optional;

public class Enemy2 extends EnemyGeneric {

  private static long spawnCooldown = 7000;
  private static long nextSpawnTime = 0;

  // --- Getters e Setters estáticos para controle de spawn ---
  public static long getSpawnCooldown() {
    return spawnCooldown;
  }

  public static void setSpawnCooldown(long cooldown) {
    spawnCooldown = cooldown;
  }

  public static long getNextSpawnTime() {
    return nextSpawnTime;
  }

  public static void setNextSpawnTime(long time) {
    nextSpawnTime = time;
  }

  // --- Construtor ---
  public Enemy2(
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
          double velocityRotation
  ) {
    super(
            color,
            position,
            velocity,
            radius,
            angle,
            health,
            damage,
            fireCooldown,
            explosionStart,
            explosionEnd,
            velocityRotation

    );
  }

  @Override
  public Optional<ProjectileEnemy> tryToShoot(long currentTime, double playerY) {
    if (currentTime > getNextShotTime() && getPosition().getY() < playerY) {
      double vx = Math.cos(getAngle()) * 0.45;
      double vy = Math.sin(getAngle()) * -0.45;
      Vector2D velocity = new Vector2D(vx, vy);

      ProjectileEnemy projectile = new ProjectileEnemy(
              Color.RED,
              new Vector2D(getPosition().getX(), getPosition().getY()),
              velocity,
              2.0,
              getDamage()
      );

      setNextShotTime(currentTime + (long)(200 + Math.random() * 500));
      return Optional.of(projectile);
    }

    return Optional.empty();
  }

  // --- Atualização do inimigo ---
  @Override
  public void update(long delta) {

    double newX = getPosition().getX() + getVelocity() * Math.cos(getAngle()) * delta;
    double newY = getPosition().getY() + getVelocity() * Math.sin(getAngle()) * delta * (-1.0);

    getPosition().setX(newX);
    getPosition().setY(newY);

    setAngle(getAngle() + getRotationVelocity() * delta);

    boolean shootNow = false;
    double threshold = GameLib.HEIGHT * 0.30;
    double previousY = getPosition().getY();

    if(previousY < threshold && getPosition().getY() >= threshold) {

      if(getPosition().getX() < GameLib.WIDTH / 2) setRotationVelocity(0.003);
      else setRotationVelocity(-0.003);
    }

    if(getRotationVelocity() > 0 && Math.abs(getAngle() - 3 * Math.PI) < 0.05){

      setRotationVelocity(0);
      setAngle(3 * Math.PI);
      shootNow = true;
    }

    if(getRotationVelocity() < 0 && Math.abs(getAngle()) < 0.05){

      setRotationVelocity(0);
      setAngle(0);
      shootNow = true;
    }

//    if(shootNow){
//
//      double [] angles = { Math.PI/2 + Math.PI/8, Math.PI/2, Math.PI/2 - Math.PI/8 };
//      int [] freeArray = findFreeIndex(e_projectile_states, angles.length);
//
//      for(int k = 0; k < freeArray.length; k++){
//
//        int free = freeArray[k];
//
//        if(free < e_projectile_states.length){
//
//          double a = angles[k] + Math.random() * Math.PI/6 - Math.PI/12;
//          double vx = Math.cos(a);
//          double vy = Math.sin(a);
//
//          e_projectile_X[free] = enemy2_X[i];
//          e_projectile_Y[free] = enemy2_Y[i];
//          e_projectile_VX[free] = vx * 0.30;
//          e_projectile_VY[free] = vy * 0.30;
//          e_projectile_states[free] = ACTIVE;
//        }
//      }
//    }

    if (newY > GameLib.HEIGHT + 10) {
      setStatus(Status.INACTIVE);
    }
  }


  // --- Renderização do inimigo ---
  @Override
  public void render(long currentTime) {
    if (getStatus() == Status.ACTIVE) {
      GameLib.setColor(getColor());
      GameLib.drawDiamond(getPosition().getX(), getPosition().getY(), getRadius());
    }
    else if (getStatus() == Status.EXPLODING) {
      double alpha = (currentTime - getExplosionStart()) / (getExplosionEnd() - getExplosionStart());

      if (alpha < 1.0) {
        GameLib.drawExplosion(getPosition().getX(), getPosition().getY(), alpha);
      } else {
        setStatus(Status.INACTIVE);
      }
    }
  }
}
