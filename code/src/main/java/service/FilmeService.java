package service;

import java.util.Scanner;

import java.io.File;


import java.util.List;
import dao.FilmeDAO;
import model.Filme;
import spark.Request;
import spark.Response;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.net.HttpURLConnection;
import java.net.URL;




public class FilmeService {

	private FilmeDAO filmeDAO = new FilmeDAO();
	private String form;
	private String form3;
	private final int FORM_INSERT = 1;
	private final int FORM_ORDERBY_ID = 1;
	
	
	public Object makeFilme(Request request, Response response) {
		int id = Integer.parseInt(request.params(":id"));
		String type = (request.params(":type"));
		String dados [] = getFilmebyId(id, type);
		
		String nomeArquivo = "src/main/resources/web/detalhesFilme/detalhesFilmes.html";
		form3 = "";
		
		try{
			Scanner entrada = new Scanner(new File(nomeArquivo));
		    while(entrada.hasNext()){
		    	form3 += (entrada.nextLine() + "\n");
		    }
		    entrada.close();
		}  catch (Exception e) { System.out.println(e.getMessage()); }
		
		String teste = "";
		teste += "<div id=\"tela\">"+
		"\n<img src=\"https://image.tmdb.org/t/p/w500"+dados[2]+"\" id=\"imagem\">" +
        "\n<div id=\"info\">" +
        "\n<h5 id=\"title\">"+ dados[0] +" </h5>" +
        "\n<p id=\"descricao\">" + dados[1] +" </p> "+
        "\n</div>"+
        "\n</div>";
		
		//System.out.println(form3);
		//System.out.println(teste);
		//String busca = "<TESTE>";
		
		form3 = form3.replaceFirst("<bala>", teste);
		response.header("Content-Type", "text/html");
	    response.header("Content-Encoding", "UTF-8");
		return form3;
		
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
	

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/*public Object salvarFilmes(Request request, Response response, int i, int j) {
		String resp = "";
		if (i < 10000) {
		Filme filme = new Filme();
		
		 try {
			
			
            URL url = new URL("https://api.themoviedb.org/3/discover/movie?api_key=f83978a258b7d74f92d6b0df55d9bb5d&include_adult=false&include_video=false&language=en-US&page="+i+"&sort_by=popularity.desc");

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

                System.out.println(informationString);


                //JSON simple library Setup with Maven is used to convert strings to JSON
                JSONParser parser = new JSONParser();
                
                Object obj = parser.parse(String.valueOf(informationString));
                JSONArray array = new JSONArray();
                

                //Get the first JSON object in the JSON array
                System.out.println("\n" + obj);
                
                obj = parser.parse(String.valueOf(((JSONObject)obj).get("results")));
                if(j < 20) {
                obj = ((JSONArray)obj).get(j);
                j = j + 1;
       
                } 
                System.out.println("\n" + obj);
               
     
                
                int id = Long.valueOf((String.valueOf(((JSONObject)obj).get("id")))).intValue();
                String titulo = (String.valueOf(((JSONObject)obj).get("title")));
                String descricao = (String.valueOf(((JSONObject)obj).get("overview")));
                String tipo = "movie";
                int avaliacao = 0;
                String data = (String.valueOf(((JSONObject)obj).get("release_date")));
                String poster = (String.valueOf(((JSONObject)obj).get("poster_path")));;
                filme = new Filme(id, titulo, descricao, tipo, avaliacao, data, poster);
                
                if(filmeDAO.insert(filme) == true) {
        			resp = "filme" +"( "  + titulo + " )" + "inserido!";
                    response.status(201); // 201 Created
        		} else {
        			resp = "filme" +"( "  + titulo + " )" + "não inserido!";
        			response.status(404); // 404 Not found
        		}
        			
        		
            	
                if ( j < 19) {
          		  salvarFilmes(request, response, (i), j);}
          		  else {
          		 salvarFilmes(request, response, (i+1), 0);}
			}

			} catch (Exception e) {
            	e.printStackTrace();
        	}
		 }
        
		
		 
		
		 return form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");
    }
	
	public Object getAll(Request request, Response response) {
		
		salvarFilmes(request, response, 10, 0);
	    response.header("Content-Type", "text/html");
	    response.header("Content-Encoding", "UTF-8");
		return form;
	}*/
	
}