package service;

import dao.UserDAO;
import model.Usuario;
import spark.Request;
import spark.Response;
import java.util.Random;

public class UserSigninService {

    private UserDAO userDAO = new UserDAO();
    private static String form;

    public Object makeForm(Request request, Response response) {
        form = "                <form id=\"formularioRegistro\" class=\"formulario\" action=\"/registrar\" method=\"post\">\r\n" + //
                "                    <label for=\"nome\">Nome de Usuário:</label>\r\n" + //
                "                    <input type=\"text\" name=\"nome\" placeholder=\"Nome de Usuário\" required>\r\n" + //
                "                \r\n" + //
                "                    <label for=\"senha\">Senha:</label>\r\n" + //
                "                    <input type=\"password\" name=\"senha\" placeholder=\"Senha\" required>\r\n" + //
                "                \r\n" + //
                "                    <input type=\"submit\" value=\"Registrar\">\r\n" + //
                "                </form";
        
        return form;
    }

    public int generateRandomInt(int min, int max) {
        if (min >= max) {
            throw new IllegalArgumentException("O valor mínimo deve ser menor que o valor máximo.");
        }

        Random random = new Random();
        return random.nextInt((max - min) + 1) + min;
    }

    public Object registrar(Request request, Response response) {
        String nome = request.queryParams("nome");
        String senha = request.queryParams("senha");
        
        //System.out.println(nome);
        //System.out.println(userDAO.getByNome(nome));
  

        if (nome != null && senha != null && !nome.equals(userDAO.getByNome(nome))) {
        	Usuario user = new Usuario(-1, nome, senha);
            if (userDAO.insert(user)) {
            	response.redirect("/");
                return "Usuário " + nome + " cadastrado com sucesso!";
            } 
        }else {
            response.status(400); // Requisição Inválida
            return "Falha ao cadastrar o usuário " + nome + "!";
        }
        return "a";
    }

    public Object getAll(Request request, Response response) {
        return form;
    }
}
