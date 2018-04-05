package com.dqdteam.game.helps;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.dqdteam.game.MonsterPong;
import com.dqdteam.game.screen.MainPlayScreen;

/**
 * Created by DATDQ on 3/27/2018.
 */

public class TiledMapStage extends Stage {
    private static Group group = new Group();
    private TiledMap tiledMap;
    Screen nextScreen;
    MonsterPong game;

    public TiledMapStage(TiledMap tiledMap, MonsterPong game) {
        this.game =game;
        this.tiledMap = tiledMap;
        group.setVisible(true);
        for (MapLayer layer : tiledMap.getLayers()) {
            TiledMapTileLayer tiledLayer = (TiledMapTileLayer)layer;
            String nameTiled = tiledLayer.getName();
            if(nameTiled.equals("boss"))
                createActorsForLayer(tiledLayer);
        }
    }

    private void createActorsForLayer(TiledMapTileLayer tiledLayer) {
        for (int x = 0; x < tiledLayer.getWidth(); x++) {
            for (int y = 0; y < tiledLayer.getHeight(); y++) {
                TiledMapTileLayer.Cell cell = tiledLayer.getCell(x, y);
                TiledMapActor actor = new TiledMapActor(tiledMap, tiledLayer, cell);
                actor.setBounds(x * tiledLayer.getTileWidth(), y * tiledLayer.getTileHeight(), tiledLayer.getTileWidth(),
                        tiledLayer.getTileHeight());
                actor.addListener(new ClickListener() {
                    @Override
                    public void touchUp(InputEvent e, float x, float y, int point, int button) {
                        nextScreen = new MainPlayScreen(game);
                        Gdx.input.setInputProcessor(null);
                        game.setScreen(nextScreen);
                    }
                });
               /* EventListener eventListener = new TiledMapClickListener(actor);
                actor.addListener(eventListener);*/
                group.addActor(actor);
//              addActor(actor);
            }
        }
        this.addActor(group);
    }
}