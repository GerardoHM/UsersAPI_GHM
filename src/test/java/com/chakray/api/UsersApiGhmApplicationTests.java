package com.chakray.api;

import com.chakray.api.dto.UserRequestDTO;
import com.chakray.api.dto.UserResponseDTO;
import com.chakray.api.mapper.UserMapper;
import com.chakray.api.model.User;
import com.chakray.api.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

	@InjectMocks
	private UserService service;

	@Mock
	private UserMapper mapper;

	@Test
	void shouldCreateUser() {

		service.getUsers().clear();

		UserRequestDTO dto = new UserRequestDTO();
		dto.setTaxId("NEW990101XXX");
		dto.setPassword("123");

		User user = new User();
		user.setTaxId(dto.getTaxId());

		when(mapper.toEntity(any(UserRequestDTO.class))).thenReturn(user);

		UserResponseDTO response = service.create(dto);

		assertNotNull(response);
	}
}
