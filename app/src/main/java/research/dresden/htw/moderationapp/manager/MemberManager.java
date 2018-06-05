package research.dresden.htw.moderationapp.manager;

import android.content.Context;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;

import research.dresden.htw.moderationapp.model.AddressBook;
import research.dresden.htw.moderationapp.model.Member;

public class MemberManager {
    private static Object lock = new Object();
    private static MemberManager instance = null;
    private final String FILENAME = "members.json";

    private MemberManager() {
        // Use getInstance
    }

    public static MemberManager getInstance() {
        if (instance == null) {
            synchronized (lock) {
                if (instance == null) {
                    instance = new MemberManager();
                }
            }
        }
        return (instance);
    }
    public ArrayList<Member> readFromJSONFile(Context context) {
        String addressBookAsString = IOHelper.readStringFromFile(context, FILENAME);
        if(addressBookAsString != null) {
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                AddressBook addressBook = objectMapper.readValue(addressBookAsString, AddressBook.class);
                return addressBook.getMemberList();
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public void writeToJSONFile(Context context, ArrayList<Member> memberList) {

        try {
            AddressBook addressBook = new AddressBook(memberList);
            ObjectMapper objectMapper = new ObjectMapper();
            String addressBookAsString = objectMapper.writeValueAsString(addressBook);
            IOHelper.writeStringToFile(context, FILENAME, addressBookAsString);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
