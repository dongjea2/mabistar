package disingPattens.A_strategy;

public abstract class Duck {

    //변하는 부분
    FlyBehavior flyBehavior;
    QuackBehavior quackBehavior;

    //행동 클레스에 위임
    public void performFly(){
        flyBehavior.fly();
    }

    public void performQuack(){
        quackBehavior.quack();
    }

    //변하지 않는 부분
    void swim(){
        System.out.printf("수영하는 오리");
    }
    void display(){
        System.out.printf("저는 오리입니다.");
    }
}

class RubberDuck extends Duck{
    @Override
    void display() {
        System.out.printf("저는 고무오리 입니다.");
    }
}

class MallardDuck extends Duck{
    public MallardDuck() {
        quackBehavior = new Quack();
        flyBehavior = new FlyWithWing();
    }
}

interface FlyBehavior {
    void fly();
}

class FlyWithWing implements FlyBehavior{
    @Override
    public void fly() {
        System.out.printf("저 날고 있어요");
    }
}
class FlyNoWay implements FlyBehavior{
    @Override
    public void fly() {
        System.out.printf("저는 못날아요");
    }
}


interface  QuackBehavior{
    void quack();
}

class Quack implements QuackBehavior{
    @Override
    public void quack() {
        System.out.printf("꽥!");
    }
}
class Squeak implements QuackBehavior{
    @Override
    public void quack() {
        //고무오리 소리
        System.out.printf("삑삑!");
    }
}
class MuteQuack implements QuackBehavior{
    @Override
    public void quack() {
        System.out.printf("아무 소리도 안남");
    }
}