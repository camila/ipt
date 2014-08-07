package best.dog;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.jws.WebService;

@WebService(endpointInterface = "best.dog.Milhagem")
public class MilhagemImpl implements Milhagem {

	private static final String DEFAULT_DB = "jdbc:h2:tcp://localhost/~/test;USER=sa";
	
	private static final int MAX = 10;

	static {
		try {
			Class.forName("org.h2.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public String calculaMilhagem(String cpf) {
		int totalAcumulado = salvaMilhas(cpf);
		System.out.println(cpf + " tem " + (totalAcumulado == 0 ? MAX : totalAcumulado) + " milhas");
		switch (totalAcumulado) {
		case 0:
			// nessa implementação, total = 0 indica que o cliente chegou no limite e ganhará um lanche grátis
			return ";desconto: 100%;";
		case 5:
			return ";desconto: 50%;";
		default:
			return ";desconto: 0%;";
		}
	}

	private int salvaMilhas(String cpf) {
		int total = 1;
		try (Connection conn = DriverManager.getConnection(DEFAULT_DB)) {
			PreparedStatement statSelect = conn
					.prepareStatement("select total from IPT_MILHAGEM where cpf = ?");
			statSelect.setString(1, cpf);
			ResultSet rs = statSelect.executeQuery();
			while (rs.next()) {
				total += rs.getInt("total");
			}
			// reseta as milhas se chegar ao limite
			if (total >= MAX) {
				total = 0;
			}
			PreparedStatement statInsert = conn
					.prepareStatement("merge into IPT_MILHAGEM key(cpf) values(?, ?)");
			statInsert.setString(1, cpf);
			statInsert.setInt(2, total);
			statInsert.executeUpdate();
			// fecha conexoes
			statInsert.close();
			statSelect.close();
			rs.close();
		} catch (Exception e) {
			// nao calcula as milhas
			e.printStackTrace();
		}
		return total;
	}
	
}
