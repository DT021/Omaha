
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.io.InputStream;
import java.io.File;
import org.json.JSONObject;
import org.json.JSONArray;

import com.mashape.client.authentication.Authentication;
import com.mashape.client.authentication.AuthenticationParameter;
import com.mashape.client.authentication.MashapeAuthentication;
import com.mashape.client.authentication.QueryAuthentication;
import com.mashape.client.authentication.HeaderAuthentication;
import com.mashape.client.authentication.BasicAuthentication;
import com.mashape.client.authentication.OAuthAuthentication;
import com.mashape.client.authentication.OAuth10aAuthentication;
import com.mashape.client.authentication.OAuth2Authentication;
import com.mashape.client.http.ContentType;
import com.mashape.client.http.HttpClient;
import com.mashape.client.http.HttpMethod;
import com.mashape.client.http.MashapeCallback;
import com.mashape.client.http.MashapeResponse;
import com.mashape.client.http.ResponseType;
import com.mashape.client.http.utils.HttpUtils;

public class StockTwits {

	private final static String PUBLIC_DNS = "stocktwits.p.mashape.com";
    private List<Authentication> authenticationHandlers;

    public StockTwits (String mashapeKey, String consumerKey, String consumerSecret, String callbackUrl) {
        authenticationHandlers = new LinkedList<Authentication>();
        authenticationHandlers.add(new MashapeAuthentication(publicKey, privateKey));
        authenticationHandlers.add(new OAuth2Authentication(consumerKey, consumerSecret, callbackUrl));
        
    }
    
    /**
     * Synchronous call with optional parameters.
     */
    public MashapeResponse<JSONObject> accountUpdate(String name, String email, String username) {
        Map<String, Object> parameters = new HashMap<String, Object>();
        
        if (name != null && !name.equals("")) {
	parameters.put("name", name);
        }
        
        
        
        if (email != null && !email.equals("")) {
	parameters.put("email", email);
        }
        
        
        
        if (username != null && !username.equals("")) {
	parameters.put("username", username);
        }
        
        
        
        return (MashapeResponse<JSONObject>) HttpClient.doRequest(JSONObject.class,
                    HttpMethod.GET,
                    "https://" + PUBLIC_DNS + "/account/update.json",
                    parameters,
                    ContentType.FORM,
                    ResponseType.JSON,
                    authenticationHandlers);
    }

    /**
     * Synchronous call without optional parameters.
     */
    public MashapeResponse<JSONObject> accountUpdate() {
        return accountUpdate("", "", "");
    }


    /**
     * Asynchronous call with optional parameters.
     */
    public Thread accountUpdate(String name, String email, String username, MashapeCallback<JSONObject> callback) {
        Map<String, Object> parameters = new HashMap<String, Object>();
        
        
        if (name != null && !name.equals("")) {
        
            parameters.put("name", name);
        }
        
        
        
        if (email != null && !email.equals("")) {
        
            parameters.put("email", email);
        }
        
        
        
        if (username != null && !username.equals("")) {
        
            parameters.put("username", username);
        }
        
        
        return HttpClient.doRequest(JSONObject.class,
                    HttpMethod.GET,
                    "https://" + PUBLIC_DNS + "/account/update.json",
                    parameters,
                    ContentType.FORM,
                    ResponseType.JSON,
                    authenticationHandlers,
                    callback);
    }

    /**
     * Asynchronous call without optional parameters.
     */
    public Thread accountUpdate(MashapeCallback<JSONObject> callback) {
        return accountUpdate("", "", "", callback);
    }

    /**
     * Synchronous call with optional parameters.
     */
    public MashapeResponse<JSONObject> accountVerify(String callback) {
        Map<String, Object> parameters = new HashMap<String, Object>();
        
        if (callback != null && !callback.equals("")) {
	parameters.put("callback", callback);
        }
        
        
        
        return (MashapeResponse<JSONObject>) HttpClient.doRequest(JSONObject.class,
                    HttpMethod.GET,
                    "https://" + PUBLIC_DNS + "/account/verify.json",
                    parameters,
                    ContentType.FORM,
                    ResponseType.JSON,
                    authenticationHandlers);
    }

    /**
     * Synchronous call without optional parameters.
     */
    public MashapeResponse<JSONObject> accountVerify() {
        return accountVerify("");
    }


    /**
     * Asynchronous call with optional parameters.
     */
    public Thread accountVerify(String callback, MashapeCallback<JSONObject> callback) {
        Map<String, Object> parameters = new HashMap<String, Object>();
        
        
        if (callback != null && !callback.equals("")) {
        
            parameters.put("callback", callback);
        }
        
        
        return HttpClient.doRequest(JSONObject.class,
                    HttpMethod.GET,
                    "https://" + PUBLIC_DNS + "/account/verify.json",
                    parameters,
                    ContentType.FORM,
                    ResponseType.JSON,
                    authenticationHandlers,
                    callback);
    }

    /**
     * Asynchronous call without optional parameters.
     */
    public Thread accountVerify(MashapeCallback<JSONObject> callback) {
        return accountVerify("", callback);
    }

    /**
     * Synchronous call with optional parameters.
     */
    public MashapeResponse<JSONObject> blocksCreate(String _id) {
        Map<String, Object> parameters = new HashMap<String, Object>();
        
        
        return (MashapeResponse<JSONObject>) HttpClient.doRequest(JSONObject.class,
                    HttpMethod.POST,
                    "https://" + PUBLIC_DNS + "/blocks/create/" + ((HttpUtils.encodeUrl(_id) == null) ? "" : HttpUtils.encodeUrl(_id)) + ".json",
                    parameters,
                    ContentType.FORM,
                    ResponseType.JSON,
                    authenticationHandlers);
    }

    /**
     * Asynchronous call with optional parameters.
     */
    public Thread blocksCreate(String _id, MashapeCallback<JSONObject> callback) {
        Map<String, Object> parameters = new HashMap<String, Object>();
        
        
        return HttpClient.doRequest(JSONObject.class,
                    HttpMethod.POST,
                    "https://" + PUBLIC_DNS + "/blocks/create/" + ((HttpUtils.encodeUrl(_id) == null) ? "" : HttpUtils.encodeUrl(_id)) + ".json",
                    parameters,
                    ContentType.FORM,
                    ResponseType.JSON,
                    authenticationHandlers,
                    callback);
    }

    /**
     * Synchronous call with optional parameters.
     */
    public MashapeResponse<JSONObject> blocksDestroy(String _id) {
        Map<String, Object> parameters = new HashMap<String, Object>();
        
        
        return (MashapeResponse<JSONObject>) HttpClient.doRequest(JSONObject.class,
                    HttpMethod.POST,
                    "https://" + PUBLIC_DNS + "/blocks/destroy/" + ((HttpUtils.encodeUrl(_id) == null) ? "" : HttpUtils.encodeUrl(_id)) + ".json",
                    parameters,
                    ContentType.FORM,
                    ResponseType.JSON,
                    authenticationHandlers);
    }

    /**
     * Asynchronous call with optional parameters.
     */
    public Thread blocksDestroy(String _id, MashapeCallback<JSONObject> callback) {
        Map<String, Object> parameters = new HashMap<String, Object>();
        
        
        return HttpClient.doRequest(JSONObject.class,
                    HttpMethod.POST,
                    "https://" + PUBLIC_DNS + "/blocks/destroy/" + ((HttpUtils.encodeUrl(_id) == null) ? "" : HttpUtils.encodeUrl(_id)) + ".json",
                    parameters,
                    ContentType.FORM,
                    ResponseType.JSON,
                    authenticationHandlers,
                    callback);
    }

    /**
     * Synchronous call with optional parameters.
     */
    public MashapeResponse<JSONObject> messagesCreate(String body, String inreplytomessageid, String chart, String sentiment) {
        Map<String, Object> parameters = new HashMap<String, Object>();
        
        if (body != null && !body.equals("")) {
	parameters.put("body", body);
        }
        
        
        
        if (inreplytomessageid != null && !inreplytomessageid.equals("")) {
	parameters.put("in_reply_to_message_id", inreplytomessageid);
        }
        
        
        
        if (chart != null && !chart.equals("")) {
	parameters.put("chart", chart);
        }
        
        
        
        if (sentiment != null && !sentiment.equals("")) {
	parameters.put("sentiment", sentiment);
        }
        
        
        
        return (MashapeResponse<JSONObject>) HttpClient.doRequest(JSONObject.class,
                    HttpMethod.POST,
                    "https://" + PUBLIC_DNS + "/messages/create.json",
                    parameters,
                    ContentType.FORM,
                    ResponseType.JSON,
                    authenticationHandlers);
    }

    /**
     * Synchronous call without optional parameters.
     */
    public MashapeResponse<JSONObject> messagesCreate(String body) {
        return messagesCreate(body, "", "", "");
    }


    /**
     * Asynchronous call with optional parameters.
     */
    public Thread messagesCreate(String body, String inreplytomessageid, String chart, String sentiment, MashapeCallback<JSONObject> callback) {
        Map<String, Object> parameters = new HashMap<String, Object>();
        
        
        if (body != null && !body.equals("")) {
        
            parameters.put("body", body);
        }
        
        
        
        if (inreplytomessageid != null && !inreplytomessageid.equals("")) {
        
            parameters.put("in_reply_to_message_id", inreplytomessageid);
        }
        
        
        
        if (chart != null && !chart.equals("")) {
        
            parameters.put("chart", chart);
        }
        
        
        
        if (sentiment != null && !sentiment.equals("")) {
        
            parameters.put("sentiment", sentiment);
        }
        
        
        return HttpClient.doRequest(JSONObject.class,
                    HttpMethod.POST,
                    "https://" + PUBLIC_DNS + "/messages/create.json",
                    parameters,
                    ContentType.FORM,
                    ResponseType.JSON,
                    authenticationHandlers,
                    callback);
    }

    /**
     * Asynchronous call without optional parameters.
     */
    public Thread messagesCreate(String body, MashapeCallback<JSONObject> callback) {
        return messagesCreate(body, "", "", "", callback);
    }

    /**
     * Synchronous call with optional parameters.
     */
    public MashapeResponse<JSONObject> messagesLike(String _id) {
        Map<String, Object> parameters = new HashMap<String, Object>();
        
        if (_id != null && !_id.equals("")) {
	parameters.put("id", _id);
        }
        
        
        
        return (MashapeResponse<JSONObject>) HttpClient.doRequest(JSONObject.class,
                    HttpMethod.GET,
                    "https://" + PUBLIC_DNS + "/messages/like.json",
                    parameters,
                    ContentType.FORM,
                    ResponseType.JSON,
                    authenticationHandlers);
    }

    /**
     * Asynchronous call with optional parameters.
     */
    public Thread messagesLike(String _id, MashapeCallback<JSONObject> callback) {
        Map<String, Object> parameters = new HashMap<String, Object>();
        
        
        if (_id != null && !_id.equals("")) {
        
            parameters.put("id", _id);
        }
        
        
        return HttpClient.doRequest(JSONObject.class,
                    HttpMethod.GET,
                    "https://" + PUBLIC_DNS + "/messages/like.json",
                    parameters,
                    ContentType.FORM,
                    ResponseType.JSON,
                    authenticationHandlers,
                    callback);
    }

    /**
     * Synchronous call with optional parameters.
     */
    public MashapeResponse<JSONObject> messagesShow(String _id, String conversation, String callback) {
        Map<String, Object> parameters = new HashMap<String, Object>();
        
        
        if (conversation != null && !conversation.equals("")) {
	parameters.put("conversation", conversation);
        }
        
        
        
        if (callback != null && !callback.equals("")) {
	parameters.put("callback", callback);
        }
        
        
        
        return (MashapeResponse<JSONObject>) HttpClient.doRequest(JSONObject.class,
                    HttpMethod.GET,
                    "https://" + PUBLIC_DNS + "/messages/show/" + ((HttpUtils.encodeUrl(_id) == null) ? "" : HttpUtils.encodeUrl(_id)) + ".json",
                    parameters,
                    ContentType.FORM,
                    ResponseType.JSON,
                    authenticationHandlers);
    }

    /**
     * Synchronous call without optional parameters.
     */
    public MashapeResponse<JSONObject> messagesShow(String _id) {
        return messagesShow(_id, "", "");
    }


    /**
     * Asynchronous call with optional parameters.
     */
    public Thread messagesShow(String _id, String conversation, String callback, MashapeCallback<JSONObject> callback) {
        Map<String, Object> parameters = new HashMap<String, Object>();
        
        
        
        if (conversation != null && !conversation.equals("")) {
        
            parameters.put("conversation", conversation);
        }
        
        
        
        if (callback != null && !callback.equals("")) {
        
            parameters.put("callback", callback);
        }
        
        
        return HttpClient.doRequest(JSONObject.class,
                    HttpMethod.GET,
                    "https://" + PUBLIC_DNS + "/messages/show/" + ((HttpUtils.encodeUrl(_id) == null) ? "" : HttpUtils.encodeUrl(_id)) + ".json",
                    parameters,
                    ContentType.FORM,
                    ResponseType.JSON,
                    authenticationHandlers,
                    callback);
    }

    /**
     * Asynchronous call without optional parameters.
     */
    public Thread messagesShow(String _id, MashapeCallback<JSONObject> callback) {
        return messagesShow(_id, "", "", callback);
    }

    /**
     * Synchronous call with optional parameters.
     */
    public MashapeResponse<JSONObject> messagesUnlike(String _id) {
        Map<String, Object> parameters = new HashMap<String, Object>();
        
        if (_id != null && !_id.equals("")) {
	parameters.put("id", _id);
        }
        
        
        
        return (MashapeResponse<JSONObject>) HttpClient.doRequest(JSONObject.class,
                    HttpMethod.GET,
                    "https://" + PUBLIC_DNS + "/messages/unlike.json",
                    parameters,
                    ContentType.FORM,
                    ResponseType.JSON,
                    authenticationHandlers);
    }

    /**
     * Asynchronous call with optional parameters.
     */
    public Thread messagesUnlike(String _id, MashapeCallback<JSONObject> callback) {
        Map<String, Object> parameters = new HashMap<String, Object>();
        
        
        if (_id != null && !_id.equals("")) {
        
            parameters.put("id", _id);
        }
        
        
        return HttpClient.doRequest(JSONObject.class,
                    HttpMethod.GET,
                    "https://" + PUBLIC_DNS + "/messages/unlike.json",
                    parameters,
                    ContentType.FORM,
                    ResponseType.JSON,
                    authenticationHandlers,
                    callback);
    }

    /**
     * Synchronous call with optional parameters.
     */
    public MashapeResponse<JSONObject> streamsDirect(String since, String max, String limit, String callback) {
        Map<String, Object> parameters = new HashMap<String, Object>();
        
        if (since != null && !since.equals("")) {
	parameters.put("since", since);
        }
        
        
        
        if (max != null && !max.equals("")) {
	parameters.put("max", max);
        }
        
        
        
        if (limit != null && !limit.equals("")) {
	parameters.put("limit", limit);
        }
        
        
        
        if (callback != null && !callback.equals("")) {
	parameters.put("callback", callback);
        }
        
        
        
        return (MashapeResponse<JSONObject>) HttpClient.doRequest(JSONObject.class,
                    HttpMethod.GET,
                    "https://" + PUBLIC_DNS + "/streams/direct.json",
                    parameters,
                    ContentType.FORM,
                    ResponseType.JSON,
                    authenticationHandlers);
    }

    /**
     * Synchronous call without optional parameters.
     */
    public MashapeResponse<JSONObject> streamsDirect() {
        return streamsDirect("", "", "", "");
    }


    /**
     * Asynchronous call with optional parameters.
     */
    public Thread streamsDirect(String since, String max, String limit, String callback, MashapeCallback<JSONObject> callback) {
        Map<String, Object> parameters = new HashMap<String, Object>();
        
        
        if (since != null && !since.equals("")) {
        
            parameters.put("since", since);
        }
        
        
        
        if (max != null && !max.equals("")) {
        
            parameters.put("max", max);
        }
        
        
        
        if (limit != null && !limit.equals("")) {
        
            parameters.put("limit", limit);
        }
        
        
        
        if (callback != null && !callback.equals("")) {
        
            parameters.put("callback", callback);
        }
        
        
        return HttpClient.doRequest(JSONObject.class,
                    HttpMethod.GET,
                    "https://" + PUBLIC_DNS + "/streams/direct.json",
                    parameters,
                    ContentType.FORM,
                    ResponseType.JSON,
                    authenticationHandlers,
                    callback);
    }

    /**
     * Asynchronous call without optional parameters.
     */
    public Thread streamsDirect(MashapeCallback<JSONObject> callback) {
        return streamsDirect("", "", "", "", callback);
    }

    /**
     * Synchronous call with optional parameters.
     */
    public MashapeResponse<JSONObject> streamsFriends(String since, String max, String limit, String callback, String filter) {
        Map<String, Object> parameters = new HashMap<String, Object>();
        
        if (since != null && !since.equals("")) {
	parameters.put("since", since);
        }
        
        
        
        if (max != null && !max.equals("")) {
	parameters.put("max", max);
        }
        
        
        
        if (limit != null && !limit.equals("")) {
	parameters.put("limit", limit);
        }
        
        
        
        if (callback != null && !callback.equals("")) {
	parameters.put("callback", callback);
        }
        
        
        
        if (filter != null && !filter.equals("")) {
	parameters.put("filter", filter);
        }
        
        
        
        return (MashapeResponse<JSONObject>) HttpClient.doRequest(JSONObject.class,
                    HttpMethod.GET,
                    "https://" + PUBLIC_DNS + "/streams/friends.json",
                    parameters,
                    ContentType.FORM,
                    ResponseType.JSON,
                    authenticationHandlers);
    }

    /**
     * Synchronous call without optional parameters.
     */
    public MashapeResponse<JSONObject> streamsFriends() {
        return streamsFriends("", "", "", "", "");
    }


    /**
     * Asynchronous call with optional parameters.
     */
    public Thread streamsFriends(String since, String max, String limit, String callback, String filter, MashapeCallback<JSONObject> callback) {
        Map<String, Object> parameters = new HashMap<String, Object>();
        
        
        if (since != null && !since.equals("")) {
        
            parameters.put("since", since);
        }
        
        
        
        if (max != null && !max.equals("")) {
        
            parameters.put("max", max);
        }
        
        
        
        if (limit != null && !limit.equals("")) {
        
            parameters.put("limit", limit);
        }
        
        
        
        if (callback != null && !callback.equals("")) {
        
            parameters.put("callback", callback);
        }
        
        
        
        if (filter != null && !filter.equals("")) {
        
            parameters.put("filter", filter);
        }
        
        
        return HttpClient.doRequest(JSONObject.class,
                    HttpMethod.GET,
                    "https://" + PUBLIC_DNS + "/streams/friends.json",
                    parameters,
                    ContentType.FORM,
                    ResponseType.JSON,
                    authenticationHandlers,
                    callback);
    }

    /**
     * Asynchronous call without optional parameters.
     */
    public Thread streamsFriends(MashapeCallback<JSONObject> callback) {
        return streamsFriends("", "", "", "", "", callback);
    }

    /**
     * Synchronous call with optional parameters.
     */
    public MashapeResponse<JSONObject> streamsHome(String since, String max, String limit, String callback, String filter) {
        Map<String, Object> parameters = new HashMap<String, Object>();
        
        if (since != null && !since.equals("")) {
	parameters.put("since", since);
        }
        
        
        
        if (max != null && !max.equals("")) {
	parameters.put("max", max);
        }
        
        
        
        if (limit != null && !limit.equals("")) {
	parameters.put("limit", limit);
        }
        
        
        
        if (callback != null && !callback.equals("")) {
	parameters.put("callback", callback);
        }
        
        
        
        if (filter != null && !filter.equals("")) {
	parameters.put("filter", filter);
        }
        
        
        
        return (MashapeResponse<JSONObject>) HttpClient.doRequest(JSONObject.class,
                    HttpMethod.GET,
                    "https://" + PUBLIC_DNS + "/streams/home.json",
                    parameters,
                    ContentType.FORM,
                    ResponseType.JSON,
                    authenticationHandlers);
    }

    /**
     * Synchronous call without optional parameters.
     */
    public MashapeResponse<JSONObject> streamsHome() {
        return streamsHome("", "", "", "", "");
    }


    /**
     * Asynchronous call with optional parameters.
     */
    public Thread streamsHome(String since, String max, String limit, String callback, String filter, MashapeCallback<JSONObject> callback) {
        Map<String, Object> parameters = new HashMap<String, Object>();
        
        
        if (since != null && !since.equals("")) {
        
            parameters.put("since", since);
        }
        
        
        
        if (max != null && !max.equals("")) {
        
            parameters.put("max", max);
        }
        
        
        
        if (limit != null && !limit.equals("")) {
        
            parameters.put("limit", limit);
        }
        
        
        
        if (callback != null && !callback.equals("")) {
        
            parameters.put("callback", callback);
        }
        
        
        
        if (filter != null && !filter.equals("")) {
        
            parameters.put("filter", filter);
        }
        
        
        return HttpClient.doRequest(JSONObject.class,
                    HttpMethod.GET,
                    "https://" + PUBLIC_DNS + "/streams/home.json",
                    parameters,
                    ContentType.FORM,
                    ResponseType.JSON,
                    authenticationHandlers,
                    callback);
    }

    /**
     * Asynchronous call without optional parameters.
     */
    public Thread streamsHome(MashapeCallback<JSONObject> callback) {
        return streamsHome("", "", "", "", "", callback);
    }

    /**
     * Synchronous call with optional parameters.
     */
    public MashapeResponse<JSONObject> streamsInvestorRelations(String since, String max, String limit, String callback) {
        Map<String, Object> parameters = new HashMap<String, Object>();
        
        if (since != null && !since.equals("")) {
	parameters.put("since", since);
        }
        
        
        
        if (max != null && !max.equals("")) {
	parameters.put("max", max);
        }
        
        
        
        if (limit != null && !limit.equals("")) {
	parameters.put("limit", limit);
        }
        
        
        
        if (callback != null && !callback.equals("")) {
	parameters.put("callback", callback);
        }
        
        
        
        return (MashapeResponse<JSONObject>) HttpClient.doRequest(JSONObject.class,
                    HttpMethod.GET,
                    "https://" + PUBLIC_DNS + "/streams/investor_relations.json",
                    parameters,
                    ContentType.FORM,
                    ResponseType.JSON,
                    authenticationHandlers);
    }

    /**
     * Synchronous call without optional parameters.
     */
    public MashapeResponse<JSONObject> streamsInvestorRelations() {
        return streamsInvestorRelations("", "", "", "");
    }


    /**
     * Asynchronous call with optional parameters.
     */
    public Thread streamsInvestorRelations(String since, String max, String limit, String callback, MashapeCallback<JSONObject> callback) {
        Map<String, Object> parameters = new HashMap<String, Object>();
        
        
        if (since != null && !since.equals("")) {
        
            parameters.put("since", since);
        }
        
        
        
        if (max != null && !max.equals("")) {
        
            parameters.put("max", max);
        }
        
        
        
        if (limit != null && !limit.equals("")) {
        
            parameters.put("limit", limit);
        }
        
        
        
        if (callback != null && !callback.equals("")) {
        
            parameters.put("callback", callback);
        }
        
        
        return HttpClient.doRequest(JSONObject.class,
                    HttpMethod.GET,
                    "https://" + PUBLIC_DNS + "/streams/investor_relations.json",
                    parameters,
                    ContentType.FORM,
                    ResponseType.JSON,
                    authenticationHandlers,
                    callback);
    }

    /**
     * Asynchronous call without optional parameters.
     */
    public Thread streamsInvestorRelations(MashapeCallback<JSONObject> callback) {
        return streamsInvestorRelations("", "", "", "", callback);
    }

    /**
     * Synchronous call with optional parameters.
     */
    public MashapeResponse<JSONObject> streamsMentions(String since, String max, String limit, String callback) {
        Map<String, Object> parameters = new HashMap<String, Object>();
        
        if (since != null && !since.equals("")) {
	parameters.put("since", since);
        }
        
        
        
        if (max != null && !max.equals("")) {
	parameters.put("max", max);
        }
        
        
        
        if (limit != null && !limit.equals("")) {
	parameters.put("limit", limit);
        }
        
        
        
        if (callback != null && !callback.equals("")) {
	parameters.put("callback", callback);
        }
        
        
        
        return (MashapeResponse<JSONObject>) HttpClient.doRequest(JSONObject.class,
                    HttpMethod.GET,
                    "https://" + PUBLIC_DNS + "/streams/mentions.json",
                    parameters,
                    ContentType.FORM,
                    ResponseType.JSON,
                    authenticationHandlers);
    }

    /**
     * Synchronous call without optional parameters.
     */
    public MashapeResponse<JSONObject> streamsMentions() {
        return streamsMentions("", "", "", "");
    }


    /**
     * Asynchronous call with optional parameters.
     */
    public Thread streamsMentions(String since, String max, String limit, String callback, MashapeCallback<JSONObject> callback) {
        Map<String, Object> parameters = new HashMap<String, Object>();
        
        
        if (since != null && !since.equals("")) {
        
            parameters.put("since", since);
        }
        
        
        
        if (max != null && !max.equals("")) {
        
            parameters.put("max", max);
        }
        
        
        
        if (limit != null && !limit.equals("")) {
        
            parameters.put("limit", limit);
        }
        
        
        
        if (callback != null && !callback.equals("")) {
        
            parameters.put("callback", callback);
        }
        
        
        return HttpClient.doRequest(JSONObject.class,
                    HttpMethod.GET,
                    "https://" + PUBLIC_DNS + "/streams/mentions.json",
                    parameters,
                    ContentType.FORM,
                    ResponseType.JSON,
                    authenticationHandlers,
                    callback);
    }

    /**
     * Asynchronous call without optional parameters.
     */
    public Thread streamsMentions(MashapeCallback<JSONObject> callback) {
        return streamsMentions("", "", "", "", callback);
    }

    /**
     * Synchronous call with optional parameters.
     */
    public MashapeResponse<JSONObject> streamsSymbol(String _id, String since, String max, String limit, String callback, String filter) {
        Map<String, Object> parameters = new HashMap<String, Object>();
        
        
        if (since != null && !since.equals("")) {
	parameters.put("since", since);
        }
        
        
        
        if (max != null && !max.equals("")) {
	parameters.put("max", max);
        }
        
        
        
        if (limit != null && !limit.equals("")) {
	parameters.put("limit", limit);
        }
        
        
        
        if (callback != null && !callback.equals("")) {
	parameters.put("callback", callback);
        }
        
        
        
        if (filter != null && !filter.equals("")) {
	parameters.put("filter", filter);
        }
        
        
        
        return (MashapeResponse<JSONObject>) HttpClient.doRequest(JSONObject.class,
                    HttpMethod.GET,
                    "https://" + PUBLIC_DNS + "/streams/symbol/" + ((HttpUtils.encodeUrl(_id) == null) ? "" : HttpUtils.encodeUrl(_id)) + ".json",
                    parameters,
                    ContentType.FORM,
                    ResponseType.JSON,
                    authenticationHandlers);
    }

    /**
     * Synchronous call without optional parameters.
     */
    public MashapeResponse<JSONObject> streamsSymbol(String _id) {
        return streamsSymbol(_id, "", "", "", "", "");
    }


    /**
     * Asynchronous call with optional parameters.
     */
    public Thread streamsSymbol(String _id, String since, String max, String limit, String callback, String filter, MashapeCallback<JSONObject> callback) {
        Map<String, Object> parameters = new HashMap<String, Object>();
        
        
        
        if (since != null && !since.equals("")) {
        
            parameters.put("since", since);
        }
        
        
        
        if (max != null && !max.equals("")) {
        
            parameters.put("max", max);
        }
        
        
        
        if (limit != null && !limit.equals("")) {
        
            parameters.put("limit", limit);
        }
        
        
        
        if (callback != null && !callback.equals("")) {
        
            parameters.put("callback", callback);
        }
        
        
        
        if (filter != null && !filter.equals("")) {
        
            parameters.put("filter", filter);
        }
        
        
        return HttpClient.doRequest(JSONObject.class,
                    HttpMethod.GET,
                    "https://" + PUBLIC_DNS + "/streams/symbol/" + ((HttpUtils.encodeUrl(_id) == null) ? "" : HttpUtils.encodeUrl(_id)) + ".json",
                    parameters,
                    ContentType.FORM,
                    ResponseType.JSON,
                    authenticationHandlers,
                    callback);
    }

    /**
     * Asynchronous call without optional parameters.
     */
    public Thread streamsSymbol(String _id, MashapeCallback<JSONObject> callback) {
        return streamsSymbol(_id, "", "", "", "", "", callback);
    }

    /**
     * Synchronous call with optional parameters.
     */
    public MashapeResponse<JSONObject> streamsUser(String _id, String since, String max, String limit, String callback, String filter) {
        Map<String, Object> parameters = new HashMap<String, Object>();
        
        
        if (since != null && !since.equals("")) {
	parameters.put("since", since);
        }
        
        
        
        if (max != null && !max.equals("")) {
	parameters.put("max", max);
        }
        
        
        
        if (limit != null && !limit.equals("")) {
	parameters.put("limit", limit);
        }
        
        
        
        if (callback != null && !callback.equals("")) {
	parameters.put("callback", callback);
        }
        
        
        
        if (filter != null && !filter.equals("")) {
	parameters.put("filter", filter);
        }
        
        
        
        return (MashapeResponse<JSONObject>) HttpClient.doRequest(JSONObject.class,
                    HttpMethod.GET,
                    "https://" + PUBLIC_DNS + "/streams/user/" + ((HttpUtils.encodeUrl(_id) == null) ? "" : HttpUtils.encodeUrl(_id)) + ".json",
                    parameters,
                    ContentType.FORM,
                    ResponseType.JSON,
                    authenticationHandlers);
    }

    /**
     * Synchronous call without optional parameters.
     */
    public MashapeResponse<JSONObject> streamsUser(String _id) {
        return streamsUser(_id, "", "", "", "", "");
    }


    /**
     * Asynchronous call with optional parameters.
     */
    public Thread streamsUser(String _id, String since, String max, String limit, String callback, String filter, MashapeCallback<JSONObject> callback) {
        Map<String, Object> parameters = new HashMap<String, Object>();
        
        
        
        if (since != null && !since.equals("")) {
        
            parameters.put("since", since);
        }
        
        
        
        if (max != null && !max.equals("")) {
        
            parameters.put("max", max);
        }
        
        
        
        if (limit != null && !limit.equals("")) {
        
            parameters.put("limit", limit);
        }
        
        
        
        if (callback != null && !callback.equals("")) {
        
            parameters.put("callback", callback);
        }
        
        
        
        if (filter != null && !filter.equals("")) {
        
            parameters.put("filter", filter);
        }
        
        
        return HttpClient.doRequest(JSONObject.class,
                    HttpMethod.GET,
                    "https://" + PUBLIC_DNS + "/streams/user/" + ((HttpUtils.encodeUrl(_id) == null) ? "" : HttpUtils.encodeUrl(_id)) + ".json",
                    parameters,
                    ContentType.FORM,
                    ResponseType.JSON,
                    authenticationHandlers,
                    callback);
    }

    /**
     * Asynchronous call without optional parameters.
     */
    public Thread streamsUser(String _id, MashapeCallback<JSONObject> callback) {
        return streamsUser(_id, "", "", "", "", "", callback);
    }

    /**
     * Synchronous call with optional parameters.
     */
    public MashapeResponse<JSONObject> streamsWatchlist(String _id, String since, String max, String limit, String callback) {
        Map<String, Object> parameters = new HashMap<String, Object>();
        
        
        if (since != null && !since.equals("")) {
	parameters.put("since", since);
        }
        
        
        
        if (max != null && !max.equals("")) {
	parameters.put("max", max);
        }
        
        
        
        if (limit != null && !limit.equals("")) {
	parameters.put("limit", limit);
        }
        
        
        
        if (callback != null && !callback.equals("")) {
	parameters.put("callback", callback);
        }
        
        
        
        return (MashapeResponse<JSONObject>) HttpClient.doRequest(JSONObject.class,
                    HttpMethod.GET,
                    "https://" + PUBLIC_DNS + "/streams/watchlist/" + ((HttpUtils.encodeUrl(_id) == null) ? "" : HttpUtils.encodeUrl(_id)) + ".json",
                    parameters,
                    ContentType.FORM,
                    ResponseType.JSON,
                    authenticationHandlers);
    }

    /**
     * Synchronous call without optional parameters.
     */
    public MashapeResponse<JSONObject> streamsWatchlist(String _id) {
        return streamsWatchlist(_id, "", "", "", "");
    }


    /**
     * Asynchronous call with optional parameters.
     */
    public Thread streamsWatchlist(String _id, String since, String max, String limit, String callback, MashapeCallback<JSONObject> callback) {
        Map<String, Object> parameters = new HashMap<String, Object>();
        
        
        
        if (since != null && !since.equals("")) {
        
            parameters.put("since", since);
        }
        
        
        
        if (max != null && !max.equals("")) {
        
            parameters.put("max", max);
        }
        
        
        
        if (limit != null && !limit.equals("")) {
        
            parameters.put("limit", limit);
        }
        
        
        
        if (callback != null && !callback.equals("")) {
        
            parameters.put("callback", callback);
        }
        
        
        return HttpClient.doRequest(JSONObject.class,
                    HttpMethod.GET,
                    "https://" + PUBLIC_DNS + "/streams/watchlist/" + ((HttpUtils.encodeUrl(_id) == null) ? "" : HttpUtils.encodeUrl(_id)) + ".json",
                    parameters,
                    ContentType.FORM,
                    ResponseType.JSON,
                    authenticationHandlers,
                    callback);
    }

    /**
     * Asynchronous call without optional parameters.
     */
    public Thread streamsWatchlist(String _id, MashapeCallback<JSONObject> callback) {
        return streamsWatchlist(_id, "", "", "", "", callback);
    }

    /**
     * Synchronous call with optional parameters.
     */
    public MashapeResponse<JSONObject> watchlistsIndex(String callback) {
        Map<String, Object> parameters = new HashMap<String, Object>();
        
        if (callback != null && !callback.equals("")) {
	parameters.put("callback", callback);
        }
        
        
        
        return (MashapeResponse<JSONObject>) HttpClient.doRequest(JSONObject.class,
                    HttpMethod.GET,
                    "https://" + PUBLIC_DNS + "/watchlists.json",
                    parameters,
                    ContentType.FORM,
                    ResponseType.JSON,
                    authenticationHandlers);
    }

    /**
     * Synchronous call without optional parameters.
     */
    public MashapeResponse<JSONObject> watchlistsIndex() {
        return watchlistsIndex("");
    }


    /**
     * Asynchronous call with optional parameters.
     */
    public Thread watchlistsIndex(String callback, MashapeCallback<JSONObject> callback) {
        Map<String, Object> parameters = new HashMap<String, Object>();
        
        
        if (callback != null && !callback.equals("")) {
        
            parameters.put("callback", callback);
        }
        
        
        return HttpClient.doRequest(JSONObject.class,
                    HttpMethod.GET,
                    "https://" + PUBLIC_DNS + "/watchlists.json",
                    parameters,
                    ContentType.FORM,
                    ResponseType.JSON,
                    authenticationHandlers,
                    callback);
    }

    /**
     * Asynchronous call without optional parameters.
     */
    public Thread watchlistsIndex(MashapeCallback<JSONObject> callback) {
        return watchlistsIndex("", callback);
    }

    /**
     * Synchronous call with optional parameters.
     */
    public MashapeResponse<JSONObject> watchlistsShow(String _id, String callback) {
        Map<String, Object> parameters = new HashMap<String, Object>();
        
        
        if (callback != null && !callback.equals("")) {
	parameters.put("callback", callback);
        }
        
        
        
        return (MashapeResponse<JSONObject>) HttpClient.doRequest(JSONObject.class,
                    HttpMethod.GET,
                    "https://" + PUBLIC_DNS + "/watchlists/show/" + ((HttpUtils.encodeUrl(_id) == null) ? "" : HttpUtils.encodeUrl(_id)) + ".json",
                    parameters,
                    ContentType.FORM,
                    ResponseType.JSON,
                    authenticationHandlers);
    }

    /**
     * Synchronous call without optional parameters.
     */
    public MashapeResponse<JSONObject> watchlistsShow(String _id) {
        return watchlistsShow(_id, "");
    }


    /**
     * Asynchronous call with optional parameters.
     */
    public Thread watchlistsShow(String _id, String callback, MashapeCallback<JSONObject> callback) {
        Map<String, Object> parameters = new HashMap<String, Object>();
        
        
        
        if (callback != null && !callback.equals("")) {
        
            parameters.put("callback", callback);
        }
        
        
        return HttpClient.doRequest(JSONObject.class,
                    HttpMethod.GET,
                    "https://" + PUBLIC_DNS + "/watchlists/show/" + ((HttpUtils.encodeUrl(_id) == null) ? "" : HttpUtils.encodeUrl(_id)) + ".json",
                    parameters,
                    ContentType.FORM,
                    ResponseType.JSON,
                    authenticationHandlers,
                    callback);
    }

    /**
     * Asynchronous call without optional parameters.
     */
    public Thread watchlistsShow(String _id, MashapeCallback<JSONObject> callback) {
        return watchlistsShow(_id, "", callback);
    }


	public MashapeResponse<JSONObject> getOAuthUrl() {
		return getOAuthUrl("");
	}
	
	public Thread getOAuthUrl(MashapeCallback<JSONObject> callback) {
		return getOAuthUrl(null, callback);
	}
	
	public Thread getOAuthUrl(String scope, MashapeCallback<JSONObject> callback) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		if (authenticationHandlers != null) {
			for (Authentication authentication : authenticationHandlers) {
				if (authentication instanceof OAuthAuthentication) {
					Map<String, String> queryParameters = authentication.getQueryParameters();
					parameters.put(OAuthAuthentication.CONSUMER_KEY, queryParameters.get(OAuthAuthentication.CONSUMER_KEY));
					parameters.put(OAuthAuthentication.CONSUMER_SECRET, queryParameters.get(OAuthAuthentication.CONSUMER_SECRET));
					parameters.put(OAuthAuthentication.CALLBACK_URL, queryParameters.get(OAuthAuthentication.CALLBACK_URL));
				}
			}
		}
		if (scope != null && scope.trim() != "") {
			parameters.put("scope", scope);
		}
		return HttpClient.doRequest(JSONObject.class,
                    HttpMethod.POST,
                    "https://" + PUBLIC_DNS + "/oauth_url",
                    parameters,
                    ContentType.FORM,
                    ResponseType.JSON,
                    authenticationHandlers, callback);
	}
	
	public MashapeResponse<JSONObject> getOAuthUrl(String scope) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		if (authenticationHandlers != null) {
			for (Authentication authentication : authenticationHandlers) {
				if (authentication instanceof OAuthAuthentication) {
					Map<String, String> queryParameters = authentication.getQueryParameters();
					parameters.put(OAuthAuthentication.CONSUMER_KEY, queryParameters.get(OAuthAuthentication.CONSUMER_KEY));
					parameters.put(OAuthAuthentication.CONSUMER_SECRET, queryParameters.get(OAuthAuthentication.CONSUMER_SECRET));
					parameters.put(OAuthAuthentication.CALLBACK_URL, queryParameters.get(OAuthAuthentication.CALLBACK_URL));
				}
			}
		}
		if (scope != null && scope.trim() != "") {
			parameters.put("scope", scope);
		}
		return (MashapeResponse<JSONObject>) HttpClient.doRequest(JSONObject.class,
                    HttpMethod.POST,
                    "https://" + PUBLIC_DNS + "/oauth_url",
                    parameters,
                    ContentType.FORM,
                    ResponseType.JSON,
                    authenticationHandlers);
	}
	
	public StockTwits authorize(String accessToken) {
		if (authenticationHandlers != null) {
			for (Authentication authentication : authenticationHandlers) {
				if (authentication instanceof OAuthAuthentication) {
					((OAuthAuthentication) authentication).setAccessToken(accessToken);
					
				}
			}
		}
		return this;
	}
}