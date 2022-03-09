package disingPattens.C_decorator;

public abstract class Beverage {
    String description;
    abstract void cost();

    public String getDescription() {
        return description;
    }
}

class HouseBlend extends Beverage{
    @Override
    void cost() {
        System.out.printf("");
    }
}

class DarkRoast extends Beverage{
    @Override
    void cost() {
        System.out.printf("");
    }
}

class Decaf extends Beverage{
    @Override
    void cost() {
        System.out.printf("");
    }
}

class Espresso extends Beverage{
    @Override
    void cost() {
        System.out.printf("");
    }
}