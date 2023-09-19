package ru.ptrff;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DependencyFetcher {
    public static List<String> getDependencies(String packageName) {
        String url = "https://pypi.org/pypi/" + packageName + "/json";
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();

        try {
            Response response = client.newCall(request).execute();
            if (!response.isSuccessful()) {
                return new ArrayList<>();
            }

            String jsonResponse = response.body().string();
            response.close();
            JsonParser parser = new JsonParser();
            JsonObject json = parser.parse(jsonResponse).getAsJsonObject();

            if(json.getAsJsonObject("info").get("requires_dist").isJsonNull()){
                return new ArrayList<>();
            }

            JsonArray requirementsArray = json.getAsJsonObject("info").getAsJsonArray("requires_dist");

            if (requirementsArray == null) {
                return new ArrayList<>();
            }

            List<String> data = new ArrayList<>();
            for (int i = 0; i < requirementsArray.size(); i++) {
                String req = requirementsArray.get(i).getAsString();
                String[] reqParts = req.split(" ");
                if (reqParts.length > 0) {
                    data.add(reqParts[0]);
                }
            }

            return data;

        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}
