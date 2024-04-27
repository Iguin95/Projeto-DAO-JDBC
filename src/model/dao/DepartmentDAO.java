package model.dao;

import java.util.List;

import model.entities.Department;

public interface DepartmentDAO {
	
	void insert(Department obj); //inserir um departamento no db
	void update(Department obj);//atualizar um departamento no db
	void deleteById(Integer id);//deletar através de um id, um departamento no db
	Department findById(Integer id);//achar um departamento no db, através de um id
	List<Department> findAll();//retornar todos os departamentos do db
	

}
