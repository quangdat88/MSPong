package com.dqdteam.game.input;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector3;
import com.dqdteam.game.Globar;
import com.dqdteam.game.MonsterPong;
import com.dqdteam.game.StatusBall;
import com.dqdteam.game.accessor.PaddleAccessor;
import com.dqdteam.game.objects.Ball;
import com.dqdteam.game.objects.Controller;
import com.dqdteam.game.objects.Paddle;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.equations.Sine;

public class InputProcessorExt implements InputProcessor {
    private final Vector3 tmpV = new Vector3();
    float timeMove;
    Controller leftCtr;
    Controller rightCtr;
    Controller middleCtr;
    Paddle paddle1;
    Paddle paddle2;
    MonsterPong game;
    Camera camera;
    float velocityPaddle = 0;

    public InputProcessorExt(Controller[] aController, Paddle[] aPaddle,
                             MonsterPong game, Camera camera, Ball ball) {
        this.leftCtr = aController[0];
        this.middleCtr = aController[1];
        this.rightCtr = aController[2];
        this.paddle1 = aPaddle[0];
        this.paddle2 = aPaddle[1];
        this.game = game;
        this.camera = camera;
        timeMove = Globar.getDefaulTimeMovePaddle();
    }

    public void setPaddleLocationForTouchDown(Vector3 pos) {
        Vector3 posmove = new Vector3();

        if (pos.x >= leftCtr.x && pos.x <= (leftCtr.x + leftCtr.width)
                && pos.y >= leftCtr.y && pos.y <= (leftCtr.y + leftCtr.height)) {
            leftCtr.pressController();
            //posmove.x = paddle1.getCenterX() - 20;
            posmove.x = 0;
            /*if (velocityPaddle != 0)
                posmove.x = (paddle1.getX() - velocityPaddle) < 0 ? 0 : paddle1.getX() - velocityPaddle;*/
            if (leftCtr.isPress)
                paddleMoveTween(paddle1, posmove, timeMove);
        }

        if (pos.x >= rightCtr.x && pos.x <= (rightCtr.x + rightCtr.width)
                && pos.y >= rightCtr.y
                && pos.y <= (rightCtr.y + rightCtr.height)) {
            rightCtr.pressController();
            posmove.x = game.width;
            /*if (velocityPaddle != 0)
                posmove.x = (paddle1.getRight() + velocityPaddle) > game.width ? game.width : paddle1.getRight() + velocityPaddle;*/
            if (rightCtr.isPress)
                paddleMoveTween(paddle1, posmove, timeMove);
        }
    }

    public void setTime(Ball ball, Paddle collision) {
        if (ball != null && ball.status == StatusBall.ICE_BALL && collision.name == "paddle2") {
            timeMove = 5f;
        } else {
            timeMove = Globar.getDefaulTimeMovePaddle();
        }
    }

    private void paddleMoveTween(final Paddle paddle, Vector3 pos, float time) {

        TweenCallback tweenCallback = new TweenCallback() {
            @Override
            public void onEvent(int type, BaseTween<?> source) {
                paddle.setTweening(false);
            }
        };

        paddle.setTweening(true);
        Tween.to(paddle, PaddleAccessor.POSITION_X, time).ease(Sine.INOUT)
                .target(pos.x).setCallback(tweenCallback)
                .start(game.tweenManager);
    }

    @Override
    public boolean keyDown(int keycode) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        setPaddleLocationForTouchDown(camera.unproject(tmpV.set(screenX, screenY, 0)));
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {

        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        // TODO Auto-generated method stub
        return false;
    }
}
