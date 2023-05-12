/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.napas.achoffline.reportoffline.models;

import java.time.LocalDate;


/**
 *
 * @author huynx
 */
public class WorkingDay {
    private LocalDate date;
    private Boolean work;

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Boolean getWork() {
        return work;
    }

    public void setWork(Boolean work) {
        this.work = work;
    }
    
    
}
