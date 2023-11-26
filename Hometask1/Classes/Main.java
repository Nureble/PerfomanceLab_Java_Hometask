public class Main {
    public static void main(String[] args) {
        Animal[] animals = new Animal[13];

        for (int i = 0; i < animals.length; i++) {
            animals[i] = Animal.createRandomAnimal();
        }

        for (Animal animal : animals) {
            int randomDistance = (int) (Math.random() * 1000) + 1;
            animal.run(randomDistance);
            animal.swim(randomDistance);
        }

        System.out.println("Количество созданных домашних котов: " + Animal.getCatCount());
        System.out.println("Количество созданных собак: " + Animal.getDogCount());
        System.out.println("Количество созданных тигров: " + Animal.getTigerCount());
        System.out.println("Количество созданных белок: " + Animal.getSquirrelCount());
        System.out.println("Количество созданных медведей: " + Animal.getBearCount());
        System.out.println("Количество созданных капибар: " + Animal.getCapybaraCount());
        System.out.println("Общее количество созданных животных: " + Animal.getTotalAnimals());
    }
}
