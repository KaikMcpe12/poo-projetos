import src.controller.LojaController;
import src.repository.LojaRepository;
import src.repository.ProdutoRepository;
import src.repository.VendaRepository;
import src.service.LojaService;
import src.service.PagamentoService;
import src.service.ProdutoService;
import src.service.VendaService;
import src.view.LojaView;

public class Main {

    public static void main(String[] args) {
        LojaRepository lojaRepository = new LojaRepository();
        ProdutoRepository produtoRepository = new ProdutoRepository();
        VendaRepository vendaRepository = new VendaRepository();

        ProdutoService produtoService = new ProdutoService(produtoRepository);
        VendaService vendaService = new VendaService(
            vendaRepository
        );
        PagamentoService pagamentoService = new PagamentoService();
        LojaService lojaService = new LojaService(
            lojaRepository,
            produtoService,
            vendaService,
            pagamentoService
        );

        LojaView view = new LojaView();

        LojaController controller = new LojaController(
            lojaService,
            view
        );
        controller.iniciar();
    }
}
