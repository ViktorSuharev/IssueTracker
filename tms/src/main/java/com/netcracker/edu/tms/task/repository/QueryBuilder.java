package com.netcracker.edu.tms.task.repository;

public class QueryBuilder {

    public static final String selectQuery = "SELECT t FROM %s t WHERE (t.%s) = :parameter";

}
