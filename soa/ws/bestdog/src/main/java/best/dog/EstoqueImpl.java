package best.dog;

<<<<<<< HEAD
=======
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.concurrent.atomic.AtomicInteger;

>>>>>>> d44bfd6... ipt tasks source code
import javax.jws.WebService;

@WebService(endpointInterface = "best.dog.Estoque")
public class EstoqueImpl implements Estoque {
<<<<<<< HEAD
=======
	
	private static final String DEFAULT_DB = "jdbc:h2:tcp://localhost/~/test;USER=sa";
	
	private static final AtomicInteger TOTAL = new AtomicInteger(10);
	
	static {
		try {
			Class.forName("org.h2.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
>>>>>>> d44bfd6... ipt tasks source code

	@Override
	public String calculaEstoque(String codItem) {
		if (buscaItem(codItem)) {
<<<<<<< HEAD
			String result = codItem + " disponivel";
			System.out.println(result);
			return result;
=======
			int value = TOTAL.get() > 0 ? TOTAL.decrementAndGet() : 0;
			System.out.println(codItem + ": " + value + " -> " + (value > 0 ? "disponivel" : "indisponivel"));
			salvaEstoque(codItem, value);
			return String.valueOf(value);
>>>>>>> d44bfd6... ipt tasks source code
		}
		// estimar melhor o estoque da proxima vez
		throw new UnsupportedOperationException(codItem + "indisponivel!");
	}
<<<<<<< HEAD
=======
	
	@Override
	public String consultaEstoque(String codItem) {
		System.out.println("consultando " + codItem);
		int consulta = consulta(codItem);
		if (consulta <= 5) {
			System.out.println("ALERTA: comprar itens de codigo " + codItem);
		}
		if (consulta == 0) {
			System.out.println("ALERTA [CRITICO]: itens de codigo " + codItem + " esgotados.");
		}
		return String.valueOf(consulta);
	}
	
	private int consulta(String codItem) {
		int total = -1; // item inexsistente no banco
		try (Connection conn = DriverManager.getConnection(DEFAULT_DB)) {
			PreparedStatement statSelect = conn
					.prepareStatement("select total from IPT_ESTOQUE where cod = ?");
			statSelect.setString(1, codItem);
			ResultSet rs = statSelect.executeQuery();
			while (rs.next()) {
				total = rs.getInt("total");
			}
			// fecha conexoes
			statSelect.close();
			rs.close();
		} catch (Exception e) {
			// nao calcula as milhas
			e.printStackTrace();
		}
		return total;
	}
	
	private int salvaEstoque(String codEstoque, int total) {
		try (Connection conn = DriverManager.getConnection(DEFAULT_DB)) {
			PreparedStatement statInsert = conn
					.prepareStatement("merge into IPT_ESTOQUE key(cod) values(?, ?)");
			statInsert.setString(1, codEstoque);
			statInsert.setInt(2, total);
			statInsert.executeUpdate();
			// fecha conexoes
			statInsert.close();
		} catch (Exception e) {
			// nao atualiza o estoque
			e.printStackTrace();
		}
		return total;
	}
>>>>>>> d44bfd6... ipt tasks source code

	private boolean buscaItem(String codItem) {
		// busca no BD de estoques
		return codItem != null && !codItem.isEmpty();
	}

}
