package service;

import java.util.List;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import entity.Contact;

/**
 * Form for marshall&unmarshall 
 * @author Sarit Suriyasangpetch
 *
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class XmlForm {
	private List<Contact> list;
	
	public XmlForm(){
		
	}

	public List<Contact> getList() {
		return list;
	}

	public void setList(List<Contact> list) {
		this.list = list;
	}
	
}
