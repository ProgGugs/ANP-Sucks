import io.javalin.Javalin;
import io.javalin.http.Handler;

import java.util.List;

public class FuncionarioController {

    private static final FuncionarioDAO pedidoService = new FuncionarioDAO();

    public static void registerRoutes(Javalin app) {
        app.get("/api/pedidos", listarPedidos);
        app.get("/api/pedidos/:id", buscarPorId);
        app.post("/api/pedidos", criarPedido);
        app.put("/api/pedidos/:id", atualizarPedido);
        app.delete("/api/pedidos/:id", deletarPedido);
    }

    // GET /api/pedidos
    public static Handler listarPedidos = ctx -> {
        List<Pedido> pedidos = pedidoService.listarTodos();
        ctx.json(pedidos);
    };

    // GET /api/pedidos/:id
    public static Handler buscarPorId = ctx -> {
        int id = Integer.parseInt(ctx.pathParam("id"));
        Pedido pedido = pedidoService.buscarPorId(id);
        if (pedido != null) {
            ctx.json(pedido);
        } else {
            ctx.status(404).result("Pedido não encontrado");
        }
    };

    // POST /api/pedidos
    public static Handler criarPedido = ctx -> {
        Pedido novoPedido = ctx.bodyAsClass(Pedido.class);
        pedidoService.criar(novoPedido);
        ctx.status(201).json(novoPedido);
    };

    // PUT /api/pedidos/:id
    public static Handler atualizarPedido = ctx -> {
        int id = Integer.parseInt(ctx.pathParam("id"));
        Pedido dadosAtualizados = ctx.bodyAsClass(Pedido.class);
        boolean atualizado = pedidoService.atualizar(id, dadosAtualizados);

        if (atualizado) {
            ctx.json(dadosAtualizados);
        } else {
            ctx.status(404).result("Pedido não encontrado");
        }
    };

    // DELETE /api/pedidos/:id
    public static Handler deletarPedido = ctx -> {
        int id = Integer.parseInt(ctx.pathParam("id"));
        boolean deletado = pedidoService.deletar(id);

        if (deletado) {
            ctx.status(204);
        } else {
            ctx.status(404).result("Pedido não encontrado");
        }
    };
}
