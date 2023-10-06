package app;

import static spark.Spark.*;
import service.FilmeService;


public class Aplicacao {
	
	private static DiscussaoService discussaoService = new DiscussaoService();
	
    public static void main(String[] args) {
        port(6789);
        
        staticFiles.location("/public");
        
        post("/", (request, response) -> discussaoService.insert(request, response));

        get("/", (request, response) -> discussaoService.get(request, response));
        

             
    }
}