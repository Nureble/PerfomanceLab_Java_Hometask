public class Squirrel extends Animal {
    public Squirrel(String name, int runLimit, int swimLimit) {
        super(name, runLimit, swimLimit);
        squirrelCount++;
    }
}
