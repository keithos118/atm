import java.util.Map;
import java.util.HashMap;

public class Database {
    Map<Integer, User> atmUsers;

    public Database() {
        System.out.print("Initialising user accounts ... ");

        atmUsers = new HashMap<Integer, User>();

        User defaultUser1 = new User(123456789, 1234, 800, 200);
        User defaultUser2 = new User(987654321, 4321, 1230, 150);
        atmUsers.put(123456789, defaultUser1);
        atmUsers.put(987654321, defaultUser2);

        System.out.println("Done!");
    }

    User retrieveUser(Integer accountNo) {

        if (atmUsers.containsKey(accountNo))
            return atmUsers.get(accountNo);
        else return null;

    }
}
