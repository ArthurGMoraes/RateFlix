package service;

import java.util.Scanner;

import java.io.File;


import java.util.List;
import dao.DiscussaoDAO;
import dao.RespDAO;
import dao.FilmeDAO;
import model.Avaliacao;
import model.Resp;
import model.Discussao;
import model.Filme;
import spark.Request;
import spark.Response;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.net.HttpURLConnection;
import java.net.URL;




public class DiscussaoService {

	private DiscussaoDAO discussaoDAO = new DiscussaoDAO();
	private RespDAO respDAO = new RespDAO();
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
	
	
	
}