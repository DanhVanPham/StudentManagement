package com.miniapp.springbootjpa.service;

import com.miniapp.springbootjpa.entity.StudentEntity;
import com.miniapp.springbootjpa.reposity.StudentReposity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StudentServiceImp implements StudentServiceInterface{
    @Autowired
    private StudentReposity reposity;

    @Override
    public List<StudentEntity> getAllStudents() {
        return reposity.findAll();
    }

    @Override
    public List<StudentEntity> getStudentByID(Integer id) {
        List<StudentEntity> student = new ArrayList<>();
        student.add(reposity.findById(id).orElse(null));
        return student;
    }

    @Override
    public StudentEntity addNewStudent(StudentEntity studentEntity) {
        StudentEntity findStudent = reposity.findById(studentEntity.getId()).orElse(null);
        if(findStudent == null) {
            return reposity.save(studentEntity);
        }else {
            return null;
        }
    }

    @Override
    public StudentEntity updateStudentExisted(StudentEntity studentEntity) {
        StudentEntity findStudent = reposity.findById(studentEntity.getId()).orElse(null);
        if(findStudent != null) {
            findStudent.setName(studentEntity.getName());
            findStudent.setEmail(studentEntity.getEmail());
            findStudent.setAddress(studentEntity.getEmail());
            return reposity.save(findStudent);
        }else{
            return null;
        }
    }

    @Override
    public String deleteStudentByID(Integer id) {
        reposity.deleteById(id);
        return "Delete successful";
    }

    @Override
    public List<StudentEntity> searchStudentByName(String name) {
        List<StudentEntity> student = new ArrayList<>();
        student.add(reposity.searchStudentByName("%" + name + "%"));
        return student;
    }
}
