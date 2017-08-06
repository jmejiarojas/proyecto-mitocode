package pe.cibertec.service;

import java.util.List;

public interface IService<T> {
	void registrar(T t) throws Exception;
	void modificar(T t) throws Exception;
	List<T> listar() throws Exception;
	T listarPorId(T t) throws Exception;
}
