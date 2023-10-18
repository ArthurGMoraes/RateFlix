package service;

import java.util.Scanner;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import dao.DiscussaoDAO;
import model.Discussao;
import spark.Request;
import spark.Response;
import model.Discussao;



public class DiscussaoService {

	private DiscussaoDAO discussaoDAO = new DiscussaoDAO();
	private String form;
	private final int FORM_INSERT = 1;
	private final int FORM_ORDERBY_ID = 1;
	
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
		
		action = "/discussao/criar/";
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
		
		
		for (Discussao p : discussoes) {
			list += "<div class=\"discTexto\">";
			
			list += "\n<h2>" + p.getTitulo() + "</h2>\n" +
            		  "<p>" + p.getConteudo() + "</p>\n" +
            		  "<p>" + p.getAutor() + "</p>\n" ;
			list += "</div>\n";	
				
		}
		
		form = form.replaceFirst("<LISTAR-DISC>", list);
			
		
		
		
		
}
	
	public Object insert(Request request, Response response) {
		String titulo = request.queryParams("titulo");
		String conteudo = request.queryParams("descricao");
		String autor = "autor";
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
	
	/*public void makeForm(int orderBy) {
		makeForm(FORM_INSERT, new Discussao(), orderBy);
	}*/
	
	public Object getAll(Request request, Response response) {
	
		makeForm();
	    response.header("Content-Type", "text/html");
	    response.header("Content-Encoding", "UTF-8");
		return form;
	}
	
}