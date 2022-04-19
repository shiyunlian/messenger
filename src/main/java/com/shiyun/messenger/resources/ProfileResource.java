package com.shiyun.messenger.resources;

import java.util.List;

import com.shiyun.messenger.model.Message;
import com.shiyun.messenger.model.Profile;
import com.shiyun.messenger.service.ProfileService;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/profiles")
@Consumes(MediaType.APPLICATION_JSON)//use @Produces to specify the expected response body format
@Produces(MediaType.APPLICATION_JSON)//use @Consumes to specify the expected request body format
public class ProfileResource {
	
	private ProfileService profileService = new ProfileService();
	
	@GET
	public List<Profile> getProfiles(){
		return profileService.getAllProfiles();
	}
	
    @GET
    @Path("/{profileName}")
	public Profile getProfile(@PathParam("profileName") String profileName) {
    	return profileService.getProfile(profileName);
    }
	
    @POST
    // accept the Model type as argument to bind to the request body
    public Profile addProfile(Profile profile) {
    	return profileService.addProfile(profile);
    }
    
    @PUT
    @Path("/{profileName}")
    public Profile updateProfile(@PathParam("profileName") String profileName, Profile profile) {
    	profile.setProfileName(profileName);
    	return profileService.updateProfile(profile);
    }
    
    @DELETE
    @Path("/{profileName}")
    public void deleteMessage(@PathParam("profileName") String profileName) {
    	profileService.removeProfile(profileName);
    }

}
