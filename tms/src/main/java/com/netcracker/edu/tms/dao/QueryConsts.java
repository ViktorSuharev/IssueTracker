package com.netcracker.edu.tms.dao;

public class QueryConsts {
    public static final String SELECT_WITH_NAME = new String(
            "SELECT p FROM Project p WHERE p.name = :name order by p.id");
    public static final String SELECT_PROJECTS_BY_CREATOR_ID = new String(
            "SELECT p FROM Project p WHERE p.creator_id = :creator_id order by p.id");
    public static final String SELECT_ALL = new String(
            "SELECT p FROM Project p order by p.id");
    public static final String SELECT_USERS_PROJECTS = new String(
            "SELECT p from UsersToProjects u, Project p where u.projectId=p.id and u.userId=:userId");
}