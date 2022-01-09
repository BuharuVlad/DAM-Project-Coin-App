package network;

import androidx.annotation.NonNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpManager {

    private URL url;

    private HttpURLConnection httpURLConnection;
    private InputStream inputStream;
    private InputStreamReader inputStreamReader;
    private BufferedReader bufferedReader;

    private final String urlAddress;


    public HttpManager(String urlAddress) {
        this.urlAddress = urlAddress;
    }

    public String process(){
        try {
            return getHttpConetentFromUrl();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeConnections();
        }
        return null;
    }

    @NonNull
    private String getHttpConetentFromUrl() throws IOException {
        url = new URL(urlAddress);
        httpURLConnection = (HttpURLConnection)url.openConnection();
        inputStream = httpURLConnection.getInputStream();
        inputStreamReader = new InputStreamReader(inputStream);
        bufferedReader = new BufferedReader(inputStreamReader);

        StringBuilder result = new StringBuilder();
        String line;
        while ((line = bufferedReader.readLine()) != null){
            result.append(line);
        }
        return result.toString();
    }

    private void closeConnections() {
        try {
            bufferedReader.close();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        try {
            inputStreamReader.close();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        try {
            inputStream.close();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        httpURLConnection.disconnect();
    }

    private void initComponents(){

    }
}
