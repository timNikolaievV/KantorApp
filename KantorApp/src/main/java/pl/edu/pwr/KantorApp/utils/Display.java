package pl.edu.pwr.KantorApp.utils;

import pl.edu.pwr.KantorApp.model.Client;
import pl.edu.pwr.KantorApp.model.Trade;
import pl.edu.pwr.KantorApp.model.User;
import pl.edu.pwr.KantorApp.model.ValuteList;
import pl.edu.pwr.KantorApp.services.ClientService;
import pl.edu.pwr.KantorApp.services.UserService;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Display {
	protected ClientService clientService;
	protected UserService userService;
	protected User currentUser = null;

	protected final int DISPLAY_WIDTH = 20;
	protected final String DELIMITER_SYMBOL = "*";

	public Display(ClientService clientService, UserService userService) {
		super();
		this.clientService = clientService;
		this.userService = userService;
	}

	/**
	 * @return the currentUser
	 */
	public User getCurrentUser() {
		return currentUser;
	}

	public void displayMainMenu() {

		displayTitle("Cantor");

		System.out.print("1. Register\n");
		System.out.print("2. Login\n");

		System.out.print("9. Check rates\n");
		System.out.print("0. Exit");

		displayDelimiter();
	}

	public void displayClientMenu() {

		displayTitle("Client: " + currentUser.getLogin());

		System.out.print("1. List trades\n");
		System.out.print("2. Add trade\n");
		System.out.print("3. My balance\n");
		System.out.print("4. Withdrawal amount\n");// google
		System.out.print("5. Deposit amount\n");

		System.out.print("9. Check rates\n");
		System.out.print("0. Exit");

		displayDelimiter();
	}

	public void displayAdminMenu() {

		displayTitle("Admin");

		System.out.print("1. List of all clients\n");
		System.out.print("2. Delete client\n");
		System.out.print("3. Get transactions for date\n");

		System.out.print("9. Check rates\n");
		System.out.print("0. Exit");

		displayDelimiter();

	}

	public void displayUserRegister() throws IOException {
		String login;
		String name;
		String surname;
		String emailAddress;
		String password;

		displayTitle("Register User");

		login = inputLine("Enter User Login: ");

		name = inputLine("Enter User Name: ");

		surname = inputLine("Enter User Surname: ");

		password = inputLine("Enter User Password: ");

		emailAddress = inputLine("Enter Email Address: ");

		Client client = clientService.createClient(login, name, surname, emailAddress, password);
		if (client == null) {
			displayDelimiter("Registering client failed try again");

		} else {
			displayDelimiter("Created User");
			System.out.print(client.toString());
		}
		displayDelimiter();
	}

	public void displayUserLogin() throws IOException {
		String login;
		String password;

		displayTitle("Log in");

		login = inputLine("Enter User Login: ");

		password = inputLine("Enter User Password: ");

		currentUser = userService.logInUser(login, password);
		if (currentUser != null) {
			displayDelimiter("Logged in");
			System.out.print(currentUser.toString());
		} else {
			displayDelimiter("Log in Failed");

		}
		displayDelimiter();
	}

	public void rightRate() {
		String currency1;
		String currency2;

		displayTitle("Rate");

		currency1 = inputValute("Enter currency1: ");
		currency2 = inputValute("Enter currency2: ");

		System.out.print("Your rate: " + userService.rightRate(currency1, currency2));
		displayDelimiter();
	}

	public void diplayCurrentClientTrades() {
		Client client = (Client) currentUser;
		displayTitle("Trades");
		for (Trade trade : client.getTrades()) {
			System.out.println(trade.toString());
		}
		displayDelimiter();

	}

	public void displayAddTrade() {
		String currency1;
		String currency2;
		LocalDateTime tradeDate;
		double amount1;
		double rate;

		displayTitle("Trade");

		currency1 = inputValute("Enter currency1: ");

		currency2 = inputValute("Enter currency2: ");

		amount1 = inputDouble("Enter amount1: ");

		Trade trade = clientService.createClientTrade(currency1, currency2, amount1, currentUser.getId());

		displayDelimiter("Added trade");
		System.out.print(trade.toString());
		displayDelimiter();
	}

	public void displayMyBalance() {
		Map<String, Double> balances = clientService.getClientBalances(currentUser.getId());
		displayDelimiter(currentUser.getLogin() + "s balances");
		for (String i : balances.keySet()) {
			System.out.println("Currency: " + i + " rate: " + balances.get(i));
		}
		displayDelimiter();

	}

	public void displayWithdrawalClientBalance() {
		String currency;
		double amount;

		displayTitle("Withdrawal");

		currency = inputValute("Enter currency: ");
		if (currency != null) {
			double oldBalance = clientService.getClientBalances(currentUser.getId()).get(currency);
			System.out.print("Current balance:" + oldBalance);
			amount = inputDouble("\nEnter amount: ");
			Double newBalance = clientService.withdrawalClientBalance(currency, amount, currentUser.getId());

			if (newBalance != null)

			{
				displayDelimiter(newBalance + "\nWithdrawal completed ");

			} else {
				displayDelimiter("Not enough money");
			}
		} else {
			System.out.print("No such valute");
		}
		displayDelimiter();
	}

	public void displayDepositClientBalance() {
		String currency;
		double amount;

		displayTitle("Deposit");

		currency = inputValute("Enter currency: ");

		amount = inputDouble("Enter amount: ");

		Double newBalance = clientService.depositClientBalance(currency, amount, currentUser.getId());

		displayDelimiter(newBalance + "Deposit completed");

		displayDelimiter();
	}

	public void displayAllClients() {
		List<Client> clients = clientService.getAllClients();
		displayTitle("All Users");
		for (Client client : clients) {
			System.out.println(client.toString());
		}
		displayDelimiter();

	}

	public void displayAllValutes() {
		displayTitle("List of supported valutes");
		for (String s : ValuteList.getValutes()) {
			System.out.println(s);
		}
		displayDelimiter();

	}

	public void displayDeleteClient() {
		String login;
		displayTitle("Delete client");
		login = inputLine("Enter User Login: ");
        Client client = clientService.getClientByLogin(login);
		
		
		if (client != null) {
			displayDelimiter("Client deleted");
			clientService.deleteClient(client);
		} else {
			displayDelimiter("Client not found");

		}
		displayDelimiter();
	}

	private void displayDelimiter(String text) {

		System.out.print("\n" + text + "\n");
	}

	private void displayDelimiter() {
		System.out.print("\n" + DELIMITER_SYMBOL.repeat(DISPLAY_WIDTH) + "\n");
	}

	private void displayTitle(String title) {
		displayDelimiter();
		System.out.print("\t\t\t" + title);
		displayDelimiter();

	}

	private String inputLine(String label) {
		String value;

		System.out.print(label);
		Scanner scan = new Scanner(System.in);
		value = scan.nextLine();

		return value;
	}

	private double inputDouble(String label) {
		double value;

		System.out.print(label);
		Scanner scan = new Scanner(System.in);
		value = scan.nextDouble();

		return value;
	}

	private String inputValute(String label) {
		String value = inputLine(label).toUpperCase();

		// Convert to stream and test it
		boolean result = Arrays.stream(ValuteList.getValutes()).anyMatch(value::equals);

		if (!result) {
			System.out.println("Unsupported valute " + value + " \n");
			return null;
		} else {
			return value.toUpperCase();
		}
	}

}