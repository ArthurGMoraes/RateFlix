package app;

import static spark.Spark.*;
import service.DiscussaoService;
import service.FilmeService;
//import service.AvaliacaoService;


public class Aplicacao {
	
	private static DiscussaoService discussaoService = new DiscussaoService();
	private static FilmeService filmeService = new FilmeService();
	//private static AvaliacaoService avaliacaoService = new AvaliacaoService();
	
	
    public static void main(String[] args) {
        port(1234);
        
        staticFiles.location("/web");
        
        //get("/", (request, response) -> site.Service.gerar(resquest, response));
        post("/avaliar", (request, response) -> filmeService.insert(request, response));
        post("/criar", (request, response) -> discussaoService.insert(request, response));
        
        get("/", (request, response) -> discussaoService.getAll(request, response));
        get("/detalhes/:id/:type", (request, response) -> filmeService.makeFilme(request, response));
       // get("/pesq", (request, response) -> filmeService.getPesq(request, response));
       
        get("/disc/:id", (request, response) -> discussaoService.getDisc(request, response));
        
             
    }
}