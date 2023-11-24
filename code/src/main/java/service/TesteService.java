package service;

import spark.Request;
import spark.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;


public class TesteService {
    public int[] getValuesFromAzureML() {
        int[] resultValues = null;
        try {
            String url = "https://ussouthcentral.services.azureml.net/workspaces/82f549a0cf89466bbac338c6e30ebcf3/services/bc8661eb22334cbaabd301685846edd8/execute?api-version=2.0&details=true";
            String apiKey = "nEqm0BF85BjLLjm3bZvdYrZzXCyNaDUagpyxy8GVIOlqZgBCtgn7CfM6XuwANf/OxN1lg86PtNW7+AMC1nh+sw==";
            String inputData = "{\"Inputs\": {\"input1\": {\"ColumnNames\": [\"Col1\", \"Col2\", \"Col3\", \"Col4\", \"Col5\", \"Col6\", \"Col7\"],\"Values\": [[\"1\", \"value\", \"5\", \"value\", \"3\", \"value\", \"0\"],[\"1\", \"value\", \"876\", \"value\", \"5\", \"value\", \"0\"],[\"1\", \"value\", \"2000\", \"value\", \"3\", \"value\", \"0\"],[\"1\", \"value\", \"1200\", \"value\", \"1\", \"value\", \"0\"],[\"2\", \"value\", \"1207\", \"value\", \"5\", \"value\", \"0\"],[\"3\", \"value\", \"1207\", \"value\", \"1\", \"value\", \"0\"]]}},\"GlobalParameters\": {}}";

            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            // Set the request method and headers
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("Authorization", "Bearer " + apiKey);

            // Enable sending data
            con.setDoOutput(true);

            // Write data to the request body
            con.getOutputStream().write(inputData.getBytes());

            // Get the response
            int responseCode = con.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                StringBuilder response = new StringBuilder();
                String inputLine;

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                JSONObject jsonResponse = new JSONObject(response.toString());
                JSONObject output1 = jsonResponse.getJSONObject("Results").getJSONObject("output1");
                JSONArray valuesArray = output1.getJSONObject("value").getJSONArray("Values");

                if (valuesArray.length() > 0) {
                    JSONArray firstRow = valuesArray.getJSONArray(0);
                    resultValues = new int[firstRow.length() - 1]; // Excluding the first string value (User)
                    for (int i = 1; i < firstRow.length(); i++) {
                        resultValues[i - 1] = firstRow.getInt(i);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultValues;
    }

    public Object testar(Request resquest, Response response) {
        int[] values = getValuesFromAzureML();
        String str = "";
        for (int i = 0; i < 4; i++) {
        	
        	str+= "<div class=\"cards\">";
            str+="<a href=\"/detalhes/"+values[i]+"/movie>";
            str+="<img src=\"https://image.tmdb.org/t/p/w500$"+getFilmebyId(values[i], "movie")[2]+">";
            str+="<h5>${title}</h5>";
            str+="</a>";
            str+="</div>";
        }
        
        return 0;
    }
    
public String[] getFilmebyId(int id, String type) {
		
		
		String resp[] = new String[6];
		try {
			
			
            URL url = new URL("https://api.themoviedb.org/3/"+type+"/"+id+"?api_key=f83978a258b7d74f92d6b0df55d9bb5d&language=pt-BR");

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            //Check if connect is made
            int responseCode = conn.getResponseCode();

            // 200 OK
            if (responseCode != 200) {
                throw new RuntimeException("HttpResponseCode: " + responseCode);
            } else {
            	
            	Scanner scanner = new Scanner(url.openStream());
            	
                StringBuilder informationString = new StringBuilder();
                

                while (scanner.hasNext()) {
                    informationString.append(scanner.nextLine());
                }
                //Close the scanner
                scanner.close();

                //System.out.println(informationString);


                //JSON simple library Setup with Maven is used to convert strings to JSON
                JSONParser parser = new JSONParser();
                Object obj = parser.parse(String.valueOf(informationString));
                //System.out.println(obj);
                if((String.valueOf(((JSONObject)obj).get("title"))).equals("null")) {
                resp[0] =(String.valueOf(((JSONObject)obj).get("name"))); }
                else {
                	resp[0] =(String.valueOf(((JSONObject)obj).get("title")));
                }
                
              
                resp[1] = (String.valueOf(((JSONObject)obj).get("overview")));
                resp[2] = (String.valueOf(((JSONObject)obj).get("poster_path")));
                
                System.out.println(resp[0]);
            }
     
                


			} catch (Exception e) {
            	e.printStackTrace();
        	}
		
		
		return resp;
	
	}
}