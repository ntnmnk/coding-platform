package com.example.springbootproject.entities;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.util.*;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@Document(collection = "users")
public class User {

  @Id
  private String userId;

  @NotBlank
  private String username;

  @NotNull(message = "Score is required")
  @Positive(message = "Score must be greater than 0")
  @Min(0)
  @Max(100)
  private int score;

  @Size(max = 3)
  private Set<String> badges = new HashSet<>();
}
