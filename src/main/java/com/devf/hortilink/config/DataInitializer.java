package com.devf.hortilink.config;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.devf.hortilink.entity.ComercioProfile;
import com.devf.hortilink.entity.ItemPedido;
import com.devf.hortilink.entity.Oferta;
import com.devf.hortilink.entity.Pedido;
import com.devf.hortilink.entity.Produto;
import com.devf.hortilink.entity.Usuario;
import com.devf.hortilink.enums.Categoria;
import com.devf.hortilink.enums.Role;
import com.devf.hortilink.enums.StatusPedido;
import com.devf.hortilink.enums.UnidadeMedida;
import com.devf.hortilink.repository.ComercioProfileRepository;
import com.devf.hortilink.repository.OfertaRepository;
import com.devf.hortilink.repository.PedidoRepository;
import com.devf.hortilink.repository.ProdutoRepository;
import com.devf.hortilink.repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UsuarioRepository usuarioRepository;
    private final ComercioProfileRepository comercioRepository;
    private final PedidoRepository pedidoRepository;
    private final ProdutoRepository produtoRepository; // Adicione este
    private final OfertaRepository ofertaRepository;   // Adicione este
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        
    	// 1. CRIAR PRODUTOS BÁSICOS
    	Produto p1 = new Produto();
    	p1.setNome("Alface Crespa");
    	p1.setDescricao("Alface fresquinha colhida hoje");
    	p1.setDataColheita(LocalDate.now());
    	p1.setCertificadoOrganico(true);
    	p1.setCategoria(Categoria.LEGUME); // Ajuste conforme seu Enum
    	p1.setUnidadeMedida(UnidadeMedida.UNIDADE); // Ajuste conforme seu Enum
    	p1.setFotos(new ArrayList<>());
    	produtoRepository.save(p1);

    	Produto p2 = new Produto();
    	p2.setNome("Tomate Italiano");
    	p2.setDescricao("Tomate ideal para molhos");
    	p2.setDataColheita(LocalDate.now().minusDays(2));
    	p2.setCertificadoOrganico(false);
    	p2.setCategoria(Categoria.LEGUME);
    	p2.setUnidadeMedida(UnidadeMedida.KILOGRAMA);
    	p2.setFotos(new ArrayList<>());
    	produtoRepository.save(p2);

    	Produto p3 = new Produto();
    	p3.setNome("Maçã Gala");
    	p3.setDescricao("Maçã doce e crocante");
    	p3.setDataColheita(LocalDate.now().minusDays(5));
    	p3.setCertificadoOrganico(true);
    	p3.setCategoria(Categoria.FRUTA);
    	p3.setUnidadeMedida(UnidadeMedida.KILOGRAMA);
    	p3.setFotos(new ArrayList<>());
    	produtoRepository.save(p3);

        // 2. CRIAR COMÉRCIO
        ComercioProfile hortifruti = new ComercioProfile();
        hortifruti.setNomeComercio("Hortifruti do Edson");
        hortifruti.setRaioMaximoEntregaKm(10);
        hortifruti.setOfertas(new ArrayList<>());
        hortifruti.setUsers(new ArrayList<>());
        comercioRepository.save(hortifruti);

        // 3. CRIAR OFERTAS PARA O COMÉRCIO
        Oferta o1 = ofertaRepository.save(new Oferta(null, hortifruti, p1, new BigDecimal("4.50"), BigDecimal.ZERO, new BigDecimal("100"), true));
        Oferta o2 = ofertaRepository.save(new Oferta(null, hortifruti, p2, new BigDecimal("8.90"), BigDecimal.ZERO, new BigDecimal("50"), true));
        Oferta o3 = ofertaRepository.save(new Oferta(null, hortifruti, p3, new BigDecimal("12.00"), BigDecimal.ZERO, new BigDecimal("30"), true));
        
        // Atualiza a lista de ofertas no comércio (boa prática para consistência)
        hortifruti.getOfertas().addAll(Arrays.asList(o1, o2, o3));

        // 4. CRIAR USUÁRIOS (Vendedor e Cliente)
        Usuario vendedor = new Usuario();
        vendedor.setNome("Edson Vendedor");
        vendedor.setEmail("vendedor@teste.com");
        vendedor.setSenha(passwordEncoder.encode("123456"));
        vendedor.setRole(Role.PRODUTOR);
        vendedor.setComercioProfile(hortifruti);
        usuarioRepository.save(vendedor);

        Usuario cliente = new Usuario();
        cliente.setNome("João Cliente");
        cliente.setEmail("cliente@teste.com");
        cliente.setSenha(passwordEncoder.encode("123456"));
        cliente.setRole(Role.CONSUMIDOR);
        usuarioRepository.save(cliente);

        // 5. MOCKANDO PEDIDOS COM ITENS REAIS
        // Passamos a lista de ofertas para que os pedidos tenham produtos variados
        criarPedidosFicticios(cliente, hortifruti, Arrays.asList(o1, o2, o3));
        
        System.out.println("✅ Mock de dados completo: Produtos, Ofertas e Pedidos criados!");
    }

    private void criarPedidosFicticios(Usuario cliente, ComercioProfile vendedor, List<Oferta> ofertasDisponiveis) {
        List<Pedido> pedidos = new ArrayList<>();
        LocalDateTime hoje = LocalDateTime.now();

        for (int i = 0; i < 15; i++) {
            Pedido p = new Pedido();
            p.setCliente(cliente);
            p.setVendedor(vendedor);
            p.setDataPedido(hoje.minusDays(i % 7).minusHours(i)); 
            p.setStatus(i % 5 == 0 ? StatusPedido.PENDENTE : StatusPedido.ENTREGUE);
            
            // Escolhe uma oferta da lista baseada no índice i
            Oferta ofertaEscolhida = ofertasDisponiveis.get(i % ofertasDisponiveis.size());
            
            ItemPedido item = new ItemPedido();
            item.setPedido(p);
            item.setOferta(ofertaEscolhida);
            item.setQuantidade(1 + (i % 3)); // Quantidades entre 1 e 3
            item.setPrecoUnitario(ofertaEscolhida.getValor());
            item.setSubtotal(item.getPrecoUnitario().multiply(BigDecimal.valueOf(item.getQuantidade())));
            
            p.setValorTotal(item.getSubtotal());
            p.setItens(Arrays.asList(item));
            
            pedidos.add(p);
        }

        pedidoRepository.saveAll(pedidos);
    }
}
