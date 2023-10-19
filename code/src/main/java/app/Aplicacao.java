package app;

import static spark.Spark.*;
import service.DiscussaoService;
import service.FilmeService;


public class Aplicacao {
	
	private static DiscussaoService discussaoService = new DiscussaoService();
	private static FilmeService filmeService = new FilmeService();
	
	
    public static void main(String[] args) {
        port(1234);
        
        staticFiles.location("/web");
        
        //get("/", (request, response) -> site.Service.gerar(resquest, response));
        
        post("/criar", (request, response) -> discussaoService.insert(request, response));
        
        get("/", (request, response) -> discussaoService.getAll(request, response));
        get("/detalhes/:id/:type", (request, response) -> filmeService.makeFilme(request, response));
       
        get("/disc/:id", (request, response) -> discussaoService.getDisc(request, response));
        
        

             
    }
}