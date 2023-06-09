
package com.web.dssapp.service;

import com.web.dssapp.dto.UserDto;
import com.web.dssapp.model.User;
import com.web.dssapp.repository.RoleRepository;
import com.web.dssapp.repository.UserRepository;

import org.springframework.aop.AopInvocationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	private RoleRepository roleRepository;
	private PasswordEncoder passwordEncoder;

	public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository,
			PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.passwordEncoder = passwordEncoder;
	}


	@Override
	public void saveUser(UserDto user) {
		User newUser = new User();
		newUser.setFirstName(user.getFirstName());
		newUser.setLastName(user.getLastName());
		newUser.setEmail(user.getEmail());
		newUser.setUsername(user.getUsername());
		try{
				newUser.set_id(maxid()+1);
		}catch(AopInvocationException e){
			newUser.set_id(1);
        }

		//newUser.set_id(maxid()+1);
		// encrypt the password once we integrate spring security
		newUser.setPassword(passwordEncoder.encode(user.getPassword()));

		newUser.setRole_id(user.getRole_id());

		/*
		 * Role role = roleRepository.findRoleByName("ROLE_ADMIN"); if (role == null) {
		 * role = checkRoleExist(); } newUser.setRoles(Arrays.asList(role));
		 */

		userRepository.save(newUser);
	}

	@Override
	public User findByEmail(String email) {
		return userRepository.findUserByEmail(email);
	}


	@Override
	public Boolean updateUser(User user, UserDto userDto) {
		try {
			user.set_id(userDto.get_id());
			user.setFirstName(userDto.getFirstName());
			user.setLastName(userDto.getLastName());
			user.setUsername(userDto.getUsername());
			user.setEmail(userDto.getEmail());
			user.setPassword(userDto.getPassword());
			user.setRole_id(userDto.getRole_id());
			userRepository.save(user);
			return true;
		} catch (Exception e) {
			return false;
		}

	}

	@Override
	public Optional<User> findUserById (int id) {
		return userRepository.findById(id);

	}

	@Override
	public String deleteUserById(int id) {
		try {
			userRepository.deleteById(id);
			return "User deleted successfully";
		} catch (Exception e) {
			return e.getMessage();
		}
	}



	@Override
	public int maxid() {
		return userRepository.maxid();
	}

	@Override
	public User findByusername(String username) {
		return userRepository.findUserByusername(username);
	}


	@Override
	public Page<User> findAllUsersP(int pageNumber, int pageSize, Sort sort) {
		Pageable page =  PageRequest.of(pageNumber-1, pageSize, sort);
		return userRepository.findAll(page);
	}


	@Override
	public List<User> findAllUsers() {
		return userRepository.findAll();
	}

}
