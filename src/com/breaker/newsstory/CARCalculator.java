package com.breaker.newsstory;

import static com.breaker.utils.Constants.STAT_TYPE_COMMENT;
import static com.breaker.utils.Constants.STAT_TYPE_THUMBS_DOWN;
import static com.breaker.utils.Constants.STAT_TYPE_THUMBS_UP;
import static com.breaker.utils.Constants.STAT_TYPE_VIEW_DETAIL;
import static com.breaker.utils.Constants.STAT_TYPE_VISIT_URL;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.breaker.utils.Constants;

public class CARCalculator {

    public static final double  MIN_HOURS               = 1.5;
    private static final double STAT_WEIGHT_CREATED     = 2;
    private static final double STAT_WEIGHT_UP          = 1;
    private static final double STAT_WEIGHT_DOWN        = 0.2;
    private static final double STAT_WEIGHT_VIEW_DETAIL = 0.1;
    private static final double STAT_WEIGHT_VISIT_URL   = 0.1;
    private static final double STAT_WEIGHT_COMMENT     = 1.5;

    private List<NewsStoryStat> stats;
    private StoryBean           story;

    public CARCalculator(StoryBean story) {
        this.story = story;
    }

    public void addStatList(List<NewsStoryStat> newList) {
        if (stats == null)
            stats = new ArrayList<NewsStoryStat>();
        stats.addAll(newList);
    }

    public double calculateCAR() {
        double car = 0;

        // Calculate base CAR based on age of story
        if (story != null) {
            car = calculateFactor(1, STAT_WEIGHT_CREATED, story.getBrokenDate());
        }

        for (NewsStoryStat stat : stats) {

            double statWeight = 0;
            if (stat.getStatTypeId() == STAT_TYPE_THUMBS_UP) {
                statWeight = STAT_WEIGHT_UP;
            } else if (stat.getStatTypeId() == STAT_TYPE_THUMBS_DOWN) {
                statWeight = STAT_WEIGHT_DOWN;
            } else if (stat.getStatTypeId() == STAT_TYPE_VIEW_DETAIL) {
                statWeight = STAT_WEIGHT_VIEW_DETAIL;
            } else if (stat.getStatTypeId() == STAT_TYPE_VISIT_URL) {
                statWeight = STAT_WEIGHT_VISIT_URL;
            } else if (stat.getStatTypeId() == STAT_TYPE_COMMENT) {
                statWeight = STAT_WEIGHT_COMMENT;
            }

            double add = calculateFactor(stat.getValue(), statWeight, stat.getActionDate());

            car = car + add;
        }

        return car;
    }

    private double calculateFactor(int value, double weight, Date actionDate) {
        double hours = (new Date().getTime() - actionDate.getTime()) / Constants.MILLIS_HOUR;
        double factor = (1 / Math.log10(.05 * hours + 3)) - 1;
        factor = factor < 0 ? 0 : factor;
        double add = factor * value * weight;
        return add;
    }
}
