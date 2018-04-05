package com.dqdteam.game.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.dqdteam.game.AbilityBoss;
import com.dqdteam.game.MonsterPong;
import com.dqdteam.game.StatusBall;

/**
 * Created by DATDQ on 1/15/2018.
 */

public class Boss extends Paddle {
    MonsterPong game;
    public Texture bossImage;
    public float bossSpeed;
    public AbilityBoss abilityBoss;
    private TextureRegion[][] tmpFrames;
    public TextureRegion[] goLeftFrames;
    public TextureRegion[] goRightFrames;
    public TextureRegion[] collisionBossFrames;

    public Boss(MonsterPong game, String name, int y, Texture bossImage, float bossSpeed, AbilityBoss abilityBoss) {
        super(game, name, y);
        this.game = game;
        this.bossSpeed = bossSpeed;
        this.abilityBoss = abilityBoss;
        this.bossImage = bossImage;

        tmpFrames = TextureRegion.split(bossImage, bossImage.getWidth() / 4, bossImage.getHeight() / 4);
        goLeftFrames = new TextureRegion[4];
        goRightFrames = new TextureRegion[4];
        collisionBossFrames = new TextureRegion[4];
        int indexL = 0, indexR = 0, indexCB = 0;
        for (int i = 0; i < 4; i++) {
            goLeftFrames[indexL++] = tmpFrames[1][i];
            goRightFrames[indexR++] = tmpFrames[2][i];
            collisionBossFrames[indexCB++] = tmpFrames[0][i];
        }
    }

    public void effectBall(Ball ball) {
        if (abilityBoss == AbilityBoss.A200S) {
            ball.xVel = ball.xVel > 0 ? ball.xVel + 200f : ball.xVel - 200f;
            ball.yVel = ball.yVel > 0 ? ball.yVel + 200f : ball.yVel - 200f;
            ball.status = StatusBall.A200_BALL;
        }
    }
}
