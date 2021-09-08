package edu.escuelaing.arep;

public class WeatherService{
	
	private String cityDefaultWS = "London";
	
	public String getUrlWS() {
		return "https://api.openweathermap.org/data/2.5/weather?q="+cityDefaultWS+"&appid=510c1340400b07fa2fda12d28b1d34e2";
	}
	
	public void setcityDWS(String newCityUserSearch) {
		cityDefaultWS = newCityUserSearch;
	}
	
}
