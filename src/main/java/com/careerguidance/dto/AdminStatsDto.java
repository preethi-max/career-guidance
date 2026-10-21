package com.careerguidance.dto;

public class AdminStatsDto {
    private long totalUsers;
    private long totalStudents;
    private long totalGuides;
    private long pendingGuides;
    private long totalCareers;
    private long totalCourses;
    private long totalConnections;

    public AdminStatsDto() {}

    public long getTotalUsers() { return totalUsers; }
    public void setTotalUsers(long totalUsers) { this.totalUsers = totalUsers; }

    public long getTotalStudents() { return totalStudents; }
    public void setTotalStudents(long totalStudents) { this.totalStudents = totalStudents; }

    public long getTotalGuides() { return totalGuides; }
    public void setTotalGuides(long totalGuides) { this.totalGuides = totalGuides; }

    public long getPendingGuides() { return pendingGuides; }
    public void setPendingGuides(long pendingGuides) { this.pendingGuides = pendingGuides; }

    public long getTotalCareers() { return totalCareers; }
    public void setTotalCareers(long totalCareers) { this.totalCareers = totalCareers; }

    public long getTotalCourses() { return totalCourses; }
    public void setTotalCourses(long totalCourses) { this.totalCourses = totalCourses; }

    public long getTotalConnections() { return totalConnections; }
    public void setTotalConnections(long totalConnections) { this.totalConnections = totalConnections; }
}
