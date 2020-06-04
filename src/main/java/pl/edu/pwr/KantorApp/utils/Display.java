package pl.edu.pwr.KantorApp.utils;

import pl.edu.pwr.KantorApp.model.Client;
import pl.edu.pwr.KantorApp.model.Trade;
import pl.edu.pwr.KantorApp.model.User;
import pl.edu.pwr.KantorApp.model.ValuteList;
import pl.edu.pwr.KantorApp.services.ClientService;
import pl.edu.pwr.KantorApp.services.UserService;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Display {
	protected ClientService clientService;
	protected UserService userService;
	protected User currentUser = null;

	// protected static Scanner scan = new Scanner(System.in);

	protected final int DISPLAY_WIDTH = 20;
	protected final String DELIMITER_SYMBOL = "*";

	public Display(ClientService clientService, UserService userService) {
		super();
		this.clientService = clientService;
		this.userService = userService;
//		this.scan = scan;

		// scan = new Scanner(System.in);
	}

	/**
	 * @return the currentUser
	 */
	public User getCurrentUser() {
		return currentUser;
	}

	public void displayMainMenu() {
		String[] items = { "1. Register", "2. Login", "9. Check rates", "0. Exit" };

		displayMenu("Cantor", items);

		displayDelimiter();
	}

	public void displayClientMenu() {
		String[] items = { "1. List trades", "2. Add trade", "3. My balance", "4. Withdrawal amount",
				"5. Deposit amount", "9. Check rates", "0. Exit" };

		displayMenu("Client: " + currentUser.getLogin(), items);

		displayDelimiter();
	}

	public void displayAdminMenu() {
		String[] items = { "1. List of all clients", "2. Delete client", "9. Check rates", "0. Exit" };

		displayMenu("Admin", items);

		displayDelimiter();

	}

	private void displayMenu(String title, String[] items) {
		displayTitle(title);

		for (String item : items) {
			System.out.print(item + "\n");
		}

		displayDelimiter();
	}

	/////////////////////////// Ogólne Menu
	// Przecisk 1
	//regestruje użytkownika
	public void displayUserRegister() throws IOException {
		String login;
		String name;
		String surname;
		String emailAddress;
		String password;

		displayTitle("Register User");

		login = inputData("Enter User Login: ", "");

		name = inputData("Enter User Name: ", "");

		surname = inputData("Enter User Surname: ", "");

		password = inputData("Enter User Password: ", "");

		emailAddress = inputData("Enter Email Address: ", "");

		Client client = clientService.createClient(login, name, surname, emailAddress, password);
		if (client == null) {
			displayDelimiter("Registering client failed try again");

		} else {
			displayDelimiter("Created User");
			displayObject(client);
		}
		displayDelimiter();
	}

//Logowanie
	// Żeby zalogować się ajko admin potrzebno wpisac Login:admin Password:admin
	// Przecisk 2
	public void displayUserLogin() throws IOException {
		String login;
		String password;

		displayTitle("Log in");

		login = inputData("Enter User Login: ", "");

		password = inputData("Enter User Password: ", "");

		currentUser = userService.logInUser(login, password);
		if (currentUser != null) {
			displayDelimiter("Logged in");
			displayObject(currentUser);
		} else {
			displayDelimiter("Log in Failed");

		}
		displayDelimiter();
	}

//Sprawdzenia ratów
	//potrzebno podać dwie waluty 
	// Przecisk 9
	public void displayRightRate() {
		String currency1;
		String currency2;

		displayTitle("Rate");

		currency1 = inputValute("Enter currency1: ");
		currency2 = inputValute("Enter currency2: ");

		System.out.print("Your rate: " + userService.rightRate(currency1, currency2));
		displayDelimiter();
	}

/////////////////Menu Clienta
	// Przecisk 1

	// Wyśwetla wszustki trajdy clienta
	public void diplayCurrentClientTrades() {
		Client client = (Client) currentUser;
		displayTitle("Trades");
		for (Trade trade : client.getTrades()) {
			displayObject(trade);
		}
		displayDelimiter();

	}

	// Precisk 2
	// wyświetla dodawanie trajdu
	public void displayAddTrade() {
		String currency1;
		String currency2;
		double amount1;

		displayTitle("Trade");

		currency1 = inputValute("Enter currency1: ");

		currency2 = inputValute("Enter currency2: ");

		amount1 = inputData("Enter amount1: ", 0.0);

		Trade trade = clientService.createClientTrade(currency1, currency2, amount1, currentUser.getId());

		displayDelimiter("Added trade");
		displayObject(trade);
		displayDelimiter();
	}

// Przecisk 3
	// wyświetla balance(wallet) clienta
	public void displayMyBalance() {
		Map<String, Double> balances = clientService.getClientBalances(currentUser.getId());
		displayDelimiter(currentUser.getLogin() + "s balances");
		for (String i : balances.keySet()) {
			System.out.println("Currency: " + i + " rate: " + balances.get(i));
		}
		displayDelimiter();

	}

//Przecisk 4 
	//potrzebno wpisać tu ile pieniądz chcesz pobrać z swoejgo konta 
	//również wyświetla ballans do pobierania  oraz po pobiraniu pieniądz
	//gdy na koncie nie ma wystarczająco środków  wyświetla bląd
	public void displayWithdrawalClientBalance() {
		String currency;
		double amount;

		displayTitle("Withdrawal");

		currency = inputValute("Enter currency: ");
		if (currency != null) {
			double oldBalance = clientService.getClientBalances(currentUser.getId()).get(currency);
			System.out.print("Current balance:" + oldBalance);
			amount = inputData("\nEnter amount: ", 0.0);
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

	// Przycisk 5

	//wplacanie pieniądz na konto
	public void displayDepositClientBalance() {
		String currency;
		double amount;

		displayTitle("Deposit");

		currency = inputValute("Enter currency: ");

		amount = inputData("Enter amount: ", 0.0);

		Double newBalance = clientService.depositClientBalance(currency, amount, currentUser.getId());

		displayDelimiter(newBalance + " Deposit completed");

		displayDelimiter();
	}

///////////////////Menu admina
	// Przecysk 1
	//wyświetla wszystkich istnijąccyh klientów
	public void displayAllClients() {
		List<Client> clients = clientService.getAllClients();
		displayTitle("All Users");
		for (Client client : clients) {
			displayObject(client);
		}
		displayDelimiter();

	}

	// Przecisk 2
//robie delete clienta 
	//ale naprawde przemieszca klienta do folderzu Deleted
	public void displayDeleteClient() {
		String login;
		displayTitle("Delete client");
		login = inputData("Enter User Login: ", "");
		Client client = clientService.getClientByLogin(login);

		if (client != null) {
			displayDelimiter("Client deleted");
			clientService.deleteClient(client);
		} else {
			displayDelimiter("Client not found");

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

///////////////////////Fucnkji którzy ułatwiają wświetlania na ekran
	// polimorfizm
	private void displayObject(Client client) {
		System.out.println(client.toString());

	}

	private void displayObject(Trade trade) {
		System.out.println(trade.toString());

	}

	private void displayObject(User user) {
		System.out.println(user.toString());

	}

	private void displayDelimiter(String text) {

		System.out.print("\n" + text + "\n");
	}

	private void displayDelimiter() {
		String repeated = new String(new char[DISPLAY_WIDTH]).replace("\0", DELIMITER_SYMBOL);
		System.out.print("\n" + repeated + "\n");
		// System.out.print("\n" + DELIMITER_SYMBOL.repeat(DISPLAY_WIDTH) + "\n");
	}

	private void displayTitle(String title) {
		displayDelimiter();
		System.out.print("\t\t\t" + title);
		displayDelimiter();

	}

	private String inputData(String label, String def) {
		String value = def;

		System.out.print(label);
		Scanner scan = new Scanner(System.in);
		while (scan.hasNextLine()) {
			value = scan.nextLine();
			return value;
		}

		scan.close();
		return value;
	}

	private double inputData(String label, double def) {
		double value = def;

		System.out.print(label);
		Scanner scan = new Scanner(System.in);
		while (scan.hasNextLine()) {
			value = scan.nextDouble();
			return value;
		}

		scan.close();

		return value;
	}

	private String inputValute(String label) {
		String value = inputData(label, null).toUpperCase();

		// Convert to stream and test it
		boolean result = Arrays.stream(ValuteList.getValutes()).anyMatch(value::equals);

		if (!result) {
			System.out.println("Unsupported valute " + value + " \n");
			return null;
		} else {
			return value;
		}
	}

}