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
public class TaskRestControlller {

    @GetMapping("/")
    public ResponseEntity<List<Task>> getAllEmployees() {
        List<Task> ret = new LinkedList<>();
        for (int i = 0; i < 20; i++) {
            ret.add(new Task(
                    BigInteger.valueOf(i),
                    "stub" + i,
                    "stub task" + i,
                    new Date(111),
                    new Date(111),
                    BigInteger.valueOf(i),
                    BigInteger.valueOf(i),
                    BigInteger.valueOf(i),
                    new Date(111),
                    BigInteger.valueOf(i),
                    BigInteger.valueOf(i)));
        }
        return new ResponseEntity<>(ret, HttpStatus.OK);
    }

}
