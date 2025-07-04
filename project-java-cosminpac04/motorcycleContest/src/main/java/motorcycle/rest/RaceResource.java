package motorcycle.rest;

import jakarta.annotation.PostConstruct;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import motorcycle.model.Race;
import motorcycle.repository.RaceHibernateRepository;
import motorcycle.service.RaceService;
import motorcycle.util.HibernateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Path("/races")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RaceResource {
    private static final Logger logger = LoggerFactory.getLogger(RaceResource.class);
    private final RaceService raceService = new RaceService(
            new RaceHibernateRepository(HibernateUtil.getSessionFactory())
    );
    
    public RaceResource() {
        logger.info("RaceResource initialized");
    }

    @GET
    public List<Race> getAll(){
        logger.info("Getting all races");
        List<Race> races = raceService.getAllRaces();
        logger.info("Found {} races", races.size());
        return races;
    }

    @GET
    @Path("/{id}")
    public Race getOne(@PathParam("id") int id){
        logger.info("Getting race with id: {}", id);
        Race race = raceService.findRaceById(id);
        if (race != null) {
            logger.info("Found race: {}", race);
        } else {
            logger.warn("No race found with id: {}", id);
        }
        return race;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(Race race){
        logger.info("Creating new race with capacity: {}", race.getCapacity());
        try {
            raceService.addRace(race);
            logger.info("Successfully created race with id: {}", race.getID());
            return Response.status(Response.Status.CREATED)
                    .entity("{\"id\":" + race.getID() + "}")
                    .build();
        } catch (Exception e) {
            logger.error("Error creating race: {}", e.getMessage(), e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\":\"" + e.getMessage() + "\"}")
                    .build();
        }
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response update(@PathParam("id") int id, Race race) {
        logger.info("Updating race with id: {}", id);
        try {
            raceService.updateRace(race, id);
            logger.info("Successfully updated race with id: {}", id);
            return Response.ok().build();
        } catch (Exception e) {
            logger.error("Error updating race: {}", e.getMessage(), e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\":\"" + e.getMessage() + "\"}")
                    .build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") int id) {
        logger.info("Deleting race with id: {}", id);
        Race race = raceService.findRaceById(id);
        if (race != null) {
            try {
                raceService.deleteRace(race);
                logger.info("Successfully deleted race with id: {}", id);
                return Response.ok().build();
            } catch (Exception e) {
                logger.error("Error deleting race: {}", e.getMessage(), e);
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                        .entity("{\"error\":\"" + e.getMessage() + "\"}")
                        .build();
            }
        } else {
            logger.warn("No race found with id: {}", id);
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @GET
    @Path("/test")
    public Response testEndpoint() {
        logger.info("Test endpoint called");
        return Response.ok("RaceResource is active!").build();
    }

    @GET
    @Path("/ping")
    public String ping() {
        logger.info("Ping endpoint called");
        return "RaceResource is active!";
    }
}
