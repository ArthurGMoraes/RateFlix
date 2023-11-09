package app;

import static spark.Spark.*;
import service.DiscussaoService;
import service.FilmeService;
import service.UserLoginService;
import service.UserSigninService;

public class Aplicacao {

    private static DiscussaoService discussaoService = new DiscussaoService();
    private static FilmeService filmeService = new FilmeService();
    private static UserLoginService loginService = new UserLoginService();
    private static UserSigninService signinService = new UserSigninService();

    public static void main(String[] args) {
        port(1234);
        staticFiles.location("/web");

        // Filter to check for authenticated users
        before("/protected", (request, response) -> {
            if (request.session().attribute("authenticatedUser") == null) {
                halt(401, "Unauthorized");
            }
        });

        post("/avaliar", (request, response) -> filmeService.insert(request, response));
        post("/criar", (request, response) -> discussaoService.insert(request, response));
        post("/criarResp", (request, response) -> discussaoService.insertResp(request, response));
        post("/registrar", (request, response) -> signinService.registrar(request, response));
        post("/logar", (request, response) -> loginService.login(request, response));

        get("/", (request, response) -> discussaoService.getAll(request, response));
        get("/detalhes/:id/:type", (request, response) -> filmeService.makeFilme(request, response));
        get("/login", (request, response) -> loginService.makeForm(request, response));
        get("/criarConta", (request, response) -> signinService.makeForm(request, response));
        get("/logout", (request, response) -> {
            request.session().invalidate();
            response.redirect("/login");
            return null;
        });
        
        get("/disc/:id", (request, response) -> discussaoService.getDisc(request, response));

        // Protected route, requires authentication
        get("/protected/", (request, response) -> {
            //String authenticatedUser = request.session().attribute("authenticatedUser");
            return discussaoService.getAll(request, response);
        });
        
        

        get("/delete/:id", (request, response) -> discussaoService.delete(request, response));
        get("/deleteResp/:id", (request, response) -> discussaoService.deleteResp(request, response));
    }
}