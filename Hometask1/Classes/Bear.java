public class Bear extends Animal {
    public Bear(String name, int runLimit, int swimLimit) {
        super(name, runLimit, swimLimit);
        bearCount++;
    }
}
