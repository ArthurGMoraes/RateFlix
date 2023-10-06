package service;

import java.util.Scanner;

import java.io.File;

import java.util.List;
import dao.DiscussaoDAO;
import model.Discussao;
import spark.Request;
import spark.Response;


public class DiscussaoService {

	private DiscussaoDAO discussaoDAO = new DiscussaoDAO();
	private String form;
	private final int FORM_INSERT = 1;
	private final int FORM_DETAIL = 2;
	private final int FORM_UPDATE = 3;
	
	public DiscussaoService() {
		makeForm();
	}
	
	public void makeForm() {
		makeForm(FORM_INSERT, new Discussao(), FORM_ORDERBY_MARCA);
	}
}