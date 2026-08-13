package ru.kashtanov.news_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Viktor Кashtanov
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewsDto {
    private Long id;
    private String title;
    private Long authorId;
    private List<Long> contentIdList;
    private List<Long> mentioned_users;

  public NewsDto( String title,Long authorId){ // empty news, like a stab
      this.title = title;
      this.authorId = authorId;
  }


}
