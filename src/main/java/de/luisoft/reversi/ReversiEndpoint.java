package de.luisoft.reversi;

import de.luisoft.reversi.engine.ReversiEngineImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Path("/reversi")
public class ReversiEndpoint {

    private static final long serialVersionUID = 1482958902388391028L;
    private static final Logger log = LoggerFactory.getLogger(ReversiEndpoint.class);

    private final ReversiEngine engine = new ReversiEngineImpl();

    @POST
    @Path("/reset")
    public void report() {
        engine.reset();
    }

    @GET
    @Path("/board")
    public Response get(@QueryParam("pretty") boolean pretty) {
        System.out.println("get " + pretty);
        if (!pretty) {
            List<Integer> l = new ArrayList<>();
            for (int i = 0; i < 64; i++) l.add(engine.getModel().getColour(i));
            return Response.ok(l).build();
        } else {
            return Response.ok(String.valueOf(engine)).build();
        }
    }

    @POST
    @Path("/board/{move}")
    public Response set(@PathParam("move") int move,
                        @QueryParam("pretty") boolean pretty) {
        try {
            engine.forceMove(move);
        } catch (IllegalArgumentException e) {
            System.out.println("illegal move");
            return Response
                    .ok("illegal move")
                    .status(400, "illegal move").build();
        }

        int otherMove = engine.getMove();
        engine.forceMove(otherMove);

        if (!pretty) {
            return Response.ok(otherMove).build();
        } else {
            return Response.ok(String.valueOf(engine.getModel())).build();
        }
    }
}
