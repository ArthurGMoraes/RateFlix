package app;

import static spark.Spark.*;
import service.FilmeService;


public class Aplicacao {
	
	private static FilmeService filmeService = new FilmeService();
	
    public static void main(String[] args) {
        port(6789);
        
        staticFiles.location("/public");
        
        post("/", (request, response) -> filmeService.insert(request, response));

        get("/", (request, response) -> filmeService.get(request, response));
        

             
    }
}