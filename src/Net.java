import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.BindException;
import java.net.HttpURLConnection;
import java.net.URL;

public class Net {

    private static String query = null; //Получаемое значение
    private static int param = 0; //Параметр работы: 0 - первый круг, 1 - второй круг
    private static String answer = null; //Ответ


    public void Net() {

        HttpURLConnection connection = null;

        //Получение названия итема и преоброзхование в строчку URL запроса
        query = query.replaceAll("\\s", "%20");

        //Получение ответа с запроса на поиск
        try {
            if (query != null) {
                if (param == 0) {
                    connection = (HttpURLConnection) new URL("https://catalog.api.onliner.by/search/products?query="
                            + query + " ").openConnection();
                } else {
                    connection = (HttpURLConnection) new URL(query).openConnection();
                }
            }else {
                System.out.println("Нет параметров входа URL");
                return;
            }
            connection.addRequestProperty("User-Agent","Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:56.0) Gecko/20100101 Firefox/56.0");
            /*
            connection.setRequestMethod("GET");
            connection.setUseCaches(false);
            connection.setConnectTimeout(250);
            connection.setReadTimeout(250);
            */

            try {
                connection.connect();
            } catch (BindException e) {
                System.out.println(e);
                System.out.println("No connect!!");
                Thread.sleep(1000);
                connection.connect();
            }

            // На случай DDOS
            Thread.sleep(500);

            StringBuilder sb = new StringBuilder();

            if (HttpURLConnection.HTTP_OK == connection.getResponseCode()) {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                while ((line = in.readLine()) != null) {
                    sb.append(line);
                    sb.append("\n");
                }

                answer = sb.toString();

            } else {
                System.out.println("Failed: " + connection.getResponseCode() + ", " + connection.getResponseMessage());
            }

        } catch (Throwable cause) {
            cause.printStackTrace();
            System.out.println("Error Net/!!!!!!!!!!!!!!!!!!!!!!");
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public void setParam(int param) {
        this.param = param;
    }

    public String getAnswer() {
        return answer;
    }
}