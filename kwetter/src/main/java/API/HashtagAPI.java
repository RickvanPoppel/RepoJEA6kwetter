package API;

import DTO.DTOConvert;
import DTO.HashtagDTO;
import DTO.detHashtagDTO;
import Domain.Hashtag;
import Services.KwetterService;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import java.util.ArrayList;
import java.util.List;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("/hashtag")
public class HashtagAPI {

    KwetterService kwetterService;

    @Inject
    public HashtagAPI(KwetterService ks){
        this.kwetterService = ks;
    }

    @GET
    @Path("/get/more")
    @Produces(APPLICATION_JSON)
    public List<HashtagDTO> getAllHashtags(@Context HttpServletResponse response) {

        response.setHeader("Access-Control-Allow-Origin" , "*");
        return hashtagListToDTO(kwetterService.getBaseHashtagService().getHashtags()); }

    @GET
    @Path("/get/one/id/{id}")
    @Produces(APPLICATION_JSON)
    public HashtagDTO getHashtagById(@PathParam("id") long id, @Context HttpServletResponse response) {

        response.setHeader("Access-Control-Allow-Origin" , "*");
        Hashtag hashtag = kwetterService.getBaseHashtagService().getHashtag(id);
        detHashtagDTO kdto = new detHashtagDTO();
        if (hashtag != null)
            kdto.generateDTO(hashtag);
        return kdto;
    }

    @GET
    @Path("/get/one/content/{name}")
    @Produces(APPLICATION_JSON)
    public HashtagDTO getHashtagByName(@PathParam("name") String name, @Context HttpServletResponse response) {

        response.setHeader("Access-Control-Allow-Origin" , "*");
        Hashtag hashtag = kwetterService.getBaseHashtagService().getExactlyMatchingHashtag(name);
        detHashtagDTO kdto = new detHashtagDTO();
        if (hashtag != null)
            kdto.generateDTO(hashtag);
        return kdto;
    }

    @GET
    @Path("/get/more/content/{name}")
    @Produces(APPLICATION_JSON)
    public List<HashtagDTO> getHashtagsByName(@PathParam("name") String name, @Context HttpServletResponse response) {

        response.setHeader("Access-Control-Allow-Origin" , "*");
        return hashtagListToDTO(kwetterService.getBaseHashtagService().getMatchingHashtags(name));
    }

    @POST
    @Path("/post/insert")
    @Produces(APPLICATION_JSON)
    public HashtagDTO createHashtag(@FormParam("name") String name, @Context HttpServletResponse response) {

        response.setHeader("Access-Control-Allow-Origin" , "*");
        Hashtag hashtag = kwetterService.getBaseHashtagService().addHashtag(name);
        detHashtagDTO kdto = new detHashtagDTO();
        if (hashtag != null)
            kdto.generateDTO(hashtag);
        return kdto;
    }

    @POST
    @Path("/post/update")
    @Produces(APPLICATION_JSON)
    public HashtagDTO updateHashtag(@FormParam("id") long id, @FormParam("name") String name, @Context HttpServletResponse response) {

        response.setHeader("Access-Control-Allow-Origin" , "*");
        Hashtag hashtag = kwetterService.getBaseHashtagService().updateHashtag(id, name);
        detHashtagDTO kdto = new detHashtagDTO();
        if (hashtag != null)
            kdto.generateDTO(hashtag);
        return kdto;
    }

    @POST
    @Path("/post/delete")
    @Produces(APPLICATION_JSON)
    public List<HashtagDTO> deleteHashtag(@FormParam("id") long id, @Context HttpServletResponse response) {

        response.setHeader("Access-Control-Allow-Origin" , "*");
        kwetterService.getBaseHashtagService().deleteHashtag(id);
        return hashtagListToDTO(kwetterService.getBaseHashtagService().getHashtags());
    }

    private List<HashtagDTO> hashtagListToDTO(List<Hashtag> hashtagList) {
        List<HashtagDTO> hashtagDTOList = new ArrayList<>();
        DTOConvert.generateHashtagDTOList(hashtagList, hashtagDTOList);
        return hashtagDTOList;
    }
}
