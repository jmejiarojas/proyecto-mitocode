package pe.cibertec.service;

import pe.cibertec.models.Config;

public interface IConfigService extends IService<Config>{
	Config leerParametro(String parametro) throws Exception;
}
