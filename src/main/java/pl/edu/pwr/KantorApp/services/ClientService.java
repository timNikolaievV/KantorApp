package pl.edu.pwr.KantorApp.services;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import pl.edu.pwr.KantorApp.model.Trade;
import pl.edu.pwr.KantorApp.model.User;
import pl.edu.pwr.KantorApp.model.ValuteList;
import pl.edu.pwr.KantorApp.model.Client;
import pl.edu.pwr.KantorApp.repository.ClientRepository;
import pl.edu.pwr.KantorApp.repository.UserRepository;
import pl.edu.pwr.KantorApp.utils.HttpConnection;

//classa wykorzystana dla robienia oprecji nad lkientem oraz robienia opertacji klientem
public class ClientService {
	// Kompozycja
	protected ClientRepository rep;

	public ClientService(ClientRepository rep) {
		super();
		this.rep = rep;
	}

	public Client createClient(String login, String name, String surname, String emailAddress, String password) {
		Client clientCheck = rep.getClientByLogin(login);
		if (clientCheck != null) {
			return null;
		}
		Client client = new Client(login, name, surname, emailAddress, password, new HashMap<String, Double>(),
				new ArrayList<Trade>());
		return rep.saveClient(client);

	}

	public boolean deleteClient(Client client) {
		return rep.deleteClient(client);
	}

	public List<Client> getAllClients() {
		return rep.getAllClients();
	}

	
	///tworzenia transakcji przez clienta
	public Trade createClientTrade(String currency1, String currency2, double amount1, UUID id) {
		LocalDateTime tradeDate = LocalDateTime.now();
		HttpConnection con = new HttpConnection();
		double rate;
		try {
			Client client = rep.getClientById(id);
			if (client != null) {
				rate = con.getRate(currency1, currency2);
				Trade trade = new Trade(currency1, currency2, tradeDate, amount1, rate);
				if (client.addTrade(trade)) {
					rep.saveClient(client);
					return trade;
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	
	//To jest używanę przez clienta dla sprawdzania  ballansu klienta
	public Map<String, Double> getClientBalances(UUID id) {
		Client client = rep.getClientById(id);
		HashMap<String, Double> valutes = new HashMap<String, Double>();

		if (client != null) {

			for (String s : ValuteList.getValutes()) {
				valutes.put(s, client.getBalance(s));
			}

		}

		return valutes;// client.getBalances();

	}

	// pobieranie pieniądz z konta
	public Double withdrawalClientBalance(String currency, double amount, UUID id) {
		Client client = rep.getClientById(id);
		if (client != null) {
			double balance = client.getBalance(currency);

			if (amount <= balance) {
				double newBalance = client.updateBalance(currency, -amount);
				rep.saveClient(client);
				return newBalance;
			}
		}

		return null;
	}

	// wplacanie środków na konto
	public double depositClientBalance(String currency, double amount, UUID id) {
		Client client = rep.getClientById(id);
		if (client != null) {
			double newBalance = client.updateBalance(currency, amount);
			rep.saveClient(client);
			return newBalance;

		}
		return 0.0;
	}

	//pobiera klientów wedlug logina
	public Client getClientByLogin(String login) {
		List<Client> clients = rep.getAllClients();
		for (Client client : clients) {
			if (login.equals(client.getLogin())) {
				return client;
			}
		}
		return null;
	}
}
