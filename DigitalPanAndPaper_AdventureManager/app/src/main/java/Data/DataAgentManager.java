package Data;

import Interfaces.IDataAgent;
import Mocks.DataAgentMock;

/**
 * Created by Dimas on 04-May-16.
 */
public class DataAgentManager {
    public static IDataAgent getDataAgent(){
        return DataAgentMock.getInstance();
    }
}
