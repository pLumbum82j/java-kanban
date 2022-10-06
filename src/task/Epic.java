package task;

import java.util.ArrayList;

public class Epic extends Task{

    private ArrayList<Integer> subtaskListId;

    public Epic(int id, String name, String description, String status) {
        super(id, name, description, status);
        subtaskListId = new ArrayList<>();
    }

    public void addSubTaskId(int subtaskId){
        subtaskListId.add(subtaskId);
    }

    public ArrayList<Integer> getSubtaskListId(){
        return subtaskListId;
    }

}
