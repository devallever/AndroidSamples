package com.allever.android.sample.jfit;

/**
 * Created by allever on 17-9-20.
 */

public class CompetitionData {
    private float distance;//运动距离
    private CompetitionLine.CompetitionUserType competitionUserType;//用户类型:ME当前用户, OTHER:其他用户
    private int rank;//排名

    public CompetitionData(CompetitionLine.CompetitionUserType competitionUserType, float distance, int rank) {
        this.rank = rank;
        this.competitionUserType = competitionUserType;
        this.distance = distance;
    }

    public float getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }

    public CompetitionLine.CompetitionUserType getCompetitionUserType() {
        return competitionUserType;
    }

    public void setCompetitionUserType(CompetitionLine.CompetitionUserType competitionUserType) {
        this.competitionUserType = competitionUserType;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }
}
