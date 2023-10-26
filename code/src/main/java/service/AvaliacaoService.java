package service;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.File;


import java.util.List;
import dao.AvaliacaoDAO;
import model.Avaliacao;
import spark.Request;
import spark.Response;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.net.HttpURLConnection;
import java.net.URL;




public class AvaliacaoService {

	private AvaliacaoDAO AvaliacaoDAO = new AvaliacaoDAO();
	private String form3;
	private final int FORM_INSERT = 1;
	private final int FORM_ORDERBY_ID = 1;
	
	
	public Object makeAvaliacao(Request request, Response response) {
		int id = Integer.parseInt(request.params(":id"));
		String type = (request.params(":type"));

        
        int nota = Integer.parseInt(request.queryParams("numberInput"));
		
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
		teste += "<div class=\"avaliacaoContainerProper\">"+
		"\n<div class=\"avaliacaoUnique shadowed\">" +
        "\n<div class=\"avaliacaoLeft\">" +
        "\n<img class=\"imgPerfil-img\" src=\"..\\public\\profile.png\" alt=\"Avatar\">" + // TODO: Add custom avatar
        "\n<div class =\"gridContainer-text\">" +
        "\n<div class=\"Written-box\">" +
        "\n<span class=\"label\" style=\"font-weight: bold;\">Nome:</span>" +   // TODO: Add name of poster
        "\n<span class=\"name\">Joãozinho2040</span>" +
        "\n</div>" +
        "\n<div class=\"Written-box\">" +
        "\n<span class=\"label\" style=\"font-weight: bold;\">Nota:</span>" +
        "\n<span class=\"name\">" + nota + "</span><span class=\"name\">/10</span>" +     // Add nota
        "\n</div>" +
        "\n</div>" +
        "\n</div>" +
        "\n<div class=\"avaliacaoRight\">" + // TODO -- Add text review
        "\n</div>" +
        "\n</div>" +
        "\n</div>" +
        "\n<sorocaba>";
		
		//System.out.println(form3);
		//System.out.println(teste);
		//String busca = "<TESTE>";
		
		form3 = form3.replaceFirst("<sorocaba>", teste);
		response.header("Content-Type", "text/html");
	    response.header("Content-Encoding", "UTF-8");
		return form3;
		
	}
	
	public Object delete(Request request, Response response) {
        int id = Integer.parseInt(request.params(":id"));
        Avaliacao avaliacao = AvaliacaoDAO.get(id);
        String resp = "";       

        if (avaliacao != null) {
            AvaliacaoDAO.delete(id);
            response.status(200); // success
            resp = "Produto (" + id + ") excluído!";
        } else {
            response.status(404); // 404 Not found
            resp = "Produto (" + id + ") não encontrado!";
        }
		return 1; // Era para retornar outra coisa...
	}
}