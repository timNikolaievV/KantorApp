package pl.edu.pwr.KantorApp.services;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import pl.edu.pwr.KantorApp.model.Client;
import pl.edu.pwr.KantorApp.model.Trade;
import pl.edu.pwr.KantorApp.model.User;
import pl.edu.pwr.KantorApp.repository.ClientRepository;
import pl.edu.pwr.KantorApp.utils.HttpConnection;

public class UserService {

	protected ClientRepository rep;

	public UserService(ClientRepository rep) {
		super();
		this.rep = rep;
	}

	public User logInUser(String login, String password) {
		
		if (login.equals("admin") && password.equals("admin")) {
			String name = "";
			String surname = "";
			String emailAddress = "";
			User admin = new User(login, name, surname, emailAddress, password);
			
			return admin;
		}
		List<Client> clients = rep.getAllClients();
		for (Client client : clients) {
			if (login.equals(client.getLogin()) && password.equals(client.getPassword())) {
				return client;
			}
		}
		return null;

	}

	public double rightRate(String currency1, String currency2) {

		HttpConnection con = new HttpConnection();
		double rate;
		try {
			rate = con.getRate(currency1, currency2);
			return rate;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
		return 0.0;
	}

}
