package json;

public class PlayersRepository extends GsonRepository<PlayerData> {

    public PlayersRepository() {
        super(PlayerData.class);
    }

}
