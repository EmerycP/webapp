package com.emerycprimeau.model;

import com.emerycprimeau.transfer.GameCompletedResponse;

import java.util.Comparator;

public class Sorted implements Comparator<Game> {
    public int compare(Game a, Game b)
    {
        return b.Score - a.Score;
    }
}
