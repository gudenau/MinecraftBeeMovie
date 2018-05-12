package net.gudenau.beemovie;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import net.gudenau.mineloader.api.MineLoader;
import net.gudenau.mineloader.api.annotation.EventHandler;
import net.gudenau.mineloader.api.event.EventBus;
import net.gudenau.mineloader.api.event.client.EventSplashesLoading;
import net.gudenau.mineloader.api.event.init.EventPreInitialize;
import net.gudenau.mineloader.api.util.Side;

public class BeeMovie{
    private final Thread loaderThread;
    private final List<String> messages = new ArrayList<>();

    public BeeMovie(){
        loaderThread = new Thread(this::load, "Script Loader");
        loaderThread.start();
    }

    private void load(){
        try{
            URLConnection connection = new URL("https://pastebin.com/raw/jELjGicn").openConnection();
            try(BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()))){
                String line;
                while((line = bufferedReader.readLine()) != null){
                    line = line.trim();
                    if(!line.isEmpty()){
                        messages.add(line);
                    }
                }
            }
        }catch(IOException ignored){}
    }

    @EventHandler
    public void preInit(EventPreInitialize event){
        EventBus clientEventBus = MineLoader.getEventBus(Side.CLIENT);
        clientEventBus.registerEventHandler(this);

        ClassLoader classLoader = getClass().getClassLoader();
        System.out.println(classLoader);
    }

    @EventHandler
    public void onSplashesLoading(EventSplashesLoading event){
        List<String> messages = event.getMessages();
        messages.clear();

        try{
            loaderThread.join();
        }catch(InterruptedException ignored){}
        messages.addAll(this.messages);

        event.setHandled();
    }
}
