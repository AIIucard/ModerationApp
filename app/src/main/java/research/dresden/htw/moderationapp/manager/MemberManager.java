package research.dresden.htw.moderationapp.manager;

import android.content.Context;
import android.util.Xml;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;
import org.xmlpull.v1.XmlSerializer;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;

import research.dresden.htw.moderationapp.model.Member;
import research.dresden.htw.moderationapp.model.Title;

public class MemberManager {
    private static Object lock = new Object();
    private static MemberManager instance = null;
    private TextView txtXml;

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

    public void readXmlPullParser(Context context) {
        XmlPullParserFactory factory;
        FileInputStream fis = null;
        try {
            StringBuilder sb = new StringBuilder();
            factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xpp = factory.newPullParser();
            fis = context.openFileInput("testxml.xml"); //TODO: Change FileName


            //xpp.setInput(new ByteArrayInputStream(xmlString.getBytes()),null);
            int eventType = xpp.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_DOCUMENT)
                    sb.append("[START]");
                else if (eventType == XmlPullParser.START_TAG)
                    sb.append("\n<" + xpp.getName() + ">");
                else if (eventType == XmlPullParser.END_TAG)
                    sb.append("</" + xpp.getName() + ">");
                else if (eventType == XmlPullParser.TEXT)
                    sb.append(xpp.getText());

                eventType = xpp.next();
            }
            txtXml.setText(sb.toString());
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void writeToAddressBookXml(Context context) {
        ArrayList<Member> memberArrayList = new ArrayList<>();
        memberArrayList.add(new Member(1, Title.DIPLOMA_OF_ARTS, "Karl", "HTW", "Hat Ahnung"));
        memberArrayList.add(new Member(2, Title.DIPLOMA_OF_ARTS, "Simon", "HTW", "Hat Ahnung"));

        XmlSerializer serializer = Xml.newSerializer();
        StringWriter writer = new StringWriter();

        try {
            serializer.setOutput(writer);
            serializer.startDocument("UTF-8", true);
            serializer.startTag("", "addressbook");
            for (Member member : memberArrayList) {
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
            String result = writer.toString();
            IOHelper.writeToFile(context, "testxml.xml", result);
            txtXml.setText("From writeToXmlFile\n" + result);


        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
