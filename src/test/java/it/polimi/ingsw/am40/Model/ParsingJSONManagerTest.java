package it.polimi.ingsw.am40.Model;

import it.polimi.ingsw.am40.Network.ICommand;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ParsingJSONManagerTest {

    @Test
    void configureCommands() {
        ParsingJSONManager pJSONm = new ParsingJSONManager();
        Map<String, ICommand> map = new HashMap<>();
        pJSONm.configureCommands(map);

    }

    @Test
    void commands() {
        ParsingJSONManager pJSONm = new ParsingJSONManager();
        ArrayList<String> arr = new ArrayList<>();
        arr.add("test1");
        arr.add("test2");
        pJSONm.commands(arr);

    }
}