package com.dqdteam.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.dqdteam.game.MonsterPong;
import com.dqdteam.game.accessor.TableAccessor;
import com.dqdteam.game.effect.ParticleEmitter;
import com.dqdteam.game.objects.Ball;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.equations.Back;

/**
 * @author DQDAT
 * Class nay tao ra Screen khi bat dau vao game gom co cac menu lua chon 
 */
public class MainMenuScreen implements Screen {

    private MonsterPong game;
    private Stage stage;
    private Ball ball;
    Table table;
    ParticleEmitter particleEmitter;
    int WIDTH;
    int HEIGHT;
    Screen nextScreen;
    private boolean[] useDots;

    //background
    private Sprite background;

    public MainMenuScreen(MonsterPong g) {
        game = g;
        particleEmitter = new ParticleEmitter(game);
        WIDTH = g.width;
        HEIGHT = g.height;

        stage = new Stage(new StretchViewport(WIDTH, HEIGHT));
        Skin skin = new Skin(Gdx.files.internal("uiskin.json"));

        TextureAtlas atlas = game.assetManager.get("data/pack.atlas", TextureAtlas.class);
//        background = atlas.createSprite("background");
        background = new Sprite(g.cartoon1Background);

        table = new Table();
        table.setFillParent(true);

        Label titleLabel = new Label("Monster Pong", game.titleStyle);

        TextButton textButton = new TextButton("2-Player \n First to Five", skin);
        textButton.addListener(new ClickListener() {
            @Override
            public void touchUp(InputEvent e, float x, float y, int point, int button) {
                nextScreen = new MainPlayScreen(game);
                Gdx.input.setInputProcessor(null);
                game.setScreen(nextScreen);
                setOutroTween();
            }
        });

        TextButton settingsButton = new TextButton("Settings", skin);
        settingsButton.addListener(new ClickListener() {
            @Override
            public void touchUp(InputEvent e, float x, float y, int point, int button) {
                nextScreen = new SettingsScreen(game);
                Gdx.input.setInputProcessor(null);
                setOutroTween();
            }
        });

        TextButton creditsButton = new TextButton("Credits", skin);
        creditsButton.addListener(new ClickListener() {
            @Override
            public void touchUp(InputEvent e, float x, float y, int point, int button) {
                nextScreen = new CreditsScreen(game);
                Gdx.input.setInputProcessor(null);
                setOutroTween();
            }
        });

        TextButton levelButton = new TextButton("LevelMap", skin);
        levelButton.addListener(new ClickListener() {
            @Override
            public void touchUp(InputEvent e, float x, float y, int point, int button) {
                nextScreen = new LevelScreen(game);
                Gdx.input.setInputProcessor(null);
                game.setScreen(nextScreen);
                setOutroTween();
            }
        });

        table.add(titleLabel).pad(30);
        table.row();
        table.add(textButton).width(200).height(75);
        table.row();
        table.add(settingsButton).width(200).height(75);
        table.row();
        table.add(creditsButton).width(200).height(75);
        table.row();
        table.add(levelButton).width(200).height(75);

        stage.addActor(table);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.075f, 0.059f, 0.188f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        normalUpdate(delta);
    }

    
    private void normalUpdate(float delta) {
        game.tweenManager.update(delta);
        //updateBallMovement(delta);
        //particleEmitter.update(ball, delta);
        stage.act(delta);
        batchDraw();
        stage.draw();
    }

    private void updateBallMovement(float deltaTime) {
        if (!(ball == null)) {
            ball.moveX(deltaTime);
            checkForWallCollision();
            ball.moveY(deltaTime);
            checkForCeilingCollision();
        }
    }

    private void batchDraw() {
        game.batch.setProjectionMatrix(stage.getCamera().combined);
        game.batch.begin();
        particleEmitter.drawParticles(game.batch);
        background.draw(game.batch);
       /* if (!(ball == null)) {
            game.batch.draw(ball.ballImage, ball.x, ball.y);
        }*/
        game.batch.end();
    }

    private void checkForCeilingCollision() {
        if (ball.getTop() > HEIGHT) {
            ball.reverseDirectionY();
            ball.setTop(HEIGHT);
        } else if (ball.getBottom() < 0) {
            ball.reverseDirectionY();
            ball.setBottom(0f);
        }
    }

    private void checkForWallCollision() {
        if (ball.getRight() > WIDTH) {
            ball.xVel *= -1;
            ball.setRight(WIDTH);
        } else if (ball.getX() < 0) {
            ball.xVel *= -1;
            ball.setX(0);
        }
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        setupMusic();
        //setTableTween();
    }

    private void setupMusic() {
        if (game.musicToPlay == null) {
            game.musicToPlay = game.assetManager.get("battle.mp3", Music.class);
        }

        if (game.musicOn && !(game.musicToPlay.isPlaying()))  {
            game.musicToPlay.stop();
            game.musicToPlay = game.assetManager.get("battle.mp3", Music.class);
            game.musicToPlay.setVolume(0.45f);
            game.musicToPlay.play();
            game.musicToPlay.setLooping(true);
        }
    }

    private void setTableTween() {
        TweenCallback callBack = new TweenCallback() {
            @Override
            public void onEvent(int type, BaseTween<?> source) {
                ball = new Ball(game);
                particleEmitter.setState("emit");
            }
        };
        table.setX(-800);
        Tween.to(table, TableAccessor.POSITION_XY, .8f)
                .targetRelative(800, 0)
                .ease(Back.OUT)
                .setCallback(callBack)
                .start(game.tweenManager);
    }

    private void setOutroTween() {
        TweenCallback tweenCallback = new TweenCallback() {
            @Override
            public void onEvent(int type, BaseTween<?> source) {
                game.setScreen(nextScreen);
                dispose();
            }
        };
        Tween.to(table, TableAccessor.POSITION_X, .8f)
                .targetRelative(800)
                .setCallback(tweenCallback)
                .ease(Back.IN)
                .start(game.tweenManager);
    }


    @Override
    public void hide() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void dispose() {
        stage.dispose();
    }

}
