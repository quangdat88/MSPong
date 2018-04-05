package com.dqdteam.game;

import com.badlogic.gdx.graphics.Color;

import java.util.HashMap;
import java.util.Map;

public final class Globar {
    private Globar(){

    }

    private static int FONT_SIZE = 30;

    private static int FONT_VEL_SIZE = 14;

    private static int DOT_COUNT = 3;

    private static int POISITION_BOSSVSTOP = 105;
    private static int POISITION_PLAYERVSBOTTOM = 80;

    private static float DEFAUL_TIME_MOVE_PADDLE = 2f;
    private static float DEFAUL_TIME_MOVE_BOSSPADDLE = 0.25f;

    private static float DEFAUL_BALL_XVEL = 200f;
    private static float DEFAUL_BALL_YVEL = 200f;

    private static float VEL_BALL_GOOD = 300f;
    private static float VEL_BALL_EXCE = 400f;
    private static float VEL_BALL_PERF = 500f;

    private static int DEFAUL_VEL_BALLVSPADDLE = 20;

    private static float DEFAUL_VEL_MONSTER = 50f;

    private static int DEFAUL_VEL_BALLVSWALL = 10;

    private static Color DEFAUL_COLOR_NORMAL = Color.WHITE;
    private static Color DEFAUL_COLOR_GOOD = Color.CYAN;
    private static Color DEFAUL_COLOR_EXCE = Color.GREEN;
    private static Color DEFAUL_COLOR_PERF = Color.ORANGE;
    private static Color DEFAUL_COLOR_DEATH = Color.BLACK;

    private static int COL_SPRITE_MONSTER = 4;
    private static int RAW_SPRITE_MONSTER = 4;

    private static int ENDPOINT = 5;

    private static int COUNT_SPRITE_MONSTER = 100;

    private static Map<StatusBall, Color> COLOR_BALL = new HashMap<StatusBall, Color>() {{
        put(StatusBall.NORMAL, Color.WHITE);
        put(StatusBall.DEATH_BALL, Color.BLACK);
        put(StatusBall.ICE_BALL, Color.WHITE);
        put(StatusBall.A100_BALL, Color.CYAN);
        put(StatusBall.A200_BALL, Color.GREEN);
        put(StatusBall.X2_BALL, Color.ORANGE);
    }};

    private static Map<AbilityBoss, String> BOSS_AVELOCITY = new HashMap<AbilityBoss, String>() {{
        put(AbilityBoss.A100S, "+100");
        put(AbilityBoss.A200S, "+200");
    }};

    private static Map<int[], StatusBall> MONSTER_BALL = new HashMap<int[], StatusBall>() {{
        put(MonsterRandom.RANDOM_DEATH, StatusBall.DEATH_BALL);
        put(MonsterRandom.RANDOM_ICE, StatusBall.ICE_BALL);
        put(MonsterRandom.RANDOM_A100, StatusBall.A100_BALL);
        put(MonsterRandom.RANDOM_A200, StatusBall.A200_BALL);
        put(MonsterRandom.RANDOM_X2, StatusBall.X2_BALL);
        put(MonsterRandom.RANDOM_HIDE, StatusBall.HIDE_BALL);
    }};

    public static int getFontSize() {
        return FONT_SIZE;
    }

    public static void setFontSize(int fontSize) {
        FONT_SIZE = fontSize;
    }

    public static int getFontVelSize() {
        return FONT_VEL_SIZE;
    }

    public static void setFontVelSize(int fontVelSize) {
        FONT_VEL_SIZE = fontVelSize;
    }

    public static int getDotCount() {
        return DOT_COUNT;
    }

    public static void setDotCount(int dotCount) {
        DOT_COUNT = dotCount;
    }

    public static int getPoisitionBossvstop() {
        return POISITION_BOSSVSTOP;
    }

    public static void setPoisitionBossvstop(int poisitionBossvstop) {
        POISITION_BOSSVSTOP = poisitionBossvstop;
    }

    public static int getPoisitionPlayervsbottom() {
        return POISITION_PLAYERVSBOTTOM;
    }

    public static void setPoisitionPlayervsbottom(int poisitionPlayervsbottom) {
        POISITION_PLAYERVSBOTTOM = poisitionPlayervsbottom;
    }

    public static float getDefaulTimeMovePaddle() {
        return DEFAUL_TIME_MOVE_PADDLE;
    }

    public static void setDefaulTimeMovePaddle(float defaulTimeMovePaddle) {
        DEFAUL_TIME_MOVE_PADDLE = defaulTimeMovePaddle;
    }

    public static float getDefaulTimeMoveBosspaddle() {
        return DEFAUL_TIME_MOVE_BOSSPADDLE;
    }

    public static void setDefaulTimeMoveBosspaddle(float defaulTimeMoveBosspaddle) {
        DEFAUL_TIME_MOVE_BOSSPADDLE = defaulTimeMoveBosspaddle;
    }

    public static float getDefaulBallXvel() {
        return DEFAUL_BALL_XVEL;
    }

    public static void setDefaulBallXvel(float defaulBallXvel) {
        DEFAUL_BALL_XVEL = defaulBallXvel;
    }

    public static float getDefaulBallYvel() {
        return DEFAUL_BALL_YVEL;
    }

    public static void setDefaulBallYvel(float defaulBallYvel) {
        DEFAUL_BALL_YVEL = defaulBallYvel;
    }

    public static float getVelBallGood() {
        return VEL_BALL_GOOD;
    }

    public static void setVelBallGood(float velBallGood) {
        VEL_BALL_GOOD = velBallGood;
    }

    public static float getVelBallExce() {
        return VEL_BALL_EXCE;
    }

    public static void setVelBallExce(float velBallExce) {
        VEL_BALL_EXCE = velBallExce;
    }

    public static float getVelBallPerf() {
        return VEL_BALL_PERF;
    }

    public static void setVelBallPerf(float velBallPerf) {
        VEL_BALL_PERF = velBallPerf;
    }

    public static int getDefaulVelBallvspaddle() {
        return DEFAUL_VEL_BALLVSPADDLE;
    }

    public static void setDefaulVelBallvspaddle(int defaulVelBallvspaddle) {
        DEFAUL_VEL_BALLVSPADDLE = defaulVelBallvspaddle;
    }

    public static float getDefaulVelMonster() {
        return DEFAUL_VEL_MONSTER;
    }

    public static void setDefaulVelMonster(float defaulVelMonster) {
        DEFAUL_VEL_MONSTER = defaulVelMonster;
    }

    public static int getDefaulVelBallvswall() {
        return DEFAUL_VEL_BALLVSWALL;
    }

    public static void setDefaulVelBallvswall(int defaulVelBallvswall) {
        DEFAUL_VEL_BALLVSWALL = defaulVelBallvswall;
    }

    public static Color getDefaulColorNormal() {
        return DEFAUL_COLOR_NORMAL;
    }

    public static void setDefaulColorNormal(Color defaulColorNormal) {
        DEFAUL_COLOR_NORMAL = defaulColorNormal;
    }

    public static Color getDefaulColorGood() {
        return DEFAUL_COLOR_GOOD;
    }

    public static void setDefaulColorGood(Color defaulColorGood) {
        DEFAUL_COLOR_GOOD = defaulColorGood;
    }

    public static Color getDefaulColorExce() {
        return DEFAUL_COLOR_EXCE;
    }

    public static void setDefaulColorExce(Color defaulColorExce) {
        DEFAUL_COLOR_EXCE = defaulColorExce;
    }

    public static Color getDefaulColorPerf() {
        return DEFAUL_COLOR_PERF;
    }

    public static void setDefaulColorPerf(Color defaulColorPerf) {
        DEFAUL_COLOR_PERF = defaulColorPerf;
    }

    public static Color getDefaulColorDeath() {
        return DEFAUL_COLOR_DEATH;
    }

    public static void setDefaulColorDeath(Color defaulColorDeath) {
        DEFAUL_COLOR_DEATH = defaulColorDeath;
    }

    public static int getColSpriteMonster() {
        return COL_SPRITE_MONSTER;
    }

    public static void setColSpriteMonster(int colSpriteMonster) {
        COL_SPRITE_MONSTER = colSpriteMonster;
    }

    public static int getRawSpriteMonster() {
        return RAW_SPRITE_MONSTER;
    }

    public static void setRawSpriteMonster(int rawSpriteMonster) {
        RAW_SPRITE_MONSTER = rawSpriteMonster;
    }

    public static int getENDPOINT() {
        return ENDPOINT;
    }

    public static void setENDPOINT(int ENDPOINT) {
        Globar.ENDPOINT = ENDPOINT;
    }

    public static int getCountSpriteMonster() {
        return COUNT_SPRITE_MONSTER;
    }

    public static void setCountSpriteMonster(int countSpriteMonster) {
        COUNT_SPRITE_MONSTER = countSpriteMonster;
    }

    public static Map<StatusBall, Color> getColorBall() {
        return COLOR_BALL;
    }

    public static void setColorBall(Map<StatusBall, Color> colorBall) {
        COLOR_BALL = colorBall;
    }

    public static Map<AbilityBoss, String> getBossAvelocity() {
        return BOSS_AVELOCITY;
    }

    public static void setBossAvelocity(Map<AbilityBoss, String> bossAvelocity) {
        BOSS_AVELOCITY = bossAvelocity;
    }

    public static Map<int[], StatusBall> getMonsterBall() {
        return MONSTER_BALL;
    }

    public static void setMonsterBall(Map<int[], StatusBall> monsterBall) {
        MONSTER_BALL = monsterBall;
    }
}
