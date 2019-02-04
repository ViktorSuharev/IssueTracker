package com.netcracker.edu.tms.ui;

import com.netcracker.edu.tms.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.LinkedList;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserRESTController {
    @GetMapping("/")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> ret = new LinkedList<>();

        ret.add(new User(
                BigInteger.valueOf(1),
                "stubUser1",
                "stubPassword1",
                "stubEmail1",
                BigInteger.valueOf(1)));
        ret.add(new User(
                BigInteger.valueOf(2),
                "stubUser2",
                "stubPassword2",
                "stubEmail2",
                BigInteger.valueOf(2)));
        ret.add(new User(
                BigInteger.valueOf(3),
                "stubUser3",
                "stubPassword3",
                "stubEmail3",
                BigInteger.valueOf(2)));
        ret.add(new User(
                BigInteger.valueOf(4),
                "stubUser4",
                "stubPassword4",
                "stubEmail4",
                BigInteger.valueOf(3)));
        ret.add(new User(
                BigInteger.valueOf(5),
                "stubUser5",
                "stubPassword5",
                "stubEmail5",
                BigInteger.valueOf(3)));


        return new ResponseEntity<>(ret, HttpStatus.OK);
    }
}
