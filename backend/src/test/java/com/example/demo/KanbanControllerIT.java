package com.example.demo;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.demo.Tag.Tag;
import com.example.demo.Task.Task;
import com.example.demo.Task.mockTag;

import java.util.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class KanbanControllerIT {

	Task task1 = new Task("Java Lernen", "Immer fleißig tests schreiben!", Status.OPEN, List.of(new mockTag("Programmieren", "#5522ff"), new mockTag("Lernen", "#5f68ea")));
	Task task2 = new Task("Backend Programmieren", "bla bla bla", Status.IN_PROGRESS);

	@Autowired
	TestRestTemplate restTemplate = new TestRestTemplate();

	@Test
	void kanbanIntegrationTest() {

		//Check for empty List
		ResponseEntity<Task[]> emptyArray = restTemplate.getForEntity("/api/kanban", Task[].class);
		Assertions.assertThat(emptyArray.getStatusCode()).isEqualTo(HttpStatus.OK);
		Assertions.assertThat(emptyArray.getBody().length).isEqualTo(0);

		// Check adding Tasks
		restTemplate.postForObject("/api/kanban", task1, Void.class);
		restTemplate.postForObject("/api/kanban", task2, Void.class);

		ResponseEntity<Task[]> ArraywithTask = restTemplate.getForEntity("/api/kanban", Task[].class);
		Assertions.assertThat(ArraywithTask.getBody().length).isEqualTo(2);
		Assertions.assertThat(ArraywithTask.getBody()).containsExactlyInAnyOrder(task1, task2);
	
		//Check get Task By ID
		Task task = restTemplate.getForObject("/api/kanban/" + task1.getId(),Task.class);
		Assertions.assertThat(task).isEqualTo(task1);

		//Check promoting Task
		restTemplate.put("/api/kanban/next", task1);
		ResponseEntity<Task> promotedTask = restTemplate.getForEntity("/api/kanban/" + task1.getId(), Task.class);
		Assertions.assertThat(promotedTask.getStatusCode()).isEqualTo(HttpStatus.OK);
		Assertions.assertThat(promotedTask.getBody().getStatus()).isEqualTo(Status.IN_PROGRESS);

		//Check demoting Task
		restTemplate.put("/api/kanban/prev", task2);
		ResponseEntity<Task> demotedTask = restTemplate.getForEntity("/api/kanban/" + task2.getId(), Task.class);
		Assertions.assertThat(demotedTask.getBody().getStatus()).isEqualTo(Status.OPEN);

		//Check editing Task
		Task task2editet = new Task("Backend Programmieren!", "TEEEEEESSSSSTSS!", Status.OPEN, task2.getId());
		restTemplate.put("/api/kanban/", task2editet );
		ResponseEntity<Task> editedTask = restTemplate.getForEntity("/api/kanban/" + task2.getId(), Task.class);
		Assertions.assertThat(editedTask.getStatusCode()).isEqualTo(HttpStatus.OK);
		Assertions.assertThat(editedTask.getBody()).isEqualTo(task2editet);

		//Task deletion test
		ResponseEntity<Task[]> resTaskArray  = restTemplate.getForEntity("/api/kanban",Task[].class);
		Assertions.assertThat(resTaskArray.getBody().length).isEqualTo(2);
		restTemplate.delete("/api/kanban/" + task1.getId());
		ResponseEntity<Task[]> newResTaskArray  = restTemplate.getForEntity("/api/kanban",Task[].class);
		Assertions.assertThat(newResTaskArray.getBody().length).isEqualTo(1);

		//Check get tags 
		ResponseEntity<Tag[]> tagArray = restTemplate.getForEntity(("api/kanban/tags"), Tag[].class);
		Assertions.assertThat(tagArray.getBody()).containsExactlyInAnyOrder(new Tag("Programmieren", "#5522ff"), new Tag("Lernen", "#5f68ea"));
	}
}
