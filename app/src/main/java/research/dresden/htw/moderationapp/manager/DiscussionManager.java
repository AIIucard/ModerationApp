package research.dresden.htw.moderationapp.manager;

import research.dresden.htw.moderationapp.model.Discussion;
import research.dresden.htw.moderationapp.model.Member;

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
    public void marshalDiscussionList() {

       // log.debug("Initialize JAXB context...");
        // JAXBContext jaxbContext = JAXBContext.newInstance(AreaList.class);

        // log.debug("Create marshaller...");
        // Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
        // jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        // log.info("Marshaling areas...");
        // jaxbMarshaller.marshal(areaList, new File(AREAS_XML_FILE));
    }

    public Discussion unMarshalDiscussionList() {

        // log.debug("Initialize JAXB context...");
        // JAXBContext jaxbContext = JAXBContext.newInstance(AreaList.class);

        // log.debug("Create unmarshaller...");
        // Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

        // log.info("Unmarshaling areas...");
        // return (AreaList) jaxbUnmarshaller.unmarshal(new File(AREAS_XML_FILE));
        return null;
    }


}
