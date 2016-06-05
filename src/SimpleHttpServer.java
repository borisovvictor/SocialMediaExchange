import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import entity.Agency;
import entity.Client;
import entity.Order;
import entity.Performer;
import servicelayer.ServiceLayer;
import servicelayer.Util;


/*
 * a simple static http server
*/
public class SimpleHttpServer {

    private HttpServer server;

    public SimpleHttpServer()
    {
        try {
            server = HttpServer.create(new InetSocketAddress(8000), 0);
            server.createContext("/getOrders", new MyHandler());
            server.setExecutor(null); // creates a default executor
            server.start();
        }
        catch(IOException e)
        {
        }
    }

    static class MyHandler implements HttpHandler {
        public void handle(HttpExchange httpExchange) throws IOException {

            StringBuilder response = new StringBuilder();
            Map <String,String>parms = SimpleHttpServer.queryToMap(httpExchange.getRequestURI().getQuery());

            response.append("<html><body>");

            boolean succ = true;

            ServiceLayer sl = new ServiceLayer();

            Object user = sl.auth(parms.get("username"), parms.get("password"));
            int userID = -1;
            if(user instanceof Client){
                response.append("client");
                userID = ((Client) user).getID();
            }else if(user instanceof Performer){
                response.append("performer");
                userID = ((Performer) user).getID();
            }else if(user instanceof Agency){
                response.append("agency");
                userID = ((Agency) user).getID();
            }else if(user instanceof Util.Result){
                if(((Util.Result)user) == Util.Result.USER_NOT_EXIST){
                    response.append("USER_NOT_EXIST");

                }else{

                }
                succ = false;
            }else{
                response.append("Internal error");
                succ = false;
            }

            if (succ) {
                int maxCount = Integer.parseInt(parms.get("maxCount"));
                response.append(": " + parms.get("username") + "<br/>");
                List<Order> orders = sl.getOrdersByUserId(userID);
                response.append("Order ID   Offer ID    Client ID   Order   Conditions<br/>");
                int idx = 0;
                for (Order order : orders)
                {
                    if (idx >= maxCount)
                        break;
                    idx++;
                    response.append(order.getID() + "   " + order.getOfferID() + "   " + order.getClientID()
                            + "   " + order.getOrderStatus().toString() + "   " +  order.getSpecialConditions() + "<br/>") ;
                }
            }

            response.append("</body></html>");

            // write result
            String result = response.toString();
            httpExchange.sendResponseHeaders(200, result.length());
            OutputStream os = httpExchange.getResponseBody();
            os.write(result.getBytes());
            os.close();
        }
    }

    /**
     * returns the url parameters in a map
     * @param query
     * @return map
     */
    public static Map<String, String> queryToMap(String query){
        Map<String, String> result = new HashMap<String, String>();
        for (String param : query.split("&")) {
            String pair[] = param.split("=");
            if (pair.length>1) {
                result.put(pair[0], pair[1]);
            }else{
                result.put(pair[0], "");
            }
        }
        return result;
    }
}