package pl.edu.pwr.KantorApp.repository;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import pl.edu.pwr.KantorApp.model.Client;
import pl.edu.pwr.KantorApp.model.User;

public class UserRepository extends BaseRepository {

	public UserRepository(String dataPath) {
		super(dataPath);

	}
	
	public void saveUsers() {  
		
	}

	public List<User> getAllUsers() {
		String fileName = getDataPath() + "\\Admins\\Users.json";

		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());
		
		try {
			User users[] = objectMapper.readValue(new File(fileName), User[].class);
			return Arrays.asList(users);
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

}
