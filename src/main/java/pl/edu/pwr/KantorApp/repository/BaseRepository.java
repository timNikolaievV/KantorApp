package pl.edu.pwr.KantorApp.repository;
//klasa od której dziedzici UserRepository i CleintRepository
import java.io.File;
import java.io.IOException;

public class BaseRepository {
	protected String dataPath;

	public BaseRepository(String dataPath) {
		super();
		this.dataPath = dataPath;
	}
	
	
	protected String getDataPath() {
		String path = "";
		try {
			path = new File(".").getCanonicalPath();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return path + dataPath;
	}

}
