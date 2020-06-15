package jp.co.tokiomarine_nichido.resources;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.Gson;

import jp.co.tokiomarine_nichido.models.Score;

@Path("fraud-detection/api/scores")
public class ScoreResource {

    private static final Logger logger = LogManager.getLogger();

    private Gson gson;

    public ScoreResource() {
        gson = new Gson();
    }

//    @GET
//    public Response testGet() {
//        return Response.status(200).entity("Success").build();
//    }

    @GET
    public Response getScoreTest() throws Exception {
        logger.info("getScore");
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
        return Response.status(200).entity(json).build();

    }
}
