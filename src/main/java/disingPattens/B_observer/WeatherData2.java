package disingPattens.B_observer;

import java.util.ArrayList;
import java.util.List;

public class WeatherData2 implements Subject{
    private List<Observer> observers;
    private float temp;
    private float humidity;

    public WeatherData2() {
        observers = new ArrayList<>();
    }

    @Override
    public void registerObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObserver() {
        for( Observer observer : observers){
            observer.update(temp,humidity);
        }
    }

    public void measurementsChanged(){
        notifyObserver();
    }

    public void setMeasurements(float temp, float humidity){
        this.temp = temp;
        this.humidity = humidity;
        measurementsChanged();
    }

    public static void main(String[] args) {
        WeatherData2 wd = new WeatherData2();

        CurrentConditionsDisplay cd = new CurrentConditionsDisplay(wd);
        wd.setMeasurements((float) 30,(float) 44.5);
        wd.setMeasurements((float) 20,(float) 24.5);
        wd.setMeasurements((float) 10,(float) 64.5);
    }
}

//
//display
class CurrentConditionsDisplay implements Observer, DisplayElement{
    private float temp;
    private float humidity;
    private Subject weatherData;

    //옵저버 등록하는 생성자
    public CurrentConditionsDisplay(Subject weatherData) {
        this.weatherData = weatherData;
        weatherData.registerObserver(this);
    }

    @Override
    public void update(float temp, float humidity) {
        this.temp = temp;
        this.humidity = humidity;
        display();
    }

    @Override
    public void display() {
        System.out.printf("온도:"+this.temp);
        System.out.printf("습도:"+this.humidity);
        System.out.println();
    }
}