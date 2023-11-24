package service;

import java.util.Scanner;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.List;
import dao.DiscussaoDAO;
import dao.RespDAO;
import dao.AvaliacaoDAO;
import dao.FilmeDAO;
import dao.UserDAO;
import model.Avaliacao;
import model.Resp;
import model.Discussao;
import model.Filme;
import spark.Request;
import spark.Response;

import org.json.simple.parser.JSONParser;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;


import java.net.HttpURLConnection;
import java.net.URL;







public class DiscussaoService {

	private DiscussaoDAO discussaoDAO = new DiscussaoDAO();
	private RespDAO respDAO = new RespDAO();
	private UserDAO userDAO = new UserDAO();
	private AvaliacaoDAO avalDAO = new AvaliacaoDAO();
	//private FilmeDAO filmeDAO = new FilmeDAO();
	private String form;
	private String form2;
	private final int FORM_INSERT = 1;
	private final int FORM_ORDERBY_ID = 1;
	private int testeId;
	private String usuarioGlobal;
	
	public DiscussaoService() {
		makeForm();
	}
	
	public void makeForm() {
		makeForm(FORM_INSERT, new Discussao(), FORM_ORDERBY_ID);
	}

	public void makeForm(int tipo, Discussao discussao, int order) {
		String nomeArquivo = "src/main/resources/web/home/index.html";
		form = "";
		try{
			Scanner entrada = new Scanner(new File(nomeArquivo));
		    while(entrada.hasNext()){
		    	form += (entrada.nextLine() + "\n");
		    }
		    entrada.close();
		}  catch (Exception e) { System.out.println(e.getMessage()); }
		
		
		
		String umDiscussao = "";
		String action = "";
		
		String name, titulo, descricao, buttonLabel, action2;
		
		if(usuarioGlobal != null) {
			action = "/criar";
		} else {
			action = "/login";
		}
		action2 = "/";
		name = "Tópicos de discussão";
		titulo = "Titulo";
		descricao = "Conteúdo";
		buttonLabel = "Criar";

		umDiscussao += "\t<form class=\"form--register\" action=\"" + action + "\" method=\"post\" id=\"atualizar\">";
		umDiscussao += "\t<table width=\"80%\" bgcolor=\"#00000\" align=\"center\">";
		umDiscussao += "\t\t<tr>";
		umDiscussao += "\t\t\t<td colspan=\"3\" align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;" + name + "</b></font></td>";
		umDiscussao += "\t\t</tr>";
		umDiscussao += "\t\t<tr>";
		umDiscussao += "\t\t\t<td colspan=\"3\" align=\"left\">&nbsp;</td>";
		umDiscussao += "\t\t</tr>";
		umDiscussao += "\t\t<tr>";
		umDiscussao += "\t\t\t<td>&nbsp;Título: <input class=\"input--register\" type=\"text\" name=\"titulo\" placeholder=\"Titulo\" value=\"" +"\"></td>";
		umDiscussao += "\t\t\t<td>Descrição: <input class=\"input--register\" type=\"text\" name=\"descricao\" placeholder=\"Conteudo\" value=\"" +"\"></td>";
		umDiscussao += "\t\t\t<td align=\"center\"><input type=\"submit\" value=\""+ buttonLabel +"\" class=\"input--main__style input--button\"></td>";
		umDiscussao += "\t\t</tr>";
		umDiscussao += "\t</table>";
		umDiscussao += "\t</form>";	
		
		form = form.replaceFirst("<UMA_DISCUSSAO>", umDiscussao);
		
		
		
		List<Discussao> discussoes;
			discussoes = discussaoDAO.getOrderByID();
		
			String list = "";
			
			String teste = "null";
		
		
		for (Discussao p : discussoes) {
			if(!(p.getTitulo().equals(teste)) && !(p.getConteudo().equals(teste))) {
			String acao = "/ ";
			if (usuarioGlobal!= null && p.getAutor().equals(usuarioGlobal)) {
			  acao = "/delete/"+p.getId()+" ";
			}
			
			list += "<div class=\"discTexto\">";
			
			list += "\n<a href=\"/disc/" + p.getId()+ "\" style=\"text-decoration: none; color: whitesmoke;\">"+
					"\n<h2>" + p.getTitulo() + "</h2>\n" +
            		  "<p>" + p.getConteudo() + "</p>\n" +
            		  "<p>" + p.getAutor() + "</p>\n" +
            		  "</a>" +
            		  "\n<a href=" + acao+  "\" style=\"text-decoration: none; color: black; background-color:white;\"> x </a>" ;
            		
					
			
			list += "</div>\n";	
			}
				
		}
		
		form = form.replaceFirst("<LISTAR-DISC>", list);
		
		int[] values = getValuesFromAzureML();
		//System.out.println(values);
        String str = "";
        str += "<div class=\"roleta2\">";
        for (int i = 0; i < 4; i++) {
        	
        	String[] array = getFilmebyId(values[i], "movie");
        	//System.out.println(array[3]);
        	//System.out.println(array[2]);
        	str+="<div class=\"cards2\">";
            str+="<a href=\"/detalhes/"+array[3]+"/movie\">";
            str+="\n<img src=\"https://image.tmdb.org/t/p/w500"+array[2]+"\" id=\"imagem\">";
            str+="<h5>"+array[0]+"</h5>";
            str+="</a>";
            str+="</div>";
        }
        str+="</div>";
        
        form = form.replace("<SISTEMA>", str);
			
		
		
		
		
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
            int id2 = id+100;
            // 200 OK
            if (responseCode != 200) {
            	String [] resp2 = getFilmebyId(id2, type);
                resp[3]=resp2[3];
                resp[0]=resp2[0];
                resp[1]=resp2[1];
                resp[2]=resp2[2];
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
                if((String.valueOf(((JSONObject)obj).get("title"))).equals("null") || obj == null) {
                resp[0] =(String.valueOf(((JSONObject)obj).get("name"))); }
                else {
                	resp[0] =(String.valueOf(((JSONObject)obj).get("title")));
                }
                
              
                resp[1] = (String.valueOf(((JSONObject)obj).get("overview")));
                resp[2] = (String.valueOf(((JSONObject)obj).get("poster_path")));
                resp[3] = Integer.toString(id);
                
                //System.out.println(resp[0]);
            }
     
                


			} catch (Exception e) {
            	e.printStackTrace();
        	}
		
		
		return resp;
	
	}
	
	 public int[] getValuesFromAzureML() {
		 int idUsr = 1;
		 List<Avaliacao> movieId =  avalDAO.getIdsByUsr(0); 
		 if (usuarioGlobal != null) {
			 idUsr = userDAO.getByNome(usuarioGlobal).getId();
			 movieId = avalDAO.getIdsByUsr(idUsr);
		 } 
		 //System.out.println(movieId);
		 
		 int[] resultValues = null;
	        try {
	        	String inputData = "";
	            // Your code for URL, API key, and input data
	        	String url = "https://ussouthcentral.services.azureml.net/workspaces/82f549a0cf89466bbac338c6e30ebcf3/services/bc8661eb22334cbaabd301685846edd8/execute?api-version=2.0&details=true";
	            String apiKey = "nEqm0BF85BjLLjm3bZvdYrZzXCyNaDUagpyxy8GVIOlqZgBCtgn7CfM6XuwANf/OxN1lg86PtNW7+AMC1nh+sw==";
	            if(movieId.size()>4) {
	            	inputData = "{\"Inputs\": {\"input1\": {\"ColumnNames\": [\"Col1\", \"Col2\", \"Col3\", \"Col4\", \"Col5\", \"Col6\", \"Col7\"]," +
	            	        "\"Values\": [[" + idUsr + ", \"value\"," + movieId.get(movieId.size()-1).getId_filme() + ", \"value\"," + movieId.get(movieId.size()-1).getValor() + ", \"value\", \"0\"]," +
	            	        "[" + idUsr + ", \"value\", " + movieId.get(movieId.size()-2).getId_filme() + ", \"value\", " + movieId.get(movieId.size()-2).getValor() + ", \"value\", \"0\"]," +
	            	        "[" + idUsr + ", \"value\", " + movieId.get(movieId.size()-3).getId_filme() + ", \"value\", " + movieId.get(movieId.size()-3).getValor() + ", \"value\", \"0\"]," +
	            	        "[" + idUsr + ", \"value\", " + movieId.get(0).getId_filme() + ", \"value\", " + movieId.get(0).getValor() + ", \"value\", \"0\"]," +
	            	        "[" + idUsr + ", \"value\", " + movieId.get(1).getId_filme() + ", \"value\", " + movieId.get(1).getValor() + ", \"value\", \"0\"]]}},\"GlobalParameters\": {}}";

	            } else {
	            	inputData = "{\"Inputs\": {\"input1\": {\"ColumnNames\": [\"Col1\", \"Col2\", \"Col3\", \"Col4\", \"Col5\", \"Col6\", \"Col7\"],\"Values\": [[\"1\", \"value\", \"5\", \"value\", \"3\", \"value\", \"0\"],[\"1\", \"value\", \"876\", \"value\", \"5\", \"value\", \"0\"],[\"1\", \"value\", \"2000\", \"value\", \"3\", \"value\", \"0\"],[\"1\", \"value\", \"1200\", \"value\", \"1\", \"value\", \"0\"],[\"2\", \"value\", \"1207\", \"value\", \"5\", \"value\", \"0\"],[\"3\", \"value\", \"1207\", \"value\", \"1\", \"value\", \"0\"]]}},\"GlobalParameters\": {}}";
	            }
	            
	            // Create a connection
	            URL obj = new URL(url);
	            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

	            // Set request properties
	            con.setRequestMethod("POST");
	            con.setRequestProperty("Content-Type", "application/json");
	            con.setRequestProperty("Authorization", "Bearer " + apiKey);
	            con.setDoOutput(true);

	            // Write data to the request body
	            con.getOutputStream().write(inputData.getBytes());

	            // Get the response code
	            int responseCode = con.getResponseCode();

	            if (responseCode == HttpURLConnection.HTTP_OK) {
	                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
	                StringBuilder response = new StringBuilder();
	                String inputLine;

	                while ((inputLine = in.readLine()) != null) {
	                    response.append(inputLine);
	                }
	                in.close();
	                
	                

	                JSONParser parser = new JSONParser();
	                JSONObject jsonResponse = (JSONObject) parser.parse(response.toString());
	                JSONObject output1 = (JSONObject) jsonResponse.get("Results");
	                output1 = (JSONObject) output1.get("output1");
	                JSONObject valueObject = (JSONObject) output1.get("value");
	                JSONArray valuesArray = (JSONArray) valueObject.get("Values");

	                if (valuesArray.size() > 0) {
	                    JSONArray firstRow = (JSONArray) valuesArray.get(0);
	                    resultValues = new int[firstRow.size() - 1]; // Excluding the first string value (User)
	                    for (int i = 1; i < firstRow.size(); i++) {
	                        resultValues[i - 1] = Integer.parseInt((String) firstRow.get(i));
	                    }
	                }
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        
	        return resultValues;
	    }
	    
	
	public Object insert(Request request, Response response) {
		String titulo = request.queryParams("titulo");
		String conteudo = request.queryParams("descricao");
		String autor = usuarioGlobal;
		int curtidas = 10;
		String data = "data";
		String resp = "";
		
		Discussao discussao = new Discussao(-1, titulo, conteudo, autor, curtidas, data);
		
		if(discussaoDAO.insert(discussao) == true) {
			resp = "Discussao" +"( "  + titulo + " )" + "inserido!";
            response.status(201); // 201 Created
		} else {
			resp = "Discussao" +"( "  + titulo + " )" + "não inserido!";
			response.status(404); // 404 Not found
		}
			
		makeForm();
		return form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");
	}
	
	
	
	public Object getAll(Request request, Response response) {
	
		makeForm();
		usuarioGlobal = (request.session().attribute("authenticatedUser"));
		System.out.println(usuarioGlobal);
	    response.header("Content-Type", "text/html");
	    response.header("Content-Encoding", "UTF-8");
		return form;
	}
	
	
	public void makeDisc(Discussao disc) {
		String nomeArquivo = "src/main/resources/web/discussao/disc.html";
		form2 = "";
		
		try{
			Scanner entrada = new Scanner(new File(nomeArquivo));
		    while(entrada.hasNext()){
		    	form2 += (entrada.nextLine() + "\n");
		    }
		    entrada.close();
		}  catch (Exception e) { System.out.println(e.getMessage()); }
		
		String teste = "";
		teste +="<div class=\"discPrincipal\">\n"+
				"<h1>" +disc.getTitulo() + "</h1>" +
				"\n<p>" + disc.getConteudo() + "</p>"+
				"</div>";
		
		form2 = form2.replaceFirst("<TESTE>", teste);
		
		
		
	}
	
	public Object getDisc(Request request, Response response) {
		int id;
		if((request.params(":id") ==null ) ){
			 id = testeId;
			} else {
			 id = testeId= Integer.parseInt(request.params(":id"));
			}
		Discussao disc = (Discussao) discussaoDAO.get(testeId);
		makeDisc(disc);
	    response.header("Content-Type", "text/html");
	    response.header("Content-Encoding", "UTF-8");
	    getAllResp(request, response);
		return form2;
	}
	
	public Object delete(Request request, Response response) {
        String id = (request.params(":id"));
        int id2 = Integer.parseInt(id);
        Discussao discussao = discussaoDAO.get(id2);
        String resp = "";       

        if (discussao != null) {
            discussaoDAO.delete(id2);
            response.status(200); // success
            resp = "Discussao (" + id2 + ") excluído!";
        } else {
            response.status(404); // 404 Not found
            resp = "Discussao (" + id2 + ") não encontrado!";
        }
		makeForm();
		return form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");
	}
	
	
	public void makeFormResp() {
		makeFormResp(FORM_INSERT, new Discussao(), FORM_ORDERBY_ID);
	}

	public void makeFormResp(int tipo, Discussao discussao, int order) {
		
		
		
		String umDiscussao = "";
		String action = "";
		
		String name, titulo, descricao, buttonLabel, action2;
		
		action = "/criarResp";
		name = "Responder";
		descricao = "Conteúdo";
		buttonLabel = "Criar";

		umDiscussao += "\t<form class=\"form--register\" action=\"" + action + "\" method=\"post\" id=\"atualizar\">";
		umDiscussao += "\t<table width=\"10%\" bgcolor=\"#00000\" align=\"center\">";
		umDiscussao += "\t\t<tr>";
		umDiscussao += "\t\t\t<td colspan=\"3\" align=\"left\"><font size=\"+2\"><b style=\"color: whitesmoke;\">&nbsp;&nbsp;&nbsp;" + name + "</b></font></td>";
		umDiscussao += "\t\t</tr>";
		umDiscussao += "\t\t<tr>";
		umDiscussao += "\t\t\t<td colspan=\"3\" align=\"left\">&nbsp;</td>";
		umDiscussao += "\t\t</tr>";
		umDiscussao += "\t\t<tr>";
		umDiscussao += "\t\t\t<td>&nbsp; <input class=\"input--register\" type=\"text\" name=\"titulo\" placeholder=\"Resposta\" value=\"" +"\"></td>";
	
		umDiscussao += "\t\t\t<td align=\"center\"><input type=\"submit\" value=\""+ buttonLabel +"\" class=\"input--main__style input--button\"></td>";
		umDiscussao += "\t\t</tr>";
		umDiscussao += "\t</table>";
		umDiscussao += "\t</form>";	
		
		form2 = form2.replaceFirst("<formResp>", umDiscussao);
		
		
		
		List<Resp> resps;
			resps = respDAO.getOrderByID();
		
			String list = "";
			
			String teste = "null";
		
		
		for (Resp p : resps) {
			if(!(p.getValor().equals(teste))) {
			
			if(p.getId_disc() == testeId) {
				list += "<div class=\"discTexto\">";
			list += 
            		  "<p>" + p.getValor() + "</p>\n" +
            		  "\n<a href=\"/deleteResp/" + p.getId()+ "\" style=\"text-decoration: none; color: black; background-color:white;\"> x </a>" ;
            		
			}		
			
			list += "</div>\n";	
			}
				
		}
		
		form2 = form2.replaceFirst("<LISTAR-RESP>", list);
			
		
		
		
		
}
	
	public Object insertResp(Request request, Response response) {
		String val = request.queryParams("titulo");
		
		String respo = "";
		
		Resp resp = new Resp(-1,val, testeId);
		
		if(respDAO.insert(resp) == true) {
			respo = "Resposta" +"( "  + val + " )" + "inserido!";
            response.status(201); // 201 Created
		} else {
			respo = "Discussao" +"( "  + val + " )" + "não inserido!";
			response.status(404); // 404 Not found
		}
			
		getDisc(request, response);
		return form2.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ respo +"\">");
	}
	
	
	
	public Object getAllResp(Request request, Response response) {
	
		makeFormResp();
	    response.header("Content-Type", "text/html");
	    response.header("Content-Encoding", "UTF-8");
		return form;
	}
	
	public Object deleteResp(Request request, Response response) {
        int id = Integer.parseInt(request.params(":id"));
        Resp resp = respDAO.get(id);
        String respo = "";       

        if (resp != null) {
            respDAO.delete(id);
            response.status(200); // success
            respo = "Resposta (" + id + ") excluído!";
        } else {
            response.status(404); // 404 Not found
            respo = "Resposta (" + id + ") não encontrado!";
        }
		
		
		return form2.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ respo +"\">");
	}
	
	
	
	
	// SI
	
	
	
	
	
}