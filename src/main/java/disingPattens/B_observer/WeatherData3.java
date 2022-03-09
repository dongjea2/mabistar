package disingPattens.B_observer;

import java.util.ArrayList;
import java.util.List;

public class WeatherData3  implements Subject{
    List<Observer> observers = new ArrayList<Observer>();
    private float temp;
    private float hum;



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
        for(Observer observer : observers){
            observer.update(temp,hum);
        }
    }

    public void measureDataChanged(){
        notifyObserver();
    }

    public void setMeasureData(float temp, float humidity){
        this.temp = temp;
        this.hum = humidity;
        measureDataChanged();
    }

    public static void main(String[] args) {
        WeatherData3  wd3 = new WeatherData3();
        CurrentDisplayd cd = new CurrentDisplayd(wd3);
    }
}

class CurrentDisplayd implements Observer{
    private float temp;
    private float hum;
    Subject weatherData;

    public CurrentDisplayd(Subject weatherData) {
        this.weatherData = weatherData;
        weatherData.registerObserver(this);
    }

    @Override
    public void update(float temp, float humidity) {
        this.temp = temp;
        this.hum= humidity;
        display();
    }

    public void display(){
        System.out.printf("온도 : " + temp);
        System.out.printf("습도 : " + hum);
        System.out.println();
    }
}