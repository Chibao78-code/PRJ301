package dto;

import java.util.Date;

public class ProjectDTO {
    private String projectID;
    private String projectName;
    private String description;
    private String status;
    private Date launchDate;

    public ProjectDTO() {
    }

    public ProjectDTO(String projectID, String projectName, String description, String status, Date launchDate) {
        this.projectID = projectID;
        this.projectName = projectName;
        this.description = description;
        this.status = status;
        this.launchDate = launchDate;
    }

    public String getProjectID() {
        return projectID;
    }

    public void setProjectID(String projectID) {
        this.projectID = projectID;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getLaunchDate() {
        return launchDate;
    }

    public void setLaunchDate(Date launchDate) {
        this.launchDate = launchDate;
    }
}