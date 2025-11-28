package service;

import com.google.gson.Gson;
import model.Filmes;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class TmdbService extends Filmes {

    
    private static final String API_KEY = "e02b92fbe3daef8b8ec276cbd3c34d95"; 

    public Filmes buscarFilme(String title) {
        // 1. URL Base da busca
        String endereco = "https://api.themoviedb.org/3/search/movie";

        try {
            // 2. Codificando o nome (ex: "Velozes e Furiosos" vira "Velozes+e+Furiosos")
            // Isso evita erro se o usuário digitar espaços ou acentos
            String nomeCodificado = URLEncoder.encode(title, StandardCharsets.UTF_8);
            
            // Montando a URL completa com a chave e a query
            String urlCompleta = endereco + "?api_key=" + API_KEY + "&query=" + nomeCodificado + "&language=pt-BR";

            // 3. Criando o cliente HTTP e a Requisição (GET)
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(urlCompleta))
                    .GET()
                    .build();

            // 4. Enviando e recebendo a resposta (JSON em texto)
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Se der erro 404 ou 401, lançamos exceção
            if (response.statusCode() != 200) {
                throw new RuntimeException("Erro na API: " + response.statusCode());
            }

            // 5. Convertendo o JSON para Objetos Java
            Gson gson = new Gson();
            TmdbResponse resposta = gson.fromJson(response.body(), TmdbResponse.class);

            // Vamos retornar apenas o PRIMEIRO filme encontrado (o mais relevante)
            // Se a lista não estiver vazia
            if (resposta.getResults() != null && !resposta.getResults().isEmpty()) {
                return resposta.getResults().get(0);
            }

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Não consegui buscar o filme: " + e.getMessage());
        }

        return null; // Retorna nulo se não achar nada
    }
}

// --- Classe Auxiliar (Wrapper) ---
// O TMDB devolve um objeto com uma lista dentro chamada "results".
// Precisamos dessa classe só para o Gson conseguir ler essa lista.
class TmdbResponse {
    private List<Filmes> results;

    public List<Filmes> getResults() {
        return results;
    }
    public void setResults(List<Filmes> results) {
        this.results = results;
    }
}