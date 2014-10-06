package resource;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;
import javax.xml.bind.JAXBElement;

import service.ContactDao;
import service.jpa.JpaDaoFactory;
import entity.Contact;
/**
 * ContactResource provides RESTful web resources using JAX-RS
 * annotations to map requests to request handling code,
 * and to inject resources into code.
 * 
 * @author Sarit Suriyasangpetch 5510546191
 *
 */
@Path("/contacts")
public class ContactResource {
	ContactDao dao = JpaDaoFactory.getInstance().getContactDao();
	
	/**
	 * Get a list of all contacts 
	 * @param title the title the we want to search
	 * @return response if it ok or not
	 */
	@GET
	@Produces( MediaType.APPLICATION_XML )
	public Response getAllContact( @QueryParam("title") String title ){
		GenericEntity<List<Contact>> entity = null;
		Response response = null;
		if( title == null ){
			entity = new GenericEntity<List<Contact>>(dao.findAll()) {};
			
		}else{
// INEFFICIENT DUPLICATE call to findByTitle
			if(dao.findByTitle(title) == null){
				return response.status(Status.NOT_FOUND).build();
			}
			entity = new GenericEntity<List<Contact>>(dao.findByTitle(title)) {};
		}
		return response = Response.ok(entity).build();
	}
	
	/**
	 * Get one contact by id 
	 * @param id the id we want to search
	 * @return response if it ok or not
	 */
	@GET
	@Path("{id}")
	@Produces( MediaType.APPLICATION_XML)
	public Response getContact( @PathParam("id") long id ) 
	{
// Code not indented correctly.
	Contact contact = dao.find(id);
	if(contact == null ){
		return Response.status(Status.NOT_FOUND).build();
	}
	return Response.ok(contact).build();
	}
	
	/**
	 * Get contact(s) whose title contains the query string (substring match).
	 * @param element is the contact
	 * @param uriInfo uri of the url
	 * @return response if it ok or not
	 * @throws URISyntaxException 
	 */
	@POST
	@Consumes( MediaType.APPLICATION_XML )
	public Response post( JAXBElement<Contact> element, @Context UriInfo uriInfo ) throws URISyntaxException 
	{
		Contact contact = element.getValue();
		if(contact == null){
			Response.status(Status.BAD_REQUEST).build();
		}
// Should check return value from save
		dao.save( contact );
		URI loc = uriInfo.getAbsolutePath();
		return Response.created(new URI(loc+"/"+contact.getId())).build();
	
	}
	
	/**
	 * Update a contact. Only update the attributes supplied in request body.
	 * @param element is the contact
	 * @param uriInfo uri of the url
	 * @param id the id of contact we want to put
	 * @return response if it ok or not
	 */
	@PUT
	@Consumes( MediaType.APPLICATION_XML )
	@Path("{id}")
	public Response put( JAXBElement<Contact> element, @Context UriInfo uriInfo, @PathParam("id") long id) 
	{
		
		Contact contact = element.getValue();
		if(contact.getId() != id && contact.getId() != 0){
			return Response.status(Status.BAD_REQUEST).build();
		}
		contact.setId(id);
		
		System.out.println(contact.getTitle() +" "+contact.getName());
// Should check return value from update
		dao.update( contact );
//			return Response.status(Status.BAD_REQUEST).build();
		URI loc = uriInfo.getAbsolutePath();
		return Response.ok(loc+"/"+contact.getId()).build();
	
	}
	
	/**
	 * Delete a contact with matching id
	 * @param id is id we want to delete
	 */
	@DELETE
	@Path("{id}")
	public Response delete(@PathParam("id") long id)
	{
// there should be 3 cases here
		return Response.ok(dao.delete(id)).build();
	}
	
	
}
