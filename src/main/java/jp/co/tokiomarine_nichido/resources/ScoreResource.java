package jp.co.tokiomarine_nichido.resources;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.jboss.resteasy.annotations.Form;

import jp.co.tokiomarine_nichido.models.Comment;
import jp.co.tokiomarine_nichido.models.Feedback;
import jp.co.tokiomarine_nichido.models.Score;
import jp.co.tokiomarine_nichido.services.ScoreService;
/**
 * for Angular 一覧画面、詳細画面 Data
 *
 * @author SKK231099 李
 *
 */

@Path("/scores")
public class ScoreResource {
    @Inject
    private ScoreService ss;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Score> getScores(@QueryParam("claimId") @DefaultValue("") String claimId) throws Exception {
        if (claimId.isEmpty()) {
            return ss.findAllByGroup();
        } else {
            return ss.findListByClaimId(claimId);
        }
    }

    @GET
    @Path("/{claimId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Score getScore(@PathParam("claimId") @DefaultValue("") String claimId) throws Exception {
        if (claimId.isEmpty()) {
            return null;
        }
        return ss.findById(claimId);
    }

    @POST
    @Path("{claimId}/updateFeedbackComment")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Feedback updateFeedback(@Form Feedback feedback) throws Exception {
    	return ss.updateFeedback(feedback);
    }

    @POST
    @Path("{claimId}/updateComment")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Comment updateComment(@Form Comment comment) throws Exception {
    	return ss.updateComment(comment);
    }

    @POST
    @Path("{claimId}/removeComment")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Integer removeComment(@Form Comment comment) throws Exception {
        return ss.removeComment(comment);
    }
}
