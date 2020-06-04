package pl.edu.pwr.KantorApp.repository;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import pl.edu.pwr.KantorApp.model.Client;
import pl.edu.pwr.KantorApp.model.User;

//nie używany
//Musi wymienić hardcoded admin

public class UserRepository extends BaseRepository {
	protected String fileName;
	protected List<User> users;

	public UserRepository(String dataPath) {
		super(dataPath);
		fileName = getDataPath() + "\\Admins\\Users.json";
		List<User> users = new ArrayList<User>();

	}

	public boolean saveUsers() {

		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());

		try {

			objectMapper.writeValue(new File(fileName), users);
			return true;

		} catch (JsonGenerationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;

	}

	public List<User> getAllUsers() {

		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());

		try {
			User users[] = objectMapper.readValue(new File(fileName), User[].class);
			this.users = Arrays.asList(users);
			return this.users;
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	public boolean addUser(User user) {
		users.add(user);

		return saveUsers();
	}

	public User getUserByLogin(String login) {
		for (User user : users) {
			if (login.contentEquals(user.getLogin())) {
				return user;
			}
		}
		return null;

	}

}
