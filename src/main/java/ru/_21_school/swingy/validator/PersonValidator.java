package ru._21_school.swingy.validator;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.hibernate.validator.messageinterpolation.ParameterMessageInterpolator;
import ru._21_school.swingy.model.equipment.Equipment;
import ru._21_school.swingy.model.person.Person;

import java.util.List;
import java.util.Set;

public class PersonValidator {

    private final Validator validator = Validation.byDefaultProvider()
            .configure()
            .messageInterpolator(new ParameterMessageInterpolator())
            .buildValidatorFactory()
            .getValidator();

    public boolean validate(Person person) {

        Set<ConstraintViolation<Person>> personViolations = validator.validate(person);

        if (personViolations.size() > 0) {
            for (ConstraintViolation<Person> violation : personViolations) {
                System.err.println(violation.getMessage());
            }
            return false;
        }

        Set<ConstraintViolation<Equipment>> equipmentViolations;
        List<Equipment> equipments = person.getEquipments();
        for (Equipment e : equipments) {
            equipmentViolations = validator.validate(e);
            if (equipmentViolations.size() > 0) {
                for (ConstraintViolation<Equipment> violation : equipmentViolations) {
                    System.err.println(violation.getMessage());
                }
                return false;
            }
        }

        return true;
    }
}
