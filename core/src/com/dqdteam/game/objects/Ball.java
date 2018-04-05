package com.dqdteam.game.objects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.math.Rectangle;
import com.dqdteam.game.Globar;
import com.dqdteam.game.MonsterPong;
import com.dqdteam.game.StatusBall;
import com.dqdteam.game.effect.ParticleEmitter;

@SuppressWarnings("serial")
public class Ball extends Rectangle {
    public Texture ballImage;
    public float xVel;
    public float yVel;
    public String name;
    private MonsterPong game;
    public StatusBall status;
    public ParticleEmitter particleEmitter;
    public ParticleEffect particleSy;
    public Color curColorParcite;

    public Ball(MonsterPong game) {
        this.game = game;
        this.name = "ball";
        ballImage = game.ballImage;
        this.width = ballImage.getWidth();
        this.height = ballImage.getHeight();
        this.xVel = Globar.getDefaulBallXvel();
        this.yVel = Globar.getDefaulBallYvel();
        this.status = StatusBall.NORMAL;
        particleEmitter = new ParticleEmitter(game);
        curColorParcite = Color.WHITE;
    }

    public void resetVelocityY(int direction) {
        this.yVel = 200f * direction;
    }

    public void pauseBall() {
        this.yVel = 0f;
        this.xVel = 0f;
    }

    public float getCombinedVelocity(float delta) {
        double velSquared = Math.pow(xVel, 2) + Math.pow(yVel, 2);
        return (float) Math.sqrt(velSquared) * delta;
    }

    public void moveX(float deltaTime) {
        this.x += this.xVel * deltaTime;
    }

    public void moveY(float deltaTime) {
        this.y -= this.yVel * deltaTime;
    }

    public void dispose() {
        ballImage.dispose();
    }

    public float getTop() {
        return this.y + this.height;
    }

    public void setTop(float posY) {
        this.y = posY - this.height;
    }

    public void setBottom(float posY) {
        this.y = posY;
    }

    public float getRight() {
        return this.x + this.width;
    }

    public void setRight(float posX) {
        this.x = posX - this.width;
    }

    public void resetEndPhase() {
        this.status = StatusBall.NORMAL;
        this.curColorParcite = Color.WHITE;
    }

    public void startPhase(Paddle paddle) {
        this.x = paddle.getCenterX();
        this.y = paddle.name == "paddle1" ? paddle.getY() + 5 : paddle.getY() - this.height;

    }

    public void reverseDirectionX() {
        this.xVel *= -1;
    }

    public void reverseDirectionY() {
        this.yVel *= -1;
    }

    public float getBottom() {
        return getY();
    }

    public void drawBall(Batch batch, float delta) {
        try {
            curColorParcite = Globar.getColorBall().get(status);
            switch (status) {
                case NORMAL:
                    batch.draw(ballImage, this.getX(), this.getY());
                    particleEmitter.killOldParticles();
                    break;
                case A100_BALL:
                    batch.draw(ballImage, this.getX(), this.getY());
                    particleEmitter.update(this, delta);
                    particleEmitter.drawParticles(game.batch);
                    break;
                case A200_BALL:
                    batch.draw(ballImage, this.getX(), this.getY());
                    particleEmitter.update(this, delta);
                    particleEmitter.drawParticles(game.batch);
                    break;
                case X2_BALL:
                    batch.draw(ballImage, this.getX(), this.getY());
                    particleEmitter.update(this, delta);
                    particleEmitter.drawParticles(game.batch);
                    break;
                case DEATH_BALL:
                    batch.draw(ballImage, this.getX(), this.getY());
                    particleEmitter.update(this, delta);
                    particleEmitter.drawParticles(game.batch);
                    break;
                case HIDE_BALL:
                    particleEmitter.killOldParticles();
                    break;
                case ICE_BALL:
                    batch.draw(ballImage, this.getX(), this.getY());
                    particleEmitter.update(this, delta);
                    particleEmitter.drawParticles(game.batch);
                    break;
            }
        }catch (Exception e){
            return;
        }
    }
}