package disingPattens.C_decorator;

public abstract class Beverage2 {
    String description;
    String milk;
    String soy;
    String mocha;
    String whip;

    public String getDescription() {
        return description;
    }
    public boolean hasMilk(){
        return false;
    }
}
