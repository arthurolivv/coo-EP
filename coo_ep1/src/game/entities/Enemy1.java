package game.entities;

import game.GameLib;
import game.util.Vector2D;
import game.util.Status;

import java.awt.Color;
import java.util.Optional;

public class Enemy1 extends EnemyGeneric {

    private static long spawnCooldown = 1500;
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
    public Enemy1(
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

        // Verifica se o inimigo saiu da tela
        if (newY > GameLib.HEIGHT + 10) {
            setStatus(Status.INACTIVE);
        }
    }


    // --- Renderização do inimigo ---
    @Override
    public void render(long currentTime) {
        if (getStatus() == Status.ACTIVE) {
            GameLib.setColor(getColor());
            GameLib.drawCircle(getPosition().getX(), getPosition().getY(), getRadius());
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
