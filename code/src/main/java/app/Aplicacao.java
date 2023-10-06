package app;

import static spark.Spark.*;
import service.DiscussaoService;


public class Aplicacao {
	
	private static DiscussaoService discussaoService = new DiscussaoService();
	
    public static void main(String[] args) {
        port(9876);
        
        staticFiles.location("/web");
        
        post("/discussao/criar", (request, response) -> discussaoService.insert(request, response));

        //get("/discussao/list/:orderby", (request, response) -> discussaoService.getAll(request, response));
        

             
    }
}