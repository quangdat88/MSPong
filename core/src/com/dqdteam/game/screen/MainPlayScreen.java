package com.dqdteam.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.dqdteam.game.AbilityBoss;
import com.dqdteam.game.Globar;
import com.dqdteam.game.MonsterPong;
import com.dqdteam.game.MonsterRandom;
import com.dqdteam.game.StateScreen;
import com.dqdteam.game.StatusBall;
import com.dqdteam.game.accessor.CameraAccessor;
import com.dqdteam.game.accessor.PaddleAccessor;
import com.dqdteam.game.input.InputProcessorExt;
import com.dqdteam.game.objects.Ball;
import com.dqdteam.game.objects.Boss;
import com.dqdteam.game.objects.Controller;
import com.dqdteam.game.objects.Monster;
import com.dqdteam.game.objects.Paddle;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.equations.Back;
import aurelienribon.tweenengine.equations.Quad;
import aurelienribon.tweenengine.equations.Sine;

/**
 * @author DQDAT Class tao ra Screen choi game chinh - xu ly cac dau vao va cac
 *         dich chuyen
 */
public class MainPlayScreen implements Screen {
    final MonsterPong game;
    private int height;
    private int width;
    Paddle paddle1;
    Boss boss;
    Controller leftCtr;
    Controller rightCtr;
    Controller middleCtr;
    private Ball ball;
    private OrthographicCamera camera;
    private Array<Paddle> paddleList;
    private Array<Rectangle> net;
    private Texture netTexture;
    private int player1Score;
    private int player2Score;
    private Sound paddleCollisionSound;
    private int paddleHits;
    private boolean allowScreenShake;
    BitmapFont scoreFont;
    InputProcessorExt input;
    private Stage stage;
    //background
    TextureAtlas atlas;
    private Sprite background;

    private static final float delay = 1f;
    private Label addVel;

    private Animation animationMonster;
    private Animation animationBoss;
    private Monster monster;
    float elapsedTime = 0f;
    float elapsedTimeBoss = 0f;

    Paddle paddleCollision;
    StateScreen currentState;
    float pauseTime = 0f;

    List<StatusBall> normalStatusBall;

    List<StateScreen> notMoveStatus;


    float curXVel = 0f;
    float curYVel = 0f;


    public MainPlayScreen(final MonsterPong gam) {
        this.game = gam;
        width = gam.width;
        height = gam.height;
        scoreFont = game.scoreFont;
        normalStatusBall = new ArrayList<StatusBall>() {{
            add(StatusBall.NORMAL);
            add(StatusBall.A100_BALL);
            add(StatusBall.A200_BALL);
            add(StatusBall.X2_BALL);
        }};

        notMoveStatus = new ArrayList<StateScreen>() {{
            add(StateScreen.ENDPHASE);
            add(StateScreen.NOTMOVE);
        }};

        stage = new Stage(new StretchViewport(gam.width, gam.height));

        // am thanh va cham
        paddleCollisionSound = game.assetManager.get("hitball2.mp3", Sound.class);
        /*atlas = game.assetManager.get("data/pack.atlas", TextureAtlas.class);
        background = atlas.createSprite("background");*/
        background = new Sprite(gam.cartoon1Background);
        // tao ra 2 paddle
        setupPaddles();
        // tao ra cac button dk
        setupController();

        // camera
        camera = new OrthographicCamera();
        camera.setToOrtho(false, width, height);

        ball = new Ball(game);
        ball.resetEndPhase();
        ball.startPhase(paddle1);
        paddleCollision = paddle1;
        currentState = StateScreen.ENDPHASE;

        Controller[] aCtrl = new Controller[3];
        aCtrl[0] = leftCtr;
        //aCtrl[1] = middleCtr;
        aCtrl[2] = rightCtr;
        Paddle[] aPad = new Paddle[2];
        aPad[0] = paddle1;
        aPad[1] = boss;
        input = new InputProcessorExt(aCtrl, aPad, gam, camera, ball);
    }

    // di chuyen cua Boss
    private void bossMoveTween(final Boss paddle, Vector3 pos) {

        TweenCallback tweenCallback = new TweenCallback() {
            @Override
            public void onEvent(int type, BaseTween<?> source) {
                paddle.setTweening(false);
            }
        };

        paddle.setTweening(true);
        float time = 1 / paddle.bossSpeed;
        if (ball != null && ball.status == StatusBall.ICE_BALL && paddleCollision == paddle1)
            time = 0.75f;
        Tween.to(paddle, PaddleAccessor.POSITION_X, time).ease(Sine.INOUT).target(pos.x).setCallback(tweenCallback)
                .start(game.tweenManager);
    }

    /**
     * method tao ra cac paddles
     */
    public void setupPaddles() {
        paddle1 = new Paddle(game, "paddle1", Globar.getPoisitionPlayervsbottom());
        boss = new Boss(game, "boss", height - Globar.getPoisitionBossvstop(), new Texture("data/Boss/phoenix.png"), 10, AbilityBoss.A200S);
        paddleList = new Array<Paddle>();
        paddleList.add(paddle1);
        paddleList.add(boss);
    }

    /**
     * method tao ra cac button trai, phai, giua
     */
    public void setupController() {
        leftCtr = new Controller(game.leftButton, "leftController", 0, 10);
        rightCtr = new Controller(game.rightButton, "rightController", width - 64, 10);
        middleCtr = new Controller(game.perButton, "middleControoler", (width - 64) / 2, 10);
    }

    @Override
    public void render(float delta) {
        if (notMoveStatus.contains(currentState)) {
            pauseTime += delta;
        }
        if (pauseTime > 0.5f) {
            if (monster != null) monster = null;
            pauseTime = 0f;
            if (currentState == StateScreen.ENDPHASE) {
                if (paddleCollision == paddle1) {
                    ball.yVel = -Globar.getDefaulBallYvel();
                    ball.xVel = Globar.getDefaulBallXvel();
                } else if (paddleCollision == boss) {
                    ball.yVel = Globar.getDefaulBallYvel();
                    ball.xVel = Globar.getDefaulBallXvel();
                }

                if (paddleCollision.getX() >= 0 && paddleCollision.getRight() <= game.width)
                    ball.x = paddleCollision.getCenterX() - ball.width / 2;
            }
            currentState = StateScreen.NORMAL;
        }
        game.tweenManager.update(delta);
        camera.update();

        updateBallMovement(delta);

        Bossmove(delta);

        monsterMove(delta);

        checkPaddleOutOfBounds();

        checkForGameOver();

        setStatusBall();
        input.setTime(ball, paddleCollision);
        Gdx.gl.glClearColor(0.075f, 0.059f, 0.188f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batchDraw(delta);
        stage.act(delta);
        stage.draw();
        game.tweenManager.update(delta);
    }

    private void Bossmove(float delta) {
        if (notMoveStatus.contains(currentState)) {
            return;
        }

        Vector3 posmove = new Vector3();
        if (this.ball != null && this.ball.status != StatusBall.HIDE_BALL) {
            posmove.x = this.ball.getRight();
            if (boss.getCenterX() <= ball.getRight()) {
                animationBoss = new Animation(1f / 4f, boss.goRightFrames);
            } else {
                animationBoss = new Animation(1f / 4f, boss.goLeftFrames);
            }
            bossMoveTween(boss, posmove);


        }
    }

    private void monsterMove(float delta) {
        if (monster != null) {
            monster.move(delta);
            if (monster.getRight() > game.width) {
                monster.xVel *= -1;
                monster.setRight(game.width);
                animationMonster = new Animation(1f / 4f, monster.goLeftFrames);
            } else if (monster.getX() < 0) {
                monster.xVel *= -1;
                monster.setX(0f);
                animationMonster = new Animation(1f / 4f, monster.goRightFrames);
            }
        }
    }

    private void updateBallMovement(float deltaTime) {
        if (ball == null || notMoveStatus.contains(currentState)) {
            return;
        }
        ball.moveX(deltaTime);
        checkForPaddleCollision();
        checkForBallOutOfBounds();
        checkForMonsterCollision();
        ball.moveY(deltaTime);
        checkForWallCollision();
    }

    private void checkForPaddleCollision() {
        if (ball != null)
            for (Paddle hitPaddle : paddleList) {
                if (Intersector.overlaps(hitPaddle, ball)) {
                    ball.yVel *= -1;
                    collisionBall(Globar.getDefaulVelBallvspaddle());
                    paddleCollisionSound.play();
                    //startScreenShake();
                    if (hitPaddle.name.equals("paddle1")) {
                        ball.setPosition(ball.x, (hitPaddle.y));
                        paddleCollision = paddle1;
                    } else if (hitPaddle.name.equals("boss")) {
                        ball.setPosition(ball.x, (hitPaddle.y - ball.height));
                        paddleCollision = boss;
                        if(new Random().nextInt(100) <50){
                            boss.effectBall(ball);
                            Label lb = new Label(Globar.getBossAvelocity().get(boss.abilityBoss),game.velStyle);
                            lb.setPosition(ball.getX(),ball.getY());
                            lb.addAction(Actions.fadeOut(2f));
                            stage.addActor(lb);
                        }
                    }
                    Random index = new Random();
                    int randomMonster = index.nextInt(Globar.getCountSpriteMonster());
                    if (MonsterRandom.getValue(randomMonster) != null) {
                        monster = new Monster(game, randomMonster, height / 2, Globar.getDefaulVelMonster());
                        animationMonster = new Animation(1f / 4f, monster.goRightFrames);
                        animationMonster.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);
                    }
                    enterNormalState();
                }
            }
    }

    private void checkForMonsterCollision() {
        if (monster != null && ball != null)
            if (Intersector.overlaps(monster, ball)) {
                monster.effectBall(ball);
                monster.xVel = 0f;
                if (paddleCollision == paddle1) {
                    animationMonster = new Animation(1f / 4f, monster.collisionPlayerFrames);
                } else if (paddleCollision == boss) {
                    animationMonster = new Animation(1f / 4f, monster.collisionPlayerFrames);
                }
                currentState = StateScreen.NOTMOVE;
            }
    }

    private void startScreenShake() {
        if (allowScreenShake) {
            Gdx.input.vibrate(150);

            Timeline.createSequence().push(Tween.set(camera, CameraAccessor.POSITION_XY).target(width / 2, height / 2))
                    .push(Tween.to(camera, CameraAccessor.POSITION_XY, 0.035f).targetRelative(8, 0).ease(Quad.IN))
                    .push(Tween.to(camera, CameraAccessor.POSITION_XY, 0.035f).targetRelative(-8, 0).ease(Quad.IN))
                    .push(Tween.to(camera, CameraAccessor.POSITION_XY, 0.0175f).target(width / 2, height / 2)
                            .ease(Quad.IN))
                    .repeatYoyo(2, 0).start(game.tweenManager);
        }
    }


    private void screenShake(float delta) {
        if (game.tweenManager.containsTarget(camera)) {
            game.tweenManager.update(delta);
        } else {
            camera.position.set(width / 2, height / 2, 0);
        }
    }

    private void checkForBallOutOfBounds() {
        if (ball.y < paddle1.y - ball.getHeight() || ball.getTop() > boss.y + ball.getHeight()) {
            enterNormalState();
        }
        if (ball.y < 0 - ball.height) {
            ball.resetEndPhase();
            ball.reverseDirectionX();
            ball.reverseDirectionY();
            ball.pauseBall();
            player2Score++;
            ball.startPhase(boss);
            paddleCollision = boss;
            currentState = StateScreen.ENDPHASE;
        } else if (ball.getTop() > height + ball.height) {
            ball.resetEndPhase();
            ball.reverseDirectionX();
            ball.reverseDirectionY();
            ball.pauseBall();
            player1Score++;
            ball.startPhase(paddle1);
            paddleCollision = paddle1;
            currentState = StateScreen.ENDPHASE;
        }
    }

    private void enterNormalState() {
        paddleHits = 0;
        allowScreenShake = false;
        ball.status = StatusBall.NORMAL;
    }

    private void checkForWallCollision() {
        if (ball.getRight() > width) {
            ball.reverseDirectionX();
            ball.setRight(width);
            collisionBall(Globar.getDefaulVelBallvswall());
        } else if (ball.getX() < 0) {
            ball.reverseDirectionX();
            ball.setX(0f);
            collisionBall(Globar.getDefaulVelBallvswall());
        }
    }

    private void checkPaddleOutOfBounds() {
        for (Paddle paddle : paddleList) {
            if (paddle.getRight() > width) {
                paddle.setRight(width);
            } else if (paddle.x < 0) {
                paddle.setX(0);
            }
        }
    }

    private void collisionBall(int aVel) {
        if (ball.yVel > 0) {
            ball.yVel += aVel;
        } else {
            ball.yVel -= aVel;
        }
        if (ball.xVel > 0) {
            ball.xVel += aVel;
        } else {
            ball.xVel -= aVel;
        }
    }

    private void checkForGameOver() {
        if ((player1Score >= Globar.getENDPOINT() && (player1Score - player2Score >= 2)) || (player2Score >= Globar.getENDPOINT() && (player2Score - player1Score >= 2))) {
            if (!game.tweenManager.containsTarget(camera)) {
                ball = null;
                game.winningPlayer = player1Score > player2Score ? "You" : "Boss";
                //beginOutroTween();
                game.setScreen(new WinScreen(game));
            }
        }
    }

    private void setStatusBall() {
       /* float absXVel = 0;
        if (ball != null) {
            if (normalStatusBall.contains(ball.status)) {
                absXVel = Math.abs(ball.xVel);
                if (absXVel >= Globar.VEL_BALL_GOOD && absXVel < Globar.VEL_BALL_EXCE) {
                    ball.status = StatusBall.A100_BALL;
                    return;
                }

                if (absXVel >= Globar.VEL_BALL_EXCE && absXVel < Globar.VEL_BALL_PERF) {
                    ball.status = StatusBall.A200_BALL;
                    return;
                }

                if (absXVel >= Globar.VEL_BALL_PERF) {
                    ball.status = StatusBall.X2_BALL;
                    return;
                }
            }
        }*/
    }

    /**
     * method ve ra giao dien - the hien cac doi tuong ra man hinh
     */
    private void batchDraw(float delta) {
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        game.batch.disableBlending();
        //backgraond
        background.draw(game.batch);
        game.batch.enableBlending();

        if (!(ball == null)) {
            ball.drawBall(game.batch, delta);
        }

        if (monster != null && animationMonster != null) {
            elapsedTime += delta;
            game.batch.draw((TextureRegion) animationMonster.getKeyFrame(elapsedTime, true), monster.x, monster.y);
        }

        if (boss != null && animationBoss != null) {
            elapsedTimeBoss += delta;
            game.batch.draw((TextureRegion) animationBoss.getKeyFrame(elapsedTimeBoss, true), boss.x, boss.y);
        }
        // cac paddles
        game.batch.draw(paddle1.paddleImage, paddle1.x, paddle1.y - 10);
        game.batch.draw(boss.paddleImage, boss.x, boss.y);
        // cac controller
        game.batch.draw(leftCtr.button, leftCtr.x, leftCtr.y);
        game.batch.draw(rightCtr.button, rightCtr.x, rightCtr.y);
        //game.batch.draw(middleCtr.button, middleCtr.x, middleCtr.y);
        //diem so
        scoreFont.draw(game.batch, String.valueOf(player1Score), 10, 70);
        scoreFont.draw(game.batch, String.valueOf(player2Score), width - 30, 70);
        game.batch.end();
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void resume() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void show() {
        player1Score = 0;
        player2Score = 0;
        paddleHits = 0;
        //beginIntroTween();
        Gdx.input.setInputProcessor(input);
    }

    @Override
    public void dispose() {
        paddle1.dispose();
        boss.dispose();
        // netTexture.dispose();
    }

    private void startMusic() {
        game.musicToPlay.stop();
        // game.musicToPlay = game.assetManager.get("recall_of_the_shadows.mp3",
        // Music.class);
        if (game.musicOn) {
            game.musicToPlay.play();
            game.musicToPlay.setLooping(true);
        }
    }

    private void beginIntroTween() {
        TweenCallback callBack = new TweenCallback() {
            @Override
            public void onEvent(int type, BaseTween<?> source) {
                // set input 9/2/2016
                // Gdx.input.setInputProcessor(new MainInputProcessor());
                Gdx.input.setInputProcessor(input);
                startMusic();
            }
        };
        camera.position.x += 300;
        Tween.to(camera, CameraAccessor.POSITION_X, 2f).targetRelative(-300).ease(Back.OUT).setCallback(callBack)
                .start(game.tweenManager);
    }

    private void beginOutroTween() {
        TweenCallback callBack = new TweenCallback() {
            @Override
            public void onEvent(int type, BaseTween<?> source) {
                game.setScreen(new WinScreen(game));
                dispose();
            }
        };
        Timeline.createSequence().pushPause(1.0f)
                .push(Tween.to(camera, CameraAccessor.POSITION_X, 2f).targetRelative(-800).ease(Back.IN))
                .setCallback(callBack).start(game.tweenManager);
    }
}