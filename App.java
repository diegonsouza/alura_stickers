import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.List;
import java.util.Map;
import java.io.InputStream;

import stickers.GeradoraDeFigurinhas;

public class App {
    public static void main(String[] args) throws Exception {

        // fazer uma conexão HTTP e buscar os top 250 filmes
        // String url = "https://imdb-api.com/en/API/Top250Movies/k_0ojt0yvm";
        String url = "https://raw.githubusercontent.com/alura-cursos/imersao-java/api/TopMovies.json";
        URI endereco = URI.create(url);
        var client = HttpClient.newHttpClient();
        var request = HttpRequest.newBuilder(endereco).GET().build();
        HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
        String body = response.body();

        // extrair só os dados que interessam (titulo, poster, classificação)
        var parser = new JsonParser();
        List<Map<String, String>> listaDeFilmes = parser.parse(body);

        // exibir e manipular os dados

        GeradoraDeFigurinhas geradoraDeFigurinhas = new GeradoraDeFigurinhas();

        for (Map<String, String> filme : listaDeFilmes) {

            String urlImagem = filme.get("image");
            String titulo = filme.get("title");

            InputStream inputStream = new URL(urlImagem).openStream();
            String nomeArquivo = "stickers/" + titulo + ".png";

            String dadosImagem = geradoraDeFigurinhas.cria(inputStream, nomeArquivo);

            System.out.println(titulo);
            System.out.println("Dados de tamanho da imagem: " + dadosImagem);
            System.out.println("Imagem: " + filme.get("image"));
            System.out.println("**********");
            System.out.println();

            // System.out.println(filme.get("title"));
            // System.out.println(filme.get("image"));
            // System.out.println(filme.get("imDbRating"));
            // System.out.println();
        }
    }
}
