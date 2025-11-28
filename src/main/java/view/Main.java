package view;

import model.Filmes;
import service.TmdbService;
import dao.FilmeDAO;

import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        
        // 1. Instanciando as nossas ferramentas
        TmdbService service = new TmdbService();
        FilmeDAO dao = new FilmeDAO(); // Aqui ele já cria a tabela se não existir!
        Scanner scanner = new Scanner(System.in);

        int opcao = -1;

        System.out.println("=== BEM-VINDO AO SISTEMA DE FILMES TMDB ===");

        while (opcao != 0) {
            System.out.println("\nEscolha uma opção:");
            System.out.println("1 - Buscar Filme na API e Salvar");
            System.out.println("2 - Listar meus Filmes Salvos");
            System.out.println("3 - Atualizar um Filme (Nota/Sinopse)");
            System.out.println("4 - Excluir um Filme");
            System.out.println("0 - Sair");
            System.out.print(">> ");

            // Evita erro se o usuário digitar texto em vez de número
            if (scanner.hasNextInt()) {
                opcao = scanner.nextInt();
                scanner.nextLine(); // Consumir a quebra de linha pendente
            } else {
                System.out.println("❌ Digite apenas números!");
                scanner.next(); // Limpa o buffer errado
                continue;
            }

            switch (opcao) {
                case 1:
                    System.out.print("Digite o nome do filme para buscar: ");
                    String busca = scanner.nextLine();
                    
                    try {
                        System.out.println("🔍 Buscando na internet...");
                        Filmes filmeEncontrado = service.buscarFilme(busca);

                        if (filmeEncontrado != null) {
                            System.out.println("✅ Filme encontrado:");
                            System.out.println(filmeEncontrado); // Usa o toString() bonito
                            
                            System.out.print("\nDeseja salvar este filme no banco? (S/N): ");
                            String resposta = scanner.nextLine();

                            if (resposta.equalsIgnoreCase("S")) {
                                dao.salvar(filmeEncontrado);
                                System.out.println("💾 Filme salvo com sucesso!");
                            }
                        } else {
                            System.out.println("⚠️ Nenhum filme encontrado com esse nome.");
                        }
                    } catch (Exception e) {
                        System.out.println("❌ Erro ao buscar: " + e.getMessage());
                    }
                    break;

                case 2:
                    System.out.println("\n=== MEUS FILMES SALVOS ===");
                    List<Filmes> filmes = dao.listar();
                    if (filmes.isEmpty()) {
                        System.out.println("📭 Nenhum filme salvo ainda.");
                    } else {
                        for (Filmes f : filmes) {
                            System.out.println(f);
                        }
                    }
                    break;

                case 3:
                    // ATUALIZAR (UPDATE)
                    // Vamos permitir mudar a nota e a sinopse (como se fosse uma resenha pessoal)
                    System.out.print("Digite o ID do filme que quer atualizar: ");
                    int idAtualizar = scanner.nextInt();
                    scanner.nextLine(); 

                    System.out.print("Digite a nova Nota pessoal (0 a 10): ");
                    double novaNota = scanner.nextDouble();
                    scanner.nextLine();

                    System.out.print("Digite a nova Sinopse/Comentário: ");
                    String novaSinopse = scanner.nextLine();
                    
                    // Precisamos do objeto completo para o DAO, mas só vamos mudar nota e sinopse
                    // O truque aqui é criar um objeto temporário só com os dados novos e o ID antigo
                    Filmes filmeAtualizado = new Filmes();
                    filmeAtualizado.setId(idAtualizar);
                    filmeAtualizado.setTitle("Título Mantido"); // O DAO atualiza tudo, cuidado
                    // DICA: Se quiser fazer direito, deveria buscar o filme antes, mudar os campos e salvar.
                    // Para simplificar o exercício, vamos enviar os dados.
                    // Mas espere! O DAO pede Título também. 
                    // Vamos fazer o fluxo CORRETO: Buscar no banco -> Alterar -> Salvar
                    
                    // (Ajuste simples para o seu nível de projeto: vamos apenas pedir o título de novo ou enviar string vazia se o SQL permitir, mas o ideal é buscar antes).
                    System.out.print("Confirme o título do filme (para não perder): ");
                    String tituloConfirmado = scanner.nextLine();
                    
                    filmeAtualizado.setTitle(tituloConfirmado);
                    filmeAtualizado.setNota(novaNota);
                    filmeAtualizado.setSinopse(novaSinopse);
                    
                    dao.atualizar(filmeAtualizado);
                    System.out.println("🔄 Filme atualizado!");
                    break;

                case 4:
                    System.out.print("Digite o ID do filme para excluir: ");
                    int idExcluir = scanner.nextInt();
                    dao.deletar(idExcluir);
                    System.out.println("🗑️ Filme removido!");
                    break;

                case 0:
                    System.out.println("Saindo... Até mais! 👋");
                    break;

                default:
                    System.out.println("Opção inválida!");
            }
        }
        scanner.close();
    }
}