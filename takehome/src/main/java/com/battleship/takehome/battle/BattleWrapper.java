package com.battleship.takehome.battle;

import com.battleship.takehome.battle.Battle;

import java.util.List;

public class BattleWrapper {
    private List<Battle> battles;
    private Boolean success;
    private String errorMessage;

    public List<Battle> getBattles() {
        return battles;
    }

    public void setBattles(List<Battle> battles) {
        this.battles = battles;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
