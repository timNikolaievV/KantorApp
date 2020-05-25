package pl.edu.pwr.KantorApp.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Random;
import java.util.Scanner;
import java.util.UUID;

//ciekawy projekt do podpatrzenia https://www.journaldev.com/2315/java-json-example
//listy artyhul https://www.geeksforgeeks.org/arrays-sort-in-java-with-examples/
//stronka jak odczytac plik https://www.java67.com/2015/08/how-to-load-data-from-csv-file-in-java.html

public class User extends BaseEntity {
	protected String login;
	protected String name;
	protected String surname;
	protected String emailAddress;
	protected String password;

	public User(String login, String name, String surname, String emailAddress, String password) {
		super();
		this.login = login;
		this.name = name;
		this.surname = surname;
		this.emailAddress = emailAddress;
		this.password = password;
	}

	public User(UUID id, LocalDateTime createdDate, String login, String name, String surname, String emailAddress,
			String password) {
		super(id, createdDate);
		this.login = login;
		this.name = name;
		this.surname = surname;
		this.emailAddress = emailAddress;
		this.password = password;
	}
	public User() {
		super();
	}

	/**
	 * @return the login
	 */
	public String getLogin() {
		return login;
	}

	/**
	 * @param login the login to set
	 */
	public void setLogin(String login) {
		this.login = login;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the surname
	 */
	public String getSurname() {
		return surname;
	}

	/**
	 * @param surname the surname to set
	 */
	public void setSurname(String surname) {
		this.surname = surname;
	}

	/**
	 * @return the emailAddress
	 */
	public String getEmailAddress() {
		return emailAddress;
	}

	/**
	 * @param emailAddress the emailAddress to set
	 */
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

}