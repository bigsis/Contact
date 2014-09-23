package service.mem;

import java.io.File;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.eclipse.persistence.internal.jaxb.JaxbClassLoader;

import entity.Contact;
import service.ContactDao;
import service.DaoFactory;
import service.XmlForm;

/**
 * MemDaoFactory is a factory for getting instances of entity DAO object
 * that use memory-based persistence, which isn't really persistence at all!
 * 
 * @see contact.service.DaoFactory
 * @version 2014.09.19
 * @author jim
 */
public class MemDaoFactory extends DaoFactory {
	/** instance of the entity DAO */
	private ContactDao daoInstance;
	
	public MemDaoFactory() {
		daoInstance = new MemContactDao();
		
		Unmarshaller um;
		try {
			JAXBContext ctx = JAXBContext.newInstance( XmlForm.class );
			um = ctx.createUnmarshaller();
			File file = new File("D:\\Work\\WebService\\Contacts\\ContactXml.xml");
			XmlForm xml = (XmlForm) um.unmarshal(file);
			for ( Contact c : xml.getList() )
				daoInstance.save(c);
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	@Override
	public ContactDao getContactDao() {
		return daoInstance;
	}
	
	@Override
	public void shutdown() {
		JAXBContext ctx;
		try {
			ctx = JAXBContext.newInstance( XmlForm.class );
			List<Contact> l = getContactDao().findAll();
			Marshaller m = ctx.createMarshaller();
			XmlForm xml = new XmlForm();
			xml.setList(l);
			m.marshal(xml, new File("D:\\Work\\WebService\\Contacts\\ContactXml.xml"));
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
}
