package json;


import org.tinylog.Logger;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class JsonBuilder {

    public void saveToFile(String name, int moves) throws IOException {
        Logger.info("Saving to Json file...");
        var repository = new PlayersRepository();
        var data = PlayerData.builder().name(name).moves(moves).build();
        File dataFile = new File("players.json");
        if (dataFile.exists())
        {
            repository.loadFromFile(dataFile);
        }
        repository.add(data);
        repository.saveToFile(dataFile);
    }

    public List<PlayerData> readFromFile() throws IOException {
        Logger.info("Reading from Json file...");
        var repository = new PlayersRepository();
        File dataFile = new File("players.json");
        repository.loadFromFile(dataFile);

        return repository.findAll();
    }




}