package service;

import java.util.Scanner;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import dao.DiscussaoDAO;
import model.Discussao;
//import spark.Request;
//import spark.Response;
import model.Discussao;
import spark.Request;
import spark.Response;


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

	public void makeForm(int tipo, Discussao discussao, int orderBy) {
		String nomeArquivo = "index.html";
		form = "";
		try{
			Scanner entrada = new Scanner(new File(nomeArquivo));
		    while(entrada.hasNext()){
		    	form += (entrada.nextLine() + "\n");
		    }
		    entrada.close();
		}  catch (Exception e) { System.out.println(e.getMessage()); }
		
		String umDiscussao = "";
		String action = "/produto/";
		String name, descricao, buttonLabel;
		
		action += "criar";
		name = "Titulo";
		descricao = "Contúdo";
		buttonLabel = "Criar";

		
		umDiscussao += "\t<form class=\"form--register\" action=\"" + action + "\" method=\"post\" id=\"form-add\">";
		umDiscussao += "\t<table width=\"80%\" bgcolor=\"#f3f3f3\" align=\"center\">";
		umDiscussao += "\t\t<tr>";
		umDiscussao += "\t\t\t<td colspan=\"3\" align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;" + name + "</b></font></td>";
		umDiscussao += "\t\t</tr>";
		umDiscussao += "\t\t<tr>";
		umDiscussao += "\t\t\t<td colspan=\"3\" align=\"left\">&nbsp;</td>";
		umDiscussao += "\t\t</tr>";
		umDiscussao += "\t\t<tr>";
		umDiscussao += "\t\t\t<td>&nbsp;Descrição: <input class=\"input--register\" type=\"text\" name=\"descricao\" value=\""+ descricao +"\"></td>";
		umDiscussao += "\t\t\t<td>Preco: <input class=\"input--register\" type=\"text\" name=\"preco\" value=\"" +"\"></td>";
		umDiscussao += "\t\t\t<td>Quantidade: <input class=\"input--register\" type=\"text\" name=\"quantidade\" value=\""+ "\"></td>";
		umDiscussao += "\t\t</tr>";
		umDiscussao += "\t\t<tr>";
		umDiscussao += "\t\t\t<td>&nbsp;Data de fabricação: <input class=\"input--register\" type=\"text\" name=\"dataFabricacao\" value=\""+  "\"></td>";
		umDiscussao += "\t\t\t<td>Data de validade: <input class=\"input--register\" type=\"text\" name=\"dataValidade\" value=\""+  "\"></td>";
		umDiscussao += "\t\t\t<td align=\"center\"><input type=\"submit\" value=\""+ buttonLabel +"\" class=\"input--main__style input--button\"></td>";
		umDiscussao += "\t\t</tr>";
		umDiscussao += "\t</table>";
		umDiscussao += "\t</form>";		
		
		form = form.replaceFirst("<UM-PRODUTO>", umDiscussao);
		
		String list = new String("<table width=\"80%\" align=\"center\" bgcolor=\"#f3f3f3\">");
		list += "\n<tr><td colspan=\"6\" align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;Relação de Discussaos</b></font></td></tr>\n" +
				"\n<tr><td colspan=\"6\">&nbsp;</td></tr>\n" +
    			"\n<tr>\n" + 
        		"\t<td><a href=\"/produto/list/" + FORM_ORDERBY_ID + "\"><b>ID</b></a></td>\n" +
        		"\t<td><a href=\"/produto/list/" + "\"><b>Descrição</b></a></td>\n" +
        		"\t<td><a href=\"/produto/list/" + "\"><b>Preço</b></a></td>\n" +
        		"\t<td width=\"100\" align=\"center\"><b>Detalhar</b></td>\n" +
        		"\t<td width=\"100\" align=\"center\"><b>Atualizar</b></td>\n" +
        		"\t<td width=\"100\" align=\"center\"><b>Excluir</b></td>\n" +
        		"</tr>\n";
		
		List<Discussao> discussoes;
			discussoes = discussaoDAO.getOrderByID();
		

		int i = 0;
		String bgcolor = "";
		for (Discussao p : discussoes) {
			bgcolor = (i++ % 2 == 0) ? "#fff5dd" : "#dddddd";
			list += "\n<tr bgcolor=\""+ bgcolor +"\">\n" + 
            		  "\t<td>" + p.getId() + "</td>\n" +
            		  "\t<td>" + p.getConteudo() + "</td>\n" +
            		  "\t<td>" + p.getAutor() + "</td>\n" +
            		  "\t<td align=\"center\" valign=\"middle\"><a href=\"/produto/" + p.getId() + "\"><img src=\"/image/detail.png\" width=\"20\" height=\"20\"/></a></td>\n" +
            		  "\t<td align=\"center\" valign=\"middle\"><a href=\"/produto/update/" + p.getId() + "\"><img src=\"/image/update.png\" width=\"20\" height=\"20\"/></a></td>\n" +
            		  "\t<td align=\"center\" valign=\"middle\"><a href=\"javascript:confirmarDeleteDiscussao('" + p.getId() + "', '" + p.getConteudo() + "', '" + p.getAutor() + "');\"><img src=\"/image/delete.png\" width=\"20\" height=\"20\"/></a></td>\n" +
            		  "</tr>\n";
		}
		list += "</table>";		
		form = form.replaceFirst("<LISTAR-PRODUTO>", list);				
}
	
	public Object insert(Request request, Response response) {
		String titulo = request.queryParams("titulo");
		String conteudo = request.queryParams("conteudo");
		String autor = request.queryParams("autor");
		int curtidas = Integer.parseInt(request.queryParams("curtidas"));
		String data = request.queryParams("data");
		String resp = "";
		
		Discussao discussao = new Discussao(-1, titulo, conteudo, autor, curtidas, data);
		
		if(discussaoDAO.insert(discussao) == true) {
            resp = "Discussao (" + titulo + "inserido!";
            response.status(201); // 201 Created
		} else {
			resp = "Discussao (" + titulo + ") não inserido!";
			response.status(404); // 404 Not found
		}
			
		makeForm();
		return form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");
	}
}