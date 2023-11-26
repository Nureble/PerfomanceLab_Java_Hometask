public class Dog extends Animal {
    public Dog(String name, int runLimit, int swimLimit) {
        super(name, Math.min(runLimit, 500), Math.min(swimLimit, 10));
    }

    @Override
    public void run(int distance) {
        System.out.println(name + " пробежала " + Math.min(distance, runLimit) + " м");
    }

    @Override
    public void swim(int distance) {
        System.out.println(name + " проплыла " + Math.min(distance, swimLimit) + " м");
    }
}

