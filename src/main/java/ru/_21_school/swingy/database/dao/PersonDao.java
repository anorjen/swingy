package ru._21_school.swingy.database.dao;

import org.apache.ibatis.annotations.Param;
import ru._21_school.swingy.model.equipment.Equipment;
import ru._21_school.swingy.model.person.Person;

import java.util.List;

public interface PersonDao {

    void onForeignKeys();
    void createPersonTable();
    void createEqiupmentTable();

    List<Person> getAll();
    void insertPerson(@Param("person") Person person);
    void updatePerson(@Param("person") Person person);
    void deletePerson(@Param("person") Person person);

    void insertEquipment(@Param("person") Person person, @Param("equipment") Equipment equipment);
    void deleteEquipments(@Param("person") Person person);

    Integer getMaxPersonId();

}
