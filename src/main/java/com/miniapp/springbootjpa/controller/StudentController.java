package com.miniapp.springbootjpa.controller;

import com.miniapp.springbootjpa.entity.StudentEntity;
import com.miniapp.springbootjpa.service.StudentServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/studentmanagement/api")
public class StudentController {
    @Autowired
    private StudentServiceInterface service;

    @CrossOrigin
    @GetMapping(path = "/getStudents")
    public List<StudentEntity> getAllStudents() {
        return service.getAllStudents();
    }
    @CrossOrigin
    @GetMapping(path = "/getStudent/{id}")
    public List<StudentEntity> getStudentByID(@PathVariable Integer id) {
        return service.getStudentByID(id);
    }
    @CrossOrigin
    @PostMapping(path = "/addStudent")
    public StudentEntity addNewStudent(@RequestBody StudentEntity studentEntity) {
        return service.addNewStudent(studentEntity);
    }

    @CrossOrigin
    @PutMapping(path = "/updateStudent")
    public StudentEntity updateStudent(@RequestBody StudentEntity studentEntity) {
        return service.updateStudentExisted(studentEntity);
    }

    @CrossOrigin
    @DeleteMapping(path = "/deleteStudent/{id}")
    public String deleteStudentByID(@PathVariable Integer id) {
        return service.deleteStudentByID(id);
    }

    @CrossOrigin
    @GetMapping(path = "/getStudentByName/{name}")
    public List<StudentEntity> searchStudentByName(@PathVariable String name) {
        return service.searchStudentByName(name);
    }
}
