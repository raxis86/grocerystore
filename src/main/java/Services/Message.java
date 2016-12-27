package Services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by raxis on 27.12.2016.
 */
public class Message {
    private static final Logger logger = LoggerFactory.getLogger(Message.class);

    private String messagesOk;
    private List<String> messagesError = new ArrayList<>();
    private boolean isOk;

    public Message(){
        isOk=true;
    }

    public Message(String message, Status status){
        switch (status){
            case OK: messagesOk=message;
                     isOk=true;
                     break;
            case ERROR: messagesError.add(message);
                        isOk=false;
                        break;
        }
    }

    public String getMessagesOk() {
        return messagesOk;
    }

    public List<String> getMessagesError() {
        return messagesError;
    }

    public void setOkMessage(String message){
        messagesOk=message;
        if(messagesError.size()==0){
            isOk=true;
        }
    }

    public void addErrorMessage(String message){
        messagesError.add(message);
        isOk=false;
    }

    public boolean isOk() {
        return isOk;
    }

    public enum Status {
        OK, ERROR
    }

}
