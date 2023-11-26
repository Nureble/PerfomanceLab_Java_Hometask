import java.util.Random;

public abstract class Animal {
    protected static int totalAnimals = 0;
    protected static int dogCount = 0;
    protected static int catCount = 0;
    protected static int tigerCount = 0;

    protected static int squirrelCount = 0;
    protected static int bearCount = 0;
    protected static int capybaraCount = 0;

    protected String name;
    protected int runLimit;
    protected int swimLimit;

    private static int minCount = 1;

    public static void setMinCount(int minCount) {
        Animal.minCount = minCount;
    }

    private static int randomCount() {
        return new Random().nextInt(3) + 1; // теперь минимальное значение всегда 1
    }


    public Animal(String name, int runLimit, int swimLimit) {
        this.name = name;
        this.runLimit = runLimit;
        this.swimLimit = swimLimit;
        totalAnimals++;

        if (this instanceof Dog) {
            dogCount++;
        } else if (this instanceof DomesticCat) {
            catCount++;
        } else if (this instanceof Tiger) {
            tigerCount++;
        }
    }

    public void run(int distance) {
        int actualDistance = Math.min(distance, runLimit);
        System.out.println(name + " пробежал " + actualDistance + " м");
    }

    public void swim(int distance) {
        int actualDistance = Math.min(distance, swimLimit);
        System.out.println(name + (swimLimit > 0 ? " проплыл " + actualDistance + " м" : " не умеет плавать"));
    }


    public static Animal createRandomAnimal() {
        Random random = new Random();
        int randomDistance = random.nextInt(1000) + 1;

        int count = randomCount();

        if (count > 0) {
            Animal[] animals = new Animal[count];

            for (int i = 0; i < count; i++) {
            if (random.nextBoolean()) {
                animals[i] = new Dog("Собака", 500, 10);
            } else if (random.nextBoolean()) {
                animals[i] = new DomesticCat("Домашний Кот", 200, 0);
            } else if (random.nextBoolean()) {
                animals[i] = new Tiger("Тигр", 1000, 50);
            } else if (random.nextBoolean()) {
                animals[i] = new Squirrel("Белка", 100, 5);
            } else if (random.nextBoolean()) {
                animals[i] = new Bear("Медведь", 300, 20);
            } else {
                animals[i] = new Capybara("Капибара", 150, 30);
            }
        }

            return animals[random.nextInt(count)];
        } else {
            return null; // или что-то еще, в зависимости от вашей логики
        }
    }



    public static int getDogCount() {
        return dogCount;
    }

    public static int getCatCount() {
        return catCount;
    }

    public static int getTigerCount() {
        return tigerCount;
    }

    public static int getTotalAnimals() {
        return totalAnimals;
    }


    public static int getSquirrelCount() {
        return squirrelCount;
    }

    public static int getBearCount() {
        return bearCount;
    }

    public static int getCapybaraCount() {
        return capybaraCount;
    }
}