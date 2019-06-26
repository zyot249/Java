package InheritanceTest;

abstract public class Human implements Interface{
    public void talk(){
        say();
    }
    abstract void say();
}
