package com.example.algoflow.models;

public class Algorithm {
    private String id;
    private String topicId;
    private String name;
    private String visualizationPath;;

    public Algorithm(String id, String topicId, String name, String visualizationPath) {
        this.id = id;
        this.topicId = topicId;
        this.name = name;
        this.visualizationPath = visualizationPath;
    }

    public String getId() {
        return id;
    }

    public String getTopicId() {
        return topicId;
    }

    public String getName() {
        return name;
    }

    public String getVisualizationPath() {
        return visualizationPath;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTopicId(String topicId) {
        this.topicId = topicId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setVisualizationPath(String visualizationPath) {
        this.visualizationPath = visualizationPath;
    }
}
