package com.dqdteam.game;

/**
 * Created by DATDQ on 11/28/2017.
 */

public final class MonsterRandom {
    public static int[] RANDOM_DEATH = {1}; //==1
    public static int[] RANDOM_ICE = {33 , 66 , 99}; //==1
    public static int[] RANDOM_A100 = {5, 10, 15, 20, 25, 30, 35, 40, 45, 50, 60, 65, 70, 75, 80, 85, 90, 95, 100}; // % 5 =0
    public static int[] RANDOM_A200 = {9, 18, 27, 36, 45, 54, 63, 72, 81}; // % 9 = 0
    public static int[] RANDOM_X2 = {11, 22, 33, 44, 55, 66, 77, 88, 99}; // % 11 =0
    public static int[] RANDOM_HIDE = {7, 14, 21, 28, 35, 42, 49, 56, 84, 91, 98}; // %7=0

    public static int[] getValue(int index) {
        if (index == 1) return RANDOM_DEATH;
        if (index == 33 || index == 66 || index == 99) return RANDOM_ICE;
        if (index % 5 == 0) return RANDOM_A100;
        else if (index % 9 == 0) return RANDOM_A200;
        else if (index % 11 == 0) return RANDOM_X2;
        else if (index % 7 == 0) return RANDOM_HIDE;
        else return null;

    }
}
