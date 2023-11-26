public class Tiger extends Animal {
    public Tiger(String name, int runLimit, int swimLimit) {
        super(name, runLimit, Math.min(swimLimit, 50));
    }


    @Override
    public void run(int distance) {
        System.out.println(name + " пробежал " + Math.min(distance, runLimit) + " м");
    }

    @Override
    public void swim(int distance) {
        System.out.println(name + " проплыл " + Math.min(distance, swimLimit) + " м");
    }
}
