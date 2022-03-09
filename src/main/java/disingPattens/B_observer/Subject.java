package disingPattens.B_observer;

public interface Subject {
    public void registerObserver(Observer observer);
    public void removeObserver(Observer observer);
    public void notifyObserver();
}

interface Observer{
    public void update(float temp, float humidity);
}
interface DisplayElement{
    public void display();
}