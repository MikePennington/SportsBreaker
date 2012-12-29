package com.breaker.newsstory;

import java.util.Comparator;

public class StoryComparator implements Comparator<StoryBean> {

    public static final int COMPARE_TITLE = 1;
    public static final int COMPARE_SCORE = 2;
    public static final int COMPARE_CAR   = 3;

    private int             compareMethod;

    public StoryComparator(int compareMethod) {
        this.compareMethod = compareMethod;
    }

    public int compare(StoryBean s1, StoryBean s2) {
        if (s1 == null)
            return -1;
        else if (s2 == null)
            return 1;

        if (compareMethod == COMPARE_TITLE) {
            return s1.getTitle().compareTo(s2.getTitle());
        } else if (compareMethod == COMPARE_SCORE) {
            StoryStatsBean stats1 = s1.getStats();
            StoryStatsBean stats2 = s2.getStats();
            if (stats1 == null)
                return -1;
            if (stats2 == null)
                return 1;
            return s2.getStats().getScore() - s1.getStats().getScore();
        } else if (compareMethod == COMPARE_CAR) {
            StoryStatsBean stats1 = s1.getStats();
            StoryStatsBean stats2 = s2.getStats();
            if (stats1 == null)
                return -1;
            if (stats2 == null)
                return 1;
            double compare = (stats2.getCurrentActivityRating() - stats1.getCurrentActivityRating()) * 1000;
            return new Double(compare).intValue();
        }
        return 0;
    }

}
