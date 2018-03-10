package controllers;

import play.data.validation.Constraints;

public class WidgetData {

    @Constraints.Required
    private String keyword;

    public WidgetData() {
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {this.keyword = keyword;}

}