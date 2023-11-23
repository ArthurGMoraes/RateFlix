package service;

import java.util.Scanner;

import java.io.File;


import java.util.List;

import dao.AvaliacaoDAO;
import dao.FilmeDAO;
import dao.UserDAO;
import model.Avaliacao;
import model.Discussao;
import model.Filme;
import model.Usuario;
//import service.AvaliacaoService;
import spark.Request;
import spark.Response;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.text.DecimalFormat;

import java.net.HttpURLConnection;
import java.net.URL;




public class FilmeService {

	private final DecimalFormat df = new DecimalFormat("0.00");
	//private FilmeDAO filmeDAO = new FilmeDAO();
	private AvaliacaoDAO avaliacaoDAO = new AvaliacaoDAO();
	private UserDAO userDAO = new UserDAO();
	//private AvaliacaoService avaliacao = new AvaliacaoService();
	private String form3;
	private int testeId;
	private String testeType;
	private String testeForm;
	private final int FORM_INSERT = 1;
	private final int FORM_ORDERBY_ID = 1;
	private String usuarioGlobal;
	
	
	public Object makeFilme(Request request, Response response) {
		
		String idS;
		int id = 0;
		String type = "";
		System.out.println(request.params());
		usuarioGlobal = (request.session().attribute("authenticatedUser"));
		if((request.params(":id") ==null ) ){
		 id = testeId;
		 type = testeType;
		} else {
		 idS = request.params(":id");
		 System.out.println(idS);
		 id = testeId= Integer.parseInt(idS);
		 type =testeType= (request.params(":type"));
		}
		//System.out.println(testeType + " AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
		
		String dados [] = getFilmebyId(id, type);
		
		String nomeArquivo = "src/main/resources/web/detalhesFilme/detalhesFilmes.html";
		form3 = "";
		if(!(request.params(":id") ==null ) ){
		try{
			Scanner entrada = new Scanner(new File(nomeArquivo));
				while(entrada.hasNext()){
			    	form3 += (entrada.nextLine() + "\n");
			    }
				testeForm = form3;
			
		    entrada.close();
		}  catch (Exception e) { System.out.println(e.getMessage()); }
		} else {
				form3 = testeForm;
		}
		
		String teste = "";
		teste += "<div id=\"tela\">"+
		"\n<img src=\"https://image.tmdb.org/t/p/w500"+dados[2]+"\" id=\"imagem\">" +
        "\n<div id=\"info\">" +
        "\n<h5 id=\"title\">"+ dados[0] +" </h5>" +
        "\n<p id=\"descricao\">" + dados[1] +" </p> "+
        "\n</div>"+
        "\n</div>";
		
		System.out.println(form3);
		System.out.println("passou" );
		//String busca = "<TESTE>";
		
		
		
		form3 = form3.replaceFirst("<bala>", teste);
		
		response.header("Content-Type", "text/html");
	    response.header("Content-Encoding", "UTF-8");
		return makeForm(request, response);
		
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
	

	
	
	public Object makeForm(Request request, Response response) {
		return makeForm(FORM_INSERT, new Discussao(), FORM_ORDERBY_ID, request, response);
	}

	public Object makeForm(int tipo, Discussao discussao, int order, Request request, Response response) {
	    String umDiscussao = "";
	    String action = "";
	    String name, buttonLabel;

	    action = "/avaliar";
	    name = "Avaliar";
	    buttonLabel = "Avaliar"; // Changed button label to be more appropriate

	    // Form for 5-star rating
	    umDiscussao += "\t<form class=\"form--register\" action=\"" + action + "\" method=\"post\" id=\"atualizar\">";
	    
	    umDiscussao += "\t<table width=\"10%\" bgcolor=\"#00000\" align=\"center\">";
	   
	    umDiscussao += "\t\t<tr>";
	    umDiscussao += "\t\t\t<td colspan=\"3\" align=\"left\"><font size=\"+2\"><resp><b id=\"aval\">&nbsp;&nbsp;&nbsp;" + name + "</b></font></td>";
	    umDiscussao += "\t\t</tr>";
	    umDiscussao += "\t\t<tr>";
	    umDiscussao += "\t\t</tr>";
	    umDiscussao += "\t\t<tr>";
	    
	    umDiscussao += "\t\t\t<td class=\"tdrat\">";
	   
	    umDiscussao += "\t\t\t  <div class=\"rating\">";
	    for (int i = 1; i <= 5; i++) {
	        umDiscussao += "\t\t\t    <input type=\"radio\" id=\"star" + i + "\" name=\"rating\" value=\"" + i + "\" />";
	        umDiscussao += "\t\t\t    <label for=\"star" + i + "\">"+"</label>";
	    }
	    umDiscussao += "\t\t\t  </div>";
	    umDiscussao += "\t\t\t</td>";
	    umDiscussao += "\t\t\t<td align=\"left\"><input type=\"submit\" value=\"" + buttonLabel + "\" class=\"input--main__style input--button\"></td>";
	   
	    umDiscussao += "\t\t</tr>";
	    
	    umDiscussao += "\t</table>";
	    umDiscussao += "\t</form>";
	   

	    form3 = form3.replaceFirst("<TESTE>", umDiscussao);

	    List<Avaliacao> avaliacoes;
	    avaliacoes = avaliacaoDAO.getOrderByID();

	    String list = "";
	    double soma = 0.0;
	    int count = 0;
	    int val;
	    int id = testeId;

	    for (Avaliacao p : avaliacoes) {
	        val = p.getId_filme();
	        if (val == id) {
	            soma += (double) p.getValor();
	            count++;
	            //System.out.println(soma + "AAAAAAAA" + count);
	        }
	    }
	    if (count == 0) {
	        count = 1;
	    }
	    double result = soma / count;
	    list = "<h2 class=\"avaliacao\">Nota: " + df.format(result) + "</h2>";

	    form3 = form3.replaceFirst("<resp>", list);

	    return form3;
	}
	
	
	public Object insert(Request request, Response response) {
		int titulo = Integer.parseInt(request.queryParams("rating"));

		//int autor = 0;
		
		Avaliacao avaliacao = new Avaliacao(-1, titulo,userDAO.getByNome(usuarioGlobal).getId(), testeId);
		
		String resp = "";
		
		if(avaliacaoDAO.insert(avaliacao) == true) {
			resp = "Discussao" +"( "  + titulo + " )" + "inserido!";
            response.status(201); // 201 Created
		} else {
			resp = "Discussao" +"( "  + titulo + " )" + "não inserido!";
			response.status(404); // 404 Not found
		}
			
		makeFilme(request, response);
		return form3;
	}
	
	
	
	public Object getAll(Request request, Response response) {
	
		makeFilme(request, response);
	    response.header("Content-Type", "text/html");
	    response.header("Content-Encoding", "UTF-8");
		return form3;
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