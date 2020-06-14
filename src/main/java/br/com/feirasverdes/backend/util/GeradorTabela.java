package br.com.feirasverdes.backend.util;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class GeradorTabela {

	public static void main(String[] args) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("feiras_verdes");
		emf.close();
	}

}
