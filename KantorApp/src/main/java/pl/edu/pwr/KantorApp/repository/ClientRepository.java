package pl.edu.pwr.KantorApp.repository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import pl.edu.pwr.KantorApp.model.Client;
import pl.edu.pwr.KantorApp.model.Trade;
import pl.edu.pwr.KantorApp.model.User;

public class ClientRepository extends BaseRepository {

	public ClientRepository(String dataPath) {
		super(dataPath);

	}

	public Client getClientById(UUID id) {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());

		Client client;
		try {
			client = objectMapper.readValue(new File(getClientFileName(id)), Client.class);
			return client;
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

	public List<Client> getAllClients() {
		ArrayList<Client> clients = new ArrayList<Client>();
		String[] pathNames;
		String path = getDataPath() + "\\Clients\\";

		File dir = new File(path);

		// Populates the array with names of files and directories
		pathNames = dir.list();

		// For each pathname in the pathnames array
		for (String pathName : pathNames) {
			// Print the names of files and directories
			if (pathName.contains(".json")) {
				//System.out.println(pathName);
				String[] s = pathName.split("\\.");
				UUID id = UUID.fromString(s[0]);
				Client client = getClientById(id);
				if (client != null) {
					clients.add(client);
				}

			}
		}

		return clients;
	}

	public Client saveClient(Client client) {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());

		try {
			String path = getClientFileName(client.getId());
			objectMapper.writeValue(new File(path), client);
			return client;
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
		return null;
	}

	public boolean deleteClient(Client client) {
		String src = getClientFileName(client.getId());
		String dst = getDeletedClientFileName(client.getId());

		Path temp;
		try {
			temp = Files.move(Paths.get(src), Paths.get(dst));
			if (temp != null) {
				return true;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return false;
	}
	
	public Client getClientByLogin(String login) {
		List<Client> clients = getAllClients();
		for (Client client : clients) {
			if (login.equals(client.getLogin())) {
				return client;
			}
		}
		return null;

	}


	private String getClientFileName(UUID id) {
		return getDataPath() + "\\Clients\\" + id.toString() + ".json";
	}

	private String getDeletedClientFileName(UUID id) {
		return getDataPath() + "\\Clients\\Deleted\\" + id.toString() + ".json";
	}

}
