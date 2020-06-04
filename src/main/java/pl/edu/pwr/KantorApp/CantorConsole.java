package pl.edu.pwr.KantorApp;

import pl.edu.pwr.KantorApp.utils.Display;
import pl.edu.pwr.KantorApp.utils.ConfigurationLoader;
import java.io.IOException;
import java.util.Scanner;

import pl.edu.pwr.KantorApp.model.User;
import pl.edu.pwr.KantorApp.repository.ClientRepositoryJson;
import pl.edu.pwr.KantorApp.services.ClientService;
import pl.edu.pwr.KantorApp.services.UserService;

public class CantorConsole {
	//Kompozycja
	public static ConfigurationLoader configurationLoader = new ConfigurationLoader();
	

	public static void main(String[] args) throws IOException {
		configurationLoader.loadProperties();
		int value;
		String dataPath = System.getProperty("data.path");
		
		//CantorConsole outer = new CantorConsole();

		ClientService clientService = new ClientService(new ClientRepositoryJson(dataPath));
		UserService userService = new UserService(new ClientRepositoryJson(dataPath));

		Scanner scanner = new Scanner(System.in);
		Display display = new Display(clientService, userService);

		do {
			display.displayMainMenu();
			System.out.print("Enter value: \n");

			value = scanner.nextInt();

			switch (value) {
			case 1:
				display.displayUserRegister();
				break;
			case 2:
				display.displayUserLogin();
				User currentUser = display.getCurrentUser();
				if (currentUser != null) {
					if (currentUser.getLogin().equals("admin")) {
						do {
							display.displayAdminMenu();
							System.out.printf("Enter value: \n");
							value = scanner.nextInt();

							switch (value) {
							case 1:
								display.displayAllClients();
								break;
							case 2:
								display.displayDeleteClient();
								break;

							case 9:
								display.displayRightRate();
								break;

							}
						} while (value != 0);
					} else {
						do {
							display.displayClientMenu();
							System.out.printf("Enter value: \n");
							value = scanner.nextInt();

							switch (value) {
							case 1:
								display.diplayCurrentClientTrades();
								break;
							case 2:
								display.displayAddTrade();
								break;
							case 3:
								display.displayMyBalance();
								break;
							case 4:
								display.displayWithdrawalClientBalance();
								break;
							case 5:
								display.displayDepositClientBalance();
								break;
							case 9:
								display.displayRightRate();
								break;

							}
						} while (value != 0);
					}
				}
				break;

			case 5:
				display.displayAllClients();
				break;
			case 9:
				display.displayRightRate();
				break;
			}
		} while (value != 0);
		scanner.close();

	}

}
