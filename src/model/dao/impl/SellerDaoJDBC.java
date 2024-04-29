package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import db.DB;
import db.DbException;
import model.dao.SellerDAO;
import model.entities.Department;
import model.entities.Seller;

public class SellerDaoJDBC implements SellerDAO{
	
	//A nossa classe, terá uma dependência com a conexão com o db
	private Connection conn; //Esse objeto de conexão, estará disponível por toda minha classe
	
	//Injeção de dependência para conexão com o db
	public SellerDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Seller obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"INSERT INTO seller "
					+ "(Name, Email, BirthDate, BaseSalary, DepartmentId) " 
					+ "VALUES " 
					+"(?, ?, ?, ?, ?)",//PlaceHolders.
					Statement.RETURN_GENERATED_KEYS //Retorna o id do novo objeto inserido.
					);
			st.setString(1, obj.getName());
			st.setString(2, obj.getEmail());
			st.setDate(3, new java.sql.Date(obj.getBirthDate().getTime()));
			st.setDouble(4, obj.getBaseSalary());
			st.setInt(5, obj.getDepartment().getId()); //Associação de classes.
			
			int rowsAffected = st.executeUpdate();
			
			if(rowsAffected > 0) {
				ResultSet rs = st.getGeneratedKeys();
				
				//O próximo bloco é um 'if' pois será inserido somente um objeto novo.
				if(rs.next()) {
					int id = rs.getInt(1); //Pegando o 'id' gerado.
					obj.setId(id); /*Inserindo no novo objeto Seller passado como argumento
					neste método o id capturado.*/
				}
				DB.ResultSet(rs); /*Foi criado dentro do escopo do 'if', então tenho que 
				fecha-lo dentro do mesmo escopo, pois fora, ele não existe.*/
				
			} //Caso na inserção, nenhuma linha for afetada, será lançada uma exceção.
			else {
				throw new DbException("Unexpected error! No rows affected!");
			}
			
		}
		catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
		}
		
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
				
				Department dep = instantiateDepartment(rs);
				
				Seller obj = instantiateSeller(rs, dep);
				
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

	private Department instantiateDepartment(ResultSet rs) throws SQLException {
		Department dep = new Department(); 
		dep.setId(rs.getInt("DepartmentId")); 
		dep.setName(rs.getString("DepName"));
		return dep;
	}

	private Seller instantiateSeller(ResultSet rs, Department dep) throws SQLException {
		Seller obj = new Seller();
		obj.setId(rs.getInt("Id"));
		obj.setName(rs.getString("Name"));
		obj.setEmail(rs.getString("Email"));
		obj.setBaseSalary(rs.getDouble("BaseSalary"));
		obj.setBirthDate(rs.getDate("BirthDate"));
		obj.setDepartment(dep);
		return obj;
	}

	@Override
	public List<Seller> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"SELECT seller.*,department.Name as DepName " 
					+ "FROM seller INNER JOIN department " 
					+ "ON seller.DepartmentId = department.Id "
					+ "ORDER BY Name "
					);
			
			rs = st.executeQuery();
			
			List<Seller> list = new ArrayList<Seller>();
			Map<Integer, Department> map = new HashMap<>();
			
			while(rs.next()) {
				Department dep = map.get(rs.getInt("DepartmentId"));
				if(dep == null) {
					dep = instantiateDepartment(rs);
					map.put(rs.getInt("DepartmentId"), dep);
				}
				Seller obj = instantiateSeller(rs, dep);
				
				list.add(obj);
			}
			return list; 
		}
		catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			DB.ResultSet(rs);
		}
	}

	@Override
	public List<Seller> findByDepartment(Department department) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"SELECT seller.*,department.Name as DepName " 
					+ "FROM seller INNER JOIN department " 
					+ "ON seller.DepartmentId = department.Id "
					+ "WHERE DepartmentId = ? "
					+ "ORDER BY Name "
					);
			
			st.setInt(1, department.getId());
			rs = st.executeQuery();
			
			List<Seller> list = new ArrayList<Seller>();
			
			//O map será responsável por ligar o nome do departamento com o seu número de identificação.
			Map<Integer, Department> map = new HashMap<>();
			
			while(rs.next()) { //Enquanto tiver um próximo irá percorrer
				
				/*Variável 'dep' irá receber o nome do departamento, pois a chave é o id
				 * e o valor é o nome do departamento.*/
				Department dep = map.get(rs.getInt("DepartmentId"));
				
				/*Caso não haja departamento existente, entrará neste 'if' abaixo
				 * e instanciará um novo departamento, adicionando na estrutura
				 * de dados map, o id do departamento mais o nome do departamento que
				 * foi obtido acima.*/
				if(dep == null) {
					dep = instantiateDepartment(rs);
					map.put(rs.getInt("DepartmentId"), dep);
				}
				
				/*Caso o 'if' falha, quer dizer que o departamento já existe e ele será
				 * reaproveitado, o que faz com que ele não instanciado a cada objeto
				 * vendedor criado. Um único departamento, com o mesmo id será utilizado
				 * para todos os vendedores.*/
				Seller obj = instantiateSeller(rs, dep);
				
				list.add(obj);
			}
			return list; 
		}
		catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			DB.ResultSet(rs);
		}
	}

}
