package research.dresden.htw.moderationapp.manager;

import android.content.Context;
import android.util.Log;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;
import org.xmlpull.v1.XmlSerializer;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.util.ArrayList;

import research.dresden.htw.moderationapp.model.AppDataViewModel;
import research.dresden.htw.moderationapp.model.Member;

public class MemberManager {
    private static Object lock = new Object();
    private static MemberManager instance = null;
    private final String FILENAME = "members.xml";

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
    public ArrayList<Member> readFromXMLFile(Context context) {
        try {
            ArrayList<Member> memberList = new ArrayList<>();

            FileInputStream fis = null;
            fis = context.openFileInput(FILENAME);
            XmlPullParserFactory factory = null;
            XmlPullParser parser = null;
            try {
                factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(true);
                parser = factory.newPullParser();
                parser.setInput(fis, null);
                int eventType = parser.getEventType();
                while (eventType != XmlPullParser.END_DOCUMENT) {
                    Member member = null;
                    String text = "";
                    String tagname = parser.getName();
                    switch (eventType) {
                    case XmlPullParser.START_TAG:
                        if (tagname.equalsIgnoreCase("member")) {
                            // create a new instance of employee
                            member = new Member();
                        }
                    break;

                    case XmlPullParser.TEXT:
                        text = parser.getText();
                        break;

                    case XmlPullParser.END_TAG:
                        if (tagname.equalsIgnoreCase("employee")) {
                            // add employee object to list
                            memberList.add(member);
                        } else if (tagname.equalsIgnoreCase("id")) {
                            member.setId(Integer.parseInt(text));
                        } else if (tagname.equalsIgnoreCase("title")) {
                            member.setTitle(text);
                        } else if (tagname.equalsIgnoreCase("name")) {
                            member.setName(text);
                        } else if (tagname.equalsIgnoreCase("organisation")) {
                            member.setOrganisation(text);
                        } else if (tagname.equalsIgnoreCase("role")) {
                            member.setRole(text);
                        }
                        break;

                    default:
                        break;
                    }
                    eventType = parser.next();
                }
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            fis.close();
            return memberList;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void writeToXMLFile(Context context, ArrayList<Member> memberList) {

        XmlSerializer serializer = Xml.newSerializer();
        StringWriter writer = new StringWriter();
        try {
            serializer.setOutput(writer);
            serializer.startDocument("UTF-8", true);
            serializer.startTag("", "MemberList");
            for (Member member : memberList) {
                serializer.startTag("", "member");
                serializer.attribute("", "id", ""+member.getId());

                serializer.startTag("", "title");
                serializer.text(member.getTitle());
                serializer.endTag("", "title");

                serializer.startTag("", "name");
                serializer.text(member.getName());
                serializer.endTag("", "name");

                serializer.startTag("", "organisation");
                serializer.text(member.getOrganisation());
                serializer.endTag("", "organisation");

                serializer.startTag("", "description");
                serializer.text(member.getRole());
                serializer.endTag("", "description");

                serializer.endTag("", "member");
            }
            serializer.endTag("", "addressbook");
            serializer.endDocument();
            serializer.flush();
            String result = writer.toString();
            IOHelper.writeStringToFile(context, FILENAME, result);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
