package com.netcracker.edu.tms.ui;

import com.netcracker.edu.tms.model.Project;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class ControllersUtils {

    public static boolean isFilled(String id) {
        return id != null && !id.isEmpty();
    }

    public static BigInteger toBigInteger(String str) {
        try {
            return new BigInteger(str);
        } catch (NumberFormatException e) {
           return new BigInteger("-1");
        }

    }

    public static Project getNonexistentProject() {
        Project nonexistentProject = new Project();
        nonexistentProject.setId(BigInteger.valueOf(-1L));

        return nonexistentProject;
    }

    public static List<Project> getStubProjectList() {
        List<Project> projectList = new ArrayList<>();

        Project Project1 = new Project(BigInteger.valueOf(1),BigInteger.valueOf(1),"stub1");
        Project Project2 = new Project(BigInteger.valueOf(2),BigInteger.valueOf(2),"stub2");


        projectList.add(Project1);
        projectList.add(Project2);

        return projectList;
    }
}
