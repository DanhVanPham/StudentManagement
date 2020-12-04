package com.miniapp.springbootjpa.service;


import com.miniapp.springbootjpa.entity.StudentEntity;

import java.util.List;
import java.util.Optional;

public interface StudentServiceInterface {
    public List<StudentEntity> getAllStudents();
    public List<StudentEntity> getStudentByID(Integer id);
    public StudentEntity addNewStudent(StudentEntity studentEntity);
    public StudentEntity updateStudentExisted(StudentEntity studentEntity);
    public String deleteStudentByID(Integer id);
    public List<StudentEntity> searchStudentByName(String name);
}
