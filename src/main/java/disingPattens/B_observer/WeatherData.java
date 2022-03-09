package disingPattens.B_observer;

public class WeatherData {
    public void measurementsChanged(){
        float temp = getTemperature();
        float humidity = getHumidity();

        currentConditionsDisplay(temp,humidity);
        statisticsDisplay(temp,humidity);
        forecastDisplay(temp,humidity);
    }



    private float getTemperature(){
        return (float) 17.1;
    }

    private float getHumidity(){
        return (float) 55.5;
    }

    private void currentConditionsDisplay(float temp, float humididy){
        System.out.println("온도:"+temp+"습도:"+humididy);
    }
    private void statisticsDisplay(float temp, float humididy){
        System.out.println("온도:"+temp+"습도:"+humididy);
    }
    private void forecastDisplay(float temp, float humididy){
        System.out.println("온도:"+temp+"습도:"+humididy);
    }

    public static void main(String[] args) {
        WeatherData wheatherData = new WeatherData();
        wheatherData.measurementsChanged();
    }

}
