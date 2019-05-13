package com.example.a2lazy2do.BE;

import java.io.Serializable;

public class Task implements Serializable {

    private String m_title;
    private String m_subject;
    private String m_image;

    public Task(String title , String subject , String image) {
        m_title = title;
        m_subject = subject;
        m_image = image;
    }

    public void setTitle(String title) { this.m_title = title; }

    public String getTitle() { return m_title; }

    public void setSubject(String subject) { this.m_subject = subject; }

    public String getSubject() { return m_subject; }

    public void setImage(String image) { this.m_image = image; }

    public String getImage() { return m_image; }
}
