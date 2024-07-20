package com.test.integration_tests;


import com.test.integration_tests.resources.pojos.CurrentWeather;
import com.test.integration_tests.resources.pojos.GeocodingAPIResponse;
import com.test.integration_tests.resources.pojos.WeatherResponse;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import static io.restassured.RestAssured.given;
@slf4j
public class WeatherAutomation {
	@BeforeClass
	public static void setup() {
		// Base URI setup
		RestAssured.baseURI = API_BASE_URL;
	}
	private static final String API_KEY = "f671dc7755fd849ae2977dc98f526a3f";
	private static final String API_BASE_URL = "http://api.openweathermap.org/geo/1.0/direct";
	private static final String WEATHER_API_URL="https://api.openweathermap.org/data/3.0/onecall";
	private static final int cityColumn =0,humidityColumn =1,pressureColumn =2,tempColumn =3,windSpeedColumn =4,windGustColumn =5,windDegColumn=6;
	@BeforeClass
	public void setUp() {
		RestAssured.baseURI = API_BASE_URL;
	}

	@Test
	public void writeWeatherDetailsToCityStatsFile() throws IOException {
		List<String> cityData= readCitiesFromCSV("src/main/resources/city.csv");
		List<String[]> cityStatsData= readCSVFile("src/main/resources/city_stats.csv");
		Map<String , Double> weatherDetails = null;
		int i=1;
		for(String city:cityData.subList(1,cityData.size()-1)){
			GeocodingAPIResponse[] geocodingAPIResponse =getGeoData(city);
			WeatherResponse weatherResponse = getWeatherData(geocodingAPIResponse[0].getLon(),geocodingAPIResponse[0].getLat());
			cityStatsData.get(i)[cityColumn]=city;
			cityStatsData.get(i)[humidityColumn]=city;
			cityStatsData.get(i)[pressureColumn]=city;
			cityStatsData.get(i)[tempColumn]=city;
			cityStatsData.get(i)[windSpeedColumn]=city;
			cityStatsData.get(i)[windGustColumn]=city;
			cityStatsData.get(i)[windDegColumn]=city;
		}
//update csv with city weather details
		writeCSVFile(cityStatsData, "src/main/resources/city_stats.csv");
	}

	@Test
	public void testTopColdestCities() throws IOException {
		List<String> cityData= readCitiesFromCSV("src/main/resources/city.csv");
		List<String[]> cityStatsData= readCSVFile("src/main/resources/city_stats.csv");
		String coldestCity="None"; double temperature=10000, tempTemperature=10000;
		for(String city:cityData.subList(1,cityData.size()-1)){
		GeocodingAPIResponse[] geocodingAPIResponse =getGeoData(city);
		WeatherResponse weatherResponse = getWeatherData(geocodingAPIResponse[0].getLon(),geocodingAPIResponse[0].getLat());
			tempTemperature=weatherResponse.getCurrent().getTemp();
       if(tempTemperature<temperature){
		   temperature=tempTemperature;
		   coldestCity=city;
	   }
		}
		log.info(coldestCity);
	}

	@Test
	public void testTopHighestTempCities() throws IOException {
		List<String> cityData= readCitiesFromCSV("src/main/resources/city.csv");
		List<String[]> cityStatsData= readCSVFile("src/main/resources/city_stats.csv");

		String hottestCity="None"; double temperature=-10000, tempTemperature=-10000;

		for(String city:cityData.subList(1,cityData.size()-1)){
			GeocodingAPIResponse[] geocodingAPIResponse =getGeoData(city);
			WeatherResponse weatherResponse = getWeatherData(geocodingAPIResponse[0].getLon(),geocodingAPIResponse[0].getLat());

			if(tempTemperature>temperature){
				temperature=tempTemperature;
				hottestCity=city;
			}
		}
		log.info(hottestCity);

	}

	@Test
	public void testTopHighestPressureCities() throws IOException {
		List<String> cityData= readCitiesFromCSV("src/main/resources/city.csv");
		List<String[]> cityStatsData= readCSVFile("src/main/resources/city_stats.csv");

		String highestPrssureCity="None"; double pressure=10000, tempPressure=10000;
		for(String city:cityData.subList(1,cityData.size()-1)){
			GeocodingAPIResponse[] geocodingAPIResponse =getGeoData(city);
			WeatherResponse weatherResponse = getWeatherData(geocodingAPIResponse[0].getLon(),geocodingAPIResponse[0].getLat());
			if(tempPressure>pressure){
				pressure=tempPressure;
				highestPrssureCity=city;
			}
		}
		log.info(highestPrssureCity);

	}

	@Test
	public void testTopHighestHumidityCities() throws IOException {
		List<String> cityData= readCitiesFromCSV("src/main/resources/city.csv");
		List<String[]> cityStatsData= readCSVFile("src/main/resources/city_stats.csv");

		String highestHumidityCity="None"; double humidity=10000, tempHumidity=10000;
		for(String city:cityData.subList(1,cityData.size()-1)){
			GeocodingAPIResponse[] geocodingAPIResponse =getGeoData(city);
			WeatherResponse weatherResponse = getWeatherData(geocodingAPIResponse[0].getLon(),geocodingAPIResponse[0].getLat());
			if(tempHumidity>humidity){
				humidity=tempHumidity;
				highestHumidityCity=city;
			}
		}
		log.info(highestHumidityCity);

	}

	@Test
	public void testTopHighestWindDegCities() throws IOException {
		List<String> cityData= readCitiesFromCSV("src/main/resources/city.csv");
		List<String[]> cityStatsData= readCSVFile("src/main/resources/city_stats.csv");

		String highestWindDegCity="None"; double windDeg=10000, tempWindDeg=10000;
		for(String city:cityData.subList(1,cityData.size()-1)){
			GeocodingAPIResponse[] geocodingAPIResponse =getGeoData(city);
			WeatherResponse weatherResponse = getWeatherData(geocodingAPIResponse[0].getLon(),geocodingAPIResponse[0].getLat());
			if(tempWindDeg>windDeg){
				windDeg=tempWindDeg;
				highestWindDegCity=city;
			}
		}
		log.info(highestWindDegCity);
	}

	@Test
	public void testTopHighestWindGustCities() throws IOException {
		List<String> cityData= readCitiesFromCSV("src/main/resources/city.csv");
		List<String[]> cityStatsData= readCSVFile("src/main/resources/city_stats.csv");

		String highestWindGustCity="None"; double windGust=10000, tempWindGust=10000;
		for(String city:cityData.subList(1,cityData.size()-1)){
			GeocodingAPIResponse[] geocodingAPIResponse =getGeoData(city);
			WeatherResponse weatherResponse = getWeatherData(geocodingAPIResponse[0].getLon(),geocodingAPIResponse[0].getLat());
			if(tempWindGust>windGust){
				windGust=tempWindGust;
				highestWindGustCity=city;
			}
		}
		log.info(highestWindGustCity);
	}

	@Test
	public void testTopHighestWindSpeedCities() throws IOException {
		List<String> cityData= readCitiesFromCSV("src/main/resources/city.csv");
		List<String[]> cityStatsData= readCSVFile("src/main/resources/city_stats.csv");

		String highestWindSpeedCity="None"; double windSpeed=10000, tempWindSpeed=10000;
		for(String city:cityData.subList(1,cityData.size()-1)){
			GeocodingAPIResponse[] geocodingAPIResponse =getGeoData(city);
			WeatherResponse weatherResponse = getWeatherData(geocodingAPIResponse[0].getLon(),geocodingAPIResponse[0].getLat());
			if(tempWindSpeed>windSpeed){
				windSpeed=tempWindSpeed;
				highestWindSpeedCity=city;
			}
		}
		log.info(highestWindSpeedCity);
	}

	private GeocodingAPIResponse[] getGeoData(String city) {
		Response response = given()
				.param("q", city)
				.param("limit", 1)
				.param("appid", API_KEY)
				.get(API_BASE_URL);

		return response.as(GeocodingAPIResponse[].class);
	}
	private WeatherResponse getWeatherData(double lon, double lat) {
		Response response = given()
				.param("lat", lat)
				.param("lon", lon)
				.param("appid", API_KEY)
				.get(WEATHER_API_URL);

		return response.as(WeatherResponse.class);
	}
	private List<String> readCitiesFromCSV(String filePath) {
		List<String> cities = new ArrayList<>();
		try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
			String line;
			while ((line = reader.readLine()) != null) {
				cities.add(line.trim());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return cities;
	}
	// Read CSV file and return data as List of String arrays
	private static List<String[]> readCSVFile(String filePath) throws IOException {
		List<String[]> records = new ArrayList<>();
		try (CSVParser parser = CSVParser.parse(new FileReader(filePath), CSVFormat.DEFAULT)) {
			for (CSVRecord csvRecord : parser) {
				String[] values = new String[csvRecord.size()];
				for (int i = 0; i < csvRecord.size(); i++) {
					values[i] = csvRecord.get(i);
				}
				records.add(values);
			}
		}
		return records;
	}

	// Write updated data back to CSV file
	private static void writeCSVFile(List<String[]> data, String filePath) throws IOException {
		try (Writer writer = new FileWriter(filePath)) {
			for (String[] record : data) {
				writer.write(String.join(",", record) + "\n");
			}
		}
	}
}
