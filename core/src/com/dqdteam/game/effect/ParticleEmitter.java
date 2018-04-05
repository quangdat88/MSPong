package com.dqdteam.game.effect;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.dqdteam.game.MonsterPong;
import com.dqdteam.game.objects.Ball;

import java.util.Iterator;

public class ParticleEmitter {
    Array<Particle> particles = new Array<Particle>();
    long timer;
    final int MAX_PARTICLE_SPEED = 50;
    public Texture bigTexture;
    public Texture mediumTexture;
    public Texture smallTexture;
    public Texture deathTexture;
    public Texture iceTexture;
    public String state = "stop_emit";

    public ParticleEmitter(MonsterPong game) {
        bigTexture = game.largeParticleImage;
        mediumTexture = game.mediumParticleImage;
        smallTexture = game.smallParticleImage;
        deathTexture = game.deathParticleImage;
        iceTexture = game.iceParticleImage;
        timer = TimeUtils.millis();
    }

    public void update(Ball ball, float delta) {
        switch (ball.status) {
            case A100_BALL:
                makeParticles(ball, delta, smallTexture);
                break;
            case A200_BALL:
                makeParticles(ball, delta, mediumTexture);
                break;
            case X2_BALL:
                makeParticles(ball, delta, bigTexture);
                break;
            case DEATH_BALL:
                makeParticles(ball, delta, deathTexture);
                break;
            case ICE_BALL:
                makeParticles(ball, delta, iceTexture);
                break;
        }
        updateParticles(delta);
        killOldParticles();
    }

    private void makeParticles(Ball ball, float delta, Texture type) {
        if (TimeUtils.timeSinceMillis(timer)
                > (14 / ball.getCombinedVelocity(delta))) {
            for (int i = 0; i < 1; i++) {
                float x = ball.getX() + (float) Math.random() * ball.getWidth();
                float y = ball.getY() + (float) Math.random() * ball.getHeight();
                float xVel = (float) Math.random() * MAX_PARTICLE_SPEED - (MAX_PARTICLE_SPEED / 2);
                float yVel = (float) Math.random() * MAX_PARTICLE_SPEED - (MAX_PARTICLE_SPEED / 2);
                Particle oneparticle = new Particle(x, y, xVel, yVel, TimeUtils.millis(), this);
                oneparticle.setImage(type);
                particles.add(oneparticle);
            }
            timer = TimeUtils.millis();
        }
    }

    private void updateParticles(float delta) {
        for (Particle particle : particles) {
            particle.update(delta);
        }
    }

    public void killOldParticles() {
        Iterator<Particle> itr = particles.iterator();
        while (itr.hasNext()) {
            Particle particle = itr.next();
            if (TimeUtils.timeSinceMillis(particle.birthTime) > 500) {
                itr.remove();
            }
        }
    }

    public void drawParticles(Batch batch) {
        for (Particle particle : particles) {
            batch.draw(particle.getImage(), particle.getX(), particle.getY());
        }
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
