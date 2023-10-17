package app;

import static spark.Spark.*;
import service.DiscussaoService;


public class Aplicacao {
	
	private static DiscussaoService discussaoService = new DiscussaoService();
	
	
    public static void main(String[] args) {
        port(1234);
        
        staticFiles.location("/web");
        
        //get("/", (request, response) -> site.Service.gerar(resquest, response));
        
        post("/discussao/criar", (request, response) -> discussaoService.insert(request, response));

        get("/", (request, response) -> discussaoService.getAll(request, response));
        
        

             
    }
}