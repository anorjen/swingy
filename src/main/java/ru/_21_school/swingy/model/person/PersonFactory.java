package ru._21_school.swingy.model.person;

import ru._21_school.swingy.view.swingUI.GameImages;

public class PersonFactory {
    public static Person newPerson(PersonRace race, String name) {
        Person person = new Person(name, race.name(),race.getHitPoints(), race.getAttack(), race.getDefense(), race.getAgility());
        person.setIcon(GameImages.getInstance().getPersonIcon(race.name()));
        person.setLabel(GameImages.getInstance().getPersonLabel(race.name()));
        return person;
    }

    public static Person newEnemy(Person hero) {
        int heroLevel = hero.getLevel();

        int minEnemyLevel = Math.max(heroLevel - 2, 0);
        int maxEnemyLevel = heroLevel + 2;
        int enemyLevel = (int) (minEnemyLevel + Math.random() * (maxEnemyLevel - minEnemyLevel));
        PersonRace[] races = PersonRace.values();
        PersonRace enemyRace = races[(int) (Math.random() * races.length)];

        Person enemy = new Person("Enemy", enemyRace.name(),enemyRace.getHitPoints(), enemyRace.getAttack(), enemyRace.getDefense(), enemyRace.getAgility());
        for (int i = 0; i < enemyLevel; i++) {
            enemy.levelUp();
        }
        enemy.setExperience((int) (enemyLevel * 1000 + Math.pow(enemyLevel - 1, 2) * 450));
        enemy.setIcon(GameImages.getInstance().getPersonIcon(enemy.getRace()));
        enemy.setLabel(GameImages.getInstance().getPersonLabel(enemy.getRace()));
        return enemy;
    }
}
