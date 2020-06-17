package jp.co.tokiomarine_nichido.resources;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jboss.resteasy.annotations.Form;

import com.google.gson.Gson;

import jp.co.tokiomarine_nichido.models.Comment;
import jp.co.tokiomarine_nichido.models.Feedback;
import jp.co.tokiomarine_nichido.models.Score;
import jp.co.tokiomarine_nichido.models.StatusCode;
import jp.co.tokiomarine_nichido.util.DateUtil;

/**
 * ScoreResource for Test
 *
 * @author SKK231099 李
 *
 */
@Path("fraud-detection/api/scores")
public class ScoreResource {

    private static final Logger logger = LogManager.getLogger();

    private Gson gson;

    public ScoreResource() {
        gson = new Gson();
    }

    @GET
    public Response getScores() throws Exception {
        List<Score> scores = new ArrayList<Score>();
        for(int i=0;i<5;i++) {
            final String tempClaimId = String.valueOf(Math.round(Math.random() * 1000));
            final String tempScoreId = String.valueOf(Math.round(Math.random() * 2000));
            Score score = new Score();
            score.setClaimId(tempClaimId);
            score.setFraudScoreId(tempScoreId);
            score.setScore(i);
            scores.add(score);
        }
        String json = gson.toJson(scores);
        Response rp = Response.status(StatusCode.SUCCESS).entity(json).build();
        return rp;
    }

    @GET
    @Path("/{claimId}")
    public Response getScore(@PathParam("claimId") @DefaultValue("") String claimId) throws Exception {
        final String tempScoreId = String.valueOf(Math.round(Math.random() * 20000));
        Score score = new Score();
        score.setClaimId(claimId);
        score.setFraudScoreId(tempScoreId);
        score.setScore(500);
        String json = gson.toJson(score);
        if (claimId != null) {
            return Response.status(StatusCode.SUCCESS).entity(json).build();
        } else {
            return Response.status(StatusCode.INTERNAL_SERVER_ERROR).build();
        }
    }

    @POST
    @Path("/{claimId}/updateFeedbackComment")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateFeedback(@Form Feedback feedback) throws Exception {
        logger.info("updateFeedback Feedback.FraudScoreId=" + feedback.getFraudScoreId());
        DateUtil du = new DateUtil();
        Feedback fb = new Feedback();
        fb.setFraudScoreId(feedback.getFraudScoreId());
        fb.setComment(feedback.getComment());
        fb.setIsCorrect(feedback.getIsCorrect());
        fb.setCreateDate(feedback.getCreateDate());
        fb.setUpdateDate(du.getNewTimestamp());
        String json = gson.toJson(fb);
        if (feedback != null && feedback.getFraudScoreId() != null) {
            return Response.status(StatusCode.SUCCESS).entity(json).build();
        } else {
            return Response.status(StatusCode.INTERNAL_SERVER_ERROR).build();
        }
    }

    @POST
    @Path("/{claimId}/updateComment")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateComment(@Form Comment comment ) throws Exception {
        logger.info("updateComment Comment.ClaimId=" + comment.getClaimId());
        DateUtil du = new DateUtil();
        Comment cmt = new Comment();
        cmt.setClaimId(comment.getClaimId());
        cmt.setComment(comment.getComment());
        cmt.setId(comment.getId());
        cmt.setUserId(comment.getUserId());
        cmt.setUserName(comment.getUserName());
        cmt.setCreateDate(comment.getCreateDate());
        cmt.setUpdateDate(du.getNewTimestamp());
        String json = gson.toJson(cmt);
        if (comment != null && comment.getClaimId() != null) {
            return Response.status(StatusCode.SUCCESS).entity(json).build();
        } else {
            return Response.status(StatusCode.INTERNAL_SERVER_ERROR).build();
        }
    }


    @POST
    @Path("/{claimId}/removeComment")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response removeComment(@Form Comment comment) throws Exception {
        String json = gson.toJson(comment);
        logger.info("received=> " + comment.getClaimId() + ", " + comment.getComment());
        if (comment != null && comment.getClaimId() != null) {
            return Response.status(StatusCode.SUCCESS).entity(json).build();
        } else {
            return Response.status(StatusCode.INTERNAL_SERVER_ERROR).build();
        }
    }
}
