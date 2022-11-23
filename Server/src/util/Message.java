package util;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Message implements Serializable{

    private String operation;
    private Status status;
    
    Map<String, Object> params;

    public Message(String operation){
        this.operation = operation;
        params = new HashMap<>();
    }

    public void setOperation(String operation){
        this.operation = operation;
    }

    public String getOperation(){
        return operation;
    }

    public void setStatus(Status s){
        this.status = s;
    }

    public Status getStatus(){
        return status;
    }

    public void setParam(String key, Object value){
        params.put(key, value);
    }

    public Object getParam(String key){
        return params.get(key);
    }

}
