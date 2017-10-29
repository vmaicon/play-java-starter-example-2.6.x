package controllers;
import play.mvc.*;
import views.html.*;
import javax.inject.Inject;
import play.data.*;
import models.*;
import java.util.List;



public class HomeController extends Controller {
    
    @Inject
    FormFactory formFactory;
    @Inject
    Produto produto;


    public Result index() {
        return ok(index.render("alô mundo...."));
    }

    public Result cadastroDeProduto(){
        Form<Produto> formularioDeProduto = formFactory
        .form(Produto.class);
        return ok(cadastroDeProduto.render("Cadatro",formularioDeProduto));
    }

    public Result cadastroDeNovoProduto(){
        //pega as informações que vem do formulário
        Form<Produto> formProduto = formFactory.form(Produto.class).bindFromRequest();

        //valida se tem aglum erro
        if(formProduto.hasErrors()){
            flash("danger", "Tem erros no formulario");
            return badRequest(cadastroDeProduto.render("Cadastro", formProduto));
        }

        //atribui os dados ao objeto produto
        produto = formProduto.get() ;
        //salva o produto
        if(produto.id != null)
            produto.update();
        else
            produto.save();
        //notifica o usuário que o produto foi salvo
        flash("success", "Novo produto adicionado: "+produto.nome);

        //redireciona para a tela de cadastro novamente
        return redirect(routes.HomeController.listaTudo());
    }

    public Result editarProduto(Long id){
        Produto p = produto.find.byId(id);
        Form<Produto> formProduto = 
        formFactory.form(Produto.class).fill(p);
        
        return ok(cadastroDeProduto
        .render("Editar",formProduto));

    }

    public Result listaTudo(){
        List<Produto> list = produto.find.all();
        return ok(listProduto.render(list));
    }

}
