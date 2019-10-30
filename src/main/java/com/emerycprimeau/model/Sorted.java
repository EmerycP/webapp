package com.emerycprimeau.model;

import com.emerycprimeau.transfer.GameCompletedResponse;

import java.util.Comparator;

public class Sorted implements Comparator<GameCompletedResponse> {
    public int compare(GameCompletedResponse a, GameCompletedResponse b)
    {
        return b.score - a.score;
    }
}
