package ru._21_school.swingy.database;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import ru._21_school.swingy.database.dao.PersonDao;
import ru._21_school.swingy.model.equipment.Equipment;
import ru._21_school.swingy.model.person.Person;

import java.io.IOException;
import java.io.Reader;
import java.util.List;

public class DatabaseService {

    private static final String MYBATIS_CONFIG = "mybatis-config.xml";
    private static final String DB_PATH = "src/main/resources/db/person.db";
    private static final String DB_URL = "jdbc:sqlite:" + DB_PATH;
    private static DatabaseService instance;
    private SqlSession session;
    private PersonDao dao;

    public static DatabaseService getInstance() {
        if (instance == null) {
            instance = new DatabaseService();
        }
        return instance;
    }

    private DatabaseService() {
        createDatabase();
        initMybatis();
        createTables();
//        dao.onForeignKeys();
    }

    private void createDatabase() {

        if (Files.notExists(Paths.get(DB_PATH))) {
            try (Connection conn = DriverManager.getConnection(DB_URL)) {
                if (conn != null) {
                    DatabaseMetaData meta = conn.getMetaData();
                    System.out.println("The driver name is " + meta.getDriverName());
                    System.out.println("A new database has been created.");
                }

            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        else {
            System.out.println("database already exists");
        }
    }

    private void initMybatis() {

        SqlSessionFactory sqlSessionFactory;
        Reader reader = null;
        try {
            //Читаем файл с настройками подключения и настройками MyBatis
            reader = Resources.getResourceAsReader(MYBATIS_CONFIG);
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
            session = sqlSessionFactory.openSession();
            //Создаем маппер, из которого и будем вызывать методы getSubscriberById и getSubscribers
            dao = session.getMapper(PersonDao.class);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createTables() {
        dao.createPersonTable();
        session.commit();
        dao.createEqiupmentTable();
        session.commit();
    }

    public void close() {
        session.close();
        System.out.println("database session closed");
    }

    public List<Person> getAll() {
        return dao.getAll();
    }

    public void insertPerson(Person person) {
        Integer maxPersonId = dao.getMaxPersonId();
        int nextId = (maxPersonId == null ? 0 : maxPersonId) + 1;
        person.setId(nextId);
        dao.insertPerson(person);
        List<Equipment> equipments = person.getEquipments();
        if (equipments != null && !equipments.isEmpty()) {
            for (Equipment e : equipments) {
                dao.insertEquipment(person, e);
            }
        }
        session.commit();
    }

    public void updatePerson(Person person) {
        dao.deleteEquipments(person);
        dao.updatePerson(person);
        List<Equipment> equipments = person.getEquipments();
        if (equipments != null && !equipments.isEmpty()) {
            for (Equipment e : equipments) {
                dao.insertEquipment(person, e);
            }
        }
        session.commit();
    }

    public void deletePerson(Person person) {
        if (person != null && person.getId() != null && person.getId() != 0) {
            dao.deletePerson(person);
            dao.deleteEquipments(person);
            session.commit();
        }
    }

    public void savePerson(Person person) {
        if (person == null) {
            return;
        }
        if (person.getId() == null || person.getId() == 0) {
            insertPerson(person);
        }
        else {
            updatePerson(person);
        }
    }

//    public static void main(String[] args) {
//        DatabaseService database = DatabaseService.getInstance();
//        Person person = PersonFactory.newPerson(PersonRace.HUMAN, "qqq");
//        List<Equipment> equipments = person.getEquipments();
//        for (int i = 0; i < 3; i++) {
//            equipments.add(EquipmentFactory.newEquip());
//        }
//        database.insertPerson(person);
//
//        List<Person> persons = database.getAll();
//
//        Person deletePerson = null;
//        for (Person p : persons) {
//            System.out.println(p);
//            if (p.getName().equals("uuu")) {
//                deletePerson = p;
//            }
//        }
//
//        database.deletePerson(deletePerson);
//
//        System.out.println();
//        persons = database.getAll();
//        for (Person p : persons) {
//            System.out.println(p);
//        }
//
//        database.close();
//    }
}
