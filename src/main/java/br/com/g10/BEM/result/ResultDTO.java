package br.com.g10.BEM.result;

import java.util.Date;
import java.util.UUID;

import br.com.g10.BEM.exam.ExamModel;

public class ResultDTO {

    private UUID id;

    private String examName;

    private Date created;

    private Date application;

    private String classname;

    private double average;

    // Construtor
    public ResultDTO(ResultModel result) {
        this.id = result.getId();
        this.examName = result.getExam().getName();
        this.average = result.getStudent().getResults().stream()
            .mapToInt(ResultModel::getTotalScore)
            .average()
            .orElse(0.0);
    }

    public ResultDTO(ExamModel exam, double averageScore) {
        //TODO Auto-generated constructor stub
    }

    // construtor vazio
    public ResultDTO() {
    }
    
    // Getters e Setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getExamName() {
        return examName;
    }

    public void setExamName(String examName) {
        this.examName = examName;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreationDate(Date created) {
        this.created = created;
    }

    public Date getApplication() {
        return application;
    }

    public void setApplication(Date application) {
        this.application = application;
    }

    public double getAverage() {
        return average;
    }

    public void setAverage(double average) {
        this.average = average;
    }

    public String getClassname() {
        return classname;
    }

    public void setClassName(String classname) {
        this.classname = classname;
    }

    @Override
    public String toString() {
        return "ResultDTO [application=" + application + ", average=" + average + ", created=" + created + ", examName="
                + examName + ", id=" + id + "]";
    }
}

