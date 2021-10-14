package ru._21_school.swingy.model;

import ru._21_school.swingy.model.equipment.AidFactory;
import ru._21_school.swingy.model.equipment.BlockFactory;
import ru._21_school.swingy.model.person.Person;
import ru._21_school.swingy.model.person.PersonFactory;

/**
 * level a hero of level 7 will be placed on a 39X39 map
 * (level-1)*5+10 -( ? =>
 * use (5 * level + 4)
 */

public class Area {

    private int level;
    private int size;
    private Person hero;
    private Object[][] area;

    public Area(int level, Person hero) {
        this.hero = hero;
        this.level = level;
        size = 5 * level + 4;
        hero.setPosition(new Coordinate(size / 2, size / 2));
        area = new Object[size][size];
        fillAids();
        fillBlocks();
        fillEnemies();
    }

    private void fillAids() {
        int aidAmount = size / 2 + 1 + (int)(Math.random() * size / 2);
        for (int i = 0; i < aidAmount; i++) {
            int x = (int) (Math.random() * size);
            int y = (int) (Math.random() * size);
            Object obj = getObject(x, y);
            if (obj == null && (x != getHeroPositionX() || y != getHeroPositionY())) {
                area[x][y] = AidFactory.newAid();
            }
        }
    }

    private void fillBlocks() {
        int blockAmount = size / 2 + 1 + (int)(Math.random() * size);
        for (int i = 0; i < blockAmount; i++) {
            int x = (int) (Math.random() * size);
            int y = (int) (Math.random() * size);
            Object obj = getObject(x, y);
            if (obj == null && (x != getHeroPositionX() || y != getHeroPositionY())) {
                area[x][y] = BlockFactory.newBlock();
            }
        }
    }

    private void fillEnemies() {
        int enemyAmount = (int) (Math.pow(size, 2) / 8);
        for (int i = 0; i < enemyAmount; i++) {
            int x = (int) (Math.random() * size);
            int y = (int) (Math.random() * size);
            Object obj = getObject(x, y);
            if (obj == null && (x != getHeroPositionX() || y != getHeroPositionY())) {
                area[x][y] = PersonFactory.newEnemy(hero);
            }
        }
    }

    public Object getObject(int x, int y) {
        if (x < area.length && y < area.length) {
            return area[x][y];
        }
        return null;
    }

    public void delete(int x, int y) {
        area[x][y] = null;
    }

    public Person getHero() {
        return hero;
    }

    public Object[][] getArea() {
        return area;
    }

    public int getSize() {
        return size;
    }

    public int getHeroPositionX() {
        return hero.getPosition().getX();
    }

    public int getHeroPositionY() {
        return hero.getPosition().getY();
    }

    public void moveHero(int dX, int dY) {
        int oldX = hero.getPosition().getX();
        int oldY = hero.getPosition().getY();
        int newX = oldX + dX;
        int newY = oldY + dY;

        if (getObject(newX, newY) == null) {
            hero.getPosition().setX(newX);
            hero.getPosition().setY(newY);
        }
    }

    public int getLevel() {
        return level;
    }
}