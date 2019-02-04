package com.netcracker.edu.tms.ui;

import com.netcracker.edu.tms.model.Task;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskRESTControlller {

    @GetMapping("/")
    public ResponseEntity<List<Task>> getAllEmployees() {
        List<Task> ret = new LinkedList<>();
        ret.add(new Task(
                BigInteger.ONE,
                "stub1",
                "stub task1",
                new Date(111),
                new Date(111),
                BigInteger.valueOf(1),
                BigInteger.valueOf(1),
                BigInteger.valueOf(1),
                new Date(111),
                BigInteger.valueOf(1),
                BigInteger.valueOf(1)));

        ret.add(new Task(
                BigInteger.valueOf(2),
                "stub2",
                "stub task2",
                new Date(222),
                new Date(222),
                BigInteger.valueOf(2),
                BigInteger.valueOf(2),
                BigInteger.valueOf(2),
                new Date(222),
                BigInteger.valueOf(2),
                BigInteger.valueOf(2)));
        ret.add(new Task(
                BigInteger.valueOf(3),
                "stub3",
                "stub task3",
                new Date(333),
                new Date(333),
                BigInteger.valueOf(3),
                BigInteger.valueOf(3),
                BigInteger.valueOf(3),
                new Date(333),
                BigInteger.valueOf(3),
                BigInteger.valueOf(3)));
        ret.add(new Task(
                BigInteger.valueOf(4),
                "stub4",
                "stub task4",
                new Date(444),
                new Date(444),
                BigInteger.valueOf(4),
                BigInteger.valueOf(4),
                BigInteger.valueOf(4),
                new Date(444),
                BigInteger.valueOf(4),
                BigInteger.valueOf(4)));
        return new ResponseEntity<>(ret, HttpStatus.OK);
    }

}
