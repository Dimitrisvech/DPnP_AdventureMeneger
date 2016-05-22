package Data;

import android.content.Context;

import Interfaces.IDataAgent;
import Mocks.DataAgentMock;

/**
 * Created by Dimas on 04-May-16.
 */
public class DataAgentManager {
    public static IDataAgent getDataAgent(Context ctx){
        return DataAgent.getInstance(ctx);
    }
}
