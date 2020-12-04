package com.miniapp.springbootjpa.reposity;

import com.miniapp.springbootjpa.entity.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentReposity extends JpaRepository<StudentEntity, Integer> {
    @Query("Select u from StudentEntity u where u.name LIKE :name")
    public StudentEntity searchStudentByName(String name);
}
