package service;

import dao.UserDAO;
import model.Usuario;
import spark.Request;
import spark.Response;
import java.util.Random;
import java.security.*;
import java.math.*;

public class UserSigninService {

    private UserDAO userDAO = new UserDAO();
    private static String form;

    public Object makeForm(Request request, Response response) {
        form = "<style>" + //
        "                        body {" + //
        "                            font-family: Arial, sans-serif;" + //
        "                            background-color: rgb(48,48,48);" + //
        "                            margin: 0;" + //
        "                            padding: 0;" + //
        "                            display: flex;" + //
        "                            align-items: center;" + //
        "                            justify-content: center;" + //
        "                            height: 100vh;" + //
        "                        }" + //

        "                        .formulario {" + //
        "                            background-color: rgb(0,0,0);;" + //
        "                            padding: 20px;" + //
        "                            border-radius: 8px;" + //
        "                            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);" + //
        "                            width: 300px;" + //
        "                            text-align: center;" + //
        "                        }" + //

        "                        .titulo {" + //
        "                           color:rgb(226, 99, 21);" + //
        "                           margin-bottom: 15px;" + //
        "                           font-size: 25px;" + //
        "                           font-weight: 10px;"  + //
        "                           }" + //

        "                        label { " + //
        "                            display: block;" + //
        "                            color: white;" + //
        "                            margin-bottom: 8px;" + //
        "                        }" + //

        "                        input {" + //
        "                            width: 100%;" + //
        "                            padding: 8px;" + //
        "                            margin-bottom: 16px;" + //
        "                            box-sizing: border-box;" + //
        "                        }" + //

        "                        input[type=\"submit\"] {" + //
        "                            background-color: rgb(226, 99, 21);" + //
        "                            color: #fff;" + //
        "                            cursor: pointer;" + //
        "                        }" + //

        "                        button {" + //
        "                            background-color: #db3434;" + //
        "                            color: #fff;" + //
        "                            padding: 8px 16px;" + //
        "                            border: none;" + //
        "                            border-radius: 4px;" + //
        "                            cursor: pointer;" + //
        "                        }" + //

        "                        button:hover {" + //
        "                            background-color: #2980b9;" + //
        "                        }" + //
        "                    </style>" + //

        "                <form id=\"formularioRegistro\" class=\"formulario\" action=\"/registrar\" method=\"post\">\r\n" + //
        "                    <label class=\"titulo\">Registrar-se</label>" + //
        "                    <label for=\"nome\">Nome de Usuário:</label>\r\n" + //
        "                    <input type=\"text\" name=\"nome\" placeholder=\"Nome de Usuário\" required>\r\n" + //
        "                \r\n" + //
        "                    <label for=\"senha\">Senha:</label>\r\n" + //
        "                    <input type=\"password\" name=\"senha\" placeholder=\"Senha\" required>\r\n" + //
        "                \r\n" + //
        "                    <input type=\"submit\" value=\"Registrar\">\r\n" + //

        "<label>Já tem conta?</label>" + //
        "<button type=\"button\" onclick=\"window.location.href='/login'\">Logar</button>" +
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

    public Object registrar(Request request, Response response) throws NoSuchAlgorithmException {
    	MessageDigest m=MessageDigest.getInstance("MD5");
    	
        String nome = request.queryParams("nome");
        String senha = request.queryParams("senha");
        m.update(senha.getBytes(),0,senha.length()); 
        senha = new BigInteger(1,m.digest()).toString(16);
        
        //System.out.println(nome);
        //System.out.println(userDAO.getByNome(nome));
  

        if (nome != null && senha != null && !nome.equals(userDAO.getByNome(nome))) {
        	Usuario user = new Usuario(-1, nome, senha);
            if (userDAO.insert(user)) {
            	response.redirect("/login");
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
