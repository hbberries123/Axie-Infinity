package axie;

import okhttp3.*;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;

public class AxieDataRequester {

    private static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    public static JSONObject getAxiesJSONObject(String jsonBody) throws IOException {
        OkHttpClient client = new OkHttpClient();

        URL website = new URL("https://axieinfinity.com/graphql-server-v2/graphql");

        RequestBody body = RequestBody.create(JSON, jsonBody);

        Request request = new Request.Builder()
                .url(website)
                .post(body)
                .build();

        String content ;

        JSONObject object ;

        try (Response response = client.newCall(request).execute()) {

            content = Objects.requireNonNull(response.body()).string();

            object = new JSONObject(content);

            response.body().close();

        }

        return object.getJSONObject("data");
    }

}
