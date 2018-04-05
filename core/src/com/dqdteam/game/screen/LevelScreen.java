package com.dqdteam.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.dqdteam.game.MonsterPong;
import com.dqdteam.game.helps.TiledMapStage;
import com.dqdteam.game.objects.Boss;

/**
 * Created by DATDQ on 3/23/2018.
 */

public class LevelScreen implements Screen {
    MonsterPong game;
    Stage stage;
    Table table;
    Screen nextScreen;
    TiledMap tiledMap;
    TiledMapRenderer tiledMapRenderer;
    OrthographicCamera camera;
    float elapsedTimeBoss = 0f;
    Boss boss;
    private Animation animationBoss;
    TiledMapTileLayer.Cell cell;

    public LevelScreen(MonsterPong g) {
        this.game = g;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.075f, 0.059f, 0.188f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        tiledMapRenderer.setView(camera);
        tiledMapRenderer.render();
        stage.act();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void show() {
        tiledMap = new TmxMapLoader().load("data/Maps/Sence1.tmx");
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
        
        stage = new TiledMapStage(tiledMap, this.game);
        Gdx.input.setInputProcessor(stage);

        camera = new OrthographicCamera();
        camera.setToOrtho(false, game.width, game.height);
        camera.update();
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
    }
}
