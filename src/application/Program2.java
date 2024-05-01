package application;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import model.dao.DaoFactory;
import model.dao.DepartmentDAO;
import model.entities.Department;

public class Program2 {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		DepartmentDAO depDao = DaoFactory.createDepartmentDao();
		
		System.out.println("\n< ============ TEST 1: Department Insert ============ > ");
		Department newDep = new Department(null, "Logística");
		depDao.insert(newDep);
		System.out.println("Departamento inserido! Novo id = " + newDep.getId());
		
		System.out.println("\n< ============ TEST 2: Department FindById ============ > ");
		Department dep = depDao.findById(3);
		System.out.println(dep);
		
		System.out.println("\n< ============ TEST 3: Department FindAll ============ > ");
		List<Department> list = new ArrayList<Department>();
		list = depDao.findAll();
		for(Department d : list) {
			System.out.println(d);
		}
		
		System.out.println("\n< ============ TEST 4: Department Delete ============ > ");
		System.out.print("Entre com o id para deletar: ");
		int id = sc.nextInt();
		depDao.deleteById(id);
		System.out.println("Id " + id + " Deletado!");
		
		System.out.println("\n< ============ TEST 5: Department Update ============ > ");
		dep = depDao.findById(6);
		dep.setName("Estamparia");
		depDao.update(dep);
		System.out.println("Atualização completada!");
		
		
		sc.close();
	}

}
