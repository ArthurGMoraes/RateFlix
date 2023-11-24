package service;

import java.util.List;

import dao.UserDAO;
import model.Usuario;
import spark.Request;
import spark.Response;
import java.security.*;
import java.math.*;

public class UserLoginService {

    private static UserDAO userDAO = new UserDAO();
    private String form;


    public Object makeForm(Request request, Response response) {
        // Carregue o formulário de login (HTML) e substitua o valor de 'form' aqui
        // O código do formulário de login deve estar aqui
        form =  "<style>" + //
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


                "                <form id=\"formularioLogin\" class=\"formulario\" action=\"/logar\" method=\"post\">\r\n" + //
                "                    <label class=\"titulo\">Fazer Login</label>" + //
                "                    <label for=\"nome\">Nome:</label>\r\n" + //
                "                    <input type=\"text\" name=\"nome\" placeholder=\"Nome\" required>\r\n" + //
                "            \r\n" + //
                "                    <label for=\"senha\">Senha:</label>\r\n" + //
                "                    <input type=\"password\" name=\"senha\" placeholder=\"Senha\" required>\r\n" + //
                "            \r\n" + //
                "                    <input type=\"submit\" value=\"Logar\">\r\n" + //

                "                     <label>Ainda não tem conta?</label>" + //
                "                     <button type=\"button\" onclick=\"window.location.href='/criarConta'\">Registre-se</button>" +
                "                </form>";
        
        return form;
    }

    public Object login(Request request, Response response) throws NoSuchAlgorithmException {
        String nome = request.queryParams("nome");
        String senha = request.queryParams("senha");
        MessageDigest m=MessageDigest.getInstance("MD5");
        m.update(senha.getBytes(),0,senha.length()); 
        senha = new BigInteger(1,m.digest()).toString(16);
        
        // Check if the user exists in the database
        Usuario user = userDAO.getByNome(nome);

        if (user != null && user.getSenha().equals(senha)) {
            // Create a session and store user information
            request.session().attribute("authenticatedUser", user.toString());

            // Redirect to the main page or any other desired destination
            response.redirect("/");
            return "ok";
        } else {
            // Authentication failed
            response.status(401); // Unauthorized
            return "Email ou senha inválidos.";
        }
    }
    

    public Object getAll(Request request, Response response) {
        return form;
    }
}
