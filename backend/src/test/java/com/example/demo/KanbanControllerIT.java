package com.example.demo;

import com.example.demo.Task.Status;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

import com.example.demo.Tag.Tag;
import com.example.demo.Task.Task;
import com.example.demo.Task.MockTag;
import com.example.demo.User.LoginData;
import com.example.demo.User.LoginResponse;
import com.example.demo.User.MyUser;
import org.springframework.http.*;
import java.util.*;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class KanbanControllerIT {

	MockTag mockTag = new MockTag("testid123123","Programmieren", "#5522ff");
    MockTag mockTag1 = new MockTag("idtest12234","Lernen", "#5f68ea");


	Task task1 = new Task("Java Lernen", "Immer fleißig tests schreiben!", Status.OPEN, List.of(mockTag1));
	Task task2 = new Task("Backend Programmieren", "bla bla bla", Status.IN_PROGRESS, List.of(mockTag));

	@Autowired
	TestRestTemplate restTemplate = new TestRestTemplate();

	@Test
	void kanbanIntegrationTest() {

		var newUser = new MyUser();
		newUser.setUsername("testUser");
		newUser.setPassword("testPassword");
		ResponseEntity<Void> createUserResponse = restTemplate.postForEntity("/api/user", newUser, Void.class);
		Assertions.assertThat(createUserResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

		// failed login
		ResponseEntity<LoginResponse> failedLoginResponse = restTemplate.postForEntity("/api/login", new LoginData("klausi", "manta"), LoginResponse.class);
		Assertions.assertThat(failedLoginResponse.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);

		//
		ResponseEntity<LoginResponse> loginResponse = restTemplate.postForEntity("/api/login", new LoginData("testUser", "testPassword"), LoginResponse.class);
		Assertions.assertThat(loginResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
		String jwt = loginResponse.getBody().getToken();

		Assertions.assertThat(loginResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
		Assertions.assertThat(jwt).isNotBlank();

		ResponseEntity<String> getResponse = restTemplate.exchange(
                "/api/user/me",
                HttpMethod.GET,
                new HttpEntity<>(createHeaders(jwt)),
                String.class
        );

		Assertions.assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
		Assertions.assertThat(getResponse.getBody()).isEqualTo("testUser");

		//Check for empty List
		ResponseEntity<Task[]> emptyArray = restTemplate.exchange("/api/kanban",
				HttpMethod.GET,
				new HttpEntity<>(createHeaders(jwt)),
				Task[].class
		);

		Assertions.assertThat(emptyArray.getStatusCode()).isEqualTo(HttpStatus.OK);
		Assertions.assertThat(emptyArray.getBody().length).isEqualTo(0);

		// Check adding Tasks
		ResponseEntity<Task> resTask = restTemplate.exchange(
			"/api/kanban",
			HttpMethod.POST,
			new HttpEntity<Object>(task1, createHeaders(jwt)),
			Task.class
		);

		ResponseEntity<Task> resTask1 = restTemplate.exchange(
			"/api/kanban",
			HttpMethod.POST,
			new HttpEntity<Object>(task2, createHeaders(jwt)),
			Task.class

		);

		ResponseEntity<Task[]> ArraywithTask = restTemplate.exchange(
			"/api/kanban",
			HttpMethod.GET,
			new HttpEntity<>(createHeaders(jwt)),
			Task[].class
		);

		Assertions.assertThat(ArraywithTask.getBody().length).isEqualTo(2);

		task1.setId(resTask.getBody().getId());
		task2.setId(resTask1.getBody().getId());
		task1.setCreatedById(resTask.getBody().getCreatedById());
		task2.setCreatedById(resTask1.getBody().getCreatedById());
		task1.setTags(resTask.getBody().getTags());
		task2.setTags(resTask1.getBody().getTags());

		Assertions.assertThat(ArraywithTask.getBody()).contains(task1, task2);
	
		//Check get Task By ID
		ResponseEntity<Task> task = restTemplate.exchange(
			"/api/kanban/" + task1.getId(),
			HttpMethod.GET,
			new HttpEntity<>(createHeaders(jwt)),
			Task.class
		);
		Assertions.assertThat(task.getBody()).isEqualTo(task1);

		//Check promoting Task
		restTemplate.exchange(
			"/api/kanban/next",
			HttpMethod.PUT,
			new HttpEntity<Object>(task1, createHeaders(jwt)),
			Task.class
		);

		ResponseEntity<Task> promotedTask = restTemplate.exchange(
			"/api/kanban/" + task1.getId(), 
			HttpMethod.GET,
			new HttpEntity<>(createHeaders(jwt)),
		 	Task.class
			);

		Assertions.assertThat(promotedTask.getStatusCode()).isEqualTo(HttpStatus.OK);
		Assertions.assertThat(promotedTask.getBody().getStatus()).isEqualTo(Status.IN_PROGRESS);

		//Check demoting Task
		restTemplate.exchange(
			"/api/kanban/prev",
			HttpMethod.PUT,
			new HttpEntity<Object>(task2, createHeaders(jwt)),
			Task.class
		);

		ResponseEntity<Task> demotedTask = restTemplate.exchange(
			"/api/kanban/" + task2.getId(),
			HttpMethod.GET,
			new HttpEntity<>(createHeaders(jwt)),
			Task.class
		);

		Assertions.assertThat(demotedTask.getBody().getStatus()).isEqualTo(Status.OPEN);
		
		Task task2editet = new Task("Backend Programmieren!", "TEEEEEESSSSSTSS!", Status.OPEN, List.of(mockTag));
		task2editet.setId(resTask1.getBody().getId());
		task2editet.setTags(resTask1.getBody().getTags());
		task2editet.setCreatedById(resTask1.getBody().getCreatedById());
		
		restTemplate.exchange(
			"/api/kanban/",
			HttpMethod.PUT,
			new HttpEntity<Object>(task2editet, createHeaders(jwt)),
			Void.class
		);

		//Check editing Task
		ResponseEntity<Task> editedTask = restTemplate.exchange(
			"/api/kanban/" + task2.getId(),
			HttpMethod.GET,
			new HttpEntity<>(createHeaders(jwt)),
			Task.class
		);

		Assertions.assertThat(editedTask.getStatusCode()).isEqualTo(HttpStatus.OK);
		Assertions.assertThat(editedTask.getBody()).isEqualTo(task2editet);

		//Task deletion test
		ResponseEntity<Task[]> resTaskArray  = restTemplate.exchange(
			"/api/kanban",
			HttpMethod.GET,
			new HttpEntity<>(createHeaders(jwt)),
			Task[].class
		);

		Assertions.assertThat(resTaskArray.getBody().length).isEqualTo(2);
		restTemplate.exchange(
			"/api/kanban/" + task1.getId(),
			HttpMethod.DELETE,
			new HttpEntity<>(createHeaders(jwt)),
			Void.class
			);

		ResponseEntity<Task[]> newResTaskArray  = restTemplate.exchange(
			"/api/kanban",
			HttpMethod.GET,
			new HttpEntity<>(createHeaders(jwt)),
			Task[].class);
		Assertions.assertThat(newResTaskArray.getBody().length).isEqualTo(1);

		//Check get tags 
		ResponseEntity<Tag[]> tagArray = restTemplate.exchange(
			"/api/kanban/tags",
			HttpMethod.GET,
			new HttpEntity<>(createHeaders(jwt)),
			Tag[].class
		);
		Tag tag = new Tag("Programmieren", "#5522ff");
		tag.setCreatedByID(resTask.getBody().getCreatedById());
		tag.setId(tagArray.getBody()[0].getId());


		Assertions.assertThat(tagArray.getBody()).containsExactlyInAnyOrder(tag);
	}

	final HttpHeaders createHeaders(String jwt) {
		String authHeaderValue = "Bearer " + jwt;
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", authHeaderValue);
		return headers;
	}
}
