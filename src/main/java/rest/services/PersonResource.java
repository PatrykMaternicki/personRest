package rest.services;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import domain.Person;

@Path("people")
@Stateless
public class PersonResource {
	
	@PersistenceContext
	EntityManager em;
	
	@POST
	@Consumes (MediaType.APPLICATION_JSON)
	public Response addPerson (Person person){
		em.persist(person);
		return Response.ok("ADDED").build();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Person> getPerson (){
		List<Person> result = em.createNamedQuery("Person.all" , Person.class).getResultList();
		return result;
	}
	
	@PUT
	@Path("/{personId}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updatePerson(@PathParam("personId") int personId , Person person){
	Person result = em.createNamedQuery("Person.id", Person.class)
			.setParameter("personId", personId).getSingleResult();
	if (result == null){
		return Response.status(400).build();
	}
	result.setAge(person.getAge());
	result.setBirthday(person.getBirthday());
	result.setEmail(person.getEmail());
	result.setFirstName(person.getFirstName());
	result.setGender(person.getGender());
	result.setLastName(person.getLastName());
	em.persist(result);
	return Response.ok("Updated").build();
		
	}
	
	@DELETE
	@Path("/{personId}")
	public Response removePerson(@PathParam("personId") int id , Person person){
	Person result = em.createNamedQuery("Person.id", Person.class)
			.setParameter("id", id).getSingleResult();
	if (result == null){
		return Response.status(400).build();
	}
	em.remove(result);
	return Response.ok("DELETED").build();
		
	}
	

	
}
