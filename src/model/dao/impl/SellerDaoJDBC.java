package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import db.DB;
import db.DbException;
import model.dao.SellerDAO;
import model.entities.Department;
import model.entities.Seller;

public class SellerDaoJDBC implements SellerDAO{
	
	//a nossa classe, terá uma dependência com a conexão com o db
	private Connection conn; //esse objeto de conexão, estará disponível por toda minha classe
	
	//injeção de dependência para conexão com o db
	public SellerDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Seller obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Seller obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteById(Integer id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Seller findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					  "SELECT seller.*,department.Name as DepName " 
					+ "FROM seller INNER JOIN department "
					+ "ON seller.DepartmentId = department.Id "
					+ "WHERE seller.Id = ? "
					);
			st.setInt(1, id);
			rs = st.executeQuery();
			  /*
			  após a execução da Query, o 'rs' retorna um índice zero que
			  por sua vez não contém dado alugum. Então, terei que validar
			  o próximo índice (no caso é o um) do que foi retornado pro ResultSet, 
			  e se esse índice estiver vazio, quer dizer que não retornou nada.
			  */
			if(rs.next()) { //verifica o próximo índice para testar se veio algum resultado.
				//instanciaremos os objetos e os associaremos um ao outro pois o ResultSet retornou algo.
				Department dep = new Department(); //instanciação do objeto
				dep.setId(rs.getInt("DepartmentId")); //vai retornar o valor que foi obtido no ResultSet dessa coluna
				dep.setName(rs.getString("DepName"));
				
				Seller obj = new Seller();
				obj.setId(rs.getInt("Id"));
				obj.setName(rs.getString("Name"));
				obj.setEmail(rs.getString("Email"));
				obj.setBaseSalary(rs.getDouble("BaseSalary"));
				obj.setBirthDate(rs.getDate("BirthDate"));
				
				obj.setDepartment(dep); //objeto department associado 
				
				return obj;
			}
			return null; //caso não haja nenhum dado no ResultSet, a função retornará nulo, significando que o índice procurado, não foi encontrado
		}
		catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			DB.ResultSet(rs);
			//não se fecha a conexão nesse caso pois o mesmo objeto DAO pode efetuar várias operação com a mesma conexão
		}
	}

	@Override
	public List<Seller> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

}