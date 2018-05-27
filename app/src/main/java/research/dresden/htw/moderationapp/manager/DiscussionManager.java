package research.dresden.htw.moderationapp.manager;

import android.util.Xml;

import org.xmlpull.v1.XmlSerializer;

import java.io.StringWriter;
import java.util.ArrayList;

import research.dresden.htw.moderationapp.model.Discussion;
import research.dresden.htw.moderationapp.model.Member;
import research.dresden.htw.moderationapp.model.Title;

public class DiscussionManager {
    private static Object lock = new Object();
    private static DiscussionManager instance = null;

    private DiscussionManager() {
        // Use getInstance
    }

    public static DiscussionManager getInstance() {
        if (instance == null) {
            synchronized (lock) {
                if (instance == null) {
                    instance = new DiscussionManager();
                }
            }
        }
        return (instance);
    }
    public void writeToDiscussionXml() {
        ArrayList<Member> memberArrayList = new ArrayList<>();
        memberArrayList.add(new Member(1, Title.DIPLOMA_OF_ARTS, "Karl", "HTW", "Hat Ahnung"));
        memberArrayList.add(new Member(2, Title.DIPLOMA_OF_ARTS, "Simon", "HTW", "Hat Ahnung"));
        Discussion discussion = new Discussion(1, "Runder Tisch", 360, memberArrayList);

        XmlSerializer serializer = Xml.newSerializer();
        StringWriter writer = new StringWriter();

        try {
            serializer.setOutput(writer);
            serializer.startDocument("UTF-8", true);



        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }

    public Discussion unMarshalDiscussionList() {


        return null;
    }


}
