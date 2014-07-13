package com.bb.rest;

import com.bb.dal.entity.Address;
import com.bb.dal.entity.Place;
import com.bb.rest.dto.PlaceDTO;
import com.bb.rest.dto.UnboundData;
import com.bb.service.PlaceService;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * User: belozovs
 * Date: 7/3/14
 * Description
 */

@Service("SightseeingService")
@Path("/places")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SightseeingService {

    private final static Logger logger = LoggerFactory.getLogger(SightseeingService.class);

    @Autowired
    PlaceService placeService;
    @Autowired
    PlaceConverter placeConverter;

    @PostConstruct
    private void init(){
        logger.info("SightseeingService CONSTRUCTED");
    }

    @GET
    public Response getAllPlaces(@DefaultValue("true") @QueryParam("nested") boolean nested) {
        List<Place> allPlaces = placeService.getAllPlaces();

        List<PlaceDTO> placeDTOs = new ArrayList<PlaceDTO>();
        if(allPlaces!=null && !allPlaces.isEmpty()){
            for(Place place : allPlaces){
                PlaceDTO placeDTO = placeConverter.setDtoFromEntity(place, nested);
                placeDTOs.add(placeDTO);
            }
        }
        if(placeDTOs.size()>0){
            return Response.status(200).entity(placeDTOs).build();
        } else {
            return Response.status(204).build();
        }
    }

    //Get addresses as a separate list rather than a part of the Place.
    //This is only added to illustrate Jackson Object Mapper usage
    @GET
    @Path("/unbound")
    public Response getAllPlacesViaMapping() {
        List<Place> allPlaces = placeService.getAllPlaces();
        List<Address> allAddresses = new ArrayList<Address>();
        if(allPlaces!=null && !allPlaces.isEmpty()){
            for(Place place : allPlaces){
                allAddresses.addAll(place.getAddresses());
            }
        }
        logger.info("We have {} places with {} addresses in the database", allPlaces.size(), allAddresses.size());

        UnboundData unboundData = new UnboundData(allPlaces, allAddresses);
        String jsonString = serializeWithMapper(unboundData);
        if(jsonString != null){
            return Response.ok().entity(jsonString).build();
        } else {
            throw new WebApplicationException("Failed to create response", 500);
        }
    }

    @POST
    public Response addPlace(PlaceDTO placeDTO) {
        Place place = placeService.addPlace(placeConverter.setEntityFromDto(placeDTO));
        return Response.status(201).entity(placeConverter.setDtoFromEntity(place)).build();
    }

    @DELETE
    @Path("/{alias}")
    public Response deletePlace(@PathParam("alias") String placeAlias){
        Place place = placeService.getPlaceByAlias(placeAlias);
        if(place!=null){
            placeService.removePlace(place.getName());
            return Response.status(200).entity(placeConverter.setDtoFromEntity(place)).build();
        } else {
            return Response.status(204).build();
        }
    }

    @PUT
    public Response updatePlace(PlaceDTO placeDTO){
        Place placeToUpdate = placeService.getPlaceByAlias(placeDTO.getAlias());
        if(placeToUpdate != null){
            Place updatedPlace = placeService.updatePlace(placeConverter.setEntityFromDto(placeDTO));
            return Response.status(200).entity(placeConverter.setDtoFromEntity(updatedPlace)).build();
        } else {
            logger.error("Place {} not found and so cannot be updated", placeDTO.getName());
            return Response.status(500).build();
        }
    }

    private String serializeWithMapper(Object object){
        ObjectMapper om = new ObjectMapper();
        om.setSerializationInclusion(JsonSerialize.Inclusion.NON_NULL); //do not send properties with null values in the response
        om.setVisibilityChecker(om.getSerializationConfig().       //set DEFAULT visibility for any class - look for fields only, not for getters, setters, etc.
                getDefaultVisibilityChecker().
                    withFieldVisibility(JsonAutoDetect.Visibility.ANY).
                    withGetterVisibility(JsonAutoDetect.Visibility.NONE).
                    withIsGetterVisibility(JsonAutoDetect.Visibility.NONE).
                    withSetterVisibility(JsonAutoDetect.Visibility.NONE).
                    withCreatorVisibility(JsonAutoDetect.Visibility.NONE));

        try {
            String jsonString = om.writeValueAsString(object);
            logger.info("The object was serialized successfully to json: {}", jsonString);
            return jsonString;
        } catch (IOException e) {
            logger.error("Failed to serialize object {} to json", object);
            return null;
        }
    }

}
