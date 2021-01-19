package br.com.desafiodevsuperior.service;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.desafiodevsuperior.dto.ClientDTO;
import br.com.desafiodevsuperior.entities.Client;
import br.com.desafiodevsuperior.repository.ClientRepository;
import br.com.desafiodevsuperior.service.exceptions.DatabaseException;
import br.com.desafiodevsuperior.service.exceptions.ResourceNotFoundException;

@Service
public class ClientService {

	@Autowired
	private ClientRepository repository;
	
	

	@Transactional
	public ClientDTO insert(ClientDTO dto) {

		Client entity = new Client();

		copyDtoToEntity(dto, entity);

		entity = repository.save(entity);

		return new ClientDTO(entity);
	}
	
	

	private void copyDtoToEntity(ClientDTO dto, Client entity) {

		entity.setName(dto.getName());
		entity.setCpf(dto.getCpf());
		entity.setIncome(dto.getIncome());
		entity.setBirthDate(dto.getBirthDate());
		entity.setChildren(dto.getChildren());

	}
	
	

	@Transactional
	public ClientDTO findById(Long id) {

		Optional<Client> obj = repository.findById(id);
		Client entity = obj.orElseThrow( () -> new ResourceNotFoundException("Entity not found"));

		return new ClientDTO(entity);

	}
	
	
	

	/**
	 * No metodo de Delete tratar sempre as excessões.
	 * 
	 * No metodo delete não e necessario colocar o @Transactional porque sera capturada uma excessão do BD caso ela ocorra, 
	 * e com o @Transactional não e possivel capturar essa excessão.
	 * @param id
	 */
	public void delete(Long id) {
		
		try {
			repository.deleteById(id);
			
		} catch (EmptyResultDataAccessException e) { // EmptyResultDataAccessException -> Excessão caso tente deletar um id que não existe
			throw new ResourceNotFoundException("Id not found");
			
		} catch (DataIntegrityViolationException e) { // DataIntegrityViolationException -> Excessão caso tente deletar uma categoria que contenha produtos
			throw new DatabaseException("Integrity violation");
		}
		
	}
	
	
	
	/**
	 * getOne() -> Metodo necessario sempre que houver uma atualização, ele não acessa o BD
	 * @param id
	 * @param dto
	 * @return
	 */
	@Transactional
	public ClientDTO update(Long id, ClientDTO dto) {
		
		try {
			Client entity = repository.getOne(id); // getOne(id) -> Metodo que não utiliza o BD, ele instancia um obj provisorio do Category com o Id informado na memoria
			
			copyDtoToEntity(dto, entity);
			
			entity = repository.save(entity); // Salvando no BD
			
			return new ClientDTO(entity);
			
		} catch (EntityNotFoundException e) { // EntityNotFoundException -> excessão para entidade que não existe
			throw new ResourceNotFoundException("Id not found exception" + id);
		}
	}
	
	
	
	@Transactional(readOnly = true)
	public Page<ClientDTO> findAllPaged(PageRequest pageRequest){
		
		Page<Client> list = repository.findAll(pageRequest);
		
		// O Page ja e um stream do Java 8 então não é necessario stream() e nem o collect(Collectors.toList())
		
		return list.map(x -> new ClientDTO(x));
		
	}
	

}
