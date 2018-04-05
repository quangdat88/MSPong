package com.dqdteam.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.TimeUtils;
import com.dqdteam.game.Globar;
import com.dqdteam.game.MonsterPong;
import com.dqdteam.game.accessor.TableAccessor;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;

/**
 * @author DQDAT Class nay tao Screen Loading & load du lieu ve font, nhac,
 *         bong, paddle
 */
public class LoadingScreen implements Screen {

    private MonsterPong game;
    private Screen nextScreen; // ** Screen next**//
    private Table table;
    private Stage stage;
    private long startTime;

    public LoadingScreen(MonsterPong game) {
        this.game = game;
        setupAssetManager();

        stage = new Stage();
        table = new Table();
        table.setFillParent(true);

        BitmapFont loadingFont = getLoadingFont();
        LabelStyle loadingStyle = new LabelStyle(loadingFont, Color.WHITE);
        Label loadLabel = new Label("Monster Pong", loadingStyle);
        table.add(loadLabel).padTop(20);
        table.row();
        Image image = new Image(new Texture(
                Gdx.files.internal("data/splashscreen.png")));
        table.add(image).center();
        stage.addActor(table);
    }

    private BitmapFont getLoadingFont() {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(
                Gdx.files.internal("fonts/segoepr.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 30;
        BitmapFont titleFont = generator.generateFont(parameter);
        generator.dispose();

        return titleFont;
    }

    private void setupAssetManager() {
        try {
            game.assetManager.load("uiskin.atlas", TextureAtlas.class);
            game.assetManager.load("hitball2.mp3", Sound.class);
            game.assetManager.load("battle.mp3", Music.class);
//        game.assetManager.load("8bit_airship.ogg", Music.class);
            game.assetManager.load("data/pack.atlas", TextureAtlas.class);
            game.titleStyle = new LabelStyle(getTitleFont(), Color.WHITE);
            game.velStyle = new LabelStyle(getVelFont(), Color.GOLD);
            //game.ballImage = new Texture("data/ball16.png");
            game.ballImage = makeCircleImage(8, Color.WHITE);
            game.paddleImage = new Texture("data/paddle.png");
            game.leftButton = new Texture("data/left.png");
            game.rightButton = new Texture("data/right.png");
            game.perButton = new Texture("data/perfect.png");
            game.smallParticleImage = makeCircleImage(1, Color.CYAN);
            game.mediumParticleImage = makeCircleImage(1, Color.GREEN);
            game.largeParticleImage = makeCircleImage(1, Color.ORANGE);
            game.deathParticleImage = makeCircleImage(1, Color.BLACK);
            game.iceParticleImage = makeCircleImage(1, Color.WHITE);
            game.netImage = makeRectImage(2, 2, Color.WHITE);
            game.scoreFont = makeScoreFont();
            game.cartoon1Background = new Texture("data/Background/cartoon1.png");
            game.cartoon2Background = new Texture("data/Background/cartoon2.png");
            game.cartoon3Background = new Texture("data/Background/cartoon3.png");
        }
        catch (Exception e){
            return;
        }
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.075f, 0.059f, 0.188f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.assetManager.update();
        game.tweenManager.update(delta);

        stage.act(delta);
        stage.draw();
        doneLoadingCheck();
    }

    private void doneLoadingCheck() {
        if (game.assetManager.update()) {
            if (TimeUtils.millis() > (startTime + 1000)) {
                if (game.tweenManager.size() == 0) {
                    nextScreen = new MainMenuScreen(game);
                    outroTween();
                }
            }
        }
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void show() {
        startTime = TimeUtils.millis();
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
    public void dispose() {
        stage.dispose();
    }

    private BitmapFont getTitleFont() {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(
                Gdx.files.internal("fonts/segoepr.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = Globar.getFontSize();
        BitmapFont titleFont = generator.generateFont(parameter);
        generator.dispose();

        return titleFont;
    }

    private BitmapFont getVelFont() {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(
                Gdx.files.internal("fonts/segoepr.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = Globar.getFontVelSize();
        BitmapFont titleFont = generator.generateFont(parameter);
        generator.dispose();

        return titleFont;
    }

    private Texture makeRectImage(int width, int height, Color color) {
        Pixmap ballPixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888);
        ballPixmap.setColor(color);
        ballPixmap.fill();
        return new Texture(ballPixmap);
    }

    private Texture makeCircleImage(int radius, Color color) {
        Pixmap pixmap = new Pixmap(2 * radius + 1, 2 * radius + 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(color);
        pixmap.fillCircle(radius, radius, radius);
        pixmap.drawLine(radius, radius, 2 * radius, radius);
        //Pixmap.setFilter(Pixmap.Filter.NearestNeighbour);
        return new Texture(pixmap);
    }

    private BitmapFont makeScoreFont() {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(
                Gdx.files.internal("fonts/segoepr.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 20;
        BitmapFont titleFont = generator.generateFont(parameter);
        generator.dispose();

        return titleFont;
    }

    private void outroTween() {
        TweenCallback tweenCallback = new TweenCallback() {
            @Override
            public void onEvent(int type, BaseTween<?> source) {
                game.getScreen().dispose();
                game.setScreen(nextScreen);

            }
        };
        Tween.to(table, TableAccessor.POSITION_X, .8f).targetRelative(800)
                .setCallback(tweenCallback)
                .ease(aurelienribon.tweenengine.equations.Back.OUT)
                .start(game.tweenManager);
    }

}
