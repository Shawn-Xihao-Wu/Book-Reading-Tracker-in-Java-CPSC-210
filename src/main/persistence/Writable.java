package persistence;

import org.json.JSONObject;

// Modelled the save and load function after WorkRoomApp.java
// source: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git

public interface Writable {
    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}
