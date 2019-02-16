package com.netcracker.edu.tms.dao;

public class QueryBuilder {

    public static final String selectQuery = "SELECT t FROM %s t WHERE (t.%s) = :parameter";

}
