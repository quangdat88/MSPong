package com.dqdteam.game.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.dqdteam.game.MonsterPong;
import com.dqdteam.game.StatusBall;

/**
 * Created by DATDQ on 11/22/2017.
 */

public class Monster extends Rectangle {
    public Texture monsterImage;
    public int randomMonster;
    public float xVel;
    private MonsterPong game;
    private TextureRegion[][] tmpFrames;
    public TextureRegion[] goLeftFrames;
    public TextureRegion[] goRightFrames;
    public TextureRegion[] collisionBossFrames;
    public TextureRegion[] collisionPlayerFrames;
    public int[] valueMonster;

    public Monster(MonsterPong game, int randomMonster, int y, float xVel) {
        this.game = game;
        this.randomMonster = randomMonster;
        this.xVel = xVel;
        if (randomMonster == 1)
            this.monsterImage =new Texture("data/Monsters/death.png");
        else if (randomMonster == 33 || randomMonster == 66 || randomMonster == 99)
            this.monsterImage =new Texture("data/Monsters/icemonster.png");
        else if (randomMonster % 5 == 0)
            this.monsterImage =new Texture("data/Monsters/deadpool.png");
        else if (randomMonster % 9 == 0)
            this.monsterImage =new Texture("data/Monsters/hulk.png");
        else if (randomMonster % 11 == 0)
            this.monsterImage =new Texture("data/Monsters/devil.png");
        else if (randomMonster % 7 == 0)
            this.monsterImage =new Texture("data/Monsters/ghost.png");

        tmpFrames = TextureRegion.split(monsterImage, monsterImage.getWidth() / 4, monsterImage.getHeight() / 4);
        goLeftFrames = new TextureRegion[4];
        goRightFrames = new TextureRegion[4];
        collisionBossFrames = new TextureRegion[4];
        collisionPlayerFrames = new TextureRegion[4];
        int indexL = 0, indexR = 0, indexCB = 0, indexCP = 0;
        for (int i = 0; i < 4; i++) {
            goLeftFrames[indexL++] = tmpFrames[1][i];
            goRightFrames[indexR++] = tmpFrames[2][i];
            collisionPlayerFrames[indexCP++] = tmpFrames[0][i];
            collisionBossFrames[indexCB++] = tmpFrames[3][i];
        }
        this.x = 0;
        this.y = y;
        this.width = monsterImage.getWidth() / 4;
        this.height = monsterImage.getHeight() / 4;
    }

    public void dispose() {
        monsterImage.dispose();
    }

    public void setCenterX(float posX) {
        this.x = posX - (this.width / 2);
    }

    public float getRight() {
        return this.x + this.width;
    }

    public void setRight(float posX) {
        this.x = posX - this.width;
    }

    public float getCenterX() {
        return x + (width / 2);
    }

    public void move(float deltaTime) {
        this.x += this.xVel * deltaTime;
    }

    public void effectBall(Ball ball) {
        if (randomMonster == 1) {
            ball.status = StatusBall.DEATH_BALL;
            ball.yVel *= -2;
            ball.xVel *= 2;
        } else if (randomMonster == 33 || randomMonster == 66 || randomMonster == 99) {
            ball.status = StatusBall.ICE_BALL;
        } else if (randomMonster % 5 == 0) {
            ball.xVel = ball.xVel > 0 ? ball.xVel + 100f : ball.xVel - 100f;
            ball.yVel = ball.yVel > 0 ? ball.yVel + 100f : ball.yVel - 100f;
            ball.status = StatusBall.A100_BALL;
        } else if (randomMonster % 9 == 0) {
            ball.xVel = ball.xVel > 0 ? ball.xVel + 200f : ball.xVel - 200f;
            ball.yVel = ball.yVel > 0 ? ball.yVel + 200f : ball.yVel - 200f;
            ball.status = StatusBall.A200_BALL;
        } else if (randomMonster % 11 == 0) {
            ball.xVel = ball.xVel * 2;
            ball.yVel = ball.yVel * 2;
            ball.status = StatusBall.X2_BALL;
        } else if (randomMonster % 7 == 0) {
            ball.status = StatusBall.HIDE_BALL;
        }
    }
}
