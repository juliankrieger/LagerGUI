package Logger;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Logger;

public class AppLogger {

    public static final Logger logger = Logger.getLogger(AppLogger.class.getName());

    private static Handler handler;

    public AppLogger(){
        try{
            handler = new FileHandler("Logger/log.txt");
        }catch(IOException e){
            e.printStackTrace();
        }

        logger.addHandler(handler);


    }

}
