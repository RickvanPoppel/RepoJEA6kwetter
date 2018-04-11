package API;

import DTO.DTOConvert;
import DTO.KweetDTO;
import DTO.detKweetDTO;
import Domain.Kweet;
import Domain.User;
import Services.KwetterService;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import java.util.ArrayList;
import java.util.List;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("/kweet")
public class KweetAPI {
    KwetterService kwetterService;

    @Inject
    public KweetAPI (KwetterService ks) {
        kwetterService = ks;
    }

    @GET
    @Path("/get/more")
    @Produces(APPLICATION_JSON)
    public List<KweetDTO> getAllKweets(@Context HttpServletResponse response) {

        response.setHeader("Access-Control-Allow-Origin" , "*");
        return kweetListToDTO(kwetterService.getBaseKweetService().getKweets());
    }

    @GET
    @Path("/get/one/id/{id}")
    @Produces(APPLICATION_JSON)
    public detKweetDTO getKweetById(@PathParam("id") long id, @Context HttpServletResponse response) {

        response.setHeader("Access-Control-Allow-Origin" , "*");
        Kweet kweet = kwetterService.getBaseKweetService().getKweet(id);
        detKweetDTO kdto = new detKweetDTO();
        if (kweet != null)
            kdto.generateDTO(kweet);
        return kdto;
    }

    @GET
    @Path("/get/more/content/{content}")
    @Produces(APPLICATION_JSON)
    public List<KweetDTO> getKweetsByContent(@PathParam("content") String content, @Context HttpServletResponse response) {

        response.setHeader("Access-Control-Allow-Origin" , "*");
        return kweetListToDTO(kwetterService.getBaseKweetService().getMatchesByContent(content));
    }

    @GET
    @Path("/get/more/hashtagid/{id}")
    @Produces(APPLICATION_JSON)
    public List<KweetDTO> getKweetsByHashtagId(@PathParam("id") long id, @Context HttpServletResponse response) {

        response.setHeader("Access-Control-Allow-Origin" , "*");
        return kweetListToDTO(kwetterService.getBaseKweetService().getKweetByHashtagId(id));
    }

    @GET
    @Path("/get/more/mentionid/{id}")
    @Produces(APPLICATION_JSON)
    public List<KweetDTO> getKweetsByMentionId(@PathParam("id") long id, @Context HttpServletResponse response) {

        response.setHeader("Access-Control-Allow-Origin" , "*");
        List<KweetDTO> kweetList = kweetListToDTO(kwetterService.getBaseKweetService().getKweetsByMentionId(id));
        return kweetList;
    }

    @GET
    @Path("/get/more/userid/{id}")
    @Produces(APPLICATION_JSON)
    public List<KweetDTO> getKweetsByUserId(@PathParam("id") long id, @Context HttpServletResponse response) {

        response.setHeader("Access-Control-Allow-Origin" , "*");
        return kweetListToDTO(kwetterService.getBaseKweetService().getKweetsByUserId(id));
    }

    @GET
    @Path("/get/more/userid/recent/{id}")
    @Produces(APPLICATION_JSON)
    public List<KweetDTO> getRecenteKweetsByUserId(@PathParam("id") long id, @Context HttpServletResponse response) {

        response.setHeader("Access-Control-Allow-Origin" , "*");
        return kweetListToDTO(kwetterService.getBaseKweetService().getRecenteEigenKweetsByUserId(id));
    }

    @GET
    @Path("/get/more/userid/timeline/{id}")
    @Produces(APPLICATION_JSON)
    public List<KweetDTO> getRecenteLeiderKweetsByUserId(@PathParam("id") long id, @Context HttpServletResponse response) {

        response.setHeader("Access-Control-Allow-Origin" , "*");
        return kweetListToDTO(kwetterService.getOwnAndFollowingKweets(id));
    }

    @GET
    @Path("/get/more/hashtagcontent/{content}")
    @Produces(APPLICATION_JSON)
    public List<KweetDTO> getTrends(@PathParam("content") String content, @Context HttpServletResponse response) {

        response.setHeader("Access-Control-Allow-Origin" , "*");
        return kweetListToDTO(kwetterService.getHashtagTrends(content));
    }

    @POST
    @Path("/post/insert")
    @Produces(APPLICATION_JSON)
    public detKweetDTO insertKweet(@FormParam("name") String name, @FormParam("content") String content, @Context HttpServletResponse response) {

        response.setHeader("Access-Control-Allow-Origin" , "*");
        User user = kwetterService.getBaseUserService().getUserByName(name);
        Kweet kweet = kwetterService.sendKweet(user.getId(), content);
        detKweetDTO kdto = new detKweetDTO();
        if (kweet != null)
            kdto.generateDTO(kweet);
        return kdto;
    }

    @POST
    @Path("/post/delete")
    @Produces(APPLICATION_JSON)
    public List<KweetDTO> deleteKweet(@FormParam("id") long id, @Context HttpServletResponse response) {

        response.setHeader("Access-Control-Allow-Origin" , "*");
        User user = kwetterService.getBaseKweetService().getKweet(id).getOwner();
        user.removeKweet(kwetterService.getBaseKweetService().getKweet(id));
        kwetterService.getBaseKweetService().deleteKweet(id);
        kwetterService.getBaseUserService().updateUser(user);
        return kweetListToDTO(kwetterService.getBaseKweetService().getKweetsByUserId(user.getId()));
    }

    public List<KweetDTO> kweetListToDTO(List<Kweet> kweetList) {
        List<KweetDTO> kweetDTOList = new ArrayList<>();
        DTOConvert.generateKweetDTOList(kweetList, kweetDTOList);
        return kweetDTOList;
    }
}
