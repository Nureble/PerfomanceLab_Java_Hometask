public class DomesticCat extends Animal {
    public DomesticCat(String name, int runLimit, int swimLimit) {
        super(name, Math.min(runLimit, 200), swimLimit);
    }

    @Override
    public void run(int distance) {
        System.out.println(name + " пробежал " + Math.min(distance, runLimit) + " м");
    }

    @Override
    public void swim(int distance) {
        System.out.println(name + " не умеет плавать");
    }
}
